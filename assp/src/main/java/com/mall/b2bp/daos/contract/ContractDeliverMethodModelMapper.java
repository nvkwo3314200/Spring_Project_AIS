package com.mall.b2bp.daos.contract;

import com.mall.b2bp.models.contract.ContractDeliverMethodModel;
import com.mall.b2bp.vos.contract.DeliverMethodVo;

import java.math.BigDecimal;
import java.util.List;


public interface ContractDeliverMethodModelMapper {

    BigDecimal selectNextId();
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ContractDeliverMethodModel record);
    int insertContractDeliverBatch(List<ContractDeliverMethodModel> list);

    int insertSelective(ContractDeliverMethodModel record);

    ContractDeliverMethodModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(ContractDeliverMethodModel record);

    int updateByPrimaryKey(ContractDeliverMethodModel record);

    List<DeliverMethodVo> selectByContractId(BigDecimal id);

    int deleteByContractId(BigDecimal id);
}