package com.mall.b2bp.vos.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by USER on 2016/5/18.
 */
public class CancelResponse {
	
	@JsonProperty("orderId")
	private String orderId;
	@JsonProperty("pickOrderId")
	private String pickOrderId;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("errorMessage")
	private String errorMessage;
	@JsonProperty("orderConsignmentStatus")
	private String orderConsignmentStatus;

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPickOrderId() {
		return pickOrderId;
	}
	public void setPickOrderId(String pickOrderId) {
		this.pickOrderId = pickOrderId;
	}
	public String getOrderConsignmentStatus() {
		return orderConsignmentStatus;
	}
	public void setOrderConsignmentStatus(String orderConsignmentStatus) {
		this.orderConsignmentStatus = orderConsignmentStatus;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
