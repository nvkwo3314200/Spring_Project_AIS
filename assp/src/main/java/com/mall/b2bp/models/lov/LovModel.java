package com.mall.b2bp.models.lov;

import java.math.BigDecimal;
import java.util.Date;

public class LovModel {
	private BigDecimal id;

	private BigDecimal lovId;

	private String lovValue;

	private String lovDesc;

	private String createdBy;

	private Date createdDate;

	private String lastUpdatedBy;

	private Date lastUpdatedDate;

	private Long sequence;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getLovId() {
		return lovId;
	}

	public void setLovId(BigDecimal lovId) {
		this.lovId = lovId;
	}

	public String getLovValue() {
		return lovValue;
	}

	public void setLovValue(String lovValue) {
		this.lovValue = lovValue == null ? null : lovValue.trim();
	}

	public String getLovDesc() {
		return lovDesc;
	}

	public void setLovDesc(String lovDesc) {
		this.lovDesc = lovDesc == null ? null : lovDesc.trim();
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
		this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy
				.trim();
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

}