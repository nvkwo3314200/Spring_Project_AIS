package com.mall.b2bp.vos.lov;

import java.math.BigDecimal;
import java.util.List;

public class LovListVo {

	private List<LovVo> lovList;
	private BigDecimal lovId;
	

	public BigDecimal getLovId() {
		return lovId;
	}

	public void setLovId(BigDecimal lovId) {
		this.lovId = lovId;
	}

	public List<LovVo> getLovList() {
		return lovList;
	}

	public void setLovList(List<LovVo> lovList) {
		this.lovList = lovList;
	}
	
	
}
