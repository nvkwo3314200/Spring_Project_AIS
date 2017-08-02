package com.mall.b2bp.daos.shop;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.models.shop.ShopIpModel;
import com.mall.b2bp.vos.shop.ShopIpViewVo;

public interface ShopIpModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ShopIpModel record);

    int insertSelective(ShopIpModel record);

    ShopIpModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(ShopIpModel record);

    int updateByPrimaryKey(ShopIpModel record);
    
    List<ShopIpModel> selectListByShopIpViewVo (ShopIpViewVo record);
}