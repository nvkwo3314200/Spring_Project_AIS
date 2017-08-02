package com.mall.b2bp.vos.order;

/**
 * Created by USER on 2016/3/14.
 */
public class OrderViewVo {

    private String supplier;
    private String orderId;
    private String pickOrderId;

    private String[] orderStatus;
    private String consignmentStatus;

    private String orderDateFr;
    private String orderDateTo;
    private String shippedDateFr;
    private String shippedDateTo;
    private String deliveryDateFr;
    private String deliveryDateTo;
    private String orderType;
    private String invoiceReadyInd;
    private String returnRequest;
    private String outstandingReturnRequest;
    private String customerName;
    private String[]  collectMethod;

    
	public String[] getCollectMethod() {
		return collectMethod;
	}
	
	public void setCollectMethod(String[] collectMethod) {
		this.collectMethod = collectMethod;
	}

	public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

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

    //    public String getOrderStatus() {
//        return orderStatus;
//    }
//
//    public void setOrderStatus(String orderStatus) {
//        this.orderStatus = orderStatus;
//    }

    public String getConsignmentStatus() {
        return consignmentStatus;
    }

    public void setConsignmentStatus(String consignmentStatus) {
        this.consignmentStatus = consignmentStatus;
    }

    public String getOrderDateFr() {
        return orderDateFr;
    }

    public void setOrderDateFr(String orderDateFr) {
        this.orderDateFr = orderDateFr;
    }

    public String getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(String orderDateTo) {
        this.orderDateTo = orderDateTo;
    }

    public String getShippedDateFr() {
        return shippedDateFr;
    }

    public void setShippedDateFr(String shippedDateFr) {
        this.shippedDateFr = shippedDateFr;
    }

    public String getShippedDateTo() {
        return shippedDateTo;
    }

    public void setShippedDateTo(String shippedDateTo) {
        this.shippedDateTo = shippedDateTo;
    }

    public String getDeliveryDateFr() {
        return deliveryDateFr;
    }

    public void setDeliveryDateFr(String deliveryDateFr) {
        this.deliveryDateFr = deliveryDateFr;
    }

    public String getDeliveryDateTo() {
        return deliveryDateTo;
    }

    public void setDeliveryDateTo(String deliveryDateTo) {
        this.deliveryDateTo = deliveryDateTo;
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

	public String getOutstandingReturnRequest() {
		return outstandingReturnRequest;
	}

	public void setOutstandingReturnRequest(String outstandingReturnRequest) {
		this.outstandingReturnRequest = outstandingReturnRequest;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
