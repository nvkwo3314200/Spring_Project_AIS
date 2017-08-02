package com.mall.b2bp.vos.contract;

import com.mall.b2bp.models.contract.ContractPayMethodModel;
import com.mall.b2bp.models.parm.ParmModel;

/**
 * Created by Harlan King on 2017/3/23.
 */
public class PayMethodVo extends ContractPayMethodModel{
    private ParmModel payMethod;

    public ParmModel getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(ParmModel payMethod) {
        this.payMethod = payMethod;
    }
}
