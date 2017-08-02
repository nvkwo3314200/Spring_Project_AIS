package com.mall.b2bp.services.impl.orderFromDB;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.order.OrderModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderEntryModel;
import com.mall.b2bp.models.order.OrderExportModel;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.populators.order.OrderExportPopulator;
import com.mall.b2bp.populators.order.OrderPopulator;
import com.mall.b2bp.services.orderFromDB.OrderEntryService;
import com.mall.b2bp.services.orderFromDB.OrderService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.order.CancelRequest;
import com.mall.b2bp.vos.order.CancelResponse;
import com.mall.b2bp.vos.order.OrderCancelReponseVo;
import com.mall.b2bp.vos.order.OrderEntryVo;
import com.mall.b2bp.vos.order.OrderExportVo;
import com.mall.b2bp.vos.order.OrderParameterVo;
import com.mall.b2bp.vos.order.OrderViewVo;
import com.mall.b2bp.vos.order.OrderVo;
import com.mall.b2bp.vos.user.UserVo;

/**
 * Created by USER on 2016/3/10.
 */
@Service("orderServiceFromDB")
class OrderServiceImpl implements OrderService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

	private OrderModelMapper orderModelMapper;

	@Resource(name = "orderEntryServiceFromDB")
	private OrderEntryService orderEntryService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	public OrderEntryService getOrderEntryService() {
		return orderEntryService;
	}

	public void setOrderEntryService(OrderEntryService orderEntryService) {
		this.orderEntryService = orderEntryService;
	}

	public OrderModelMapper getOrderModelMapper() {
		return orderModelMapper;
	}

	@Autowired
	public void setOrderModelMapper(OrderModelMapper orderModelMapper) {
		this.orderModelMapper = orderModelMapper;
	}

	private OrderParameterVo getOrderParameterVo(final OrderViewVo orderViewVo) {
		Date orderfmDate = null;
		Date ordertoDate = null;
		Date shippedfmDate = null;
		Date shippedtoDate = null;
		Date deliveryfmDate = null;
		Date deliverytoDate = null;

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

		orderVo.setSupplier(StringUtils.isNotEmpty(orderViewVo.getSupplier()) ? orderViewVo.getSupplier().split(",")
				: null);

		orderVo.setOrderStatus(getOrderstatus(orderViewVo.getOrderStatus()));

		orderVo.setDeliveryDateFr(deliveryfmDate);
		orderVo.setDeliveryDateTo(deliverytoDate);
		orderVo.setOrderDateFr(orderfmDate);
		orderVo.setOrderDateTo(ordertoDate);
		orderVo.setShippedDateFr(shippedfmDate);
		orderVo.setShippedDateTo(shippedtoDate);
		orderVo.setReturnRequest(orderViewVo.getReturnRequest());

		return orderVo;
	}

	@Override
	public List<OrderVo> viewOrderList(final OrderViewVo orderViewVo) throws ServiceException {

		try {

			OrderParameterVo orderVo = getOrderParameterVo(orderViewVo);
			List<OrderModel> orderModelList = orderModelMapper.viewOrderList(orderVo);

			return populatorVo(orderModelList);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);

		}

	}

	private List<OrderVo> populatorVo(List<OrderModel> orderModelList) {
		List<OrderVo> orderVoArrayList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(orderModelList)) {
			OrderPopulator populator = new OrderPopulator();
			for (OrderModel orderModel : orderModelList) {
				orderVoArrayList.add(populator.converModelToVo(orderModel));
			}
		}
		return orderVoArrayList;
	}

	private String[] getOrderstatus(String[] status) {

		if (status == null || status.length == 0) {
			return new String[0];
		}

		if (status.length == 1 && StringUtils.isEmpty(status[0]))
			return new String[0];

		List<String> list = new ArrayList<>();
		for (String str : status) {

			if ("DELIVERY".equals(str)) {
				list.add("DELIVERY_CONFIRMED");
				list.add("DELIVERY_FAILURE");
			} else
				list.add(str);
		}

		String[] array = new String[list.size()];
		int i = 0;
		for (String str : list) {
			array[i] = str;
			i++;
		}

		return array;
	}

	private List<OrderVo> regetOrderList(List<OrderVo> orderVoList) {
		List<OrderVo> list = new ArrayList<>();
		try {
			for (OrderVo orderVo : orderVoList) {
				list.add(selectByPrimaryKey(orderVo.getId()));
			}
		} catch (ServiceException e) {
			LOG.error("regetOrderList error:" + e.getMessage(), e);
		}
		return list;
	}

	/**
	 * Delivery Success to Y Deliver date to today Delivered quantity to picked
	 * quantity
	 * 
	 * @param waitForUpdateStatus
	 * @param orderVoList
	 * @param responseData
	 *            //@param session
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public boolean updateBatchForSupplier(String waitForUpdateStatus, List<OrderVo> orderVoList,
			final ResponseData responseData) throws ServiceException {

		boolean success = false;

		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

		String msg = checkOrderStatus(waitForUpdateStatus, orderVoList);
		if (msg != null) {
			responseData.add(msg);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return false;
		}
		try {

			List<OrderVo> list = regetOrderList(orderVoList);

			for (OrderVo orderVo : list) {

				String cStatus = orderVo.getStatus();

				if (ConstantUtil.PICKED.equals(waitForUpdateStatus) && ConstantUtil.NEW.equals(cStatus)) {
					handlePick(orderVo);
				}

				else if (ConstantUtil.SHIPPED.equals(waitForUpdateStatus) && ConstantUtil.PICKED.equals(cStatus)) {
					orderVo.setConsignmentShippedDate(new Date());
				}

				else if (ConstantUtil.DELIVERY.equals(waitForUpdateStatus) && ConstantUtil.SHIPPED.equals(cStatus)) {
					handleDelivery(orderVo);
				}

				success = updateForSupplier(waitForUpdateStatus, orderVo, responseData);

				if (!success) {
					break;
				}
			}
		} catch (Exception e) {
			LOG.error("UpdateBatchForSupplier error:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return success;
	}

	private void handleDelivery(OrderVo orderVo) {

		orderVo.setDeliveryDate(new Date());
		orderVo.setDeliverySuccess("Y");

		if (CollectionUtils.isNotEmpty(orderVo.getEntryVoList())) {
			for (OrderEntryVo vo : orderVo.getEntryVoList()) {
				vo.setDeliveryQty(vo.getPickedQty());

			}
		}

	}

	private void handlePick(OrderVo orderVo) {
		orderVo.setPickDate(new Date());

		if (CollectionUtils.isNotEmpty(orderVo.getEntryVoList())) {
			for (OrderEntryVo vo : orderVo.getEntryVoList()) {
				vo.setPickedQty(vo.getQty());
			}
		}
	}

	private String checkOrderStatus(String waitForUpdateStatus, List<OrderVo> orderVoList) {
		if (orderVoList != null) {

			for (OrderVo orderVo : orderVoList) {

				String msg = validateStatus(orderVo, waitForUpdateStatus);
				if (StringUtils.isNotEmpty(msg)) {
					return msg;
				}
			}
		}
		return null;
	}

	private String validateStatus(OrderVo orderVo, String waitForUpdateStatus) {
		String cStatus = orderVo.getStatus();

		if (!ConstantUtil.PICKED.equals(waitForUpdateStatus) && !ConstantUtil.SHIPPED.equals(waitForUpdateStatus)
				&& !ConstantUtil.DELIVERY.equals(waitForUpdateStatus)) {
			return "order_Validation_waitOrderStatus_null";
		}

		if (ConstantUtil.PICKED.equals(waitForUpdateStatus) && !ConstantUtil.NEW.equals(cStatus)) {
			return "order_Validation_select_Waiting";
		}

		if (ConstantUtil.SHIPPED.equals(waitForUpdateStatus) && !ConstantUtil.PICKED.equals(cStatus))
			return "order_Validation_select_Pick";

		if (ConstantUtil.DELIVERY.equals(waitForUpdateStatus) && !ConstantUtil.SHIPPED.equals(cStatus)) {
			return "order_Validation_select_Shipped";
		}

		return null;
	}

	@Override
	public boolean updateSingleForSupplier(String waitForUpdateStatus, final OrderVo orderVo, ResponseData responseData)
			throws ServiceException {
		return updateForSupplier(waitForUpdateStatus, orderVo, responseData);
	}

	private boolean updateForSupplier(String waitForUpdateStatus, final OrderVo orderVo, final ResponseData responseData)
			throws ServiceException {

		String updatedBy = null;

		UserVo userVo = sessionService.getCurrentUser();
		if (userVo != null) {
			updatedBy = userVo.getUserId();
		}

		String cStatus = orderVo.getStatus();

		boolean validateResult = true;

		String msg = validateStatus(orderVo, waitForUpdateStatus);
		if (StringUtils.isNotEmpty(msg)) {
			responseData.add(msg);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);

			return false;
		}

		try {

			if (ConstantUtil.PICKED.equals(waitForUpdateStatus) && ConstantUtil.NEW.equals(cStatus)) {
				validateResult = handlePick(orderVo, responseData, updatedBy);
			} else if (ConstantUtil.SHIPPED.equals(waitForUpdateStatus) && ConstantUtil.PICKED.equals(cStatus)) {
				validateResult = handleShipped(orderVo, responseData, updatedBy);
			} else if (ConstantUtil.DELIVERY.equals(waitForUpdateStatus) && ConstantUtil.SHIPPED.equals(cStatus)) {
				validateResult = handleDelivery(orderVo, responseData, updatedBy);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

		return validateResult;
	}

	private OrderModel getUpdateModel(OrderVo orderVo, String updatedBy) {

		OrderModel updateModel = new OrderModel();
		updateModel.setId(orderVo.getId());
		updateModel.setLastUpdatedDate(new Date());
		updateModel.setLastUpdatedBy(updatedBy);

		return updateModel;
	}

	private boolean handleDelivery(final OrderVo orderVo, final ResponseData responseData, String updatedBy)
			throws ServiceException {
		boolean validateResult = true;

		OrderModel updateModel = getUpdateModel(orderVo, updatedBy);

		Date deliveryDate = orderVo.getDeliveryDate();
		if (deliveryDate == null) {
			responseData.add("order_Validation_Deliver_Date_null");
			validateResult = false;
		}

		if (StringUtils.isEmpty(orderVo.getDeliverySuccess())) {
			responseData.add("order_Validation_Delivery_Success_null");
			validateResult = false;
		}
		List<OrderEntryVo> orderEntryVoList = orderVo.getEntryVoList();

		// Picked quantity must be less than or equal to the ordered quantity
		if (CollectionUtils.isEmpty(orderEntryVoList)) {
			validateResult = false;
			responseData.add("order_Validation_not_entry");
			return validateResult;
		}

		// Picked quantity must be less than or equal to the ordered quantity.

		for (OrderEntryVo vo : orderEntryVoList) {

			if (vo.getDeliveryQty() == null) {
				responseData.add("order_Validation_Picked_quantity_null");
				validateResult = false;

			}

			if (vo.getDeliveryQty() != null && vo.getPickedQty() != null
					&& vo.getDeliveryQty().intValue() > vo.getPickedQty().intValue()) {
				responseData.add("order_Validation_Delivery_quantity");
				validateResult = false;

			}

			if (!validateResult)
				break;

		}

		if (validateResult) {
			String f = "Y".equalsIgnoreCase(orderVo.getDeliverySuccess()) ? ConstantUtil.DELIVERY_CONFIRMED
					: ConstantUtil.DELIVERY_FAILURE;
			updateModel.setStatus(f);
			updateModel.setDeliveryDate(orderVo.getDeliveryDate());
			updateModel.setDeliverySuccess(orderVo.getDeliverySuccess());
			updateModel.setDeliveryInd("Y");
			updateModel.setLastUpdatedBy(updatedBy);
			updateModel.setLastUpdatedDate(new Date());

			updateByPrimaryKeySelective(updateModel);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

			for (OrderEntryVo vo : orderEntryVoList) {
				OrderEntryModel orderEntryModel = new OrderEntryModel();
				orderEntryModel.setLastUpdatedBy(updatedBy);
				orderEntryModel.setLastUpdatedDate(new Date());
				orderEntryModel.setId(vo.getId());

				orderEntryModel.setDeliveryQty(vo.getDeliveryQty());

				orderEntryService.updateByPrimaryKeySelective(orderEntryModel);
			}

		}

		return validateResult;
	}

	private boolean handleShipped(final OrderVo orderVo, final ResponseData responseData, String updatedBy)
			throws ServiceException {
		boolean validateResult = true;

		OrderModel updateModel = getUpdateModel(orderVo, updatedBy);
		Date shippedDate = orderVo.getConsignmentShippedDate();
		if (shippedDate == null) {
			responseData.add("order_Validation_Ship_Date_null");
			validateResult = false;
		} else {
			Date pickDate = orderVo.getPickDate();
			// validata Pick date must be later than or equal to the order
			// create date
			if (!DateUtils.compareDate(shippedDate, pickDate)) {

				responseData.add("order_Validation_Ship_Date");
				validateResult = false;
			}
		}

		if (validateResult) {
			updateModel.setStatus(ConstantUtil.SHIPPED);
			updateModel.setConsignmentShippedDate(orderVo.getConsignmentShippedDate());
			updateModel.setBoxNum(orderVo.getBoxNum());
			updateModel.setTrackId(orderVo.getTrackId());
			updateModel.setShippedInd("Y");
			updateModel.setLastUpdatedBy(updatedBy);
			updateModel.setLastUpdatedDate(new Date());
			updateByPrimaryKeySelective(updateModel);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		}

		return validateResult;

	}

	private boolean handlePick(final OrderVo orderVo, final ResponseData responseData, String updatedBy)
			throws ServiceException {

		boolean validateResult = true;

		OrderModel updateModel = getUpdateModel(orderVo, updatedBy);

		Date pickDate = orderVo.getPickDate();
		if (pickDate == null) {
			responseData.add("order_Validation_Pick_Date_null");
			validateResult = false;
		} else {
			Date createDate = orderVo.getOrderDatetime();
			// validata Pick date must be later than or equal to the order
			// create date
			if (!DateUtils.compareDate(pickDate, createDate)) {

				responseData.add("order_Validation_Pick_Date");
				validateResult = false;
			}
		}

		List<OrderEntryVo> orderEntryVoList = orderVo.getEntryVoList();

		if (CollectionUtils.isEmpty(orderEntryVoList)) {
			validateResult = false;
			responseData.add("order_Validation_not_entry");
			return validateResult;
		}

		// Picked quantity must be less than or equal to the ordered quantity
		for (OrderEntryVo vo : orderEntryVoList) {

			if (vo.getPickedQty() == null) {
				responseData.add("order_Validation_Picked_quantity_null");
				validateResult = false;

			}

			if (vo.getQty() != null && vo.getPickedQty() != null
					&& vo.getPickedQty().intValue() > vo.getQty().intValue()) {
				responseData.add("order_Validation_Picked_quantity");
				validateResult = false;

			}

			if (!validateResult)
				break;
		}

		if (validateResult) {
			updateModel.setStatus(ConstantUtil.PICKED);
			updateModel.setPickDate(orderVo.getPickDate());
			updateModel.setBoxNum(orderVo.getBoxNum());
			updateModel.setTrackId(orderVo.getTrackId());
			updateModel.setPickedInd("Y");
			updateModel.setLastUpdatedBy(updatedBy);
			updateModel.setLastUpdatedDate(new Date());

			updateByPrimaryKeySelective(updateModel);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

			for (OrderEntryVo vo : orderEntryVoList) {
				OrderEntryModel orderEntryModel = new OrderEntryModel();
				orderEntryModel.setLastUpdatedBy(updatedBy);
				orderEntryModel.setLastUpdatedDate(new Date());
				orderEntryModel.setId(vo.getId());

				orderEntryModel.setPickedQty(vo.getPickedQty());

				orderEntryService.updateByPrimaryKeySelective(orderEntryModel);
			}
		}

		return validateResult;
	}

	@Override
	public int insertSelective(OrderModel record) throws ServiceException {
		try {
			return orderModelMapper.insertSelective(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public OrderVo selectByPrimaryKey(BigDecimal orderId) throws ServiceException {
		try {
			OrderModel orderModel = orderModelMapper.selectByPrimaryKey(orderId);
			OrderVo orderVo = null;
			if (orderModel != null) {
				OrderPopulator populator = new OrderPopulator();
				orderVo = populator.converModelToVo(orderModel);
				List<OrderEntryVo> list = orderEntryService.selectByOrderId(orderId);
				orderVo.setEntryVoList(list);
			}

			return orderVo;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public int updateByPrimaryKeySelective(OrderModel record) throws ServiceException {
		try {
			return orderModelMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<OrderModel> selectByHybrisOrderId(String id) throws ServiceException {
		try {
			return orderModelMapper.selectByHybrisOrderId(id);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<OrderModel> getOrderListByInvoiceInd() throws ServiceException {

		try {
			return orderModelMapper.getOrderListByInvoiceInd();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<OrderVo> selectByOrderHome() throws ServiceException {
		List<OrderVo> list = new ArrayList<>();
		try {
			List<OrderModel> orderModelList = orderModelMapper.selectByOrderHome();

			if (CollectionUtils.isEmpty(orderModelList))
				return list;

			for (OrderModel orderVo : orderModelList) {
				list.add(selectByPrimaryKey(orderVo.getId()));
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

		return list;
	}

	@Override
	public OrderModel getOrderConsignmentID(String orderId, String supplierId) {
		OrderParameterVo vo = new OrderParameterVo();
		if (StringUtils.isNotEmpty(orderId) && StringUtils.isNotEmpty(supplierId)) {
			vo.setSupplierId(supplierId);
			vo.setOrderId(orderId);
			List<OrderModel> orders = orderModelMapper.getOrderConsignmentID(vo);
			if (CollectionUtils.isNotEmpty(orders)) {
				return orders.get(0);
			}
		}

		return null;
	}

	@Override
	public List<OrderExportVo> exportOrderList(OrderViewVo orderViewVo) throws ServiceException {

		try {
			OrderParameterVo orderVo = getOrderParameterVo(orderViewVo);

			List<OrderExportModel> orderModelList = orderModelMapper.exportOrderList(orderVo);

			return populatorExportOrderVo(orderModelList);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private List<OrderExportVo> populatorExportOrderVo(List<OrderExportModel> orderModelList) {
		List<OrderExportVo> orderVoArrayList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(orderModelList)) {
			OrderExportPopulator populator = new OrderExportPopulator();
			for (OrderExportModel orderModel : orderModelList) {
				orderVoArrayList.add(populator.converModelToVo(orderModel));
			}
		}
		return orderVoArrayList;
	}

	@Override
	public boolean deleteOutOfDateOrderData() throws ServiceException {
		boolean result = true;
		String filePath = ResourceUtil.getSystemConfig().getProperty("pdf_for_order_invoice_ready_ind");
		String days = ResourceUtil.getSystemConfig().getProperty("pdf.archive.delete.days");
		Map map = new HashMap();
		map.put("status", ConstantUtil.COMPLETED);
		map.put("outOfDate", Integer.valueOf(days));
		List<OrderModel> orderList = this.orderModelMapper.getOutOfDateOrderList(map);
		if (orderList != null && !orderList.isEmpty()) {
			for (OrderModel orderModel : orderList) {
				if (StringUtils.isNotEmpty(orderModel.getInvoiceFilename())) {
					try {
						if (new File(filePath + File.separator + orderModel.getInvoiceFilename()).exists()) {
							FileUtils
									.forceDelete(new File(filePath + File.separator + orderModel.getInvoiceFilename()));
						}
					} catch (IOException e) {
						LOG.error(e.getMessage(), e);
						throw new ServiceException(e.getMessage(), e);

					}
					orderModel.setInvoiceReadyInd(ConstantUtil.CONTANT_NO);
					orderModelMapper.updateByPrimaryKeySelective(orderModel);
				}

			}
		}
		return result;

	}

	@Override
	public OrderModel getOrderByidAndskuId(String hybrisOrderId, String skuId) {

		OrderParameterVo vo = new OrderParameterVo();
		if (StringUtils.isNotEmpty(hybrisOrderId) && StringUtils.isNotEmpty(skuId)) {
			vo.setSkuId(skuId);
			vo.setOrderId(hybrisOrderId);
			List<OrderModel> orders = orderModelMapper.getOrderByidAndskuId(vo);
			if (CollectionUtils.isNotEmpty(orders)) {
				return orders.get(0);
			}
		}

		return null;

	}

	private final static String FIAL_ORDER_NOT_EXISTS = "fail.ordernotexists";
	private final static String FIAL_CONSIGMENT_NOT_EXISTS = "fail.consignmentnotexists";
	private final static String FIAL_ALREADY_SHIPPED = "fail.consignmentalreadyshipped";
	private final static String FIAL_ALERADY_CANCELLED = "fail.consignmentalreadycancelled";
	private final static String FIAL_ERROR = "fail.error";

	@Override
	public OrderCancelReponseVo getAllCancleResponses(List<CancelRequest> cancelRequests) {
		OrderCancelReponseVo orderCancelReponseVo = new OrderCancelReponseVo();
		List<CancelResponse> cancelResponses = new ArrayList<>();
		orderCancelReponseVo.setCancelResponses(cancelResponses);

		if (CollectionUtils.isEmpty(cancelRequests)) {
			CancelResponse cancelResponse = new CancelResponse();
			cancelResponse.setErrorCode("fail.error");
			cancelResponse.setErrorMessage("Jason file is empty.");
			cancelResponses.add(cancelResponse);
			return orderCancelReponseVo;
		}

		for (CancelRequest cancelRequest : cancelRequests) {
			CancelResponse cancelResponse = new CancelResponse();
			OrderModel orderModel = null;
			String orderType = "";
			String orderStatus = "";

			if (cancelRequest == null)
				continue;

			cancelResponse.setOrderId(cancelRequest.getOrderId());
			cancelResponse.setPickOrderId(cancelRequest.getPickOrderId());
			cancelResponse.setOrderConsignmentStatus("");// (cancelRequest.getOrderConsignmentStatus());

			// 2 fail.ordernotexists Order not exists
			String orderId = cancelRequest.getOrderId();
			boolean orderNotExists = false;
			if (StringUtils.isNotEmpty(orderId)) {
				if (CollectionUtils.isEmpty(orderModelMapper.selectByHybrisOrderId(orderId)))
					orderNotExists = true;
			} else {
				orderNotExists = true;
			}

			if (orderNotExists) {
				cancelResponse.setErrorCode(FIAL_ORDER_NOT_EXISTS);
				cancelResponse.setErrorMessage("Order not exists");
				cancelResponses.add(cancelResponse);
				continue;
			}

			// 3 fail.consignmentnotexists Consignment not exists
			boolean pickIdNotExists = false;
			if (StringUtils.isEmpty(cancelRequest.getPickOrderId())) {
				pickIdNotExists = true;
			} else {
				orderModel = orderModelMapper.getOrderByPickOrderId(cancelRequest.getPickOrderId());
				if (orderModel == null)
					pickIdNotExists = true;
			}

			if (pickIdNotExists) {
				cancelResponse.setErrorCode(FIAL_CONSIGMENT_NOT_EXISTS);
				cancelResponse.setErrorMessage("Consignment not exists");
				cancelResponses.add(cancelResponse);
				continue;
			}
			orderType = orderModel.getOrderType();
			orderStatus = orderModel.getStatus();

			// 5 Fail.consignmentalreadycancelled Consignment already cancelled
			if ("CANCELLED".equals(orderStatus)) {
				cancelResponse.setErrorCode(FIAL_ALERADY_CANCELLED);
				cancelResponse.setErrorMessage("Consignment already cancelled");
				cancelResponses.add(cancelResponse);
				continue;
			}

			// fail.consignmentalreadyshipped Consignment already shipped

			if (StringUtils.isNotEmpty(orderType)
					&& ("CONSIGNMENT".equals(orderType) || "CONSIGNMENT_VIA_WAREHOUSE".equals(orderType) || ("SUPPLIER_DIRECT_DELIVERY"
							.equals(orderType) && ("NEW".equals(orderStatus) || "PICKED".equals(orderStatus))))) {

				OrderModel updateOrderModel = new OrderModel();
				updateOrderModel.setId(orderModel.getId());
				updateOrderModel.setStatus("CANCELLED");
				updateOrderModel.setLastUpdatedDate(new Date());
				updateOrderModel.setLastUpdatedBy("webserviceCaller");
				try {
					orderModelMapper.updateByPrimaryKeySelective(updateOrderModel);
					cancelResponse.setOrderConsignmentStatus(updateOrderModel.getStatus());
					cancelResponse.setErrorCode("success");
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					cancelResponse.setErrorCode(FIAL_ERROR);
					cancelResponse.setErrorMessage(e.getMessage());
				}
			} else {
				cancelResponse.setErrorCode(FIAL_ALREADY_SHIPPED);
				cancelResponse.setErrorMessage("Consignment already shipped");
				// cancelResponse.setOrderConsignmentStatus(cancelRequest.getOrderConsignmentStatus());
			}

			cancelResponses.add(cancelResponse);
		}

		return orderCancelReponseVo;
	}

	@Override
	public OrderModel getOrderByPickOrderId(String pickOrderId) {

		return orderModelMapper.getOrderByPickOrderId(pickOrderId);
	}

	@Override
	public Map<String, Object> selectByPrimaryKey(String orderId) throws ServiceException {
	//	BigDecimal id = null;
//		if (StringUtils.isNotEmpty(orderId))
//			id = new BigDecimal(orderId);

		List<OrderModel> orderModels = selectByHybrisOrderId(orderId);
		OrderVo orderVo = null;
		if (orderModels != null && orderModels.size() > 0) {
			OrderModel orderModel = orderModels.get(0);

			OrderPopulator populator = new OrderPopulator();
			orderVo = populator.converModelToVo(orderModel);
			
			List<OrderEntryVo> list = orderEntryService.selectByOrderId(orderVo.getId());
			orderVo.setEntryVoList(list);
			
		}
		List<OrderEntryVo> orderEntryVoList = null;
		if (orderVo != null)
			orderEntryVoList = orderVo.getEntryVoList();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderDetails", orderVo);
		map.put("orderEntryList", orderEntryVoList);

		System.out.println(orderVo);
		System.out.println(orderEntryVoList);
		return map;

	}
}
