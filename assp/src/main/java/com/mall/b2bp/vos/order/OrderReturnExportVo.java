package com.mall.b2bp.vos.order;

import java.math.BigDecimal;


public class OrderReturnExportVo {
    private String orderId;
    private String hybrisReturnId;
    private String returnCreateDate;
    private String returnRequestStatus;
    private String returnRequestUpdateDate;
    private BigDecimal customerId;
    private String customerType;
    private String customerName;
    private String customerPhoneNo;
    private String customerMobileNo;
    private String tenderType;
    private String paymentRef;
    private String collectDate;
    private String collectTimeSlot;
    private String contactName;
    private String contactMobileNo;
    private String contactPhoneNo;
    private String collectDistrict;
    private String collectAddress;
    private String remark;
    private String specialinstruction;
    
    private String skuId;
    private String brand;
    private String brandSec;
    private String productName;
    private String productNameSec;
    private String sizeDesc;
    private BigDecimal orderQty;
    private BigDecimal returnReqQty;
    private BigDecimal expectedQty;
    private BigDecimal actualCollectedQty;
    private BigDecimal writeOffQty;
    private String skuCollectRmk;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getHybrisReturnId() {
		return hybrisReturnId;
	}
	public void setHybrisReturnId(String hybrisReturnId) {
		this.hybrisReturnId = hybrisReturnId;
	}
	public String getReturnCreateDate() {
		return returnCreateDate;
	}
	public void setReturnCreateDate(String returnCreateDate) {
		this.returnCreateDate = returnCreateDate;
	}
	public String getReturnRequestStatus() {
		return returnRequestStatus;
	}
	public void setReturnRequestStatus(String returnRequestStatus) {
		this.returnRequestStatus = returnRequestStatus;
	}
	public String getReturnRequestUpdateDate() {
		return returnRequestUpdateDate;
	}
	public void setReturnRequestUpdateDate(String returnRequestUpdateDate) {
		this.returnRequestUpdateDate = returnRequestUpdateDate;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhoneNo() {
		return customerPhoneNo;
	}
	public void setCustomerPhoneNo(String customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}
	public String getCustomerMobileNo() {
		return customerMobileNo;
	}
	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}
	public String getTenderType() {
		return tenderType;
	}
	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}
	public String getPaymentRef() {
		return paymentRef;
	}
	public void setPaymentRef(String paymentRef) {
		this.paymentRef = paymentRef;
	}
	public String getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}
	public String getCollectTimeSlot() {
		return collectTimeSlot;
	}
	public void setCollectTimeSlot(String collectTimeSlot) {
		this.collectTimeSlot = collectTimeSlot;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactMobileNo() {
		return contactMobileNo;
	}
	public void setContactMobileNo(String contactMobileNo) {
		this.contactMobileNo = contactMobileNo;
	}
	public String getContactPhoneNo() {
		return contactPhoneNo;
	}
	public void setContactPhoneNo(String contactPhoneNo) {
		this.contactPhoneNo = contactPhoneNo;
	}
	public String getCollectDistrict() {
		return collectDistrict;
	}
	public void setCollectDistrict(String collectDistrict) {
		this.collectDistrict = collectDistrict;
	}
	public String getCollectAddress() {
		return collectAddress;
	}
	public void setCollectAddress(String collectAddress) {
		this.collectAddress = collectAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSpecialinstruction() {
		return specialinstruction;
	}
	public void setSpecialinstruction(String specialinstruction) {
		this.specialinstruction = specialinstruction;
	}
	
	
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBrandSec() {
		return brandSec;
	}
	public void setBrandSec(String brandSec) {
		this.brandSec = brandSec;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductNameSec() {
		return productNameSec;
	}
	public void setProductNameSec(String productNameSec) {
		this.productNameSec = productNameSec;
	}
	public String getSizeDesc() {
		return sizeDesc;
	}
	public void setSizeDesc(String sizeDesc) {
		this.sizeDesc = sizeDesc;
	}
	public BigDecimal getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(BigDecimal orderQty) {
		this.orderQty = orderQty;
	}
	public BigDecimal getReturnReqQty() {
		return returnReqQty;
	}
	public void setReturnReqQty(BigDecimal returnReqQty) {
		this.returnReqQty = returnReqQty;
	}
	public BigDecimal getExpectedQty() {
		return expectedQty;
	}
	public void setExpectedQty(BigDecimal expectedQty) {
		this.expectedQty = expectedQty;
	}
	public BigDecimal getActualCollectedQty() {
		return actualCollectedQty;
	}
	public void setActualCollectedQty(BigDecimal actualCollectedQty) {
		this.actualCollectedQty = actualCollectedQty;
	}
	public BigDecimal getWriteOffQty() {
		return writeOffQty;
	}
	public void setWriteOffQty(BigDecimal writeOffQty) {
		this.writeOffQty = writeOffQty;
	}
	public String getSkuCollectRmk() {
		return skuCollectRmk;
	}
	public void setSkuCollectRmk(String skuCollectRmk) {
		this.skuCollectRmk = skuCollectRmk;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	



}
