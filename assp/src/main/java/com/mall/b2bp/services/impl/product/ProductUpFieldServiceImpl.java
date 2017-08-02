package com.mall.b2bp.services.impl.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.product.ProductUpFieldModelMapper;
import com.mall.b2bp.models.product.ProductUpFieldModel;
import com.mall.b2bp.services.product.ProductUpFieldService;

@Service("productUpFieldService")
public class ProductUpFieldServiceImpl implements ProductUpFieldService {
	private ProductUpFieldModelMapper productUpFieldModelMapper;

	public ProductUpFieldModelMapper getProductUpFieldModelMapper() {
		return productUpFieldModelMapper;
	}

	@Autowired
	public void setProductUpFieldModelMapper(
			ProductUpFieldModelMapper productUpFieldModelMapper) {
		this.productUpFieldModelMapper = productUpFieldModelMapper;
	}

	@Override
	public List<ProductUpFieldModel> getProductUpFieldModelByProductId(
			String productId) {
		return productUpFieldModelMapper.getProductUpFieldModelByProductId(productId);
	}

	@Override
	public int deleteByProductId(String productId) {

		return productUpFieldModelMapper.deleteByProductId(productId);
	}

	@Override
	public int deleteBySupplierId(String supplierCode) {
		return productUpFieldModelMapper.deleteBySupplierId(supplierCode);
	}



}
