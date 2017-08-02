package com.mall.b2bp.models.dept;

import java.math.BigDecimal;
import java.util.Date;

public class SubClassModel extends SubClassModelKey {
    private String description;
    
    private String descriptionCode;

    private String createdBy;

    private Date createdDate;

    private String lastUpdatedBy;
    
    private BigDecimal deptId;

    public BigDecimal getDeptId() {
		return deptId;
	}

	public void setDeptId(BigDecimal deptId) {
		this.deptId = deptId;
	}

	private Date lastUpdatedDate;
    private String ecategroyId;//ECATEGORY_ID;
    private String lovDesc;
    private BigDecimal id;
    
    private String deptClassSubClassId;
    
    private String deptClassSubClassDesc;
    
    private String deptClassSubclassId;
 
    private BigDecimal deptIdReal;
    
	private BigDecimal classIdReal;
    
	public BigDecimal getDeptIdReal() {
		return deptIdReal;
	}

	public void setDeptIdReal(BigDecimal deptIdReal) {
		this.deptIdReal = deptIdReal;
	}

	public BigDecimal getClassIdReal() {
		return classIdReal;
	}

	public void setClassIdReal(BigDecimal classIdReal) {
		this.classIdReal = classIdReal;
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

	public String getLovDesc() {
		return lovDesc;
	}

	public void setLovDesc(String lovDesc) {
		this.lovDesc = lovDesc;
	}

	public String getEcategroyId() {
		return ecategroyId;
	}

	public void setEcategroyId(String ecategroyId) {
		this.ecategroyId = ecategroyId;
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

	public String getDeptClassSubClassId() {
		return deptClassSubClassId;
	}

	public void setDeptClassSubClassId(String deptClassSubClassId) {
		this.deptClassSubClassId = deptClassSubClassId;
	}

	public String getDeptClassSubClassDesc() {
		return deptClassSubClassDesc;
	}

	public void setDeptClassSubClassDesc(String deptClassSubClassDesc) {
		this.deptClassSubClassDesc = deptClassSubClassDesc;
	}

	public String getDeptClassSubclassId() {
		return deptClassSubclassId;
	}

	public void setDeptClassSubclassId(String deptClassSubclassId) {
		this.deptClassSubclassId = deptClassSubclassId;
	}
}