package com.mall.b2bp.vos.product;

import java.math.BigDecimal;

public class SubProductInfo {
	private BigDecimal id;
	
    public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	private String supplierProductCode;
    
    private String colorDesc;

    private String variantColor;

    private String variantSize;
    
    private BigDecimal baseProductId;
    
    private String status;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getBaseProductId() {
		return baseProductId;
	}

	public void setBaseProductId(BigDecimal baseProductId) {
		this.baseProductId = baseProductId;
	}

	public String getSupplierProductCode() {
		return supplierProductCode;
	}

	public void setSupplierProductCode(String supplierProductCode) {
		this.supplierProductCode = supplierProductCode;
	}

	public String getColorDesc() {
		return colorDesc;
	}

	public void setColorDesc(String colorDesc) {
		this.colorDesc = colorDesc;
	}

	public String getVariantColor() {
		return variantColor;
	}

	public void setVariantColor(String variantColor) {
		this.variantColor = variantColor;
	}

	public String getVariantSize() {
		return variantSize;
	}

	public void setVariantSize(String variantSize) {
		this.variantSize = variantSize;
	}
    
    


}
