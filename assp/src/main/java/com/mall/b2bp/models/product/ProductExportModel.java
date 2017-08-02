package com.mall.b2bp.models.product;

import java.math.BigDecimal;

public class ProductExportModel {

private BigDecimal id;

private String suName;
private String productCode;
private String baseProductCode;
private String supplierProductCode;

private String baseSupplierProductCode;

private String status;

private String shortDescEn;
private String shortDescTc;
private String shortDescSc;

private String longDescEn;
private String longDescTc;
private String longDescSc;

private String brandDescEn;

private String colorGroup;
private String colorCode;
private String colorDesc;
private String colorHexCode;

private String variantType;

private String onlineDate;
private String offlineDate;

private BigDecimal unitRetail;

private String standardUom;

private String country;

private String dcs;
private String category;

private String deliveryMode;

private BigDecimal supplierLeadTime;
private BigDecimal minOrderQty;
private BigDecimal dailyInventory;
private String consignmentType;
private BigDecimal minDeliverDate;
private BigDecimal maxDeliverDate;

private String consignmentCalBasis;
private String consignmentRate;
private String sizeDesc;
private BigDecimal casePackCase;
private BigDecimal casePackInner;

private String dimUnit;

private BigDecimal productDimHeight;
private BigDecimal productDimLength;
private BigDecimal productDimWidth;

private BigDecimal shippingDimLength;
private BigDecimal shippingDimHeight;
private BigDecimal shippingDimWidth;

private BigDecimal caseDimHeight;
private BigDecimal caseDimWidth;
private BigDecimal caseDimLength;

private String weightUnit;

private BigDecimal grossWeight;
private BigDecimal shippingWeight;

private String barcodeType;
private String barcodeNum;

private String nutritionLabel;

private String productUsageEn;
private String productUsageTc;
private String productUsageSc;

private String productWarningsEn;
private String productWarningsTc;
private String productWarningsSc;

private String storageEn;
private String storageTc;
private String storageSc;

private String productIngredientsEn;
private String productIngredientsTc;
private String productIngredientsSc;
private String  manufPackage;
private String smallExpensive;

private String manufCountry;
private String productShelfLife;
private String minShelfLife;

private BigDecimal minReplenishmentLevel;
private BigDecimal maxReplenishmentLevel;
private String shippingInfo;
private String pnsOnlineDeliveryType;




public BigDecimal getId() {
	return id;
}
public void setId(BigDecimal id) {
	this.id = id;
}
public String getSuName() {
	return suName;
}
public void setSuName(String suName) {
	this.suName = suName;
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
public String getSupplierProductCode() {
	return supplierProductCode;
}
public void setSupplierProductCode(String supplierProductCode) {
	this.supplierProductCode = supplierProductCode;
}
public String getBaseSupplierProductCode() {
	return baseSupplierProductCode;
}
public void setBaseSupplierProductCode(String baseSupplierProductCode) {
	this.baseSupplierProductCode = baseSupplierProductCode;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
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
public String getLongDescEn() {
	return longDescEn;
}
public void setLongDescEn(String longDescEn) {
	this.longDescEn = longDescEn;
}
public String getLongDescTc() {
	return longDescTc;
}
public void setLongDescTc(String longDescTc) {
	this.longDescTc = longDescTc;
}
public String getLongDescSc() {
	return longDescSc;
}
public void setLongDescSc(String longDescSc) {
	this.longDescSc = longDescSc;
}
public String getBrandDescEn() {
	return brandDescEn;
}
public void setBrandDescEn(String brandDescEn) {
	this.brandDescEn = brandDescEn;
}

public String getColorCode() {
	return colorCode;
}
public void setColorCode(String colorCode) {
	this.colorCode = colorCode;
}
public String getColorDesc() {
	return colorDesc;
}
public void setColorDesc(String colorDesc) {
	this.colorDesc = colorDesc;
}
public String getColorHexCode() {
	return colorHexCode;
}
public void setColorHexCode(String colorHexCode) {
	this.colorHexCode = colorHexCode;
}
public String getVariantType() {
	return variantType;
}
public void setVariantType(String variantType) {
	this.variantType = variantType;
}
public String getOnlineDate() {
	return onlineDate;
}
public void setOnlineDate(String onlineDate) {
	this.onlineDate = onlineDate;
}
public String getOfflineDate() {
	return offlineDate;
}
public void setOfflineDate(String offlineDate) {
	this.offlineDate = offlineDate;
}
public String getColorGroup() {
	return colorGroup;
}
public void setColorGroup(String colorGroup) {
	this.colorGroup = colorGroup;
}
public BigDecimal getUnitRetail() {
	return unitRetail;
}
public void setUnitRetail(BigDecimal unitRetail) {
	this.unitRetail = unitRetail;
}
public String getStandardUom() {
	return standardUom;
}
public void setStandardUom(String standardUom) {
	this.standardUom = standardUom;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getDcs() {
	return dcs;
}
public void setDcs(String dcs) {
	this.dcs = dcs;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getDeliveryMode() {
	return deliveryMode;
}
public void setDeliveryMode(String deliveryMode) {
	this.deliveryMode = deliveryMode;
}
public BigDecimal getSupplierLeadTime() {
	return supplierLeadTime;
}
public void setSupplierLeadTime(BigDecimal supplierLeadTime) {
	this.supplierLeadTime = supplierLeadTime;
}
public BigDecimal getMinOrderQty() {
	return minOrderQty;
}
public void setMinOrderQty(BigDecimal minOrderQty) {
	this.minOrderQty = minOrderQty;
}
public BigDecimal getDailyInventory() {
	return dailyInventory;
}
public void setDailyInventory(BigDecimal dailyInventory) {
	this.dailyInventory = dailyInventory;
}
public String getConsignmentType() {
	return consignmentType;
}
public void setConsignmentType(String consignmentType) {
	this.consignmentType = consignmentType;
}
public BigDecimal getMinDeliverDate() {
	return minDeliverDate;
}
public void setMinDeliverDate(BigDecimal minDeliverDate) {
	this.minDeliverDate = minDeliverDate;
}
public BigDecimal getMaxDeliverDate() {
	return maxDeliverDate;
}
public void setMaxDeliverDate(BigDecimal maxDeliverDate) {
	this.maxDeliverDate = maxDeliverDate;
}
public String getConsignmentCalBasis() {
	return consignmentCalBasis;
}
public void setConsignmentCalBasis(String consignmentCalBasis) {
	this.consignmentCalBasis = consignmentCalBasis;
}
public String getConsignmentRate() {
	return consignmentRate;
}
public void setConsignmentRate(String consignmentRate) {
	this.consignmentRate = consignmentRate;
}
public String getSizeDesc() {
	return sizeDesc;
}
public void setSizeDesc(String sizeDesc) {
	this.sizeDesc = sizeDesc;
}
public BigDecimal getCasePackCase() {
	return casePackCase;
}
public void setCasePackCase(BigDecimal casePackCase) {
	this.casePackCase = casePackCase;
}
public BigDecimal getCasePackInner() {
	return casePackInner;
}
public void setCasePackInner(BigDecimal casePackInner) {
	this.casePackInner = casePackInner;
}
public String getDimUnit() {
	return dimUnit;
}
public void setDimUnit(String dimUnit) {
	this.dimUnit = dimUnit;
}
public BigDecimal getProductDimHeight() {
	return productDimHeight;
}
public void setProductDimHeight(BigDecimal productDimHeight) {
	this.productDimHeight = productDimHeight;
}
public BigDecimal getProductDimLength() {
	return productDimLength;
}
public void setProductDimLength(BigDecimal productDimLength) {
	this.productDimLength = productDimLength;
}
public BigDecimal getProductDimWidth() {
	return productDimWidth;
}
public void setProductDimWidth(BigDecimal productDimWidth) {
	this.productDimWidth = productDimWidth;
}
public BigDecimal getShippingDimLength() {
	return shippingDimLength;
}
public void setShippingDimLength(BigDecimal shippingDimLength) {
	this.shippingDimLength = shippingDimLength;
}
public BigDecimal getShippingDimHeight() {
	return shippingDimHeight;
}
public void setShippingDimHeight(BigDecimal shippingDimHeight) {
	this.shippingDimHeight = shippingDimHeight;
}
public BigDecimal getShippingDimWidth() {
	return shippingDimWidth;
}
public void setShippingDimWidth(BigDecimal shippingDimWidth) {
	this.shippingDimWidth = shippingDimWidth;
}
public BigDecimal getCaseDimHeight() {
	return caseDimHeight;
}
public void setCaseDimHeight(BigDecimal caseDimHeight) {
	this.caseDimHeight = caseDimHeight;
}
public BigDecimal getCaseDimWidth() {
	return caseDimWidth;
}
public void setCaseDimWidth(BigDecimal caseDimWidth) {
	this.caseDimWidth = caseDimWidth;
}
public BigDecimal getCaseDimLength() {
	return caseDimLength;
}
public void setCaseDimLength(BigDecimal caseDimLength) {
	this.caseDimLength = caseDimLength;
}
public String getWeightUnit() {
	return weightUnit;
}
public void setWeightUnit(String weightUnit) {
	this.weightUnit = weightUnit;
}

public String getBarcodeType() {
	return barcodeType;
}
public void setBarcodeType(String barcodeType) {
	this.barcodeType = barcodeType;
}
public String getBarcodeNum() {
	return barcodeNum;
}
public void setBarcodeNum(String barcodeNum) {
	this.barcodeNum = barcodeNum;
}
public String getNutritionLabel() {
	return nutritionLabel;
}
public void setNutritionLabel(String nutritionLabel) {
	this.nutritionLabel = nutritionLabel;
}
public String getProductUsageEn() {
	return productUsageEn;
}
public void setProductUsageEn(String productUsageEn) {
	this.productUsageEn = productUsageEn;
}
public String getProductUsageTc() {
	return productUsageTc;
}
public void setProductUsageTc(String productUsageTc) {
	this.productUsageTc = productUsageTc;
}
public String getProductUsageSc() {
	return productUsageSc;
}
public void setProductUsageSc(String productUsageSc) {
	this.productUsageSc = productUsageSc;
}
public String getProductWarningsEn() {
	return productWarningsEn;
}
public void setProductWarningsEn(String productWarningsEn) {
	this.productWarningsEn = productWarningsEn;
}
public String getProductWarningsTc() {
	return productWarningsTc;
}
public void setProductWarningsTc(String productWarningsTc) {
	this.productWarningsTc = productWarningsTc;
}
public String getProductWarningsSc() {
	return productWarningsSc;
}
public void setProductWarningsSc(String productWarningsSc) {
	this.productWarningsSc = productWarningsSc;
}
public String getStorageEn() {
	return storageEn;
}
public void setStorageEn(String storageEn) {
	this.storageEn = storageEn;
}
public String getStorageTc() {
	return storageTc;
}
public void setStorageTc(String storageTc) {
	this.storageTc = storageTc;
}
public String getStorageSc() {
	return storageSc;
}
public void setStorageSc(String storageSc) {
	this.storageSc = storageSc;
}
public String getProductIngredientsEn() {
	return productIngredientsEn;
}
public void setProductIngredientsEn(String productIngredientsEn) {
	this.productIngredientsEn = productIngredientsEn;
}
public String getProductIngredientsTc() {
	return productIngredientsTc;
}
public void setProductIngredientsTc(String productIngredientsTc) {
	this.productIngredientsTc = productIngredientsTc;
}
public String getProductIngredientsSc() {
	return productIngredientsSc;
}
public void setProductIngredientsSc(String productIngredientsSc) {
	this.productIngredientsSc = productIngredientsSc;
}
public String getManufPackage() {
	return manufPackage;
}
public void setManufPackage(String manufPackage) {
	this.manufPackage = manufPackage;
}
public String getSmallExpensive() {
	return smallExpensive;
}
public void setSmallExpensive(String smallExpensive) {
	this.smallExpensive = smallExpensive;
}
public String getManufCountry() {
	return manufCountry;
}
public void setManufCountry(String manufCountry) {
	this.manufCountry = manufCountry;
}
public String getProductShelfLife() {
	return productShelfLife;
}
public void setProductShelfLife(String productShelfLife) {
	this.productShelfLife = productShelfLife;
}
public String getMinShelfLife() {
	return minShelfLife;
}
public void setMinShelfLife(String minShelfLife) {
	this.minShelfLife = minShelfLife;
}
public BigDecimal getGrossWeight() {
	return grossWeight;
}
public void setGrossWeight(BigDecimal grossWeight) {
	this.grossWeight = grossWeight;
}
public BigDecimal getShippingWeight() {
	return shippingWeight;
}
public void setShippingWeight(BigDecimal shippingWeight) {
	this.shippingWeight = shippingWeight;
}
public BigDecimal getMinReplenishmentLevel() {
	return minReplenishmentLevel;
}
public void setMinReplenishmentLevel(BigDecimal minReplenishmentLevel) {
	this.minReplenishmentLevel = minReplenishmentLevel;
}
public BigDecimal getMaxReplenishmentLevel() {
	return maxReplenishmentLevel;
}
public void setMaxReplenishmentLevel(BigDecimal maxReplenishmentLevel) {
	this.maxReplenishmentLevel = maxReplenishmentLevel;
}
public String getShippingInfo() {
	return shippingInfo;
}
public void setShippingInfo(String shippingInfo) {
	this.shippingInfo = shippingInfo;
}
public String getPnsOnlineDeliveryType() {
	return pnsOnlineDeliveryType;
}
public void setPnsOnlineDeliveryType(String pnsOnlineDeliveryType) {
	this.pnsOnlineDeliveryType = pnsOnlineDeliveryType;
}

	


}
