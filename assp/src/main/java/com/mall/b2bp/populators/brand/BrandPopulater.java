package com.mall.b2bp.populators.brand;

import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.vos.brand.BrandVo;


public class BrandPopulater {

	
	 public BrandVo converModelToVo(BrandModel brand) {
		 BrandVo vo = new BrandVo();

	        if (brand != null) {
	        		vo.setCode (brand.getCode());
	        		vo.setBrandCode(brand.getBrandCode());
	        		vo.setSysRef(brand.getSysRef());
	        		vo.setMasterId(brand.getMasterId());
	        		vo.setCreatedBy(brand.getCreatedBy());
	        		vo.setCreatedDate(brand.getCreatedDate());
	        		vo.setLastUpdatedBy(brand.getLastUpdatedBy());
	        		vo.setLastUpdatedDate(brand.getLastUpdatedDate());
	        		
	        		if(vo.getMasterId()!=null)
	        			vo.setMasterIdStr(vo.getMasterId().toString());
	        		
	        		if(vo.getCode()!=null)
	        			vo.setCodeStr(vo.getCode().toString());
	        		
	        		vo.setDescEn(brand.getDescEn());
	        		vo.setDescSc(brand.getDescSc());
	        		vo.setDescTc(brand.getDescTc());

	        		vo.setBrandNameEn(brand.getBrandNameEn());
	    			vo.setBrandNameTc(brand.getBrandNameTc());
	    			vo.setBrandNameSc(brand.getBrandNameSc());

	    			vo.setBrandDescEn(brand.getBrandDescEn());
	    			vo.setBrandDescTc(brand.getBrandDescTc());
	    			vo.setBrandDescSc(brand.getBrandDescSc());

	    			vo.setBrandTaglineEn(brand.getBrandTaglineEn());
	    			vo.setBrandTaglineTc(brand.getBrandTaglineTc());
	    			vo.setBrandTaglineSc(brand.getBrandTaglineSc());
	        		
	        		
	        		vo.setImageFileName(brand.getImageFileName());
	        		vo.setWatsonsMallInd(brand.getWatsonsMallInd());
	        		vo.setSupplierId(brand.getSupplierId());
	        }
	       
	        return vo;
	 }

	        	
}
