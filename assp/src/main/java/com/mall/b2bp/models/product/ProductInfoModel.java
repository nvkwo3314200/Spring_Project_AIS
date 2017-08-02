package com.mall.b2bp.models.product;

import java.math.BigDecimal;
import java.util.Date;

public class ProductInfoModel {
    private BigDecimal id;



	private String productCode;
	private String supplierProfileUpdateInd;

    private String baseProductCode;

    private String supplierCode;
    
    private String supplierName;

  

	private String shortDescEn;

    private String shortDescTc;

    private String shortDescSc;

    private BigDecimal brandCode;

    private Date onlineDate;

    private Date offlineDate;

    private BigDecimal unitRetail;

    private BigDecimal unitCost;

    private String currency;

    private String standardUom;

    private String originCountry;

    private BigDecimal dept;

    private BigDecimal clazz;

    private BigDecimal subClass;

    private String deliveryMode;

    private BigDecimal supplierLeadTime;

    private BigDecimal minOrderQty;

    private BigDecimal dailyInventory;

    private BigDecimal minDeliverDate;

    private BigDecimal maxDeliverDate;

    private String consignmentType;

    private String consignmentCalBasis;

    private BigDecimal consignmentRate;

    private String sizeDesc;

    private BigDecimal casePackInner;

    private BigDecimal casePackCase;

    private BigDecimal productDimHeight;

    private BigDecimal productDimLength;

    private BigDecimal storageDimHeight;

    private BigDecimal storageDimWidth;

    private BigDecimal grossWeight;

    private String status;

    private String failedReason;

    private String createdBy;

    private Date createdDate;

    private String lastUpdatedBy;

    private Date lastUpdatedDate;

    private String supplierProductCode;

    private BigDecimal productDimWidth;

    private BigDecimal storageDimLength;

    private String longDescEn;

    private String longDescTc;

    private String longDescSc;

    private BigDecimal caseDimHeight;

    private BigDecimal caseDimWidth;

    private BigDecimal caseDimLength;

    private BigDecimal shippingWeight;

    private String colorGroup;

    private String colorCode;

    private String colorDesc;

    private String variantColor;

    private String variantSize;

    private BigDecimal shippingDimLength;

    private BigDecimal shippingDimHeight;

    private BigDecimal shippingDimWidth;

    private String supplierPortalIndicator;

    private String estoreCategory;

    private BigDecimal baseProductId;

    private String version;
    
    private Date lastExportedDate;
    
    private Date lastApprovalDate;

    private String autoApprovalInd;

   private String productUsageEn;

    private String productUsageTc;

    private String productUsageSc;

    private String productWarningsEn;

    private String productWarningsTc;

    private String productWarningsSc;

    private String productIngredientsEn;

    private String productIngredientsTc;

    private String productIngredientsSc;

    private String packAge;
    
    private String storageConditionEn;

    private String storageConditionTc;

    private String storageConditionSc;
    
    private String colorHexCode;
    
    private String smallExpensive;

    private String nutritionLabel;
    
    private String weightUnit;
    
    private String dimUnit;
    
    private String onlineUpdatedInd;
    
    private Date lastOverWriteDate;
    
    private String deliveryFeeProductInd;
    
    
    private String retekStatus;//RETEK_STATUS
    private Date retekLastUpdatedDate;//RETEK_LAST_UPDATED_DATE
    private Date retekLastApprovedDate;
    
    private BigDecimal minReplenishmentLevel;
    private BigDecimal maxReplenishmentLevel;
    private String shippingInfo;
    private String pnsOnlineDeliveryType;
    private BigDecimal imageSyncCount;
    
    public String getSupplierName() {
  		return supplierName;
  	}

  	public void setSupplierName(String supplierName) {
  		this.supplierName = supplierName;
  	}
    
	public Date getLastOverWriteDate() {
		return lastOverWriteDate;
	}

	public void setLastOverWriteDate(Date lastOverWriteDate) {
		this.lastOverWriteDate = lastOverWriteDate;
	}

	public String getColorHexCode() {
		return colorHexCode;
	}

	public void setColorHexCode(String colorHexCode) {
		this.colorHexCode = colorHexCode;
	}

	public void setRetekStatus(String retekStatus) {
		this.retekStatus = retekStatus;
	}

	public Date getRetekLastUpdatedDate() {
		return retekLastUpdatedDate;
	}

	public void setRetekLastUpdatedDate(Date retekLastUpdatedDate) {
		this.retekLastUpdatedDate = retekLastUpdatedDate;
	}

	public String getStorageConditionEn() {
		return storageConditionEn;
	}

	public void setStorageConditionEn(String storageConditionEn) {
		this.storageConditionEn = storageConditionEn;
	}

	public String getStorageConditionTc() {
		return storageConditionTc;
	}

	public void setStorageConditionTc(String storageConditionTc) {
		this.storageConditionTc = storageConditionTc;
	}

	public String getStorageConditionSc() {
		return storageConditionSc;
	}

	public void setStorageConditionSc(String storageConditionSc) {
		this.storageConditionSc = storageConditionSc;
	}

	public String getPackAge() {
		return packAge;
	}

	public void setPackAge(String packAge) {
		this.packAge = packAge;
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

	private String manufCountry;

    private String productShelfLife;

    private String minShelfLife;
	
    public String getSupplierProfileUpdateInd() {
        return supplierProfileUpdateInd;
    }

    public void setSupplierProfileUpdateInd(String supplierProfileUpdateInd) {
        this.supplierProfileUpdateInd = supplierProfileUpdateInd;
    }

    public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getBaseProductCode() {
        return baseProductCode;
    }

    public void setBaseProductCode(String baseProductCode) {
        this.baseProductCode = baseProductCode == null ? null : baseProductCode.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public BigDecimal getClazz() {
		return clazz;
	}

	public void setClazz(BigDecimal clazz) {
		this.clazz = clazz;
	}
	
    public String getShortDescEn() {
        return shortDescEn;
    }

    public void setShortDescEn(String shortDescEn) {
        this.shortDescEn = shortDescEn == null ? null : shortDescEn.trim();
    }

    public String getShortDescTc() {
        return shortDescTc;
    }

    public void setShortDescTc(String shortDescTc) {
        this.shortDescTc = shortDescTc == null ? null : shortDescTc.trim();
    }

    public String getShortDescSc() {
        return shortDescSc;
    }

    public void setShortDescSc(String shortDescSc) {
        this.shortDescSc = shortDescSc == null ? null : shortDescSc.trim();
    }

    public BigDecimal getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(BigDecimal brandCode) {
        this.brandCode = brandCode;
    }

    public Date getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    public Date getOfflineDate() {
        return offlineDate;
    }

    public void setOfflineDate(Date offlineDate) {
        this.offlineDate = offlineDate;
    }

    public BigDecimal getUnitRetail() {
        return unitRetail;
    }

    public void setUnitRetail(BigDecimal unitRetail) {
        this.unitRetail = unitRetail;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getStandardUom() {
        return standardUom;
    }

    public void setStandardUom(String standardUom) {
        this.standardUom = standardUom == null ? null : standardUom.trim();
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry == null ? null : originCountry.trim();
    }

    public BigDecimal getDept() {
        return dept;
    }

    public void setDept(BigDecimal dept) {
        this.dept = dept;
    }

    public BigDecimal getSubClass() {
        return subClass;
    }

    public void setSubClass(BigDecimal subClass) {
        this.subClass = subClass;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode == null ? null : deliveryMode.trim();
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

    public String getConsignmentType() {
        return consignmentType;
    }

    public void setConsignmentType(String consignmentType) {
        this.consignmentType = consignmentType == null ? null : consignmentType.trim();
    }

    public String getConsignmentCalBasis() {
        return consignmentCalBasis;
    }

    public void setConsignmentCalBasis(String consignmentCalBasis) {
        this.consignmentCalBasis = consignmentCalBasis == null ? null : consignmentCalBasis.trim();
    }

    public BigDecimal getConsignmentRate() {
        return consignmentRate;
    }

    public void setConsignmentRate(BigDecimal consignmentRate) {
        this.consignmentRate = consignmentRate;
    }

    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc == null ? null : sizeDesc.trim();
    }

    public BigDecimal getCasePackInner() {
        return casePackInner;
    }

    public void setCasePackInner(BigDecimal casePackInner) {
        this.casePackInner = casePackInner;
    }

    public BigDecimal getCasePackCase() {
        return casePackCase;
    }

    public void setCasePackCase(BigDecimal casePackCase) {
        this.casePackCase = casePackCase;
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

    public BigDecimal getStorageDimHeight() {
        return storageDimHeight;
    }

    public void setStorageDimHeight(BigDecimal storageDimHeight) {
        this.storageDimHeight = storageDimHeight;
    }

    public BigDecimal getStorageDimWidth() {
        return storageDimWidth;
    }

    public void setStorageDimWidth(BigDecimal storageDimWidth) {
        this.storageDimWidth = storageDimWidth;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
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

    public String getSupplierProductCode() {
        return supplierProductCode;
    }

    public void setSupplierProductCode(String supplierProductCode) {
        this.supplierProductCode = supplierProductCode == null ? null : supplierProductCode.trim();
    }

    public BigDecimal getProductDimWidth() {
        return productDimWidth;
    }

    public void setProductDimWidth(BigDecimal productDimWidth) {
        this.productDimWidth = productDimWidth;
    }

    public BigDecimal getStorageDimLength() {
        return storageDimLength;
    }

    public void setStorageDimLength(BigDecimal storageDimLength) {
        this.storageDimLength = storageDimLength;
    }

    public String getLongDescEn() {
        return longDescEn;
    }

    public void setLongDescEn(String longDescEn) {
        this.longDescEn = longDescEn == null ? null : longDescEn.trim();
    }

    public String getLongDescTc() {
        return longDescTc;
    }

    public void setLongDescTc(String longDescTc) {
        this.longDescTc = longDescTc == null ? null : longDescTc.trim();
    }

    public String getLongDescSc() {
        return longDescSc;
    }

    public void setLongDescSc(String longDescSc) {
        this.longDescSc = longDescSc == null ? null : longDescSc.trim();
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

    public BigDecimal getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(BigDecimal shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public String getColorGroup() {
        return colorGroup;
    }

    public void setColorGroup(String colorGroup) {
        this.colorGroup = colorGroup == null ? null : colorGroup.trim();
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode == null ? null : colorCode.trim();
    }

    public String getColorDesc() {
        return colorDesc;
    }

    public void setColorDesc(String colorDesc) {
        this.colorDesc = colorDesc == null ? null : colorDesc.trim();
    }

    public String getVariantColor() {
        return variantColor;
    }

    public void setVariantColor(String variantColor) {
        this.variantColor = variantColor == null ? null : variantColor.trim();
    }

    public String getVariantSize() {
        return variantSize;
    }

    public void setVariantSize(String variantSize) {
        this.variantSize = variantSize == null ? null : variantSize.trim();
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

    public String getSupplierPortalIndicator() {
        return supplierPortalIndicator;
    }

    public void setSupplierPortalIndicator(String supplierPortalIndicator) {
        this.supplierPortalIndicator = supplierPortalIndicator == null ? null : supplierPortalIndicator.trim();
    }

    public String getEstoreCategory() {
        return estoreCategory;
    }

    public void setEstoreCategory(String estoreCategory) {
        this.estoreCategory = estoreCategory == null ? null : estoreCategory.trim();
    }

    public BigDecimal getBaseProductId() {
        return baseProductId;
    }

    public void setBaseProductId(BigDecimal baseProductId) {
        this.baseProductId = baseProductId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

	public Date getLastExportedDate() {
		return lastExportedDate;
	}

	public void setLastExportedDate(Date lastExportedDate) {
		this.lastExportedDate = lastExportedDate;
	}

	public Date getLastApprovalDate() {
		return lastApprovalDate;
	}

	public void setLastApprovalDate(Date lastApprovalDate) {
		this.lastApprovalDate = lastApprovalDate;
	}

    public BigDecimal getSupplierLeadTime() {
        return supplierLeadTime;
    }

    public void setSupplierLeadTime(BigDecimal supplierLeadTime) {
        this.supplierLeadTime = supplierLeadTime;
    }
    public String getAutoApprovalInd() {
        return autoApprovalInd;
    }

    public void setAutoApprovalInd(String autoApprovalInd) {
        this.autoApprovalInd = autoApprovalInd;
    }

	public String getRetekStatus() {
		return retekStatus;
	}

	public String getSmallExpensive() {
		return smallExpensive;
	}

	public void setSmallExpensive(String smallExpensive) {
		this.smallExpensive = smallExpensive;
	}

	public String getNutritionLabel() {
		return nutritionLabel;
	}

	public void setNutritionLabel(String nutritionLabel) {
		this.nutritionLabel = nutritionLabel;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getDimUnit() {
		return dimUnit;
	}

	public void setDimUnit(String dimUnit) {
		this.dimUnit = dimUnit;
	}

	public String getOnlineUpdatedInd() {
		return onlineUpdatedInd;
	}

	public void setOnlineUpdatedInd(String onlineUpdatedInd) {
		this.onlineUpdatedInd = onlineUpdatedInd;
	}

	public String getDeliveryFeeProductInd() {
		return deliveryFeeProductInd;
	}

	public void setDeliveryFeeProductInd(String deliveryFeeProductInd) {
		this.deliveryFeeProductInd = deliveryFeeProductInd;
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

	public Date getRetekLastApprovedDate() {
		return retekLastApprovedDate;
	}

	public void setRetekLastApprovedDate(Date retekLastApprovedDate) {
		this.retekLastApprovedDate = retekLastApprovedDate;
	}

	public BigDecimal getImageSyncCount() {
		return imageSyncCount;
	}

	public void setImageSyncCount(BigDecimal imageSyncCount) {
		this.imageSyncCount = imageSyncCount;
	}

	
    
}