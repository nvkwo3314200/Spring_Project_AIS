package com.mall.b2bp.services.system;

import java.util.Map;

import com.mall.b2bp.models.system.PowerModel;
import com.mall.b2bp.models.system.UserInfoModel;

public interface SessionService {

	// login initCurrentUser
	public void initCurrentUser(UserInfoModel userInfo);
	
	// update user initCurrentUser
	public void initCurrentUser();
	
	// get userInfo in session
	public UserInfoModel getCurrentUser();
	
	//by lh(login) -8-12
	public UserInfoModel getCurrentUserInfo();

	public String getCurrentMenu(UserInfoModel user);
	
	// getCurrentUserPower : url
	public PowerModel getCurrentUserPower(String url);

	// initCurrentUserPower
	public void initCurrentUserPower();
	
	// initCurrentUserPower : parameter
	public void initCurrentUserPower(String userCode);
	
	//by lh -9-26
	public Map<String, PowerModel> getCurrentUserAllFuncPower(String userCode);
	
	public void setAttribute(String key, Object value);
	
	public Object getAttribute(String key);
	
	public void removeAttribute(String key);
	
	public void setSupplier(UserInfoModel user);
	
	public void setMall(UserInfoModel user);
	
}
