package com.mall.b2bp.populators.order;

import com.mall.b2bp.models.order.OrderReturnEntryModel;
import com.mall.b2bp.vos.order.OrderReturnEntryVo;

public class OrderReturnEntryPopulator {

	
	
	
	public OrderReturnEntryVo converModelToVo(OrderReturnEntryModel orderModel) {
		OrderReturnEntryVo vo = new OrderReturnEntryVo();

		if (orderModel != null) {
			
			vo.setId(orderModel.getId());
			
			vo.setOrderReturnId(orderModel.getOrderReturnId());
			vo.setSkuId(orderModel.getSkuId());
			vo.setSkuType(orderModel.getSkuType());
			vo.setSupplierId(orderModel.getSupplierId());
			vo.setBrand(orderModel.getBrand());
			vo.setBrandSec(orderModel.getBrandSec());
			vo.setProductName(orderModel.getProductName());
			vo.setProductNameSec(orderModel.getProductNameSec());
			vo.setOrderQty(orderModel.getOrderQty());
			vo.setExpectedQty(orderModel.getExpectedQty());
			vo.setReturnReqQty(orderModel.getReturnReqQty());
			vo.setWriteOffQty(orderModel.getWriteOffQty());
			vo.setSizeDesc(orderModel.getSizeDesc());
			vo.setSkuCollectRmk(orderModel.getSkuCollectRmk());
			vo.setActualCollectedQty(orderModel.getActualCollectedQty());
			vo.setCreatedDate(orderModel.getCreatedDate());
			vo.setCreatedBy(orderModel.getCreatedBy());
			vo.setLastUpdatedDate(orderModel.getLastUpdatedDate());
			vo.setLastUpdatedBy(orderModel.getLastUpdatedBy());
		}
		
		
		return vo;
	}
}
