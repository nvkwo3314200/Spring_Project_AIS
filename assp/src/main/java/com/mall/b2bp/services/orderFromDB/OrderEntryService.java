package com.mall.b2bp.services.orderFromDB;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderEntryModel;
import com.mall.b2bp.vos.order.OrderEntryVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2016/3/16.
 */
public interface OrderEntryService {

	int insertSelective(OrderEntryModel record) throws ServiceException;

	OrderEntryVo selectByPrimaryKey(BigDecimal id) throws ServiceException;

	List<OrderEntryVo> selectByOrderId(BigDecimal id) throws ServiceException;

	int updateByPrimaryKeySelective(OrderEntryModel record)
			throws ServiceException;

	int updateByPrimaryKey(OrderEntryModel record) throws ServiceException;

	OrderEntryModel selectByHashMap(Map hashMap) throws ServiceException;

	int updateTotalReturnedQtyFromRVS(BigDecimal id);

	int updateTotalDeliveryQtyFromTlog(BigDecimal id);
}
