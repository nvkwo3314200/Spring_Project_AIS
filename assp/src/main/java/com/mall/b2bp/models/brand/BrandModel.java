package com.mall.b2bp.models.brand;

import java.math.BigDecimal;
import java.util.Date;

public class BrandModel {
    private BigDecimal brandCode;
    private BigDecimal code;

    private String descEn;
    private String descTc;
    private String descSc;

    private String sysRef;

    private BigDecimal masterId;

    private String createdBy;

    private Date createdDate;

    private String lastUpdatedBy;

    private Date lastUpdatedDate;

    private String imageFileName;
    private String watsonsMallInd;
    private String supplierId;
    
    private String brandDescEn;
    private String brandDescTc;
    private String brandDescSc;
    
    private String brandTaglineEn;
    private String brandTaglineSc;
    private String brandTaglineTc;
    
    private String brandNameEn;
    private String brandNameTc;
    private String brandNameSc;
    
    
//    private String shopCategory;

	public BigDecimal getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(BigDecimal brandCode) {
        this.brandCode = brandCode;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn == null ? null : descEn.trim();
    }

    public String getDescTc() {
        return descTc;
    }

    public void setDescTc(String descTc) {
        this.descTc = descTc == null ? null : descTc.trim();
    }

    public String getDescSc() {
        return descSc;
    }

    public void setDescSc(String descSc) {
        this.descSc = descSc == null ? null : descSc.trim();
    }

    public String getSysRef() {
        return sysRef;
    }

    public void setSysRef(String sysRef) {
        this.sysRef = sysRef == null ? null : sysRef.trim();
    }

    public BigDecimal getMasterId() {
        return masterId;
    }

    public void setMasterId(BigDecimal masterId) {
        this.masterId = masterId;
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

	public BigDecimal getCode() {
		return code;
	}

	public void setCode(BigDecimal code) {
		this.code = code;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getWatsonsMallInd() {
		return watsonsMallInd;
	}

	public void setWatsonsMallInd(String watsonsMallInd) {
		this.watsonsMallInd = watsonsMallInd;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getBrandDescEn() {
		return brandDescEn;
	}

	public void setBrandDescEn(String brandDescEn) {
		this.brandDescEn = brandDescEn;
	}

	public String getBrandDescTc() {
		return brandDescTc;
	}

	public void setBrandDescTc(String brandDescTc) {
		this.brandDescTc = brandDescTc;
	}

	public String getBrandDescSc() {
		return brandDescSc;
	}

	public void setBrandDescSc(String brandDescSc) {
		this.brandDescSc = brandDescSc;
	}

	public String getBrandTaglineEn() {
		return brandTaglineEn;
	}

	public void setBrandTaglineEn(String brandTaglineEn) {
		this.brandTaglineEn = brandTaglineEn;
	}

	public String getBrandTaglineSc() {
		return brandTaglineSc;
	}

	public void setBrandTaglineSc(String brandTaglineSc) {
		this.brandTaglineSc = brandTaglineSc;
	}

	public String getBrandTaglineTc() {
		return brandTaglineTc;
	}

	public void setBrandTaglineTc(String brandTaglineTc) {
		this.brandTaglineTc = brandTaglineTc;
	}

	public String getBrandNameEn() {
		return brandNameEn;
	}

	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}

	public String getBrandNameTc() {
		return brandNameTc;
	}

	public void setBrandNameTc(String brandNameTc) {
		this.brandNameTc = brandNameTc;
	}

	public String getBrandNameSc() {
		return brandNameSc;
	}

	public void setBrandNameSc(String brandNameSc) {
		this.brandNameSc = brandNameSc;
	}


}