package com.mall.b2bp.schedule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpProtocolException;

import com.mall.b2bp.services.ftp.FtpService;
import com.mall.b2bp.services.product.ProductSyncService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;

public class ProductMasterSyncFromRetekJob {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductMasterSyncFromRetekJob.class);
	
	private ProductSyncService productSyncService;
	
	private FtpService ftpService;
	
	public void executeMasterFromRetek(){
		LOG.info(" Start  " + new Date());
		String newPath = ResourceUtil.getSystemConfig().getProperty(
				"product_master_import_path");
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
			ftpService.downloadFiles(newPath, "IF323_TOSP_ITEM_MASTER", null);
		} catch (IOException | FtpProtocolException e) {
			LOG.error(e.getMessage(), e);
		}
		
		String[] files = FileHandle.getFilesByPath(newPath);
        if(files ==null || files.length==0)
            return;
//		log.info(" file nubmer: " + files.length);
		for (String fileName : files) {
            try {
            	LOG.info("handle product master file:" + fileName);
                //handle
                boolean result=productSyncService.importProductMasterFromRetek(newPath+File.separator+fileName);
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
            } catch (Exception e) {
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
