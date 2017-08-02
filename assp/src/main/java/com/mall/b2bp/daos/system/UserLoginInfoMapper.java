package com.mall.b2bp.daos.system;

import java.util.List;

import com.mall.b2bp.models.system.UserInfoModel;

public interface UserLoginInfoMapper {

	List<UserInfoModel> searchUserLoginInfo(UserInfoModel model);
}
