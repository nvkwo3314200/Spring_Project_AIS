package com.mall.b2bp.services.shop;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.models.shop.ShopIpModel;
import com.mall.b2bp.vos.shop.ShopIpViewVo;

public interface ShopIpService {
	int delete(String ids);

    int save(ShopIpViewVo record);

    ShopIpModel selectByPrimaryKey(BigDecimal id);

    List<ShopIpViewVo> selectListByShopIpViewVo (ShopIpViewVo record);
}
