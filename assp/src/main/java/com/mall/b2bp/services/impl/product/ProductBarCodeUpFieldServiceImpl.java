package com.mall.b2bp.services.impl.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.product.ProductBarCodeUpFieldModelMapper;
import com.mall.b2bp.models.product.ProductBarCodeUpFieldModel;
import com.mall.b2bp.services.product.ProductBarCodeUpFieldService;

@Service("productBarCodeUpFieldService")
public class ProductBarCodeUpFieldServiceImpl implements ProductBarCodeUpFieldService {
	private ProductBarCodeUpFieldModelMapper productBarCodeUpFieldModelMapper;

	public ProductBarCodeUpFieldModelMapper getProductBarCodeUpFieldModelMapper() {
		return productBarCodeUpFieldModelMapper;
	}

	@Autowired
	public void setProductBarCodeUpFieldModelMapper(
			ProductBarCodeUpFieldModelMapper productBarCodeUpFieldModelMapper) {
		this.productBarCodeUpFieldModelMapper = productBarCodeUpFieldModelMapper;
	}

	@Override
	public List<ProductBarCodeUpFieldModel> getProductBarCodeUpFieldModelById(
			String id) {
		return productBarCodeUpFieldModelMapper.getProductBarCodeUpFieldModelById(id);
	}

	@Override
	public int deleteByProductId(String productId) {
		
		return productBarCodeUpFieldModelMapper.deleteByProductId(productId);
	}
	
	



}
