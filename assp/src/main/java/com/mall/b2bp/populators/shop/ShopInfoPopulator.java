package com.mall.b2bp.populators.shop;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.models.shop.ShopInfoModel;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.shop.ShopInfoViewVo;

public class ShopInfoPopulator {
	
	public ShopInfoViewVo converModelToVo(ShopInfoModel shopInfoModel){
		return converModelToVo(shopInfoModel, null);
	}
	
	public ShopInfoViewVo converModelToVo(ShopInfoModel shopInfoModel, String path) {
		ShopInfoViewVo shopVo = new ShopInfoViewVo();
		shopVo.setId(shopInfoModel.getId());
		shopVo.setAddress(shopInfoModel.getAddress());
		shopVo.setAddress2(shopInfoModel.getAddress2());
		shopVo.setAddress3(shopInfoModel.getAddress3());
		shopVo.setAddress4(shopInfoModel.getAddress4());		
		shopVo.setContactEmail(shopInfoModel.getContactEmail());
		shopVo.setContactPerson(shopInfoModel.getContactPerson());
		shopVo.setContractStartDate(shopInfoModel.getContractStartDate());
		shopVo.setContractEndDate(shopInfoModel.getContractEndDate());
		shopVo.setCreatedBy(shopInfoModel.getCreatedBy());
		shopVo.setCreatedDate(shopInfoModel.getCreatedDate());
		shopVo.setFax(shopInfoModel.getFax());
		shopVo.setLastUpdatedBy(shopInfoModel.getLastUpdatedBy());
		shopVo.setLastUpdatedDate(shopInfoModel.getLastUpdatedDate());
		shopVo.setRespPerson(shopInfoModel.getRespPerson());
		shopVo.setShopCode(shopInfoModel.getShopCode());
		shopVo.setShopName(shopInfoModel.getShopName());
		shopVo.setTelNo(shopInfoModel.getTelNo());
		shopVo.setUseShopCartInd(shopInfoModel.getUseShopCartInd());
		shopVo.setWebsiteActiveInd(shopInfoModel.getWebsiteActiveInd());
		shopVo.setWebsiteContactEmail(shopInfoModel.getWebsiteContactEmail());
		shopVo.setWebsiteDispSeq(shopInfoModel.getWebsiteDispSeq());
		shopVo.setWebsiteDomain(shopInfoModel.getWebsiteDomain());
		shopVo.setWebsiteEmailDomain(shopInfoModel.getWebsiteEmailDomain());
		shopVo.setWebsiteInactiveDate(shopInfoModel.getWebsiteInactiveDate());
		shopVo.setWebsiteName(shopInfoModel.getWebsiteName());
		shopVo.setWebsiteOnlineDatetime(shopInfoModel.getWebsiteOnlineDatetime());
		shopVo.setWebsiteStatus(shopInfoModel.getWebsiteStatus());
		shopVo.setWebsiteStatusReasonDesc(shopInfoModel.getWebsiteStatusReasonDesc());
		shopVo.setWebsiteStatusReasonId(shopInfoModel.getWebsiteStatusReasonId());
		shopVo.setLogoName(shopInfoModel.getLogoName());
		shopVo.setDataTotal(shopInfoModel.getDataTotal());
		
		if(StringUtils.isNotEmpty(path) && StringUtils.isNotEmpty(shopInfoModel.getLogoName())) {
			String suffix = "300Wx300H";
			String ext = "jpeg";
	        String mediaCode = shopInfoModel.getLogoName();
	        if(shopInfoModel.getLogoName().indexOf(".")>-1){
	             mediaCode = shopInfoModel.getLogoName().substring(0,shopInfoModel.getLogoName().lastIndexOf("."));
	             ext = shopInfoModel.getLogoName().substring(shopInfoModel.getLogoName().lastIndexOf(".")+1);
	        }
	        String storePath = ResourceUtil.getSystemConfig().getProperty("upload_file_resizepath");
	        String newfilename=mediaCode+"_"+ suffix.toLowerCase()+"."+ext;
			int index = storePath.indexOf("webapps");
			path = path+"/"+storePath.substring(index+8, storePath.length());		
			path = path +"/"+newfilename;
			if(StringUtils.isNotEmpty(shopInfoModel.getLogoName())) {
				shopVo.setLogoPath(path);
			}
		}
		shopVo.setShopDesc(shopInfoModel.getShopDesc());
		shopVo.setMallId(shopInfoModel.getMallId());
		return shopVo;
	}
	
	public ShopInfoModel converVoToModel(ShopInfoViewVo shopVo,SessionService sessionService){
		ShopInfoModel shopInfoModel = new ShopInfoModel();
		if(shopVo.getId() != null){
			shopInfoModel.setId(shopVo.getId());
		}
		
		if(StringUtils.isNotEmpty(shopVo.getAddress())) {
			shopInfoModel.setAddress(shopVo.getAddress());
		}
		if(StringUtils.isNotEmpty(shopVo.getAddress2())) {
			shopInfoModel.setAddress2(shopVo.getAddress2());
		}
		if(StringUtils.isNotEmpty(shopVo.getAddress3())) {
			shopInfoModel.setAddress3(shopVo.getAddress3());
		}
		if(StringUtils.isNotEmpty(shopVo.getAddress4())) {
			shopInfoModel.setAddress4(shopVo.getAddress4());
		}
		if(StringUtils.isNotEmpty(shopVo.getContactEmail())) {
			shopInfoModel.setContactEmail(shopVo.getContactEmail());
		}
		if(StringUtils.isNotEmpty(shopVo.getContactPerson())) {
			shopInfoModel.setContactPerson(shopVo.getContactPerson());
		}
		if(shopVo.getContractStartDate() != null) {
			shopInfoModel.setContractStartDate(shopVo.getContractStartDate());
		}
		if(shopVo.getContractEndDate() != null) {
			shopInfoModel.setContractEndDate(shopVo.getContractEndDate());
		}
		if(StringUtils.isNotEmpty(shopVo.getFax())) {
			shopInfoModel.setFax(shopVo.getFax());
		}
		if(StringUtils.isNotEmpty(shopVo.getRespPerson())) {
			shopInfoModel.setRespPerson(shopVo.getRespPerson());
		}
		if(StringUtils.isNotEmpty(shopVo.getShopCode())) {
			shopInfoModel.setShopCode(shopVo.getShopCode());
		}
		if(StringUtils.isNotEmpty(shopVo.getShopName())) {
			shopInfoModel.setShopName(shopVo.getShopName());
		}
		if(StringUtils.isNotEmpty(shopVo.getTelNo())) {
			shopInfoModel.setTelNo(shopVo.getTelNo());
		}
		if(StringUtils.isNotEmpty(shopVo.getUseShopCartInd())) {
			shopInfoModel.setUseShopCartInd(shopVo.getUseShopCartInd());
		}
		if(StringUtils.isNotEmpty(shopVo.getWebsiteActiveInd())) {
			shopInfoModel.setWebsiteActiveInd(shopVo.getWebsiteActiveInd());
		}
		if(StringUtils.isNotEmpty(shopVo.getWebsiteContactEmail())) {
			shopInfoModel.setWebsiteContactEmail(shopVo.getWebsiteContactEmail());
		}
		if(shopVo.getWebsiteDispSeq() != null) {
			shopInfoModel.setWebsiteDispSeq(shopVo.getWebsiteDispSeq());
		}
		if(StringUtils.isNotEmpty(shopVo.getWebsiteDomain())) {
			shopInfoModel.setWebsiteDomain(shopVo.getWebsiteDomain());
		}
		if(StringUtils.isNotEmpty(shopVo.getWebsiteEmailDomain())) {
			shopInfoModel.setWebsiteEmailDomain(shopVo.getWebsiteEmailDomain());
		}
		if(shopVo.getWebsiteInactiveDate() != null) {
			shopInfoModel.setWebsiteInactiveDate(shopVo.getWebsiteInactiveDate());
		}
		if(StringUtils.isNotEmpty(shopVo.getWebsiteName())) {
			shopInfoModel.setWebsiteName(shopVo.getWebsiteName());
		}
		if(shopVo.getWebsiteOnlineDatetime() != null) {
			shopInfoModel.setWebsiteOnlineDatetime(shopVo.getWebsiteOnlineDatetime());
		}
		if(shopVo.getWebsiteStatus() != null) {
			shopInfoModel.setWebsiteStatus(shopVo.getWebsiteStatus());
		}
		if(StringUtils.isNotEmpty(shopVo.getWebsiteStatusReasonDesc())) {
			shopInfoModel.setWebsiteStatusReasonDesc(shopVo.getWebsiteStatusReasonDesc());
		}
		if(shopVo.getWebsiteStatusReasonId() != null) {
			shopInfoModel.setWebsiteStatusReasonId(shopVo.getWebsiteStatusReasonId());
		}
		if(shopVo.getLogoName() != null) {
			shopInfoModel.setLogoName(shopVo.getLogoName());
		}
		if(shopVo.getShopDesc() != null) {
			shopInfoModel.setShopDesc(shopVo.getShopDesc());
		}
		if(sessionService.getCurrentUser()!=null)
			shopInfoModel.setCreatedBy(sessionService.getCurrentUser().getUserName());
		shopInfoModel.setCreatedDate(new Date());
		
		if(sessionService.getCurrentUser()!=null)
			shopInfoModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
		shopInfoModel.setLastUpdatedDate(new Date());
		
		if(sessionService.getCurrentUser()!=null && sessionService.getCurrentUser().getMallId() !=null) {
			shopInfoModel.setMallId(new BigDecimal(sessionService.getCurrentUser().getMallId()));
		} else {
			shopInfoModel.setMallId(shopVo.getMallId());
		}
		shopInfoModel.setDataTotal(shopVo.getDataTotal());
		return shopInfoModel;
	}
}
