package com.mall.b2bp.vos.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by USER on 2016/5/18.
 */
public class CancelRequest {
	
	@JsonProperty("orderId")
	private String orderId;
	
	@JsonProperty("pickOrderId")
	private String pickOrderId;
	
	@JsonProperty("orderConsignmentStatus")
	private String orderConsignmentStatus;
	
	@JsonProperty("lastUpdateDate")
	private String lastUpdateDate;
	
	@JsonProperty("lastUpdateBy")
	private String lastUpdateBy;
	
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
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
}
