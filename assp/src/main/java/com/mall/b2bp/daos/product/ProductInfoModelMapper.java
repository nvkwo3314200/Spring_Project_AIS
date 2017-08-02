package com.mall.b2bp.daos.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.product.ProductExportModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.vos.product.ProductParameterVo;
import com.mall.b2bp.vos.product.SubProductInfo;

public interface ProductInfoModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ProductInfoModel record);

    int insertSelective(ProductInfoModel record);


    ProductInfoModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(ProductInfoModel record);

    int updateByPrimaryKey(ProductInfoModel record);
    
    List<ProductInfoModel> getAll();
    
    int getMaxProductId();
    
//    @PostFilter("hasPermission(filterObject, 'read') or hasRole('ROLE_ADMIN')")
    List<ProductInfoModel> selectProductList(ProductParameterVo parameterVo);
    
//    @PostFilter("hasPermission(filterObject, 'read')")
    ProductInfoModel getProductInfoModelBySupplierProductCode(Map<String,Object> map);
    
	
	List<SubProductInfo> getSubProductListByBaseProductId(String baseProductId);
	
	ProductInfoModel getProductInfoModelByProductCode(Map<String,Object> map);
	
	List<ProductInfoModel> getExportProducts();

    int editSettingSyncProduct(ProductInfoModel record);
    
    List<ProductExportModel>  exportProductList(ProductParameterVo parameterVo);
    
    List<ProductInfoModel> getProductInfoModelBySupplierId(String supplierCode);
    
    ProductInfoModel getProductBySkuAndVersion(Map<String,Object> map);
    
    ProductInfoModel getSupplierProductInfoModelBySupplierId(String supplierCode);
    
    int deleteDeliveryFeeProductBySu(String supplierCode);

    List<ProductInfoModel> getProductBySupplierCode(ProductParameterVo parameterVo);
}
