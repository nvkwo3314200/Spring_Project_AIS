package com.mall.b2bp.populators.product;

import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.vos.product.ProductInfoVo;

public class ProductPopulator {
	
	public  ProductInfoVo converProductModelToVo(ProductInfoModel productInfoModel){
		ProductInfoVo productInfoVo = new ProductInfoVo();
		productInfoVo.setId(productInfoModel.getId());
		productInfoVo.setBaseProductCode(productInfoModel.getBaseProductCode());
		productInfoVo.setBrandCode(String.valueOf(productInfoModel.getBrandCode()));
		productInfoVo.setCaseDimHeight(productInfoModel.getCaseDimHeight());
		productInfoVo.setCaseDimLength(productInfoModel.getCaseDimLength());
		productInfoVo.setCaseDimWidth(productInfoModel.getCaseDimWidth());
		productInfoVo.setCasePackCase(productInfoModel.getCasePackCase());
		productInfoVo.setCasePackInner(productInfoModel.getCasePackInner());
		productInfoVo.setColorCode(productInfoModel.getColorCode());
		productInfoVo.setColorDesc(productInfoModel.getColorDesc());
		productInfoVo.setColorGroup(productInfoModel.getColorGroup());
		productInfoVo.setConsignmentCalBasis(productInfoModel.getConsignmentCalBasis());
		productInfoVo.setConsignmentType(productInfoModel.getConsignmentType());
		productInfoVo.setCreatedBy(productInfoModel.getCreatedBy());
		productInfoVo.setCreatedDate(productInfoModel.getCreatedDate());
		productInfoVo.setCurrency(productInfoModel.getCurrency());
		productInfoVo.setDeliveryMode(productInfoModel.getDeliveryMode());
		productInfoVo.setFailedReason(productInfoModel.getFailedReason());
		productInfoVo.setGrossWeight(productInfoModel.getGrossWeight());
		productInfoVo.setLastUpdatedBy(productInfoModel.getLastUpdatedBy());
		productInfoVo.setLastUpdatedDate(productInfoModel.getLastUpdatedDate());
		productInfoVo.setLongDescEn(productInfoModel.getLongDescEn());
		productInfoVo.setLongDescSc(productInfoModel.getLongDescSc());
		productInfoVo.setLongDescTc(productInfoModel.getLongDescTc());
		productInfoVo.setSizeDesc(productInfoModel.getSizeDesc());
		productInfoVo.setStandardUom(productInfoModel.getStandardUom());
		productInfoVo.setProductStatus(productInfoModel.getStatus());
		if("DRAFT".equals(productInfoModel.getStatus())){
			productInfoVo.setStatus("Draft");
		}else if("SUBMIT_FOR_APPROVAL".equals(productInfoModel.getStatus())){
			productInfoVo.setStatus("Pending for approval");
		}else if("APPROVED".equals(productInfoModel.getStatus())){
			productInfoVo.setStatus("Approved");
		}else if("UPLOADED".equals(productInfoModel.getStatus())){
			productInfoVo.setStatus("Uploaded");
		}else if("AUTO_APPROVED".equals(productInfoModel.getStatus())){
			productInfoVo.setStatus("Auto Approved");
		}else if("APPROVED_IN_RETEK".equals(productInfoModel.getStatus())){
			productInfoVo.setStatus("Approved in Retek");
		}else if("UNAPPROVED_IN_RETEK".equals(productInfoModel.getStatus())){
			productInfoVo.setStatus("Unapproved in Retek");
		}else if("REJECTED".equals(productInfoModel.getStatus())){
			productInfoVo.setStatus("Rejected");
		}
		
		productInfoVo.setStorageDimHeight(productInfoModel.getStorageDimHeight());
		productInfoVo.setStorageDimLength(productInfoModel.getShippingDimLength());
		productInfoVo.setStorageDimWidth(productInfoModel.getStorageDimWidth());
		productInfoVo.setSupplierProductCode(productInfoModel.getSupplierProductCode());
		productInfoVo.setUnitCost(productInfoModel.getUnitCost());
		productInfoVo.setUnitRetail(productInfoModel.getUnitCost());
		productInfoVo.setSupplierCode(productInfoModel.getSupplierCode());
		productInfoVo.setSupplierName(productInfoModel.getSupplierName());
		productInfoVo.setProductCode(productInfoModel.getProductCode());
		productInfoVo.setShortDescEn(productInfoModel.getShortDescEn());
		productInfoVo.setDeliveryMode(productInfoModel.getDeliveryMode());
		productInfoVo.setDailyInventory(String.valueOf(productInfoModel.getDailyInventory()));
		productInfoVo.setSupplierProfileUpdateInd(productInfoModel.getSupplierProfileUpdateInd());
		productInfoVo.setOnlineUpdatedInd(productInfoModel.getOnlineUpdatedInd());
		if(productInfoModel.getDailyInventory() != null){
		productInfoVo.setDailyInventory(productInfoModel.getDailyInventory().toString());
		}else{
			productInfoVo.setDailyInventory("");
		}
		return productInfoVo;
	}
	
	public ProductInfoModel converProductVoToModelForCsv(ProductInfoVo productInfoVo){
		ProductInfoModel productInfoModel = new ProductInfoModel();
		productInfoModel.setSupplierCode(productInfoVo.getSupplierCode());
		productInfoModel.setOriginCountry(productInfoVo.getOriginCountry());
		productInfoModel.setProductCode(productInfoVo.getProductCode());
		productInfoModel.setDept(productInfoVo.getDept());
		productInfoModel.setClazz(productInfoVo.getClazz());
		productInfoModel.setSubClass(productInfoVo.getSubClass());
		productInfoModel.setCaseDimWidth(productInfoVo.getCaseDimWidth());
		productInfoModel.setStandardUom(productInfoVo.getStandardUom());
		productInfoModel.setShortDescEn(productInfoVo.getShortDescEn());
		productInfoModel.setCaseDimLength(productInfoVo.getCaseDimLength());
		productInfoModel.setCaseDimWidth(productInfoVo.getCaseDimWidth());
		productInfoModel.setCaseDimHeight(productInfoVo.getCaseDimHeight());
		productInfoModel.setCasePackCase(productInfoVo.getCasePackCase());
		productInfoModel.setCasePackInner(productInfoVo.getCasePackInner());
		productInfoModel.setUnitCost(productInfoVo.getUnitCost());
		productInfoModel.setUnitRetail(productInfoVo.getUnitRetail());
		productInfoModel.setProductDimLength(productInfoVo.getProductDimLength());
		productInfoModel.setProductDimWidth(productInfoVo.getProductDimWidth());
		productInfoModel.setProductDimHeight(productInfoVo.getProductDimHeight());
		productInfoModel.setProductDimWidth(productInfoVo.getProductDimWidth());
		productInfoModel.setSupplierProductCode(productInfoVo.getSupplierProductCode());
		productInfoModel.setConsignmentRate(productInfoVo.getConsignmentRate());
//		productInfoModel.setBaseProductCode(productInfoVo.getBaseProductCode());
		productInfoModel.setShortDescEn(productInfoVo.getShortDescEn());
		productInfoModel.setShortDescSc(productInfoVo.getShortDescSc());
		productInfoModel.setShortDescTc(productInfoVo.getShortDescTc());
		productInfoModel.setLongDescEn(productInfoVo.getLongDescEn());
		productInfoModel.setProductUsageEn(productInfoVo.getProductUsageEn());
		productInfoModel.setProductUsageSc(productInfoVo.getProductUsageSc());
		productInfoModel.setProductUsageTc(productInfoVo.getProductUsageTc());
		productInfoModel.setProductWarningsEn(productInfoVo.getProductWarningsEn());
		productInfoModel.setProductWarningsSc(productInfoVo.getProductWarningsSc());
		productInfoModel.setProductWarningsTc(productInfoVo.getProductWarningsTc());
		productInfoModel.setStorageConditionEn(productInfoVo.getStorageConditionEn());
		productInfoModel.setStorageConditionSc(productInfoVo.getStorageConditionSc());
		productInfoModel.setStorageConditionTc(productInfoVo.getStorageConditionTc());
		productInfoModel.setProductIngredientsEn(productInfoVo.getProductIngredientsEn());
		productInfoModel.setProductIngredientsSc(productInfoVo.getProductIngredientsSc());
		productInfoModel.setProductIngredientsTc(productInfoVo.getProductIngredientsTc());
		productInfoModel.setGrossWeight(productInfoVo.getGrossWeight());
		if(!"".equals(productInfoVo.getConsignmentType())){
			productInfoModel.setConsignmentType(productInfoVo.getConsignmentType());
		}
		if(!"".equals(productInfoVo.getConsignmentCalBasis())){
			productInfoModel.setConsignmentCalBasis(productInfoVo.getConsignmentCalBasis());
		}
		productInfoModel.setShippingDimHeight(productInfoVo.getShippingDimHeight());
		productInfoModel.setStandardUom(productInfoVo.getStandardUom());
		productInfoModel.setShippingDimWidth(productInfoVo.getShippingDimWidth());
		productInfoModel.setWeightUnit(productInfoVo.getWeightUnit());
		return productInfoModel;
	}

}
