package com.mall.b2bp.daos.contract;

import com.mall.b2bp.models.contract.ContractModel;
import com.mall.b2bp.vos.contract.ContractVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


public interface ContractModelMapper {
    BigDecimal selectNextId ();
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ContractModel record);

    int insertSelective(ContractModel record);

    ContractModel selectByPrimaryKey(@Param("id")BigDecimal id);
    ContractModel selectBySupplierAndRef(@Param("id")BigDecimal supplierId, @Param("ref") String ref);
    int selectCountBySupplierAndRef(@Param("id")BigDecimal supplierId, @Param("ref") String ref);
    ContractVo selectVOByPrimaryKey(@Param("id")BigDecimal id);
   List<ContractVo> selectVOBySupplier (@Param("id")BigDecimal id);
    List<ContractModel> selectBySupplierId(@Param("id") BigDecimal id);

    int updateByPrimaryKeySelective(ContractModel record);

    int updateByPrimaryKey(ContractModel record);
}