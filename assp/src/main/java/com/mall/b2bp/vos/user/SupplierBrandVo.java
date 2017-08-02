package com.mall.b2bp.vos.user;
import java.math.BigDecimal;

import com.mall.b2bp.vos.BaseVo;

public class SupplierBrandVo extends BaseVo {
	private boolean ticked;
	private BigDecimal brandCode;
    private BigDecimal code;

    private String descEn;

    private BigDecimal masterId;
		
	
	
	public boolean isTicked() {
		return ticked;
	}
	public void setTicked(boolean ticked) {
		this.ticked = ticked;
	}
	public BigDecimal getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(BigDecimal brandCode) {
		this.brandCode = brandCode;
	}
	public BigDecimal getCode() {
		return code;
	}
	public void setCode(BigDecimal code) {
		this.code = code;
	}
	public String getDescEn() {
		return descEn;
	}
	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}
	public BigDecimal getMasterId() {
		return masterId;
	}
	public void setMasterId(BigDecimal masterId) {
		this.masterId = masterId;
	}
	
	


}
