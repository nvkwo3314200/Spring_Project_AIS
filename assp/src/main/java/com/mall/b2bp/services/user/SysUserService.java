package com.mall.b2bp.services.user;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.vos.user.SysUserViewVo;

public interface SysUserService {
	int save (SysUserViewVo sysUserViewVo);
	int insert (SysUserViewVo sysUserViewVo);
	int delete (String ids) throws ServiceException;
	int update (SysUserViewVo sysUserViewVo);
	SysUserViewVo selectById(String id);
	List<SysUserViewVo> selectList(String id, String shopId, String userCode, String userName, String active);
	void changePassword(SysUserViewVo viewVo);
	
	
}
