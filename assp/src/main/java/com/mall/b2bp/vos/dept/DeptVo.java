package com.mall.b2bp.vos.dept;

import java.math.BigDecimal;
import java.util.List;

public class DeptVo {
	private BigDecimal id;
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	private BigDecimal deptId;

	private String description;
	
	private String descriptionCode;
	
	private List<ClassVo> classList;
	public BigDecimal getDeptId() {
		return deptId;
	}

	public void setDeptId(BigDecimal deptId) {
		this.deptId = deptId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ClassVo> getClassList() {
		return classList;
	}

	public void setClassList(List<ClassVo> classList) {
		this.classList = classList;
	}

	public String getDescriptionCode() {
		return descriptionCode;
	}

	public void setDescriptionCode(String descriptionCode) {
		this.descriptionCode = descriptionCode;
	}
	

}
