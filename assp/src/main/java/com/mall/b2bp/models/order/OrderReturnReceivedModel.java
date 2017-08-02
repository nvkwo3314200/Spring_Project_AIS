package com.mall.b2bp.models.order;

import java.math.BigDecimal;


public class OrderReturnReceivedModel {
	private BigDecimal id;
    private String orderId;
    private String returnId;
    private String returnUpdateDate;
    private String skuId;
    private String totalReturnQty;
    private String actualCollectedQty;
    private String writeOffQty;
    private String returnSkuRemark;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	public String getReturnUpdateDate() {
		return returnUpdateDate;
	}
	public void setReturnUpdateDate(String returnUpdateDate) {
		this.returnUpdateDate = returnUpdateDate;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getTotalReturnQty() {
		return totalReturnQty;
	}
	public void setTotalReturnQty(String totalReturnQty) {
		this.totalReturnQty = totalReturnQty;
	}
	public String getActualCollectedQty() {
		return actualCollectedQty;
	}
	public void setActualCollectedQty(String actualCollectedQty) {
		this.actualCollectedQty = actualCollectedQty;
	}
	public String getWriteOffQty() {
		return writeOffQty;
	}
	public void setWriteOffQty(String writeOffQty) {
		this.writeOffQty = writeOffQty;
	}
	public String getReturnSkuRemark() {
		return returnSkuRemark;
	}
	public void setReturnSkuRemark(String returnSkuRemark) {
		this.returnSkuRemark = returnSkuRemark;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
    
    
}