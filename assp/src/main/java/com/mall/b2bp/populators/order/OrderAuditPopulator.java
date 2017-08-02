package com.mall.b2bp.populators.order;

import com.mall.b2bp.models.order.OrderAuditModel;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderAuditVo;

/**
 * Created by USER on 2016/4/7.
 */
public class OrderAuditPopulator {

    public OrderAuditVo converModelToVo(OrderAuditModel source){

        OrderAuditVo vo =new OrderAuditVo();
        vo.setId(source.getId());
        vo.setAccountId(source.getAccountId());
        vo.setUserName(source.getUserName());
        vo.setUserId(source.getUserId());
        vo.setOrderId(source.getOrderId().toString());

        vo.setAction(source.getAction());
        vo.setActionTime(source.getActionTime());
        vo.setCreatedBy(source.getCreatedBy());
        vo.setCreatedDate(source.getCreatedDate());
        vo.setLastUpdatedBy(source.getLastUpdatedBy());
        vo.setLastUpdatedDate(source.getLastUpdatedDate());


        if (source.getActionTime() != null) {
            vo.setActionTimeStr(DateUtils.formatDate(source.getActionTime(), DateUtils.DEFAULT_DATE_FORMAT));

        }

        return vo;


    }
}
