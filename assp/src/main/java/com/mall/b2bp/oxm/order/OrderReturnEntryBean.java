package com.mall.b2bp.oxm.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderReturnEntryBean {
	private BigDecimal id;

    private BigDecimal orderReturnId;

    private String skuId;

    private String skuType;

    private String supplierId;

    private String brand;

    private String brandSec;

    private String productName;

    private String productNameSec;

    private BigDecimal orderQty;

    private BigDecimal expectedQty;

    private BigDecimal returnReqQty;

    private BigDecimal writeOffQty;

    private String sizeDesc;

    private String skuCollectRmk;

    private String createdBy;

    private Date createdDate;

    private String lastUpdatedBy;

    private Date lastUpdatedDate;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getOrderReturnId() {
        return orderReturnId;
    }

    public void setOrderReturnId(BigDecimal orderReturnId) {
        this.orderReturnId = orderReturnId;
    }

   

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType == null ? null : skuType.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getBrandSec() {
        return brandSec;
    }

    public void setBrandSec(String brandSec) {
        this.brandSec = brandSec == null ? null : brandSec.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductNameSec() {
        return productNameSec;
    }

    public void setProductNameSec(String productNameSec) {
        this.productNameSec = productNameSec == null ? null : productNameSec.trim();
    }

    public BigDecimal getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(BigDecimal orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(BigDecimal expectedQty) {
        this.expectedQty = expectedQty;
    }

    public BigDecimal getReturnReqQty() {
        return returnReqQty;
    }

    public void setReturnReqQty(BigDecimal returnReqQty) {
        this.returnReqQty = returnReqQty;
    }

    public BigDecimal getWriteOffQty() {
        return writeOffQty;
    }

    public void setWriteOffQty(BigDecimal writeOffQty) {
        this.writeOffQty = writeOffQty;
    }

    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc == null ? null : sizeDesc.trim();
    }

    public String getSkuCollectRmk() {
        return skuCollectRmk;
    }

    public void setSkuCollectRmk(String skuCollectRmk) {
        this.skuCollectRmk = skuCollectRmk == null ? null : skuCollectRmk.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
}
