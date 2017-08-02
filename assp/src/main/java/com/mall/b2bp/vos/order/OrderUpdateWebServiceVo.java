package com.mall.b2bp.vos.order;

import java.util.List;

/**
 *  TODO
 *  Created on 2016年8月22日.
 */
public class OrderUpdateWebServiceVo {
	
	private String code;
	
	private String status;
	
	private List<PickQtyVo> pickQtyList ;
	
	private String trackId;

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<PickQtyVo> getPickQtyList() {
		return pickQtyList;
	}

	public void setPickQtyList(List<PickQtyVo> pickQtyList) {
		this.pickQtyList = pickQtyList;
	}
	
	
	
	
}
