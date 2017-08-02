package com.mall.b2bp.vos.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderCancelReponseVo {
	
	@JsonProperty("cancelResponses")
	private List<CancelResponse> cancelResponses;

	public List<CancelResponse> getCancelResponses() {
		return cancelResponses;
	}

	public void setCancelResponses(List<CancelResponse> cancelResponses) {
		this.cancelResponses = cancelResponses;
	}
}
