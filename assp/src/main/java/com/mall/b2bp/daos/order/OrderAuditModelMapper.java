package com.mall.b2bp.daos.order;

import java.util.List;

import com.mall.b2bp.models.order.OrderAuditModel;
import com.mall.b2bp.vos.order.OrderAuditVo;

public interface OrderAuditModelMapper {
   /* int deleteByPrimaryKey(BigDecimal id);

    int insert(OrderAuditModel record);

    int insertSelective(OrderAuditModel record);	

    OrderAuditModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(OrderAuditModel record);

    int updateByPrimaryKey(OrderAuditModel record);*/
	
	  List<OrderAuditModel> getAll();
	  List<OrderAuditModel> viewHistory(OrderAuditVo productAuditVo);
	    
	  int deleteByOrderId(String productId);
}