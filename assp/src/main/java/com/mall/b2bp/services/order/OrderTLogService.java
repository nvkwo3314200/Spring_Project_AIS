package com.mall.b2bp.services.order;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderTLogModel;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.order.OrderTLogVo;

public interface OrderTLogService {

	int insertSelective(OrderTLogModel record) throws ServiceException;


	List<OrderTLogVo> selectByOrderId(BigDecimal id) throws ServiceException;
	
	public boolean updateOrderCompleted(String fileName, ErrorLog errorLog)
			throws ServiceException ;
}
