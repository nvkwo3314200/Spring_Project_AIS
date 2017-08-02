package com.mall.b2bp.vos.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderTLogVo {

	 private BigDecimal id;

	    private String hybrisOrderId;

	    private String skuId;

	    private Date transactionDate;
	    private Date orderCreatedDate;
	    
	    private String transactionDateStr;
	    private String orderCreatedDateStr;

	    private BigDecimal amount;

	    private BigDecimal qty;

	    private String netSales;

	    private String typeOfDistribution;

	    private String createdBy;

	    private Date createdDate;

	    private String lastUpdatedBy;

	    private Date lastUpdatedDate;

		public BigDecimal getId() {
			return id;
		}

		public void setId(BigDecimal id) {
			this.id = id;
		}

		public String getHybrisOrderId() {
			return hybrisOrderId;
		}

		public void setHybrisOrderId(String hybrisOrderId) {
			this.hybrisOrderId = hybrisOrderId;
		}

		

		public Date getTransactionDate() {
			return transactionDate;
		}

		public void setTransactionDate(Date transactionDate) {
			this.transactionDate = transactionDate;
		}

		public Date getOrderCreatedDate() {
			return orderCreatedDate;
		}

		public void setOrderCreatedDate(Date orderCreatedDate) {
			this.orderCreatedDate = orderCreatedDate;
		}

		

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public BigDecimal getQty() {
			return qty;
		}

		public void setQty(BigDecimal qty) {
			this.qty = qty;
		}

		public String getNetSales() {
			return netSales;
		}

		public void setNetSales(String netSales) {
			this.netSales = netSales;
		}

		public String getTypeOfDistribution() {
			return typeOfDistribution;
		}

		public void setTypeOfDistribution(String typeOfDistribution) {
			this.typeOfDistribution = typeOfDistribution;
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

		public String getTransactionDateStr() {
			return transactionDateStr;
		}

		public void setTransactionDateStr(String transactionDateStr) {
			this.transactionDateStr = transactionDateStr;
		}

		public String getOrderCreatedDateStr() {
			return orderCreatedDateStr;
		}

		public void setOrderCreatedDateStr(String orderCreatedDateStr) {
			this.orderCreatedDateStr = orderCreatedDateStr;
		}

		public String getSkuId() {
			return skuId;
		}

		public void setSkuId(String skuId) {
			this.skuId = skuId;
		}
	    
	    
}
