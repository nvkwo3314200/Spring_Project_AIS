package com.mall.b2bp.oxm.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderBean {
	
	public OrderBean(){
		entryBeanList=new ArrayList<>();
	}
	private List<OrderEntryBean> entryBeanList;
	
	private BigDecimal pickStore;
	private String pickOrderId;
	private String hybrisOrderId;//orderid
	private String orderDatetime;
	private String lastUpdatedDate;
	private String remark;
	private String specialInstruction;
	private String pickType;
	private BigDecimal customerId;
	private String customerType;
	private String customerName;
	private String customerPhoneNo;
	private String customerMobileNo;
	private String tenderType;
	private String paymentRef;
	private BigDecimal totalAmount;
	private BigDecimal surcharge;
	private BigDecimal deliveryFee;
	private String deliveryDate;
	private String deliveryTimeslot;
	private String shipDistrict;
	private String receiverName;
	private String receiverPhoneNo;
	private String receiverMobileNo;
	private String deliveryAddress;
	private String showPrice;
	private String invoiceFilename;
	private String supplierId;

	private String externalOrderId;//ExternalOrderId
	private String externalLogisticCode;//ExternalLogisticCode
	private String deliveryPostalCode;//DeliveryPostalCode
	private String dcReferenceCode;//DCReferenceCode
	private String dcAddress;//DCAddress
	private String orderType;//TypeOfDistribution


	public void addEntryBean(OrderEntryBean orderEntryBean){
		entryBeanList.add(orderEntryBean);
	}
	public List<OrderEntryBean> getEntryBeanList() {
		return entryBeanList;
	}

	public void setEntryBeanList(List<OrderEntryBean> entryBeanList) {
		this.entryBeanList = entryBeanList;
	}
	public BigDecimal getPickStore() {
		return pickStore;
	}
	public void setPickStore(BigDecimal pickStore) {
		this.pickStore = pickStore;
	}
	public String getPickOrderId() {
		return pickOrderId;
	}
	public void setPickOrderId(String pickOrderId) {
		this.pickOrderId = pickOrderId;
	}
	public String getHybrisOrderId() {
		return hybrisOrderId;
	}
	public void setHybrisOrderId(String hybrisOrderId) {
		this.hybrisOrderId = hybrisOrderId;
	}
	public String getOrderDatetime() {
		return orderDatetime;
	}
	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSpecialInstruction() {
		return specialInstruction;
	}
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}
	public String getPickType() {
		return pickType;
	}
	public void setPickType(String pickType) {
		this.pickType = pickType;
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
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(BigDecimal surcharge) {
		this.surcharge = surcharge;
	}
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryTimeslot() {
		return deliveryTimeslot;
	}
	public void setDeliveryTimeslot(String deliveryTimeslot) {
		this.deliveryTimeslot = deliveryTimeslot;
	}
	public String getShipDistrict() {
		return shipDistrict;
	}
	public void setShipDistrict(String shipDistrict) {
		this.shipDistrict = shipDistrict;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhoneNo() {
		return receiverPhoneNo;
	}
	public void setReceiverPhoneNo(String receiverPhoneNo) {
		this.receiverPhoneNo = receiverPhoneNo;
	}
	public String getReceiverMobileNo() {
		return receiverMobileNo;
	}
	public void setReceiverMobileNo(String receiverMobileNo) {
		this.receiverMobileNo = receiverMobileNo;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(String showPrice) {
		this.showPrice = showPrice;
	}
	public String getInvoiceFilename() {
		return invoiceFilename;
	}
	public void setInvoiceFilename(String invoiceFilename) {
		this.invoiceFilename = invoiceFilename;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getExternalOrderId() {
		return externalOrderId;
	}
	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}
	public String getExternalLogisticCode() {
		return externalLogisticCode;
	}
	public void setExternalLogisticCode(String externalLogisticCode) {
		this.externalLogisticCode = externalLogisticCode;
	}
	public String getDeliveryPostalCode() {
		return deliveryPostalCode;
	}
	public void setDeliveryPostalCode(String deliveryPostalCode) {
		this.deliveryPostalCode = deliveryPostalCode;
	}
	public String getDcReferenceCode() {
		return dcReferenceCode;
	}
	public void setDcReferenceCode(String dcReferenceCode) {
		this.dcReferenceCode = dcReferenceCode;
	}
	public String getDcAddress() {
		return dcAddress;
	}
	public void setDcAddress(String dcAddress) {
		this.dcAddress = dcAddress;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	



}
