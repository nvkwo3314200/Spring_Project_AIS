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
import com.mall.b2bp.services.product.ProductBarcodeSyncService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.ResourceUtil;

public class ProductBarcodeSyncJob {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductBarcodeSyncJob.class);
	
	private ProductBarcodeSyncService productBarcodeSyncService;
	
	private FtpService ftpService;
	
	public void execute(){

		String path = ResourceUtil.getSystemConfig().getProperty(
				"product_export_path");
		String fileName = path + File.separator + "IF323_BARCODE"
				+ DateUtils.getCurrentFormatDate(DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + ".csv";
		
		String archivePath = ResourceUtil.getSystemConfig().getProperty(
				"product_export_path")+File.separator+ConstantUtil.ARCHIVE;
		File archiveDir = new File(archivePath);
		
		File file = new File(fileName);

            LOG.info("==========ProductBarcodeSyncJob begin");
            productBarcodeSyncService.exportProductBarcode(file);
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
            LOG.info("==========ProductBarcodeSyncJob end");

	}

	public ProductBarcodeSyncService getProductBarcodeSyncService() {
		return productBarcodeSyncService;
	}

	public void setProductBarcodeSyncService(
			ProductBarcodeSyncService productBarcodeSyncService) {
		this.productBarcodeSyncService = productBarcodeSyncService;
	}

	public FtpService getFtpService() {
		return ftpService;
	}

	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}

}
