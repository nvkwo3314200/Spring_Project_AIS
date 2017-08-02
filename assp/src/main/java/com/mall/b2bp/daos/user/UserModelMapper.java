package com.mall.b2bp.daos.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.user.UserModel;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.user.SupplierLovVo;
import com.mall.b2bp.vos.user.UserViewVo;
import com.mall.b2bp.vos.user.UserVo;

public interface UserModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);
    
    UserModel selectUserByUserId(String userId); 
    
    int updateTokenByUserId(UserModel record);
    UserModel forgetPassword(HashMap<String, String> map);
    
    List<UserModel> selectUserList(UserVo userVo);
    
    List<UserModel> getUserModeListByEmail(Map<String,Object> map);
    
    List<UserModel> selectUserByUserEmail(String email);
    
    List<SupplierLovVo> getSupplierLovByUserId(BigDecimal id);
     
    List<UserModel> getAllUserBySupplierId(UserViewVo userViewVo);
    
    UserModel getUserBySupplierId(String supplierId);
    
    List<DeptVo> getAllDeptByDesc(String description);
    
    
    int updateUserForLoginSuccess(UserModel record);
    int updateUserForLoginFailTime(UserModel record);
    
}