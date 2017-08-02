package com.mall.b2bp.populators.supplier;


import com.mall.b2bp.models.supplier.SupplierModel;
import com.mall.b2bp.vos.supplier.SupplierVo;

/**
 * Created by USER on 2016/3/10.
 */
public class SupplierPopulator {


    public SupplierVo converModelToVo(SupplierModel supplierModel){
        SupplierVo vo = new SupplierVo();

        if(supplierModel!=null){

            vo.setId(supplierModel.getId());
            vo.setName(supplierModel.getName());
            vo.setDeliveryFee(supplierModel.getDeliveryFee());
            vo.setFreeDeliveryThreshold(supplierModel.getFreeDeliveryThreshold());
            vo.setMinDeliveryDay(supplierModel.getMinDeliveryDay());
            vo.setMaxDeliveryDay(supplierModel.getMaxDeliveryDay());
            vo.setConsignmentVia(supplierModel.getConsignmentVia());
            vo.setWarehouseDeliLeadTime(supplierModel.getWarehouseDeliLeadTime());

            vo.setLastUpdatedDate(supplierModel.getLastUpdatedDate());
            vo.setLastUpdatedBy(supplierModel.getLastUpdatedBy());

            vo.setCreatedBy(supplierModel.getCreatedBy());
            vo.setCreatedDate(supplierModel.getCreatedDate());
            vo.setShopDescEn(supplierModel.getShopDescEn());
            vo.setShopDescTc(supplierModel.getShopDescTc());
            vo.setShopDescSc(supplierModel.getShopDescSc());
            vo.setShopNameEn(supplierModel.getShopNameEn());
            vo.setShopNameTc(supplierModel.getShopNameTc());
            vo.setShopNameSc(supplierModel.getShopNameSc());
//            vo.setShopCategory(supplierModel.getShopCategory());
//            vo.setImageFileName(supplierModel.getImageFileName());
            


        }
        return vo;
    }

}
