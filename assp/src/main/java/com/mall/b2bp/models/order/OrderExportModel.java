package com.mall.b2bp.models.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderExportModel {
//	o	Order ID
//	o	Order consignment ID
//	o	Order type
//	o	Order status
//	o	Recipient Name
//	o	Recipient Contact number
//	o	Recipient Address
//	o	Customer Name
//	o	Customer Contact number
//	o	Order create date
//	o	Order shipped date
//	o	Order delivered date
//	o	SKU
//	o	VPN 
//	o	Brand name
//	o	Product description
//	o	Size Description
//	o	Ordered quantity
//	o	Picked/Shipped quantity
//	o	Refunded quantity
//	o	Price
	
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
    
    private Date orderDatetime;
    private Date consignmentShippedDate;
    private Date deliveryDate;
    
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
	public Date getOrderDatetime() {
		return orderDatetime;
	}
	public void setOrderDatetime(Date orderDatetime) {
		this.orderDatetime = orderDatetime;
	}
	public Date getConsignmentShippedDate() {
		return consignmentShippedDate;
	}
	public void setConsignmentShippedDate(Date consignmentShippedDate) {
		this.consignmentShippedDate = consignmentShippedDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
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
    

}
