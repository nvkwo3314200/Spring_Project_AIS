package com.mall.b2bp.schedule;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpProtocolException;

import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ErrorLogType;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.ftp.FtpService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;

public class BrandMasterJob {

	private static final Logger LOG = LoggerFactory.getLogger(BrandMasterJob.class);

	@Resource(name = "brandService")
	private BrandService brandService;

	@Resource
	private SendEmail sendEmail;
	
	private FtpService ftpService;

	public void executeInternal() {

		LOG.info("#####start BrandMasterJob ####");

		String newPath = ResourceUtil.getSystemConfig().getProperty("import_brand_file_path");
		String ftpArchivePath = ResourceUtil.getSystemConfig().getProperty(
				"retek.outbox.ftp.archive.path");
		String ftpErrorPath = ResourceUtil.getSystemConfig().getProperty(
				"retek.outbox.ftp.error.path");
		
		String historyPath = newPath + File.separator+ ConstantUtil.ARCHIVE;//ResourceUtil.getSystemConfig().getProperty("import_brand_history_file_path");
		String errorPath = newPath+ File.separator+ ConstantUtil.ERROR;//ResourceUtil.getSystemConfig().getProperty("import_brand_error_file_path");

		File historyDir = new File(historyPath);
		if(!historyDir.exists()){
			historyDir.mkdirs();
		}
		
		File errorDir = new File(errorPath);
		if(!errorDir.exists()){
			errorDir.mkdirs();
		}
		
		try {
			ftpService.downloadFiles(newPath, "sp_brand", null);
		} catch (IOException | FtpProtocolException e) {
			LOG.error(e.getMessage(), e);
		}
		
		String[] files = FileHandle.getFilesByPath(newPath);
		if (files == null || files.length == 0)
			return;

		List<ErrorLog> errorLogList = new ArrayList<>();
		for (String fileName : files) {

			ErrorLog errorLog = new ErrorLog();
			errorLog.setFileName(fileName);
			errorLog.setErrorLogType(ErrorLogType.BRAND_SYNC_FROM_RETEK);
			errorLog.setCreateTime(new Date());
			errorLog.setMethodName("executeInternal");
			boolean result = false;
			try {
				result = brandService.importBrandMastor(newPath + File.separator + fileName, errorLog);

			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				result = false;
			}
			if (result) {
				FileHandle.copyFile(newPath, historyPath, fileName);
                List<File> fileList = new ArrayList();
                fileList.add(new File(newPath+File.separator+fileName));
                try {
					ftpService.uploadFiles(ftpArchivePath, fileList);
					ftpService.deleteFile(fileName);
				} catch (IOException | FtpProtocolException e) {
					LOG.error(e.getMessage(), e);
				}
			} else {
				FileHandle.copyFile(newPath, errorPath, fileName);
                List<File> fileList = new ArrayList();
                fileList.add(new File(newPath+File.separator+fileName));
                try {
					ftpService.uploadFiles(ftpErrorPath, fileList);
					ftpService.deleteFile(fileName);
				} catch (IOException | FtpProtocolException e) {
					LOG.error(e.getMessage(), e);
				}
			}

			FileHandle.deleteFile(newPath + File.separator + fileName);

			if (CollectionUtils.isNotEmpty(errorLog.getErrorList())) {
				errorLogList.add(errorLog);
			}
		}

		try {
			if(CollectionUtils.isNotEmpty(errorLogList))
				LOG.info("import BrandMasterJob has some errro,plesase check email..");
			this.sendEmail.sendErrorLogMail(errorLogList);
		} catch (UnsupportedEncodingException e) {
			LOG.error("BrandMasterJob send "+e.getMessage(), e);

		}

		LOG.info("#####end BrandMasterJob ####");
	}

	public BrandService getBrandService() {
		return brandService;
	}

	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	public FtpService getFtpService() {
		return ftpService;
	}

	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}

	
}
