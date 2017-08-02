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
import com.mall.b2bp.services.ftp.FtpService;
import com.mall.b2bp.services.product.ProductSyncService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;

public class ProductImagesSyncFromRetekJob {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductImagesSyncFromRetekJob.class);
	
	private ProductSyncService productSyncService;
	
	private FtpService ftpService;
	
	@Resource
	SendEmail sendEmail;
	
	public void executeImagesFromRetek(){
		LOG.info(" Start  " + new Date());
		String path = ResourceUtil.getSystemConfig().getProperty("product_files_import_path");
		String ftpArchivePath = ResourceUtil.getSystemConfig().getProperty(
				"retek.outbox.ftp.archive.path");
		String ftpErrorPath = ResourceUtil.getSystemConfig().getProperty(
				"retek.outbox.ftp.error.path");
		String imagesPath = ResourceUtil.getSystemConfig().getProperty("product_images_import_path");

		String historyPath = path + File.separator+ ConstantUtil.ARCHIVE;
		String errorPath = path+ File.separator+ ConstantUtil.ERROR;
		
		File historyDir = new File(historyPath);
		if(!historyDir.exists()){
			historyDir.mkdirs();
		}
		
		File errorDir = new File(errorPath);
		if(!errorDir.exists()){
			errorDir.mkdirs();
		}
		
		File imagesDir = new File(imagesPath);
		if(!imagesDir.exists()){
			imagesDir.mkdirs();
		}
		
		try {
			ftpService.downloadFiles(path, "IF351", null);
			ftpService.downloadFiles(imagesPath, null, ".jpg");
		} catch (IOException | FtpProtocolException e) {
			LOG.error(e.getMessage(), e);
		}
		
		String[] files = FileHandle.getFilesByPath(path);
        if(files ==null || files.length==0)
            return;
        List<ErrorLog> errorLogList=new ArrayList<>();
        boolean result=false;
		for (String fileName : files) {
			 ErrorLog errorLog=new ErrorLog();
            try {
            	errorLog.setErrorLogType(ErrorLogType.PRODUCT_IMAGE_SYNC_FROM_RETEK);
    			errorLog.setFileName(fileName);
    			errorLog.setCreateTime(new Date());
    			errorLog.setMethodName("executeImagesFromRetek");
    			LOG.info("handle product images file:" + fileName);
                
                //handle
                result=productSyncService.importProductImagesFromRetek(path+File.separator+fileName,errorLog);
            } catch (Exception e) {
            	errorLog.add(e.getMessage());
            	LOG.error(e.getMessage(), e);
            }
            // copy file and delete file
            if(result){
                FileHandle.copyFile(path, historyPath, fileName);
                List<File> fileList = new ArrayList();
                fileList.add(new File(path+File.separator+fileName));
                try {
					ftpService.uploadFiles(ftpArchivePath, fileList);
					ftpService.deleteFile(fileName);
				} catch (IOException | FtpProtocolException e) {
					LOG.error(e.getMessage(), e);
				}
            }else {
				FileHandle.copyFile(path, errorPath, fileName);
                List<File> fileList = new ArrayList();
                fileList.add(new File(path+File.separator+fileName));
                try {
					ftpService.uploadFiles(ftpErrorPath, fileList);
					ftpService.deleteFile(fileName);
				} catch (IOException | FtpProtocolException e) {
					LOG.error(e.getMessage(), e);
				}
			}
            
            FileHandle.deleteFile(path + File.separator + fileName);
            if(CollectionUtils.isNotEmpty(errorLog.getErrorList())){
            	errorLogList.add(errorLog);
            }
        }
		
		if(CollectionUtils.isNotEmpty(errorLogList)){
			try {
				sendEmail.sendErrorLogMail(errorLogList);
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		LOG.info(" End " + new Date());
	}

	public ProductSyncService getProductSyncService() {
		return productSyncService;
	}

	public void setProductSyncService(ProductSyncService productSyncService) {
		this.productSyncService = productSyncService;
	}

	public FtpService getFtpService() {
		return ftpService;
	}

	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}

	
}
