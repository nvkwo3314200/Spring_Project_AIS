package com.mall.b2bp.models.dept;

import java.math.BigDecimal;

public class ClassModelKey {
    private BigDecimal classId;

    private BigDecimal deptId;

    public BigDecimal getClassId() {
        return classId;
    }

    public void setClassId(BigDecimal classId) {
        this.classId = classId;
    }

    public BigDecimal getDeptId() {
        return deptId;
    }

    public void setDeptId(BigDecimal deptId) {
        this.deptId = deptId;
    }
}