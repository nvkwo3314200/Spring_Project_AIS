package com.mall.b2bp.enums;

public enum ImportLogType {
	
	PRODUCT_MASTER_EXPORT_TO_RETEK("LOG001","Product master export to Retek"),
	PRODUCT_BARCODE_EXPORT_TO_RETEK("LOG002","Product barcode export to Retek"),
	PRODUCT_IMAGE_EXPORT_TO_RETEK("LOG003","Product image export to Retek"),
	PRODUCT_MASTER_ACK_FROM_RETEK("LOG004","Product master ack from Retek"),
	PRODUCT_BARCODE_ACK_FROM_RETEK("LOG005","Product barcode ack from Retek"),
	PRODUCT_IMAGE_ACK_FROM_RETEK("LOG006","Product image ack from Retek");
	
	
	private ImportLogType(String importLogCode, String importLogDesc) {
		this.importLogCode = importLogCode;
		this.importLogDesc = importLogDesc;
	}
	private String importLogCode;
	private String importLogDesc;
	
	public static ImportLogType getLogType(String importLogCode){
		for(ImportLogType importLogType : ImportLogType.values()){
			if(importLogType.getImportLogCode().equals(importLogCode)){
				return importLogType;
			}
		}
		return null;
	}
	
	public String getImportLogCode() {
		return importLogCode;
	}
	
	public String getImportLogDesc() {
		return importLogDesc;
	}
	

}
