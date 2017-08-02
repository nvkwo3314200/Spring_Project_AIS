package com.mall.b2bp.services.orderFromDB;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.order.CancelRequest;
import com.mall.b2bp.vos.order.OrderCancelReponseVo;
import com.mall.b2bp.vos.order.OrderExportVo;
import com.mall.b2bp.vos.order.OrderViewVo;
import com.mall.b2bp.vos.order.OrderVo;

/**
 * Created by USER on 2016/3/10.
 */
public interface OrderService {

	
	public Map<String , Object> selectByPrimaryKey(String orderId) throws ServiceException ;

    int insertSelective(OrderModel record)throws ServiceException;

    OrderVo selectByPrimaryKey(BigDecimal id) throws ServiceException;

    int updateByPrimaryKeySelective(OrderModel record)throws ServiceException;

    List<OrderVo> viewOrderList(final OrderViewVo orderViewVo) throws ServiceException;

    boolean updateBatchForSupplier(String waitForUpdateStatus, List<OrderVo> orderVoList,
                                          final ResponseData responseData) throws ServiceException;


    boolean updateSingleForSupplier(String waitForUpdateStatus, final OrderVo orderVo,
                                      ResponseData responseData) throws ServiceException;
    
    List<OrderModel> selectByHybrisOrderId(String id)throws ServiceException;

    
    List<OrderModel> getOrderListByInvoiceInd()throws ServiceException;



    List<OrderVo> selectByOrderHome()throws ServiceException;
    List<OrderExportVo> exportOrderList(final OrderViewVo orderViewVo) throws ServiceException;
    OrderModel getOrderConsignmentID(String pickOrderId,String supplierId) ;
    OrderModel getOrderByidAndskuId(String hybrisOrderId,String skuId) ;
    
    boolean  deleteOutOfDateOrderData() throws ServiceException;
    
    OrderCancelReponseVo getAllCancleResponses(List<CancelRequest> cancelRequests);
    OrderModel getOrderByPickOrderId(String pickOrderId);

}
