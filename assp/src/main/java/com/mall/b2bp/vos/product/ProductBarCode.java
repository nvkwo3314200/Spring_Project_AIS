package com.mall.b2bp.vos.product;

import com.mall.b2bp.vos.BaseVo;

import java.math.BigDecimal;

public class ProductBarCode extends BaseVo {
	
	private BigDecimal id;
    private BigDecimal productId;
    private String failedReason;
    private String productCode;
    private String status;
    
    private boolean isHighLightBarCodeNum;
    
    private boolean isHighLightItemNumType;
    
    private String  primaryRefItemInd;
    
    private String actionItem;
    
    
	public BigDecimal getProductId() {
		return productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	private String barcodeNum;
	
	private String itemNumType;
	
	public String getBarcodeNum() {
		return barcodeNum;
	}

	public void setBarcodeNum(String barcodeNum) {
		this.barcodeNum = barcodeNum;
	}

	public String getItemNumType() {
		return itemNumType;
	}

	public void setItemNumType(String itemNumType) {
		this.itemNumType = itemNumType;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isHighLightBarCodeNum() {
		return isHighLightBarCodeNum;
	}

	public void setHighLightBarCodeNum(boolean isHighLightBarCodeNum) {
		this.isHighLightBarCodeNum = isHighLightBarCodeNum;
	}

	public boolean isHighLightItemNumType() {
		return isHighLightItemNumType;
	}

	public void setHighLightItemNumType(boolean isHighLightItemNumType) {
		this.isHighLightItemNumType = isHighLightItemNumType;
	}

	public String getPrimaryRefItemInd() {
		return primaryRefItemInd;
	}

	public void setPrimaryRefItemInd(String primaryRefItemInd) {
		this.primaryRefItemInd = primaryRefItemInd;
	}

	public String getActionItem() {
		return actionItem;
	}

	public void setActionItem(String actionItem) {
		this.actionItem = actionItem;
	}


}
