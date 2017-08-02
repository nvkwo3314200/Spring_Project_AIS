package com.mall.b2bp.vos.order;

import java.math.BigDecimal;

public class OrderExportVo {
	private String orderTypeDesc;
    private String hybrisOrderId;
    private String pickOrderId;
    
    private String orderType;
    private String status;
    
    private String receiverName;
    private String receiverPhoneNo;
    private String receiverMobileNo;
    private String deliveryAddress;
    
    private String customerName;
    private String customerPhoneNo;
    private String customerMobileNo;
    
    private String orderDatetime;
    private String consignmentShippedDate;
    private String deliveryDate;
    
    private String skuId;
    private String supplierProductCode;
    private String brandSec;
    private String productName;
    private String sizeDesc;
    private BigDecimal qty;
    private BigDecimal pickedQty;
    private BigDecimal shippedQty;
    
    private BigDecimal refundedQty;//?
    private BigDecimal unitPrice;
	public String getHybrisOrderId() {
		return hybrisOrderId;
	}
	public void setHybrisOrderId(String hybrisOrderId) {
		this.hybrisOrderId = hybrisOrderId;
	}
	public String getPickOrderId() {
		return pickOrderId;
	}
	public void setPickOrderId(String pickOrderId) {
		this.pickOrderId = pickOrderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getOrderDatetime() {
		return orderDatetime;
	}
	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}
	public String getConsignmentShippedDate() {
		return consignmentShippedDate;
	}
	public void setConsignmentShippedDate(String consignmentShippedDate) {
		this.consignmentShippedDate = consignmentShippedDate;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String getSupplierProductCode() {
		return supplierProductCode;
	}
	public void setSupplierProductCode(String supplierProductCode) {
		this.supplierProductCode = supplierProductCode;
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
	public String getSizeDesc() {
		return sizeDesc;
	}
	public void setSizeDesc(String sizeDesc) {
		this.sizeDesc = sizeDesc;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public BigDecimal getPickedQty() {
		return pickedQty;
	}
	public void setPickedQty(BigDecimal pickedQty) {
		this.pickedQty = pickedQty;
	}
	public BigDecimal getRefundedQty() {
		return refundedQty;
	}
	public void setRefundedQty(BigDecimal refundedQty) {
		this.refundedQty = refundedQty;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public BigDecimal getShippedQty() {
		return shippedQty;
	}
	public void setShippedQty(BigDecimal shippedQty) {
		this.shippedQty = shippedQty;
	}
	public String getOrderTypeDesc() {
		return orderTypeDesc;
	}
	public void setOrderTypeDesc(String orderTypeDesc) {
		this.orderTypeDesc = orderTypeDesc;
	}
    
    
}
