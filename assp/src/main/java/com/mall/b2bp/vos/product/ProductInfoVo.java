package com.mall.b2bp.vos.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mall.b2bp.models.product.ProductBarCodeUpFieldModel;
import com.mall.b2bp.models.product.ProductImagesUpFieldModel;
import com.mall.b2bp.models.product.ProductUpFieldModel;

public class ProductInfoVo {
	private BigDecimal id;

	private String productCode;
	
	private String barcode;
	
	private String itemNumType;

	private String baseProductCode;

	private String supplierCode;

	private String supplierName;

	private String shortDescEn;

	private String shortDescTc;

	private String shortDescSc;

	private String brandCode;

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

	private String minOrderQty;

	private String dailyInventory;

	private BigDecimal minDeliverDate;

	private BigDecimal maxDeliverDate;
	
	private String minDeliverDateStr;
	
	private String maxDeliverDateStr;

	/**
	 * @return the minDeliverDateStr
	 */
	public String getMinDeliverDateStr() {
		return minDeliverDateStr;
	}

	/**
	 * @param minDeliverDateStr the minDeliverDateStr to set
	 */
	public void setMinDeliverDateStr(String minDeliverDateStr) {
		this.minDeliverDateStr = minDeliverDateStr;
	}

	/**
	 * @return the maxDeliverDateStr
	 */
	public String getMaxDeliverDateStr() {
		return maxDeliverDateStr;
	}

	/**
	 * @param maxDeliverDateStr the maxDeliverDateStr to set
	 */
	public void setMaxDeliverDateStr(String maxDeliverDateStr) {
		this.maxDeliverDateStr = maxDeliverDateStr;
	}

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
	private String productStatus;
	

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


	private List<ProductBarCode> barcodeList;

	private String estoreCategory;

	private List<SubProductInfo> subProductInfoList;
	
	private List<ProductUpFieldModel> productUpFiledList;
	
	private List<ProductBarCodeUpFieldModel> productBarCodeUpFiledList;
	
	private List<ProductImagesUpFieldModel> productImagesUpFiledList;

	private BigDecimal baseProductId;

	private boolean hasOnline;

	private String version;
	
    private String autoApprovalInd;
    
    private Date lastExportedDate;
    
    private Date lastApprovalDate;

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
    
    private String productShelfLife;

    private String minShelfLife;
    
    private String colorHexCode;
    
    private String smallExpensive;
    
    private String retekStatus;//RETEK_STATUS
    private Date retekLastUpdatedDate;//RETEK_LAST_UPDATED_DATE
    private Date retekLastApprovedDate;
    
    private String nutritionLabel;
    
    private String weightUnit;
    
    private String dimUnit;
    
	private String manufCountry;

	private String variantOn;
	
	private String onlineUpdatedInd;
	
	private String dealType;
	
	private Date lastOverWriteDate;
	
	private String onlineProductId;
	
	private String stagingProductId;
	
	private String deliveryFeeProductInd;
	
    private BigDecimal minReplenishmentLevel;
    private BigDecimal maxReplenishmentLevel;
    private String shippingInfo;
    private String pnsOnlineDeliveryType;
	
	public String getStagingProductId() {
		return stagingProductId;
	}

	public void setStagingProductId(String stagingProductId) {
		this.stagingProductId = stagingProductId;
	}

	public String getOnlineProductId() {
		return onlineProductId;
	}

	public void setOnlineProductId(String onlineProductId) {
		this.onlineProductId = onlineProductId;
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

	public String getPackAge() {
		return packAge;
	}

	public void setPackAge(String packAge) {
		this.packAge = packAge;
	}

	List<ProductImagesVo> imagesList;
	List<ProductImagesVo> deleteImagesList;

	List<ProductImagesVo> swatchImagesList;
	List<ProductImagesVo> deleteSwatchImagesList;
	private String supplierProfileUpdateInd;

	public String getSupplierProfileUpdateInd() {
		return supplierProfileUpdateInd;
	}

	public void setSupplierProfileUpdateInd(String supplierProfileUpdateInd) {
		this.supplierProfileUpdateInd = supplierProfileUpdateInd;
	}

	public boolean isHasOnline() {
		return hasOnline;
	}

	public void setHasOnline(boolean hasOnline) {
		this.hasOnline = hasOnline;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<ProductImagesVo> getImagesList() {
		return imagesList;
	}

	public void setImagesList(List<ProductImagesVo> imagesList) {
		this.imagesList = imagesList;
	}

	public BigDecimal getBaseProductId() {
		return baseProductId;
	}

	public void setBaseProductId(BigDecimal baseProductId) {
		this.baseProductId = baseProductId;
	}

	public List<SubProductInfo> getSubProductInfoList() {
		return subProductInfoList;
	}

	public void setSubProductInfoList(List<SubProductInfo> subProductInfoList) {
		this.subProductInfoList = subProductInfoList;
	}

	public String getEstoreCategory() {
		return estoreCategory;
	}

	public void setEstoreCategory(String estoreCategory) {
		this.estoreCategory = estoreCategory;
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
		this.baseProductCode = baseProductCode == null ? null : baseProductCode
				.trim();
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
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
		this.originCountry = originCountry == null ? null : originCountry
				.trim();
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

	public BigDecimal getSupplierLeadTime() {
		return supplierLeadTime;
	}

	public void setSupplierLeadTime(BigDecimal supplierLeadTime) {
		this.supplierLeadTime = supplierLeadTime;
	}

	public String getMinOrderQty() {
		return minOrderQty;
	}

	public void setMinOrderQty(String minOrderQty) {
		this.minOrderQty = minOrderQty;
	}

	public String getDailyInventory() {
		return dailyInventory;
	}

	public void setDailyInventory(String dailyInventory) {
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
		this.consignmentType = consignmentType == null ? null : consignmentType
				.trim();
	}

	public String getConsignmentCalBasis() {
		return consignmentCalBasis;
	}

	public void setConsignmentCalBasis(String consignmentCalBasis) {
		this.consignmentCalBasis = consignmentCalBasis == null ? null
				: consignmentCalBasis.trim();
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
		this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy
				.trim();
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
		this.supplierProductCode = supplierProductCode == null ? null
				: supplierProductCode.trim();
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
		this.supplierPortalIndicator = supplierPortalIndicator == null ? null
				: supplierPortalIndicator.trim();
	}


	public List<ProductBarCode> getBarcodeList() {
		return barcodeList;
	}

	public void setBarcodeList(List<ProductBarCode> barcodeList) {
		this.barcodeList = barcodeList;
	}

	public List<ProductImagesVo> getDeleteImagesList() {
		return deleteImagesList;
	}

	public void setDeleteImagesList(List<ProductImagesVo> deleteImagesList) {
		this.deleteImagesList = deleteImagesList;
	}

	public List<ProductImagesVo> getSwatchImagesList() {
		return swatchImagesList;
	}

	public void setSwatchImagesList(List<ProductImagesVo> swatchImagesList) {
		this.swatchImagesList = swatchImagesList;
	}

	public List<ProductImagesVo> getDeleteSwatchImagesList() {
		return deleteSwatchImagesList;
	}

	public void setDeleteSwatchImagesList(
			List<ProductImagesVo> deleteSwatchImagesList) {
		this.deleteSwatchImagesList = deleteSwatchImagesList;
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

	public void setRetekStatus(String retekStatus) {
		this.retekStatus = retekStatus;
	}

	public Date getRetekLastUpdatedDate() {
		return retekLastUpdatedDate;
	}

	public void setRetekLastUpdatedDate(Date retekLastUpdatedDate) {
		this.retekLastUpdatedDate = retekLastUpdatedDate;
	}

	public List<ProductUpFieldModel> getProductUpFiledList() {
		return productUpFiledList;
	}

	public void setProductUpFiledList(
			List<ProductUpFieldModel> productUpFiledList) {
		this.productUpFiledList = productUpFiledList;
	}

	public String getSmallExpensive() {
		return smallExpensive;
	}

	public void setSmallExpensive(String smallExpensive) {
		this.smallExpensive = smallExpensive;
	}

	public List<ProductBarCodeUpFieldModel> getProductBarCodeUpFiledList() {
		return productBarCodeUpFiledList;
	}

	public void setProductBarCodeUpFiledList(
			List<ProductBarCodeUpFieldModel> productBarCodeUpFiledList) {
		this.productBarCodeUpFiledList = productBarCodeUpFiledList;
	}

	public List<ProductImagesUpFieldModel> getProductImagesUpFiledList() {
		return productImagesUpFiledList;
	}

	public void setProductImagesUpFiledList(
			List<ProductImagesUpFieldModel> productImagesUpFiledList) {
		this.productImagesUpFiledList = productImagesUpFiledList;
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

	public String getManufCountry() {
		return manufCountry;
	}

	public void setManufCountry(String manufCountry) {
		this.manufCountry = manufCountry;
	}

	public String getVariantOn() {
		return variantOn;
	}

	public void setVariantOn(String variantOn) {
		this.variantOn = variantOn;
	}

	public String getItemNumType() {
		return itemNumType;
	}

	public void setItemNumType(String itemNumType) {
		this.itemNumType = itemNumType;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getOnlineUpdatedInd() {
		return onlineUpdatedInd;
	}

	public void setOnlineUpdatedInd(String onlineUpdatedInd) {
		this.onlineUpdatedInd = onlineUpdatedInd;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
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

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public Date getRetekLastApprovedDate() {
		return retekLastApprovedDate;
	}

	public void setRetekLastApprovedDate(Date retekLastApprovedDate) {
		this.retekLastApprovedDate = retekLastApprovedDate;
	}
	
	
	
}