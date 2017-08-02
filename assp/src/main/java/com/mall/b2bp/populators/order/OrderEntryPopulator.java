package com.mall.b2bp.populators.order;

import com.mall.b2bp.models.order.OrderEntryModel;
import com.mall.b2bp.vos.order.OrderEntryVo;

/**
 * Created by USER on 2016/3/10.
 */
public class OrderEntryPopulator {


    public OrderEntryVo converModelToVo(OrderEntryModel orderModel){
        OrderEntryVo vo = new OrderEntryVo();

        if(orderModel!=null){

            vo.setId(orderModel.getId());
            vo.setOrderId(orderModel.getOrderId());
            vo.setBrand(orderModel.getBrand());
            vo.setBrandSec(orderModel.getBrandSec());
            vo.setSkuId(orderModel.getSkuId());
            vo.setSkuAmount(orderModel.getSkuAmount());
            vo.setSkuType(orderModel.getSkuType());
            vo.setSeqNum(orderModel.getSeqNum());
            vo.setQty(orderModel.getQty());
            vo.setSizeDesc(orderModel.getSizeDesc());
            vo.setUnitPrice(orderModel.getUnitPrice());
            vo.setPickDate(orderModel.getPickDate());
            vo.setPickedQty(orderModel.getPickedQty());
            vo.setBoxNum(orderModel.getBoxNum());
            vo.setShipDate(orderModel.getShipDate());
            vo.setDeliverySuccess(orderModel.getDeliverySuccess());
            vo.setCreatedBy(orderModel.getCreatedBy());
            vo.setCreatedDate(orderModel.getCreatedDate());
            vo.setDeliveryDate(orderModel.getDeliveryDate());
            vo.setDeliveryQty(orderModel.getDeliveryQty());

            vo.setSupplierName(orderModel.getSupplierName());
            vo.setSupplierProductCode(orderModel.getSupplierProductCode());
            
            String pn = orderModel.getProductName();
            if(pn!=null){
            	pn = pn.replaceAll("null", "");
            }
            vo.setProductName(pn);
            vo.setProductNameSec(orderModel.getProductNameSec());
            vo.setSupplierId(orderModel.getSupplierId());
            
            vo.setReturnTotal(orderModel.getReturnTotal());
            

        }
        return vo;
    }

}
