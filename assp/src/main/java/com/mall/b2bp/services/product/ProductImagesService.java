package com.mall.b2bp.services.product;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductImagesVo;
import com.mall.b2bp.vos.product.ProductInfoVo;

public interface ProductImagesService {
    int deleteByPrimaryKey(BigDecimal id);
    List<ProductImagesVo> getProductImagesByProductId(BigDecimal productId,String imageType);
    List<ProductImagesVo> getProductImagesBySkutId(String productId,String imageType);
    boolean addAllProductImages(String createdBy,BigDecimal productId,List<ProductImagesVo> productImages,String imagType);
    int deleteByProductId(BigDecimal id);
    boolean validateImages(BigDecimal productId,List<ProductImagesVo> list,ResponseData<ProductInfoVo> responseData);
    boolean validateSwatchImages(BigDecimal productId,List<ProductImagesVo> list,ResponseData<ProductInfoVo> responseData);
   
}
