package com.mall.b2bp.schedule;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ErrorLogType;
import com.mall.b2bp.services.order.OrderTLogService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;

/**
 * Created by USER on 2016/4/6.
 */
public class ConsignmentOrderCompletedImportJob {

	private static final Logger LOG = LoggerFactory
			.getLogger(ConsignmentOrderCompletedImportJob.class);

	@Resource(name = "orderTLogService")
	private OrderTLogService orderTLogService;

	@Resource
	private SendEmail sendEmail;

	public SendEmail getSendEmail() {
		return sendEmail;
	}
	

	public void setSendEmail(SendEmail sendEmail) {
		this.sendEmail = sendEmail;
	}

	
	
	public void executeInternal() {
		LOG.info("#####start Order Completed (Consignment orders)####");

		String newPath = ResourceUtil.getSystemConfig().getProperty(
				"import_consignment_order_completed_path");
		String historyPath = newPath + File.separator + ConstantUtil.ARCHIVE;
		String errorPath = newPath + File.separator + ConstantUtil.ERROR;

		String[] files = FileHandle.getFilesByPath(newPath);
		if (files == null || files.length == 0)
			return;

		List<ErrorLog> errorLogList = new ArrayList<>();
		for (String fileName : files) {

			ErrorLog errorLog = new ErrorLog();
			errorLog.setFileName(fileName);
			errorLog.setErrorLogType(ErrorLogType.CONSIGNMENT_ORDER_COMPLETED);
			errorLog.setCreateTime(new Date());
			errorLog.setMethodName("executeInternal");
			boolean result = false;
			try {
				LOG.info("import file: "+fileName);
				
				result = orderTLogService.updateOrderCompleted(newPath+ File.separator + fileName, errorLog);

			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				result = false;
			}
			if (result) {
				FileHandle.copyFile(newPath, historyPath, fileName);
			} else {
				FileHandle.copyFile(newPath, errorPath, fileName);
			}

			FileHandle.deleteFile(newPath + File.separator + fileName);

			if (CollectionUtils.isNotEmpty(errorLog.getErrorList())) {
				errorLogList.add(errorLog);
			}
		}

		try {
			this.sendEmail.sendErrorLogMail(errorLogList);
		} catch (UnsupportedEncodingException e) {
			LOG.error("BrandMasterJob send " + e.getMessage(), e);

		}

		LOG.info("#####end Order Completed (Consignment orders) ####");
	}


	public OrderTLogService getOrderTLogService() {
		return orderTLogService;
	}


	public void setOrderTLogService(OrderTLogService orderTLogService) {
		this.orderTLogService = orderTLogService;
	}

}
