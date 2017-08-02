package com.mall.b2bp.daos.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.product.ProductBarcodeModel;

public interface ProductBarcodeModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ProductBarcodeModel record);

    int insertSelective(ProductBarcodeModel record);

    ProductBarcodeModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(ProductBarcodeModel record);

    int updateByPrimaryKey(ProductBarcodeModel record);
    
    List<ProductBarcodeModel> getAll();
    
    List<ProductBarcodeModel> getProductBarcodeModelByBarcodeNum(Map<String,Object> map);
    
    List<ProductBarcodeModel> getProductBarcodeModelsByProductId(String productId);
    
    void deleteProductBarcodeByProductId(String productId);
    
    ProductBarcodeModel getProductBarcodeByProductCode(Map map);
    
    List<ProductBarcodeModel> getExportBarcodes();
    
}