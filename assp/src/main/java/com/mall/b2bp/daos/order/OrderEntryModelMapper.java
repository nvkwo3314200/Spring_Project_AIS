package com.mall.b2bp.daos.order;

import com.mall.b2bp.models.order.OrderEntryModel;
import com.mall.b2bp.vos.order.OrderParameterVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderEntryModelMapper {
	int deleteByPrimaryKey(BigDecimal id);

	int insert(OrderEntryModel record);

	int insertSelective(OrderEntryModel record);

	OrderEntryModel selectByPrimaryKey(BigDecimal id);

	List<OrderEntryModel> selectByOrderId(OrderParameterVo vo);

	int updateByPrimaryKeySelective(OrderEntryModel record);

	int updateByPrimaryKey(OrderEntryModel record);

	Integer getPkId();

	OrderEntryModel selectByHashMap(Map hashMap);
	
	int updateTotalReturnedQtyFromRVS(BigDecimal orderId);
	
	int updateTotalDeliveryQtyFromTlog(BigDecimal orderId);
	
}