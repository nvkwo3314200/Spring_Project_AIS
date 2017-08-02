package com.mall.b2bp.daos.product;

import java.util.List;

import com.mall.b2bp.models.product.ProductBarCodeUpFieldModel;

public interface ProductBarCodeUpFieldModelMapper {
	List<ProductBarCodeUpFieldModel> getProductBarCodeUpFieldModelById(String id);
	
	int deleteByProductId(String productId);
}