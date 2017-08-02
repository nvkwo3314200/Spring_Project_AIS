package com.mall.b2bp.services.order;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.order.CancelRequest;
import com.mall.b2bp.vos.order.OrderCancelReponseVo;
import com.mall.b2bp.vos.order.OrderEntrySerialNoListVo;
import com.mall.b2bp.vos.order.OrderEntryVo;
import com.mall.b2bp.vos.order.OrderExportVo;
import com.mall.b2bp.vos.order.OrderViewVo;
import com.mall.b2bp.vos.order.OrderVo;
import com.mall.b2bp.vos.order.SupplierCollectionData;

/**
 * Created by USER on 2016/3/10.
 */
public interface OrderService {

    int insertSelective(OrderModel record)throws ServiceException;

    OrderVo selectByPrimaryKey(String id) throws ServiceException;
//    OrderVo selectShipDetailsById(String id) throws ServiceException;

    int updateByPrimaryKeySelective(OrderModel record)throws ServiceException;

    List<OrderVo> viewOrderList(final OrderViewVo orderViewVo) throws ServiceException;

    boolean updateBatchForSupplier(String waitForUpdateStatus, List<OrderVo> orderVoList, String trackId,final ResponseData responseData) throws ServiceException;

    boolean updateSingleForSupplier(String waitForUpdateStatus, final OrderVo orderVo, ResponseData responseData) throws ServiceException;
    
    boolean updateOrderEntrySerialList(final OrderEntrySerialNoListVo orderEntrySerialsData, ResponseData responseData) throws ServiceException;
    
    List<OrderModel> selectByHybrisOrderId(String id)throws ServiceException;
    
    List<OrderModel> getOrderListByInvoiceInd()throws ServiceException;

    List<OrderVo> selectByOrderHome()throws ServiceException;
    
    List<OrderExportVo> exportOrderList(final OrderViewVo orderViewVo) throws ServiceException;
    
    OrderModel getOrderConsignmentID(String pickOrderId,String supplierId) ;
    
    OrderModel getOrderByidAndskuId(String hybrisOrderId,String skuId) ;
    
    boolean  deleteOutOfDateOrderData() throws ServiceException;
    
    OrderCancelReponseVo getAllCancleResponses(List<CancelRequest> cancelRequests);
    
    OrderModel getOrderByPickOrderId(String pickOrderId);
    
    List<SupplierCollectionData> getSupplierCollectionByOrderCode(final String orderCode) throws ServiceException;
    
    boolean updateSupplierCollection(List<SupplierCollectionData>  orderShipDetails) throws ServiceException;
    
    public List<OrderEntryVo>  selectOrderEngtryList(String orderId) throws ServiceException;

}
