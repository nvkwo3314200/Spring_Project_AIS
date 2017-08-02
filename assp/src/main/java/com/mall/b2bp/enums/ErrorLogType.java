package com.mall.b2bp.enums;

public enum ErrorLogType {
	PRODUCT_MASTER_SYNC_FROM_RETEK("Product master sync from Retek"),
	PRODUCT_BARCODE_SYNC_FROM_RETEK("Product barcode sync from Retek"),
	
	PRODUCT_IMAGE_SYNC_FROM_RETEK("Product image sync from Retek"),
	
	PRODUCT_MASTER_SYNC_IMPORT("Product master sync import"),
	PRODUCT_BARCODE_SYNC_IMPORT("Product barcode sync import"),
	PRODUCT_IMAGE_SYNC_IMPORT("Product image sync import"),
	

	BRAND_SYNC_FROM_RETEK("Brand sync from Retek"),
	
	//order
	ORDER_RETURN_REQUEST_IMPORT("Order Return – Hybris to PSSP(RVS)"),
	HOME_ORDER("Home Order"),
	RETURN_RECEIVED("Order Return – PSSP to Hybris(Return receive)"),
	NEW_ORDER_IMPORT("New Order Import"),
	CONSIGNMENT_ORDER_COMPLETED("Consignment Order Completed"),
	
	IS54SU("Supplier Interface"),
	IS20MD("Brand Master Data Interface"),
	
	
	PRODUCT_MASTER_SYNC_EXPORT("Product master sync export"),
	PRODUCT_BARCODE_SYNC_EXPORT("Product barcode sync export"),
	PRODUCT_IMAGE_SYNC_EXPORT("Product image sync export");
	
	
	
	
	
	
	private String errorType;
    
    private ErrorLogType(String errorType) {
        this.errorType = errorType;
    }

    public static ErrorLogType getType(String errorType) {
        for (ErrorLogType type : ErrorLogType.values()) {
            if (type.getErrorType().equals(errorType)) {
                return type;
            }
        }
        return null;
    }

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
    
    

	
}
