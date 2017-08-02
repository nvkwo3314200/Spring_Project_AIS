package com.mall.b2bp.services.impl.orderFromDB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.order.OrderReturnEntryModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderReturnEntryModel;
import com.mall.b2bp.populators.order.OrderReturnEntryPopulator;
import com.mall.b2bp.services.orderFromDB.OrderReturnEntryService;
import com.mall.b2bp.vos.order.OrderReturnEntryVo;

@Service("orderReturnEntryServiceFromDB")
public class OrderReturnEntryServiceImpl implements OrderReturnEntryService {
	private static final Logger LOG = LoggerFactory
			.getLogger(OrderReturnEntryServiceImpl.class);
	private OrderReturnEntryModelMapper orderReturnEntryModelMapper;

	public OrderReturnEntryModelMapper getOrderReturnEntryModelMapper() {
		return orderReturnEntryModelMapper;
	}

	@Autowired
	public void setOrderReturnEntryModelMapper(
			OrderReturnEntryModelMapper orderReturnEntryModelMapper) {
		this.orderReturnEntryModelMapper = orderReturnEntryModelMapper;
	}

	// @Override
	// public int deleteByPrimaryKey(BigDecimal id) {
	// // TODO Auto-generated method stub
	// return orderReturnEntryModelMapper.deleteByPrimaryKey(id);
	// }

	// @Override
	// public int insert(OrderReturnEntryModel record) {
	// // TODO Auto-generated method stub
	// return orderReturnEntryModelMapper.insert(record);
	// }

	@Override
	public int insertSelective(OrderReturnEntryModel record) {
		return orderReturnEntryModelMapper.insertSelective(record);
	}

	@Override
	public OrderReturnEntryModel selectByPrimaryKey(BigDecimal id) {
		return orderReturnEntryModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<OrderReturnEntryVo> selectByReturnId(BigDecimal id)
			throws ServiceException {

		List<OrderReturnEntryVo> retList = new ArrayList<>();
		try {

			List<OrderReturnEntryModel> list = orderReturnEntryModelMapper.selectByReturnId(id);

			OrderReturnEntryPopulator p = new OrderReturnEntryPopulator();

			if (CollectionUtils.isNotEmpty(list)) {
				for (OrderReturnEntryModel m : list) {
					retList.add(p.converModelToVo(m));
				}
			}

			return retList;

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	 @Override
	 public int updateByPrimaryKeySelective(OrderReturnEntryModel record) {

		 return orderReturnEntryModelMapper.updateByPrimaryKeySelective(record);
	 }

//	 @Override
//	 public int updateByPrimaryKey(OrderReturnEntryModel record) {
//	 return orderReturnEntryModelMapper.updateByPrimaryKey(record);
//	 }

}
