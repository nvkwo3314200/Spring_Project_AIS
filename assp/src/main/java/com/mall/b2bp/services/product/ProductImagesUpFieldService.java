package com.mall.b2bp.services.product;

import java.util.List;

import com.mall.b2bp.models.product.ProductImagesUpFieldModel;


public interface ProductImagesUpFieldService {

    List<ProductImagesUpFieldModel> getProductImagesUpFieldModelByProductId(String productId);
    
    int deleteByProductId(String productId);

    

}
