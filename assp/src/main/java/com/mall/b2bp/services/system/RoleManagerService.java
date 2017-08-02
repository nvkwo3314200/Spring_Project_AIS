  package com.mall.b2bp.services.system;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.RoleModel;
import com.mall.b2bp.vos.ResponseData;

public interface RoleManagerService{
	public List<RoleModel> selectRoleList(RoleModel roleModel);
	
	public List<RoleModel> searchRole(RoleModel roleModel);
	
	public ResponseData<RoleModel> checkRoleSave(RoleModel roleVo) throws SystemException;
	
	public ResponseData<RoleModel> checkroleSave(RoleModel roleModel) throws SystemException;
	
	public boolean chack(RoleModel roleVo);
	
	public int addRole(RoleModel roleVo,ResponseData<RoleModel> responseData) throws ServiceException;
	
	public int insertRole(RoleModel roleVo);
	
	public int updateRole(RoleModel roleVo);
	
	public RoleModel getRoleViewVoById(Integer roleId);
	
	public List<RoleModel> searchUserRole(Integer roleId);
	
	public ResponseData<RoleModel> deleteByPrimaryKey(String ids) throws SystemException;
	
	public List<RoleModel> getRoleUserlist(Integer userId);
}
