package com.mall.b2bp.populators.mall;

import java.util.Date;

import com.mall.b2bp.models.mall.MallModel;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.vos.mall.MallVo;

public class MallPopulator {
	
	public MallModel convertVToM(MallVo vo, SessionService sessionService) {
		MallModel model = new MallModel(vo.getId(), vo.getCode(), vo.getName());
		model.setCreatedDate(new Date());
		model.setLastUpdatedDate(new Date());
		if(sessionService.getCurrentUser() != null) {
			model.setCreatedBy(sessionService.getCurrentUser().getUserName());
			model.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
		}
		model.setStartRow(vo.getStartRow());
		model.setEndRow(vo.getEndRow());
		model.setDataTotal(vo.getDataTotal());
		return model;
	}
	
	public MallVo convertMToV(MallModel model) {
		MallVo vo = new MallVo(model.getId(), model.getCode(), model.getName());
		vo.setCreatedBy(model.getCreatedBy());
		vo.setCreatedDate(model.getCreatedDate());
		vo.setLastUpdatedBy(model.getLastUpdatedBy());
		vo.setLastUpdatedDate(model.getLastUpdatedDate());
		vo.setStartRow(model.getStartRow());
		vo.setEndRow(model.getEndRow());
		vo.setDataTotal(model.getDataTotal());
		return vo;
	}
}
