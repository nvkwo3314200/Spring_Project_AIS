package com.mall.b2bp.models.dept;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ClassModel extends ClassModelKey {
    private String description;
    
    private String descriptionCode;

    private String createdBy;

    private Date createdDate;

    private String lastUpdatedBy;

    private Date lastUpdatedDate;
    
    private BigDecimal subClassId;
    private BigDecimal id;
    private List<SubClassModel> subClassList;
    
    private String deptCLassId;
    
    private BigDecimal deptIdReal;

	public BigDecimal getDeptIdReal() {
		return deptIdReal;
	}

	public void setDeptIdReal(BigDecimal deptIdReal) {
		this.deptIdReal = deptIdReal;
	}

	public String getDeptCLassId() {
		return deptCLassId;
	}

	public void setDeptCLassId(String deptCLassId) {
		this.deptCLassId = deptCLassId;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getDescriptionCode() {
		return descriptionCode;
	}

	public void setDescriptionCode(String descriptionCode) {
		this.descriptionCode = descriptionCode;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

	public List<SubClassModel> getSubClassList() {
		return subClassList;
	}

	public void setSubClassList(List<SubClassModel> subClassList) {
		this.subClassList = subClassList;
	}

	public BigDecimal getSubClassId() {
		return subClassId;
	}

	public void setSubClassId(BigDecimal subClassId) {
		this.subClassId = subClassId;
	}
    
    
}