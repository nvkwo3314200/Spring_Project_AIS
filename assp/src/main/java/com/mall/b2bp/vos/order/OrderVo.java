package com.mall.b2bp.vos.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by USER on 2016/3/10.
 */
public class OrderVo {

	private List<OrderEntryVo> entryVoList;

	private BigDecimal id;

	private String hybrisOrderId;
	private String waitForUpdateStatus;
	private String trackId;
	private String boxNum;
	private String deliverySuccess;

	private Date orderDatetime;
	
	private Date pickDate;
	private Long pickDateLong;
	private String pickDateStr;
	
	private String orderDatetimeStr;
	private String invoiceReadyInd;

	private Long orderDatetimeLong;

	private BigDecimal pickStore;

	private String pickType;

	private String supplierName;

	private String specialInstruction;

	private String supplierId;

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

	private Date deliveryDate;
	private Long deliveryDateLong;
	private String deliveryDateStr;
	
	private Date collectDate;
	private Long collectDateLong;
	private String collectDateStr;

	private String collectMethod;

	private String deliveryTimeslot;

	private String shipDistrict;

	private String receiverName;

	private String receiverPhoneNo;

	private String receiverMobileNo;

	private String deliveryAddress;

	private String showPrice;

	private String invoiceFilename;

	private String dnFilename;

	private String remark;

	private String status;
	private String entryStatus;
	
	
	private String statusDesc;

	private String createdBy;

	private Date createdDate;

	private String lastUpdatedBy;

	private Date lastUpdatedDate;

	private String pickOrderId;

	private String consignmentStatus;
	private String consignmentStatusDesc;

	private Date consignmentShippedDate;
	private Long shippedDateLong;
	private String orderType;
	private String orderTypeDesc;
 

	private String returnRequest;

	private String pickedInd;
	private String shippedInd;
	private String deliveryInd;

	private String outstandingReturnRequest;
	private BigDecimal sizeWaitReturn;
	private BigDecimal totalRetrnRequest;
	private BigDecimal origOrderId;// ORIG_ORDER_ID;
	private String replaceOrderInd;// REPLACE_ORDER_IND
	
	private String orderStatus;
	private String deliveryFlag;
	

	private String origHybrisOrderId;

	public String getPickedInd() {
		return pickedInd;
	}

	public void setPickedInd(String pickedInd) {
		this.pickedInd = pickedInd;
	}

	public String getShippedInd() {
		return shippedInd;
	}

	public void setShippedInd(String shippedInd) {
		this.shippedInd = shippedInd;
	}

	public String getDeliveryInd() {
		return deliveryInd;
	}

	public void setDeliveryInd(String deliveryInd) {
		this.deliveryInd = deliveryInd;
	}

	public String getReturnRequest() {
		return returnRequest;
	}

	public void setReturnRequest(String returnRequest) {
		this.returnRequest = returnRequest;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getInvoiceReadyInd() {
		return invoiceReadyInd;
	}

	public void setInvoiceReadyInd(String invoiceReadyInd) {
		this.invoiceReadyInd = invoiceReadyInd;
	}

	public Long getOrderDatetimeLong() {
		return orderDatetimeLong;
	}

	public void setOrderDatetimeLong(Long orderDatetimeLong) {
		this.orderDatetimeLong = orderDatetimeLong;
	}

	public String getWaitForUpdateStatus() {
		return waitForUpdateStatus;
	}

	public void setWaitForUpdateStatus(String waitForUpdateStatus) {
		this.waitForUpdateStatus = waitForUpdateStatus;
	}

	public Long getShippedDateLong() {
		return shippedDateLong;
	}

	public void setShippedDateLong(Long shippedDateLong) {
		this.shippedDateLong = shippedDateLong;
	}

	private String consignmentShippedDateStr;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getHybrisOrderId() {
		return hybrisOrderId;
	}

	public List<OrderEntryVo> getEntryVoList() {
		return entryVoList;
	}

	public void setEntryVoList(List<OrderEntryVo> entryVoList) {
		this.entryVoList = entryVoList;
	}

	public String getOrderDatetimeStr() {
		return orderDatetimeStr;
	}

	public void setOrderDatetimeStr(String orderDatetimeStr) {
		this.orderDatetimeStr = orderDatetimeStr;
	}

	public String getDeliveryDateStr() {
		return deliveryDateStr;
	}

	public void setDeliveryDateStr(String deliveryDateStr) {
		this.deliveryDateStr = deliveryDateStr;
	}
	
	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public Long getCollectDateLong() {
		return collectDateLong;
	}

	public void setCollectDateLong(Long collectDateLong) {
		this.collectDateLong = collectDateLong;
	}

	public String getCollectDateStr() {
		return collectDateStr;
	}

	public void setCollectDateStr(String collectDateStr) {
		this.collectDateStr = collectDateStr;
	}

	public String getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}

	public String getConsignmentShippedDateStr() {
		return consignmentShippedDateStr;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getConsignmentStatusDesc() {
		return consignmentStatusDesc;
	}

	public void setConsignmentStatusDesc(String consignmentStatusDesc) {
		this.consignmentStatusDesc = consignmentStatusDesc;
	}

	public void setConsignmentShippedDateStr(String consignmentShippedDateStr) {
		this.consignmentShippedDateStr = consignmentShippedDateStr;
	}

	public void setHybrisOrderId(String hybrisOrderId) {
		this.hybrisOrderId = hybrisOrderId == null ? null : hybrisOrderId
				.trim();
	}

	public Date getOrderDatetime() {
		return orderDatetime;
	}

	public void setOrderDatetime(Date orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public BigDecimal getPickStore() {
		return pickStore;
	}

	public void setPickStore(BigDecimal pickStore) {
		this.pickStore = pickStore;
	}

	public String getPickType() {
		return pickType;
	}

	public void setPickType(String pickType) {
		this.pickType = pickType == null ? null : pickType.trim();
	}

	public String getSpecialInstruction() {
		return specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction == null ? null
				: specialInstruction.trim();
	}

	public String getPickDateStr() {
		return pickDateStr;
	}

	public void setPickDateStr(String pickDateStr) {
		this.pickDateStr = pickDateStr;
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
		this.customerType = customerType == null ? null : customerType.trim();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public String getCustomerPhoneNo() {
		return customerPhoneNo;
	}

	public void setCustomerPhoneNo(String customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo == null ? null : customerPhoneNo
				.trim();
	}

	public String getCustomerMobileNo() {
		return customerMobileNo;
	}

	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo == null ? null
				: customerMobileNo.trim();
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType == null ? null : tenderType.trim();
	}

	public String getPaymentRef() {
		return paymentRef;
	}

	public void setPaymentRef(String paymentRef) {
		this.paymentRef = paymentRef == null ? null : paymentRef.trim();
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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTimeslot() {
		return deliveryTimeslot;
	}

	public void setDeliveryTimeslot(String deliveryTimeslot) {
		this.deliveryTimeslot = deliveryTimeslot == null ? null
				: deliveryTimeslot.trim();
	}

	public String getShipDistrict() {
		return shipDistrict;
	}

	public void setShipDistrict(String shipDistrict) {
		this.shipDistrict = shipDistrict == null ? null : shipDistrict.trim();
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName == null ? null : receiverName.trim();
	}

	public String getReceiverPhoneNo() {
		return receiverPhoneNo;
	}

	public void setReceiverPhoneNo(String receiverPhoneNo) {
		this.receiverPhoneNo = receiverPhoneNo == null ? null : receiverPhoneNo
				.trim();
	}

	public String getReceiverMobileNo() {
		return receiverMobileNo;
	}

	public void setReceiverMobileNo(String receiverMobileNo) {
		this.receiverMobileNo = receiverMobileNo == null ? null
				: receiverMobileNo.trim();
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress
				.trim();
	}

	public String getShowPrice() {
		return showPrice;
	}

	public void setShowPrice(String showPrice) {
		this.showPrice = showPrice == null ? null : showPrice.trim();
	}

	public String getInvoiceFilename() {
		return invoiceFilename;
	}

	public void setInvoiceFilename(String invoiceFilename) {
		this.invoiceFilename = invoiceFilename == null ? null : invoiceFilename
				.trim();
	}

	public String getDnFilename() {
		return dnFilename;
	}

	public void setDnFilename(String dnFilename) {
		this.dnFilename = dnFilename == null ? null : dnFilename.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
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

	public String getPickOrderId() {
		return pickOrderId;
	}

	public void setPickOrderId(String pickOrderId) {
		this.pickOrderId = pickOrderId;
	}

	public String getConsignmentStatus() {
		return consignmentStatus;
	}

	public void setConsignmentStatus(String consignmentStatus) {
		this.consignmentStatus = consignmentStatus == null ? null
				: consignmentStatus.trim();
	}

	public Date getConsignmentShippedDate() {
		return consignmentShippedDate;
	}

	public void setConsignmentShippedDate(Date consignmentShippedDate) {
		this.consignmentShippedDate = consignmentShippedDate;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	public String getDeliverySuccess() {
		return deliverySuccess;
	}

	public void setDeliverySuccess(String deliverySuccess) {
		this.deliverySuccess = deliverySuccess;
	}

	public Date getPickDate() {
		return pickDate;
	}

	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
	}

	public Long getPickDateLong() {
		return pickDateLong;
	}

	public void setPickDateLong(Long pickDateLong) {
		this.pickDateLong = pickDateLong;
	}

	public Long getDeliveryDateLong() {
		return deliveryDateLong;
	}

	public void setDeliveryDateLong(Long deliveryDateLong) {
		this.deliveryDateLong = deliveryDateLong;
	}

	public String getOutstandingReturnRequest() {
		return outstandingReturnRequest;
	}

	public void setOutstandingReturnRequest(String outstandingReturnRequest) {
		this.outstandingReturnRequest = outstandingReturnRequest;
	}

	public BigDecimal getSizeWaitReturn() {
		return sizeWaitReturn;
	}

	public void setSizeWaitReturn(BigDecimal sizeWaitReturn) {
		this.sizeWaitReturn = sizeWaitReturn;
	}

	public BigDecimal getTotalRetrnRequest() {
		return totalRetrnRequest;
	}

	public void setTotalRetrnRequest(BigDecimal totalRetrnRequest) {
		this.totalRetrnRequest = totalRetrnRequest;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public BigDecimal getOrigOrderId() {
		return origOrderId;
	}

	public void setOrigOrderId(BigDecimal origOrderId) {
		this.origOrderId = origOrderId;
	}

	public String getReplaceOrderInd() {
		return replaceOrderInd;
	}

	public void setReplaceOrderInd(String replaceOrderInd) {
		this.replaceOrderInd = replaceOrderInd;
	}

	public String getOrigHybrisOrderId() {
		return origHybrisOrderId;
	}

	public void setOrigHybrisOrderId(String origHybrisOrderId) {
		this.origHybrisOrderId = origHybrisOrderId;
	}

	public String getOrderTypeDesc() {
		return orderTypeDesc;
	}

	public void setOrderTypeDesc(String orderTypeDesc) {
		this.orderTypeDesc = orderTypeDesc;
	}

	public String getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}
}
