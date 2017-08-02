package com.mall.b2bp.services.product;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.vos.product.ProductAuditVo;

import java.util.List;

/**
 * Created by USER on 2016/4/7.
 */
public interface ProductAuditService {

    List<ProductAuditVo> viewHistory(String productId, String userId,
                                     String action,
                                     String actionTimeFr,String actionTimeTo) throws ServiceException;

//    List<ProductAuditVo> getAll() throws ServiceException;
    int deleteByProductId(String productId);
    
    int deleteBySupplierId(String supplierCode);
}
