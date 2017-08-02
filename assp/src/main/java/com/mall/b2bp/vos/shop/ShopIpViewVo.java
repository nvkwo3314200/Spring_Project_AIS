package com.mall.b2bp.vos.shop;

import java.math.BigDecimal;

import com.mall.b2bp.vos.BaseVo;

public class ShopIpViewVo extends BaseVo {
	private BigDecimal id;

    private BigDecimal shopId;

    private String exIncludeInd;

    private Short ipStart1;
    
    private Short ipStart2;
    
    private Short ipStart3;
    
    private Short ipStart4;

    private Short ipEnd1;
    
    private Short ipEnd2;
    
    private Short ipEnd3;
    
    private Short ipEnd4;

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
		this.exIncludeInd = exIncludeInd;
	}

	public Short getIpStart1() {
		return ipStart1;
	}

	public void setIpStart1(Short ipStart1) {
		this.ipStart1 = ipStart1;
	}

	public Short getIpStart2() {
		return ipStart2;
	}

	public void setIpStart2(Short ipStart2) {
		this.ipStart2 = ipStart2;
	}

	public Short getIpStart3() {
		return ipStart3;
	}

	public void setIpStart3(Short ipStart3) {
		this.ipStart3 = ipStart3;
	}

	public Short getIpStart4() {
		return ipStart4;
	}

	public void setIpStart4(Short ipStart4) {
		this.ipStart4 = ipStart4;
	}

	public Short getIpEnd1() {
		return ipEnd1;
	}

	public void setIpEnd1(Short ipEnd1) {
		this.ipEnd1 = ipEnd1;
	}

	public Short getIpEnd2() {
		return ipEnd2;
	}

	public void setIpEnd2(Short ipEnd2) {
		this.ipEnd2 = ipEnd2;
	}

	public Short getIpEnd3() {
		return ipEnd3;
	}

	public void setIpEnd3(Short ipEnd3) {
		this.ipEnd3 = ipEnd3;
	}

	public Short getIpEnd4() {
		return ipEnd4;
	}

	public void setIpEnd4(Short ipEnd4) {
		this.ipEnd4 = ipEnd4;
	}
}
