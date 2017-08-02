package com.mall.b2bp.services.impl.orderFromDB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.order.OrderReturnModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderReturnEntryModel;
import com.mall.b2bp.models.order.OrderReturnExportModel;
import com.mall.b2bp.models.order.OrderReturnModel;
import com.mall.b2bp.models.order.OrderReturnReceivedModel;
import com.mall.b2bp.oxm.order.OrderReturnBean;
import com.mall.b2bp.oxm.order.OrderReturnBeans;
import com.mall.b2bp.oxm.order.OrderReturnEntryBean;
import com.mall.b2bp.populators.email.EmailPopulator;
import com.mall.b2bp.populators.order.OrderReturnExportPopulator;
import com.mall.b2bp.populators.order.OrderReturnPopulator;
import com.mall.b2bp.services.orderFromDB.OrderEntryService;
import com.mall.b2bp.services.orderFromDB.OrderReturnEntryService;
import com.mall.b2bp.services.orderFromDB.OrderReturnService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.email.NotificationEmailVo;
import com.mall.b2bp.vos.order.OrderParameterVo;
import com.mall.b2bp.vos.order.OrderReturnEntryVo;
import com.mall.b2bp.vos.order.OrderReturnExportVo;
import com.mall.b2bp.vos.order.OrderReturnVo;
import com.mall.b2bp.vos.order.OrderViewVo;
import com.mall.b2bp.vos.user.UserVo;

@Service("orderReturnServiceFromDB")
public class OrderReturnServiceImpl implements OrderReturnService {

	private static final Logger LOG = LoggerFactory
			.getLogger(OrderReturnServiceImpl.class);

	private OrderReturnModelMapper orderReturnModelMapper;


	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "orderReturnEntryServiceFromDB")
	private OrderReturnEntryService orderReturnEntryService;

	
	@Resource(name = "orderEntryServiceFromDB")
	private OrderEntryService orderEntryService;
	
	public OrderReturnModelMapper getOrderReturnModelMapper() {
		return orderReturnModelMapper;
	}

	@Autowired
	public void setOrderReturnModelMapper(
			OrderReturnModelMapper orderReturnModelMapper) {
		this.orderReturnModelMapper = orderReturnModelMapper;
	}


	@Override
	public int insertSelective(OrderReturnModel record) {
		return orderReturnModelMapper.insertSelective(record);
	}

	@Override
	public OrderReturnVo selectReturnAndReturnEntryByPrimaryKey(BigDecimal id)
			throws ServiceException {
		OrderReturnModel model = orderReturnModelMapper.selectByPrimaryKey(id);
		OrderReturnVo vo = null;
		try {
			if (model != null) {

				OrderReturnPopulator p = new OrderReturnPopulator();
				vo = p.converModelToVo(model);
				BigDecimal returnId = model.getId();

				List<OrderReturnEntryVo> entryList = orderReturnEntryService
						.selectByReturnId(returnId);
				vo.setEntryList(entryList);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return vo;
	}

	@Override
	public int updateByPrimaryKeySelective(OrderReturnModel record) {
		return orderReturnModelMapper.updateByPrimaryKeySelective(record);
	}


	public OrderReturnEntryService getOrderReturnEntryService() {
		return orderReturnEntryService;
	}

	public void setOrderReturnEntryService(
			OrderReturnEntryService orderReturnEntryService) {
		this.orderReturnEntryService = orderReturnEntryService;
	}

	private OrderReturnModel getModel(OrderReturnBean bean) {
		OrderReturnModel r = new OrderReturnModel();

		r.setReturnType(bean.getReturnType());
		r.setOrderId(bean.getOrderId());
		r.setHybrisOrderId(bean.getHybrisOrderId());
		r.setHybrisReturnId(bean.getHybrisReturnId());
		r.setPickStore(bean.getPickStore());
		r.setPickOrderId(bean.getPickOrderId());
		r.setSupplierId(bean.getSupplierId());
		r.setReturnCreateDate(getDate(bean.getReturnCreateDate()));
		r.setRemark(bean.getRemark());
		r.setSpecialInstruction(bean.getSpecialInstruction());

		r.setCustomerId(bean.getCustomerId());
		r.setCustomerType(bean.getCustomerType());
		r.setCustomerName(bean.getCustomerName());
		r.setCustomerPhoneNo(bean.getCustomerPhoneNo());
		r.setCustomerMobileNo(bean.getCustomerMobileNo());
		r.setTenderType(bean.getTenderType());
		r.setPaymentRef(bean.getPaymentRef());
		r.setCollectDate(getDate(bean.getCollectDate()));
		r.setCollectTimeSlot(bean.getCollectTimeSlot());
		r.setCollectDistrict(bean.getCollectDistrict());
		r.setContactName(bean.getContactName());
		r.setContactPhoneNo(bean.getContactPhoneNo());
		r.setContactMobileNo(bean.getContactMobileNo());
		r.setCollectAddress(bean.getCollectAddress());

		r.setCreatedBy(ConstantUtil.JOB_USER_NAME);
		r.setCreatedDate(new Date());
		r.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
		r.setLastUpdatedDate(new Date());

		List<OrderReturnEntryBean> orderReturnEntryBeanList = bean
				.getEntryBeanList();

		String status = getReturnStatus(orderReturnEntryBeanList);

		r.setReturnRequestStatus(status);
		r.setReturnRequestUpdateDate(new Date());
		return r;
	}

	private OrderReturnEntryModel getOrderReturnEntryModel(OrderReturnEntryBean bean, BigDecimal orderReturnId) {

		OrderReturnEntryModel r = new OrderReturnEntryModel();
		r.setOrderReturnId(orderReturnId);

		r.setSkuId(bean.getSkuId());
		r.setSkuType(bean.getSkuType());
		r.setSupplierId(bean.getSupplierId());
		r.setBrand(bean.getBrand());
		r.setBrandSec(bean.getBrandSec());
		r.setProductName(bean.getProductName());
		r.setProductNameSec(bean.getProductNameSec());
		r.setOrderQty(bean.getOrderQty());
		r.setExpectedQty(bean.getExpectedQty());
		r.setReturnReqQty(bean.getReturnReqQty());
		r.setWriteOffQty(bean.getWriteOffQty());
		r.setSizeDesc(bean.getSizeDesc());
		r.setSkuCollectRmk(bean.getSkuCollectRmk());

		r.setCreatedBy(ConstantUtil.JOB_USER_NAME);
		r.setCreatedDate(new Date());
		r.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
		r.setLastUpdatedDate(new Date());

		return r;
	}

	private Date getDate(String date) {
		if (StringUtils.isNotEmpty(date)) {
			return DateUtils.parseDateStr(date, DateUtils.DATE_FORMAT_2);
		}
		return null;
	}

	@Override
	public List<NotificationEmailVo> updateReturnOrders(OrderReturnBeans orderBeans)throws ServiceException {
		Set set = new HashSet<>();
		List<NotificationEmailVo> returnSaveBeanList = new ArrayList<>();
		if (orderBeans != null && CollectionUtils.isNotEmpty(orderBeans.getOrderReturnBeanList())) {
			List<OrderReturnBean> orderReturnList = orderBeans.getOrderReturnBeanList();
			try {
				for (OrderReturnBean r : orderReturnList) {

					List<OrderReturnEntryBean> orderReturnEntryBeanList = r.getEntryBeanList();

					if (selectByReturnId(r.getHybrisReturnId()) != null)
						continue;

					OrderReturnModel record = getModel(r);

					insertSelective(record);
					LOG.info("insert into PSSP_ORDER_RETURN table. ReturnReceiveInd id:"+ record.getReturnReceiveInd());

					if (CollectionUtils.isNotEmpty(orderReturnEntryBeanList)) {

						for (OrderReturnEntryBean er : orderReturnEntryBeanList) {
							OrderReturnEntryModel returnEntry = getOrderReturnEntryModel(er, record.getId());							
							orderReturnEntryService.insertSelective(returnEntry);
							
							//LOG.info("insert into PSSP_ORDER_RETURN_ENTRY table. skuid:"+returnEntry.getSkuId());
							
							//LOG.info("record.getHybrisOrderId()_record.getHybrisReturnId()_returnEntry.getSupplierId():"+
							//		record.getHybrisOrderId()+"_"+record.getHybrisReturnId()+"_"+returnEntry.getSupplierId());
							if(set.add(record.getHybrisOrderId()+"_"+record.getHybrisReturnId()+"_"+returnEntry.getSupplierId())){
							//	LOG.info("success add hybrisOrderid+rvsId+ supplierId:"+record.getHybrisOrderId()+"_"+record.getHybrisReturnId()+"_"+returnEntry.getSupplierId());
								returnSaveBeanList.add(	getBean(record,returnEntry));
							}
						}
					}
				}
				
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage(), e);
			}
		}
		return returnSaveBeanList;
		
	}
	
	
	private NotificationEmailVo getBean(OrderReturnModel orderModel,OrderReturnEntryModel entryModel){
		NotificationEmailVo vo = new NotificationEmailVo();
		vo.setEmailType(EmailPopulator.RVS_ARRIVES);
		vo.setHybridOrderId(orderModel.getHybrisOrderId());
		vo.setOrderReturnId(orderModel.getHybrisReturnId());
		vo.setSupplierId(entryModel.getSupplierId());
		
		return vo;
	}


	private String getReturnStatus(
			List<OrderReturnEntryBean> orderReturnEntryBeanList) {

		String status = ConstantUtil.RETURN_CONFIRMED;
		if (CollectionUtils.isNotEmpty(orderReturnEntryBeanList)) {

			for (OrderReturnEntryBean er : orderReturnEntryBeanList) {

				if (er.getExpectedQty() != null
						&& er.getExpectedQty().intValue() > 0) {

					status = ConstantUtil.WAIT_RETURN;
					break;
				}
			}
		}
		return status;
	}

	@Override
	public OrderReturnModel selectByReturnId(String hybrisReturnId) {
		return orderReturnModelMapper.selectByReturnId(hybrisReturnId);
	}

	@Override
	public List<OrderReturnVo> selectByOrderId(String orderId)
			throws ServiceException {
		List<OrderReturnVo> voList = new ArrayList<>();

		OrderParameterVo vo = new OrderParameterVo();
		String supplierId = null;
		UserVo userVo = sessionService.getCurrentUser();
		if (userVo != null) {
			if ("SUPPLIER".equals(userVo.getUserRole())) {
				supplierId = userVo.getSupplierId();
			}
		}

		vo.setSupplierId(supplierId);
		vo.setOrderId(orderId);
		try {
			List<OrderReturnModel> list = orderReturnModelMapper
					.selectByOrderId(vo);
			if (CollectionUtils.isNotEmpty(list)) {
				OrderReturnPopulator p = new OrderReturnPopulator();
				for (OrderReturnModel m : list) {
					voList.add(p.converModelToVo(m));
				}
			}

		} catch (Exception e) {

			LOG.error(e.getMessage(), e);

			throw new ServiceException(e.getMessage(), e);
		}
		return voList;
	}

	private String checkOrderEntryReturn(List<OrderReturnEntryVo> list) {

		if (CollectionUtils.isNotEmpty(list)) {

			for (OrderReturnEntryVo vo : list) {
				// ExpectedCollectQty is blank or 0, no items need to be
				// collected

				if( vo.getReturnReqQty()==null)
					continue;
				
				
				
				if (vo.getExpectedQty() != null && vo.getExpectedQty().intValue() > 0) {
					if (vo.getActualCollectedQty() == null) {
						return "order_return_actual_collected_Quantity_not_null";
					}

					if (vo.getActualCollectedQty().intValue() > vo.getExpectedQty().intValue()) {
						return "order_return_actual_collected_Quantity_invalid";
					}
				}
			}
		}

		return null;

	}

	@Override
	public boolean updateConfirmOrderReturn(OrderReturnVo orderReturnVo,ResponseData responseData) throws ServiceException {

		String updatedBy = null;
		try {
			UserVo userVo = sessionService.getCurrentUser();
			if (userVo != null) {
				updatedBy = userVo.getUserId();
			}

			OrderReturnModel record = new OrderReturnModel();
			if (orderReturnVo != null) {
				List<OrderReturnEntryVo> list = orderReturnVo.getEntryList();

				String msg = checkOrderEntryReturn(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

				if (StringUtils.isNotEmpty(msg)) {
					responseData.add(msg);
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					return false;
				}
				
				record.setId(orderReturnVo.getId());
				record.setLastUpdatedBy(updatedBy);
				record.setLastUpdatedDate(new Date());
				record.setReturnRequestStatus(ConstantUtil.RETURN_CONFIRMED);
				record.setReturnReceiveInd("P");
				record.setReturnRequestUpdateDate(new Date());

				updateByPrimaryKeySelective(record);
				
				

				if (CollectionUtils.isNotEmpty(list)) {

					for (OrderReturnEntryVo vo : list) {
						// ExpectedCollectQty is blank or 0, no items need to be
						// collected

						if (vo.getExpectedQty() != null && vo.getExpectedQty().intValue() > 0) {
							
							
							OrderReturnEntryModel enModel = new OrderReturnEntryModel();
							enModel.setId(vo.getId());
							enModel.setLastUpdatedBy(updatedBy);
							enModel.setLastUpdatedDate(new Date());
							enModel.setActualCollectedQty(vo.getActualCollectedQty());
							enModel.setSkuCollectRmk(vo.getSkuCollectRmk());

							orderReturnEntryService.updateByPrimaryKeySelective(enModel);
							
						
						}
					}
				}
				
				//update order entry refund
				orderEntryService.updateTotalReturnedQtyFromRVS(orderReturnVo.getOrderId());
			}

			return true;
		} catch (Exception e) {

			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<OrderReturnExportVo> exportOrderReturnList(
			OrderViewVo orderViewVo) throws ServiceException {
		  Date orderfmDate = null;
	        Date ordertoDate = null;
	        Date shippedfmDate = null;
	        Date shippedtoDate = null;
	        Date deliveryfmDate = null;
	        Date deliverytoDate = null;

	        try {

	            orderfmDate = DateUtils.parseDateStr(orderViewVo.getOrderDateFr(), DateUtils.DATE_FORMAT);
	            ordertoDate = DateUtils.parseDateStr(orderViewVo.getOrderDateTo(), DateUtils.DATE_FORMAT);
	            shippedfmDate = DateUtils.parseDateStr(orderViewVo.getShippedDateFr(), DateUtils.DATE_FORMAT);
	            shippedtoDate = DateUtils.parseDateStr(orderViewVo.getShippedDateTo(), DateUtils.DATE_FORMAT);
	            deliveryfmDate = DateUtils.parseDateStr(orderViewVo.getDeliveryDateFr(), DateUtils.DATE_FORMAT);
	            deliverytoDate = DateUtils.parseDateStr(orderViewVo.getDeliveryDateTo(), DateUtils.DATE_FORMAT);

	            OrderParameterVo orderVo = new OrderParameterVo();
	            orderVo.setOrderId(orderViewVo.getOrderId());
	            orderVo.setPickOrderId(orderViewVo.getPickOrderId());
	            orderVo.setInvoiceReadyInd(orderViewVo.getInvoiceReadyInd());
	            orderVo.setOrderType(orderViewVo.getOrderType());
	            orderVo.setReturnRequest(orderViewVo.getReturnRequest());

	            orderVo.setSupplier(StringUtils.isNotEmpty(orderViewVo.getSupplier())? orderViewVo.getSupplier().split(",") : null);

	            orderVo.setOrderStatus(getOrderstatus(orderViewVo.getOrderStatus()));


	            orderVo.setDeliveryDateFr(deliveryfmDate);
	            orderVo.setDeliveryDateTo(deliverytoDate);
	            orderVo.setOrderDateFr(orderfmDate);
	            orderVo.setOrderDateTo(ordertoDate);
	            orderVo.setShippedDateFr(shippedfmDate);
	            orderVo.setShippedDateTo(shippedtoDate);

	            List<OrderReturnExportModel> orderModelList = orderReturnModelMapper.exportOrderReturnList(orderVo);

	            return populatorOrderReturnExportVo(orderModelList);

	        } catch (Exception e) {

	            LOG.error(e.getMessage(), e);
	            throw new ServiceException(e.getMessage(), e);

	        }
	}
	
    private String[] getOrderstatus(String[] status) {

        if (status == null || status.length == 0) {
            return new String[0] ;
        }

        if (status.length == 1 && StringUtils.isEmpty(status[0]))
            return new String[0];
        else
            return status;

    }
	
    private List<OrderReturnExportVo> populatorOrderReturnExportVo(List<OrderReturnExportModel> orderModelList){
        List<OrderReturnExportVo> orderVoArrayList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderModelList)) {
        	OrderReturnExportPopulator populator = new OrderReturnExportPopulator();
            for (OrderReturnExportModel orderModel : orderModelList) {
                orderVoArrayList.add(populator.converModelToVo(orderModel));
            }
        }
        return orderVoArrayList;
    }


	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	
	@Override
	public List<OrderReturnReceivedModel> getReturnReceived() {
		return orderReturnModelMapper.getReturnReceived();
	}

}
