package com.mall.b2bp.daos.system;

import java.util.List;

import com.mall.b2bp.models.system.MenuModel;
import com.mall.b2bp.models.system.PowerModel;
import com.mall.b2bp.models.system.UserInfoModel;

public interface UserManagerMapper {
    List<UserInfoModel> selectUserList(UserInfoModel userInfoVo);
    List<UserInfoModel> search(UserInfoModel userInfoVo);
    int insert(UserInfoModel userInfoVo);
    int update(UserInfoModel userInfoVo);
    int deleteByPrimaryKey(Integer id);
    int deleteUser(Integer id);
    Integer createExsitUser(UserInfoModel userInfoVo);
    
    //login by lh-6-29
    UserInfoModel selectUser(String userCode); 
    List<MenuModel> selectMenu(String userCode);
    List<PowerModel> getUserAllFuncPower(String userCode); //-9-8
    int saveUserLoginSuccess(UserInfoModel record); // create login data -16-10-12
    int updateUserLoginSuccess(UserInfoModel record); 
    int updateUserLoginFail(UserInfoModel record);
    
    //update the user selected language by Keven in July 20, 2016
    int updateUserLang(UserInfoModel model);
}