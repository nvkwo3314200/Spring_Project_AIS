package com.mall.b2bp.vos.dept;

import java.math.BigDecimal;

public class SubClassVo {
	private BigDecimal classId;

	private BigDecimal deptId;

	private BigDecimal subClassId;
	private String description;
	private String ecategroyId;
	private String lovDesc;
	private BigDecimal id;
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getLovDesc() {
		return lovDesc;
	}
	public void setLovDesc(String lovDesc) {
		this.lovDesc = lovDesc;
	}
	public BigDecimal getClassId() {
		return classId;
	}
	public void setClassId(BigDecimal classId) {
		this.classId = classId;
	}
	public BigDecimal getDeptId() {
		return deptId;
	}
	public void setDeptId(BigDecimal deptId) {
		this.deptId = deptId;
	}
	public BigDecimal getSubClassId() {
		return subClassId;
	}
	public void setSubClassId(BigDecimal subClassId) {
		this.subClassId = subClassId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEcategroyId() {
		return ecategroyId;
	}
	public void setEcategroyId(String ecategroyId) {
		this.ecategroyId = ecategroyId;
	}
	
	
}
