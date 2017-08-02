package com.mall.b2bp.vos.order;



import java.util.List;

/**
 *  TODO
 *  Created on 2016年8月22日.
 */
public class SupplierCollectionVoRequest {
	
	private String orderCode;
	private List<SupplierVo> supplierVoList ;

    public List<SupplierVo> getSupplierVoList() {
        return supplierVoList;
    }

    public void setSupplierVoList(List<SupplierVo> supplierVoList) {
        this.supplierVoList = supplierVoList;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
