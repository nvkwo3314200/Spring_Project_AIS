package com.mall.b2bp.models.contract;

import java.math.BigDecimal;
import java.util.Date;

public class ContractDeliverMethodModel {
    private BigDecimal id;

    private BigDecimal contractId;

    private BigDecimal deliverMethodId;

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

    public BigDecimal getContractId() {
        return contractId;
    }

    public void setContractId(BigDecimal contractId) {
        this.contractId = contractId;
    }

    public BigDecimal getDeliverMethodId() {
        return deliverMethodId;
    }

    public void setDeliverMethodId(BigDecimal deliverMethodId) {
        this.deliverMethodId = deliverMethodId;
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

    public ContractDeliverMethodModel() {
    }

    public ContractDeliverMethodModel(BigDecimal id, BigDecimal contractId, BigDecimal deliverMethodId, String createdBy, Date createdDate, String lastUpdatedBy, Date lastUpdatedDate) {
        this.id = id;
        this.contractId = contractId;
        this.deliverMethodId = deliverMethodId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
    }
}