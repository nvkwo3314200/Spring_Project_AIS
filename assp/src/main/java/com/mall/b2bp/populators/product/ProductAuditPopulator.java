package com.mall.b2bp.populators.product;

import com.mall.b2bp.models.product.ProductAuditModel;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.product.ProductAuditVo;

/**
 * Created by USER on 2016/4/7.
 */
public class ProductAuditPopulator {

    public ProductAuditVo converModelToVo(ProductAuditModel source){

        ProductAuditVo vo =new ProductAuditVo();
        vo.setId(source.getId());
        vo.setAccountId(source.getAccountId());
        vo.setUserName(source.getUserName());
        vo.setUserId(source.getUserId());
        vo.setProductId(source.getProductId());

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
