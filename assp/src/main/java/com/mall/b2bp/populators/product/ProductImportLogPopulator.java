package com.mall.b2bp.populators.product;
import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.enums.ImportLogType;
import com.mall.b2bp.models.product.ProductImportLogModel;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.product.ProductImportLogVo;

public class ProductImportLogPopulator {
	
	public  ProductImportLogVo converProductImportModelToVo(ProductImportLogModel productImportLogModel){
		ProductImportLogVo productImportLogVo = new ProductImportLogVo();
		  productImportLogVo.setId(productImportLogModel.getId());
		  productImportLogVo.setFileName(productImportLogModel.getFileName());
		  productImportLogVo.setImportDate(productImportLogModel.getImportDate());
		  if(StringUtils.isNotEmpty(productImportLogModel.getImportType())){
		  productImportLogVo.setImportType(ImportLogType.getLogType(productImportLogModel.getImportType()).getImportLogDesc());
		  }
		  productImportLogModel.setCreatedBy(productImportLogModel.getCreatedBy());
		  productImportLogModel.setCreatedDate(productImportLogModel.getCreatedDate());
		  productImportLogVo.setLastUpdatedBy(productImportLogModel.getLastUpdatedBy());
		  productImportLogVo.setLastUpdatedDate(productImportLogModel.getLastUpdatedDate());
		  productImportLogVo.setMessage(productImportLogModel.getMessage());
          if(productImportLogModel.getImportDate() != null){
        	  productImportLogVo.setImportDateStr(DateUtils.formatDate(productImportLogModel.getImportDate(), DateUtils.DATE_FORMAT_7));
          }
          productImportLogVo.setImportCode(productImportLogModel.getImportType());
		return productImportLogVo;
	}

}
