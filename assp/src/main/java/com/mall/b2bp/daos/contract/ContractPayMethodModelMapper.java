package com.mall.b2bp.daos.contract;

import com.mall.b2bp.models.contract.ContractPayMethodModel;
import com.mall.b2bp.vos.contract.PayMethodVo;

import java.math.BigDecimal;
import java.util.List;

public interface ContractPayMethodModelMapper {

    BigDecimal selectNextId();
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ContractPayMethodModel record);
    int insertContractPayBatch(List<ContractPayMethodModel> list);

    int insertSelective(ContractPayMethodModel record);

    ContractPayMethodModel selectByPrimaryKey(BigDecimal id);
    List<ContractPayMethodModel> selectByContractId(BigDecimal id);

    int updateByPrimaryKeySelective(ContractPayMethodModel record);

    int updateByPrimaryKey(ContractPayMethodModel record);

    List<PayMethodVo> selectPayVosByContractId(BigDecimal id);

    int deleteByContractId(BigDecimal id);


}