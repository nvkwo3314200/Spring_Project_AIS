package com.mall.b2bp.schedule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ErrorLogType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderReturnModel;
import com.mall.b2bp.models.order.OrderReturnReceivedModel;
import com.mall.b2bp.services.order.OrderReturnService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;

/**
 * Created by USER on 2016/4/6.
 */
public class OrderReturnReceivedExportJob {

	private static final Logger LOG = LoggerFactory.getLogger(OrderReturnReceivedExportJob.class);
	private static final String NEW_LINE_SEPARATOR = "\n";

	@Resource(name = "orderReturnService")
	private OrderReturnService orderReturnService;

	@Resource
	private SendEmail sendEmail;
	
	
	//private FtpService ftpService;

	private ErrorLog createLog(String fileName, String log) {

		if (StringUtils.isEmpty(log))
			return null;

		ErrorLog errorLog = new ErrorLog();
		errorLog.setFileName(fileName);
		errorLog.setErrorLogType(ErrorLogType.RETURN_RECEIVED);
		errorLog.setCreateTime(new Date());
		errorLog.setMethodName("executeInternal");
		errorLog.add(log);

		return errorLog;
	}
//<!--  Order Return – PSSP to Hybris interface (Return receive)-->
	public void executeInternal() throws ServiceException {

		LOG.info("#####start Order Return – PSSP to Hybris interface (Return receive) ####");
		String newPath = ResourceUtil.getSystemConfig().getProperty("export_order_return_received_path");
		
		
		if(!new File(newPath).exists()){
			new File(newPath).mkdir();
		}
		
		String archivePath = ResourceUtil.getSystemConfig().getProperty("export_order_return_received_path")+File.separator+ConstantUtil.ARCHIVE;
		File archiveDir = new File(archivePath);
		if(!archiveDir.exists()){
			archiveDir.mkdir();
		}
		
		List<OrderReturnReceivedModel> orderVoList = orderReturnService.getReturnReceived();

	
		if (CollectionUtils.isEmpty(orderVoList))
			return;
		List<ErrorLog> errorLogList = new ArrayList<>();
		
		String fileName =newPath + File.separator+  "Return_Receive_SP_PNSHK_"
				+ DateUtils.formatDate(new Date(),DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + ".csv";
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator(NEW_LINE_SEPARATOR);
		try (FileWriter fileWriter = new FileWriter(fileName);
				CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter,csvFileFormat);) {

			List<List<String>> list = generateRecordList(orderVoList);
			wirteDataRecord(csvFilePrinter, list);
		
			//update order Request status
			updateOrderRetrunStatus(orderVoList);
			
		} catch (Exception e) {
			e.printStackTrace();
			errorLogList.add(createLog(fileName, "Throw Excepton:" + e.getMessage()));
			LOG.error(e.getMessage(), e);
		}

		
		try {
			this.sendEmail.sendErrorLogMail(errorLogList);
		} catch (UnsupportedEncodingException e) {
			LOG.error("BrandMasterJob send "+e.getMessage(), e);
		}
		
		
//		 File file = new File(fileName);
//		 List<File> fileList = new ArrayList<>();
//           fileList.add(file);
//           try {
//				if(file.exists()) {
//					ftpService.uploadFiles(fileList);
//					FileUtils.moveToDirectory(file, archiveDir, true);
//				}
//			} catch (IOException | FtpProtocolException e) {
//				LOG.error(e.getMessage(), e);
//			}
           
		
		LOG.info("#####end Order Return – PSSP to Hybris interface (Return receive)  ####");
	}
	
	private void updateOrderRetrunStatus(List<OrderReturnReceivedModel> orderVoList){
		
		
		
		if (CollectionUtils.isNotEmpty(orderVoList)){
		
			
			
			for(OrderReturnReceivedModel m:orderVoList){
				OrderReturnModel record = new OrderReturnModel();
				record.setId(m.getId());
				record.setLastUpdatedDate(new Date());
				record.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				record.setReturnReceiveInd("Y");
				
				orderReturnService.updateByPrimaryKeySelective(record);
			}
		}
		
	}

	private void wirteDataRecord(CSVPrinter csvFilePrinter,
			List<List<String>> userList) throws IOException {
		if (CollectionUtils.isNotEmpty(userList)) {
			for (List<String> list : userList) {
				try {
					csvFilePrinter.printRecord(list);
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
					throw e;
				}
			}
		}
	}

	public List<List<String>> generateRecordList(
			List<OrderReturnReceivedModel> orderVoList) {

		List<List<String>> allLines = new ArrayList<>();

		if (CollectionUtils.isEmpty(orderVoList)) {
			return null;
		}

		for (OrderReturnReceivedModel entry : orderVoList) {
			List<String> recordList = new ArrayList<>();

			recordList.add(entry.getOrderId());
			recordList.add(entry.getReturnId());
			recordList.add(entry.getReturnUpdateDate());
			recordList.add(entry.getSkuId());

			recordList.add(entry.getTotalReturnQty());// 4 TrckId
			recordList.add(entry.getActualCollectedQty());
			recordList.add(entry.getWriteOffQty());
			recordList.add(entry.getReturnSkuRemark());
			allLines.add(recordList);
		}
		return allLines;
	}
	public OrderReturnService getOrderReturnService() {
		return orderReturnService;
	}
	public void setOrderReturnService(OrderReturnService orderReturnService) {
		this.orderReturnService = orderReturnService;
	}
	public SendEmail getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(SendEmail sendEmail) {
		this.sendEmail = sendEmail;
	}
//	public FtpService getFtpService() {
//		return ftpService;
//	}
//	public void setFtpService(FtpService ftpService) {
//		this.ftpService = ftpService;
//	}

}
