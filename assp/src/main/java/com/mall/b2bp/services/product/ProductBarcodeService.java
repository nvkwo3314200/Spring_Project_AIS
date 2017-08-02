package com.mall.b2bp.services.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.product.ProductBarcodeModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductInfoVo;

public interface ProductBarcodeService {
	 int insert(ProductBarcodeModel record);
	
	 List<ProductBarcodeModel> getProductBarcodeModelByBarcodeNum(Map<String,Object> map);
	 
	 boolean validateProductBarCode(ProductInfoVo productInfoVo,ResponseData<ProductInfoVo> responseData);
	 
	 List<ProductBarcodeModel> getProductBarcodeModelsByProductId(String productId);
	 
	 void deleteProductBarcodeByProductId(String productId);
	 
	 int updateByPrimaryKeySelective(ProductBarcodeModel record);
	 
	 int deleteByPrimaryKey(BigDecimal id);
	 
	 
}
