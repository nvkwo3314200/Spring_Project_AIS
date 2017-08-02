package com.mall.b2bp.schedule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpProtocolException;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.services.ftp.FtpService;
import com.mall.b2bp.services.product.ProductImagesSyncService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.ResourceUtil;

public class ProductImageSyncJob {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductImageSyncJob.class);
	
	private ProductImagesSyncService productImagesSyncService;
	
	private FtpService ftpService;
	
	public void execute(){

		String path = ResourceUtil.getSystemConfig().getProperty(
				"product_export_path");
		String fileName = path + File.separator + "IF351_"
				+ DateUtils.getCurrentFormatDate(DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + "_SP.csv";
		
		String archivePath = ResourceUtil.getSystemConfig().getProperty(
				"product_export_path")+File.separator+ConstantUtil.ARCHIVE;
		File archiveDir = new File(archivePath);
		
		File file = new File(fileName);

		LOG.info("==========ProductImageSyncJob begin");
		try {
			productImagesSyncService.exportProductImage(file);

			List<File> fileList = new ArrayList<>();
			fileList.add(file);
			try {
				ftpService.uploadFiles(fileList);
				if (file.exists()) {
					FileUtils.moveToDirectory(file, archiveDir, true);
				}
			} catch (IOException | FtpProtocolException e) {
				LOG.error(e.getMessage(), e);
			}
		} catch (ServiceException e) {
			LOG.info("export image error", e);
		}
		LOG.info("==========ProductImageSyncJob end");

	}

	public ProductImagesSyncService getProductImagesSyncService() {
		return productImagesSyncService;
	}

	public void setProductImagesSyncService(
			ProductImagesSyncService productImagesSyncService) {
		this.productImagesSyncService = productImagesSyncService;
	}

	public FtpService getFtpService() {
		return ftpService;
	}

	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}

}
