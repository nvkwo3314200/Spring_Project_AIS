package com.mall.b2bp.vos.mall;

import java.math.BigDecimal;

import com.mall.b2bp.vos.BaseVo;

public class MallVo extends BaseVo{
	private BigDecimal id;
	private String code;
	private String name;
	
	
	
	public MallVo() {
		super();
	}
	
	public MallVo(BigDecimal id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
