package com.mall.b2bp.daos.supplier;

import com.mall.b2bp.models.supplier.SupplierCategoryModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SupplierCategoryModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(SupplierCategoryModel record);

    int insertSelective(SupplierCategoryModel record);

    SupplierCategoryModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(SupplierCategoryModel record);

    int updateByPrimaryKey(SupplierCategoryModel record);
    
    int deleteBySupplierId(String suppilerId);
    
    SupplierCategoryModel selectBySupplierCategoryId(Map<String,Object> map);

    int insertCategoryBatch(List<SupplierCategoryModel> models);
    BigDecimal selectNextId();
    List<SupplierCategoryModel> selectBySupplierId(int id);
}