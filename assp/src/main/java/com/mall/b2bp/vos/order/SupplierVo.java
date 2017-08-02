package com.mall.b2bp.vos.order;





/**
 *  TODO
 *  Created on 2016年8月22日.
 */
public class SupplierVo {
	
	private String supplier;

    private String pick; //Y/ N

    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
