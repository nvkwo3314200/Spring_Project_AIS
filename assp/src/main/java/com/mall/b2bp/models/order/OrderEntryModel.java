package com.mall.b2bp.models.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderEntryModel {
    private BigDecimal id;

    private BigDecimal orderId;

    private BigDecimal seqNum;

    private String skuId;
    private BigDecimal returnTotal;

    private String skuType;
    private String productCode;
    private String baseProductCode;
    private String shortDescEn;
    private String shortDescTc;
    private String shortDescSc;
    private String supplierProductCode;
    private String supplierName;
	private String notBeforeDate;//NotBeforeDate NOT_BEFORE_DATE
	private String notAfterDate;//NotAfterDate NOT_AFTER_DATE
    
    private String supplierId;
    private String brand;

    private String brandSec;

    private String productName;
    private String productNameSec;

    private BigDecimal qty;

    private String sizeDesc;

    private BigDecimal unitPrice;

    private BigDecimal skuAmount;

    private BigDecimal pickedQty;

    private Date pickDate;

    private String trackId;

    private String boxNum;

    private Date shipDate;

    private String deliverySuccess;

    private Date deliveryDate;

    private BigDecimal deliveryQty;

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

    public BigDecimal getOrderId() {
        return orderId;
    }

    public void setOrderId(BigDecimal orderId) {
        this.orderId = orderId;
    }

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
        this.skuType = skuType == null ? null : skuType.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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
        this.sizeDesc = sizeDesc == null ? null : sizeDesc.trim();
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

    public BigDecimal getPickedQty() {
        return pickedQty;
    }

    public void setPickedQty(BigDecimal pickedQty) {
        this.pickedQty = pickedQty;
    }

    public Date getPickDate() {
        return pickDate;
    }

    public void setPickDate(Date pickDate) {
        this.pickDate = pickDate;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId == null ? null : trackId.trim();
    }

    public String getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(String boxNum) {
        this.boxNum = boxNum == null ? null : boxNum.trim();
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getDeliverySuccess() {
        return deliverySuccess;
    }

    public void setDeliverySuccess(String deliverySuccess) {
        this.deliverySuccess = deliverySuccess == null ? null : deliverySuccess.trim();
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getDeliveryQty() {
        return deliveryQty;
    }

    public void setDeliveryQty(BigDecimal deliveryQty) {
        this.deliveryQty = deliveryQty;
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

	public BigDecimal getReturnTotal() {
		return returnTotal;
	}

	public void setReturnTotal(BigDecimal returnTotal) {
		this.returnTotal = returnTotal;
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
	
	public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBaseProductCode() {
        return baseProductCode;
    }

    public void setBaseProductCode(String baseProductCode) {
        this.baseProductCode = baseProductCode;
    }

    public String getShortDescEn() {
        return shortDescEn;
    }

    public void setShortDescEn(String shortDescEn) {
        this.shortDescEn = shortDescEn;
    }

    public String getShortDescTc() {
        return shortDescTc;
    }

    public void setShortDescTc(String shortDescTc) {
        this.shortDescTc = shortDescTc;
    }

    public String getShortDescSc() {
        return shortDescSc;
    }

    public void setShortDescSc(String shortDescSc) {
        this.shortDescSc = shortDescSc;
    }

    public String getSupplierProductCode() {
        return supplierProductCode;
    }

    public void setSupplierProductCode(String supplierProductCode) {
        this.supplierProductCode = supplierProductCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
	
	
}