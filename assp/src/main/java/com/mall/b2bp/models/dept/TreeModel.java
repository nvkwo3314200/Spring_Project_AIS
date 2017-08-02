package com.mall.b2bp.models.dept;

import java.math.BigDecimal;

public class TreeModel {
    private BigDecimal pdId;
    private BigDecimal deptId;
    private String pdDescription;
    

    private BigDecimal pcId;
    private BigDecimal classId;
    private String pcDescription; 
    
    private BigDecimal psId;
    private BigDecimal subClassId;
    private String psDescription;
    
    private String ecategroyId;
    private String lovDesc;
    
	public BigDecimal getPdId() {
		return pdId;
	}
	public void setPdId(BigDecimal pdId) {
		this.pdId = pdId;
	}
	public BigDecimal getDeptId() {
		return deptId;
	}
	public void setDeptId(BigDecimal deptId) {
		this.deptId = deptId;
	}
	public String getPdDescription() {
		return pdDescription;
	}
	public void setPdDescription(String pdDescription) {
		this.pdDescription = pdDescription;
	}
	public BigDecimal getPcId() {
		return pcId;
	}
	public void setPcId(BigDecimal pcId) {
		this.pcId = pcId;
	}
	public BigDecimal getClassId() {
		return classId;
	}
	public void setClassId(BigDecimal classId) {
		this.classId = classId;
	}
	public String getPcDescription() {
		return pcDescription;
	}
	public void setPcDescription(String pcDescription) {
		this.pcDescription = pcDescription;
	}
	public BigDecimal getPsId() {
		return psId;
	}
	public void setPsId(BigDecimal psId) {
		this.psId = psId;
	}
	public BigDecimal getSubClassId() {
		return subClassId;
	}
	public void setSubClassId(BigDecimal subClassId) {
		this.subClassId = subClassId;
	}
	public String getPsDescription() {
		return psDescription;
	}
	public void setPsDescription(String psDescription) {
		this.psDescription = psDescription;
	}
	public String getEcategroyId() {
		return ecategroyId;
	}
	public void setEcategroyId(String ecategroyId) {
		this.ecategroyId = ecategroyId;
	}
	public String getLovDesc() {
		return lovDesc;
	}
	public void setLovDesc(String lovDesc) {
		this.lovDesc = lovDesc;
	} 
    
    
}
