package com.mall.b2bp.vos.order;





import java.util.List;

/**
 *  TODO
 *  Created on 2016年8月22日.
 */
public class SupplierCollectionDataResponse {
	
	private String orderCode;
	private List<SupplierCollectionData> supplierDataList ;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<SupplierCollectionData> getSupplierDataList() {
        return supplierDataList;
    }

    public void setSupplierDataList(List<SupplierCollectionData> supplierDataList) {
        this.supplierDataList = supplierDataList;
    }
}
