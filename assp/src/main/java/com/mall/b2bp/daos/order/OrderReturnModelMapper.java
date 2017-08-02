package com.mall.b2bp.daos.order;

import com.mall.b2bp.models.order.OrderReturnExportModel;
import com.mall.b2bp.models.order.OrderReturnModel;
import com.mall.b2bp.models.order.OrderReturnReceivedModel;
import com.mall.b2bp.vos.order.OrderParameterVo;

import java.math.BigDecimal;
import java.util.List;

public interface OrderReturnModelMapper {
	int deleteByPrimaryKey(BigDecimal id);

	int insert(OrderReturnModel record);

	int insertSelective(OrderReturnModel record);

	OrderReturnModel selectByPrimaryKey(BigDecimal id);

	OrderReturnModel selectByReturnId(String hybrisReturnId);

	int updateByPrimaryKeySelective(OrderReturnModel record);

	int updateByPrimaryKey(OrderReturnModel record);

	List<OrderReturnModel> selectByOrderId(OrderParameterVo vo);

	List<OrderReturnExportModel> exportOrderReturnList(OrderParameterVo orderVo);
	
	List< OrderReturnReceivedModel> getReturnReceived();

}