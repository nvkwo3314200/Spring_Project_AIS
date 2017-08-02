package com.mall.b2bp.services.system;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.MenuModel;
import com.mall.b2bp.vos.ResponseData;

@Service("menuService")
public interface MenuService{

	public List<MenuModel> search(MenuModel model) throws ServiceException;

	public List<MenuModel> searchMenu(MenuModel model) throws ServiceException;
	
	public ResponseData<MenuModel> checkMenuSave(MenuModel menuModel) throws SystemException; 
	
	public List<MenuModel> getMenuName(MenuModel model) throws SystemException, ServiceException;
	
	public ResponseData<MenuModel> checkMenuUpdateSave(MenuModel menuModel) throws SystemException;
	
	public boolean chack(MenuModel menuModel)throws ServiceException;
	
	public int update(MenuModel model) throws ServiceException;

	public int delete(MenuModel model) throws ServiceException;

	public int insert(MenuModel model) throws ServiceException;
	
	public List<MenuModel> selectmenu(MenuModel model) throws ServiceException;
}
