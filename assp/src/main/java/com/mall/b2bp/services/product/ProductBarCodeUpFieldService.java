package com.mall.b2bp.services.product;

import java.util.List;

import com.mall.b2bp.models.product.ProductBarCodeUpFieldModel;


public interface ProductBarCodeUpFieldService {

    List<ProductBarCodeUpFieldModel> getProductBarCodeUpFieldModelById(String id);

    int deleteByProductId(String productId);


}
