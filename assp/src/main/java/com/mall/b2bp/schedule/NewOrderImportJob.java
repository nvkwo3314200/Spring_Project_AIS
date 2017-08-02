package com.mall.b2bp.schedule;

import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ErrorLogType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderEntryModel;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.oxm.order.OrderBean;
import com.mall.b2bp.oxm.order.OrderBeans;
import com.mall.b2bp.oxm.order.OrderEntryBean;
import com.mall.b2bp.oxm.order.OrderXmlToObject;
import com.mall.b2bp.populators.email.EmailPopulator;
import com.mall.b2bp.populators.order.OrderBeanPopulator;
import com.mall.b2bp.populators.order.OrderEntryBeanPopulator;
import com.mall.b2bp.services.order.OrderEntryService;
import com.mall.b2bp.services.order.OrderService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.email.NotificationEmailVo;

public class NewOrderImportJob {

	private static final Logger LOG = LoggerFactory.getLogger(NewOrderImportJob.class);

	private OrderXmlToObject orderXmlToObject;

    @Resource(name = "orderService")
    private OrderService orderService;
    
    @Resource(name = "orderEntryService")
    private OrderEntryService orderEntryService;

    @Resource
	private SendEmail sendEmail;

    private Set set = new HashSet<>();
    
    protected void executeInternal() throws ServiceException {
		LOG.info(" log " + new Date());
		String newPath = ResourceUtil.getSystemConfig().getProperty(
				"import_new_order_file_path");
		String historyPath = newPath+File.separator+ConstantUtil.ARCHIVE;
		String errorPath = newPath+File.separator+ConstantUtil.ERROR;

		String[] files = FileHandle.getFilesByPath(newPath);
        if(files ==null || files.length==0)
            return;
        
        set.clear();
    	List<ErrorLog> errorLogList=new ArrayList<>();
    	List<NotificationEmailVo> returnSaveBeanList = new ArrayList<>();
		for (String fileName : files) {
			ErrorLog errorLog=new ErrorLog();
			boolean result=false;
			OrderBeans orderBeans = null;
			try {
				// get data
				 orderBeans = loadSimpleBean(newPath+ File.separator + fileName,errorLog);
				// save data
				 if(orderBeans!=null){
					 returnSaveBeanList=saveOrderBean(orderBeans);
					 result  = true;
				 }
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				errorLog.add("throw exception:"+e);
				result=false;
			}
				// copy file and delete file
				if(result){
					FileHandle.copyFile(newPath, historyPath, fileName);
				 	this.sendEmail.sendNotificationEmail(returnSaveBeanList);					
				}else{
					FileHandle.copyFile(newPath, errorPath, fileName);
				}
				try {
					FileHandle.deleteFile(newPath + File.separator + fileName);
				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
					throw new ServiceException(e.getMessage(),e);
				}
		
				LOG.info("comp:" + fileName);

		
			if(!errorLog.getErrorList().isEmpty()){
				errorLogList.add(errorLog);
			}

			
		}
		try {
			if(!errorLogList.isEmpty()){
			this.sendEmail.sendErrorLogMail(errorLogList);
			}
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(),e);
		throw new ServiceException(e.getMessage(),e);
		}

	}
	
	
	
	private boolean isNotEmptyBean(OrderBeans orderBeans){
		if(orderBeans==null)
			return false;
		if(orderBeans.getOrderBeanList().isEmpty()){
			return false;
		}
		return true;
		
	}
	
	

	
	public List<NotificationEmailVo>  saveOrderBean(OrderBeans orderBeans) throws ServiceException  {

		
		List<NotificationEmailVo> 	returnSaveBeanList = new ArrayList<>();
			
		if (isNotEmptyBean(orderBeans)) {
			List<OrderBean> orderBeanList = orderBeans.getOrderBeanList();
			OrderBeanPopulator orderBeanPopulator = new OrderBeanPopulator();
			for (OrderBean orderBean : orderBeanList) {
				if (orderBean == null)
					continue;
				
				OrderModel	orderModel = orderService.getOrderByPickOrderId(orderBean.getPickOrderId());//selectByHybrisOrderId(orderBean.getHybrisOrderId());
			
					BigDecimal orderId = null;
					if (orderModel == null) {
						orderModel = new OrderModel();
						orderBeanPopulator.converBeanlToModel(orderModel,
								orderBean);
						if(StringUtils.isEmpty(orderModel.getOrderType())){
							continue;
						}
						
						orderModel.setCreatedDate(new Date());
						orderModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
						orderModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
						orderModel.setId(orderId);
						orderModel.setStatus(ConstantUtil.NEW);
						//if(orderBean.getHybrisOrderId().contains("-R")){
						if(orderBean.getPickOrderId().contains("-R")){
							String orgOrderId=orderBean.getPickOrderId().substring(0, orderBean.getPickOrderId().indexOf("-R"));
							OrderModel	orgOrder = orderService.getOrderByPickOrderId(orgOrderId);//selectByHybrisOrderId(orgOrderId);
							if(orgOrder!=null){
							orderModel.setOrigOrderId(orgOrder.getId());
							}
							orderModel.setReplaceOrderInd("Y");
						}
						orderService.insertSelective(orderModel);
				
						
					} else {
						orderBeanPopulator.converBeanlToModel(orderModel,
								orderBean);
						if(StringUtils.isEmpty(orderModel.getOrderType())){
							continue;
						}

                        orderModel.setCreatedDate(new Date());
                        orderModel.setLastUpdatedDate(new Date());
                        orderModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
                        orderModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
//						if(orderBean.getHybrisOrderId().contains("-R")){
                        if(orderBean.getPickOrderId().contains("-R")){
							String orgOrderId=orderBean.getPickOrderId().substring(0, orderBean.getPickOrderId().indexOf("-R"));
							OrderModel	orgOrder = orderService.getOrderByPickOrderId(orgOrderId);//selectByHybrisOrderId(orgOrderId);
							if(orgOrder!=null){
								orderModel.setOrigOrderId(orgOrder.getId());
								}
							orderModel.setReplaceOrderInd("Y");
						}
						orderService.updateByPrimaryKeySelective(orderModel);
					
					}

					List<OrderEntryBean> entryBeanList = orderBean.getEntryBeanList();
					saveOrderEntryBeanList(orderModel, entryBeanList,returnSaveBeanList);
						
//
				}

			}
		
		return returnSaveBeanList;

	}

	private void saveOrderEntryBeanList(OrderModel orderModel,List<OrderEntryBean> entryBeanList,
			List<NotificationEmailVo> returnSaveBeanList) throws ServiceException {
		if (!entryBeanList.isEmpty()) {
			OrderEntryBeanPopulator entryBeanPopulator = new OrderEntryBeanPopulator();
			for (OrderEntryBean orderEntryBean : entryBeanList) {
				HashMap hashMap = new HashMap();
				hashMap.put("orderId", orderModel.getId());
				hashMap.put("skuId", orderEntryBean.getSkuId());
				hashMap.put("supplierId", orderEntryBean.getSupplierId());

				
				 OrderEntryModel entryModel = orderEntryService.selectByHashMap(hashMap);
		
				if (entryModel == null) {
					entryModel = new OrderEntryModel();
					entryBeanPopulator.converBeanlToMode(entryModel, orderEntryBean);
					entryModel.setOrderId(orderModel.getId());

					entryModel.setCreatedDate(new Date());
					entryModel.setLastUpdatedDate(new Date());
					entryModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
					entryModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
			
					orderEntryService.insertSelective(entryModel);
				

				} else {
					entryBeanPopulator.converBeanlToMode(entryModel, orderEntryBean);
					entryModel.setCreatedDate(new Date());
					entryModel.setLastUpdatedDate(new Date());
					entryModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
					entryModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				
					orderEntryService.updateByPrimaryKeySelective(entryModel);
				}
				//Given that HomeOrder interface of Supplier Direct order is interfaced to Supplier Portal,
				//when scheduled job starts, then I will receive email notification to the email address specified on the user profile
				if(ConstantUtil.SUPPLIER_DIRECT_DELIVERY.equals(orderModel.getOrderType())&&
						set.add(orderModel.getHybrisOrderId()+"_"+entryModel.getSupplierId())){
					LOG.info("success add hybrisOrderid+ supplierId:"+orderModel.getHybrisOrderId()+entryModel.getSupplierId());
					returnSaveBeanList.add(	getBean(orderModel,entryModel));
				}

			}

		}
	}


	private NotificationEmailVo getBean(OrderModel orderModel,OrderEntryModel entryModel){
		NotificationEmailVo vo = new NotificationEmailVo();
		vo.setEmailType(EmailPopulator.HOME_ORDER_INTERFACE);
		vo.setHybridOrderId(orderModel.getHybrisOrderId());
		vo.setOrderReturnId(null);
		vo.setSupplierId(entryModel.getSupplierId());
		
		return vo;
	}

	



	public OrderBeans loadSimpleBean(String fileName,ErrorLog errorLog)  {
		OrderBeans beans = null;
		errorLog.setCreateTime(new Date());
		errorLog.setMethodName("NewOrderImportJob.loadSimpleBean");
		errorLog.setFileName(fileName);
		errorLog.setErrorLogType(ErrorLogType.NEW_ORDER_IMPORT);
		try (
				FileReader is = new FileReader(fileName);
				){
			
			beans = (OrderBeans) this.orderXmlToObject.getUnmarshaller()
					.unmarshal(new StreamSource(is));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			errorLog.add("throw Exception:"+e);
			
		} 

		return beans;
	}

	public OrderXmlToObject getOrderXmlToObject() {
		return orderXmlToObject;
	}

	public void setOrderXmlToObject(OrderXmlToObject orderXmlToObject) {
		this.orderXmlToObject = orderXmlToObject;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderEntryService getOrderEntryService() {
		return orderEntryService;
	}

	public void setOrderEntryService(OrderEntryService orderEntryService) {
		this.orderEntryService = orderEntryService;
	}

	public SendEmail getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(SendEmail sendEmail) {
		this.sendEmail = sendEmail;
	}



}
