package com.mall.b2bp.vos.parm;

import java.math.BigDecimal;

import com.mall.b2bp.vos.BaseVo;

public class ParmVo extends BaseVo{
    private BigDecimal id;

    private String segment;

    private String code;

    private String shortDesc;
    
    private String longDesc;
    
    private String value;
    
    private BigDecimal mallId;

    private Short dispSeq;
    
    public ParmVo() {
		super();
	}

	public ParmVo(BigDecimal id, String segment, String code, String shortDesc, String longDesc, BigDecimal mallId,
			Short dispSeq, String value) {
		super();
		this.id = id;
		this.segment = segment;
		this.code = code;
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
		this.mallId = mallId;
		this.dispSeq = dispSeq;
		this.value = value;
	}

	public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment == null ? null : segment.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc == null ? null : shortDesc.trim();
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc == null ? null : longDesc.trim();
    }

    public Short getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(Short dispSeq) {
        this.dispSeq = dispSeq;
    }

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getMallId() {
		return mallId;
	}

	public void setMallId(BigDecimal mallId) {
		this.mallId = mallId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
    
}