package com.mall.b2bp.services.product;

import java.io.File;

import com.mall.b2bp.exception.ServiceException;

public interface ProductImagesSyncService {

	void exportProductImage(File productImageFile) throws ServiceException;
	

}
