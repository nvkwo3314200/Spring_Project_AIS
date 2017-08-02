package com.mall.b2bp.populators.order;

import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderVo;

/**
 * Created by USER on 2016/3/10.
 */
public class OrderPopulator {


    public OrderVo converModelToVo(OrderModel orderModel) {
        OrderVo vo = new OrderVo();

        if (orderModel != null) {

            vo.setId(orderModel.getId());

            vo.setConsignmentStatus(orderModel.getConsignmentStatus());
            vo.setHybrisOrderId(orderModel.getHybrisOrderId());
            vo.setOrderDatetime(orderModel.getOrderDatetime());
            vo.setPickOrderId(orderModel.getPickOrderId());
            vo.setPickType(orderModel.getPickType());
            vo.setPickStore(orderModel.getPickStore());
            vo.setSpecialInstruction(orderModel.getSpecialInstruction());
            vo.setSupplierId(orderModel.getSupplierId());
            vo.setSupplierName(orderModel.getSupplierName());
            vo.setCustomerId(orderModel.getCustomerId());
            vo.setCustomerType(orderModel.getCustomerType());
            vo.setCustomerName(orderModel.getCustomerName());
            vo.setInvoiceReadyInd(orderModel.getInvoiceReadyInd());
            vo.setInvoiceFilename(orderModel.getInvoiceFilename());
            vo.setTotalAmount(orderModel.getTotalAmount());
//            vo.setCollectMethod(orderModel.getCollectMethod());
            setCollectMethod(orderModel, vo);

            vo.setStatus(orderModel.getStatus());
            vo.setOrderStatus(orderModel.getOrderStatus());
            setStatus(orderModel, vo);
            
            vo.setConsignmentStatus(orderModel.getConsignmentStatus());
            
            vo.setOutstandingReturnRequest( orderModel.getOutstandingReturnRequest());
            vo.setTotalRetrnRequest(orderModel.getTotalRetrnRequest() );
            vo.setSizeWaitReturn(orderModel.getSizeWaitReturn() );


            vo.setConsignmentShippedDate(orderModel.getConsignmentShippedDate());
            if (orderModel.getConsignmentShippedDate() != null) {
                vo.setConsignmentShippedDateStr(DateUtils.formatDate(orderModel.getConsignmentShippedDate(), DateUtils.DATE_FORMAT_7));
                String v = DateUtils.formatDate(orderModel.getConsignmentShippedDate(), DateUtils.DATE_FORMATE_YYYYMMDD);
                if (v != null)
                    vo.setShippedDateLong(Long.valueOf(v));
            }

            //deliveryDate
            vo.setDeliveryDate(orderModel.getDeliveryDate());
            if (orderModel.getDeliveryDate() != null) {
                vo.setDeliveryDateStr(DateUtils.formatDate(orderModel.getDeliveryDate(), DateUtils.DATE_FORMAT_7));
                String v = DateUtils.formatDate(orderModel.getDeliveryDate(), DateUtils.DATE_FORMATE_YYYYMMDD);
                if (v != null)
                    vo.setDeliveryDateLong(Long.valueOf(v));
            }
            
            //collectDate
            vo.setCollectDate(orderModel.getCollectDate());
            if (orderModel.getCollectDate() != null) {
            	vo.setCollectDateStr(DateUtils.formatDate(orderModel.getCollectDate(), DateUtils.DATE_FORMAT_9));
            	String v = DateUtils.formatDate(orderModel.getCollectDate(), DateUtils.DATE_FORMATE_YYYYMMDD);
            	if (v != null)
            		vo.setCollectDateLong(Long.valueOf(v));
            }
            
            
            vo.setOrderDatetime(orderModel.getOrderDatetime());

            if (orderModel.getOrderDatetime() != null) {
                vo.setOrderDatetimeStr(DateUtils.formatDate(orderModel.getOrderDatetime(), DateUtils.DATE_FORMAT_7));
                String v = DateUtils.formatDate(orderModel.getOrderDatetime(), DateUtils.DATE_FORMATE_YYYYMMDD);
                if (v != null)
                    vo.setOrderDatetimeLong(Long.valueOf(v));
            }

            vo.setCustomerName(orderModel.getCustomerName());
            vo.setCustomerPhoneNo(orderModel.getCustomerPhoneNo());
            vo.setCustomerMobileNo(orderModel.getCustomerMobileNo());
            vo.setReceiverName(orderModel.getReceiverName());
            vo.setDeliveryAddress(orderModel.getDeliveryAddress());
            vo.setReceiverMobileNo(orderModel.getReceiverMobileNo());
            vo.setReceiverPhoneNo(orderModel.getReceiverPhoneNo());
            vo.setDeliveryFee(orderModel.getDeliveryFee());

            vo.setPickDate(orderModel.getPickDate());

            if (orderModel.getPickDate() != null) {
                vo.setPickDateStr(DateUtils.formatDate(orderModel.getPickDate(), DateUtils.DATE_FORMAT_7));
                String v = DateUtils.formatDate(orderModel.getPickDate(), DateUtils.DATE_FORMATE_YYYYMMDD);
                if (v != null)
                    vo.setPickDateLong(Long.valueOf(v));
            }
            vo.setBoxNum(orderModel.getBoxNum());
            vo.setTrackId(orderModel.getTrackId());
            vo.setDeliverySuccess(orderModel.getDeliverySuccess());

            vo.setCreatedDate(orderModel.getCreatedDate());
            vo.setCreatedBy(orderModel.getCreatedBy());
            vo.setLastUpdatedDate(orderModel.getLastUpdatedDate());
            vo.setLastUpdatedBy(orderModel.getLastUpdatedBy());
            vo.setShowPrice(orderModel.getShowPrice());
            vo.setTenderType(orderModel.getTenderType());
            vo.setOrderType(orderModel.getOrderType());
            if("CONSIGNMENT_VIA_WAREHOUSE".equals(orderModel.getOrderType()) || "CONSIGNMENT".equals(orderModel.getOrderType()))
            	vo.setOrderTypeDesc("Consignment");
            else if("SUPPLIER_DIRECT_DELIVERY".equals(orderModel.getOrderType()) )
            	vo.setOrderTypeDesc("Supplier Direct Delivery ");
            
            vo.setReturnRequest(orderModel.getReturnRequest());

            vo.setPickedInd(orderModel.getPickedInd());
            vo.setShippedInd(orderModel.getShippedInd());
            vo.setDeliveryInd(orderModel.getDeliveryInd());
            
            vo.setOrigOrderId(orderModel.getOrigOrderId());
            vo.setReplaceOrderInd(orderModel.getReplaceOrderInd());
            
            if("Y".equals(orderModel.getReplaceOrderInd())){
            	String[] ar = orderModel.getHybrisOrderId().split("-");
            	if(ar !=null && ar .length>=2){
            		vo.setOrigHybrisOrderId(ar[0]);
            	}
            }
            
            vo.setDeliveryFlag(orderModel.getDeliveryFlag());
            
        }
        
        
        return vo;
    }

    private void setStatus(OrderModel orderModel, OrderVo vo) {

        if (StringUtils.isNotEmpty(orderModel.getStatus())) {
            if (ConstantUtil.NEW.equals(orderModel.getStatus()))
                vo.setStatusDesc("New");
            else if (ConstantUtil.PICKED.equals(orderModel.getStatus()))
                vo.setStatusDesc("Picked ");
            else if (ConstantUtil.SHIPPED.equals(orderModel.getStatus()))
                vo.setStatusDesc("Shipped");
            else if (ConstantUtil.CANCELLED.equals(orderModel.getStatus()))
                vo.setStatusDesc("Cancelled");
            else if (ConstantUtil.DELIVERY_CONFIRMED.equals(orderModel.getStatus()))
                vo.setStatusDesc("Delivery confirmed");
            else if (ConstantUtil.DELIVERY_FAILURE.equals(orderModel.getStatus()))
                vo.setStatusDesc("Delivery failure");
            else if ("DELIVERY".equals(orderModel.getStatus()))
            	vo.setStatusDesc("Delivery");
            else if (ConstantUtil.COMPLETED.equals(orderModel.getStatus()))
                vo.setStatusDesc("Completed");
            else if (ConstantUtil.UPLOADED.equals(orderModel.getStatus()))
            	vo.setStatusDesc("Uploaded");
            else 
            	vo.setStatusDesc(orderModel.getStatus());


        }
    }
    private void setCollectMethod(OrderModel orderModel, OrderVo vo) {
    	
    	if (StringUtils.isNotEmpty(orderModel.getCollectMethod())) {
    		if (StringUtils.equals(orderModel.getCollectMethod(), "C"))
    			vo.setCollectMethod("Collection Point");
    		else if (StringUtils.equals(orderModel.getCollectMethod(), "S"))
    			vo.setCollectMethod("Shop Collection");
    		else if (StringUtils.equals(orderModel.getCollectMethod(), "D"))
    			vo.setCollectMethod("Customer Address");
    		}else{
    			
    			
    			if (StringUtils.isNotEmpty(orderModel.getDeliveryFlag())) {
    	    		if (StringUtils.equals(orderModel.getCollectMethod(), "C"))
    	    			vo.setCollectMethod("Collection Point");
    	    		else if (StringUtils.equals(orderModel.getDeliveryFlag(), "S"))
    	    			vo.setCollectMethod("Shop Collection");
    	    		else if (StringUtils.equals(orderModel.getDeliveryFlag(), "D"))
    	    			vo.setCollectMethod("Customer Address");
    	    	}else{
    	    		vo.setCollectMethod("Customer Address");
    	    	}
    			
    		}
    	
    	
    		
    }

}
