package com.mall.b2bp.populators.user;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.models.user.SysUserModel;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.vos.user.SysUserViewVo;

public class SysUserPopulator {
	
	public SysUserViewVo converModelToVo(SysUserModel sysUserModel){
		SysUserViewVo sysUserViewVo = new SysUserViewVo();
		sysUserViewVo.setId(sysUserModel.getId());
		sysUserViewVo.setUserCode(sysUserModel.getUserCode());
		sysUserViewVo.setUserName(sysUserModel.getUserName());
		sysUserViewVo.setShopId(sysUserModel.getShopId());
		sysUserViewVo.setActiveInd(sysUserModel.getActiveInd());
		sysUserViewVo.setCreatedBy(sysUserModel.getCreatedBy());
		sysUserViewVo.setCreatedDate(sysUserModel.getCreatedDate());
		sysUserViewVo.setLastUpdatedBy(sysUserModel.getLastUpdatedBy());
		sysUserViewVo.setLastUpdatedDate(sysUserModel.getLastUpdatedDate());
		return sysUserViewVo;
	}
	
	public SysUserModel converVoToModel(SysUserViewVo sysUserViewVo, SessionService sessionService) {
		SysUserModel sysUserModel = new SysUserModel();
		if(sysUserViewVo.getId() != null) {
			sysUserModel.setId(sysUserViewVo.getId());
		}
		if(StringUtils.isNotEmpty(sysUserViewVo.getUserCode())) {
			sysUserModel.setUserCode(sysUserViewVo.getUserCode());
		}
		if(StringUtils.isNotEmpty(sysUserViewVo.getUserName())) {
			sysUserModel.setUserName(sysUserViewVo.getUserName());
		}
		if(sysUserViewVo.getShopId() != null) {
			sysUserModel.setShopId(sysUserViewVo.getShopId());
		}
		if(StringUtils.isNotEmpty(sysUserViewVo.getActiveInd())) {
			sysUserModel.setActiveInd(sysUserViewVo.getActiveInd());
		}
		if(StringUtils.isNotEmpty(sysUserViewVo.getPassword())) {
			sysUserModel.setPassword(sysUserViewVo.getPassword());
		}
		sysUserModel.setCreatedBy(sessionService.getCurrentUser().getUserId());
		sysUserModel.setCreatedDate(new Date());
		sysUserModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
		sysUserModel.setLastUpdatedDate(new Date());
		return sysUserModel;
	}
}
