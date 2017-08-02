package com.mall.b2bp.services.product;

import java.io.File;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.vos.ErrorLog;


public interface ProductSyncService {
	void exportProductMaster(File productMasterFile);

	
	boolean importProductMasterFromRetek(String fileName);
	
	boolean importProductBarcodeFromRetek(String fileName,ErrorLog errorLog);
	
	boolean importProductImagesFromRetek(String fileName,ErrorLog errorLog);

	boolean importProductMastor(String string, ErrorLog errorLog)throws ServiceException;
}
