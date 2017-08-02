package com.mall.b2bp.services.impl.order;

import com.mall.b2bp.daos.order.OrderEntryModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderEntryModel;
import com.mall.b2bp.populators.order.OrderEntryPopulator;
import com.mall.b2bp.services.order.OrderEntryService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.vos.order.OrderEntryVo;
import com.mall.b2bp.vos.order.OrderParameterVo;
import com.mall.b2bp.vos.user.UserVo;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Created by USER on 2016/3/16.
 */
@Service("orderEntryService")
public class OrderEntryServiceImpl implements OrderEntryService {
	private static final Logger LOG = LoggerFactory
			.getLogger(OrderEntryServiceImpl.class);
	private OrderEntryModelMapper orderEntryModelMapper;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	public OrderEntryModelMapper getOrderEntryModelMapper() {
		return orderEntryModelMapper;
	}

	@Autowired
	public void setOrderEntryModelMapper(
			OrderEntryModelMapper orderEntryModelMapper) {
		this.orderEntryModelMapper = orderEntryModelMapper;
	}

	@Override
	public int insertSelective(OrderEntryModel record) throws ServiceException {
		try {
			return orderEntryModelMapper.insertSelective(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public OrderEntryVo selectByPrimaryKey(BigDecimal id)
			throws ServiceException {
		try {
			OrderEntryPopulator populator = new OrderEntryPopulator();
			return populator.converModelToVo(orderEntryModelMapper
					.selectByPrimaryKey(id));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<OrderEntryVo> selectByOrderId(BigDecimal id)
			throws ServiceException {

		OrderParameterVo vo = new OrderParameterVo();
		String supplierId = null;
		
		UserVo userVo = sessionService.getCurrentUser();
		if (userVo != null) {
			if ("SUPPLIER".equals(userVo.getUserRole())) {
				supplierId = userVo.getSupplierId();
			}
		}

		vo.setSupplierId(supplierId);
		vo.setOrderId(id != null ? id.toString() : null);

		try {
			List<OrderEntryModel> orderEntryModelList = orderEntryModelMapper.selectByOrderId(vo);

			List<OrderEntryVo> list = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(orderEntryModelList)) {
				OrderEntryPopulator populator = new OrderEntryPopulator();
				for (OrderEntryModel model : orderEntryModelList) {
					list.add(populator.converModelToVo(model));
				}
			}
			return list;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int updateByPrimaryKeySelective(OrderEntryModel record)
			throws ServiceException {
		try {
			return orderEntryModelMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int updateByPrimaryKey(OrderEntryModel record)
			throws ServiceException {
		try {
			return orderEntryModelMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public OrderEntryModel selectByHashMap(Map hashMap) throws ServiceException {

		try {
			return orderEntryModelMapper.selectByHashMap(hashMap);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Override
	public int updateTotalReturnedQtyFromRVS(BigDecimal orderId) {		
		return orderEntryModelMapper.updateTotalReturnedQtyFromRVS(orderId);
	}

	@Override
	public int updateTotalDeliveryQtyFromTlog(BigDecimal orderId) {
		return orderEntryModelMapper.updateTotalDeliveryQtyFromTlog(orderId);
	}

}
