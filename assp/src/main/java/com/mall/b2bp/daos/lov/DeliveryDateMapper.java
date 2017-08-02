package com.mall.b2bp.daos.lov;

import java.util.List;

import com.mall.b2bp.models.lov.DeliveryDateModel;

public interface DeliveryDateMapper {
    int insert(DeliveryDateModel deliveryDateModel);
    int insertSelective(DeliveryDateModel deliveryDateModel);
    
    int deleteByPrimaryKey(String id);
    
    DeliveryDateModel selectBySegment(String id);
    
    int updateByPrimaryKeySelective(DeliveryDateModel record);
    int updateByPrimaryKey(DeliveryDateModel record);

    List<DeliveryDateModel> searchAll();
    
    int searchCountByPrimaryKey(String segment);
/*
    List<UserSupplierVo> getAllUserSupplier();
    UserSupplierVo getUserSupplierVoBySupplierId(String supplierId);
    List<SupplierModel> getSupplierByUpdatedInd();
    */
    
}