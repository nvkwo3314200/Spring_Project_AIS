package com.mall.b2bp.services.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.system.UserLoginInfoMapper;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.services.system.UserLoginInfoService;
@Service("userLoginInfoService")
public class UserLoginInfoServiceImpl extends BaseService implements UserLoginInfoService{
	
	private UserLoginInfoMapper mapper;
	public UserLoginInfoMapper getMapper() {
		return mapper;
	}
	@Autowired
	public void setMapper(UserLoginInfoMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public List<UserInfoModel> search(UserInfoModel model){
		return mapper.searchUserLoginInfo(model);
	}
}
