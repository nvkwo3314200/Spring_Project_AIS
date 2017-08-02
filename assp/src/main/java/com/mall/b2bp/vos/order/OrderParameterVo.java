package com.mall.b2bp.vos.order;

import java.util.Date;

/**
 * Created by USER on 2016/3/14.
 */
public class OrderParameterVo {

    private String[] supplier;
    private String supplierId;
    
    private String orderId;
    private String pickOrderId;

    private String[] orderStatus;
    private String[] consignmentStatus;

    private Date orderDateFr;
    private Date orderDateTo;
    private Date shippedDateFr;
    private Date shippedDateTo;
    private Date deliveryDateFr;
    private Date deliveryDateTo;
    private String orderType;
    private String invoiceReadyInd;
    private String returnRequest;
    
    private String skuId;
    
	private String outstandingReturnRequest;
	private String userName;
	private String customerName;
	
	

    public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getInvoiceReadyInd() {
        return invoiceReadyInd;
    }

    public void setInvoiceReadyInd(String invoiceReadyInd) {
        this.invoiceReadyInd = invoiceReadyInd;
    }

    public String getReturnRequest() {
        return returnRequest;
    }

    public void setReturnRequest(String returnRequest) {
        this.returnRequest = returnRequest;
    }

    public String[] getSupplier() {
        return supplier;
    }

    public void setSupplier(String[] supplier) {
        this.supplier = supplier;
    }
//    public String getSupplier() {
//        return supplier;
//    }
//
//    public void setSupplier(String supplier) {
//        this.supplier = supplier;
//    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPickOrderId() {
        return pickOrderId;
    }

    public void setPickOrderId(String pickOrderId) {
        this.pickOrderId = pickOrderId;
    }

    public String[] getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String[] orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String[] getConsignmentStatus() {
        return consignmentStatus;
    }

    public void setConsignmentStatus(String[] consignmentStatus) {
        this.consignmentStatus = consignmentStatus;
    }

    public Date getOrderDateFr() {
        return orderDateFr;
    }

    public void setOrderDateFr(Date orderDateFr) {
        this.orderDateFr = orderDateFr;
    }

    public Date getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(Date orderDateTo) {
        this.orderDateTo = orderDateTo;
    }

    public Date getShippedDateFr() {
        return shippedDateFr;
    }

    public void setShippedDateFr(Date shippedDateFr) {
        this.shippedDateFr = shippedDateFr;
    }

    public Date getShippedDateTo() {
        return shippedDateTo;
    }

    public void setShippedDateTo(Date shippedDateTo) {
        this.shippedDateTo = shippedDateTo;
    }

    public Date getDeliveryDateFr() {
        return deliveryDateFr;
    }

    public void setDeliveryDateFr(Date deliveryDateFr) {
        this.deliveryDateFr = deliveryDateFr;
    }

    public Date getDeliveryDateTo() {
        return deliveryDateTo;
    }

    public void setDeliveryDateTo(Date deliveryDateTo) {
        this.deliveryDateTo = deliveryDateTo;
    }

	public String getOutstandingReturnRequest() {
		return outstandingReturnRequest;
	}

	public void setOutstandingReturnRequest(String outstandingReturnRequest) {
		this.outstandingReturnRequest = outstandingReturnRequest;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
}
