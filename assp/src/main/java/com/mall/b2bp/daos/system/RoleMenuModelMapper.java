package com.mall.b2bp.daos.system;

import java.util.List;

import com.mall.b2bp.models.system.RoleMenuModel;

public interface RoleMenuModelMapper {
	List <RoleMenuModel> selectMenuModelList(Integer id);
	List<RoleMenuModel> selectlist(RoleMenuModel menuModel);
	int creatRolePermission(RoleMenuModel menuModel);
	int updateRolePermission(RoleMenuModel menuModel);
	int deleteRoleMenu(Integer roleId);
	int deleteRole(Integer funcId);
}

