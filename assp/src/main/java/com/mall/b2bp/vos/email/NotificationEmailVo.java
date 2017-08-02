package com.mall.b2bp.vos.email;


public class NotificationEmailVo {

	private String emailType;
	private String supplierId;
	private String hybridOrderId;
	private String orderReturnId;
	
	
	
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	
	
	public String getOrderReturnId() {
		return orderReturnId;
	}
	public void setOrderReturnId(String orderReturnId) {
		this.orderReturnId = orderReturnId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getHybridOrderId() {
		return hybridOrderId;
	}
	public void setHybridOrderId(String hybridOrderId) {
		this.hybridOrderId = hybridOrderId;
	}
}
