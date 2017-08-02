package com.mall.b2bp.daos.brand;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.vos.brand.BrandVo;

public interface BrandModelMapper {
    int deleteByPrimaryKey(BigDecimal brandCode);

    int insert(BrandModel record);

    int insertSelective(BrandModel record);

    BrandModel selectByPrimaryKey(BigDecimal brandCode);

    int updateByPrimaryKeySelective(BrandModel record);
    int updateWatsonsMallInd(BrandModel record);

    int updateByPrimaryKey(BrandModel record);
    
    List<BrandModel> view(BrandVo brandVo);
    List<BrandModel> view_brand_supplier(BrandVo brandVo);
    List<BrandModel> selectByPk(BrandVo brandVo);
    
    List<BrandModel> getBrandsBySupplierId(String supplierId);
    
    List<BrandModel>  selectBrandList(BrandVo vo);
    
    List<BrandModel> selectAllBrandList();
    
    List<BrandModel> getSelectSupplierBrandByUserId(BigDecimal id);
    
    List<BrandModel> getUnSelectSupplierBrandByUserId(BigDecimal id);
    
    List<BrandModel> getBrandModelByDescEn(String descEn);
    
    List<BrandModel> getAllBrandsBySupplierId(String supplierId);
    
    
   
}