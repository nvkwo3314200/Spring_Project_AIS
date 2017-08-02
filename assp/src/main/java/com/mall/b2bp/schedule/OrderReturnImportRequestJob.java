package com.mall.b2bp.schedule;

import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ErrorLogType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.oxm.order.OrderReturnBean;
import com.mall.b2bp.oxm.order.OrderReturnBeans;
import com.mall.b2bp.oxm.order.OrderReturnEntryBean;
import com.mall.b2bp.oxm.order.OrderReturnXmlToObject;
import com.mall.b2bp.services.order.OrderReturnService;
import com.mall.b2bp.services.order.OrderService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.email.NotificationEmailVo;

/**
 * Created by USER on 2016/4/6.
 */
public class OrderReturnImportRequestJob {

	private static final Logger LOG = LoggerFactory
			.getLogger(OrderReturnImportRequestJob.class);

	@Resource(name = "orderService")
	private OrderService orderService;

	@Resource(name = "orderReturnService")
	private OrderReturnService orderReturnService;

	public static final String RVS_ARRIVES="RVS";
	
	private OrderReturnXmlToObject orderReturnXmlToObject;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderReturnService getOrderReturnService() {
		return orderReturnService;
	}

	public void setOrderReturnService(OrderReturnService orderReturnService) {
		this.orderReturnService = orderReturnService;
	}

	public OrderReturnXmlToObject getOrderReturnXmlToObject() {
		return orderReturnXmlToObject;
	}

	public void setOrderReturnXmlToObject(
			OrderReturnXmlToObject orderReturnXmlToObject) {
		this.orderReturnXmlToObject = orderReturnXmlToObject;
	}

	public SendEmail getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(SendEmail sendEmail) {
		this.sendEmail = sendEmail;
	}

	@Resource
	private SendEmail sendEmail;

	
	//<!--  Order Return Order Return – Hybris to PSSP interface (RVS) -->
	protected void executeInternal() throws ServiceException {
		LOG.info(" start Order Return – Hybris to PSSP interface (RVS) " + new Date());
		String newPath = ResourceUtil.getSystemConfig().getProperty("import_order_return_request_path");
		
		String historyPath = newPath + File.separator + ConstantUtil.ARCHIVE;
		String errorPath = newPath + File.separator + ConstantUtil.ERROR;

		String[] files = FileHandle.getFilesByPath(newPath);
		if (files == null || files.length == 0){
			
			LOG.info(" end Order Return – Hybris to PSSP interface (RVS),not file... " );
			return;
		}

		List<ErrorLog> errorLogList = new ArrayList<>();
		List<NotificationEmailVo> returnSaveBeanList = null;
		for (String fileName : files) {
			ErrorLog errorLog = new ErrorLog();
			errorLog.setCreateTime(new Date());
			errorLog.setMethodName("OrderReturnImportRequestJob.executeInternal");
			errorLog.setFileName(fileName);
			errorLog.setErrorLogType(ErrorLogType.ORDER_RETURN_REQUEST_IMPORT);

			boolean result = false;
			try {
				
				LOG.info("import file: "+fileName);
				// get data
				OrderReturnBeans orderBeans = loadSimpleBean(newPath+ File.separator + fileName, errorLog);
				
				
				boolean checkError =checkOrderReturnBean( orderBeans, errorLog);
				if( !checkError){
					returnSaveBeanList =	orderReturnService.updateReturnOrders(orderBeans);
					result = true;
				}else{
					LOG.error("check order return Request error,please check email..");
					result = false;
				}
				
				//result = saveOrderBean(orderBeans,errorLog,returnSaveBeanList);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				errorLog.add("throw exception:" + e);
				result = false;
			}
			
			// copy file and delete file
			if (result) {
				FileHandle.copyFile(newPath, historyPath, fileName);
				LOG.info("start send Notification Email..");
				this.sendEmail.sendNotificationEmail(returnSaveBeanList);	
			} else {
				FileHandle.copyFile(newPath, errorPath, fileName);
			}

			FileHandle.deleteFile(newPath + File.separator + fileName);
			

			if (!errorLog.getErrorList().isEmpty()) {
				errorLogList.add(errorLog);
			}
		}

		try {

			this.sendEmail.sendErrorLogMail(errorLogList);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
//			throw new ServiceException(e.getMessage(), e);
		}
		
		LOG.info(" end 	Order Return – Hybris to PSSP interface (RVS)" + new Date());

	}
	
//	public boolean saveOrderBean(OrderReturnBeans orderBeans,ErrorLog errorLog,List<NotificationEmailVo> returnSaveBeanList)
//			throws ServiceException {
//		
//		boolean checkError =checkOrderReturnBean( orderBeans, errorLog);
//		if( !checkError){
//			orderReturnService.updateReturnOrders(orderBeans);
//			return true;
//		}else{
//			LOG.error("check order return Request error,please check email..");
//			return false;
//		}
//	}
	
	


	public boolean checkOrderReturnBean(OrderReturnBeans orderBeans,ErrorLog errorLog) {
	
		if (orderBeans != null&& CollectionUtils.isNotEmpty(orderBeans.getOrderReturnBeanList())) {

			List<OrderReturnBean> orderReturnList = orderBeans
					.getOrderReturnBeanList();

			int i = 0;
			for (OrderReturnBean r : orderReturnList) {
				i++;
				BigDecimal returnType = r.getReturnType();
				BigDecimal pickStore = r.getPickStore();
				String returnId = r.getHybrisReturnId();
				String hybrisOrderId = r.getHybrisOrderId();

				if (returnType == null ||returnType.intValue()==0)
					errorLog.add(i + "th ReturnRequest Item,ReturnType is empty.");

				if (pickStore == null ||pickStore.intValue()==0)
					errorLog.add(i + "th ReturnRequest Item,PickStore is empty.");

				if (StringUtils.isEmpty(returnId) )
					errorLog.add(i + "th ReturnRequest Item,ReturnId is empty.");

				if (StringUtils.isEmpty(hybrisOrderId))
					errorLog.add(i + "th ReturnRequest Item,OrderId is empty.");

				List<OrderReturnEntryBean> orderReturnEntryBeanList = r.getEntryBeanList();
				
				
				boolean error = checkOrderReturnEntryBean(i,orderReturnEntryBeanList, errorLog);
				LOG.info(" check order Return Entry BeanList success?:"+ (error?"NOT OK":"OK"));
				if(!error){
					String supplierId=orderReturnEntryBeanList.get(0).getSupplierId();
					
					OrderModel orderModel = orderService.getOrderConsignmentID(hybrisOrderId, supplierId);
					r.setSupplierId(supplierId);
					if(orderModel==null || StringUtils.isEmpty(orderModel.getPickOrderId()) ||
							orderModel.getId()==null){
						errorLog.add(i + "th ReturnRequest Item,with Order number:"+hybrisOrderId +" and Supplier Code:"+supplierId+", system can not map to the exact order consignment");
					}else{
						LOG.info(i + "th ReturnRequest Item,with Order number:"+hybrisOrderId +" and Supplier Code:"+supplierId+",system can map to the exact order consignment");
						r.setOrderId(orderModel.getId());
						r.setPickOrderId(orderModel.getPickOrderId());
					}
				}
			}

		}else{
			errorLog.add("RVS xml file not item");
		}
		
		
		return CollectionUtils.isNotEmpty(errorLog.getErrorList())?true:false;
	}

	private boolean checkOrderReturnEntryBean(int i,
			List<OrderReturnEntryBean> orderReturnEntryBeanList,
			ErrorLog errorLog) {

		boolean error = false;
		int k = 0;
		if (CollectionUtils.isEmpty(orderReturnEntryBeanList)) {
			errorLog.add(i + " ReturnRequest Item,ReturnEntry is empty.");
			error = true;
		} else {

			for (OrderReturnEntryBean r : orderReturnEntryBeanList) {
				k++;
				String skuId = r.getSkuId();

				String supplierId = r.getSupplierId();
//				BigDecimal orderQty= r.getOrderQty();

				if (StringUtils.isEmpty(skuId)) {
					errorLog.add(k + " ReturnEntry Item,SkuId is empty.");
					error = true;
				}

				if (supplierId == null) {
					errorLog.add(k + " ReturnEntry Item,SupplierCode is empty.");
					error = true;
				}
//				if (orderQty == null) {
//					errorLog.add(k + " ReturnEntry Item,OrderQty is empty.");
//					error = true;
//				}
			}
		}
		return error;
	}

	

	private OrderReturnBeans loadSimpleBean(String fileName, ErrorLog errorLog) throws Exception {
		FileReader is = null;
		OrderReturnBeans beans = null;

		try {
			is = new FileReader(fileName);
			beans = (OrderReturnBeans) this.orderReturnXmlToObject
					.getUnmarshaller().unmarshal(new StreamSource(is));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			errorLog.add("throw Exception:" + e);
			throw e;
		}

		return beans;
	}

}
