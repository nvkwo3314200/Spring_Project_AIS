package com.mall.b2bp.vos.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductImagesVo  implements Serializable{
/**
	 * 
	 */
private static final long serialVersionUID = -5091665481578629113L;

private BigDecimal id;

private BigDecimal productId;


private String fileName;

private String filePath;

private String status;

private String description;

private Long sequence;

private String createdBy;

private Date createdDate;

private String lastUpdatedBy;

private Date lastUpdatedDate;
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
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}
public String getLastUpdatedBy() {
	return lastUpdatedBy;
}
public void setLastUpdatedBy(String lastUpdatedBy) {
	this.lastUpdatedBy = lastUpdatedBy;
}
public Date getLastUpdatedDate() {
	return lastUpdatedDate;
}
public void setLastUpdatedDate(Date lastUpdatedDate) {
	this.lastUpdatedDate = lastUpdatedDate;
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
