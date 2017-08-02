package com.mall.b2bp.services.impl.contract;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.contract.ContractDeliverMethodModelMapper;
import com.mall.b2bp.models.contract.ContractDeliverMethodModel;
import com.mall.b2bp.services.contract.DeliverMethodService;
import com.mall.b2bp.vos.contract.DeliverMethodVo;

/**
 * Created by Harlan King on 2017/3/23.
 */
@Service("deliverMethodService")
public class DeliverMethodServiceimpl implements DeliverMethodService {
    private ContractDeliverMethodModelMapper contractDeliverMethodModelMapper;

    public ContractDeliverMethodModelMapper getContractDeliverMethodModelMapper() {
        return contractDeliverMethodModelMapper;
    }

    @Autowired
    public void setContractDeliverMethodModelMapper(ContractDeliverMethodModelMapper contractDeliverMethodModelMapper) {
        this.contractDeliverMethodModelMapper = contractDeliverMethodModelMapper;
    }

    @Override
    public List<DeliverMethodVo> selectByContractId(BigDecimal id) {
        return contractDeliverMethodModelMapper.selectByContractId(id);
    }

    @Override
    public int save(DeliverMethodVo deliver) {
        if (deliver != null && deliver.getContractId() != null && deliver.getDeliverMethodId() != null) {
            ContractDeliverMethodModel model = new ContractDeliverMethodModel(contractDeliverMethodModelMapper.selectNextId(),
                    deliver.getContractId(),
                    deliver.getDeliverMethodId(),
                    deliver.getCreatedBy(),
                    deliver.getCreatedDate(),
                    deliver.getLastUpdatedBy(),
                    deliver.getLastUpdatedDate()

            );
            return contractDeliverMethodModelMapper.insert(model);
        } else return 0;

    }

    @Override
    public int save(List<ContractDeliverMethodModel> list) {
        for (ContractDeliverMethodModel model : list) {
            model.setId(contractDeliverMethodModelMapper.selectNextId());
        }
        return contractDeliverMethodModelMapper.insertContractDeliverBatch(list);


    }

    @Override
    public int deleteByContractId(BigDecimal id) {
        return contractDeliverMethodModelMapper.deleteByContractId(id);
    }

}
