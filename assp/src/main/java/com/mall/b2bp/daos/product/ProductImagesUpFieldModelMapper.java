package com.mall.b2bp.daos.product;

import java.util.List;

import com.mall.b2bp.models.product.ProductImagesUpFieldModel;

public interface ProductImagesUpFieldModelMapper {

	List<ProductImagesUpFieldModel> getProductImagesUpFieldModelByProductId(String productId);
	
	int deleteByProductId(String productId);
}