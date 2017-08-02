package com.mall.b2bp.services.shop;

import com.mall.b2bp.vos.shop.CategoryListWsDTO;
import com.mall.b2bp.vos.shop.ShopSynVo;

public interface ShopSynService {

	public boolean uppdateShop(ShopSynVo vo);
	
	public CategoryListWsDTO getAllProduceCateloy();
	
	public CategoryListWsDTO getAllCategoriesForTreeGrid();
}
