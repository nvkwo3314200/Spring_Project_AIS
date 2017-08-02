  package com.mall.b2bp.services.system;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.MenuModel;
import com.mall.b2bp.models.system.PowerModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.vos.ResponseData;

public interface UserManagerService {
	
	public List<UserInfoModel> selectUserList(UserInfoModel searchUserVo) throws ServiceException;
	
	public List<UserInfoModel> search(UserInfoModel searchUserVo) throws ServiceException;
	
	public boolean chack(UserInfoModel userInfoModel);
	
	public boolean checkUserSave(UserInfoModel userInfoVo,ResponseData<UserInfoModel> responseData) throws SystemException;

	
	public void addUser(UserInfoModel userInfoVo,ResponseData<UserInfoModel> responseData) throws ServiceException;
	
	public int insertUser(UserInfoModel userVo);
	

	public int updateUser(UserInfoModel userVo);
	
	public UserInfoModel getUserViewVoById(Integer userId);

	public int deleteByPrimaryKey(String ids);
	
	//login	by lh-6-29
	public UserInfoModel getUserInfo(String userCode);

	//login	by lh-7-1
	public List<MenuModel> getCurrentMenu(String userCode);
	
	//login	by lh-9-8
	public List<PowerModel> getUserAllFuncPower(String userCode);
	
	//login	by lh-6-30 PS:Update Login successful information
	public int updateUserLoginSuccess(UserInfoModel record) throws ServiceException;
	
	//login	by lh-10-12 PS:Save Login successful data(static)
	//public static int saveUserLoginSuccess(UserInfoModel record) throws ServiceException;
	
	//login	by lh-6-30 PS:Update Login fail information
	public int updateUserLoginFail(UserInfoModel record) throws ServiceException;
	
	public void changePassword (UserInfoModel userInfoVo);
	

	public ResponseData<UserInfoModel> checkUserRoleSave(UserInfoModel userInfoVo) throws SystemException;

	
	public void userRoleListSaveAll(UserInfoModel userInfoVo);
	
	public ResponseData<UserInfoModel> deletebufferRoles(UserInfoModel userInfoVo) throws SystemException;
	
	//update the user selected language by Keven in July 20, 2016
	public int updateUserLang(UserInfoModel model) throws ServiceException;
	
}
