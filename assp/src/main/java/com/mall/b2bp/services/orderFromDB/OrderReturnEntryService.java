package com.mall.b2bp.services.orderFromDB;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderReturnEntryModel;
import com.mall.b2bp.vos.order.OrderReturnEntryVo;

public interface OrderReturnEntryService {

	
//	int deleteByPrimaryKey(BigDecimal id);
//
//    int insert(OrderReturnEntryModel record);

    int insertSelective(OrderReturnEntryModel record);

    OrderReturnEntryModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(OrderReturnEntryModel record);
//
//    int updateByPrimaryKey(OrderReturnEntryModel record);
    
    List<OrderReturnEntryVo> selectByReturnId(BigDecimal id) throws ServiceException;
}
