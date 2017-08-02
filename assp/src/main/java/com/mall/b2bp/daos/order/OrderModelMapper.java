package com.mall.b2bp.daos.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.order.OrderExportModel;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.vos.order.OrderParameterVo;


public interface OrderModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(OrderModel record);

    int insertSelective(OrderModel record);

    OrderModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(OrderModel record);

    int updateByPrimaryKey(OrderModel record);

    List<OrderModel> viewOrderList(OrderParameterVo vo);
    List<OrderExportModel> exportOrderList(OrderParameterVo vo);

	Integer getPkId();
	
    List<OrderModel> selectByHybrisOrderId(String id);

    List<OrderModel> getOrderListByInvoiceInd();
    List<OrderModel> selectByOrderHome();

    List<OrderModel> getOrderConsignmentID(OrderParameterVo vo);
    List<OrderModel> getOrderByidAndskuId(OrderParameterVo vo);
    
    List<OrderModel> getOutOfDateOrderList(Map map);
    
    OrderModel getOrderByPickOrderId(String pickOrderId);
    
}