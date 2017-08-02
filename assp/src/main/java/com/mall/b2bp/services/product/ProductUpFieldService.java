package com.mall.b2bp.services.product;

import java.util.List;

import com.mall.b2bp.models.product.ProductUpFieldModel;


public interface ProductUpFieldService {

    List<ProductUpFieldModel> getProductUpFieldModelByProductId(String productId);

    int deleteByProductId(String productId);
    int deleteBySupplierId(String supplierCode);


}
