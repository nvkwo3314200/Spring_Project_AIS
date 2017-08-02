package com.mall.b2bp.daos.product;

import com.mall.b2bp.models.product.ProductImportLogModel;
import com.mall.b2bp.vos.product.ProductImportLogVo;

import java.math.BigDecimal;
import java.util.List;

public interface ProductImportLogModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ProductImportLogModel record);

    int insertSelective(ProductImportLogModel record);

    ProductImportLogModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(ProductImportLogModel record);

    int updateByPrimaryKey(ProductImportLogModel record);
    
    List<ProductImportLogModel> getAllProductImportLog(ProductImportLogVo productImportLogVo);
}