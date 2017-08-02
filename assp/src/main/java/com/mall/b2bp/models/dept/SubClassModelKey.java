package com.mall.b2bp.models.dept;

import java.math.BigDecimal;

public class SubClassModelKey {
    private BigDecimal classId;

    private BigDecimal subClassId;

    public BigDecimal getClassId() {
        return classId;
    }

    public void setClassId(BigDecimal classId) {
        this.classId = classId;
    }


    public BigDecimal getSubClassId() {
        return subClassId;
    }

    public void setSubClassId(BigDecimal subClassId) {
        this.subClassId = subClassId;
    }
}