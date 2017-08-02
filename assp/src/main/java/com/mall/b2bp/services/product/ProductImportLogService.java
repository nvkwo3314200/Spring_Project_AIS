package com.mall.b2bp.services.product;
import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.product.ProductImportLogModel;
import com.mall.b2bp.vos.product.ProductImportLogVo;

public interface ProductImportLogService {

	 List<ProductImportLogVo> getAllProductImportLog(String importType,String importDateFrStr,String importDateToStr) throws ServiceException;
	 
	 void insertProductImportLog(ProductImportLogModel productImportLogModel);
}
