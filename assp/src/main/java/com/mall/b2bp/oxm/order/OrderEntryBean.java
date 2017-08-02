package com.mall.b2bp.oxm.order;

import java.math.BigDecimal;

public class OrderEntryBean {
	private BigDecimal seqNum;
	private String skuId;
	private String skuType;
	private String brand;
	private String brandSec;
	private String productName;
	private String productNameSec;
	private BigDecimal qty;
	private String sizeDesc;
	private BigDecimal unitPrice;
	private BigDecimal skuAmount;
	private String supplierId;//SupplierCode
	private String notBeforeDate;//NotBeforeDate
	private String notAfterDate;//NotAfterDate
	
	public BigDecimal getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(BigDecimal seqNum) {
		this.seqNum = seqNum;
	}
	
	public String getSkuType() {
		return skuType;
	}
	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBrandSec() {
		return brandSec;
	}
	public void setBrandSec(String brandSec) {
		this.brandSec = brandSec;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductNameSec() {
		return productNameSec;
	}
	public void setProductNameSec(String productNameSec) {
		this.productNameSec = productNameSec;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public String getSizeDesc() {
		return sizeDesc;
	}
	public void setSizeDesc(String sizeDesc) {
		this.sizeDesc = sizeDesc;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getSkuAmount() {
		return skuAmount;
	}
	public void setSkuAmount(BigDecimal skuAmount) {
		this.skuAmount = skuAmount;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getNotBeforeDate() {
		return notBeforeDate;
	}
	public void setNotBeforeDate(String notBeforeDate) {
		this.notBeforeDate = notBeforeDate;
	}
	public String getNotAfterDate() {
		return notAfterDate;
	}
	public void setNotAfterDate(String notAfterDate) {
		this.notAfterDate = notAfterDate;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	
}
