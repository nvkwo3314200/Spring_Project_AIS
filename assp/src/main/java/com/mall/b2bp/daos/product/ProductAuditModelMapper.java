package com.mall.b2bp.daos.product;


import java.util.List;

import com.mall.b2bp.models.product.ProductAuditModel;
import com.mall.b2bp.vos.product.ProductAuditVo;

public interface ProductAuditModelMapper {
//    int deleteByPrimaryKey(BigDecimal id);
//
//    int insert(ProductAuditModel record);
//
//    int insertSelective(ProductAuditModel record);
//
//    ProductAuditModel selectByPrimaryKey(BigDecimal id);
//
//    int updateByPrimaryKeySelective(ProductAuditModel record);
//
//    int updateByPrimaryKey(ProductAuditModel record);
    
    List<ProductAuditModel> getAll();
    List<ProductAuditModel> viewHistory(ProductAuditVo productAuditVo);
    
    int deleteByProductId(String productId);
    int deleteBySupplierId(String supplierCode);
}