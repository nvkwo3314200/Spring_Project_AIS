package com.mall.b2bp.populators.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.oxm.order.OrderBean;
import com.mall.b2bp.utils.ConstantUtil;

/**
 * Created by lwh on 2016/3/21
 */
public class OrderBeanPopulator {

	private final static String LONG_DATEFORMAT="yyyyMMddHHmmss";
	private final static String SHORT_DATEFORMAT="yyyyMMdd";
    private static final Logger LOG = LoggerFactory.getLogger(OrderBeanPopulator.class);
    public OrderModel converBeanlToModel(OrderModel target,OrderBean source) {
    	
    	SimpleDateFormat sdf=new SimpleDateFormat(LONG_DATEFORMAT);
    	SimpleDateFormat shortSdf=new SimpleDateFormat(SHORT_DATEFORMAT);
        if (source != null) {

            target.setPickStore(source.getPickStore());
            target.setPickOrderId(source.getPickOrderId());
            target.setHybrisOrderId(source.getHybrisOrderId());
            
            if(StringUtils.isNotEmpty(source.getOrderDatetime())){
            	try {
					target.setOrderDatetime(sdf.parse(source.getOrderDatetime()));
				} catch (ParseException e) {
                    LOG.error(e.getMessage(), e);
				}
            }
            
            if(StringUtils.isNotEmpty(source.getLastUpdatedDate())){
             	try {
    					target.setLastUpdatedDate(sdf.parse(source.getLastUpdatedDate()));
    				} catch (ParseException e) {
                    LOG.error(e.getMessage(), e);
    				}
            }
            
            target.setRemark(source.getRemark());
            target.setSpecialInstruction(source.getSpecialInstruction());
            target.setPickType(source.getPickType());
            target.setCustomerId(source.getCustomerId());
            target.setCustomerType(source.getCustomerType());
            target.setCustomerName(source.getCustomerName());
            target.setCustomerPhoneNo(source.getCustomerPhoneNo());
            target.setCustomerMobileNo(source.getCustomerMobileNo());
            target.setTenderType(source.getTenderType());
            target.setTotalAmount(source.getTotalAmount());
            target.setPaymentRef(source.getPaymentRef());
            target.setSurcharge(source.getSurcharge());
            target.setDeliveryFee(source.getDeliveryFee());
//            target.setSupplierId(source.getSupplierId());
            
            if(StringUtils.isNotEmpty(source.getDeliveryDate())){
             	try {
                    target.setDeliveryDate(shortSdf.parse(source.getDeliveryDate()));
    				} catch (ParseException e) {
                    LOG.error(e.getMessage(), e);
    				}
            }
            target.setDeliveryTimeslot(source.getDeliveryTimeslot());
            target.setShipDistrict(source.getShipDistrict());
            target.setReceiverName(source.getReceiverName());
            target.setDeliveryAddress(source.getDeliveryAddress());
            target.setReceiverMobileNo(source.getReceiverMobileNo());
            target.setReceiverPhoneNo(source.getReceiverPhoneNo());
            target.setShowPrice(source.getShowPrice());
            target.setInvoiceFilename(source.getInvoiceFilename());
            
            target.setExternalOrderId(source.getExternalOrderId());
            target.setExternalLogisticCode(source.getExternalLogisticCode());
            target.setDeliveryPostalCode(source.getDeliveryPostalCode());
            target.setDcReferenceCode(source.getDcReferenceCode());
            target.setDcAddress(source.getDcAddress());
            
            if("Consignment".equals(source.getOrderType()))
            {
            	target.setOrderType(ConstantUtil.CONSIGNMENT);
            
            }
            if("Consignmentviawarehouse".equals(source.getOrderType()))
            {
            	target.setOrderType(ConstantUtil.CONSIGNMENT_VIA_WAREHOUSE);
            	
            }
            if("SupplierDirectDelivery".equals(source.getOrderType()))
            {
            	target.setOrderType(ConstantUtil.SUPPLIER_DIRECT_DELIVERY);
            }
            


        }
        return target;
    }

}
