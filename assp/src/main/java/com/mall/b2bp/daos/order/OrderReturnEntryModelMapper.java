package com.mall.b2bp.daos.order;

import com.mall.b2bp.models.order.OrderReturnEntryModel;

import java.math.BigDecimal;
import java.util.List;

public interface OrderReturnEntryModelMapper {
//    int deleteByPrimaryKey(BigDecimal id);
//
//    int insert(OrderReturnEntryModel record);

    int insertSelective(OrderReturnEntryModel record);

    OrderReturnEntryModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(OrderReturnEntryModel record);
//
//    int updateByPrimaryKey(OrderReturnEntryModel record);
    
    List<OrderReturnEntryModel> selectByReturnId(BigDecimal id);
    
}