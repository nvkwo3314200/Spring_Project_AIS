package com.mall.b2bp.daos.supplier;

import com.mall.b2bp.models.supplier.SupplierSubModel;

import java.math.BigDecimal;

public interface SupplierSubModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(SupplierSubModel record);

    int insertSelective(SupplierSubModel record);

    SupplierSubModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(SupplierSubModel record);

    int updateByPrimaryKey(SupplierSubModel record);
    
    int deleteBySupplierId(String supplierId);
}