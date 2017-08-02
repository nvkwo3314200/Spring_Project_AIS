package com.mall.b2bp.models.supplier;

import java.math.BigDecimal;
import java.util.Date;

public class SupplierModel {
	private BigDecimal idDb;
	private String id;

	private String name;

	private BigDecimal deliveryFee;
	private BigDecimal freeDeliveryThreshold;
	private String deliveryFeeStr;
	private String freeDeliveryThresholdStr;

	private BigDecimal minDeliveryDay;

	private BigDecimal maxDeliveryDay;

	private BigDecimal consignmentVia;

	private BigDecimal warehouseDeliLeadTime;

	private String createdBy;

	private Date createdDate;

	private String lastUpdatedBy;

	private Date lastUpdatedDate;

	private String shopNameEn;

	private String shopNameTc;

	private String shopNameSc;

	private String shopDescEn;

	private String shopDescTc;

	private String shopDescSc;
	private String updatedInd;
	private String deliveryCode;
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public BigDecimal getFreeDeliveryThreshold() {
		return freeDeliveryThreshold;
	}

	public void setFreeDeliveryThreshold(BigDecimal freeDeliveryThreshold) {
		this.freeDeliveryThreshold = freeDeliveryThreshold;
	}

	public BigDecimal getMinDeliveryDay() {
		return minDeliveryDay;
	}

	public void setMinDeliveryDay(BigDecimal minDeliveryDay) {
		this.minDeliveryDay = minDeliveryDay;
	}

	public BigDecimal getMaxDeliveryDay() {
		return maxDeliveryDay;
	}

	public void setMaxDeliveryDay(BigDecimal maxDeliveryDay) {
		this.maxDeliveryDay = maxDeliveryDay;
	}

	public BigDecimal getConsignmentVia() {
		return consignmentVia;
	}

	public void setConsignmentVia(BigDecimal consignmentVia) {
		this.consignmentVia = consignmentVia;
	}

	public BigDecimal getWarehouseDeliLeadTime() {
		return warehouseDeliLeadTime;
	}

	public void setWarehouseDeliLeadTime(BigDecimal warehouseDeliLeadTime) {
		this.warehouseDeliLeadTime = warehouseDeliLeadTime;
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

	public String getShopNameEn() {
		return shopNameEn;
	}

	public void setShopNameEn(String shopNameEn) {
		this.shopNameEn = shopNameEn;
	}

	public String getShopNameTc() {
		return shopNameTc;
	}

	public void setShopNameTc(String shopNameTc) {
		this.shopNameTc = shopNameTc;
	}

	public String getShopNameSc() {
		return shopNameSc;
	}

	public void setShopNameSc(String shopNameSc) {
		this.shopNameSc = shopNameSc;
	}

	public String getShopDescEn() {
		return shopDescEn;
	}

	public void setShopDescEn(String shopDescEn) {
		this.shopDescEn = shopDescEn;
	}

	public String getShopDescTc() {
		return shopDescTc;
	}

	public void setShopDescTc(String shopDescTc) {
		this.shopDescTc = shopDescTc;
	}

	public String getShopDescSc() {
		return shopDescSc;
	}

	public void setShopDescSc(String shopDescSc) {
		this.shopDescSc = shopDescSc;
	}

	public String getUpdatedInd() {
		return updatedInd;
	}

	public void setUpdatedInd(String updatedInd) {
		this.updatedInd = updatedInd;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeliveryFeeStr() {
		return deliveryFeeStr;
	}

	public void setDeliveryFeeStr(String deliveryFeeStr) {
		this.deliveryFeeStr = deliveryFeeStr;
	}

	public String getFreeDeliveryThresholdStr() {
		return freeDeliveryThresholdStr;
	}

	public void setFreeDeliveryThresholdStr(String freeDeliveryThresholdStr) {
		this.freeDeliveryThresholdStr = freeDeliveryThresholdStr;
	}

	public BigDecimal getIdDb() {
		return idDb;
	}

	public void setIdDb(BigDecimal idDb) {
		this.idDb = idDb;
	}
	
	//

}