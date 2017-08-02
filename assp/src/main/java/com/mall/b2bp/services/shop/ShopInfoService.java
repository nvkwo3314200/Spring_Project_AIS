package com.mall.b2bp.services.shop;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.shop.ShopInfoModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.shop.CategoryWsDTO;
import com.mall.b2bp.vos.shop.ShopInfoViewVo;

public interface ShopInfoService {
	void saveShopInfo(ShopInfoViewVo shopInfoViewVo,ResponseData<ShopInfoViewVo> responseData) throws ServiceException;
	
	int deleteByPrimaryKey(String ids, ResponseData<ShopInfoViewVo> responseData) throws ServiceException;
	
	ShopInfoModel selectByPrimaryKey(BigDecimal id) throws ServiceException;
	
	ShopInfoViewVo selectInterfaceByKey(String id, String path) throws ServiceException;
	
	List<ShopInfoModel> selectShopList(String shopId, String mallId,String shopCode, String shopName, String respPerson, String contactPerson, String contactEmail, String websiteName, String telNo) throws ServiceException;
	
	ShopInfoViewVo getShopViewVoById(String id) throws ServiceException;
	
	int updateShopInfo(ShopInfoViewVo shopInfoViewVo,ResponseData<ShopInfoViewVo> responseData) throws ServiceException;
	
	boolean checkExist(ShopInfoViewVo shopInfoViewVo); 
	
	boolean validateIP(UserInfoModel user);
	
	List<ShopInfoViewVo> search(ShopInfoViewVo vo);
	
	List<CategoryWsDTO> getCategoryList();
	
	List<ShopInfoModel> getAllShop() throws ServiceException;
}
