package com.mall.b2bp.populators.shop;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.models.shop.ShopIpModel;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.vos.shop.ShopIpViewVo;

public class ShopIpPopulator {
	public ShopIpViewVo converModelToVo(ShopIpModel shopIpModel){
		ShopIpViewVo vo = new ShopIpViewVo();
		vo.setId(shopIpModel.getId());
		vo.setShopId(shopIpModel.getShopId());
		vo.setCreatedBy(shopIpModel.getCreatedBy());
		vo.setCreatedDate(shopIpModel.getCreatedDate());
		vo.setLastUpdatedBy(shopIpModel.getLastUpdatedBy());
		vo.setLastUpdatedDate(shopIpModel.getLastUpdatedDate());
		vo.setExIncludeInd(shopIpModel.getExIncludeInd());
		
		if(StringUtils.isNotBlank(shopIpModel.getIpStartRange())) {
			String[] ipArr = shopIpModel.getIpStartRange().split("\\.");
			if(ipArr.length == 4) {
				vo.setIpStart1(Short.parseShort(ipArr[0]));
				vo.setIpStart2(Short.parseShort(ipArr[1]));
				vo.setIpStart3(Short.parseShort(ipArr[2]));
				vo.setIpStart4(Short.parseShort(ipArr[3]));
			}
			
		}
		
		if(StringUtils.isNotBlank(shopIpModel.getIpEndRange())) {
			String[] ipArr = shopIpModel.getIpEndRange().split("\\.");
			if(ipArr.length == 4) {
				vo.setIpEnd1(Short.parseShort(ipArr[0]));
				vo.setIpEnd2(Short.parseShort(ipArr[1]));
				vo.setIpEnd3(Short.parseShort(ipArr[2]));
				vo.setIpEnd4(Short.parseShort(ipArr[3]));
			}
			
		}
		return vo;
		
	}
	
	public ShopIpModel converVoToModel(ShopIpViewVo shopIpViewVo, SessionService sessionService){
		ShopIpModel model = new ShopIpModel();
		if(shopIpViewVo.getId() != null) {
			model.setId(shopIpViewVo.getId());
		}
		if(shopIpViewVo.getShopId() != null) {
			model.setShopId(shopIpViewVo.getShopId());
		}
		model.setCreatedBy(sessionService.getCurrentUser().getUserName());
		model.setCreatedDate(new Date());
		model.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
		model.setLastUpdatedDate(new Date());
		if(StringUtils.isNotBlank(shopIpViewVo.getExIncludeInd())) {
			model.setExIncludeInd(shopIpViewVo.getExIncludeInd());
		}
		if(	shopIpViewVo.getIpStart1() != null &&
			shopIpViewVo.getIpStart2() != null &&
			shopIpViewVo.getIpStart3() != null &&
			shopIpViewVo.getIpStart4() != null ) {
			StringBuffer ipStartRangeBf = new StringBuffer();
			ipStartRangeBf.append(shopIpViewVo.getIpStart1()).append(".");
			ipStartRangeBf.append(shopIpViewVo.getIpStart2()).append(".");
			ipStartRangeBf.append(shopIpViewVo.getIpStart3()).append(".");
			ipStartRangeBf.append(shopIpViewVo.getIpStart4());
			model.setIpStartRange(ipStartRangeBf.toString());
		}
		if(	shopIpViewVo.getIpEnd1() != null &&
				shopIpViewVo.getIpEnd2() != null &&
				shopIpViewVo.getIpEnd3() != null &&
				shopIpViewVo.getIpEnd4() != null ) {
				StringBuffer ipEndRangeBf = new StringBuffer();
				ipEndRangeBf.append(shopIpViewVo.getIpEnd1()).append(".");
				ipEndRangeBf.append(shopIpViewVo.getIpEnd2()).append(".");
				ipEndRangeBf.append(shopIpViewVo.getIpEnd3()).append(".");
				ipEndRangeBf.append(shopIpViewVo.getIpEnd4());
				model.setIpEndRange(ipEndRangeBf.toString());
			}
		return model;
	}
}
