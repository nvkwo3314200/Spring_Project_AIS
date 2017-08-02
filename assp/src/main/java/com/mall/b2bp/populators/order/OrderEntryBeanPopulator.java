package com.mall.b2bp.populators.order;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderEntryModel;
import com.mall.b2bp.oxm.order.OrderEntryBean;

/**
 * Created by USER on 2016/3/10.
 */
public class OrderEntryBeanPopulator {
	private static final Logger LOG = LoggerFactory
			.getLogger(OrderEntryBeanPopulator.class);

    public OrderEntryModel converBeanlToMode(OrderEntryModel target,OrderEntryBean source) throws ServiceException{
        if(source!=null){
            target.setBrand(source.getBrand());
            target.setBrandSec(source.getBrandSec());
            target.setSkuId(source.getSkuId());
            target.setSkuAmount(source.getSkuAmount());
            target.setSkuType(source.getSkuType());
            target.setSeqNum(source.getSeqNum());
            target.setQty(source.getQty());
            target.setSizeDesc(source.getSizeDesc());
            target.setUnitPrice(source.getUnitPrice());
            target.setProductName(source.getProductName());
            target.setProductNameSec(source.getProductNameSec());
            target.setSupplierId(source.getSupplierId());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            try {
				sdf.parse(source.getNotBeforeDate());
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
				throw new ServiceException ("Not Before Date format error.");
			}
            try {
				sdf.parse(source.getNotAfterDate());
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
				throw new ServiceException ("Not After Date format error.");
			}
            target.setNotBeforeDate(source.getNotBeforeDate());
            target.setNotAfterDate(source.getNotAfterDate());

        }
        return target;
    }

}
