package com.mall.b2bp.services.order;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderReturnModel;
import com.mall.b2bp.models.order.OrderReturnReceivedModel;
import com.mall.b2bp.oxm.order.OrderReturnBeans;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.email.NotificationEmailVo;
import com.mall.b2bp.vos.order.OrderReturnExportVo;
import com.mall.b2bp.vos.order.OrderReturnVo;
import com.mall.b2bp.vos.order.OrderViewVo;


public interface OrderReturnService {

    int insertSelective(OrderReturnModel record);

    OrderReturnVo selectReturnAndReturnEntryByPrimaryKey(BigDecimal id) throws ServiceException;
    OrderReturnModel selectByReturnId(String hybrisReturnId);

    int updateByPrimaryKeySelective(OrderReturnModel record);
    public List<NotificationEmailVo> updateReturnOrders(OrderReturnBeans orderBeans)throws ServiceException;
    
    List< OrderReturnVo>  selectByOrderId(String orderId) throws ServiceException;
    
     boolean updateConfirmOrderReturn(OrderReturnVo orderReturnVo,ResponseData responseData) throws ServiceException;
     
     List<OrderReturnExportVo> exportOrderReturnList(final OrderViewVo orderViewVo) throws ServiceException;
     
     List< OrderReturnReceivedModel> getReturnReceived();

}
