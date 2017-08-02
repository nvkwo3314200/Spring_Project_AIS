package com.mall.b2bp.enums;

public enum CronJob {
	PRODUCT_MASTER_SYNC_JOB("productMasterSyncJobMethod", "Product Master Sync Job(to Retek)"),
	PRODUCT_IMAGE_SYNC_JOB("productImageSyncJobMethod", "Product Image Sync Job(to Retek)"),
	PRODUCT_BARCODE_SYNC_JOB("productBarcodeSyncJobMethod", "Product Barcode Sync Job(to Retek)"),
	PRODUCT_MASTER_ACKNOWLEDGEMENT_JOB("productMasterSyncExceptionMethod", "Product Master Acknowledgement Job"),
	PRODUCT_BARCODE_ACKNOWLEDGEMENT_JOB("productBarcodeSyncExceptionMethod", "Product Barcode Acknowledgement Job"),
	PRODUCT_IMAGE_ACKNOWLEDGEMENT_JOB("productImagesSyncExceptionMethod", "Product Image Acknowledgement Job"),
	PRODUCT_MASTER_SYNC_FROM_RETEK_JOB("productMasterSyncFromRetekJobMethod", "Product Master Sync Job(from Retek)"),
	PRODUCT_IMAGE_SYNC_FROM_RETEK_JOB("productImagesSyncFromRetekJobMethod", "Product Image Sync Job(from Retek)"),
	PRODUCT_BARCODE_SYNC_FROM_RETEK_JOB("productBarcodeSyncFromRetekJobMethod", "Product Barcode Sync Job(from Retek)"),
	BRAND_MASTER_SYNC_JOB("brandMasterJobMethod", "Brand Master Sync Job"),
	ORDER_INVOICE_UPDATE_JOB("SpringInvoiceIndJobMethod", "Order Invoice Update Job"),
	ORDER_DATA_ACHIEVE_JOB("deleteOrderAchieveDataQtzJobMethod", "Order Data Achieve Job"),
	ORDER_DATA_IMPORT_JOB("newOrderImportQtzJobMethod", "Order Data Import Job"),
	ORDER_DATA_EXPORT_JOB("homeOrderJobMethod", "Order Data Export Job"),
	ORDER_RETURN_IMPORT_JOB("returnOrderImportJobMethod", "Order Return Data Import Job"),
	ORDER_RETURN_EXPORT_JOB("orderReturnReceivedJobMethod", "Order Return Data Export Job"),
	SUPPLIER_BRAND_EXPORT_JOB("supplierBrandExportJobMethod", "Supplier & Brand Data Export Job"),
	CONSIGNMENT_ORDER_COMPLETED_IMPORT_JOB("consignmentOrderCompletedImportJobMethod", "Consignment Order Completed Data Import Job"),
	;

	private String jobId;
	private String jobName;
    
    private CronJob(String jobId, String jobName) {
        this.jobId = jobId;
        this.jobName = jobName;
    }
    
    public static String getJobName(String jobId) {
        for (CronJob job : CronJob.values()) {
            if (job.getJobId().equals(jobId)) {
                return job.getJobName();
            }
        }
        return jobId;
    }

	public String getJobId() {
		return jobId;
	}

	public String getJobName() {
		return jobName;
	}

}
