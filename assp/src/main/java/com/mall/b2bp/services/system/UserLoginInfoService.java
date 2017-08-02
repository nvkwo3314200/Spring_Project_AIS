package com.mall.b2bp.services.system;

import java.util.List;

import com.mall.b2bp.models.system.UserInfoModel;
public interface UserLoginInfoService{
	
	public List<UserInfoModel> search(UserInfoModel model);
	
}
