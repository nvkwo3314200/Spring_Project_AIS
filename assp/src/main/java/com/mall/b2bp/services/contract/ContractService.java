package com.mall.b2bp.services.contract;

import com.mall.b2bp.models.contract.ContractModel;
import com.mall.b2bp.vos.contract.ContractVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Harlan King on 2017/3/21.
 */
public interface ContractService {
    ContractModel selectBySupplierAndRef(BigDecimal supplierId, String ref);

    int selectCountBySupplierAndRef(BigDecimal supplierId, String ref);

    List<ContractVo> selectContracts(BigDecimal supplierId);

    ContractVo selectById(BigDecimal id);

    ContractVo selectVOById(BigDecimal id);

    ContractModel save(ContractVo contractVo);

    int delete(BigDecimal[] ids);
}
