package com.mall.b2bp.vos.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductImagesVoISO8601 implements Serializable {
	/**
		 * 
		 */

	final String DATEFORMAT_ISO8601="yyyy-MM-dd'T'HH:mm:ssZ";
	SimpleDateFormat iso8601=new SimpleDateFormat(DATEFORMAT_ISO8601);
	
	private static final long serialVersionUID = -5091665481578629113L;

	private BigDecimal id;

	private BigDecimal productId;

	private String fileName;

	private String filePath;

	private String status;

	private String description;

	private Long sequence;

	private String createdBy;

	private String createdDate;

	private String lastUpdatedBy;

	private String lastUpdatedDate;
	private String failedReason;
	private String productCode;

	private String imageType;

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		if(createdDate !=null)
		this.createdDate = iso8601.format(createdDate);
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		if(lastUpdatedDate !=null)
		this.lastUpdatedDate = iso8601.format(lastUpdatedDate);
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getProductId() {
		return productId;
	}

	public void setProductId(BigDecimal productId) {
		this.productId = productId;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
