package com.mall.b2bp.daos.mall;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.models.mall.MallModel;
import com.mall.b2bp.vos.mall.MallVo;

public interface MallModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(MallModel record);

    int insertSelective(MallModel record);

    MallModel selectByPrimaryKey(BigDecimal id);
    
    List<MallModel> selectByCriteria(MallVo record);
    
    List<MallModel> search(MallVo record);

    int updateByPrimaryKeySelective(MallModel record);

    int updateByPrimaryKey(MallModel record);
}