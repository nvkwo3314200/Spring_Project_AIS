package com.mall.b2bp.schedule;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.csv.CsvFileBuilder;
import com.mall.b2bp.csv.CsvFileBuilderFactory;
import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ErrorLogType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.services.order.OrderService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.order.OrderVo;

/**
 * Created by USER on 2016/4/6.
 */
public class HomeOrderExportJob {

	private static final Logger LOG = LoggerFactory.getLogger(HomeOrderExportJob.class);

	@Resource(name = "orderService")
	private OrderService orderService;

	@Resource
	private SendEmail sendEmail;
	
	//private FtpService ftpService;
	
//	public FtpService getFtpService() {
//		return ftpService;
//	}
//
//	public void setFtpService(FtpService ftpService) {
//		this.ftpService = ftpService;
//	}

	private ErrorLog createLog(CsvFileBuilder pickedFile, String log) {

		if (pickedFile == null)
			return null;

		if (StringUtils.isEmpty(log))
			return null;

		ErrorLog errorLog = new ErrorLog();
		errorLog.setFileName(pickedFile.getFileName());
		errorLog.setErrorLogType(ErrorLogType.HOME_ORDER);
		errorLog.setCreateTime(new Date());
		errorLog.setMethodName("executeInternal");
		errorLog.add(log);

		return errorLog;
	}

	public void executeInternal() throws ServiceException {

		LOG.info("#####start home order ####");

		List<OrderVo> orderVoList = orderService.selectByOrderHome();

		//String path = ResourceUtil.getSystemConfig().getProperty("home_order_path");
		
		String archivePath = ResourceUtil.getSystemConfig().getProperty("home_order_path")+File.separator+ConstantUtil.ARCHIVE;
		File archiveDir = new File(archivePath);
		
		if(!archiveDir.exists()){
			archiveDir.mkdir();
		}
		
		if (CollectionUtils.isEmpty(orderVoList))
			return;

		Map<String, List<OrderVo>> map = reduceList(orderVoList);

		List<OrderVo> pickedList = map.get("P");
		List<OrderVo> shippedList = map.get("S");
		List<OrderVo> deliveryList = map.get("D");

		CsvFileBuilder pickedFile = null;
		//List<File> fileList = new ArrayList<>();
		 
		List<ErrorLog> errorLogList = new ArrayList<>();
		try {
			if(CollectionUtils.isNotEmpty(pickedList)){
				pickedFile = CsvFileBuilderFactory.getCsvFileBuilder(ConstantUtil.PICKED, pickedList);
				pickedFile.writeCsvFile();
//				String fileName = path + File.separator + pickedFile.getFileName(); 
//				
//				File file = new File(fileName);
//				if(file.exists()) {
//					FileUtils.moveToDirectory(file, archiveDir, true);
//					fileList.add(file);
//				}
				
			}
		} catch (Exception e) {
			errorLogList.add(createLog(pickedFile, "Throw Excepton:" + e.getMessage()));
			LOG.error(e.getMessage(), e);
		}

		CsvFileBuilder shipFile = null;
		try {
			shipFile = CsvFileBuilderFactory.getCsvFileBuilder(ConstantUtil.SHIPPED, shippedList);
			shipFile.writeCsvFile();
			
//			String fileName = path + File.separator + shipFile.getFileName(); 
//			
//			File file = new File(fileName);
//			if(file.exists()) {
//				FileUtils.moveToDirectory(file, archiveDir, true);
//				fileList.add(file);
//			}
			
		} catch (Exception e) {
			errorLogList.add(createLog(shipFile, "Throw Excepton:" + e.getMessage()));
			LOG.error(e.getMessage(), e);
		}

		CsvFileBuilder deliveryFile = null;
		try {
			deliveryFile = CsvFileBuilderFactory.getCsvFileBuilder(ConstantUtil.DELIVERY, deliveryList);
			deliveryFile.writeCsvFile();
			
//			String fileName = path + File.separator + deliveryFile.getFileName(); 
//			
//			File file = new File(fileName);
//			if(file.exists()) {
//				FileUtils.moveToDirectory(file, archiveDir, true);
//				fileList.add(file);
//			}
			
		} catch (Exception e) {
			errorLogList.add(createLog(deliveryFile, "Throw Excepton:" + e.getMessage()));
			LOG.error(e.getMessage(), e);
		}

		batchUpdateOrder(orderVoList);
		try {
			this.sendEmail.sendErrorLogMail(errorLogList);
		} catch (UnsupportedEncodingException e) {
			LOG.error("BrandMasterJob send "+e.getMessage(), e);

		}
		
		
//		if(CollectionUtils.isNotEmpty(fileList)){
//			try {
//				ftpService.uploadFiles(fileList);
//			} catch (IOException | FtpProtocolException e) {
//				LOG.error("ftp error:"+e.getMessage(),e);
//			}
//		}
		LOG.info("#####end home order ####");
	}

	private Map<String, List<OrderVo>> reduceList(List<OrderVo> orderVoList) {
		Map<String, List<OrderVo>> map = new HashMap();

		List<OrderVo> pickList = new ArrayList<>();
		List<OrderVo> shippedList = new ArrayList<>();
		List<OrderVo> deliveryList = new ArrayList<>();

		try {

			for (OrderVo orderVo : orderVoList) {
				

				if ("Y".equals(orderVo.getPickedInd())) {
					pickList.add(orderVo);
				}
				if ("Y".equals(orderVo.getShippedInd())) {
					shippedList.add(orderVo);
				}
				if ("Y".equals(orderVo.getDeliveryInd())) {
					deliveryList.add(orderVo);
				}
			}
		} catch (Exception e) {
			LOG.error("regetOrderList error:" + e.getMessage(), e);
		}

		map.put("P", pickList);
		map.put("S", shippedList);
		map.put("D", deliveryList);

		return map;
	}

	private void batchUpdateOrder(List<OrderVo> orderVoList) {

		try {

			for (OrderVo orderVo : orderVoList) {
				OrderModel orderModel = new OrderModel();
				orderModel.setLastUpdatedDate(new Date());
				orderModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				orderModel.setId(orderVo.getId());

				if ("Y".equals(orderVo.getPickedInd())) {
					orderModel.setPickedInd("P");
					orderService.updateByPrimaryKeySelective(orderModel);
				}

				if ("Y".equals(orderVo.getShippedInd())) {
					orderModel.setShippedInd("P");
					orderService.updateByPrimaryKeySelective(orderModel);
				}

				if ("Y".equals(orderVo.getDeliveryInd())) {
					orderModel.setDeliveryInd("P");
					orderService.updateByPrimaryKeySelective(orderModel);
				}
			}
		} catch (Exception e) {
			LOG.error("regetOrderList error:" + e.getMessage(), e);
		}
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}
