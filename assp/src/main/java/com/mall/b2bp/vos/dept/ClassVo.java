package com.mall.b2bp.vos.dept;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClassVo {
    private String description;
    private BigDecimal classId;
    private BigDecimal deptId;
	private BigDecimal id;
	private List<SubClassVo> subClassList=new ArrayList<>();
	private String deptDesc;
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	public List<SubClassVo> getSubClassList() {
		return subClassList;
	}

	public void setSubClassList(List<SubClassVo> subClassList) {
		this.subClassList = subClassList;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
	
	
	public void addSubClass(SubClassVo subClassVo){
		subClassList.add(subClassVo);
		
	}
    
    
}
