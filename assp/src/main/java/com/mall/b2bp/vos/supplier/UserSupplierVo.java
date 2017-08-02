package com.mall.b2bp.vos.supplier;

/**
 * Created by USER on 2016/3/10.
 */
public class UserSupplierVo {

	private String supplierId;
	private String userName;
	
	 private boolean ticked;
	 
	public boolean isTicked() {
		return ticked;
	}
	public void setTicked(boolean ticked) {
		this.ticked = ticked;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
