package com.mall.b2bp.populators.order;

import com.mall.b2bp.models.order.OrderTLogModel;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderTLogVo;

public class OrderTLogPopulator {

	public OrderTLogVo converModelToVo(OrderTLogModel m) {
		OrderTLogVo vo = new OrderTLogVo();

		if (m != null) {
			vo.setId( m.getId());
			vo.setHybrisOrderId(m.getHybrisOrderId());
			vo.setSkuId(m.getSkuId());
			vo.setTransactionDate(m.getTransactionDate());
			
			
			if (m.getTransactionDate() != null) {
				vo.setTransactionDateStr(DateUtils.formatDate(
						m.getTransactionDate(),
						DateUtils.DATE_FORMAT_5));
			}
			
			vo.setOrderCreatedDate(m.getOrderCreatedDate());
			
			if (m.getOrderCreatedDate() != null) {
				vo.setOrderCreatedDateStr(DateUtils.formatDate(
						m.getOrderCreatedDate(),
						DateUtils.DATE_FORMAT_5));
			}
			
			vo.setAmount(m.getAmount());
			vo.setQty(m.getQty());
			vo.setNetSales(m.getNetSales());
			vo.setTypeOfDistribution(m.getTypeOfDistribution());
			vo.setCreatedBy(m.getCreatedBy());
			vo.setCreatedDate(m.getCreatedDate());
			
			vo.setLastUpdatedBy(m.getLastUpdatedBy());
			vo.setLastUpdatedDate(m.getLastUpdatedDate());
			
		}
		
		
		return vo;
	}
}
