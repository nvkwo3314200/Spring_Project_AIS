package com.mall.b2bp.services.orderFromDB;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.vos.order.OrderAuditVo;

/**
 * Created by USER on 2016/4/7.
 */
public interface OrderAuditService {

    List<OrderAuditVo> viewHistory(String orderId, String userId,
                                     String action,
                                     String actionTimeFr,String actionTimeTo) throws ServiceException;

    int deleteByOrderId(String orderId);
}
