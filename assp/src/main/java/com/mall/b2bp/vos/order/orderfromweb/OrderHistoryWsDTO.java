package com.mall.b2bp.vos.order.orderfromweb;

import java.util.Date;
import java.util.List;

public class OrderHistoryWsDTO implements java.io.Serializable {

	private String code;

	private String status;

	private String statusDisplay;

	private Date placed;

	private String guid;

	private PriceWsDTO total;

	private PrincipalWsDTO user;

	private List<ConsignmentWsDTO> consignments;

	private List<SupplierCollectionWsDTO> supplierCollections;

	private String deliveryStatus;

	private String deliveryStatusDisplay;

	private String orderStatus;

	private Date collectDate;

	private String deliveryFlag;

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public String getDeliveryFlag() {
		return deliveryFlag;
	}

	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryStatusDisplay() {
		return deliveryStatusDisplay;
	}

	public void setDeliveryStatusDisplay(String deliveryStatusDisplay) {
		this.deliveryStatusDisplay = deliveryStatusDisplay;
	}

	public OrderHistoryWsDTO() {
		// default constructor
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatusDisplay(final String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setPlaced(final Date placed) {
		this.placed = placed;
	}

	public Date getPlaced() {
		return placed;
	}

	public void setGuid(final String guid) {
		this.guid = guid;
	}

	public String getGuid() {
		return guid;
	}

	public void setTotal(final PriceWsDTO total) {
		this.total = total;
	}

	public PriceWsDTO getTotal() {
		return total;
	}

	public void setUser(final PrincipalWsDTO user) {
		this.user = user;
	}

	public PrincipalWsDTO getUser() {
		return user;
	}

	public void setConsignments(final List<ConsignmentWsDTO> consignments) {
		this.consignments = consignments;
	}

	public List<ConsignmentWsDTO> getConsignments() {
		return consignments;
	}

	public List<SupplierCollectionWsDTO> getSupplierCollections() {
		return supplierCollections;
	}

	public void setSupplierCollections(List<SupplierCollectionWsDTO> supplierCollections) {
		this.supplierCollections = supplierCollections;
	}

}