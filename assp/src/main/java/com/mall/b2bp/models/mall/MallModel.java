package com.mall.b2bp.models.mall;

import java.math.BigDecimal;

import com.mall.b2bp.models.system.BaseModel;

public class MallModel extends BaseModel{
	private BigDecimal id;
	private String code;
	private String name;
	
	
	
	public MallModel() {
		super();
	}
	
	public MallModel(BigDecimal id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
