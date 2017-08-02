package com.mall.b2bp.services.impl.contract;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.services.contract.DeliverMethodService;
import com.mall.b2bp.services.contract.PayMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.contract.ContractModelMapper;
import com.mall.b2bp.models.contract.ContractModel;
import com.mall.b2bp.services.contract.ContractService;
import com.mall.b2bp.vos.contract.ContractVo;

/**
 * Created by Harlan King on 2017/3/21.
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {
    private ContractModelMapper contractModelMapper;
    private PayMethodService payMethodService;
    private DeliverMethodService deliverMethodService;
    private List<ContractVo> voList;
    private ContractVo contractVo;

    public ContractModelMapper getContractModelMapper() {
        return contractModelMapper;
    }

    @Autowired
    public void setContractModelMapper(ContractModelMapper contractModelMapper) {
        this.contractModelMapper = contractModelMapper;
    }

    public PayMethodService getPayMethodService() {
        return payMethodService;
    }

    @Autowired
    public void setPayMethodService(PayMethodService payMethodService) {
        this.payMethodService = payMethodService;
    }

    public DeliverMethodService getDeliverMethodService() {
        return deliverMethodService;
    }

    @Autowired
    public void setDeliverMethodService(DeliverMethodService deliverMethodService) {
        this.deliverMethodService = deliverMethodService;
    }

    @Override
    public ContractModel selectBySupplierAndRef(BigDecimal supplierId, String ref) {
        return contractModelMapper.selectBySupplierAndRef(supplierId, ref);
    }

    @Override
    public int selectCountBySupplierAndRef(BigDecimal supplierId, String ref) {
        return contractModelMapper.selectCountBySupplierAndRef(supplierId, ref);
    }

    @Override
    public List<ContractVo> selectContracts(BigDecimal supplierId) {
        voList = contractModelMapper.selectVOBySupplier(supplierId);
        return voList;
    }

    @Override
    public ContractVo selectById(BigDecimal id) {
        ContractModel m = contractModelMapper.selectByPrimaryKey(id);
        if (m != null && m.getId().compareTo(id) == 0) {
            contractVo = new ContractVo(m.getId(),
                    m.getRef(),
                    m.getSupplierId(),
                    m.getStartDate(),
                    m.getEndDate(),
                    m.getStatus(),
                    m.getCreatedBy(),
                    m.getCreatedDate(),
                    m.getLastUpdatedBy(),
                    m.getLastUpdatedDate());
        } else {
            contractVo = new ContractVo();
        }

        return contractVo;
    }

    @Override
    public ContractVo selectVOById(BigDecimal id) {
        contractVo = contractModelMapper.selectVOByPrimaryKey(id);
        return contractVo;
    }

    @Override
    public ContractModel save(ContractVo contractVo) {
        int r = -1;
        if (contractVo != null) {
            ContractModel contractModel;
            if (contractVo.getId() != null) {
                contractModel = new ContractModel(contractVo.getId(),
                        contractVo.getRef(),
                        contractVo.getSupplierId(),
                        contractVo.getStartDate(),
                        contractVo.getEndDate(),
                        contractVo.getStatus(),
                        contractVo.getCreatedBy(),
                        contractVo.getLastUpdatedDate(),
                        contractVo.getLastUpdatedBy(),
                        contractVo.getLastUpdatedDate());

            } else {
                contractModel = new ContractModel(contractModelMapper.selectNextId(),
                        contractVo.getRef(),
                        contractVo.getSupplierId(),
                        contractVo.getStartDate(),
                        contractVo.getEndDate(),
                        contractVo.getStatus(),
                        contractVo.getCreatedBy(),
                        contractVo.getLastUpdatedDate(),
                        contractVo.getLastUpdatedBy(),
                        contractVo.getLastUpdatedDate());
            }

            try {
                r = contractVo.getId() != null ? contractModelMapper.updateByPrimaryKey(contractModel) : contractModelMapper.insert(contractModel);
                return r>0 ? contractModel:null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }

    @Override
    public int delete(BigDecimal[] ids) {
        int i = 0;
        for (BigDecimal id : ids) {
            i += contractModelMapper.deleteByPrimaryKey(id);
        }

        return i;
    }
}
