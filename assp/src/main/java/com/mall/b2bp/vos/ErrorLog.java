package com.mall.b2bp.vos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mall.b2bp.enums.ErrorLogType;

public class ErrorLog {

	private String fileName;
	private String methodName;
//	private String errorMsg;
	private List<String> errorList = new ArrayList<>();
	private ErrorLogType errorLogType;
	private Date createTime;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public void add(String errorMsg) {
		errorList.add(errorMsg);
	}
	public ErrorLogType getErrorLogType() {
		return errorLogType;
	}
	public void setErrorLogType(ErrorLogType errorLogType) {
		this.errorLogType = errorLogType;
	}
	
}
