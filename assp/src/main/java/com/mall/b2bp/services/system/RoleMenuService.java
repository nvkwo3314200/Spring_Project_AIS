package com.mall.b2bp.services.system;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.system.RoleMenuModel;

public interface RoleMenuService{
	
	public List<RoleMenuModel> selectMenuModelList(Integer id) throws ServiceException;
	
	public List<RoleMenuModel> selectList(RoleMenuModel menuModel) throws ServiceException;
	
	public int creatRolePermission(RoleMenuModel menuModel)throws ServiceException;
	
	public int deleteRoleMenu(Integer roleId)throws ServiceException;
	
	public int deleteRole(Integer funcId)throws ServiceException;
	
	public int updateRolePermission(RoleMenuModel menuModel) throws ServiceException;
	
}
