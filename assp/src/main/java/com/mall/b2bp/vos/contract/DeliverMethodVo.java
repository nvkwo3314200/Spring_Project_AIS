package com.mall.b2bp.vos.contract;

import com.mall.b2bp.models.contract.ContractDeliverMethodModel;
import com.mall.b2bp.models.parm.ParmModel;

/**
 * Created by Harlan King on 2017/3/24.
 */
public class DeliverMethodVo extends ContractDeliverMethodModel{
    private ParmModel deliverMethod;

    public ParmModel getDeliverMethod() {
        return deliverMethod;
    }

    public void setDeliverMethod(ParmModel deliverMethod) {
        this.deliverMethod = deliverMethod;
    }
}
