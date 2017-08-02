package com.mall.b2bp.services.user;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.user.UserModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.user.UserViewVo;
import com.mall.b2bp.vos.user.UserVo;

public interface UserService {
	UserVo selectUserByUserId(String userId);
    void cleanUserAuthToken(String id);
    void updateToken(String userId, String token);

    boolean sendForgetEmail(String email);
    boolean changePasswordByToken(String token,String password,ResponseData<UserVo> responseData);
    boolean changePassword(BigDecimal pkId,String password,ResponseData<UserVo> responseData) throws ServiceException;
    
    int updateByPrimaryKeySelective(UserModel record);
    
    UserModel selectByPrimaryKey(BigDecimal id);
    
    List<UserVo> selectUserList(String userId,String userName,String userRole,String email,String contactNo,String activate,String supplierName);
    
    int insertSelective(UserViewVo userVo);
    
    int deleteByPrimaryKey(String ids) throws ServiceException;
    
    UserViewVo getUserViewVoById(String id);
    
	 List<UserModel> getUserModeListByEmail(Map<String,Object> map);
    
    
    ResponseData<UserViewVo> checkUserSave(UserViewVo userViewVo)  throws SystemException;
   
    
    void insertSuppBrand(UserViewVo userViewVo);
    
    void insertSuppCategory(UserViewVo userViewVo);
    
    void insertDeptClass(UserViewVo userViewVo);
    
    void insertBrandAndDeptAndLov(UserViewVo userViewVo);
    
    void insertBrandAndCategoryAndDept(UserViewVo userViewVo);
    
//    void deleteBrandAndCategoryAndDept(String supplierId);
    
//    void updateBrandAndCategoryAndDept(UserViewVo userViewVo);
    
    UserModel converVoToModel(UserViewVo userVo);
    
    int insertSupplier(UserViewVo userViewVo);
    
//    int updateSupplier(UserViewVo userViewVo);
    
    void saveUser(UserViewVo userViewVo,ResponseData<UserViewVo> responseData) throws ServiceException;
    
    UserModel getUserBySupplierId(String supplierId);
    
    int updateUserForLoginSuccess(UserModel record) throws ServiceException;
    int updateUserForLoginFailTime(UserModel record) throws ServiceException;
    
}
