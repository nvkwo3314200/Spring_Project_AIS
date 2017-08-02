package com.mall.b2bp.vos.order;

import java.math.BigDecimal;
import java.util.List;

public class OrderEntrySerialNoWebServiceVo
{
    private String code;
    private BigDecimal entryNumber;
    private List<OrderEntrySerialNoVo> orderEntrySerialNoVoList;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public BigDecimal getEntryNumber()
    {
        return entryNumber;
    }

    public void setEntryNumber(BigDecimal entryNumber)
    {
        this.entryNumber = entryNumber;
    }

    public List<OrderEntrySerialNoVo> getOrderEntrySerialNoVoList()
    {
        return orderEntrySerialNoVoList;
    }

    public void setOrderEntrySerialNoVoList(List<OrderEntrySerialNoVo> orderEntrySerialNoVoList)
    {
        this.orderEntrySerialNoVoList = orderEntrySerialNoVoList;
    }
}
