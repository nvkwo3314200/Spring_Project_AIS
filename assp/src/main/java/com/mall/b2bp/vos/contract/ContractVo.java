package com.mall.b2bp.vos.contract;

import com.mall.b2bp.models.contract.ContractDeliverMethodModel;
import com.mall.b2bp.models.contract.ContractPayMethodModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Harlan King on 2017/3/21.
 */
public class ContractVo {
    private BigDecimal id;

    private String ref;

    private BigDecimal supplierId;

    private Date startDate;

    private Date endDate;

    private String status;

    private String statusName;

    private String createdBy;

    private Date createdDate;

    private String lastUpdatedBy;

    private Date lastUpdatedDate;
    private List<ContractPayMethodModel> payList;
    private List<ContractDeliverMethodModel> deliverList;



    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BigDecimal getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(BigDecimal supplierId) {
        this.supplierId = supplierId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        if(this.status!=null){
            switch (this.status){
                case "A":this.statusName="Active";
                    break;
                case "C":this.statusName="Cancelled";
                    break;
                default:this.statusName="";
            }

        } else{
            this.statusName="";
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public ContractVo(BigDecimal id, String ref, BigDecimal supplierId, Date startDate, Date endDate, String status, String createdBy, Date createdDate, String lastUpdatedBy, Date lastUpdatedDate) {
        this.id = id;
        this.ref = ref;
        this.supplierId = supplierId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public ContractVo() {
    }

    public List<ContractPayMethodModel> getPayList() {
        return payList;
    }

    public void setPayList(List<ContractPayMethodModel> payList) {
        this.payList = payList;
    }

    public List<ContractDeliverMethodModel> getDeliverList() {
        return deliverList;
    }

    public void setDeliverList(List<ContractDeliverMethodModel> deliverList) {
        this.deliverList = deliverList;
    }
}
