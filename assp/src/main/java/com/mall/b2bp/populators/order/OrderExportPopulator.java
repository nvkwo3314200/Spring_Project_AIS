package com.mall.b2bp.populators.order;

import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.models.order.OrderExportModel;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderExportVo;

/**
 * Created by USER on 2016/3/10.
 */
public class OrderExportPopulator {


    public OrderExportVo converModelToVo(OrderExportModel orderModel) {
    	OrderExportVo vo = new OrderExportVo();

        if (orderModel != null) {

            vo.setHybrisOrderId(orderModel.getHybrisOrderId());
            vo.setPickOrderId(orderModel.getPickOrderId());
            vo.setOrderType(orderModel.getOrderType());
            
            if("CONSIGNMENT_VIA_WAREHOUSE".equals(orderModel.getOrderType()) || "CONSIGNMENT".equals(orderModel.getOrderType()))
            	vo.setOrderTypeDesc("Consignment");
            else if("SUPPLIER_DIRECT_DELIVERY".equals(orderModel.getOrderType()) )
            	vo.setOrderTypeDesc("Supplier Direct Delivery ");
            
            setStatus(orderModel, vo);
            
            vo.setReceiverName(orderModel.getReceiverName());
            vo.setDeliveryAddress(orderModel.getDeliveryAddress());
            vo.setReceiverMobileNo(StringUtils.isNotEmpty(orderModel.getReceiverMobileNo())?orderModel.getReceiverMobileNo():"");
            vo.setReceiverPhoneNo(StringUtils.isNotEmpty(orderModel.getReceiverPhoneNo())?orderModel.getReceiverPhoneNo():"");
            
            vo.setCustomerName(orderModel.getCustomerName());
            vo.setCustomerPhoneNo(StringUtils.isNotEmpty(orderModel.getCustomerPhoneNo())?orderModel.getCustomerPhoneNo():"");
            vo.setCustomerMobileNo(StringUtils.isNotEmpty(orderModel.getCustomerMobileNo())?orderModel.getCustomerMobileNo():"");

            if (orderModel.getConsignmentShippedDate() != null) {
                vo.setConsignmentShippedDate(DateUtils.formatDate(orderModel.getConsignmentShippedDate(), DateUtils.DATE_FORMAT_5));
            }
            
            if (orderModel.getDeliveryDate() != null) {
                vo.setDeliveryDate(DateUtils.formatDate(orderModel.getDeliveryDate(), DateUtils.DATE_FORMAT_5));
            }

            if (orderModel.getOrderDatetime() != null) {
                vo.setOrderDatetime(DateUtils.formatDate(orderModel.getOrderDatetime(), DateUtils.DATE_FORMAT_5));
            }
            
            vo.setSkuId(orderModel.getSkuId());
            vo.setSupplierProductCode(orderModel.getSupplierProductCode());
            vo.setBrandSec(orderModel.getBrandSec());
            vo.setProductName(orderModel.getProductName());
            vo.setSizeDesc(orderModel.getSizeDesc());
            vo.setQty(orderModel.getQty());
            vo.setPickedQty(orderModel.getPickedQty());
            vo.setRefundedQty(orderModel.getRefundedQty());
            vo.setShippedQty(orderModel.getShippedQty());
            vo.setUnitPrice(orderModel.getUnitPrice());

        }
        return vo;
    }

    private void setStatus(OrderExportModel orderModel, OrderExportVo vo) {

        if (StringUtils.isNotEmpty(orderModel.getStatus())) {
            if (ConstantUtil.NEW.equals(orderModel.getStatus()))
                vo.setStatus("New");
            else if (ConstantUtil.PICKED.equals(orderModel.getStatus()))
                vo.setStatus("Picked ");
            else if (ConstantUtil.SHIPPED.equals(orderModel.getStatus()))
                vo.setStatus("Shipped");
            else if (ConstantUtil.CANCELLED.equals(orderModel.getStatus()))
                vo.setStatus("cancelled");
            else if (ConstantUtil.DELIVERY_CONFIRMED.equals(orderModel.getStatus()))
                vo.setStatus("Delivery confirmed");
            else if (ConstantUtil.DELIVERY_FAILURE.equals(orderModel.getStatus()))
                vo.setStatus("Delivery failure");


        }
    }

}
