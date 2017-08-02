package com.mall.b2bp.vos.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderCancelRequestVo {
	
	@JsonProperty("cancelRequests")
	private List<CancelRequest> cancelRequests;

	public List<CancelRequest> getCancelRequests() {
		return cancelRequests;
	}

	public void setCancelRequests(List<CancelRequest> cancelRequests) {
		this.cancelRequests = cancelRequests;
	}

}
