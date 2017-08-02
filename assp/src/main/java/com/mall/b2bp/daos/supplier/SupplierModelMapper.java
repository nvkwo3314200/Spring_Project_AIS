package com.mall.b2bp.daos.supplier;



import java.util.List;

import com.mall.b2bp.models.supplier.SupplierModel;
import com.mall.b2bp.vos.supplier.UserSupplierVo;

public interface SupplierModelMapper {
    int deleteByPrimaryKey(String id);
    
    int deleteBySupplerIdNotExistsPd(String id);
    
    int insert(SupplierModel record);

    int insertSelective(SupplierModel record);

    SupplierModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SupplierModel record);

    int updateByPrimaryKey(SupplierModel record);
    
    List<SupplierModel> searchAll();
    
    List<SupplierModel> getAllSupplierByName(String name);
    
    List<UserSupplierVo> getAllUserSupplier();
    
    UserSupplierVo getUserSupplierVoBySupplierId(String supplierId);
    List<SupplierModel> getSupplierByUpdatedInd();
    
    
   
}