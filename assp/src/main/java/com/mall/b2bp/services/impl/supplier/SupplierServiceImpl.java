package com.mall.b2bp.services.impl.supplier;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.supplier.SupplierCategoryModelMapper;
import com.mall.b2bp.daos.supplier.SupplierModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.models.supplier.SupplierCategoryModel;
import com.mall.b2bp.models.supplier.SupplierModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.populators.supplier.SupplierPopulator;
import com.mall.b2bp.services.product.ProductInfoService;
import com.mall.b2bp.services.product.RetekService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.services.system.SessionService;
//import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.utils.ValidateUtils;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.supplier.SupplierUpdateVo;
import com.mall.b2bp.vos.supplier.SupplierVo;
import com.mall.b2bp.vos.supplier.UserSupplierVo;
//import com.mall.b2bp.vos.user.UserVo;

/**
 * Created by USER on 2016/3/10.
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
    private static final Logger LOG = LoggerFactory.getLogger(SupplierServiceImpl.class);
    private SupplierModelMapper supplierModelMapper;
    private SupplierCategoryModelMapper supplierCategoryModelMapper;
//    private static final String STAGING = "STAGING";
//    private static final String DRAFT = "DRAFT";
//    private static final String Y = "Y";

    public SupplierModelMapper getSupplierModelMapper() {
        return supplierModelMapper;
    }

    @Resource(name = "sessionService1")
	SessionService sessionService;


    @Autowired
    public void setSupplierModelMapper(SupplierModelMapper supplierModelMapper) {
        this.supplierModelMapper = supplierModelMapper;
    }

    @Resource(name = "productInfoService")
    private ProductInfoService productInfoService;

    public SupplierCategoryModelMapper getSupplierCategoryModelMapper() {
        return supplierCategoryModelMapper;
    }

    @Autowired
    public void setSupplierCategoryModelMapper(SupplierCategoryModelMapper supplierCategoryModelMapper) {
        this.supplierCategoryModelMapper = supplierCategoryModelMapper;
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return supplierModelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBySupplerIdNotExistsPd(String id) {
        return supplierModelMapper.deleteBySupplerIdNotExistsPd(id);
    }

    @Resource(name = "retekService")
    private RetekService retekService;

    @Override
    public int insert(SupplierModel record) throws ServiceException {
        try {
            return supplierModelMapper.insert(record);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int insertSelective(SupplierModel record) throws ServiceException {
        try {
            return supplierModelMapper.insertSelective(record);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public SupplierVo selectByPrimaryKey(String id) throws ServiceException {

        try {
            SupplierPopulator populator = new SupplierPopulator();

            SupplierModel model = supplierModelMapper.selectByPrimaryKey(id);
            if (model != null) {
                return populator.converModelToVo(model);
            }
            return null;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean validation(SupplierUpdateVo data, ResponseData responseData) {

        boolean validation = false;

        if (data != null) {
            // Delivery fee
            // Validation: Blank, or >= 0, with 1 decimal place
            String deliveryFee = data.getDeliveryFee();
            if (!ValidateUtils.judgeOneDecimal(deliveryFee)) {
                // ok
                validation = true;
                responseData.add("user_edit_update_setting_invalid_delivery_fee");
            }
            // Free delivery threshold
            // Validation: Blank, or >= 0, with 1 decimal place
            String freeDeliveryThreshold = data.getFreeDeliveryThreshold();
            if (!ValidateUtils.judgeOneDecimal(freeDeliveryThreshold)) {
                // ok
                validation = true;
                responseData.add("user_edit_update_setting_invalid_free_delivery_threshold");
            }

            String max = data.getMaxDeliveryDay();
            String min = data.getMinDeliveryDay();

            if (StringUtils.isNotEmpty(max) && StringUtils.isNotEmpty(min) && Integer.valueOf(min) > Integer.valueOf(max)) {

                validation = true;
                responseData.add("user_edit_update_setting_invalid_max_min_value");

            }

            // Mandatory and must be larger than 0.
            String warehouseDeliLeadTime = data.getWarehouseDeliLeadTime();
            if (StringUtils.isNotEmpty(warehouseDeliLeadTime) && Integer.valueOf(warehouseDeliLeadTime) < 0) {
                validation = true;
                responseData.add("user_edit_update_setting_invalid_lead_time_value");

            }
        } else {
            validation = true;
            responseData.add("system_error");
        }

        return validation;
    }


    @Override
    public boolean editSetting(SupplierUpdateVo data, ResponseData responseData) throws ServiceException {

        // boolean success = false;
        boolean validation = validation(data, responseData);
        if (validation) {
            responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);

            return false;
        }

        String userName = null;

        UserInfoModel userVo = sessionService.getCurrentUser();
        if (userVo != null) {
            userName = userVo.getUserCode();
        }

        /**
         * Delivery fee Yes Free delivery threshold Yes Supplier Direct Delivery
         * SLA Yes - Min. value - Max. value Consignment via warehouse delivery
         * lead time Yes
         */

        try {
            SupplierModel model = new SupplierModel();
            model.setId(data.getId());
            model.setLastUpdatedBy(userName);
            model.setLastUpdatedDate(new Date());
            String deliveryFee = data.getDeliveryFee();
            // Free delivery threshold
            // Validation: Blank, or >= 0, with 1 decimal place
            String freeDeliveryThreshold = data.getFreeDeliveryThreshold();

            String max = data.getMaxDeliveryDay();
            String min = data.getMinDeliveryDay();

            // Mandatory and must be larger than 0.
            String warehouseDeliLeadTime = data.getWarehouseDeliLeadTime();

            // Mandatory and default value 0.
            if (StringUtils.isEmpty(deliveryFee)) {
                //	model.setDeliveryFee(BigDecimal.ZERO);
                model.setDeliveryFeeStr("");
            } else {
                model.setDeliveryFee(new BigDecimal(deliveryFee));
            }

            // Mandatory and default value 0.
            if (StringUtils.isEmpty(freeDeliveryThreshold)) {
                //	model.setFreeDeliveryThreshold(BigDecimal.ZERO);
                model.setFreeDeliveryThresholdStr("");
            } else {
                model.setFreeDeliveryThreshold(new BigDecimal(freeDeliveryThreshold));
            }

            if (StringUtils.isNotEmpty(min)) {
                model.setMinDeliveryDay(new BigDecimal(min));
            } else {
                model.setMinDeliveryDay(null);
            }

            if (StringUtils.isNotEmpty(max)) {
                model.setMaxDeliveryDay(new BigDecimal(max));
            } else {
                model.setMaxDeliveryDay(null);
            }

            // Mandatory and must be larger than 0
            model.setWarehouseDeliLeadTime(new BigDecimal(warehouseDeliLeadTime));

            model.setShopNameEn(data.getShopNameEn());
            model.setShopNameTc(data.getShopNameTc());
            model.setShopNameSc(data.getShopNameSc());
            model.setShopDescEn(data.getShopDescEn());
            model.setShopDescTc(data.getShopDescTc());
            model.setShopDescSc(data.getShopDescSc());

            model.setUpdatedInd("Y");
//			model.setShopCategory(data.getShopCategory());
//			model.setImageFileName(data.getImageFileName());

            synchronizationProduct(data);

            supplierModelMapper.updateByPrimaryKeySelective(model);

            if (model != null) {
                // LOG.info(" update updateByPrimaryKeySelective..");
                supplierModelMapper.updateByPrimaryKeySelective(model);

//                if (model.getFreeDeliveryThreshold() != null && model.getDeliveryFee() != null) {
//
//
//                    ProductInfoModel productInfo = getFreeDeliveryProduct(model, userVo.getSupplierId());
//                    if (productInfo.getId() == null) {
//                        productInfoService.insertSelective(productInfo);
//                    } else {
//                        productInfoService.updateByPrimaryKeySelective(productInfo);
//                    }
//                }
            }


        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }

        return true;

    }

//    private ProductInfoModel getFreeDeliveryProduct(SupplierModel model, String supplierCode) throws ServiceException {
//
//        ProductInfoModel productInfo = productInfoService.getSupplierProductInfoModelBySupplierId(supplierCode);
//        try {
//        	UserInfoModel currentUser = sessionService.getCurrentUser();
//            String createdBy = "";
//            if (currentUser != null) {
//                createdBy = currentUser.getUserCode();
//            }
//            if (productInfo == null) {
//                productInfo = new ProductInfoModel();
//
//                productInfo.setSupplierCode(supplierCode);
//
//                productInfo.setStatus(DRAFT);
//                productInfo.setVersion(STAGING);
//                productInfo.setAutoApprovalInd(Y);
//                productInfo.setCreatedDate(new Date());
//                productInfo.setCreatedBy(createdBy);
//            }
//
//            if (model.getFreeDeliveryThreshold() != null) {
//                productInfo.setSupplierProductCode(StringUtil.decimalFormat(model.getFreeDeliveryThreshold()));
//            }
//
//            String pCode = retekService.generateSkuId(productInfo.getSupplierCode(),
//                    productInfo.getSupplierProductCode());
//
//            // LOG.info("start get pcod from retekService...ProductCode："+pCode);
//            productInfo.setUnitRetail(model.getDeliveryFee());
//
//            productInfo.setProductCode(pCode);
//            productInfo.setLastUpdatedDate(new Date());
//            productInfo.setDeliveryFeeProductInd("Y");
//            productInfo.setLastUpdatedBy(createdBy);
//
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//            throw new ServiceException(e.getMessage());
//        }
//        return productInfo;
//    }

//	private String decimalFormat(BigDecimal b) {
//
//		if (b == null)
//			return "";
//		try {
//			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
//
//			df.applyPattern("##.##");
//			return df.format(b.doubleValue());
//		} catch (Exception ex) {
//			return "";
//		}
//	}
//	
//	private String convert(String val) {
//		String str = "";
//		if (StringUtils.isNotEmpty(val)) {
//			str = val;
//		}
//		return str;
//	}
//
//	private String convert(BigDecimal val) {
//		String str = "";
//		if (val != null) {
//			str = val.toString();
//		}
//		return str;
//	}

    private void synchronizationProduct(SupplierUpdateVo data) throws ServiceException {

        try {

            String deliveryFee = StringUtil.convert(data.getDeliveryFee());
            String freeDeliveryThreshold = StringUtil.convert(data.getFreeDeliveryThreshold());
            String max = StringUtil.convert(data.getMaxDeliveryDay());
            String min = StringUtil.convert(data.getMinDeliveryDay());
            String via = StringUtil.convert(data.getWarehouseDeliLeadTime());
            SupplierVo oldVo = selectByPrimaryKey(data.getId());

            String userName = null;

            UserInfoModel userVo = sessionService.getCurrentUser();
            if (userVo != null) {
                userName = userVo.getUserCode();
            }

            if (oldVo != null) {
                String oldfreeDeliveryThreshold = StringUtil.convert(oldVo.getFreeDeliveryThreshold());
                String oldDeliveryFee = StringUtil.convert(oldVo.getDeliveryFee());
                String oldMax = StringUtil.convert(oldVo.getMaxDeliveryDay());
                String oldMin = StringUtil.convert(oldVo.getMinDeliveryDay());
                String oldVia = StringUtil.convert(oldVo.getWarehouseDeliLeadTime());

                if (max.equals(oldMax) && min.equals(oldMin)
                        && via.equals(oldVia)
                        && deliveryFee.equals(oldDeliveryFee)
                        && freeDeliveryThreshold.equals(oldfreeDeliveryThreshold)) {

                    return;
                }
            }

            ProductInfoModel model = new ProductInfoModel();
            model.setSupplierCode(data.getId());
            model.setLastUpdatedBy(userName);
            model.setLastUpdatedDate(new Date());

            if (StringUtils.isNotEmpty(max)) {
                model.setMaxDeliverDate(new BigDecimal(max));
            } else {
                model.setMaxDeliverDate(null);
            }

            if (StringUtils.isNotEmpty(min)) {
                model.setMinDeliverDate(new BigDecimal(min));
            } else {
                model.setMinDeliverDate(null);
            }

            model.setSupplierLeadTime(new BigDecimal(data.getWarehouseDeliLeadTime()));

            productInfoService.editSettingSyncProduct(model);

        } catch (Exception e) {

            LOG.error(" synchronizationProduct " + e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int updateByPrimaryKeySelective(SupplierModel record) {
        return supplierModelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SupplierModel record) throws ServiceException {
        try {
            return supplierModelMapper.updateByPrimaryKey(record);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<SupplierVo> searchAll() throws ServiceException {
        try {
            List<SupplierModel> supplierModelList = supplierModelMapper.searchAll();
            List<SupplierVo> supplierVoList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(supplierModelList)) {
                SupplierPopulator supplierPopulator = new SupplierPopulator();
                for (SupplierModel model : supplierModelList) {
                    supplierVoList.add(supplierPopulator.converModelToVo(model));
                }
            }

            Collections.sort(supplierVoList, new Comparator<SupplierVo>() {
                @Override
                public int compare(SupplierVo arg0, SupplierVo arg1) {
                    if (arg0 != null && arg1 != null && arg0.getName() != null && arg1.getName() != null)
                        return arg0.getName().compareTo(arg1.getName());
                    else
                        return -1;
                }
            });

            return supplierVoList;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public List<UserSupplierVo> getAllUserSupplier() {
        return supplierModelMapper.getAllUserSupplier();
    }

    @Override
    public UserSupplierVo getUserSupplierVoBySupplierId(String supplierId) {
        return supplierModelMapper.getUserSupplierVoBySupplierId(supplierId);
    }

    @Override
    public Map getFileItem(HttpServletRequest request)
            throws FileUploadException, UnsupportedEncodingException {
        return FileHandle.getFileItem(request);
    }

    @Override
    public List<SupplierModel> getSupplierByUpdatedInd() {
        return supplierModelMapper.getSupplierByUpdatedInd();
    }

    @Override
    public int saveProductCategory(String[] catgorys, BigDecimal suplierId) {
        if (catgorys==null){
            catgorys = new String[0];
        }
        String userName = null;
        UserInfoModel userVo = sessionService.getCurrentUser();
        if (userVo != null) {
            userName = userVo.getUserCode();
        }

        if (catgorys.length > 0) {

            List<SupplierCategoryModel> list = new ArrayList<>();
            SupplierCategoryModel model;

            for (String id : catgorys) {
                model = new SupplierCategoryModel();
                model.setId(supplierCategoryModelMapper.selectNextId());
                model.setSupplierId(suplierId);
                model.setCategoryId(id);
                model.setCreatedBy(userName);
                model.setCreatedDate(new Date());
                model.setLastUpdatedBy(userName);
                model.setLastUpdatedDate(new Date());
                list.add(model);
            }
            supplierCategoryModelMapper.deleteBySupplierId(suplierId.toString());
            int i = supplierCategoryModelMapper.insertCategoryBatch(list);
            return i;
        } else if (catgorys.length == 0) {
            supplierCategoryModelMapper.deleteBySupplierId(suplierId.toString());
        }


        return 0;
    }
}
