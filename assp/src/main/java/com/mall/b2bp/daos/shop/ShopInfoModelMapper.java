package com.mall.b2bp.daos.shop;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.models.shop.ShopInfoModel;
import com.mall.b2bp.vos.shop.ShopInfoViewVo;

public interface ShopInfoModelMapper {
	int insert(ShopInfoModel record);
	
	int insertSelective(ShopInfoModel record);
	
	int deleteByPrimaryKey(BigDecimal id);
	
	ShopInfoModel selectByPrimaryKey(BigDecimal id);
	
	List<ShopInfoModel> selectShopList(ShopInfoViewVo vo);
	
	int updateByPrimaryKeySelective(ShopInfoModel record);
	
	ShopInfoModel selectInterfaceByKey(BigDecimal id);
	
	List<ShopInfoModel> selectIdList(ShopInfoViewVo vo);
	
	List<ShopInfoModel> search(ShopInfoViewVo vo);
}
