package com.mall.b2bp.vos.product;


/**
 * Created by USER on 2016/3/14.
 */
public class ProductParameterVo {
    private String[] supplier;
	private String supplierCode;
    private String supplierProductCode;
    private String productCode;
    private String[] status;
    private String onlineUpdatedInd;
    public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	private String shortDescEn;
    private Object[] deliveryMode;


	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Object[] getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(Object[] deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	private String supplierId;
    

    public String[] getSupplier() {
        return supplier;
    }

    public void setSupplier(String[] supplier) {
        this.supplier = supplier;
    }

	public String getSupplierProductCode() {
		return supplierProductCode;
	}

	public void setSupplierProductCode(String supplierProductCode) {
		this.supplierProductCode = supplierProductCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	public String getShortDescEn() {
		return shortDescEn;
	}

	public void setShortDescEn(String shortDescEn) {
		this.shortDescEn = shortDescEn;
	}

	

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getOnlineUpdatedInd() {
		return onlineUpdatedInd;
	}

	public void setOnlineUpdatedInd(String onlineUpdatedInd) {
		this.onlineUpdatedInd = onlineUpdatedInd;
	}

    
}
