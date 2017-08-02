package com.mall.b2bp.populators.order;

import com.mall.b2bp.models.order.OrderReturnModel;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderReturnVo;

/**
 * Created by USER on 2016/3/10.
 */
public class OrderReturnPopulator {

	public OrderReturnVo converModelToVo(OrderReturnModel orderModel) {
		OrderReturnVo vo = new OrderReturnVo();

		if (orderModel != null) {

			vo.setId(orderModel.getId());
			vo.setOrderId(orderModel.getOrderId());
			vo.setHybrisReturnId(orderModel.getHybrisReturnId());
			vo.setHybrisOrderId(orderModel.getHybrisOrderId());
			vo.setReturnType(orderModel.getReturnType());
			vo.setPickStore(orderModel.getPickStore());
			vo.setSupplierId(orderModel.getSupplierId());
			vo.setPickOrderId(orderModel.getPickOrderId());

			vo.setSpecialInstruction(orderModel.getSpecialInstruction());

			vo.setCustomerId(orderModel.getCustomerId());
			vo.setCustomerType(orderModel.getCustomerType());
			vo.setCustomerName(orderModel.getCustomerName());
			vo.setCustomerMobileNo(orderModel.getCustomerMobileNo());
			vo.setCustomerPhoneNo(orderModel.getCustomerPhoneNo());
			vo.setTenderType(orderModel.getTenderType());
			vo.setPaymentRef(orderModel.getPaymentRef());

			vo.setCollectDate(orderModel.getCollectDate());
			if (orderModel.getCollectDate() != null) {
				vo.setCollectDateStr(DateUtils.formatDate(
						orderModel.getCollectDate(), DateUtils.DATE_FORMAT_5));
			}

			vo.setReturnCreateDate(orderModel.getReturnCreateDate());
			if (orderModel.getReturnCreateDate() != null) {
				vo.setReturnCreateDateStr(DateUtils.formatDate(
						orderModel.getReturnCreateDate(),
						DateUtils.DATE_FORMAT_5));
			}

			vo.setCollectTimeSlot(orderModel.getCollectTimeSlot());
			vo.setContactName(orderModel.getContactName());
			vo.setContactMobileNo(orderModel.getContactMobileNo());
			vo.setContactPhoneNo(orderModel.getContactPhoneNo());
			vo.setCollectAddress(orderModel.getCollectAddress());
			vo.setCollectDistrict(orderModel.getCollectDistrict());

			vo.setCreatedDate(orderModel.getCreatedDate());
			vo.setCreatedBy(orderModel.getCreatedBy());
			vo.setLastUpdatedDate(orderModel.getLastUpdatedDate());
			vo.setLastUpdatedBy(orderModel.getLastUpdatedBy());

			vo.setRemark(orderModel.getRemark());
			vo.setReturnRequestStatus(orderModel.getReturnRequestStatus());

			vo.setReturnRequestUpdateDate(orderModel
					.getReturnRequestUpdateDate());
			if (orderModel.getReturnRequestUpdateDate() != null) {
				vo.setReturnRequestUpdateDateStr(DateUtils.formatDate(
						orderModel.getReturnRequestUpdateDate(),
						DateUtils.DATE_FORMAT_5));
			}
		}
		return vo;
	}

}
