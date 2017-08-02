package com.mall.b2bp.enums;

import java.io.File;

import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.ResourceUtil;

public enum ImportLogPath {
	
	PRODUCT_MASTER_EXPORT_TO_RETEK_PATH(ImportLogType.PRODUCT_MASTER_EXPORT_TO_RETEK.getImportLogCode(),ResourceUtil.getSystemConfig().getProperty(
			"product_export_path")+File.separator+ConstantUtil.ARCHIVE),
	PRODUCT_BARCODE_EXPORT_TO_RETEK_PATH(ImportLogType.PRODUCT_BARCODE_EXPORT_TO_RETEK.getImportLogCode(),ResourceUtil.getSystemConfig().getProperty(
			"product_export_path")+File.separator+ConstantUtil.ARCHIVE),
	PRODUCT_IMAGE_EXPORT_TO_RETEK_PATH(ImportLogType.PRODUCT_IMAGE_EXPORT_TO_RETEK.getImportLogCode(),ResourceUtil.getSystemConfig().getProperty(
			"product_export_path")+File.separator+ConstantUtil.ARCHIVE),
	PRODUCT_MASTER_ACK_FROM_RETEK_PATH(ImportLogType.PRODUCT_MASTER_ACK_FROM_RETEK.getImportLogCode(),ResourceUtil.getSystemConfig().getProperty(
			"product.acknowledgement.path")+File.separator+ConstantUtil.ARCHIVE),
	PRODUCT_BARCODE_ACK_FROM_RETEK_PATH(ImportLogType.PRODUCT_BARCODE_ACK_FROM_RETEK.getImportLogCode(),ResourceUtil.getSystemConfig().getProperty(
			"product.acknowledgement.path")+File.separator+ConstantUtil.ARCHIVE),
	PRODUCT_IMAGE_ACK_FROM_RETEK_PATH(ImportLogType.PRODUCT_IMAGE_ACK_FROM_RETEK.getImportLogCode(),ResourceUtil.getSystemConfig().getProperty(
			"product.acknowledgement.path")+File.separator+ConstantUtil.ARCHIVE),
	PRODUCT_ACK_FROM_RETEK_ERROR_PATH(ImportLogType.PRODUCT_MASTER_ACK_FROM_RETEK.getImportLogCode(),ResourceUtil.getSystemConfig().getProperty(
			"product.acknowledgement.path")+File.separator+ConstantUtil.ERROR);
	
	
	private ImportLogPath(String importLogPathCode, String importLogPathDesc) {
		this.importLogPathCode = importLogPathCode;
		this.importLogPathDesc = importLogPathDesc;
	}
	public String getImportLogPathCode() {
		return importLogPathCode;
	}


	public String getImportLogPathDesc() {
		return importLogPathDesc;
	}
	private String importLogPathCode;
	private String importLogPathDesc;
	
	public static ImportLogPath getLogType(String importLogPathCode){
		for(ImportLogPath importLogPath : ImportLogPath.values()){
			if(importLogPath.getImportLogPathCode().equals(importLogPathCode)){
				return importLogPath;
			}
		}
		return null;
	}

}
