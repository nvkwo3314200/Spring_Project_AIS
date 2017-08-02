package com.mall.b2bp.vos.product;

import java.math.BigDecimal;
import java.util.Date;

import com.mall.b2bp.vos.BaseVo;

public class ProductImportLogVo extends BaseVo{
	
	private BigDecimal id;
	
	private String importType;
	
	private String[] importTypeArr;
	
    private Date importDate;

    private String fileName;
    
    private String importDateStr;
    
    private String message;
    
    private String importDateFrStr;
    
    private String importDateToStr;
    
    private Date  importDateFr;
    
    private Date  importDateTo;
    
    private String importCode;
    
    public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getImportDateStr() {
		return importDateStr;
	}

	public void setImportDateStr(String importDateStr) {
		this.importDateStr = importDateStr;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImportDateFrStr() {
		return importDateFrStr;
	}

	public void setImportDateFrStr(String importDateFrStr) {
		this.importDateFrStr = importDateFrStr;
	}

	public String getImportDateToStr() {
		return importDateToStr;
	}

	public void setImportDateToStr(String importDateToStr) {
		this.importDateToStr = importDateToStr;
	}

	public Date getImportDateFr() {
		return importDateFr;
	}

	public void setImportDateFr(Date importDateFr) {
		this.importDateFr = importDateFr;
	}

	public Date getImportDateTo() {
		return importDateTo;
	}

	public void setImportDateTo(Date importDateTo) {
		this.importDateTo = importDateTo;
	}

	public String getImportCode() {
		return importCode;
	}

	public void setImportCode(String importCode) {
		this.importCode = importCode;
	}

	public String[] getImportTypeArr() {
		return importTypeArr;
	}

	public void setImportTypeArr(String[] importTypeArr) {
		this.importTypeArr = importTypeArr;
	}

	
}
