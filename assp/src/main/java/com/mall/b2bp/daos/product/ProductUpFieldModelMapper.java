package com.mall.b2bp.daos.product;

import java.util.List;

import com.mall.b2bp.models.product.ProductUpFieldModel;

public interface ProductUpFieldModelMapper {
	List<ProductUpFieldModel> getProductUpFieldModelByProductId(String productId);
	
	int deleteByProductId(String productId);
	int deleteBySupplierId(String supplierCode);
}