package com.mall.b2bp.services.contract;

import com.mall.b2bp.models.contract.ContractDeliverMethodModel;
import com.mall.b2bp.vos.contract.DeliverMethodVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Harlan King on 2017/3/23.
 */
public interface DeliverMethodService {
    List<DeliverMethodVo> selectByContractId(BigDecimal id);

    int save(DeliverMethodVo deliver);

    int save(List<ContractDeliverMethodModel> list);

    int deleteByContractId(BigDecimal id);
}
