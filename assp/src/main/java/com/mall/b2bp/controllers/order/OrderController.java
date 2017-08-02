package com.mall.b2bp.controllers.order;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.order.OrderExportReportHandler;
import com.mall.b2bp.services.order.OrderReturnExportReportHandler;
import com.mall.b2bp.services.order.OrderReturnService;
import com.mall.b2bp.services.order.OrderService;
import com.mall.b2bp.services.order.OrderTLogService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.order.CancelRequest;
import com.mall.b2bp.vos.order.CancelResponse;
import com.mall.b2bp.vos.order.OrderCancelReponseVo;
import com.mall.b2bp.vos.order.OrderCancelRequestVo;
import com.mall.b2bp.vos.order.OrderEntrySerialNoListVo;
import com.mall.b2bp.vos.order.OrderEntryVo;
import com.mall.b2bp.vos.order.OrderExportVo;
import com.mall.b2bp.vos.order.OrderReturnExportVo;
import com.mall.b2bp.vos.order.OrderReturnVo;
import com.mall.b2bp.vos.order.OrderTLogVo;
import com.mall.b2bp.vos.order.OrderUpdateParameterVo;
import com.mall.b2bp.vos.order.OrderViewVo;
import com.mall.b2bp.vos.order.OrderVo;
import com.mall.b2bp.vos.order.SupplierCollectionData;
import com.mall.b2bp.vos.supplier.SupplierVo;

@Controller("OrderController")
@RequestMapping(value = "/order")
public class OrderController extends BaseConroller {
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@Resource(name = "supplierService")
	private SupplierService supplierService;

	@Resource(name = "orderService")
	private OrderService orderService;

	@Resource(name = "orderReturnService")
	private OrderReturnService orderReturnService;

	@Resource(name = "orderTLogService")
	private OrderTLogService orderTLogService;

	@Resource(name = "orderExportReportHandler")
	private OrderExportReportHandler orderExportReportHandler;

	@Resource(name = "orderReturnExportReportHandler")
	private OrderReturnExportReportHandler orderReturnExportReportHandler;
	
	// Get from DB
	//=========================================================================
	@Resource(name = "orderServiceFromDB")
	private com.mall.b2bp.services.orderFromDB.OrderService orderServiceFromDB;

	@Resource(name = "orderReturnServiceFromDB")
	private com.mall.b2bp.services.orderFromDB.OrderReturnService orderReturnServiceFromDB;

	@Resource(name = "orderTLogServiceFromDB")
	private com.mall.b2bp.services.orderFromDB.OrderTLogService orderTLogServiceFromDB;
	//==========================================================================

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/listSupplier", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public List<SupplierVo> listSupplier() throws SystemException {
		try {
			return supplierService.searchAll();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/updateForSupplier", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public ResponseData updateForSupplier(@RequestBody final OrderVo orderVo) throws SystemException {

		ResponseData<OrderVo> responseData = (ResponseData<OrderVo>) responseDataService.getReturnData(OrderVo.class);
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		responseData.setResourceBundleMessageSource(messageSource);
		List<OrderVo> orderVoList = new ArrayList<OrderVo>();
		orderVoList.add(orderVo);
		try {
			boolean success = orderService.updateBatchForSupplier(orderVo.getWaitForUpdateStatus(), orderVoList,null,
					responseData);
			if (success) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				return responseData;
			} else {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}

	}
	
	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/updateOrderEntrySerialList", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public ResponseData updateOrderEntrySerialList(@RequestBody final  OrderEntrySerialNoListVo orderEntrySerialsData) throws SystemException {
		
		ResponseData<OrderVo> responseData = (ResponseData<OrderVo>) responseDataService.getReturnData(OrderVo.class);
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		responseData.setResourceBundleMessageSource(messageSource);
		
		//System.out.println(orderEntrySerialsData);
		try {
			boolean success = orderService.updateOrderEntrySerialList(orderEntrySerialsData, responseData);
			if (success) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			} else {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		
				return responseData;
	}

	@Secured({  "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/batchUpdateForSupplier", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public ResponseData batchUpdateForSupplier(@RequestBody OrderUpdateParameterVo data) throws SystemException {

		ResponseData<OrderVo> responseData = (ResponseData<OrderVo>) responseDataService.getReturnData(OrderVo.class);
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		responseData.setResourceBundleMessageSource(messageSource);
		try {

			if (data == null || data.getOrderVoList() == null) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("system_error");

				return responseData;
			}

			boolean success = orderService.updateBatchForSupplier(data.getWaitForUpdateStatus(), data.getOrderVoList(),data.getTrackId(),responseData);
			if (success) {

				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				return responseData;
			} else {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}

	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/orderDetails", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public OrderVo orderDetails(@RequestParam(value = "orderId", required = false) final String orderId)
			throws SystemException {
		try {
//			String fromDB =ResourceUtil.getSystemConfig().getProperty("fromDB");
			OrderVo orderVo  = new OrderVo();
//			
//			
//			if (StringUtils.isNotEmpty(orderId)){
//				if("Y".equals(fromDB)){
//					orderVo  = orderServiceFromDB.selectByPrimaryKey(orderId);
//				}
//				else
					orderVo  = orderService.selectByPrimaryKey(orderId);
			//}
			
			
			return orderVo;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}
	
	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/permissionExecut", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public boolean permissionExecut(@RequestParam(value = "orderId", required = false) final String orderId)throws SystemException {
		
		boolean f = false;
		try{
			List<SupplierCollectionData>  orderShipDetails = null;
		
		if (StringUtils.isNotEmpty(orderId)){
			orderShipDetails = orderService.getSupplierCollectionByOrderCode(orderId);
		}
		
		
		
		OrderVo orderVo  = orderService.selectByPrimaryKey(orderId);
		List<OrderEntryVo>  orderEntryVos  = orderService.selectOrderEngtryList(orderId);
		if(orderVo  !=null && CollectionUtils.isNotEmpty(orderVo  .getEntryVoList())){
			String deliveryFlag = orderVo.getDeliveryFlag();
			//List<OrderEntryVo> orderEntryVos = orderVo  .getEntryVoList();
			
			Set<String> brands=	 getBrandBySupplier(orderEntryVos);
			
			  if( ("S".equals(deliveryFlag) || ("C".equals(deliveryFlag) && brands!=null && brands.size()==1))  
					  && "PICKED".equalsIgnoreCase(orderVo.getStatus()) ){
				  boolean allPicked = false;
				  if(CollectionUtils.isNotEmpty(orderShipDetails)){
					  allPicked = true;
					  for(SupplierCollectionData data: orderShipDetails){
						  if(data.getPickedConfirmDate() == null){
							  allPicked = false;
							  break;
						  }
					  }
					  if(allPicked)
						  f = true;
					  else
						  f = false;
				  }
				  
			  }
		}
		
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return f;
	}
	
	 private Set<String> getBrandBySupplier(List<OrderEntryVo> orderEntryVos){
	        Set<String> brands = new HashSet<String>();
	        
	        if(CollectionUtils.isEmpty(  orderEntryVos))
	        	return brands;
	        
	        for (OrderEntryVo model : orderEntryVos) {
	            
	            String brand = model.getBrand();
	            brands.add(brand);
	        }
	        return brands;
	    }
	 
	
	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/orderShipDetails", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public List<SupplierCollectionData> orderShipDetails(@RequestParam(value = "orderId", required = false) final String orderId)
			throws SystemException {
		try {
			List<SupplierCollectionData>  orderShipDetails = null;
			
			if (StringUtils.isNotEmpty(orderId)){
				orderShipDetails = orderService.getSupplierCollectionByOrderCode(orderId);
			}
			return orderShipDetails;
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}
	
	
	
	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/orderUpdateShipDetails", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public boolean orderUpdateShipDetails(@RequestBody final  List<SupplierCollectionData>  orderShipDetails)
			throws SystemException {
		try {
			boolean upFlag = orderService.updateSupplierCollection(orderShipDetails);
			
			boolean enableShipFlag = true;
			
			if(upFlag){
				if(orderShipDetails != null){
					for(SupplierCollectionData supplierCollectionData : orderShipDetails){
						if(!StringUtils.equals(supplierCollectionData.getFlag() , "Y")){
							enableShipFlag = false;
							break;
						}
					}
				}
			}
			
			return !enableShipFlag;
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}

	//@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/listOrder", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public List<OrderVo> listOrder(@RequestBody final OrderViewVo orderViewVo) throws SystemException {

//		String fromDB =ResourceUtil.getSystemConfig().getProperty("fromDB");

		List<OrderVo> list = null;
		try {
//			if("Y".equals(fromDB)){
			// list = orderServiceFromDB.viewOrderList(orderViewVo);
			// }else
				list = orderService.viewOrderList(orderViewVo);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return list;
	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/exportOrder", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/json" })
	@ResponseBody
	public ResponseData exportOrder(@RequestBody final OrderViewVo orderViewVo) throws SystemException {

		try {

			List<OrderExportVo> data = this.orderService.exportOrderList(orderViewVo);

			byte[] fileContent = exportExcel(data);
			ResponseData<OrderExportVo> responseData = (ResponseData<OrderExportVo>) responseDataService
					.getReturnData(OrderExportVo.class);
			responseData.setFileContent(fileContent);
			return responseData;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	private byte[] exportExcel(List<OrderExportVo> data) throws SystemException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();

		) {
			Workbook work = orderExportReportHandler.generateXls(data);
			work.write(os);

			return os.toByteArray();
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}

	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/exportOrderReturn", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/json" })
	@ResponseBody
	public ResponseData exportOrderReturn(@RequestBody final OrderViewVo orderViewVo) throws SystemException {

		try {

			List<OrderReturnExportVo> data = this.orderReturnService.exportOrderReturnList(orderViewVo);

			byte[] fileContent = exportReturnExcel(data);
			ResponseData<OrderReturnExportVo> responseData = (ResponseData<OrderReturnExportVo>) responseDataService
					.getReturnData(OrderReturnExportVo.class);
			responseData.setFileContent(fileContent);
			return responseData;
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	private byte[] exportReturnExcel(List<OrderReturnExportVo> data) throws SystemException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();

		) {
			Workbook work = orderReturnExportReportHandler.generateXls(data);
			work.write(os);

			return os.toByteArray();
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}

	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/orderReturnList", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public List<OrderReturnVo> viewOrderReturnListByOrderId(
			@RequestParam(value = "orderId", required = false) final String orderId) throws SystemException {
		try {
			List<OrderReturnVo> list = null;
			if (StringUtils.isNotEmpty(orderId))
				list = orderReturnService.selectByOrderId(orderId);
			return list;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/orderCompleteList", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public List<OrderTLogVo> viewOrderCompleteByOrderId(
			@RequestParam(value = "orderId", required = false) final String orderId) throws SystemException {
		try {
			List<OrderTLogVo> list = null;
			if (StringUtils.isNotEmpty(orderId))
				list = orderTLogService.selectByOrderId(new BigDecimal(orderId));
			return list;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/orderReturnDetail", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public OrderReturnVo getOrderReturnDetail(@RequestParam(value = "id", required = false) final String id)
			throws SystemException {
		try {
			OrderReturnVo vo = null;
			if (StringUtils.isNotEmpty(id))
				vo = orderReturnService.selectReturnAndReturnEntryByPrimaryKey(new BigDecimal(id));
			return vo;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({ "ROLE_SUPPLIER" })
	@RequestMapping(value = "/confirmOrderReturn", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public ResponseData confirmOrderReturn(@RequestBody final OrderReturnVo orderReturnVo) throws SystemException {

		ResponseData<OrderVo> responseData = (ResponseData<OrderVo>) responseDataService.getReturnData(OrderVo.class);
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		responseData.setResourceBundleMessageSource(messageSource);
		try {
			boolean success = orderReturnService.updateConfirmOrderReturn(orderReturnVo, responseData);
			if (success) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				return responseData;
			} else {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}

	}

	// @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/cancelRequest", method = { RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody

	public OrderCancelReponseVo cancelRequest(@RequestBody final OrderCancelRequestVo orderCancelRequestVo)
			throws SystemException {
		List<CancelRequest> cancelRequests = null;
		OrderCancelReponseVo orderCancelReponseVo = new OrderCancelReponseVo();

		try {
			if (orderCancelRequestVo != null) {
				cancelRequests = orderCancelRequestVo.getCancelRequests();
				return orderService.getAllCancleResponses(cancelRequests);
			} else {
				List<CancelResponse> cancelResponses = new ArrayList<>();
				orderCancelReponseVo.setCancelResponses(cancelResponses);

				CancelResponse cancelResponse = new CancelResponse();
				
				cancelResponse.setErrorCode("fail.error");
				cancelResponse.setErrorMessage("Jason file is empty.");
				return orderCancelReponseVo;

			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			// throw new SystemException(e.getMessage(), e);

			List<CancelResponse> cancelResponses = new ArrayList<>();
			orderCancelReponseVo.setCancelResponses(cancelResponses);

			CancelResponse cancelResponse = new CancelResponse();
			cancelResponse.setErrorCode("fail.error");
			cancelResponse.setErrorMessage(e.getMessage());
			return orderCancelReponseVo;

		}

	}
}
