package com.mall.b2bp.vos.contract;

import java.math.BigDecimal;

/**
 * Created by Harlan King on 2017/3/24.
 */
public class SaveContractPayAndDeliverVo {
    private BigDecimal[] ids;
    private BigDecimal id;

    public BigDecimal[] getIds() {
        return ids;
    }

    public void setIds(BigDecimal[] ids) {
        this.ids = ids;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }
}
