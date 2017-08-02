package com.mall.b2bp.oxm.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderReturnBean {

	
	public OrderReturnBean(){
		entryBeanList=new ArrayList<>();
	}
	
	private List<OrderReturnEntryBean> entryBeanList;
	
    
	 private BigDecimal id;

	    private BigDecimal orderId;

	    private String hybrisOrderId;

	    private String hybrisReturnId;

	    private BigDecimal returnType;

	    private BigDecimal pickStore;

	    private String supplierId;

	    private String returnCreateDate;

	    private String specialInstruction;

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

	    private String collectDistrict;

	    private String contactMobileNo;

	    private String contactPhoneNo;

	    private String collectAddress;

	    private String createdBy;

	    private Date createdDate;

	    private String lastUpdatedBy;

	    private Date lastUpdatedDate;

	    private String remark;

	 

	    private String pickOrderId;
	    
    public void addEntryBean(OrderReturnEntryBean orderEntryBean){
		entryBeanList.add(orderEntryBean);
	}
	public List<OrderReturnEntryBean> getEntryBeanList() {
		return entryBeanList;
	}
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
	public String getHybrisOrderId() {
		return hybrisOrderId;
	}
	public void setHybrisOrderId(String hybrisOrderId) {
		this.hybrisOrderId = hybrisOrderId;
	}
	
	public BigDecimal getReturnType() {
		return returnType;
	}
	public void setReturnType(BigDecimal returnType) {
		this.returnType = returnType;
	}
	public BigDecimal getPickStore() {
		return pickStore;
	}
	public void setPickStore(BigDecimal pickStore) {
		this.pickStore = pickStore;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getSpecialInstruction() {
		return specialInstruction;
	}
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
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
	public String getCollectDistrict() {
		return collectDistrict;
	}
	public void setCollectDistrict(String collectDistrict) {
		this.collectDistrict = collectDistrict;
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
	public String getCollectAddress() {
		return collectAddress;
	}
	public void setCollectAddress(String collectAddress) {
		this.collectAddress = collectAddress;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getPickOrderId() {
		return pickOrderId;
	}
	public void setPickOrderId(String pickOrderId) {
		this.pickOrderId = pickOrderId;
	}
	public void setEntryBeanList(List<OrderReturnEntryBean> entryBeanList) {
		this.entryBeanList = entryBeanList;
	}
	public String getReturnCreateDate() {
		return returnCreateDate;
	}
	public void setReturnCreateDate(String returnCreateDate) {
		this.returnCreateDate = returnCreateDate;
	}
	public String getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}
	public String getHybrisReturnId() {
		return hybrisReturnId;
	}
	public void setHybrisReturnId(String hybrisReturnId) {
		this.hybrisReturnId = hybrisReturnId;
	}
	
	
	
	
	
   
    
    
    
}
