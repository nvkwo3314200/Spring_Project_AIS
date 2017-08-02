package com.mall.b2bp.daos.system;

import com.mall.b2bp.models.system.RoleModel;

public interface UserRoleManagerMapper {
	int userOfRoleId();
    int insert(RoleModel roleModel);
    int update(RoleModel roleModel);
    int delete(RoleModel roleModel);
}