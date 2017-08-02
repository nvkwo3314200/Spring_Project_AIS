package com.mall.b2bp.vos.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderReturnVo {
	
	private List<OrderReturnEntryVo> entryList;
	 private BigDecimal id;

	    private BigDecimal orderId;

	    private String hybrisOrderId;

	    private String hybrisReturnId;

	    private BigDecimal returnType;

	    private BigDecimal pickStore;

	    private String supplierId;

	    private Date returnCreateDate;
	    private String returnCreateDateStr;

	    private String specialInstruction;

	    private BigDecimal customerId;

	    private String customerType;

	    private String customerName;

	    private String customerPhoneNo;

	    private String customerMobileNo;

	    private String tenderType;

	    private String paymentRef;

	    private Date collectDate;
	    private String collectDateStr;

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

	    private String specialinstruction;

	    private String pickOrderId;
	    
	    private String returnRequestStatus;

	    private Date returnRequestUpdateDate;
	    private String returnRequestUpdateDateStr;
	    
	    
	    
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
	        this.hybrisOrderId = hybrisOrderId == null ? null : hybrisOrderId.trim();
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
	        this.supplierId = supplierId == null ? null : supplierId.trim();
	    }

	    public Date getReturnCreateDate() {
	        return returnCreateDate;
	    }

	    public void setReturnCreateDate(Date returnCreateDate) {
	        this.returnCreateDate = returnCreateDate;
	    }

	    public String getSpecialInstruction() {
	        return specialInstruction;
	    }

	    public void setSpecialInstruction(String specialInstruction) {
	        this.specialInstruction = specialInstruction == null ? null : specialInstruction.trim();
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
	        this.customerPhoneNo = customerPhoneNo == null ? null : customerPhoneNo.trim();
	    }

	    public String getCustomerMobileNo() {
	        return customerMobileNo;
	    }

	    public void setCustomerMobileNo(String customerMobileNo) {
	        this.customerMobileNo = customerMobileNo == null ? null : customerMobileNo.trim();
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

	    public Date getCollectDate() {
	        return collectDate;
	    }

	    public void setCollectDate(Date collectDate) {
	        this.collectDate = collectDate;
	    }

	    public String getCollectTimeSlot() {
	        return collectTimeSlot;
	    }

	    public void setCollectTimeSlot(String collectTimeSlot) {
	        this.collectTimeSlot = collectTimeSlot == null ? null : collectTimeSlot.trim();
	    }

	    public String getContactName() {
	        return contactName;
	    }

	    public void setContactName(String contactName) {
	        this.contactName = contactName == null ? null : contactName.trim();
	    }

	    public String getCollectDistrict() {
	        return collectDistrict;
	    }

	    public void setCollectDistrict(String collectDistrict) {
	        this.collectDistrict = collectDistrict == null ? null : collectDistrict.trim();
	    }

	    public String getContactMobileNo() {
	        return contactMobileNo;
	    }

	    public void setContactMobileNo(String contactMobileNo) {
	        this.contactMobileNo = contactMobileNo == null ? null : contactMobileNo.trim();
	    }

	    public String getContactPhoneNo() {
	        return contactPhoneNo;
	    }

	    public void setContactPhoneNo(String contactPhoneNo) {
	        this.contactPhoneNo = contactPhoneNo == null ? null : contactPhoneNo.trim();
	    }

	    public String getCollectAddress() {
	        return collectAddress;
	    }

	    public void setCollectAddress(String collectAddress) {
	        this.collectAddress = collectAddress == null ? null : collectAddress.trim();
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
	        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
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
	        this.remark = remark == null ? null : remark.trim();
	    }

	    public String getSpecialinstruction() {
	        return specialinstruction;
	    }

	    public void setSpecialinstruction(String specialinstruction) {
	        this.specialinstruction = specialinstruction == null ? null : specialinstruction.trim();
	    }

	    public String getPickOrderId() {
	        return pickOrderId;
	    }

	    public void setPickOrderId(String pickOrderId) {
	        this.pickOrderId = pickOrderId == null ? null : pickOrderId.trim();
	    }

		public String getReturnRequestStatus() {
			return returnRequestStatus;
		}

		public void setReturnRequestStatus(String returnRequestStatus) {
			this.returnRequestStatus = returnRequestStatus;
		}

		public Date getReturnRequestUpdateDate() {
			return returnRequestUpdateDate;
		}

		public void setReturnRequestUpdateDate(Date returnRequestUpdateDate) {
			this.returnRequestUpdateDate = returnRequestUpdateDate;
		}

		public String getHybrisReturnId() {
			return hybrisReturnId;
		}

		public void setHybrisReturnId(String hybrisReturnId) {
			this.hybrisReturnId = hybrisReturnId;
		}

		public String getReturnCreateDateStr() {
			return returnCreateDateStr;
		}

		public void setReturnCreateDateStr(String returnCreateDateStr) {
			this.returnCreateDateStr = returnCreateDateStr;
		}

		public String getCollectDateStr() {
			return collectDateStr;
		}

		public void setCollectDateStr(String collectDateStr) {
			this.collectDateStr = collectDateStr;
		}

		public String getReturnRequestUpdateDateStr() {
			return returnRequestUpdateDateStr;
		}

		public void setReturnRequestUpdateDateStr(String returnRequestUpdateDateStr) {
			this.returnRequestUpdateDateStr = returnRequestUpdateDateStr;
		}

		public List<OrderReturnEntryVo> getEntryList() {
			return entryList;
		}

		public void setEntryList(List<OrderReturnEntryVo> entryList) {
			this.entryList = entryList;
		}

	
}
