package com.mall.b2bp.services.contract;

import com.mall.b2bp.models.contract.ContractPayMethodModel;
import com.mall.b2bp.vos.contract.PayMethodVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Harlan King on 2017/3/23.
 */
public interface PayMethodService {
    List<PayMethodVo> selectByContractId(BigDecimal id);

    int deleteByContractId(BigDecimal id);

    int save(List<ContractPayMethodModel> list);
}
