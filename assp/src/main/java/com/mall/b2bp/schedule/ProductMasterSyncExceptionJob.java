package com.mall.b2bp.schedule;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpProtocolException;

import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.services.ftp.FtpService;
import com.mall.b2bp.services.product.ProductSyncService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;

public class ProductMasterSyncExceptionJob {

	private static final Logger LOG = LoggerFactory.getLogger(ProductMasterSyncExceptionJob.class);


	private ProductSyncService productSyncService;
	private SendEmail sendEmail;
	private FtpService ftpService;
	
	protected void executeInternal() throws ServiceException {
		LOG.info(" Start  " + new Date());
		String newPath = ResourceUtil.getSystemConfig().getProperty(
				"product.acknowledgement.path");
		String ftpArchivePath = ResourceUtil.getSystemConfig().getProperty(
				"ack.ftp.archive.path");
		String ftpErrorPath = ResourceUtil.getSystemConfig().getProperty(
				"ack.ftp.error.path");
		String historyPath = newPath+File.separator+ConstantUtil.ARCHIVE;
		String errorPath = newPath+File.separator+ConstantUtil.ERROR;

		File historyDir = new File(historyPath);
		if(!historyDir.exists()){
			historyDir.mkdirs();
		}
		
		File errorDir = new File(errorPath);
		if(!errorDir.exists()){
			errorDir.mkdirs();
		}
		
		try {
			ftpService.downloadFiles(newPath, "sp_rms_ack", null);
		} catch (IOException | FtpProtocolException e) {
			LOG.error(e.getMessage(), e);
		}
		String[] files = FileHandle.getFilesByPath(newPath);
        if(files ==null || files.length==0)
            return;
//		log.info(" file nubmer: " + files.length);
		List<ErrorLog> errorLogList=new ArrayList<>();
		for (String fileName : files) {
	      	ErrorLog errorLog=new ErrorLog();
	      	boolean result=false;
            try {
            	LOG.info("handle product master file:" + fileName);
                //handle
                 result=productSyncService.importProductMastor(newPath+File.separator+fileName,errorLog);

            } catch (Exception e) {
                // TODO Auto-generated catch block
            	LOG.error(e.getMessage(), e);
            	errorLog.add("throw exception:"+e);
            	result=false;
            }
            
            // copy file and delete file
            if(result){
                FileHandle.copyFile(newPath, historyPath, fileName);
                List<File> fileList = new ArrayList();
                fileList.add(new File(newPath+File.separator+fileName));
                try {
					ftpService.uploadFiles(ftpArchivePath, fileList);
					ftpService.deleteFile(fileName);
				} catch (IOException | FtpProtocolException e) {
					LOG.error(e.getMessage(), e);
				}
            }else{
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
            LOG.info("delete:" + fileName);
            
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
		
		LOG.info(" End " + new Date());
	}



	public SendEmail getSendEmail() {
		return sendEmail;
	}



	public void setSendEmail(SendEmail sendEmail) {
		this.sendEmail = sendEmail;
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
