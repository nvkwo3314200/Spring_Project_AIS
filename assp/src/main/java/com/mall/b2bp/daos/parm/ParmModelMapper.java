package com.mall.b2bp.daos.parm;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.models.parm.ParmModel;
import com.mall.b2bp.vos.parm.ParmVo;

public interface ParmModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ParmModel record);

    int insertSelective(ParmModel record);
    
    List<ParmModel> selectByCriteria(ParmVo vo);
    
    List<ParmModel> search(ParmVo vo);

    ParmModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(ParmModel record);

    int updateByPrimaryKey(ParmModel record);
    
    List<ParmModel> selectSegmentList(ParmVo vo); 
}