package com.mall.b2bp.services.impl.contract;

import com.mall.b2bp.daos.contract.ContractPayMethodModelMapper;
import com.mall.b2bp.models.contract.ContractPayMethodModel;
import com.mall.b2bp.services.contract.PayMethodService;
import com.mall.b2bp.vos.contract.PayMethodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Harlan King on 2017/3/23.
 */
@Service("payMethodService")
public class PayMethodServiceimpl implements PayMethodService {
    private ContractPayMethodModelMapper contractPayMethodModelMapper;

    public ContractPayMethodModelMapper getContractPayMethodModelMapper() {
        return contractPayMethodModelMapper;
    }

    @Autowired
    public void setContractPayMethodModelMapper(ContractPayMethodModelMapper contractPayMethodModelMapper) {
        this.contractPayMethodModelMapper = contractPayMethodModelMapper;
    }


    @Override
    public List<PayMethodVo> selectByContractId(BigDecimal id){
        return contractPayMethodModelMapper.selectPayVosByContractId(id);
    }

    @Override
    public int deleteByContractId(BigDecimal id) {
        return contractPayMethodModelMapper.deleteByContractId(id);
    }

    @Override
    public int save(List<ContractPayMethodModel> list) {
        for (ContractPayMethodModel pay: list) {
            pay.setId(contractPayMethodModelMapper.selectNextId());
        }
        return contractPayMethodModelMapper.insertContractPayBatch(list);
    }
}
