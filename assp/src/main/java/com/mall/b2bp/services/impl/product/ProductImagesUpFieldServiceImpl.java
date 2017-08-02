package com.mall.b2bp.services.impl.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.product.ProductImagesUpFieldModelMapper;
import com.mall.b2bp.models.product.ProductImagesUpFieldModel;
import com.mall.b2bp.services.product.ProductImagesUpFieldService;

@Service("productImagesUpFieldService")
public class ProductImagesUpFieldServiceImpl implements ProductImagesUpFieldService {
	private ProductImagesUpFieldModelMapper productImagesUpFieldModelMapper;

	public ProductImagesUpFieldModelMapper getProductImagesUpFieldModelMapper() {
		return productImagesUpFieldModelMapper;
	}

	@Autowired
	public void setProductImagesUpFieldModelMapper(
			ProductImagesUpFieldModelMapper productImagesUpFieldModelMapper) {
		this.productImagesUpFieldModelMapper = productImagesUpFieldModelMapper;
	}

	@Override
	public List<ProductImagesUpFieldModel> getProductImagesUpFieldModelByProductId(
			String productId) {
		return productImagesUpFieldModelMapper.getProductImagesUpFieldModelByProductId(productId);
	}

	@Override
	public int deleteByProductId(String productId) {

		return productImagesUpFieldModelMapper.deleteByProductId(productId);
	}



}
