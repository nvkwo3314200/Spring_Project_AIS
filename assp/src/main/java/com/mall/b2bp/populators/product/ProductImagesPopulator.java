package com.mall.b2bp.populators.product;

import com.mall.b2bp.models.product.ProductImagesModel;
import com.mall.b2bp.vos.product.ProductImagesVo;

public class ProductImagesPopulator {

	public ProductImagesVo converModelToVo(ProductImagesModel source){
		ProductImagesVo target=new ProductImagesVo();
		target.setId(source.getId());
		target.setProductId(source.getProductId());
		target.setFileName(source.getFileName());
		target.setFilePath(source.getFilePath());
		target.setImageType(source.getImageType());
		target.setDescription(source.getDescription());
		target.setSequence(source.getSequence());
		
		target.setCreatedBy(source.getCreatedBy());
		target.setCreatedDate(source.getCreatedDate());
		target.setLastUpdatedBy(source.getLastUpdatedBy());
		target.setLastUpdatedDate(source.getLastUpdatedDate());
		
		return target;
		
	}
	
	public ProductImagesModel converVoToModel(ProductImagesVo source){
		ProductImagesModel target=new ProductImagesModel();
		target.setProductId(source.getProductId());
		target.setFileName(source.getFileName());
		target.setFilePath(source.getFilePath());
		target.setImageType(source.getImageType());
		target.setDescription(source.getDescription());
		target.setId(source.getId());
		target.setSequence(source.getSequence());
		
		target.setCreatedBy(source.getCreatedBy());
		target.setCreatedDate(source.getCreatedDate());
		target.setLastUpdatedBy(source.getLastUpdatedBy());
		target.setLastUpdatedDate(source.getLastUpdatedDate());
		return target;
		
	}
}
