package com.mall.b2bp.daos.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.product.ProductImagesModel;

public interface ProductImagesModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ProductImagesModel record);

    int insertSelective(ProductImagesModel record);

    ProductImagesModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(ProductImagesModel record);

    int updateByPrimaryKey(ProductImagesModel record);

	Integer getMaxProductImagesId();
	
	List<ProductImagesModel> getProductImagesByProductId(Map map);
	
	List<ProductImagesModel> getProductImagesBySkutId(Map map);
	
	int deleteByProductId(BigDecimal id);
	
	ProductImagesModel getProductImagesByProductCode(Map map);
	
	List<ProductImagesModel> getExportImages();
	
	ProductImagesModel getProductImagesByProductIdSequence(Map<String , Object> map);
	
}