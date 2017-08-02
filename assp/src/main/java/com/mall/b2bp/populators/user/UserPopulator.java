package com.mall.b2bp.populators.user;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.mall.b2bp.daos.user.UserModelMapper;
import com.mall.b2bp.models.user.UserModel;
import com.mall.b2bp.services.system.SessionService;
//import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.vos.user.UserViewVo;
import com.mall.b2bp.vos.user.UserVo;

public class UserPopulator {
	
	public  UserVo converModelToVo(UserModel userModel){
		UserVo userVo = new UserVo();
		userVo.setId(userModel.getId());
		userVo.setUserId(userModel.getUserId());
		userVo.setUserName(userModel.getUserName());
		userVo.setUserRole(userModel.getUserRole());
		userVo.setPassword(userModel.getPassword());
		userVo.setSupplierId(userModel.getSupplierId());
		userVo.setActivate(userModel.getActivate());
		userVo.setDeactivateDate(userModel.getDeactivateDate());
		userVo.setCreatedBy(userModel.getCreatedBy());
		userVo.setCreatedDate(userModel.getCreatedDate());
		userVo.setLastUpdatedBy(userModel.getLastUpdatedBy());
		userVo.setLastUpdatedDate(userModel.getLastUpdatedDate());
		userVo.setCreatedDate(userModel.getCreatedDate());	
		userVo.setToken(userModel.getToken());
		userVo.setEmail(userModel.getEmail());
		userVo.setContactNo(userModel.getContactNo());
		userVo.setSupplierName(userModel.getSupplierName());
		userVo.setSessionLang(userModel.getSessionLang());
		userVo.setLastLoginDate(userModel.getLastLoginDate());
		
		userVo.setLoginFailTimes(userModel.getLoginFailTimes());
		return userVo;
	}
	
	public UserModel converVoToModel(UserViewVo userVo,SessionService sessionService,UserModelMapper userModelMapper){
		UserModel userModel = new UserModel();
		if(userVo.getId() != null){
			userModel.setId(userVo.getId());
		}
		if(StringUtils.isNotEmpty(userVo.getUserId())){
			userModel.setUserId(userVo.getUserId());
		}
		
		if(StringUtils.isNotEmpty(userVo.getUserName())){
		userModel.setUserName(userVo.getUserName());
		}
		if(StringUtils.isNotEmpty(userVo.getUserRole())){
		userModel.setUserRole(userVo.getUserRole());
		}
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		if(userVo.getId() != null){
			 UserModel usModel = userModelMapper.selectByPrimaryKey(userVo.getId());
			 if(usModel != null && StringUtils.isNotEmpty(usModel.getPassword()) && StringUtils.isNotEmpty(userVo.getPassword()) && !usModel.getPassword().equals(userVo.getPassword())){
			    userModel.setPassword(md5PasswordEncoder.encodePassword(userVo.getPassword(), userVo.getUserId()));
			 }
		}else{
			if(StringUtils.isNotEmpty(userVo.getPassword())){
				userModel.setPassword(md5PasswordEncoder.encodePassword(userVo.getPassword(), userVo.getUserId()));
			}
		}
		if(StringUtils.isNotEmpty(userVo.getSupplierId())){
		 userModel.setSupplierId(userVo.getSupplierId());
		}
		if(StringUtils.isNotEmpty(userVo.getActivate())){
		userModel.setActivate(userVo.getActivate());
		}
		userModel.setCreatedBy(sessionService.getCurrentUser().getUserName());
		userModel.setCreatedDate(new Date());
		userModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
		userModel.setLastUpdatedDate(new Date());
		if(StringUtils.isNotEmpty(userVo.getEmail())){
		userModel.setEmail(userVo.getEmail());
		}
		if(StringUtils.isNotEmpty(userVo.getContactNo())){
		userModel.setContactNo(userVo.getContactNo());
		}
		userModel.setLoginFailTimes(new BigDecimal(0));
		
		
		return userModel;
	}
	
	public UserViewVo converModelToUserViewVo(UserModel userModel,SessionService sessionService){
        UserViewVo userVo = new UserViewVo();
        if(userModel.getId() != null){
        userVo.setId(userModel.getId());
        }
		if(StringUtils.isNotEmpty(userModel.getUserId())){
			userVo.setUserId(userModel.getUserId());
		}
		if(StringUtils.isNotEmpty(userModel.getUserName())){
			userVo.setUserName(userModel.getUserName());
		}
		if(StringUtils.isNotEmpty(userModel.getUserRole())){
			userVo.setUserRole(userModel.getUserRole());
		}
		if(StringUtils.isNotEmpty(userModel.getPassword())){
			userVo.setPassword(userModel.getPassword());
		}
		if(userModel.getSupplierId() != null){
			userVo.setSupplierId(userModel.getSupplierId());
		}
		if(StringUtils.isNotEmpty(userModel.getActivate())){
			userVo.setActivate(userModel.getActivate());
		}
		userVo.setCreatedBy(sessionService.getCurrentUser().getUserName());
		userVo.setCreatedDate(new Date());
		userVo.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
		userVo.setLastUpdatedDate(new Date());
		if(StringUtils.isNotEmpty(userModel.getEmail())){
			userVo.setEmail(userModel.getEmail());
		}
		if(StringUtils.isNotEmpty(userModel.getContactNo())){
			userVo.setContactNo(userModel.getContactNo());
		}
		return userVo;
	}

}
