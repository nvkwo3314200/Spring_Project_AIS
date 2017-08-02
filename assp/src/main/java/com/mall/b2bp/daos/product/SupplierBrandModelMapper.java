package com.mall.b2bp.daos.product;

import com.mall.b2bp.models.product.SupplierBrandModel;

public interface SupplierBrandModelMapper {
    int deleteByPrimaryKey(Short id);

    int insert(SupplierBrandModel record);

    int insertSelective(SupplierBrandModel record);

    SupplierBrandModel selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(SupplierBrandModel record);

    int updateByPrimaryKey(SupplierBrandModel record);
    
    int deleteBySupplierId(String supplierId);
}