package com.mall.b2bp.daos.system;

import java.util.List;

import com.mall.b2bp.models.system.RoleModel;

public interface RoleManagerMapper {
    List<RoleModel> selectRoleList(RoleModel roleVo);
    List<RoleModel> searchRole(RoleModel roleVo);
    int insert(RoleModel roleVo);
    int update(RoleModel roleVo);
    int deleteByPrimaryKey(Integer id);
    List<RoleModel> getRoleUserlist(Integer userId);
    List<RoleModel> searchUserRole(Integer roleid);
}