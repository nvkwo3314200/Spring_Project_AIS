package com.mall.b2bp.models.shop;

import java.math.BigDecimal;
import java.util.Date;

public class ShopIpModel {
    private BigDecimal id;

    private BigDecimal shopId;

    private String exIncludeInd;

    private String ipStartRange;

    private String ipEndRange;

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

	public BigDecimal getShopId() {
		return shopId;
	}

	public void setShopId(BigDecimal shopId) {
		this.shopId = shopId;
	}

	public String getExIncludeInd() {
        return exIncludeInd;
    }

    public void setExIncludeInd(String exIncludeInd) {
        this.exIncludeInd = exIncludeInd == null ? null : exIncludeInd.trim();
    }

    public String getIpStartRange() {
        return ipStartRange;
    }

    public void setIpStartRange(String ipStartRange) {
        this.ipStartRange = ipStartRange == null ? null : ipStartRange.trim();
    }

    public String getIpEndRange() {
        return ipEndRange;
    }

    public void setIpEndRange(String ipEndRange) {
        this.ipEndRange = ipEndRange == null ? null : ipEndRange.trim();
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
}