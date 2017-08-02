package com.mall.b2bp.services.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.product.ProductExportModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.models.supplier.SupplierCategoryModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductInfoVo;
import com.mall.b2bp.vos.product.ProductParameterVo;
import com.mall.b2bp.vos.product.SubProductInfo;
import com.mall.b2bp.vos.user.UserVo;


public interface ProductInfoService {

    int insertSelective(ProductInfoModel record);

    int updateByPrimaryKeySelective(ProductInfoModel record);

    int updateByPrimaryKey(ProductInfoModel record);
    
    List<ProductInfoVo> selectProductList(String onlineUpdateInd,String supplier, String supplierProductCode, String productCode, String status, String deliveryMode, String shortDescEn, UserVo userVo);

    ProductInfoModel getProductInfoModelBySupplierProductCode(Map<String, Object> map);

    ProductInfoModel selectByPrimaryKey(BigDecimal id);

    boolean validateProduct(ProductInfoVo productInfoVo, ResponseData<ProductInfoVo> responseData) throws ServiceException;

    boolean insertProduct(ProductInfoVo productjson, ResponseData<ProductInfoVo> responseData, UserVo userVo) throws ServiceException;

    boolean updateProduct(ProductInfoVo productjson, ResponseData<ProductInfoVo> responseData, UserVo userVo) throws ServiceException;

    void deleteProductByProductId(String productId);

    List<SubProductInfo> getSubProductListByBaseProductId(String baseProductId);

    ProductInfoVo getProductInfoVo(BigDecimal productId, BigDecimal baseProductId) throws ServiceException;

    void updateProductStatus(String pId, String status, String pType) throws  ServiceException ;

    ProductInfoModel getProductInfoModelByProductCode(Map<String, Object> map);

    void deleteProductByPId(String pids);
    
    void uploadProductByPId(String pIds) throws IOException;

    int deleteDeliveryFeeProductBySu(String supplierCode);

    int editSettingSyncProduct(ProductInfoModel record);
    
    List<ProductExportModel> exportProductList(String onlineUpdateInd,String supplier,
			String supplierProductCode, String productCode, String status,String deliveryMode, String shortDescEn,UserVo userVo);
    
    void batchUploadProduct(ProductInfoVo productInfoVo,ResponseData<ProductInfoVo> responseData,UserVo userVo)throws ServiceException;
    
    List<ResponseData<ProductInfoVo>> generateProductForSu(Sheet sheet) throws  SystemException, IOException,ServiceException;
    List<ResponseData<ProductInfoVo>> generateProductForAp(Sheet sheet) throws  SystemException, IOException,ServiceException;

    SupplierCategoryModel selectBySupplierCategoryId(Map<String,Object> map);
    
    ProductInfoVo copyOnlineProductBarCodeToStaging(String productId,  String onlineProductId,ProductInfoVo onlineProductInfoVo);
    
    ProductInfoVo copyOnlineProductImagesToStaging(String productId,  String onlineProductId,ProductInfoVo onlineProductInfoVo);
    
    ProductInfoModel getSupplierProductInfoModelBySupplierId(String supplierCode);

    List<ProductInfoModel> getProductBySupplierCode(ProductParameterVo parameterVo);

}
