package com.mall.b2bp.schedule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpProtocolException;

import com.mall.b2bp.services.ftp.FtpService;
import com.mall.b2bp.services.product.ProductSyncService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.ResourceUtil;

public class ProductMasterSyncJob {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductMasterSyncJob.class);
	
	private ProductSyncService productSyncService;
	
	private FtpService ftpService;
	
	public void execute(){

		String path = ResourceUtil.getSystemConfig().getProperty(
				"product_export_path");

		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		String archivePath = ResourceUtil.getSystemConfig().getProperty(
				"product_export_path")+File.separator+ConstantUtil.ARCHIVE;
		File archiveDir = new File(archivePath);
		
		String fileName = path + File.separator + "IF323_ITEM_MASTER_"
				+ DateUtils.getCurrentFormatDate(DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + ".csv";
		File file = new File(fileName);
//		if(file.exists()) {
            LOG.info("==========ProductMasterSyncJob begin");
            productSyncService.exportProductMaster(file);
            
            List<File> fileList = new ArrayList<>();
            fileList.add(file);
            try {
				ftpService.uploadFiles(fileList);
				if(file.exists()) {
					FileUtils.moveToDirectory(file, archiveDir, true);
				}
			} catch (IOException | FtpProtocolException e) {
				LOG.error(e.getMessage(), e);
			}
            LOG.info("==========ProductMasterSyncJob end");
//        }else{
//            LOG.info("ProductMasterSyncJob ,but file :"+fileName + " not exists.");
//        }
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
