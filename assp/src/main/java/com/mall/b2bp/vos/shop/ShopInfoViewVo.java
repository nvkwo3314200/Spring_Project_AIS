package com.mall.b2bp.vos.shop;

import java.math.BigDecimal;
import java.util.Date;

import com.mall.b2bp.vos.BaseVo;

public class ShopInfoViewVo extends BaseVo {
	private BigDecimal id;

	private String shopCode;

	private String shopName;

	private String respPerson;

	private String contactPerson;

	private String contactEmail;

	private String telNo;

	private String fax;
	
	private String address;
	
	private String address2;
	
	private String address3;
	
	private String address4;
	
	private Date contractStartDate;
	
	private Date contractEndDate;
	
	private String websiteName;
	
	private String websiteDomain;
	
	private String websiteEmailDomain;
	
	private String websiteContactEmail;
	
	private Integer websiteStatus; //1.正式上線／2.建置中／3.專案進駐／4.系統網站／5.網站關閉 
	
	private Integer websiteDispSeq;
	
	private String useShopCartInd;  // Y/N

	private Date websiteOnlineDatetime;
	
	private String websiteActiveInd; // Y/N
	
	private Date websiteInactiveDate;
	
	private BigDecimal websiteStatusReasonId;
	
	private String websiteStatusReasonDesc;
	
	private String logoName;
	
	private String shopDesc;
	
	private String logoPath;
	
	private BigDecimal mallId;
	
	//function
	private boolean saveSuccess;
	
	private boolean syncSuccess;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getRespPerson() {
		return respPerson;
	}

	public void setRespPerson(String respPerson) {
		this.respPerson = respPerson;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getWebsiteDomain() {
		return websiteDomain;
	}

	public void setWebsiteDomain(String websiteDomain) {
		this.websiteDomain = websiteDomain;
	}

	public String getWebsiteEmailDomain() {
		return websiteEmailDomain;
	}

	public void setWebsiteEmailDomain(String websiteEmailDomain) {
		this.websiteEmailDomain = websiteEmailDomain;
	}
	
	public String getWebsiteContactEmail() {
		return websiteContactEmail;
	}

	public void setWebsiteContactEmail(String websiteContactEmail) {
		this.websiteContactEmail = websiteContactEmail;
	}

	public Integer getWebsiteStatus() {
		return websiteStatus;
	}

	public void setWebsiteStatus(Integer websiteStatus) {
		this.websiteStatus = websiteStatus;
	}

	public Integer getWebsiteDispSeq() {
		return websiteDispSeq;
	}

	public void setWebsiteDispSeq(Integer websiteDispSeq) {
		this.websiteDispSeq = websiteDispSeq;
	}
	
	public String getUseShopCartInd() {
		return useShopCartInd;
	}

	public void setUseShopCartInd(String useShopCartInd) {
		this.useShopCartInd = useShopCartInd;
	}

	public String getWebsiteActiveInd() {
		return websiteActiveInd;
	}

	public void setWebsiteActiveInd(String websiteActiveInd) {
		this.websiteActiveInd = websiteActiveInd;
	}

	public BigDecimal getWebsiteStatusReasonId() {
		return websiteStatusReasonId;
	}

	public void setWebsiteStatusReasonId(BigDecimal websiteStatusReasonId) {
		this.websiteStatusReasonId = websiteStatusReasonId;
	}

	public String getWebsiteStatusReasonDesc() {
		return websiteStatusReasonDesc;
	}

	public void setWebsiteStatusReasonDesc(String websiteStatusReasonDesc) {
		this.websiteStatusReasonDesc = websiteStatusReasonDesc;
	}

	public Date getWebsiteOnlineDatetime() {
		return websiteOnlineDatetime;
	}

	public void setWebsiteOnlineDatetime(Date websiteOnlineDatetime) {
		this.websiteOnlineDatetime = websiteOnlineDatetime;
	}

	public Date getWebsiteInactiveDate() {
		return websiteInactiveDate;
	}

	public void setWebsiteInactiveDate(Date websiteInactiveDate) {
		this.websiteInactiveDate = websiteInactiveDate;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getShopDesc() {
		return shopDesc;
	}

	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	public BigDecimal getMallId() {
		return mallId;
	}

	public void setMallId(BigDecimal mallId) {
		this.mallId = mallId;
	}

	public boolean isSaveSuccess() {
		return saveSuccess;
	}

	public void setSaveSuccess(boolean saveSuccess) {
		this.saveSuccess = saveSuccess;
	}

	public boolean isSyncSuccess() {
		return syncSuccess;
	}

	public void setSyncSuccess(boolean syncSuccess) {
		this.syncSuccess = syncSuccess;
	}
	
	
}
