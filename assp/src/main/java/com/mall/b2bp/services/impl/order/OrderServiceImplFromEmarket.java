package com.mall.b2bp.services.impl.order;

import static com.mall.b2bp.utils.CommonUtil.printJSON;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.web.client.RestTemplate;



//import com.mall.b2bp.vos.order.orderfromweb.CategoryWsDTO;
import com.mall.b2bp.daos.order.OrderModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderEntryModel;
import com.mall.b2bp.models.order.OrderExportModel;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.populators.order.OrderExportPopulator;
import com.mall.b2bp.populators.order.OrderPopulator;
import com.mall.b2bp.services.order.OrderEntryService;
import com.mall.b2bp.services.order.OrderService;
import com.mall.b2bp.services.parm.ParmService;
import com.mall.b2bp.services.product.ProductImagesService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.order.CancelRequest;
import com.mall.b2bp.vos.order.CancelResponse;
import com.mall.b2bp.vos.order.OrderCancelReponseVo;
import com.mall.b2bp.vos.order.OrderEntrySerialNoListVo;
import com.mall.b2bp.vos.order.OrderEntrySerialNoVo;
import com.mall.b2bp.vos.order.OrderEntryVo;
import com.mall.b2bp.vos.order.OrderExportVo;
import com.mall.b2bp.vos.order.OrderParameterVo;
import com.mall.b2bp.vos.order.OrderUpdateWebServiceVo;
import com.mall.b2bp.vos.order.OrderViewVo;
import com.mall.b2bp.vos.order.OrderVo;
import com.mall.b2bp.vos.order.PickQtyVo;
import com.mall.b2bp.vos.order.SupplierCollectionData;
import com.mall.b2bp.vos.order.SupplierCollectionDataResponse;
import com.mall.b2bp.vos.order.SupplierCollectionVoRequest;
import com.mall.b2bp.vos.order.SupplierVo;
import com.mall.b2bp.vos.order.orderfromweb.BrandWsDTO;
import com.mall.b2bp.vos.order.orderfromweb.ConsignmentEntryWsDTO;
import com.mall.b2bp.vos.order.orderfromweb.ErrorListWsDTO;
import com.mall.b2bp.vos.order.orderfromweb.ImageWsDTO;
import com.mall.b2bp.vos.order.orderfromweb.OrderEntrySerialNoWsDTO;
import com.mall.b2bp.vos.order.orderfromweb.OrderEntryWsDTO;
import com.mall.b2bp.vos.order.orderfromweb.OrderHistoryListWsDTO;
import com.mall.b2bp.vos.order.orderfromweb.OrderHistoryWsDTO;
import com.mall.b2bp.vos.order.orderfromweb.OrderWsDTO;
import com.mall.b2bp.vos.user.UserVo;

/**
 * Created by AIS on 2016/8/8.
 */
@Service("orderService")
class OrderServiceImplFromEmarket implements OrderService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImplFromEmarket.class);

	// private static final String SUPPLIER = "SUPPLIER";

	private OrderModelMapper orderModelMapper;

	@Resource(name = "orderEntryService")
	private OrderEntryService orderEntryService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "productImagesService")
	ProductImagesService productImagesService;

	@Resource(name = "supplierService")
	private SupplierService supplierService;
	
	
	@Resource(name="parmService")
	ParmService parmService;

	
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

		orderVo.setCustomerName(orderViewVo.getCustomerName());

		return orderVo;
	}

	public OrderVo filterOrder(OrderVo order) {

		UserVo userVo = sessionService.getCurrentUser();
		// String supplierId = new String();

		String currentSupplierName = "";
		boolean f = false;
		if (userVo != null) {
			
//			 currentSupplierName = userVo.getUserName();
			 currentSupplierName = getSupplierCode(userVo.getSupplierId());
			 if(StringUtils.isNotEmpty(currentSupplierName)){
				 currentSupplierName = currentSupplierName.toLowerCase();
			 }
			if ("SUPPLIER".equals(userVo.getUserRole())) {
				f = true;
			}

			order.setEntryStatus(order.getStatus());
			if (!f) {
				return order;
			}

			if (order != null && CollectionUtils.isNotEmpty(order.getEntryVoList())) {
				List<OrderEntryVo> list = order.getEntryVoList();
				List<OrderEntryVo> newList = new ArrayList<>();
				boolean flag = false;
				for (OrderEntryVo vo : list) {
					String brand = vo.getBrand();

					if (StringUtils.equalsIgnoreCase(brand, currentSupplierName)) {
						newList.add(vo);

						if (!flag) {
							if (StringUtils.isEmpty(vo.getEntryStatus()))
								order.setEntryStatus(ConstantUtil.NEW);
							else
								order.setEntryStatus(vo.getEntryStatus());
							flag = true;
						}
					}

				} // end for
				order.setEntryVoList(newList);
			}
		}

		return order;
	}

	public interface ListOrderVo extends List<OrderVo> {
	}

	@Override
	public List<SupplierCollectionData> getSupplierCollectionByOrderCode(final String orderCode)
			throws ServiceException {
//		StringBuffer uri = new StringBuffer(ResourceUtil.getSystemConfig().getProperty("url_emarket_webservice"));
		StringBuffer uri  = new StringBuffer();
		uri.append(parmService.getWebserviceUrl());
		uri.append("orders/getSupplierCollection");
		uri.append("?1=1");
		uri.append("&orderCode=").append(orderCode);

		try {
			RestTemplate restTemplate = new RestTemplate();
			SupplierCollectionDataResponse webResult = restTemplate.getForObject(uri.toString(),
					SupplierCollectionDataResponse.class);

			List<SupplierCollectionData> supplierDataList = null;
			if (webResult != null) {
				supplierDataList = webResult.getSupplierDataList();

				if (CollectionUtils.isNotEmpty(supplierDataList)) {
					for (SupplierCollectionData data : supplierDataList) {
						if (data.getPickedConfirmDate() == null) {
							data.setFlag("N");
							data.setDisableCheckbox(true);
						} else {
							if (data.getShippedConfirmDate() != null) {
								data.setFlag("Y");
							} else {
								data.setFlag("N");
							}
							data.setStatus("Picked");
							data.setDisableCheckbox(false);
						}

					}
				}
			}

			return supplierDataList;

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

	}
	
	private String getSupplierCode(String supplierCode){
	
		String supplierName= "";		 	
		 LOG.info("supplierCode:"+supplierCode);
		 if(StringUtils.isNotEmpty(supplierCode)){
		 com.mall.b2bp.vos.supplier.SupplierVo supplierVo = null;
		try {
			supplierVo = supplierService.selectByPrimaryKey(supplierCode);
			if(supplierVo!=null)
				supplierName = supplierVo.getName();
			if(StringUtils.isNotEmpty(supplierName))
				supplierName = supplierName.toLowerCase();
		} catch (ServiceException e) {
			LOG.error(e.getMessage(),e);
		}
		}
		 return supplierName;
	}

	@Override
	public List<OrderVo> viewOrderList(final OrderViewVo orderViewVo) throws ServiceException {

		// get传参code\orderType\customerName\orderCreateDateFm\orderCreateDateTo\orderDeliveryDateFm\orderDeliveryDateTo\statuses
		String orderCreateDateFm = DateUtils.parseDateStrStr(orderViewVo.getOrderDateFr(), DateUtils.DATE_FORMAT,
				DateUtils.DATE_FORMAT_5);
		String orderCreateDateTo = DateUtils.parseDateStrStr(orderViewVo.getOrderDateTo(), DateUtils.DATE_FORMAT,
				DateUtils.DATE_FORMAT_5);
		String orderDeliveryDateFm = DateUtils.parseDateStrStr(orderViewVo.getDeliveryDateFr(), DateUtils.DATE_FORMAT,
				DateUtils.DATE_FORMAT_5);
		String orderDeliveryDateTo = DateUtils.parseDateStrStr(orderViewVo.getDeliveryDateTo(), DateUtils.DATE_FORMAT,
				DateUtils.DATE_FORMAT_5);
		String statuses = StringUtils.join(orderViewVo.getOrderStatus(), ",");
		String collectMethod = StringUtils.join(orderViewVo.getCollectMethod(), ",");

		// http://emarketplace.aishk.com/
		// 设置uri
		//StringBuffer uri = new StringBuffer(ResourceUtil.getSystemConfig().getProperty("url_emarket_webservice"));
		StringBuffer uri  = new StringBuffer();
		uri.append(parmService.getWebserviceUrl());
		
		uri.append("orders/getOrderHistory");
		uri.append("?1=1");
		uri.append("&time=").append(System.currentTimeMillis());

		if (StringUtils.isNotEmpty(orderViewVo.getOrderId()))
			uri.append("&code=").append(orderViewVo.getOrderId());
		// if(StringUtils.isNotEmpty(orderViewVo.getOrderType()))
		// uri.append("&orderType=").append(orderViewVo.getOrderType());
		if (StringUtils.isNotEmpty(orderViewVo.getCustomerName()))
			uri.append("&customerName=").append(orderViewVo.getCustomerName());
		if (StringUtils.isNotEmpty(orderCreateDateFm))
			uri.append("&orderCreateDateFm=").append(orderCreateDateFm);
		if (StringUtils.isNotEmpty(orderCreateDateTo))
			uri.append("&orderCreateDateTo=").append(orderCreateDateTo);
		if (StringUtils.isNotEmpty(orderDeliveryDateFm))
			uri.append("&orderDeliveryDateFm=").append(orderDeliveryDateFm);
		if (StringUtils.isNotEmpty(orderDeliveryDateTo))
			uri.append("&orderDeliveryDateTo=").append(orderDeliveryDateTo);
		// if(StringUtils)
		if (StringUtils.isNotEmpty(collectMethod))
			uri.append("&collectMethod=").append(collectMethod);
		if (StringUtils.isNotEmpty(statuses))
			uri.append("&statuses=").append(statuses);
		boolean f = false;
		UserVo userVo = sessionService.getCurrentUser();
		if (userVo != null) {
			// supplierId = userVo.getSupplierId();
			if ("SUPPLIER".equals(userVo.getUserRole())) {
				f = true;
				 String currentSupplierName = getSupplierCode(userVo.getSupplierId());
				 if(StringUtils.isNotEmpty(currentSupplierName)){
					 currentSupplierName = currentSupplierName.toLowerCase();
					 uri.append("&supplier=").append(currentSupplierName);
				 }
			}
		}
		// uri.append("&supplier=").append("1000000000");

		// System.out.println(StringUtils.join(orderViewVo.getOrderStatus(),","));
		LOG.info("[viewOrderList] start call webaservice:" + uri);
		try {
			// printJSON(orderViewVo);

			RestTemplate restTemplate = new RestTemplate();
			OrderHistoryListWsDTO result = restTemplate.getForObject(uri.toString(), OrderHistoryListWsDTO.class);

			LOG.info("[viewOrderList] restTemplate.getForObject result:" + result);

			if (result == null)
				return null;

			printJSON(result);

			List<OrderHistoryWsDTO> orderWsList = result.getOrders();
			List<OrderModel> orderModelList = new ArrayList<OrderModel>();

			if (orderWsList != null) {
				for (OrderHistoryWsDTO orderWs : orderWsList) {
					OrderModel orderModel = new OrderModel();

					// orderModel.setSupplierName(orderWs.getUser().getName());
					orderModel.setHybrisOrderId(orderWs.getCode());
					if (orderWs.getUser() != null)
						orderModel.setCustomerName(orderWs.getUser().getName());
					// orderModel.setOrderType(orderType);

					if (StringUtils.isEmpty(orderWs.getDeliveryStatus())) {
						orderModel.setStatus(ConstantUtil.NEW);
					} else {
						orderModel.setStatus(orderWs.getDeliveryStatus());
					}

					orderModel.setOrderDatetime(orderWs.getPlaced());
					orderModel.setCollectMethod(orderWs.getDeliveryFlag());
					orderModel.setCollectDate(orderWs.getCollectDate());

					if (orderWs.getConsignments() != null && orderWs.getConsignments().size() > 0) {
						orderModel.setPickDate(orderWs.getConsignments().get(0).getPickedDate());
					}

					if (orderWs.getConsignments() != null && orderWs.getConsignments().size() > 0  && StringUtils.isNotEmpty(orderWs.getDeliveryStatus())) {
						orderModel.setConsignmentShippedDate(orderWs.getConsignments().get(0).getShippingDate());
						orderModel.setDeliveryDate(orderWs.getConsignments().get(0).getNamedDeliveryDate());
					}
					// orderModel.setOutstandingReturnRequest(orderWs.);

					orderModel.setSupplierCollections(orderWs.getSupplierCollections());

					orderModel.setOrderStatus(orderWs.getOrderStatus());

					orderModelList.add(orderModel);
				}
			}

			
			// status=================
			List<OrderVo> l = populatorVo(orderModelList);

			List<OrderVo> orderVos = new ArrayList<>();
			if (!f) {

				// Admin returnTotal

				if (CollectionUtils.isNotEmpty(l)) {
					for (OrderVo vo : l) {
						OrderVo o = selectByPrimaryKey(vo.getHybrisOrderId());
						vo.setTrackId(o.getTrackId());
						BigDecimal reNumForSu = new BigDecimal(0);
						for (OrderEntryVo oEntry : o.getEntryVoList()) {
							if (oEntry.getReturnTotal() != null) {
								reNumForSu = reNumForSu.add(oEntry.getReturnTotal());
							}
						}
						if (reNumForSu.compareTo(new BigDecimal(0)) == 1) {
							vo.setTotalRetrnRequest(reNumForSu);
						}
					}
				}

				return l;
			} else {
				// Supplier returnTotal
				if (CollectionUtils.isNotEmpty(l)) {
					for (OrderVo vo : l) {
						OrderVo o = selectByPrimaryKey(vo.getHybrisOrderId());
						vo.setTrackId(o.getTrackId());
						if (StringUtils.isNotEmpty(vo.getOrderStatus()))
							o.setOrderStatus(vo.getOrderStatus());
						o.setCollectMethod(vo.getCollectMethod());
						o.setCollectDateStr(vo.getCollectDateStr());

						BigDecimal reNumForSu = new BigDecimal(0);
						for (OrderEntryVo oEntry : o.getEntryVoList()) {
							if (oEntry.getReturnTotal() != null) {
								reNumForSu = reNumForSu.add(oEntry.getReturnTotal());
							}
						}
						if (reNumForSu.compareTo(new BigDecimal(0)) == 1) {
							o.setTotalRetrnRequest(reNumForSu);
						}

						if (CollectionUtils.isNotEmpty(o.getEntryVoList()))
							orderVos.add(o);
					}
				}

				return orderVos;
			}
			// ===================================================

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);

		}

	}

	@Override
	public List<OrderEntryVo> selectOrderEngtryList(String orderId) throws ServiceException {

//		StringBuffer uri = new StringBuffer(ResourceUtil.getSystemConfig().getProperty("url_emarket_webservice"));
		StringBuffer uri  = new StringBuffer();
		uri.append(parmService.getWebserviceUrl());
		
		uri.append("orders/getOrderDetail");
		uri.append("?code=" + orderId);

		LOG.info("start call webaservice:" + uri);
		List<OrderEntryVo> orderEntryVoList = new ArrayList<OrderEntryVo>();
		try {
			RestTemplate restTemplate = new RestTemplate();
			OrderWsDTO webResult = restTemplate.getForObject(uri.toString(), OrderWsDTO.class);

			List<OrderEntryWsDTO> webOrderEntryList = webResult.getEntries();
			for (OrderEntryWsDTO webOrderEntry : webOrderEntryList) {

				OrderEntryVo orderEntryVo = new OrderEntryVo();

				orderEntryVo.setId(BigDecimal.valueOf(webOrderEntry.getEntryNumber()));

				if (webOrderEntry.getBasePrice() != null)
					orderEntryVo.setUnitPrice(webOrderEntry.getTotalPrice().getValue());

				if (webOrderEntry.getProduct() != null) {
					/**
					 * 需要修改，产品品牌从 product.brand中获取，brand有code/name属性。
					 * 不需要从Categories取，直接取product.brand
					 */

					BrandWsDTO brand = webOrderEntry.getProduct().getBrand();
					if (brand != null) {
						orderEntryVo.setBrand(StringUtils.isNotEmpty(brand.getName()) ? brand.getName() : brand
								.getCode());
					}
					// for (CategoryWsDTO categoryWsDTO :
					// webOrderEntry.getProduct().getCategories()) {
					//
					// if ("bossini".equalsIgnoreCase(categoryWsDTO.getCode())
					// || "bossini".equalsIgnoreCase(categoryWsDTO.getName())
					// || "fortress".equalsIgnoreCase(categoryWsDTO.getCode())
					// || "fortress".equalsIgnoreCase(categoryWsDTO.getName()))
					// {
					// orderEntryVo.setBrand(categoryWsDTO.getName());
					// break;
					// }
					// orderEntryVo.setBrand(categoryWsDTO.getName());
					// }
				}

				orderEntryVoList.add(orderEntryVo);

			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

		return orderEntryVoList;

	}

	@Override
	public OrderVo selectByPrimaryKey(String orderId) throws ServiceException {

		
//		StringBuffer uri_image = new StringBuffer(ResourceUtil.getSystemConfig().getProperty("url_emarket_webService_image"));
		StringBuffer uri_image = new StringBuffer();
		uri_image.append(parmService.getWebSite());
		
//		StringBuffer uri = new StringBuffer(ResourceUtil.getSystemConfig().getProperty("url_emarket_webservice"));
		StringBuffer uri  = new StringBuffer();
		uri.append(parmService.getWebserviceUrl());
		uri.append("orders/getOrderDetail");
		uri.append("?code=" + orderId);
		System.out.println( uri);
		try {
			RestTemplate restTemplate = new RestTemplate();
			OrderWsDTO webResult = restTemplate.getForObject(uri.toString(), OrderWsDTO.class);

			// orderModel
			OrderModel orderModel = new OrderModel();

			if (StringUtils.isNotEmpty(webResult.getCode())) {
				orderModel.setHybrisOrderId(webResult.getCode());
				//
				try {
					if (StringUtils.isNotEmpty(webResult.getCode()))
						orderModel.setOrigOrderId(new BigDecimal(webResult.getCode()));
				} catch (Exception ex) {
					LOG.error(ex.getMessage());
				}
			}
			orderModel.setOrderDatetime(webResult.getCreated());
			if (webResult.getUser() != null)
				orderModel.setCustomerName(webResult.getUser().getName());
			orderModel.setDeliveryFlag(webResult.getDeliveryFlag());
			if (webResult.getDeliveryAddress() != null) {

				orderModel
						.setReceiverName((StringUtils.isNotEmpty(webResult.getDeliveryAddress().getFirstName()) ? webResult
								.getDeliveryAddress().getFirstName() : "")
								+ " "
								+ (StringUtils.isNotEmpty(webResult.getDeliveryAddress().getLastName()) ? webResult
										.getDeliveryAddress().getLastName() : ""));
				orderModel.setReceiverPhoneNo(webResult.getDeliveryAddress().getPhone());
				orderModel.setDeliveryAddress(

				(StringUtils.isNotEmpty(webResult.getDeliveryAddress().getLine1()) ? (webResult.getDeliveryAddress()
						.getLine1() + " , ") : "")
						+ (StringUtils.isNotEmpty(webResult.getDeliveryAddress().getLine2()) ? (webResult
								.getDeliveryAddress().getLine2() + " , ") : "")
						+ (StringUtils.isNotEmpty(webResult.getDeliveryAddress().getPostalCode()) ? (webResult
								.getDeliveryAddress().getPostalCode() + " , ") : "")
						+ (StringUtils.isNotEmpty(webResult.getDeliveryAddress().getTown()) ? (webResult
								.getDeliveryAddress().getTown() + " , ") : "")

						+ (webResult.getDeliveryAddress().getCountry() != null ? webResult.getDeliveryAddress()
								.getCountry().getName() : ""));
			}
			if (StringUtils.isEmpty(webResult.getDeliveryStatus())) {
				orderModel.setStatus(ConstantUtil.NEW);
			} else {
				orderModel.setStatus(webResult.getDeliveryStatus());
			}

			if (webResult.getDeliveryCost() != null)
				orderModel.setDeliveryFee(webResult.getDeliveryCost().getValue());

			if (webResult.getConsignments() != null && webResult.getConsignments().size() > 0) {
				if(StringUtils.isNotEmpty(webResult.getDeliveryStatus()))
					orderModel.setConsignmentShippedDate(webResult.getConsignments().get(0).getShippingDate());
				orderModel.setDeliveryDate(webResult.getConsignments().get(0).getNamedDeliveryDate());
				orderModel.setPickDate(webResult.getConsignments().get(0).getPickedDate());
				// orderModel.setTrackId(webResult.getConsignments().get(0).getTrackingID());
			}
			orderModel.setTrackId(webResult.getTrackId());
			orderModel.setOrderStatus(webResult.getStatus());

			// orderModel.setBoxNum(boxNum);
			OrderPopulator populator = new OrderPopulator();
			OrderVo orderVo = populator.converModelToVo(orderModel);

			// web - orderEntry
			List<OrderEntryWsDTO> webOrderEntryList = webResult.getEntries();

			List<OrderEntryVo> orderEntryVoList = new ArrayList<OrderEntryVo>();

			if (webOrderEntryList != null) {
				UserVo userVo = sessionService.getCurrentUser();
				String currentSupplierName = new String();
				boolean f = false;
				if (userVo != null) {
					  currentSupplierName = getSupplierCode(userVo.getSupplierId());
					 if(StringUtils.isNotEmpty(currentSupplierName)){
						 currentSupplierName = currentSupplierName.toLowerCase();
					 }
					if ("SUPPLIER".equals(userVo.getUserRole())) {
						f = true;
					}
				}
				

				for (OrderEntryWsDTO webOrderEntry : webOrderEntryList) {

					OrderEntryVo orderEntryVo = new OrderEntryVo();

					orderEntryVo.setEntryStatus(webOrderEntry.getStatus());
					orderEntryVo.setId(BigDecimal.valueOf(webOrderEntry.getEntryNumber()));

					List<OrderEntrySerialNoWsDTO> orderEntrySerialNoWsDTOList = webOrderEntry.getSerialNos();
					List<OrderEntrySerialNoVo> orderEntrySerialNoVoList = new ArrayList<OrderEntrySerialNoVo>();
					if (orderEntrySerialNoWsDTOList != null && orderEntrySerialNoWsDTOList.size() > 0) {
						OrderEntrySerialNoVo orderEntrySerialNoVo = null;
						for (OrderEntrySerialNoWsDTO orderEntrySerialNoWsDTO : orderEntrySerialNoWsDTOList) {
							orderEntrySerialNoVo = new OrderEntrySerialNoVo();
							orderEntrySerialNoVo.setSerialNo(orderEntrySerialNoWsDTO.getSerialNo());
							orderEntrySerialNoVo.setReturnInd(orderEntrySerialNoWsDTO.getReturnInd());
							orderEntrySerialNoVoList.add(orderEntrySerialNoVo);
						}
					}
					orderEntryVo.setOrderEntrySerialList(orderEntrySerialNoVoList);

					orderEntryVo.setEntryStatus(webOrderEntry.getStatus());

					orderEntryVo.setPickDate(webOrderEntry.getPickedDate());
					orderEntryVo.setShipDate(webOrderEntry.getShippedDate());
					orderEntryVo.setDeliveryDate(webOrderEntry.getDeliveredDate());

					// orderReturnVo.setId(id);
					orderEntryVo.setQty(BigDecimal.valueOf(webOrderEntry.getQuantity()));

					if (webOrderEntry.getBasePrice() != null)
						orderEntryVo.setUnitPrice(webOrderEntry.getTotalPrice().getValue());

					if (webOrderEntry.getProduct() != null) {
						/**
						 * 需要修改，产品品牌从 product.brand中获取，brand有code/name属性。
						 * 不需要从Categories取，直接取product.brand
						 */
						BrandWsDTO brand = webOrderEntry.getProduct().getBrand();
						if (brand != null) {
							orderEntryVo.setBrand(StringUtils.isNotEmpty(brand.getName()) ? brand.getName() : brand
									.getCode());
						}
						// for (CategoryWsDTO categoryWsDTO :
						// webOrderEntry.getProduct().getCategories()) {
						//
						// if
						// ("bossini".equalsIgnoreCase(categoryWsDTO.getCode())
						// ||
						// "bossini".equalsIgnoreCase(categoryWsDTO.getName())
						// ||
						// "fortress".equalsIgnoreCase(categoryWsDTO.getCode())
						// ||
						// "fortress".equalsIgnoreCase(categoryWsDTO.getName()))
						// {
						// orderEntryVo.setBrand(categoryWsDTO.getName());
						// break;
						// }
						// orderEntryVo.setBrand(categoryWsDTO.getName());
						// }

						if (f) {

							// System.out.println(orderEntryVo.getBrand()
							// +" ->"+ currentSupplierName);
							if (!StringUtils.equalsIgnoreCase(orderEntryVo.getBrand(), currentSupplierName)) {
								continue;
							}
						}

						String skuId = String.valueOf(webOrderEntry.getProduct().getCode());

						orderEntryVo.setSkuId(skuId);
						orderEntryVo.setProductName(webOrderEntry.getProduct().getName());

						// getImages from Hybris
						Collection<ImageWsDTO> imagesList = webOrderEntry.getProduct().getImages();
						if (imagesList != null) {
							for (ImageWsDTO imageWsDTO : imagesList) {
								if (imageWsDTO.getUrl() != null) {
									orderEntryVo.setProductImageDefault(uri_image + imageWsDTO.getUrl());
									break;
								}
							}
						}

						if (webResult.getConsignments() != null && webResult.getConsignments().size() > 0) {
							for (ConsignmentEntryWsDTO consignmentEntryWsDTO : webResult.getConsignments().get(0)
									.getEntries()) {
								if (consignmentEntryWsDTO.getOrderEntry() != null
										&& consignmentEntryWsDTO.getOrderEntry().getEntryNumber().intValue() == webOrderEntry
												.getEntryNumber().intValue()) {

									if (consignmentEntryWsDTO.getQuantity() != null)
										orderEntryVo.setPickedQty(BigDecimal.valueOf(consignmentEntryWsDTO
												.getQuantity()));
									if (consignmentEntryWsDTO.getShippedQuantity() != null)
										orderEntryVo.setDeliveryQty(BigDecimal.valueOf(consignmentEntryWsDTO
												.getShippedQuantity()));
									if (consignmentEntryWsDTO.getOrderEntry().getReturnQty() != null)
										orderEntryVo.setReturnTotal(BigDecimal.valueOf(consignmentEntryWsDTO
												.getOrderEntry().getReturnQty()));

									break;
								}
							}
						}

					}
					orderEntryVoList.add(orderEntryVo);
				}
			}

			orderVo.setEntryVoList(orderEntryVoList);

			return filterOrder(orderVo);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public boolean updateOrderEntrySerialList(OrderEntrySerialNoListVo orderEntrySerialsData, ResponseData responseData)
			throws ServiceException {

		boolean success = true;

		LOG.info("========start SerialNo=======");
		if (CollectionUtils.isNotEmpty(orderEntrySerialsData.getEntrySerialNos())) {
			for (OrderEntrySerialNoVo s : orderEntrySerialsData.getEntrySerialNos()) {
				LOG.info(s.getSerialNo());
			}
		}
		LOG.info("========end SerialNo=======");
		// 设置uri

//		StringBuffer uri = new StringBuffer(ResourceUtil.getSystemConfig().getProperty("url_emarket_webservice"));
		StringBuffer uri  = new StringBuffer();
		uri.append(parmService.getWebserviceUrl());
		
		uri.append("orders/updateOrderEntrySerialNo?1=1");

		uri.append("&code=" + orderEntrySerialsData.getOrderId());
		uri.append("&entryNumber=" + orderEntrySerialsData.getEntryId());

		if (orderEntrySerialsData.getEntrySerialNos() != null) {

			String[] serialNoArray = new String[orderEntrySerialsData.getEntrySerialNos().size()];

			for (int i = 0; i < serialNoArray.length; i++) {

				serialNoArray[i] = orderEntrySerialsData.getEntrySerialNos().get(i).getSerialNo() + "-"
						+ orderEntrySerialsData.getEntrySerialNos().get(i).getReturnInd();
			}
			uri.append("&serialNos=" + StringUtils.join(serialNoArray, ","));
		}

		LOG.info("start call webaservice:" + uri);
		try {

			// webservice update
			RestTemplate restTemplate = new RestTemplate();
			ErrorListWsDTO result = restTemplate.postForObject(uri.toString(), null, ErrorListWsDTO.class);

			printJSON(result);

			if (result.getErrors() != null
					&& !StringUtils.equalsIgnoreCase(result.getErrors().get(0).getType(), "Success")) {
				success = false;
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

		return success;

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

	@Override
	public boolean updateSupplierCollection(List<SupplierCollectionData> orderShipDetails) throws ServiceException {
		boolean success = false;

		SupplierCollectionVoRequest request = new SupplierCollectionVoRequest();
		List<SupplierVo> supplierVoList = new ArrayList<SupplierVo>();

		boolean getIdFlag = false;
		for (SupplierCollectionData supplierCollectionData : orderShipDetails) {
			if (!getIdFlag) {
				if (StringUtils.isNotEmpty(supplierCollectionData.getOrder())) {
					request.setOrderCode(supplierCollectionData.getOrder());
					getIdFlag = true;
				}
			}
			SupplierVo supplierVo = new SupplierVo();
			supplierVo.setSupplier(supplierCollectionData.getSupplier());
			supplierVo.setPick(supplierCollectionData.getFlag());
			supplierVoList.add(supplierVo);
		}

		request.setSupplierVoList(supplierVoList);

//		StringBuffer uri = new StringBuffer(ResourceUtil.getSystemConfig().getProperty("url_emarket_webservice"));
		StringBuffer uri  = new StringBuffer();
		uri.append(parmService.getWebserviceUrl());
		
		uri.append("orders/updateSupplierCollection");

		LOG.info("start call webaservice:" + uri);
		// webservice update
		RestTemplate restTemplate = new RestTemplate();

		ErrorListWsDTO result = restTemplate.postForObject(uri.toString(), request, ErrorListWsDTO.class);

		printJSON(result);

		if (result.getErrors() != null && !StringUtils.equalsIgnoreCase(result.getErrors().get(0).getType(), "Success")) {
			success = false;
		} else {
			success = true;
		}

		return success;

	}

	@Override
	public boolean updateBatchForSupplier(String waitForUpdateStatus, List<OrderVo> orderVoList, String trackId,
			final ResponseData responseData) throws ServiceException {
		boolean success = false;
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		OrderUpdateWebServiceVo orderUpdateWebServiceVo = new OrderUpdateWebServiceVo();
		try {
			UserVo userVo = sessionService.getCurrentUser();
			for (OrderVo orderVo : orderVoList) {

				// 设置uri
//				StringBuffer uri = new StringBuffer(ResourceUtil.getSystemConfig()
//						.getProperty("url_emarket_webservice"));
				StringBuffer uri  = new StringBuffer();
				uri.append(parmService.getWebserviceUrl());
				
				uri.append("orders/updateDeliveryStatus");

				LOG.info("start call webaservice:" + uri);

				orderUpdateWebServiceVo.setCode(orderVo.getHybrisOrderId());
				orderUpdateWebServiceVo.setTrackId(orderVo.getTrackId());

				List<PickQtyVo> pickQtyVoList = new ArrayList<PickQtyVo>();

				if (ConstantUtil.PICKED.equals(waitForUpdateStatus)) {
					orderUpdateWebServiceVo.setStatus(ConstantUtil.PICKED);

					if (orderVo.getEntryVoList() != null && !orderVo.getEntryVoList().isEmpty()) {

						for (OrderEntryVo orderEntryVo : orderVo.getEntryVoList()) {
							if (StringUtils.equalsIgnoreCase(userVo.getUserName(), orderEntryVo.getBrand())) {
								PickQtyVo pickQtyVo = new PickQtyVo();
								pickQtyVo.setEntryNumber(orderEntryVo.getId());
								pickQtyVo.setPickedQty(orderEntryVo.getPickedQty());
								pickQtyVoList.add(pickQtyVo);
							}
						}
					}

					orderUpdateWebServiceVo.setPickQtyList(pickQtyVoList);

				}

				else if (ConstantUtil.SHIPPED.equals(waitForUpdateStatus)

				) {
					// orderVo.setConsignmentShippedDate(new Date());
					// uri.append("&status="+ConstantUtil.SHIPPED);

					if (orderVo.getEntryVoList() != null && !orderVo.getEntryVoList().isEmpty()) {

						for (OrderEntryVo orderEntryVo : orderVo.getEntryVoList()) {

							if (StringUtils.equalsIgnoreCase(userVo.getUserName(), orderEntryVo.getBrand()) // ||
							// !SUPPLIER.equals(userVo.getUserRole())
							) {
								PickQtyVo pickQtyVo = new PickQtyVo();
								pickQtyVo.setEntryNumber(orderEntryVo.getId());
								pickQtyVo.setPickedQty(orderEntryVo.getPickedQty());
								pickQtyVoList.add(pickQtyVo);
							}
						}
					}

					orderUpdateWebServiceVo.setPickQtyList(pickQtyVoList);
					orderUpdateWebServiceVo.setStatus(ConstantUtil.SHIPPED);

				}

				else if (ConstantUtil.DELIVERY.equals(waitForUpdateStatus)) {

					if (orderVo.getEntryVoList() != null && !orderVo.getEntryVoList().isEmpty()) {
						for (OrderEntryVo orderEntryVo : orderVo.getEntryVoList()) {
							if (StringUtils.equalsIgnoreCase(userVo.getUserName(), orderEntryVo.getBrand())) {
								PickQtyVo pickQtyVo = new PickQtyVo();
								pickQtyVo.setEntryNumber(orderEntryVo.getId());
								pickQtyVo.setPickedQty(orderEntryVo.getPickedQty());
								pickQtyVoList.add(pickQtyVo);
							}
						}
					}
					if (StringUtils.isNotEmpty(trackId))
						orderUpdateWebServiceVo.setTrackId(trackId);

					orderUpdateWebServiceVo.setPickQtyList(pickQtyVoList);
					orderUpdateWebServiceVo.setStatus(ConstantUtil.DELIVERY);
				}

				else if (ConstantUtil.RETURN_CONFIRMED.equals(waitForUpdateStatus)) {
					// handleDelivery(orderVo);
					// uri.append("&status="+ConstantUtil.DELIVERY);

					if (orderVo.getEntryVoList() != null && !orderVo.getEntryVoList().isEmpty()) {
						for (OrderEntryVo orderEntryVo : orderVo.getEntryVoList()) {

							// if (StringUtils.equals(userVo.getUserName(),
							// orderEntryVo.getBrand())) {
							PickQtyVo pickQtyVo = new PickQtyVo();
							pickQtyVo.setEntryNumber(orderEntryVo.getId());
							pickQtyVo.setPickedQty(orderEntryVo.getReturnTotal());
							pickQtyVoList.add(pickQtyVo);
							// }
						}
					}
					orderUpdateWebServiceVo.setPickQtyList(pickQtyVoList);
					orderUpdateWebServiceVo.setStatus(ConstantUtil.RETURN_CONFIRMED);
				}

				// webservice update
				RestTemplate restTemplate = new RestTemplate();
				// ErrorListWsDTO result =
				// restTemplate.postForObject(uri.toString(), null,
				// ErrorListWsDTO.class);
				ErrorListWsDTO result = restTemplate.postForObject(uri.toString(), orderUpdateWebServiceVo,
						ErrorListWsDTO.class);

				printJSON(result);

				if (result.getErrors() != null
						&& !StringUtils.equalsIgnoreCase(result.getErrors().get(0).getType(), "Success")) {
					break;
				} else {
					success = true;
				}
			}

		} catch (Exception e) {
			LOG.error("UpdateBatchForSupplier error:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return success;
	}

	private String validateStatus(OrderVo orderVo, String waitForUpdateStatus) {
		String cStatus = orderVo.getStatus();

		if (cStatus == null) {
			cStatus = ConstantUtil.NEW;
		}

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

		return null;
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

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public ParmService getParmService() {
		return parmService;
	}

	public void setParmService(ParmService parmService) {
		this.parmService = parmService;
	}

}
