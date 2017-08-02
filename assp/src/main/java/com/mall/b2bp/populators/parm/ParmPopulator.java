package com.mall.b2bp.populators.parm;

import java.util.Date;

import com.mall.b2bp.models.parm.ParmModel;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.vos.parm.ParmVo;

public class ParmPopulator {
	
	public ParmVo convertMToV(ParmModel model) {
		ParmVo vo = new ParmVo(model.getId(), model.getSegment(), model.getCode(), model.getShortDesc(), model.getLongDesc(), model.getMallId(), model.getDispSeq(), model.getValue());
		vo.setCreatedBy(model.getCreatedBy());
		vo.setCreatedDate(model.getCreatedDate());
		vo.setLastUpdatedBy(model.getLastUpdatedBy());
		vo.setLastUpdatedDate(model.getLastUpdatedDate());
		vo.setStartRow(model.getStartRow());
		vo.setEndRow(model.getEndRow());
		vo.setDataTotal(model.getDataTotal());
		return vo;
	}
	
	public ParmModel convertVToM(ParmVo vo, SessionService sessionService) {
		ParmModel model = new ParmModel(vo.getId(),vo.getSegment(),vo.getCode(),vo.getShortDesc(),vo.getLongDesc(),vo.getMallId(), vo.getDispSeq(), vo.getValue());
		
		if(sessionService.getCurrentUser() != null) {
			model.setCreatedBy(sessionService.getCurrentUser().getUserCode());
			model.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
		}
		model.setCreatedDate(new Date());
		model.setLastUpdatedDate(new Date());
		model.setStartRow(vo.getStartRow());
		model.setEndRow(vo.getEndRow());
		model.setDataTotal(vo.getDataTotal());
		return model;
	}
}
