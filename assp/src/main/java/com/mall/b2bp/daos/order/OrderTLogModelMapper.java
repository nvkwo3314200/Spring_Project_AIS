package com.mall.b2bp.daos.order;

import com.mall.b2bp.models.order.OrderTLogModel;
import com.mall.b2bp.vos.order.OrderParameterVo;

import java.math.BigDecimal;
import java.util.List;

public interface OrderTLogModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(OrderTLogModel record);

    int insertSelective(OrderTLogModel record);

    OrderTLogModel selectByPrimaryKey(BigDecimal id);
    
    int updateByPrimaryKeySelective(OrderTLogModel record);

    int updateByPrimaryKey(OrderTLogModel record);
    
   List< OrderTLogModel> selectByOrderId(OrderParameterVo vo);
    
}