package com.mall.b2bp.populators.order;

import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.models.order.OrderReturnExportModel;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderReturnExportVo;

/**
 * Created by USER on 2016/3/10.
 */
public class OrderReturnExportPopulator {


    public OrderReturnExportVo converModelToVo(OrderReturnExportModel orderModel) {
    	OrderReturnExportVo vo = new OrderReturnExportVo();

        if (orderModel != null) {

            vo.setOrderId(orderModel.getOrderId());
            vo.setHybrisReturnId(orderModel.getHybrisReturnId());
            if (orderModel.getReturnCreateDate() != null) {
                vo.setReturnCreateDate(DateUtils.formatDate(orderModel.getReturnCreateDate(), DateUtils.DATE_FORMAT_5));
            }
            
            vo.setReturnRequestStatus(orderModel.getReturnRequestStatus());
            
            if (orderModel.getReturnRequestUpdateDate() != null) {
                vo.setReturnRequestUpdateDate(DateUtils.formatDate(orderModel.getReturnRequestUpdateDate(), DateUtils.DATE_FORMAT_5));
            }
            vo.setCustomerId(orderModel.getCustomerId());
            vo.setCustomerType(orderModel.getCustomerType());
            vo.setCustomerName(orderModel.getCustomerName());
            vo.setCustomerPhoneNo(StringUtils.isNotEmpty(orderModel.getCustomerPhoneNo())?orderModel.getCustomerPhoneNo():"");
            vo.setCustomerMobileNo(StringUtils.isNotEmpty(orderModel.getCustomerMobileNo())?orderModel.getCustomerMobileNo():"");
            vo.setTenderType(orderModel.getTenderType());
            vo.setPaymentRef(orderModel.getPaymentRef());
            
            if (orderModel.getCollectDate() != null) {
                vo.setCollectDate(DateUtils.formatDate(orderModel.getCollectDate(), DateUtils.DATE_FORMAT_5));
            }
            vo.setCollectTimeSlot(orderModel.getCollectTimeSlot());
            vo.setContactName(orderModel.getContactName());
            vo.setContactPhoneNo(StringUtils.isNotEmpty(orderModel.getContactPhoneNo())?orderModel.getContactPhoneNo():"");
            vo.setContactMobileNo(StringUtils.isNotEmpty(orderModel.getContactMobileNo())?orderModel.getContactMobileNo():"");
            vo.setCollectDistrict(orderModel.getCollectDistrict());
            vo.setCollectAddress(orderModel.getCollectAddress());
            vo.setRemark(orderModel.getRemark());
            vo.setSpecialinstruction(orderModel.getSpecialinstruction());
                        
            vo.setSkuId(orderModel.getSkuId());
            vo.setBrand(orderModel.getBrand());
            vo.setBrandSec(orderModel.getBrandSec());
            vo.setProductName(orderModel.getProductName());
            vo.setProductNameSec(orderModel.getProductNameSec());
            vo.setSizeDesc(orderModel.getSizeDesc());
            vo.setOrderQty(orderModel.getOrderQty());
            vo.setReturnReqQty(orderModel.getReturnReqQty());
            vo.setExpectedQty(orderModel.getExpectedQty());
            vo.setActualCollectedQty(orderModel.getActualCollectedQty());
            vo.setWriteOffQty(orderModel.getWriteOffQty());
            vo.setSkuCollectRmk(orderModel.getSkuCollectRmk());

        }
        return vo;
    }


}
