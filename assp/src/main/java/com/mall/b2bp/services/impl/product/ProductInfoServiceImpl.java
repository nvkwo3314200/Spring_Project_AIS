package com.mall.b2bp.services.impl.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mall.b2bp.daos.product.ProductBarcodeModelMapper;
import com.mall.b2bp.daos.product.ProductImagesModelMapper;
import com.mall.b2bp.daos.product.ProductInfoModelMapper;
import com.mall.b2bp.daos.supplier.SupplierCategoryModelMapper;
import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.models.lov.LovModel;
import com.mall.b2bp.models.product.ProductBarCodeUpFieldModel;
import com.mall.b2bp.models.product.ProductBarcodeModel;
import com.mall.b2bp.models.product.ProductExportModel;
import com.mall.b2bp.models.product.ProductImagesModel;
import com.mall.b2bp.models.product.ProductImagesUpFieldModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.models.product.ProductUpFieldModel;
import com.mall.b2bp.models.shop.ShopInfoModel;
import com.mall.b2bp.models.supplier.SupplierCategoryModel;
import com.mall.b2bp.populators.product.ProductPopulator;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.dept.ClassService;
import com.mall.b2bp.services.dept.DeptService;
import com.mall.b2bp.services.dept.SubClassService;
import com.mall.b2bp.services.lov.DeliveryDateService;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.services.parm.ParmService;
import com.mall.b2bp.services.product.ProductBarCodeUpFieldService;
import com.mall.b2bp.services.product.ProductBarcodeService;
import com.mall.b2bp.services.product.ProductImagesService;
import com.mall.b2bp.services.product.ProductImagesUpFieldService;
import com.mall.b2bp.services.product.ProductInfoService;
import com.mall.b2bp.services.product.ProductUpFieldService;
import com.mall.b2bp.services.product.RetekService;
import com.mall.b2bp.services.response.ResponseDataService;
import com.mall.b2bp.services.shop.ShopInfoService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.services.webservice.UploadProductResponse;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.utils.ValidateUtils;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductBarCode;
import com.mall.b2bp.vos.product.ProductImagesVo;
import com.mall.b2bp.vos.product.ProductImagesVoISO8601;
import com.mall.b2bp.vos.product.ProductInfoVo;
import com.mall.b2bp.vos.product.ProductInfoVoDateISO8601;
import com.mall.b2bp.vos.product.ProductParameterVo;
import com.mall.b2bp.vos.product.SubProductInfo;
import com.mall.b2bp.vos.supplier.SupplierVo;
import com.mall.b2bp.vos.user.UserVo;

@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {
	private static final String CONSIGNMENT = "Consignment";
	private static final String CONSIGNMENT_VIA_WAREHOUSE = "Consignment via warehouse";
	private static final String SUPPLIER_DIRECT_DELIVERY = "Supplier direct delivery";
	private static final String STRING3 = ",";
	private static final String SIZE = "Size";
	private static final String COLOR = "Color";
	private static final String SUPPLIER_CODE = "supplierCode";
	private static final String SUPPLIER_PRODUCT_CODE = "supplierProductCode";
	private static final String VERSION = "version";
	private static final String D = "D";
	private static final String C = "C";
	private static final String NULL = "null";
	private static final String APPROVE = "approve";
	private static final String N = "N";
	private static final String Y = "Y";
	private static final String ONLINE = "ONLINE";
	private static final String AUTO_APPROVED = "AUTO_APPROVED";
	private static final String REJECTED = "REJECTED";
	private static final String APPROVED = "APPROVED";
	private static final String ITEM_NUM_TYPE = "ITEM_NUM_TYPE";
	private static final String BARCODE_NUM = "BARCODE_NUM";
	private static final String STAGING = "STAGING";
	private static final String DRAFT = "DRAFT";
	private static final String UPLOADED = "UPLOADED";
	private static final String SUBMIT_FOR_APPROVAL = "SUBMIT_FOR_APPROVAL";
	// private static final String PRODUCT_APPROVAL_LEAD_TIME2 =
	// "product_approval_lead_time";
	// private static final String APPROVER = "approver";
	// private static final String SYSTEM_ADMIN = "systemAdmin";
	private static final String STRING2 = ":";
	private static final String STRING = "-";
	private static final String E = "E";
	private static final String WITH_NUTRITION_LABEL_ON_PACKAGE = "with nutrition label on package";
	private static final String NUTRITION_LABELING_EXEMPTION = "nutrition labeling exemption";
	private static final String W = "W";
	private static final String YES = "yes";

	private static final Logger LOG = LoggerFactory.getLogger(ProductInfoServiceImpl.class);
	private ProductInfoModelMapper productInfoMapper;
	private ProductBarcodeModelMapper productBarcodeModelMapper;
	private ProductImagesModelMapper productImagesModelMapper;
	private SupplierCategoryModelMapper supplierCategoryModelMapper;

	@Resource(name = "parmService")
	ParmService parmService;

	public SupplierCategoryModelMapper getSupplierCategoryModelMapper() {
		return supplierCategoryModelMapper;
	}

	@Autowired
	public void setSupplierCategoryModelMapper(SupplierCategoryModelMapper supplierCategoryModelMapper) {
		this.supplierCategoryModelMapper = supplierCategoryModelMapper;
	}

	public ProductImagesModelMapper getProductImagesModelMapper() {
		return productImagesModelMapper;
	}

	@Autowired
	public void setProductImagesModelMapper(ProductImagesModelMapper productImagesModelMapper) {
		this.productImagesModelMapper = productImagesModelMapper;
	}

	public ProductInfoModelMapper getProductInfoMapper() {
		return productInfoMapper;
	}

	@Resource(name = "productImagesService")
	ProductImagesService productImagesService;

	@Resource(name = "sessionService")
	SessionService sessionService;

	@Resource(name = "supplierService")
	SupplierService supplierService;

	@Resource(name = "retekService")
	RetekService retekService;

	@Resource(name = "productUpFieldService")
	ProductUpFieldService productUpFieldService;

	@Resource(name = "productImagesUpFieldService")
	ProductImagesUpFieldService productImagesUpFieldService;

	@Resource(name = "productBarCodeUpFieldService")
	ProductBarCodeUpFieldService productBarCodeUpFieldService;

	@Resource(name = "productBarcodeService")
	private ProductBarcodeService productBarcodeService;

	@Resource(name = "brandService")
	BrandService brandService;

	@Resource(name = "deptService")
	DeptService deptService;

	@Resource(name = "classService")
	ClassService classService;

	@Resource(name = "subClassService")
	SubClassService subClassService;

	@Resource(name = "lovService")
	LovService lovService;

	@Resource(name = "deliveryDateService")
	DeliveryDateService deliveryDateService;

	@Resource
	private ShopInfoService shopInfoService;

	@Resource
	protected ResponseDataService responseDataService;

	@Resource
	protected ResourceBundleMessageSource messageSource;

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ProductBarcodeModelMapper getProductBarcodeModelMapper() {
		return productBarcodeModelMapper;
	}

	@Autowired
	public void setProductBarcodeModelMapper(ProductBarcodeModelMapper productBarcodeModelMapper) {
		this.productBarcodeModelMapper = productBarcodeModelMapper;
	}

	@Autowired
	public void setProductInfoMapper(ProductInfoModelMapper productInfoMapper) {
		this.productInfoMapper = productInfoMapper;
	}

	@Override
	public int insertSelective(ProductInfoModel productinfo) {
		return productInfoMapper.insertSelective(productinfo);
	}

	private ProductParameterVo getProductParameterVo(String onlineUpdatedInd, String supplier,
			String supplierProductCode, String productCode, String status, String deliveryMode, String shortDescEn,
			UserVo userVo) {

		ProductParameterVo parameterVo = new ProductParameterVo();
		if (StringUtils.isNotEmpty(onlineUpdatedInd)) {
			parameterVo.setOnlineUpdatedInd(onlineUpdatedInd);
		}

		if (StringUtils.isNotBlank(supplier)) {
			parameterVo.setSupplier(supplier.split(STRING3));
		}

		if (StringUtils.isNotEmpty(supplierProductCode)) {
			parameterVo.setSupplierProductCode(supplierProductCode);
		}

		if (StringUtils.isNotEmpty(productCode)) {
			parameterVo.setProductCode(productCode);
		}

		if (StringUtils.isNotEmpty(status)) {
			parameterVo.setStatus(status.split(STRING3));
		}

		String[] modeStr = null;
		List<String> slist = new ArrayList<>();
		if (StringUtils.isNotEmpty(deliveryMode)) {

			if (deliveryMode.contains(STRING3)) {
				modeStr = deliveryMode.split(STRING3);
			} else {
				modeStr = new String[] { deliveryMode };
			}

		}

		if (modeStr != null && modeStr.length > 0) {
			for (String str : modeStr) {
				if (SUPPLIER_DIRECT_DELIVERY.equals(str)) {
					slist.add(D);
				}
				if (CONSIGNMENT_VIA_WAREHOUSE.equals(str)) {
					slist.add(W);
				}
				if (CONSIGNMENT.equals(str)) {
					slist.add(C);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(slist)) {
			parameterVo.setDeliveryMode(slist.toArray());
		}

		if (StringUtils.isNotEmpty(shortDescEn)) {
			parameterVo.setShortDescEn(shortDescEn);
		}

		if (userVo != null && StringUtils.isNotEmpty(userVo.getUserRole()) && "SUPPLIER".equals(userVo.getUserRole())) {
			parameterVo.setSupplierId(userVo.getSupplierId());
		}

		return parameterVo;
	}

	@Override
	public List<ProductInfoVo> selectProductList(String onlineUpdatedInd, String supplier, String supplierProductCode,
			String productCode, String status, String deliveryMode, String shortDescEn, UserVo userVo) {
		List<ProductInfoVo> productInfoVos = new ArrayList<>();

		ProductParameterVo parameterVo = getProductParameterVo(onlineUpdatedInd, supplier, supplierProductCode,
				productCode, status, deliveryMode, shortDescEn, userVo);

		List<ProductInfoModel> productInfoModels = productInfoMapper.selectProductList(parameterVo);
		ProductPopulator populator = new ProductPopulator();
		if (CollectionUtils.isNotEmpty(productInfoModels)) {
			for (ProductInfoModel productInfoModel : productInfoModels) {
				productInfoVos.add(populator.converProductModelToVo(productInfoModel));
			}
		}
		return productInfoVos;
	}

	@Override
	public ProductInfoModel getProductInfoModelBySupplierProductCode(Map<String, Object> map) {
		return productInfoMapper.getProductInfoModelBySupplierProductCode(map);
	}

	@Override
	public ProductInfoModel selectByPrimaryKey(BigDecimal id) {
		return productInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean validateProduct(ProductInfoVo productInfoVo, ResponseData<ProductInfoVo> responseData)
			throws ServiceException {

		boolean validateResult = true;
		if (productInfoVo == null) {
			responseData.add("product_productInfovo_null");
			validateResult = false;
		} else {
			// if (StringUtils.isEmpty(productInfoVo.getBrandCode())) {
			// responseData.add("product_save_brandcode");
			// validateResult = false;
			// }

			// assp 20170330
			// if (StringUtils.isEmpty(productInfoVo.getEstoreCategory())) {
			// responseData.add("product_save_estoreCategory");
			// validateResult = false;
			// }

			if (StringUtils.isEmpty(productInfoVo.getSupplierProductCode())) {
				responseData.add("product_save_supplierproductcode");
				validateResult = false;
			} else if (productInfoVo.getSupplierProductCode().length() > 30) {
				responseData.add("product_save_supplierproductcode_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getSupplierProductCode())) {
				responseData.add("product_save_supplierproductcode_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isEmpty(productInfoVo.getShortDescEn())) {
				responseData.add("product_save_shortdescen");
				validateResult = false;
			}
			// else if
			// (!StringUtil.checkItemNameEn(productInfoVo.getShortDescEn())) {
			// responseData.add("item_name_en_err");
			// validateResult = false;
			// }
			else if (productInfoVo.getShortDescEn().length() > 100) {
				responseData.add("product_save_shortdescen_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getShortDescEn())) {
				responseData.add("product_save_shortdescen_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isEmpty(productInfoVo.getShortDescTc())) {
				responseData.add("product_save_shortdesctc");
				validateResult = false;
			} // else if (StringUtils.isNotEmpty(productInfoVo.getShortDescTc())
				// && productInfoVo.getShortDescTc().length() > 13) {
				// responseData.add("product_save_shortdesctc_length_err");
				// validateResult = false;
				// }
			else if (StringUtil.checkSpecialCharacter(productInfoVo.getShortDescTc())) {
				responseData.add("product_save_shortdesctc_specialcharacter_err");
				validateResult = false;
			}

			// if (StringUtils.isNotEmpty(productInfoVo.getShortDescSc()) &&
			// productInfoVo.getShortDescSc().length() > 13) {
			// responseData.add("product_save_shortdescsc_length_err");
			// validateResult = false;
			// }
			else if (StringUtil.checkSpecialCharacter(productInfoVo.getShortDescSc())) {
				responseData.add("product_save_shortdescsc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getLongDescEn())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getLongDescEn()).length() > 4000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getLongDescEn()).getBytes().length > 4000)) {
				responseData.add("product_save_longdescen_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getLongDescEn())) {
				responseData.add("product_save_longdescen_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getLongDescTc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getLongDescTc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getLongDescTc()).getBytes().length > 4000)) {
				responseData.add("product_save_longdesctc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getLongDescTc())) {
				responseData.add("product_save_longdesctc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getLongDescSc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getLongDescSc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getLongDescSc()).getBytes().length > 4000)) {
				responseData.add("product_save_longdescsc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getLongDescSc())) {
				responseData.add("product_save_longdescsc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductUsageEn())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductUsageEn()).length() > 4000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductUsageEn()).getBytes().length > 4000)) {
				responseData.add("product_save_productUsageen_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductUsageEn())) {
				responseData.add("product_save_productUsageen_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductUsageTc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductUsageTc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductUsageTc()).getBytes().length > 4000)) {
				responseData.add("product_save_productUsagetc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductUsageTc())) {
				responseData.add("product_save_productUsagetc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductUsageSc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductUsageSc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductUsageSc()).getBytes().length > 4000)) {
				responseData.add("product_save_productUsagesc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductUsageSc())) {
				responseData.add("product_save_productUsagesc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductWarningsEn())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductWarningsEn()).length() > 4000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductWarningsEn()).getBytes().length > 4000)) {
				responseData.add("product_save_productWarningsen_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductWarningsEn())) {
				responseData.add("product_save_productWarningsen_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductWarningsTc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductWarningsTc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductWarningsTc()).getBytes().length > 4000)) {
				responseData.add("product_save_productWarningstc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductWarningsTc())) {
				responseData.add("product_save_productWarningstc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductWarningsSc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductWarningsSc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductWarningsSc()).getBytes().length > 4000)) {
				responseData.add("product_save_productWarningssc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductWarningsSc())) {
				responseData.add("product_save_productWarningssc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getStorageConditionEn())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getStorageConditionEn()).length() > 4000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getStorageConditionEn()).getBytes().length > 4000)) {
				responseData.add("product_save_storageConditionen_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getStorageConditionEn())) {
				responseData.add("product_save_storageConditionen_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getStorageConditionTc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getStorageConditionTc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getStorageConditionTc()).getBytes().length > 4000)) {
				responseData.add("product_save_storageConditiontc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getStorageConditionTc())) {
				responseData.add("product_save_storageConditiontc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getStorageConditionSc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getStorageConditionSc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getStorageConditionSc()).getBytes().length > 4000)) {
				responseData.add("product_save_storageConditionsc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getStorageConditionSc())) {
				responseData.add("product_save_storageConditionsc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductIngredientsEn())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductIngredientsEn()).length() > 4000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductIngredientsEn()).getBytes().length > 4000)) {
				responseData.add("product_save_productIngredientsen_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductIngredientsEn())) {
				responseData.add("product_save_productIngredientsen_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductIngredientsTc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductIngredientsTc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductIngredientsTc()).getBytes().length > 4000)) {
				responseData.add("product_save_productIngredientstc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductIngredientsTc())) {
				responseData.add("product_save_productIngredientstc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getProductIngredientsSc())
					&& (StringUtil.repalceAllNewLineToBr(productInfoVo.getProductIngredientsSc()).length() > 2000 || StringUtil
							.repalceAllNewLineToBr(productInfoVo.getProductIngredientsSc()).getBytes().length > 4000)) {
				responseData.add("product_save_productIngredientssc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getProductIngredientsSc())) {
				responseData.add("product_save_productIngredientssc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getColorCode()) && productInfoVo.getColorCode().length() > 250) {
				responseData.add("product_save_colorcode_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getColorCode())) {
				responseData.add("product_save_colorcode_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getColorDesc()) && productInfoVo.getColorDesc().length() > 250) {
				responseData.add("product_save_colordesc_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getColorDesc())) {
				responseData.add("product_save_colordesc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isNotEmpty(productInfoVo.getColorHexCode()) && productInfoVo.getColorHexCode().length() > 6) {
				responseData.add("product_save_colorhexcode_length_err");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getColorHexCode())) {
				responseData.add("product_save_colorhexcode_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isEmpty(productInfoVo.getProductCode())) {
				if (productInfoVo.getOnlineDate() == null && productInfoVo.getOfflineDate() != null) {
					responseData.add("product_save_onlinedate");
					validateResult = false;
				} else if (productInfoVo.getOnlineDate() != null && productInfoVo.getOfflineDate() != null
						&& productInfoVo.getOfflineDate().getTime() <= productInfoVo.getOnlineDate().getTime()) {
					responseData.add("product_save_offlinedate_err");
					validateResult = false;
				} else if (productInfoVo.getOnlineDate() != null) {
					// String productApprovalLeadTime =
					// ResourceUtil.getSystemConfig().getProperty(PRODUCT_APPROVAL_LEAD_TIME2);
					// if (StringUtils.isEmpty(productApprovalLeadTime)) {
					// responseData.add("product_save_product_approval_lead_time_err");
					// validateResult = false;
					// } else if
					// (!StringUtils.isNumeric(productApprovalLeadTime)) {
					// responseData.add("product_save_product_approval_lead_time_format_err");
					// validateResult = false;
					// } else if (productApprovalLeadTime.length() > 10) {
					// responseData.add("product_save_product_approval_lead_time_lenght_err");
					// validateResult = false;
					// } else if (productInfoVo.getOnlineDate().getTime() <
					// DateUtils.addOneDay(new
					// Date(),Integer.parseInt(productApprovalLeadTime)).getTime())
					// {
					// responseData.add("product_save_onlinedate_err");
					// validateResult = false;
					// }
				}
			} else {
				if (productInfoVo.getOnlineDate() == null && productInfoVo.getOfflineDate() != null) {
					responseData.add("product_save_onlinedate");
					validateResult = false;
				} else if (productInfoVo.getOnlineDate() != null && productInfoVo.getOfflineDate() != null
						&& productInfoVo.getOfflineDate().getTime() <= productInfoVo.getOnlineDate().getTime()) {
					responseData.add("product_save_offlinedate_err");
					validateResult = false;
				}
			}

			if (StringUtils.isEmpty(productInfoVo.getOriginCountry())) {
				responseData.add("product_save_originCountry");
				validateResult = false;
			}

			if (StringUtils.isEmpty(productInfoVo.getSizeDesc())) {
				responseData.add("product_save_sizeDesc");
				validateResult = false;
			} else if (StringUtil.checkSpecialCharacter(productInfoVo.getSizeDesc())) {
				responseData.add("product_save_sizeDesc_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtil.checkSpecialCharacter(productInfoVo.getShippingInfo())) {
				responseData.add("product_save_ShippingInfo_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtil.checkSpecialCharacter(productInfoVo.getProductShelfLife())) {
				responseData.add("product_save_ProductShelfLife_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtil.checkSpecialCharacter(productInfoVo.getMinShelfLife())) {
				responseData.add("product_save_MinShelfLife_specialcharacter_err");
				validateResult = false;
			}

			if (StringUtils.isEmpty(productInfoVo.getPnsOnlineDeliveryType())) {
				responseData.add("product_save_pnsOnlinedeliverytype");
				validateResult = false;
			}

			if (StringUtils.isEmpty(productInfoVo.getStandardUom())) {
				responseData.add("product_save_standarduom");
				validateResult = false;
			}
			if (StringUtils.isEmpty(productInfoVo.getDeliveryMode())) {
				responseData.add("product_save_deliverymode");
				validateResult = false;
			} else {
				if (!C.equals(productInfoVo.getDeliveryMode())
						&& StringUtils.isEmpty(productInfoVo.getDailyInventory())) {
					responseData.add("product_save_dailyinventory");
					validateResult = false;
				}

				// if (W.equals(productInfoVo.getDeliveryMode())
				// && null == productInfoVo.getSupplierLeadTime()) {
				// responseData.add("supplier_settings_err");
				// validateResult = false;
				// } else if (D.equals(productInfoVo.getDeliveryMode())) {
				// SupplierVo supplierVo =
				// supplierService.selectByPrimaryKey(productInfoVo.getSupplierCode());
				// if (null != supplierVo) {
				// if (null == supplierVo.getDeliveryFee()
				// || new BigDecimal(0) == supplierVo.getDeliveryFee()
				// || new BigDecimal(0) == supplierVo.getFreeDeliveryThreshold()
				// || null == supplierVo.getFreeDeliveryThreshold()
				// || null == supplierVo.getMaxDeliveryDay()
				// || null == supplierVo.getMinDeliveryDay()) {
				// responseData.add("supplier_settings_err");
				// validateResult = false;
				// }
				// }
				// }
			}

			if (productInfoVo.getUnitRetail() == null) {
				responseData.add("product_save_unitretail");
				validateResult = false;
			} else if (String.valueOf(productInfoVo.getUnitRetail()).length() > 20) {
				responseData.add("product_save_unitretail_length_err");
				validateResult = false;
			}

			if (productInfoVo.getConsignmentRate() != null && productInfoVo.getConsignmentRate().intValue() > 100) {
				responseData.add("product_save_consignmentRate_length_err");
				validateResult = false;
			}

			if (productInfoVo.getProductDimHeight() != null
					&& productInfoVo.getProductDimHeight().toString().length() > 12) {
				responseData.add("product_save_productdimheight_length_err");
				validateResult = false;
			}

			if (productInfoVo.getProductDimWidth() != null
					&& productInfoVo.getProductDimWidth().toString().length() > 12) {
				responseData.add("product_save_productdimwidth_length_err");
				validateResult = false;
			}

			if (productInfoVo.getProductDimLength() != null
					&& productInfoVo.getProductDimLength().toString().length() > 12) {
				responseData.add("product_save_productdimlength_length_err");
				validateResult = false;
			}

		}

		if (!validateResult) {
			return validateResult;
		}

		// Map<String, Object> map = new HashMap<>();
		//
		// if (productInfoVo.getId() == null) {
		// map.put(SUPPLIER_CODE, productInfoVo.getSupplierCode());
		// map.put(SUPPLIER_PRODUCT_CODE,
		// productInfoVo.getSupplierProductCode());
		// try {
		// ProductInfoModel productInfoModel = productInfoMapper
		// .getProductInfoModelBySupplierProductCode(map);
		// if (null != productInfoModel) {
		// responseData.add("product_save_supplierproductcode_exist");
		// validateResult = false;
		// }
		// } catch (Exception e) {
		// LOG.error(e.getMessage(), e);
		// throw new ServiceException(e.getMessage(), e);
		// }
		// }

		if (!checkVPN(productInfoVo, responseData)) {
			validateResult = false;
		}

		return validateResult;

	}

	public boolean checkVPN(ProductInfoVo productInfoVo, ResponseData<ProductInfoVo> responseData)
			throws ServiceException {
		Map<String, Object> map = new HashMap<>();

		map.put(SUPPLIER_CODE, productInfoVo.getSupplierCode());
		map.put(SUPPLIER_PRODUCT_CODE, productInfoVo.getSupplierProductCode());

		if (productInfoVo.getId() == null) {
			try {
				ProductInfoModel productInfoModel = productInfoMapper.getProductInfoModelBySupplierProductCode(map);
				if (null != productInfoModel) {
					responseData.add("product_save_supplierproductcode_exist");
					return false;
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage(), e);
			}
		} else if (!StringUtils.equals(productInfoVo.getSupplierProductCode(),
				productInfoMapper.selectByPrimaryKey(productInfoVo.getId()).getSupplierProductCode())) {
			try {
				ProductInfoModel productInfoModel = productInfoMapper.getProductInfoModelBySupplierProductCode(map);
				if (null != productInfoModel) {
					responseData.add("product_save_supplierproductcode_exist");
					return false;
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage(), e);
			}
		}
		return true;
	}

	@Override
	public boolean insertProduct(ProductInfoVo productjson, ResponseData<ProductInfoVo> responseData, UserVo userVo)
			throws ServiceException {
		boolean validateResult = true;

		if (userVo == null) {
			if (responseData != null) {
				responseData.add("user_login_expire");
			}
			validateResult = false;
			return validateResult;
		}

		if (productjson == null) {
			if (responseData != null) {
				responseData.add("product_productInfovo_null");
			}
			validateResult = false;
			return validateResult;
		}

		ProductInfoModel productInfo = new ProductInfoModel();

		try {
			if (productjson != null) {

				// copy object
				BeanUtils.copyProperties(productjson, productInfo);

				if (productjson.getDeliveryMode().equals(W)) {
					if (productjson.getMinOrderQty() != null && !productjson.getMinOrderQty().equals(NULL)
							&& StringUtils.isNotBlank(productjson.getMinOrderQty())) {
						productInfo.setMinOrderQty(new BigDecimal(productjson.getMinOrderQty()));
					}
					productInfo.setMinReplenishmentLevel(null);
					productInfo.setMaxReplenishmentLevel(null);
					productInfo.setMaxDeliverDate(null);
					productInfo.setMinDeliverDate(null);
					productInfo.setDailyInventory(new BigDecimal(productjson.getDailyInventory()));
				} else if (productjson.getDeliveryMode().equals(C)) {
					productInfo.setDailyInventory(null);
					productInfo.setSupplierLeadTime(null);
					productInfo.setMaxDeliverDate(productjson.getMaxDeliverDate());
					productInfo.setMinDeliverDate(productjson.getMinDeliverDate());
					if (productjson.getMinOrderQty() != null && !productjson.getMinOrderQty().equals(NULL)
							&& StringUtils.isNotBlank(productjson.getMinOrderQty())) {
						productInfo.setMinOrderQty(new BigDecimal(productjson.getMinOrderQty()));
					}
				} else if (productjson.getDeliveryMode().equals(D)) {
					productInfo.setSupplierLeadTime(null);
					productInfo.setMinOrderQty(null);
					productInfo.setMinReplenishmentLevel(null);
					productInfo.setMaxReplenishmentLevel(null);
					productInfo.setDailyInventory(new BigDecimal(productjson.getDailyInventory()));
				}

				// if (null == productjson.getBaseProductId()) {
				// if (StringUtils.isNotEmpty(productjson.getBrandCode())) {
				// productInfo.setBrandCode(new
				// BigDecimal(productjson.getBrandCode()));
				// }
				// } else {
				// ProductInfoModel productInfoModel =
				// selectByPrimaryKey(productjson.getBaseProductId());
				// productInfo.setBrandCode(productInfoModel.getBrandCode());
				// }

				if (null == productjson.getBaseProductId()) {
					if (productjson.getVariantOn() != null && COLOR.equals(productjson.getVariantOn())) {
						productInfo.setVariantColor(Y);
						productInfo.setVariantSize(N);
					} else if (productjson.getVariantOn() != null && SIZE.equals(productjson.getVariantOn())) {
						productInfo.setVariantColor(N);
						productInfo.setVariantSize(Y);
					} else {
						productInfo.setVariantColor(N);
						productInfo.setVariantSize(N);
					}
				} else {
					ProductInfoModel productInfoModel = selectByPrimaryKey(productjson.getBaseProductId());
					productInfo.setVariantColor(productInfoModel.getVariantColor());
					productInfo.setVariantSize(productInfoModel.getVariantSize());
				}

				productInfo.setLongDescEn(StringUtil.repalceAllNewLineToBr(productjson.getLongDescEn()));
				productInfo.setLongDescSc(StringUtil.repalceAllNewLineToBr(productjson.getLongDescSc()));
				productInfo.setLongDescTc(StringUtil.repalceAllNewLineToBr(productjson.getLongDescTc()));

				productInfo.setProductUsageEn(StringUtil.repalceAllNewLineToBr(productjson.getProductUsageEn()));
				productInfo.setProductUsageSc(StringUtil.repalceAllNewLineToBr(productjson.getProductUsageSc()));
				productInfo.setProductUsageTc(StringUtil.repalceAllNewLineToBr(productjson.getProductUsageTc()));

				productInfo.setProductIngredientsEn(StringUtil.repalceAllNewLineToBr(productjson
						.getProductIngredientsEn()));
				productInfo.setProductIngredientsSc(StringUtil.repalceAllNewLineToBr(productjson
						.getProductIngredientsSc()));
				productInfo.setProductIngredientsTc(StringUtil.repalceAllNewLineToBr(productjson
						.getProductIngredientsTc()));

				productInfo.setProductWarningsEn(StringUtil.repalceAllNewLineToBr(productjson.getProductWarningsEn()));
				productInfo.setProductWarningsSc(StringUtil.repalceAllNewLineToBr(productjson.getProductWarningsSc()));
				productInfo.setProductWarningsTc(StringUtil.repalceAllNewLineToBr(productjson.getProductWarningsTc()));

				productInfo
						.setStorageConditionEn(StringUtil.repalceAllNewLineToBr(productjson.getStorageConditionEn()));
				productInfo
						.setStorageConditionSc(StringUtil.repalceAllNewLineToBr(productjson.getStorageConditionSc()));
				productInfo
						.setStorageConditionTc(StringUtil.repalceAllNewLineToBr(productjson.getStorageConditionTc()));

				if (StringUtils.isEmpty(productjson.getNutritionLabel())) {
					productInfo.setNutritionLabel(N);
				}

				if (productjson.getBaseProductId() != null) {
					productInfo.setBaseProductId(productjson.getBaseProductId());
				}
				productInfo.setStatus(DRAFT);
				productInfo.setVersion(STAGING);
				productInfo.setAutoApprovalInd(Y);
				productInfo.setCreatedDate(new Date());
				productInfo.setLastUpdatedDate(new Date());
				if (userVo != null) {
					productInfo.setCreatedBy(userVo.getUserId());
					productInfo.setLastUpdatedBy(userVo.getUserId());
				}

				insertSelective(productInfo);

				// save productimages
				productImagesService.addAllProductImages(userVo.getUserId(), productInfo.getId(),
						productjson.getImagesList(), ConstantUtil.PRODUCT_IMAGE_TYPE);
				productImagesService.addAllProductImages(userVo.getUserId(), productInfo.getId(),
						productjson.getSwatchImagesList(), ConstantUtil.SWATCH_IMAGE_TYPE);
				// remove images
				if (productjson.getDeleteImagesList() != null && !productjson.getDeleteImagesList().isEmpty()) {
					for (ProductImagesVo productImagesVo : productjson.getDeleteImagesList()) {
						this.productImagesService.deleteByPrimaryKey(productImagesVo.getId());
					}
				}

				// remove images
				if (productjson.getDeleteSwatchImagesList() != null
						&& !productjson.getDeleteSwatchImagesList().isEmpty()) {
					for (ProductImagesVo productImagesVo : productjson.getDeleteSwatchImagesList()) {
						this.productImagesService.deleteByPrimaryKey(productImagesVo.getId());
					}
				}

				// insert data to product_barcode table
				boolean isPrimary = false;
				List<ProductBarCode> productBarCodeList = productjson.getBarcodeList();
				for (ProductBarCode productBarcodevo : productBarCodeList) {
					if (StringUtils.isNotEmpty(productBarcodevo.getItemNumType())
							& StringUtils.isNotEmpty(productBarcodevo.getBarcodeNum())) {
						ProductBarcodeModel productBarcodeModel = new ProductBarcodeModel();
						productBarcodeModel.setBarcodeNum(productBarcodevo.getBarcodeNum());
						productBarcodeModel.setItemNumType(productBarcodevo.getItemNumType());
						productBarcodeModel.setProductId(productInfo.getId());
						productBarcodeModel.setCreatedDate(new Date());
						productBarcodeModel.setLastUpdatedDate(new Date());

						if (userVo != null) {
							productBarcodeModel.setCreatedBy(userVo.getUserId());
							productBarcodeModel.setLastUpdatedBy(userVo.getUserId());
						}

						if (!isPrimary) {
							productBarcodeModel.setPrimaryInd(Y);
							isPrimary = true;
						} else {
							productBarcodeModel.setPrimaryInd(N);
						}

						productBarcodeService.insert(productBarcodeModel);
					}
				}

				if (productjson.getBaseProductId() == null) {
					ProductInfoVo productInfoVo = getProductInfoVo(productInfo.getId(), productInfo.getId());
					if (productInfoVo != null && responseData != null) {
						responseData.setReturnData(productInfoVo);
					}
				} else {
					ProductInfoVo productInfoVo = getProductInfoVo(productInfo.getId(), productInfo.getBaseProductId());
					if (productInfoVo != null && responseData != null) {
						responseData.setReturnData(productInfoVo);
					}
				}
			}

			return validateResult;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public boolean updateProduct(ProductInfoVo productjson, ResponseData<ProductInfoVo> responseData, UserVo userVo)
			throws ServiceException {
		boolean validateResult = true;

		if (userVo == null) {
			if (responseData != null) {
				responseData.add("user_login_expire");
			}
			validateResult = false;
			return validateResult;
		}

		if (productjson == null) {
			if (responseData != null) {
				responseData.add("product_productInfovo_null");
			}
			validateResult = false;
			return validateResult;
		}

		ProductInfoModel productInfo = new ProductInfoModel();
		try {
			if (productjson != null) {

				if (productjson.getMinDeliverDateStr() != null) {
					productjson.setMinDeliverDate(new BigDecimal(productjson.getMinDeliverDateStr()));
					productjson.setMinDeliverDateStr(null);
				}
				if (productjson.getMaxDeliverDateStr() != null) {
					productjson.setMaxDeliverDate(new BigDecimal(productjson.getMaxDeliverDateStr()));
					productjson.setMaxDeliverDateStr(null);
				}

				ProductInfoModel oldProductInfoModel = selectByPrimaryKey(productjson.getId());

				if ("Draft".equals(productjson.getStatus())) {
					productjson.setStatus("DRAFT");
				} else if ("Pending for approval".equals(productjson.getStatus())) {
					productjson.setStatus("SUBMIT_FOR_APPROVAL");
				} else if ("Approved".equals(productjson.getStatus())) {
					productjson.setStatus("APPROVED");
				} else if ("Auto Approved".equals(productjson.getStatus())) {
					productjson.setStatus("AUTO_APPROVED");
				} else if ("Approved in Retek".equals(productjson.getStatus())) {
					productjson.setStatus("APPROVED_IN_RETEK");
				} else if ("Unapproved in Retek".equals(productjson.getStatus())) {
					productjson.setStatus("UNAPPROVED_IN_RETEK");
				} else if ("Rejected".equals(productInfo.getStatus())) {
					productjson.setStatus("REJECTED");
				}

				// copy object
				BeanUtils.copyProperties(productjson, productInfo);

				if (StringUtils.isNotEmpty(oldProductInfoModel.getProductCode())) {
					productInfo.setBrandCode(oldProductInfoModel.getBrandCode());
					productInfo.setColorGroup(oldProductInfoModel.getColorGroup());
					productInfo.setColorHexCode(oldProductInfoModel.getColorHexCode());
					productInfo.setColorCode(oldProductInfoModel.getColorCode());
					productInfo.setColorDesc(oldProductInfoModel.getColorDesc());
					productInfo.setUnitRetail(oldProductInfoModel.getUnitRetail());
					productInfo.setOriginCountry(oldProductInfoModel.getOriginCountry());
					productInfo.setDept(oldProductInfoModel.getDept());
					productInfo.setClazz(oldProductInfoModel.getClazz());
					productInfo.setSubClass(oldProductInfoModel.getSubClass());
					productInfo.setDeliveryMode(oldProductInfoModel.getDeliveryMode());
					productInfo.setMinOrderQty(oldProductInfoModel.getMinOrderQty());
					productInfo.setConsignmentRate(oldProductInfoModel.getConsignmentRate());
				}

				productInfo.setLongDescEn(StringUtil.repalceAllNewLineToBr(productjson.getLongDescEn()));
				productInfo.setLongDescSc(StringUtil.repalceAllNewLineToBr(productjson.getLongDescSc()));
				productInfo.setLongDescTc(StringUtil.repalceAllNewLineToBr(productjson.getLongDescTc()));

				productInfo.setProductUsageEn(StringUtil.repalceAllNewLineToBr(productjson.getProductUsageEn()));
				productInfo.setProductUsageSc(StringUtil.repalceAllNewLineToBr(productjson.getProductUsageSc()));
				productInfo.setProductUsageTc(StringUtil.repalceAllNewLineToBr(productjson.getProductUsageTc()));

				productInfo.setProductIngredientsEn(StringUtil.repalceAllNewLineToBr(productjson
						.getProductIngredientsEn()));
				productInfo.setProductIngredientsSc(StringUtil.repalceAllNewLineToBr(productjson
						.getProductIngredientsSc()));
				productInfo.setProductIngredientsTc(StringUtil.repalceAllNewLineToBr(productjson
						.getProductIngredientsTc()));

				productInfo.setProductWarningsEn(StringUtil.repalceAllNewLineToBr(productjson.getProductWarningsEn()));
				productInfo.setProductWarningsSc(StringUtil.repalceAllNewLineToBr(productjson.getProductWarningsSc()));
				productInfo.setProductWarningsTc(StringUtil.repalceAllNewLineToBr(productjson.getProductWarningsTc()));

				productInfo
						.setStorageConditionEn(StringUtil.repalceAllNewLineToBr(productjson.getStorageConditionEn()));
				productInfo
						.setStorageConditionSc(StringUtil.repalceAllNewLineToBr(productjson.getStorageConditionSc()));
				productInfo
						.setStorageConditionTc(StringUtil.repalceAllNewLineToBr(productjson.getStorageConditionTc()));
				// if (StringUtils.isNotEmpty(productjson.getBrandCode())) {
				// productInfo.setBrandCode(new
				// BigDecimal(productjson.getBrandCode()));
				// }

				if (productjson.getDailyInventory() != null && !NULL.equals(productjson.getDailyInventory())
						&& StringUtils.isNotEmpty(productjson.getDailyInventory())) {
					productInfo.setDailyInventory(new BigDecimal(productjson.getDailyInventory()));
				}

				// deal with variant on
				if (null == productjson.getBaseProductId()) {
					if (StringUtils.isNotEmpty(productjson.getVariantOn()) && COLOR.equals(productjson.getVariantOn())) {
						productInfo.setVariantColor(Y);
						productInfo.setVariantSize(N);
						List<SubProductInfo> subProductInfoList = getSubProductListByBaseProductId(String
								.valueOf(oldProductInfoModel.getId()));
						for (SubProductInfo subProductInfo : subProductInfoList) {
							ProductInfoModel productInfoModel = selectByPrimaryKey(subProductInfo.getId());
							productInfoModel.setVariantColor(Y);
							productInfoModel.setVariantSize(N);
							updateByPrimaryKey(productInfoModel);
						}

					} else if (StringUtils.isNotEmpty(productjson.getVariantOn())
							&& SIZE.equals(productjson.getVariantOn())) {
						productInfo.setVariantColor(N);
						productInfo.setVariantSize(Y);
						List<SubProductInfo> subProductInfoList = getSubProductListByBaseProductId(String
								.valueOf(oldProductInfoModel.getId()));
						for (SubProductInfo subProductInfo : subProductInfoList) {
							ProductInfoModel productInfoModel = selectByPrimaryKey(subProductInfo.getId());
							productInfoModel.setVariantColor(N);
							productInfoModel.setVariantSize(Y);
							updateByPrimaryKey(productInfoModel);
						}

					} else {
						productInfo.setVariantColor(N);
						productInfo.setVariantSize(N);
						List<SubProductInfo> subProductInfoList = getSubProductListByBaseProductId(String
								.valueOf(oldProductInfoModel.getId()));
						for (SubProductInfo subProductInfo : subProductInfoList) {
							ProductInfoModel productInfoModel = selectByPrimaryKey(subProductInfo.getId());
							productInfoModel.setVariantColor(N);
							productInfoModel.setVariantSize(N);
							updateByPrimaryKey(productInfoModel);
						}

					}
				} else {
					ProductInfoModel productInfoModel = selectByPrimaryKey(productjson.getBaseProductId());
					productInfo.setVariantColor(productInfoModel.getVariantColor());
					productInfo.setVariantSize(productInfoModel.getVariantSize());
				}

				// deal with brand code
				if (null == productjson.getBaseProductId()) {
					// if (!productInfo.getBrandCode().equals(
					// oldProductInfoModel.getBrandCode())) {
					// List<SubProductInfo> subProductInfoList =
					// getSubProductListByBaseProductId(String.valueOf(oldProductInfoModel.getId()));
					// for (SubProductInfo subProductInfo : subProductInfoList)
					// {
					// ProductInfoModel productInfoModel =
					// selectByPrimaryKey(subProductInfo.getId());
					// productInfoModel.setBrandCode(productInfo.getBrandCode());
					// updateByPrimaryKey(productInfoModel);
					// }
					// }
				} else {
					// ProductInfoModel productInfoModel =
					// selectByPrimaryKey(productjson.getBaseProductId());
					// productInfo.setBrandCode(productInfoModel.getBrandCode());
				}

				if (productjson.getDeliveryMode().equals(W)) {
					if (productjson.getMinOrderQty() != null && !productjson.getMinOrderQty().equals(NULL)
							&& StringUtils.isNotBlank(productjson.getMinOrderQty())) {
						productInfo.setMinOrderQty(new BigDecimal(productjson.getMinOrderQty()));
					}
					productInfo.setMinReplenishmentLevel(null);
					productInfo.setMaxReplenishmentLevel(null);
					productInfo.setMaxDeliverDate(null);
					productInfo.setMinDeliverDate(null);
					productInfo.setDailyInventory(new BigDecimal(productjson.getDailyInventory()));
				} else if (productjson.getDeliveryMode().equals(C)) {
					productInfo.setDailyInventory(null);
					productInfo.setSupplierLeadTime(null);
					productInfo.setMaxDeliverDate(productjson.getMaxDeliverDate());
					productInfo.setMinDeliverDate(productjson.getMinDeliverDate());
					if (productjson.getMinOrderQty() != null && !productjson.getMinOrderQty().equals(NULL)
							&& StringUtils.isNotBlank(productjson.getMinOrderQty())) {
						productInfo.setMinOrderQty(new BigDecimal(productjson.getMinOrderQty()));
					}
				} else if (productjson.getDeliveryMode().equals(D)) {
					productInfo.setSupplierLeadTime(null);
					productInfo.setMinOrderQty(null);
					productInfo.setMinReplenishmentLevel(null);
					productInfo.setMaxReplenishmentLevel(null);
					productInfo.setDailyInventory(new BigDecimal(productjson.getDailyInventory()));
				}

				if (StringUtils.isNotEmpty(productjson.getDealType()) && "COPY".equals(productjson.getDealType())) {
					productInfo.setStatus(productjson.getStatus());
				} else if (!NULL.equals(productjson.getStatus()) && null != productjson.getStatus()
						&& !productjson.getStatus().equals(SUBMIT_FOR_APPROVAL)) {
					productInfo.setStatus(DRAFT);
				}

				if (StringUtils.isNotEmpty(productjson.getProductCode()) && checkAutoApprove(productjson, responseData)
						&& !N.equals(productjson.getAutoApprovalInd())) {
					productInfo.setAutoApprovalInd(N);
				}
				productInfo.setLastUpdatedDate(new Date());
				if (userVo != null) {
					productInfo.setLastUpdatedBy(userVo.getUserId());
				}

				updateByPrimaryKey(productInfo);

				// save productimages
				productImagesService.addAllProductImages(userVo.getUserId(), productInfo.getId(),
						productjson.getImagesList(), ConstantUtil.PRODUCT_IMAGE_TYPE);
				productImagesService.addAllProductImages(userVo.getUserId(), productInfo.getId(),
						productjson.getSwatchImagesList(), ConstantUtil.SWATCH_IMAGE_TYPE);

				// remove images
				if (productjson.getDeleteImagesList() != null && !productjson.getDeleteImagesList().isEmpty()) {
					for (ProductImagesVo productImagesVo : productjson.getDeleteImagesList()) {
						this.productImagesService.deleteByPrimaryKey(productImagesVo.getId());
					}
				}

				// remove images
				if (productjson.getDeleteSwatchImagesList() != null
						&& !productjson.getDeleteSwatchImagesList().isEmpty()) {
					for (ProductImagesVo productImagesVo : productjson.getDeleteSwatchImagesList()) {
						this.productImagesService.deleteByPrimaryKey(productImagesVo.getId());
					}
				}

				List<ProductBarcodeModel> originalProductBarCodeList = productBarcodeService
						.getProductBarcodeModelsByProductId(String.valueOf(productjson.getId()));
				deleteProductBarcode(productjson, originalProductBarCodeList);

				// insert data to product_barcode table
				List<ProductBarCode> productBarCodeList = productjson.getBarcodeList();
				boolean isPrimary = false;

				for (ProductBarCode productBarcodevo : productBarCodeList) {
					boolean productBarcodeUpdateFlag = true;

					for (ProductBarcodeModel originalProductBar : originalProductBarCodeList) {
						if (StringUtils.equals(originalProductBar.getItemNumType(), productBarcodevo.getItemNumType())
								&& StringUtils.equals(originalProductBar.getBarcodeNum(),
										productBarcodevo.getBarcodeNum())) {
							productBarcodeUpdateFlag = false;
						}
					}

					if (productBarcodeUpdateFlag) {

						ProductBarcodeModel productBarcodeModel = new ProductBarcodeModel();
						productBarcodeModel.setBarcodeNum(productBarcodevo.getBarcodeNum());
						productBarcodeModel.setItemNumType(productBarcodevo.getItemNumType());
						if (userVo != null) {
							productBarcodeModel.setLastUpdatedBy(userVo.getUserId());
						}

						if (productBarcodevo.getId() == null) {
							if (StringUtils.isNotEmpty(productBarcodevo.getItemNumType())
									&& StringUtils.isNotEmpty(productBarcodevo.getBarcodeNum())) {
								productBarcodeModel.setCreatedDate(new Date());
								productBarcodeModel.setLastUpdatedDate(new Date());
								if (userVo != null) {
									productBarcodeModel.setCreatedBy(userVo.getUserId());
								}
								productBarcodeModel.setProductId(productjson.getId());
								if (!isPrimary) {
									productBarcodeModel.setPrimaryInd(Y);
									isPrimary = true;
								} else {
									productBarcodeModel.setPrimaryInd(N);
								}
								productBarcodeService.insert(productBarcodeModel);
							}
						} else {
							if (StringUtils.isEmpty(productBarcodevo.getItemNumType())
									&& StringUtils.isEmpty(productBarcodevo.getBarcodeNum())) {
								productBarcodeService.deleteByPrimaryKey(productBarcodevo.getId());

							} else {
								productBarcodeModel.setLastUpdatedDate(new Date());
								productBarcodeModel.setId(productBarcodevo.getId());
								if (!isPrimary) {
									productBarcodeModel.setPrimaryInd(Y);
									isPrimary = true;
								} else {
									productBarcodeModel.setPrimaryInd(N);
								}
								productBarcodeService.updateByPrimaryKeySelective(productBarcodeModel);
							}
						}
					}
				}

				if (productjson.getBaseProductId() == null) {
					ProductInfoVo productInfoVo = getProductInfoVo(productjson.getId(), productjson.getId());
					if (productInfoVo != null && responseData != null) {
						responseData.setReturnData(productInfoVo);
					}
				} else {
					ProductInfoVo productInfoVo = getProductInfoVo(productjson.getId(), productjson.getBaseProductId());
					if (productInfoVo != null && responseData != null) {
						responseData.setReturnData(productInfoVo);
					}
				}

			}
			return validateResult;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private void deleteProductBarcode(ProductInfoVo productjson, List<ProductBarcodeModel> originalProductBarCodeList) {
		List<BigDecimal> productBarcodeIds = new ArrayList<>();
		List<ProductBarCode> productBarCodeList = productjson.getBarcodeList();
		for (ProductBarCode productBarCode : productBarCodeList) {
			if (productBarCode.getId() != null) {
				productBarcodeIds.add(productBarCode.getId());
			}
		}

		// List<ProductBarcodeModel> originalProductBarCodeList =
		// productBarcodeService.getProductBarcodeModelsByProductId(String.valueOf(productjson.getId()));
		for (ProductBarcodeModel productBarcodeModel : originalProductBarCodeList) {
			if (!productBarcodeIds.contains(productBarcodeModel.getId())) {
				productBarcodeService.deleteByPrimaryKey(productBarcodeModel.getId());
			}
		}

	}

	@Override
	public ProductInfoVo getProductInfoVo(BigDecimal productId, BigDecimal baseProductId) throws ServiceException {
		if (productId == null || baseProductId == null) {
			return null;
		}
		ProductInfoVo productInfoVo = new ProductInfoVo();

		ProductInfoModel productInfoModel = selectByPrimaryKey(productId);
		if (productInfoModel == null) {
			return null;
		}

		// copy object
		BeanUtils.copyProperties(productInfoModel, productInfoVo);
		// productInfoVo.setRetekLastApprovedDate(productInfoModel.getRetekLastApprovedDate());

		if (productInfoModel.getVariantColor() != null && Y.equalsIgnoreCase(productInfoModel.getVariantColor())) {
			productInfoVo.setVariantOn(COLOR);
		} else if (productInfoModel.getVariantSize() != null && Y.equalsIgnoreCase(productInfoModel.getVariantSize())) {
			productInfoVo.setVariantOn(SIZE);
		} else {
			productInfoVo.setVariantOn("");
		}

		productInfoVo.setDailyInventory(String.valueOf(productInfoModel.getDailyInventory()));
		productInfoVo.setMinOrderQty(String.valueOf(productInfoModel.getMinOrderQty()));
		// productInfoVo.setBrandCode(String.valueOf(productInfoModel.getBrandCode()));

		productInfoVo.setLongDescEn(StringUtil.repalceAllBrToNewLine(productInfoVo.getLongDescEn()));
		productInfoVo.setLongDescSc(StringUtil.repalceAllBrToNewLine(productInfoVo.getLongDescSc()));
		productInfoVo.setLongDescTc(StringUtil.repalceAllBrToNewLine(productInfoVo.getLongDescTc()));

		productInfoVo.setProductUsageEn(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductUsageEn()));
		productInfoVo.setProductUsageSc(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductUsageSc()));
		productInfoVo.setProductUsageTc(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductUsageTc()));

		productInfoVo
				.setProductIngredientsEn(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductIngredientsEn()));
		productInfoVo
				.setProductIngredientsSc(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductIngredientsSc()));
		productInfoVo
				.setProductIngredientsTc(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductIngredientsTc()));

		productInfoVo.setProductWarningsEn(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductWarningsEn()));
		productInfoVo.setProductWarningsSc(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductWarningsSc()));
		productInfoVo.setProductWarningsTc(StringUtil.repalceAllBrToNewLine(productInfoVo.getProductWarningsTc()));

		productInfoVo.setStorageConditionEn(StringUtil.repalceAllBrToNewLine(productInfoVo.getStorageConditionEn()));
		productInfoVo.setStorageConditionSc(StringUtil.repalceAllBrToNewLine(productInfoVo.getStorageConditionSc()));
		productInfoVo.setStorageConditionTc(StringUtil.repalceAllBrToNewLine(productInfoVo.getStorageConditionTc()));

		productInfoVo.setProductStatus(productInfoModel.getStatus());
		Map<String, Object> onlineMap = new HashMap<>();
		onlineMap.put("productCode", productInfoModel.getProductCode());
		onlineMap.put("supplierProductCode", productInfoModel.getSupplierProductCode());
		onlineMap.put(VERSION, ONLINE);
		ProductInfoModel onlineProductInfoModel = getProductInfoModelByProductCode(onlineMap);

		Map<String, Object> stagingMap = new HashMap<>();
		stagingMap.put("productCode", productInfoModel.getProductCode());
		stagingMap.put("supplierProductCode", productInfoModel.getSupplierProductCode());
		stagingMap.put(VERSION, "STAGING");
		ProductInfoModel stagingProductInfoModel = getProductInfoModelByProductCode(stagingMap);

		if (onlineProductInfoModel != null) {
			productInfoVo.setHasOnline(true);
			productInfoVo.setOnlineUpdatedInd(onlineProductInfoModel.getOnlineUpdatedInd());
			productInfoVo.setOnlineProductId(String.valueOf(onlineProductInfoModel.getId()));
		} else {
			productInfoVo.setHasOnline(false);
		}

		if (stagingProductInfoModel != null) {
			productInfoVo.setStagingProductId(String.valueOf(stagingProductInfoModel.getId()));
		}

		System.out.println(productInfoModel.getSupplierCode());
		if (null != productInfoModel.getSupplierCode()
				&& StringUtils.isNotEmpty(String.valueOf(productInfoModel.getSupplierCode()))) {
			SupplierVo supplierModel = supplierService.selectByPrimaryKey(productInfoModel.getSupplierCode());
			if (null != supplierModel) {
				productInfoVo.setSupplierName(supplierModel.getName());
			}
		}

		List<ProductBarcodeModel> productBarcodeModels = productBarcodeService
				.getProductBarcodeModelsByProductId(String.valueOf(productId));
		List<ProductBarCode> productBarCodeList = new ArrayList<>();
		// UserVo userVo = sessionService.getCurrentUser();
		for (ProductBarcodeModel productBarcodeModel : productBarcodeModels) {
			ProductBarCode productBarCode = new ProductBarCode();
			BeanUtils.copyProperties(productBarcodeModel, productBarCode);
			// if (userVo.getUserName().equals(APPROVER)
			// || userVo.getUserName().equals(SYSTEM_ADMIN)) {
			List<ProductBarCodeUpFieldModel> productBarCodeUpdatedFiledModelList = productBarCodeUpFieldService
					.getProductBarCodeUpFieldModelById(String.valueOf(productBarcodeModel.getId()));
			for (ProductBarCodeUpFieldModel productBarCodeUpFieldModel : productBarCodeUpdatedFiledModelList) {
				if (!productBarCode.isHighLightBarCodeNum()
						&& productBarCodeUpFieldModel.getColumnName().equals(BARCODE_NUM)) {
					productBarCode.setHighLightBarCodeNum(true);
				}
				if (!productBarCode.isHighLightItemNumType()
						&& productBarCodeUpFieldModel.getColumnName().equals(ITEM_NUM_TYPE)) {
					productBarCode.setHighLightItemNumType(true);
				}
			}
			// }
			productBarCodeList.add(productBarCode);
		}

		List<ProductImagesVo> imagesList = this.productImagesService.getProductImagesByProductId(productId,
				ConstantUtil.PRODUCT_IMAGE_TYPE);
		List<ProductImagesVo> swatchImagesList = this.productImagesService.getProductImagesByProductId(productId,
				ConstantUtil.SWATCH_IMAGE_TYPE);
		// lwh images
		productInfoVo.setImagesList(imagesList);
		productInfoVo.setSwatchImagesList(swatchImagesList);

		productInfoVo.setBarcodeList(productBarCodeList);

		List<SubProductInfo> subProductInfoList = getSubProductListByBaseProductId(String.valueOf(baseProductId));
		if (CollectionUtils.isNotEmpty(subProductInfoList)) {
			productInfoVo.setSubProductInfoList(subProductInfoList);
		}

		List<ProductUpFieldModel> productUpdatedFiledModelList = productUpFieldService
				.getProductUpFieldModelByProductId(String.valueOf(productId));
		productInfoVo.setProductUpFiledList(productUpdatedFiledModelList);

		List<ProductImagesUpFieldModel> productImagesUpdatedFiledModelList = productImagesUpFieldService
				.getProductImagesUpFieldModelByProductId(String.valueOf(productId));

		productInfoVo.setProductImagesUpFiledList(productImagesUpdatedFiledModelList);

		// UserSupplierVo userSupplierVo =
		// supplierService.getUserSupplierVoBySupplierId(productInfoVo.getSupplierCode());
		// if (null != userSupplierVo) {
		// productInfoVo.setSupplierName(userSupplierVo.getUserName());
		// }

		if ("DRAFT".equals(productInfoVo.getStatus())) {
			productInfoVo.setStatus("Draft");
		} else if ("SUBMIT_FOR_APPROVAL".equals(productInfoVo.getStatus())) {
			productInfoVo.setStatus("Pending for approval");
		} else if ("APPROVED".equals(productInfoVo.getStatus())) {
			productInfoVo.setStatus("Approved");
		} else if ("AUTO_APPROVED".equals(productInfoVo.getStatus())) {
			productInfoVo.setStatus("Auto Approved");
		} else if ("APPROVED_IN_RETEK".equals(productInfoVo.getStatus())) {
			productInfoVo.setStatus("Approved in Retek");
		} else if ("UNAPPROVED_IN_RETEK".equals(productInfoVo.getStatus())) {
			productInfoVo.setStatus("Unapproved in Retek");
		} else if ("REJECTED".equals(productInfoVo.getStatus())) {
			productInfoVo.setStatus("Rejected");
		}

		if (productInfoVo.getMinDeliverDate() != null) {
			productInfoVo.setMinDeliverDateStr(productInfoVo.getMinDeliverDate().toString());
			productInfoVo.setMinDeliverDate(null);
		}
		if (productInfoVo.getMaxDeliverDate() != null) {
			productInfoVo.setMaxDeliverDateStr(productInfoVo.getMaxDeliverDate().toString());
			productInfoVo.setMaxDeliverDate(null);
		}

		return productInfoVo;

	}

	@Override
	public void deleteProductByProductId(String productId) {
		productInfoMapper.deleteByPrimaryKey(new BigDecimal(productId));
	}

	public boolean uploadProductByProductId(ProductInfoModel productInfoModel,
			List<ProductImagesVoISO8601> imagesListISO8601) {


		StringBuffer uri = new StringBuffer();
		uri.append(parmService.getWebserviceUrl());
		uri.append("products/uploadProduct");

		if (productInfoModel == null)
			return false;

		ProductInfoVoDateISO8601 productInfoVo = new ProductInfoVoDateISO8601();
		BeanUtils.copyProperties(productInfoModel, productInfoVo);

		String barcode = "";
		if (productInfoVo.getId() != null) {
			List<ProductBarcodeModel> productBarcodeModels = productBarcodeService
					.getProductBarcodeModelsByProductId(productInfoVo.getId().toString());

			if (CollectionUtils.isNotEmpty(productBarcodeModels)) {
				ProductBarcodeModel productBarcodeModel = productBarcodeModels.get(0);
				barcode = productBarcodeModel.getBarcodeNum();
			}
		}

		productInfoVo.setBarcode(barcode);
		productInfoVo.setImagesList(imagesListISO8601);
		LOG.info("*start*******************************");
		LOG.info("start call webservice:" + uri);

		// get shop code
		String supplierCode = productInfoModel.getSupplierCode();
		if (StringUtils.isNotEmpty(supplierCode)) {
			try {
				ShopInfoModel shopModel = shopInfoService.selectByPrimaryKey(new BigDecimal(supplierCode));
				if (shopModel != null) {
					productInfoVo.setSupplierCode(shopModel.getShopCode());
					productInfoVo.setSupplierName(shopModel.getShopCode());
				}
			} catch (ServiceException e) {
				LOG.error("get shop code error:" + e.getMessage());
			}
		}

		// if(StringUtils.isNotBlank(productInfoVo.getSupplierName())){
		// if(productInfoVo.getSupplierName().equalsIgnoreCase("fortress"))
		// productInfoVo.setEstoreCategory("fortress");
		// else if(productInfoVo.getSupplierName().equalsIgnoreCase("bossini"))
		// productInfoVo.setEstoreCategory("bossini");
		// else{
		// productInfoVo.setEstoreCategory(productInfoVo.getSupplierName().toLowerCase());
		// }
		// }

		LOG.info("productCode:" + productInfoVo.getProductCode());
		LOG.info("product getLongDescEn:" + productInfoVo.getLongDescEn());
		LOG.info("product getLongDescSc:" + productInfoVo.getLongDescSc());
		LOG.info("product getLongDescTc:" + productInfoVo.getLongDescTc());
		LOG.info("barcode:" + productInfoVo.getBarcode());
		LOG.info("estoreCategory:" + productInfoVo.getEstoreCategory());
		LOG.info("dailyInventory:" + productInfoVo.getDailyInventory());
		LOG.info("OfflineDate:" + productInfoVo.getOfflineDate());
		LOG.info("OnlineDate:" + productInfoVo.getOnlineDate());
		LOG.info("SupplierName:" + productInfoVo.getSupplierName());
		LOG.info("Suppliercode:" + productInfoVo.getSupplierCode());

		RestTemplate restTemplate = new RestTemplate();

		UploadProductResponse result = restTemplate.postForObject(uri.toString(), productInfoVo,
				UploadProductResponse.class);
		if (result != null)
			LOG.info("end call webservice result:" + result.getResponseCode());
		LOG.info("*end*******************************");
		if (result.getResponseCode().equals("S00001"))
			return true;
		return false;
	}

	@Override
	public List<SubProductInfo> getSubProductListByBaseProductId(String baseProductId) {
		return productInfoMapper.getSubProductListByBaseProductId(baseProductId);
	}

	public ProductImagesService getProductImagesService() {
		return productImagesService;
	}

	public void setProductImagesService(ProductImagesService productImagesService) {
		this.productImagesService = productImagesService;
	}

	@Override
	public void updateProductStatus(String pId, String status, String pType) throws ServiceException {
		UserVo userVo = sessionService.getCurrentUser();
		try {
			// ProductInfoModel record = new ProductInfoModel();
			String[] pids = StringUtil.getAllProductId(pId);
			if (pids != null && pids.length > 0) {
				for (String code : pids) {

					if (StringUtils.isEmpty(code))
						continue;

					ProductInfoModel record = productInfoMapper.selectByPrimaryKey(new BigDecimal(code));
					record.setLastUpdatedDate(new Date());
					record.setLastApprovalDate(new Date());
					if (userVo != null) {
						record.setLastUpdatedBy(userVo.getUserId());
					}
					record.setId(new BigDecimal(code));
					if (StringUtils.isNotEmpty(pType)) {
						if (APPROVE.equals(pType)) {
							// approve
							record.setStatus(APPROVED);
							approveProduct(record);

						}
						if ("reject".equals(pType)) {
							record.setStatus(REJECTED);
						}
						if (("submitApprove").equals(pType)) {
							if (StringUtils.isNotEmpty(record.getProductCode())
									&& record.getAutoApprovalInd().equals(Y)) {
								record.setLastApprovalDate(new Date());
								// approve
								record.setStatus(AUTO_APPROVED);
								approveProduct(record);
							} else {
								record.setStatus(SUBMIT_FOR_APPROVAL);
							}
						}
					}
					// modify at 20160329
					record.setSupplierProfileUpdateInd(N);
					productInfoMapper.updateByPrimaryKey(record);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

	}

	private String getFixLenthString(int strLength) {

		Random rm = new Random();

		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

		String fixLenthString = String.valueOf(pross);

		System.out.println(fixLenthString);

		return fixLenthString.substring(1, strLength + 1);
	}

	private void approveProduct(ProductInfoModel record) throws SystemException {
		// if exit update else insert into
		if (record != null) {
			record.setAutoApprovalInd(Y);

			String productCode = record.getProductCode();
			BigDecimal productId = record.getId();

			Map imageMap = new HashMap();
			imageMap.put("productId", record.getId());
			imageMap.put("imageType", null);// ConstantUtil.PRODUCT_IMAGE_TYPE);
			// productimage
			List<ProductImagesModel> productImagesModels = productImagesModelMapper
					.getProductImagesByProductId(imageMap);

			if (StringUtils.isEmpty(record.getProductCode())) {

				String pCode = DateUtils.formatDate(new Date()) + getFixLenthString(6);
				// insert productcode
				// String pCode =
				// retekService.generateSkuId(record.getSupplierCode(),record.getSupplierProductCode());
				record.setProductCode(pCode);
				record.setLastApprovalDate(new Date());
				productInfoMapper.updateByPrimaryKey(record);
				//
				record.setProductCode(pCode);
				record.setVersion(ONLINE);
				record.setStatus(APPROVED);
				// int nextProductId=productInfoMapper.getMaxProductId();
				// record.setId(new BigDecimal(nextProductId));
				record.setCreatedBy(sessionService.getCurrentUser().getUserId());
				record.setCreatedDate(new Date());
				record.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
				record.setLastUpdatedDate(new Date());
				productInfoMapper.insertSelective(record);
				// productbarcode
				if (productId != null) {
					List<ProductBarcodeModel> productBarcodeModels = productBarcodeService
							.getProductBarcodeModelsByProductId(productId.toString());// record.getId().toString());
					if (CollectionUtils.isNotEmpty(productBarcodeModels)) {
						for (ProductBarcodeModel productBarcodeModel : productBarcodeModels) {
							productBarcodeModel.setProductId(record.getId());
							productBarcodeModel.setCreatedBy(sessionService.getCurrentUser().getUserId());
							productBarcodeModel.setCreatedDate(new Date());
							productBarcodeModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
							productBarcodeModel.setLastUpdatedDate(new Date());
							productBarcodeModelMapper.insertSelective(productBarcodeModel);
						}
					}
				}

				// productimage
				// Map map=new HashMap();
				// map.put("productId", record.getId());
				// map.put("imageType", ConstantUtil.PRODUCT_IMAGE_TYPE);
				// List<ProductImagesModel> productImagesModels =
				// productImagesModelMapper.getProductImagesByProductId(map);
				if (CollectionUtils.isNotEmpty(productImagesModels)) {
					for (ProductImagesModel productImagesModel : productImagesModels) {
						productImagesModel.setProductId(record.getId());
						productImagesModel.setCreatedBy(sessionService.getCurrentUser().getUserId());
						productImagesModel.setCreatedDate(new Date());
						productImagesModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
						productImagesModel.setLastUpdatedDate(new Date());
						productImagesModelMapper.insertSelective(productImagesModel);
					}
				}

			} else {
				// update productcode
				Map<String, Object> map = new HashMap<>();
				map.put("productCode", record.getProductCode());
				map.put(VERSION, ONLINE);
				ProductInfoModel onlineProductInfoModel = getProductInfoModelByProductCode(map);
				if (onlineProductInfoModel != null) {
					BigDecimal onlineProductInfoModelId = onlineProductInfoModel.getId();
					String baseProductCode = onlineProductInfoModel.getBaseProductCode();
					BeanUtils.copyProperties(record, onlineProductInfoModel);
					// BigDecimal bid = onlineProductInfoModel.getId();
					onlineProductInfoModel.setVersion(ONLINE);
					onlineProductInfoModel.setId(onlineProductInfoModelId);
					onlineProductInfoModel.setBaseProductCode(baseProductCode);
					onlineProductInfoModel.setCreatedBy(sessionService.getCurrentUser().getUserId());
					onlineProductInfoModel.setCreatedDate(new Date());
					onlineProductInfoModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
					onlineProductInfoModel.setLastUpdatedDate(new Date());
					onlineProductInfoModel.setLastApprovalDate(new Date());
					productInfoMapper.updateByPrimaryKey(onlineProductInfoModel);

					// productbarcode update(1,delete 2,insert)
					productBarcodeService.deleteProductBarcodeByProductId(onlineProductInfoModel.getId().toString());
					List<ProductBarcodeModel> productBarcodeModels = productBarcodeService
							.getProductBarcodeModelsByProductId(record.getId().toString());
					if (CollectionUtils.isNotEmpty(productBarcodeModels)) {
						for (ProductBarcodeModel productBarcodeModel : productBarcodeModels) {
							productBarcodeModel.setProductId(onlineProductInfoModel.getId());
							productBarcodeModel.setCreatedBy(sessionService.getCurrentUser().getUserId());
							productBarcodeModel.setCreatedDate(new Date());
							productBarcodeModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
							productBarcodeModel.setLastUpdatedDate(new Date());
							productBarcodeModelMapper.insertSelective(productBarcodeModel);
						}
					}

					// Map map2=new HashMap();
					// map2.put("productId", record.getId());
					// map2.put("imageType",
					// null);//ConstantUtil.PRODUCT_IMAGE_TYPE);
					// productimage
					productImagesModelMapper.deleteByProductId(onlineProductInfoModel.getId());
					// List<ProductImagesModel> productImagesModels =
					// productImagesModelMapper.getProductImagesByProductId(map2);
					if (CollectionUtils.isNotEmpty(productImagesModels)) {
						for (ProductImagesModel productImagesModel : productImagesModels) {
							productImagesModel.setProductId(onlineProductInfoModel.getId());
							productImagesModel.setCreatedBy(sessionService.getCurrentUser().getUserId());
							productImagesModel.setCreatedDate(new Date());
							productImagesModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
							productImagesModel.setLastUpdatedDate(new Date());
							productImagesModelMapper.insertSelective(productImagesModel);
						}
					}

				} else {
					// insert productcode
					// String pCode = retekService.generateSkuId(
					// record.getSupplierCode(),
					// record.getSupplierProductCode());
					// record.setProductCode(pCode);
					record.setLastApprovalDate(new Date());
					productInfoMapper.updateByPrimaryKey(record);
					//
					record.setProductCode(productCode);
					record.setVersion(ONLINE);
					int nextProductId = productInfoMapper.getMaxProductId();
					record.setId(new BigDecimal(nextProductId));
					record.setCreatedBy(sessionService.getCurrentUser().getUserId());
					record.setCreatedDate(new Date());
					record.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
					record.setLastUpdatedDate(new Date());
					productInfoMapper.insertSelective(record);
					// productbarcode
					List<ProductBarcodeModel> productBarcodeModels = productBarcodeService
							.getProductBarcodeModelsByProductId(record.getId().toString());
					if (CollectionUtils.isNotEmpty(productBarcodeModels)) {
						for (ProductBarcodeModel productBarcodeModel : productBarcodeModels) {
							productBarcodeModel.setProductId(new BigDecimal(nextProductId));
							productBarcodeModel.setCreatedBy(sessionService.getCurrentUser().getUserId());
							productBarcodeModel.setCreatedDate(new Date());
							productBarcodeModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
							productBarcodeModel.setLastUpdatedDate(new Date());
							productBarcodeModelMapper.insertSelective(productBarcodeModel);
						}
					}

					// Map map2=new HashMap();
					// map2.put("productId", record.getId());
					// map2.put("imageType", ConstantUtil.PRODUCT_IMAGE_TYPE);
					// productimage
					// List<ProductImagesModel> productImagesModels =
					// productImagesModelMapper.getProductImagesByProductId(map2);
					if (CollectionUtils.isNotEmpty(productImagesModels)) {
						for (ProductImagesModel productImagesModel : productImagesModels) {
							productImagesModel.setProductId(new BigDecimal(nextProductId));
							productImagesModel.setCreatedBy(sessionService.getCurrentUser().getUserId());
							productImagesModel.setCreatedDate(new Date());
							productImagesModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserId());
							productImagesModel.setLastUpdatedDate(new Date());
							productImagesModelMapper.insertSelective(productImagesModel);
						}
					}
				}
			}
		}
	}

	public boolean checkAutoApprove(ProductInfoVo productjson, ResponseData<ProductInfoVo> responseData) {
		boolean validateResult = false;
		if (productjson == null) {
			if (responseData != null) {
				responseData.add("product_productInfovo_null");
			}
			validateResult = false;
			return validateResult;
		}

		if (StringUtils.isNotEmpty(String.valueOf(productjson.getId()))) {

			ProductInfoModel productInfoModel = productInfoMapper.selectByPrimaryKey(productjson.getId());
			productInfoModel.setLongDescEn(StringUtil.repalceAllBrToNewLine(productInfoModel.getLongDescEn()));
			productInfoModel.setLongDescSc(StringUtil.repalceAllBrToNewLine(productInfoModel.getLongDescSc()));
			productInfoModel.setLongDescTc(StringUtil.repalceAllBrToNewLine(productInfoModel.getLongDescTc()));

			productInfoModel.setProductUsageEn(StringUtil.repalceAllBrToNewLine(productInfoModel.getProductUsageEn()));
			productInfoModel.setProductUsageSc(StringUtil.repalceAllBrToNewLine(productInfoModel.getProductUsageSc()));
			productInfoModel.setProductUsageTc(StringUtil.repalceAllBrToNewLine(productInfoModel.getProductUsageTc()));

			productInfoModel.setProductIngredientsEn(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getProductIngredientsEn()));
			productInfoModel.setProductIngredientsSc(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getProductIngredientsSc()));
			productInfoModel.setProductIngredientsTc(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getProductIngredientsTc()));

			productInfoModel.setProductWarningsEn(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getProductWarningsEn()));
			productInfoModel.setProductWarningsSc(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getProductWarningsSc()));
			productInfoModel.setProductWarningsTc(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getProductWarningsTc()));

			productInfoModel.setStorageConditionEn(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getStorageConditionEn()));
			productInfoModel.setStorageConditionSc(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getStorageConditionSc()));
			productInfoModel.setStorageConditionTc(StringUtil.repalceAllBrToNewLine(productInfoModel
					.getStorageConditionTc()));

			if (StringUtil.checkStringEquals(productInfoModel.getLongDescEn(), productjson.getLongDescEn())
					|| StringUtil.checkStringEquals(productInfoModel.getLongDescSc(), productjson.getLongDescSc())
					|| StringUtil.checkStringEquals(productInfoModel.getLongDescTc(), productjson.getLongDescTc())

					|| StringUtil.checkStringEquals(productInfoModel.getColorGroup(), productjson.getColorGroup())
					|| StringUtil.checkStringEquals(productInfoModel.getColorHexCode(), productjson.getColorHexCode())

					|| StringUtil.checkStringEquals(productInfoModel.getColorCode(), productjson.getColorCode())
					|| StringUtil.checkStringEquals(productInfoModel.getColorDesc(), productjson.getColorDesc())
					|| StringUtil.checkStringEquals(productInfoModel.getVariantColor(), productjson.getVariantColor())
					|| StringUtil.checkStringEquals(productInfoModel.getVariantSize(), productjson.getVariantSize())
					|| StringUtil.checkStringEquals(String.valueOf(productInfoModel.getOnlineDate()),
							String.valueOf(productjson.getOnlineDate()))
					|| StringUtil.checkStringEquals(String.valueOf(productInfoModel.getOfflineDate()),
							String.valueOf(productjson.getOfflineDate()))
					|| StringUtil.checkStringEquals(String.valueOf(productInfoModel.getUnitRetail()),
							String.valueOf(productjson.getUnitRetail()))

					|| StringUtil.checkBigDecimalEquals(productInfoModel.getDept(), productjson.getDept())
					|| StringUtil.checkBigDecimalEquals(productInfoModel.getClazz(), productjson.getClazz())
					|| StringUtil.checkBigDecimalEquals(productInfoModel.getSubClass(), productjson.getSubClass())
					|| StringUtil.checkStringEquals(productInfoModel.getEstoreCategory(),
							productjson.getEstoreCategory())
					|| StringUtil.checkStringEquals(productInfoModel.getDeliveryMode(), productjson.getDeliveryMode())
					|| StringUtil.checkBigDecimalEquals(productInfoModel.getSupplierLeadTime(),
							productjson.getSupplierLeadTime())
					|| StringUtil.checkStringEquals(String.valueOf(productInfoModel.getMinOrderQty()),
							productjson.getMinOrderQty())

					|| StringUtil.checkBigDecimalEquals(productInfoModel.getMaxReplenishmentLevel(),
							productjson.getMaxReplenishmentLevel())
					|| StringUtil.checkBigDecimalEquals(productInfoModel.getMinReplenishmentLevel(),
							productjson.getMinReplenishmentLevel())

					|| StringUtil.checkBigDecimalEquals(productInfoModel.getMinDeliverDate(),
							productjson.getMinDeliverDate())
					|| StringUtil.checkBigDecimalEquals(productInfoModel.getMaxDeliverDate(),
							productjson.getMaxDeliverDate())

					|| StringUtil.checkStringEquals(productInfoModel.getConsignmentType(),
							productjson.getConsignmentType())
					|| StringUtil.checkStringEquals(productInfoModel.getConsignmentCalBasis(),
							productjson.getConsignmentCalBasis())
					|| StringUtil.checkBigDecimalEquals(productInfoModel.getConsignmentRate(),
							productjson.getConsignmentRate())
					|| StringUtil.checkStringEquals(productInfoModel.getProductUsageEn(),
							productjson.getProductUsageEn())

					|| StringUtil.checkStringEquals(productInfoModel.getProductUsageTc(),
							productjson.getProductUsageTc())
					|| StringUtil.checkStringEquals(productInfoModel.getProductUsageSc(),
							productjson.getProductUsageSc())

					|| StringUtil.checkStringEquals(productInfoModel.getProductWarningsEn(),
							productjson.getProductWarningsEn())
					|| StringUtil.checkStringEquals(productInfoModel.getProductWarningsTc(),
							productjson.getProductWarningsTc())
					|| StringUtil.checkStringEquals(productInfoModel.getProductWarningsSc(),
							productjson.getProductWarningsSc())

					|| StringUtil.checkStringEquals(productInfoModel.getStorageConditionEn(),
							productjson.getStorageConditionEn())
					|| StringUtil.checkStringEquals(productInfoModel.getStorageConditionTc(),
							productjson.getStorageConditionTc())
					|| StringUtil.checkStringEquals(productInfoModel.getStorageConditionSc(),
							productjson.getStorageConditionSc())

					|| StringUtil.checkStringEquals(productInfoModel.getProductIngredientsEn(),
							productjson.getProductIngredientsEn())
					|| StringUtil.checkStringEquals(productInfoModel.getProductIngredientsTc(),
							productjson.getProductIngredientsTc())
					|| StringUtil.checkStringEquals(productInfoModel.getProductIngredientsSc(),
							productjson.getProductIngredientsSc())

					|| StringUtil.checkStringEquals(productInfoModel.getPackAge(), productjson.getPackAge())
					|| StringUtil.checkStringEquals(productInfoModel.getProductShelfLife(),
							productjson.getProductShelfLife())
					|| StringUtil.checkStringEquals(productInfoModel.getMinShelfLife(), productjson.getMinShelfLife())
					|| StringUtil.checkStringEquals(productInfoModel.getSmallExpensive(),
							productjson.getSmallExpensive())
					|| StringUtil.checkStringEquals(productInfoModel.getNutritionLabel(),
							productjson.getNutritionLabel())
					|| StringUtil.checkStringEquals(productInfoModel.getManufCountry(), productjson.getManufCountry())) {
				validateResult = true;
			}
		}
		return validateResult;
	}

	@Override
	public int updateByPrimaryKeySelective(ProductInfoModel record) {

		return productInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ProductInfoModel getProductInfoModelByProductCode(Map<String, Object> map) {
		return productInfoMapper.getProductInfoModelByProductCode(map);
	}

	@Override
	public void deleteProductByPId(String pIds) {
		if (StringUtils.isNotEmpty(pIds)) {
			String[] pStrings = StringUtil.getAllProductId(pIds);
			if (pStrings != null && pStrings.length > 0) {
				for (String str : pStrings) {
					if (StringUtils.isNotEmpty(str)) {
						ProductInfoModel productInfoModel = productInfoMapper.selectByPrimaryKey(new BigDecimal(str));
						if (productInfoModel.getBaseProductId() != null) {
							// delete barCode
							productBarcodeService.deleteProductBarcodeByProductId(str);
							// delete imgid
							productImagesService.deleteByProductId(new BigDecimal(str));
							// delete pid
							deleteProductByProductId(str);
						}
					}
				}
			}
		}

	}

	/**
	 * 使用 FileaHandle.resizeImage　产生6种类型size的图片 存于　upload_format_file_path
	 * gengerate filename= realFilename +"_"+ 1200Wx1200H +".jpeg"
	 */
	// private String separator = "_";
	// private String mime = "image/jpeg";
	private String mime = "jpeg";
	private String[] filenames = new String[] { "1200Wx1200H", "515Wx515H", "300Wx300H", "96Wx96H", "65Wx65H",
			"30Wx30H" };
	private Integer[] widths = new Integer[] { 1200, 515, 300, 96, 65, 30 };

	@Override
	public void uploadProductByPId(String pIds) throws IOException {
		if (StringUtils.isNotEmpty(pIds)) {
			String[] pStrings = StringUtil.getAllProductId(pIds);
			if (pStrings != null && pStrings.length > 0) {
				for (String str : pStrings) {
					if (StringUtils.isNotEmpty(str)) {

						// 取得相应的数据
						ProductInfoModel productInfoModel = selectByPrimaryKey(new BigDecimal(str));
						// 取得图片列表
						List<ProductImagesVo> imagesList = this.productImagesService.getProductImagesByProductId(
								new BigDecimal(str), ConstantUtil.PRODUCT_IMAGE_TYPE);

						// 图片时间格式转换为ISO8601
						List<ProductImagesVoISO8601> imagesListISO8601 = new ArrayList<ProductImagesVoISO8601>();
						for (ProductImagesVo productImagesVo : imagesList) {
							ProductImagesVoISO8601 imagesVoISO8601 = new ProductImagesVoISO8601();
							BeanUtils.copyProperties(productImagesVo, imagesVoISO8601);
							imagesListISO8601.add(imagesVoISO8601);
						}

						// 设置保存图片的地址
						// String outPath="/home/portal/product_upload_file2";
						String outPath = ResourceUtil.getSystemConfig().getProperty("upload_file_resizepath");

						if (productInfoModel != null) {
							boolean uploadFlag = uploadProductByProductId(productInfoModel, imagesListISO8601);

							LOG.info("upload flag---->:" + uploadFlag);
							try {

								// 对图片进行处理，设置成为不同的格式
								if (imagesList != null) {
									for (ProductImagesVo imagesVo : imagesList) {
										for (int i = 0; i < filenames.length; i++) {

											// 重设并保存图片

											String ext = "jpeg";
											String mediaCode = imagesVo.getFilePath();
											if (imagesVo.getFilePath().indexOf(".") > -1) {
												mediaCode = imagesVo.getFilePath().substring(0,
														imagesVo.getFilePath().lastIndexOf("."));
												ext = imagesVo.getFilePath().substring(
														imagesVo.getFilePath().lastIndexOf(".") + 1);

											}

											// 设置图片名称
											String newfilename = mediaCode + "_" + filenames[i].toLowerCase() + "."
													+ ext;

											FileHandle.resizeAndSetImage(imagesVo.getFilePath(), newfilename,
													(int) widths[i], (int) widths[i], mime, outPath);
										}
									}
								}
							} catch (Exception ex) {
								LOG.error(ex.getMessage(), ex);
							}

							if (uploadFlag) {
								LOG.info("upload product :" + productInfoModel.getProductCode() + " status->UPLOADED");
								productInfoModel.setStatus(UPLOADED);
								/** TEMP */
								// productInfoModel.setStatus(APPROVED);
								productInfoMapper.updateByPrimaryKey(productInfoModel);
							}
						}
					}
				}
			}
		}
	}

	// //getImage , resizeImage , setImage
	// public void resizeAndSetImage(String fileName , String newfilename ,int
	// newWidth , int newHight , String mime , String outPath)
	// throws IOException{
	// //getImage
	// byte[] imageByte=getImage(fileName);
	// //ResizeImage
	// byte[] resizeImageByte=FileHandle.resizeImage(imageByte, newWidth,
	// newHight, mime);
	// //图片保存到硬盘
	// FileHandle.imageToDisk(resizeImageByte , newfilename , outPath);
	// }
	//
	// //getImage
	// public byte[] getImage(String filename) throws IOException{
	// //取得图片的地址
	// String path =
	// ResourceUtil.getSystemConfig().getProperty("upload_file_path");
	// byte[] imageByte;
	// imageByte = FileUtils.readFileToByteArray(new File(path + File.separator
	// + filename));
	//
	// return imageByte;
	// }

	// //resizeImage
	// public byte[] resizeImage(byte[] imageByte , int newWidth , int newHight
	// , String mime ) throws IOException{
	//
	// try(InputStream is = new ByteArrayInputStream(imageByte);
	// ByteArrayOutputStream os = new ByteArrayOutputStream();) {
	//
	// //获取图片Buffer
	// BufferedImage prevImage = ImageIO.read(is);
	// //取得图片
	// Image image = prevImage.getScaledInstance(newWidth, newHight,
	// Image.SCALE_SMOOTH);
	// //制作画板
	// BufferedImage bufferImage = new BufferedImage(newWidth, newHight,
	// BufferedImage.TYPE_INT_BGR);
	// //画笔
	// Graphics graphics = bufferImage.getGraphics();
	// //绘图
	// graphics.drawImage(image, 0, 0, null);
	// graphics.dispose();
	// // ImageIO.write(bufferImage, format, os);
	// ImageIO.write(bufferImage, mime, os);
	// byte[] resizeImage = os.toByteArray();
	//
	// // os.flush();
	// // is.close();
	// // os.close();
	//
	// return resizeImage;
	// } catch (IOException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	//
	// //imageToDisk(image , imageName , path)
	// public void imageToDisk(byte[] resizeImageByte , String newfilename ,
	// String outPath) throws IOException{
	// try(
	// ByteArrayInputStream inputStream = new
	// ByteArrayInputStream(resizeImageByte);
	// FileOutputStream fos = new
	// FileOutputStream(outPath+File.separator+newfilename);
	// BufferedOutputStream bos = new BufferedOutputStream(fos);
	// )
	// {
	// byte[] buffer = new byte[1024];
	// int len;
	//
	// while ((len = inputStream.read(buffer)) >= 0) {
	// bos.write(buffer, 0, len);
	// }
	// bos.flush();
	// bos.close();
	// inputStream.close();
	//
	// }catch(IOException e){
	// e.printStackTrace();
	// }
	// }

	@Override
	public int editSettingSyncProduct(ProductInfoModel record) {
		return productInfoMapper.editSettingSyncProduct(record);
	}

	@Override
	public List<ProductExportModel> exportProductList(String onlineUpdatedInd, String supplier,
			String supplierProductCode, String productCode, String status, String deliveryMode, String shortDescEn,
			UserVo userVo) {

		ProductParameterVo parameterVo = getProductParameterVo(onlineUpdatedInd, supplier, supplierProductCode,
				productCode, status, deliveryMode, shortDescEn, userVo);

		return productInfoMapper.exportProductList(parameterVo);

	}

	@Override
	public void batchUploadProduct(ProductInfoVo productInfoVo, ResponseData<ProductInfoVo> responseData, UserVo userVo)
			throws ServiceException {
		boolean productFlag = validateProduct(productInfoVo, responseData);
		boolean barcodeFlag = productBarcodeService.validateProductBarCode(productInfoVo, responseData);
		if (productFlag && barcodeFlag) {
			boolean isEdit = false;
			boolean isAdd = false;
			if (productInfoVo.getId() != null) {
				isEdit = updateProduct(productInfoVo, responseData, userVo);
			} else {
				isAdd = insertProduct(productInfoVo, responseData, userVo);
			}

			if (isAdd) {
				responseData.add("product_added_info");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			} else if (isEdit) {
				responseData.add("product_updated_info");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			} else {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		}
	}

	@Override
	public List<ResponseData<ProductInfoVo>> generateProductForSu(Sheet sheet) throws SystemException, IOException,
			ServiceException {

		UserVo userVo = sessionService.getCurrentUser();
		List<ResponseData<ProductInfoVo>> responseDataList = new ArrayList<>();
		int t = 0;
		Map variantTypeMap = new HashMap();
		Map brandMap = new HashMap();

		for (Row workbookRow : sheet) {
			if (t == 0) {
				t++;
				continue;
			}
			String rowNum = workbookRow.getRowNum() + 1 + "";
			List<String> row = new ArrayList();
			boolean f = false;
			for (int n = 0; n < 72; n++) {
				// row.add(FileHandle.getCellValue(workbookRow.getCell(n)));
				try {
					row.add(FileHandle.getCellValue(workbookRow.getCell(n)));
				} catch (Exception ex) {

					ResponseData<ProductInfoVo> responseData = (ResponseData<ProductInfoVo>) responseDataService
							.getReturnData(ProductInfoVo.class);
					responseData.setHook(rowNum);
					responseData.add("upload_file_get_cell_failed");
					String[] param = { FileHandle.getCellByNum((n + 1)), ex.getMessage() };
					responseData.putMessagesParamArray("upload_file_get_cell_failed", param);

					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData);
					f = true;
					continue;
				}

			}
			if (f)
				return responseDataList;

			if (CollectionUtils.isNotEmpty(row)) {

				// checking
				String key = row.get(0) + "_" + row.get(1);
				String variantOn = "";
				if (StringUtils.isNotEmpty(row.get(12))) {
					variantOn = row.get(12);
				}

				String brand = "";
				if (StringUtils.isNotEmpty(row.get(11))) {
					brand = row.get(11);
				}

				if (!variantTypeMap.containsKey(key)) {
					variantTypeMap.put(key, variantOn);
				}

				if (!brandMap.containsKey(key)) {
					brandMap.put(key, brand);
				}
				// checking

			}
		}

		t = 0;
		Map variantTypeIssueMap = new HashMap();
		Map brandIssueMap = new HashMap();
		for (Row workbookRow : sheet) {
			List<String> row = new ArrayList();
			for (int n = 0; n < 72; n++) {
				row.add(FileHandle.getCellValue(workbookRow.getCell(n)));
			}

			if (t == 0) {
				t++;
				continue;
			}

			if (CollectionUtils.isNotEmpty(row)) {

				// checking
				String key = row.get(0) + "_" + row.get(1);
				String variantOn = "";
				if (StringUtils.isNotEmpty(row.get(12))) {
					variantOn = row.get(12);
				}

				String brand = "";
				if (StringUtils.isNotEmpty(row.get(11))) {
					brand = row.get(11);
				}

				String baseKey = row.get(2) + "_" + row.get(3);

				if (variantTypeMap.get(baseKey) != null && !variantOn.equals(variantTypeMap.get(baseKey))) {
					variantTypeIssueMap.put(key, key);
					if (StringUtils.isNotEmpty(row.get(2)) || StringUtils.isNotEmpty(row.get(3))) {
						variantTypeIssueMap.put(baseKey, baseKey);
					}
				}

				if (brandMap.get(baseKey) != null && !brand.equals(brandMap.get(baseKey))) {
					brandIssueMap.put(key, key);
					if (StringUtils.isNotEmpty(row.get(2)) || StringUtils.isNotEmpty(row.get(3))) {
						brandIssueMap.put(baseKey, baseKey);
					}
				}
				// checking

			}
		}

		t = 0;
		for (Row workbookRow : sheet) {

			String rowNum = workbookRow.getRowNum() + 1 + "";
			List<String> row = new ArrayList();
			for (int n = 0; n < 72; n++) {
				row.add(FileHandle.getCellValue(workbookRow.getCell(n)));
			}

			if (t == 0) {
				t++;
				continue;
			}

			if (CollectionUtils.isNotEmpty(row)) {
				ProductInfoVo productInfoVo = new ProductInfoVo();
				SupplierVo supplierModel = supplierService.selectByPrimaryKey(userVo.getSupplierId());
				boolean isUpdated = false;

				ResponseData<ProductInfoVo> responseData1 = (ResponseData<ProductInfoVo>) responseDataService
						.getReturnData(ProductInfoVo.class);
				responseData1.setHook(rowNum);

				if (StringUtils.isEmpty(row.get(1))) {
					responseData1.add("product_save_supplierproductcode");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData1);
					continue;
				} else {
					Map<String, Object> map = new HashMap<>();
					map.put("supplierCode", userVo.getSupplierId());
					map.put("supplierProductCode", row.get(1));
					ProductInfoModel productInfoModel = productInfoMapper.getProductInfoModelBySupplierProductCode(map);

					if (null == productInfoModel) {
						isUpdated = false;
						productInfoVo.setSupplierProductCode(row.get(1));
					} else {
						isUpdated = true;
						BeanUtils.copyProperties(productInfoModel, productInfoVo);
						// productInfoVo.setBrandCode(String.valueOf(productInfoModel.getBrandCode()));
						productInfoVo.setDailyInventory(String.valueOf(productInfoModel.getDailyInventory()));
						productInfoVo.setMinOrderQty(String.valueOf(productInfoModel.getMinOrderQty()));
					}
				}

				// checking
				String key = row.get(0) + "_" + row.get(1);

				if (variantTypeIssueMap.containsKey(key)) {
					responseData1.add("product_variantType_mismatch");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData1);
					continue;
				}

				if (brandIssueMap.containsKey(key)) {
					responseData1.add("product_brand_mismatch");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData1);
					continue;
				}

				if (StringUtils.isNotEmpty(row.get(2)) || StringUtils.isNotEmpty(row.get(3))) {
					String baseKey = row.get(2) + "_" + row.get(3);
					if (variantTypeIssueMap.containsKey(baseKey)) {
						responseData1.add("product_variantType_mismatch");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseDataList.add(responseData1);
						continue;
					}

					if (brandIssueMap.containsKey(baseKey)) {
						responseData1.add("product_brand_mismatch");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseDataList.add(responseData1);
						continue;
					}
				}
				// checking

				if (isUpdated) {
					if (StringUtils.isNotEmpty(row.get(0))) {
						Map<String, Object> map1 = new HashMap<>();
						map1.put("productCode", row.get(0));
						map1.put(VERSION, STAGING);
						ProductInfoModel productInfoModel = getProductInfoModelByProductCode(map1);
						if (null == productInfoModel) {
							responseData1.add("product_sku_not_found");
							responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseDataList.add(responseData1);
							continue;
						} else {
							if (!productInfoModel.getSupplierCode().equals(userVo.getSupplierId())) {
								responseData1.add("supplier_code_mismatch");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								responseDataList.add(responseData1);
								continue;
							}
							if (!productInfoModel.getSupplierProductCode().equals(row.get(1))) {
								responseData1.add("product_sku_mismatch_with_product_vpn");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								responseDataList.add(responseData1);
								continue;
							}
							if (StringUtil.checkStringEquals(productInfoModel.getBaseProductCode(), row.get(2))) {
								responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								responseDataList.add(responseData1);
								continue;
							}
						}
					}
					if (StringUtils.isEmpty(productInfoVo.getProductCode())) {
						dealWithProductFieldsForSu(userVo, row, productInfoVo, responseData1, supplierModel);
					} else {
						if (!CONSIGNMENT.equals(row.get(24))) {
							if (StringUtils.isNotEmpty(row.get(29))) {
								if (StringUtils.isNumeric(row.get(29))) {
									productInfoVo.setDailyInventory(row.get(29));
								} else {
									responseData1.add("daily_inventory_should_be_numeric");
									responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								}
							} else {
								responseData1.add("product_save_dailyinventory");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							}
						}

						if (StringUtils.isNotEmpty(row.get(71))) {
							if ("Home delivery only".equals(row.get(71))) {
								productInfoVo.setPnsOnlineDeliveryType("1");
							} else if ("Click & Collect only".equals(row.get(71))) {
								productInfoVo.setPnsOnlineDeliveryType("2");
							} else if ("Both Home Delivery & Click & Collect".equals(row.get(71))) {
								productInfoVo.setPnsOnlineDeliveryType("3");
							}
						}

						// eStore category
						if (StringUtils.isNotEmpty(row.get(23))) {
							String value = row.get(23);
							if (value.indexOf(STRING2) != -1) {
								String[] valueArr = value.split(STRING2);
								Map<String, Object> map3 = new HashMap<>();
								map3.put("supplierId", userVo.getSupplierId());
								map3.put("categoryId", valueArr[0]);
								SupplierCategoryModel supplierCategoryModel = selectBySupplierCategoryId(map3);
								if (null == supplierCategoryModel) {
									responseData1.add("product_the_category_not_exists");
									responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								} else {
									productInfoVo.setEstoreCategory(valueArr[0]);
								}
							} else {
								responseData1.add("product_category_format_err");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							}
						}
					}
				} else {
					productInfoVo.setSupplierCode(userVo.getSupplierId());

					if (StringUtils.isNotEmpty(row.get(0))) {
						responseData1.add("supplier_product_code_not_found");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseDataList.add(responseData1);
						continue;
					}

					dealWithProductFieldsForSu(userVo, row, productInfoVo, responseData1, supplierModel);
				}

				if (StringUtils.isNotEmpty(row.get(2)) && StringUtils.isEmpty(row.get(3))) {
					responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData1);
					continue;
				} else if (StringUtils.isEmpty(row.get(2)) && StringUtils.isNotEmpty(row.get(3))) {
					if (!row.get(1).equals(row.get(3))) {
						Map<String, Object> map3 = new HashMap<>();
						map3.put(SUPPLIER_CODE, userVo.getSupplierId());
						map3.put(SUPPLIER_PRODUCT_CODE, row.get(3));
						ProductInfoModel productInfoModel3 = getProductInfoModelBySupplierProductCode(map3);
						if (null != productInfoModel3) {
							productInfoVo.setBaseProductId(productInfoModel3.getId());
						} else {
							responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
							responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseDataList.add(responseData1);
							continue;
						}
					}
				} else if (StringUtils.isNotEmpty(row.get(2)) && StringUtils.isNotEmpty(row.get(3))) {
					Map<String, Object> map2 = new HashMap<>();
					map2.put("productCode", row.get(2));
					map2.put(VERSION, STAGING);
					ProductInfoModel productInfoModel2 = getProductInfoModelByProductCode(map2);
					if (null != productInfoModel2) {
						if (!productInfoModel2.getSupplierProductCode().equals(row.get(3))) {
							responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
							responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseDataList.add(responseData1);
							continue;
						} else if (!productInfoModel2.getSupplierCode().equals(userVo.getSupplierId())) {
							responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
							responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseDataList.add(responseData1);
							continue;
						} else {
							productInfoVo.setBaseProductId(productInfoModel2.getId());
						}
					} else {
						responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseDataList.add(responseData1);
						continue;
					}
				}

				// if (StringUtils.isNotEmpty(row.get(5))) {
				productInfoVo.setShortDescEn(StringUtil.repalceMultipleReturnLine(row.get(5)));
				// }

				// if (StringUtils.isNotEmpty(row.get(6))) {
				productInfoVo.setShortDescTc(StringUtil.repalceMultipleReturnLine(row.get(6)));
				// }

				// if (StringUtils.isNotEmpty(row.get(7))) {
				productInfoVo.setShortDescSc(StringUtil.repalceMultipleReturnLine(row.get(7)));
				// }

				// if (StringUtils.isNotEmpty(row.get(8))) {
				productInfoVo.setLongDescEn(StringUtil.repalceMultipleReturnLine(row.get(8)));
				// }

				// if (StringUtils.isNotEmpty(row.get(9))) {
				productInfoVo.setLongDescTc(StringUtil.repalceMultipleReturnLine(row.get(9)));
				// }

				// if (StringUtils.isNotEmpty(row.get(10))) {
				productInfoVo.setLongDescSc(StringUtil.repalceMultipleReturnLine(row.get(10)));
				// }

				if (StringUtils.isNotEmpty(row.get(18))) {
					String offlineDateStr = row.get(18);
					if (DateUtils.isValidDate(offlineDateStr)) {
						productInfoVo.setOfflineDate(DateUtils.parseDateStr(offlineDateStr, DateUtils.DATE_FORMAT_6));
					} else {
						responseData1.add("offline_date_must_be_date_format");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setOfflineDate(null);
				}

				if (StringUtils.isNotEmpty(row.get(20))) {
					String value = row.get(20);
					if (value.indexOf(STRING) != -1) {
						String[] valueArr1 = value.split(STRING);
						productInfoVo.setStandardUom(valueArr1[0]);
					} else {
						responseData1.add("standard_uom_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setStandardUom("");
				}

				if (StringUtils.isNotEmpty(row.get(33))) {
					if (ValidateUtils.validateNumber(row.get(33))) {
						productInfoVo.setConsignmentRate(new BigDecimal(row.get(33)));
					} else {
						responseData1.add("consignment_rate_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setConsignmentRate(null);
				}

				// if (StringUtils.isNotEmpty(row.get(34))) {
				productInfoVo.setSizeDesc(row.get(34));
				// }

				if (StringUtils.isNotEmpty(row.get(35))) {
					if (StringUtils.isNumeric(row.get(35))) {
						productInfoVo.setCasePackInner(new BigDecimal(row.get(35)));
					} else {
						responseData1.add("case_pack_(inner)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCasePackInner(null);
				}

				if (StringUtils.isNotEmpty(row.get(36))) {
					if (StringUtils.isNumeric(row.get(36))) {
						productInfoVo.setCasePackCase(new BigDecimal(row.get(36)));
					} else {
						responseData1.add("case_pack_(case)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCasePackCase(null);
				}

				// Dim Unit
				if (StringUtils.isNotEmpty(row.get(37))) {
					if (row.get(37).indexOf(STRING) != -1) {
						String[] valueArr = row.get(37).split(STRING);
						productInfoVo.setDimUnit(valueArr[0]);
					} else {
						responseData1.add("product_dimunit_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setDimUnit("");
				}

				if (StringUtils.isNotEmpty(row.get(38))) {
					if (ValidateUtils.validateNumber(row.get(38))) {
						productInfoVo.setProductDimHeight(new BigDecimal(row.get(38)));
					} else {
						responseData1.add("product_dimension_(height)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setProductDimHeight(null);
				}

				if (StringUtils.isNotEmpty(row.get(39))) {
					if (ValidateUtils.validateNumber(row.get(39))) {
						productInfoVo.setProductDimWidth(new BigDecimal(row.get(39)));
					} else {
						responseData1.add("product_dimension_(width)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setProductDimWidth(null);
				}

				if (StringUtils.isNotEmpty(row.get(40))) {
					if (ValidateUtils.validateNumber(row.get(40))) {
						productInfoVo.setProductDimLength(new BigDecimal(row.get(40)));
					} else {
						responseData1.add("product_dimension_(length)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setProductDimLength(null);
				}

				if (StringUtils.isNotEmpty(row.get(41))) {
					if (ValidateUtils.validateNumber(row.get(41))) {
						productInfoVo.setShippingDimHeight(new BigDecimal(row.get(41)));
					} else {
						responseData1.add("shipping_dimension_(height)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setShippingDimHeight(null);
				}

				if (StringUtils.isNotEmpty(row.get(42))) {
					if (ValidateUtils.validateNumber(row.get(42))) {
						productInfoVo.setShippingDimWidth(new BigDecimal(row.get(42)));
					} else {
						responseData1.add("shipping_dimension_(width)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setShippingDimWidth(null);
				}

				if (StringUtils.isNotEmpty(row.get(43))) {
					if (ValidateUtils.validateNumber(row.get(43))) {
						productInfoVo.setShippingDimLength(new BigDecimal(row.get(43)));
					} else {
						responseData1.add("shipping_dimension_(length)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setShippingDimLength(null);
				}

				if (StringUtils.isNotEmpty(row.get(44))) {
					if (ValidateUtils.validateNumber(row.get(44))) {
						productInfoVo.setCaseDimHeight(new BigDecimal(row.get(44)));
					} else {
						responseData1.add("case_dimension_(height)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCaseDimHeight(null);
				}

				if (StringUtils.isNotEmpty(row.get(45))) {
					if (ValidateUtils.validateNumber(row.get(45))) {
						productInfoVo.setCaseDimWidth(new BigDecimal(row.get(45)));
					} else {
						responseData1.add("case_dimension_(width)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCaseDimWidth(null);
				}

				if (StringUtils.isNotEmpty(row.get(46))) {
					if (ValidateUtils.validateNumber(row.get(46))) {
						productInfoVo.setCaseDimLength(new BigDecimal(row.get(46)));
					} else {
						responseData1.add("case_dimension_(length)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCaseDimLength(null);
				}

				// weight unit
				if (StringUtils.isNotEmpty(row.get(47))) {
					if (row.get(47).indexOf(STRING) != -1) {
						String[] valueArr = row.get(47).split(STRING);
						productInfoVo.setWeightUnit(valueArr[0]);
					} else {
						responseData1.add("product_weightunit_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setWeightUnit("");
				}

				if (StringUtils.isNotEmpty(row.get(48))) {
					if (ValidateUtils.validateNumber(row.get(48))) {
						productInfoVo.setGrossWeight(new BigDecimal(row.get(48)));
					} else {
						responseData1.add("gross_weight_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setGrossWeight(null);
				}

				if (StringUtils.isNotEmpty(row.get(49))) {
					if (ValidateUtils.validateNumber(row.get(49))) {
						productInfoVo.setShippingWeight(new BigDecimal(row.get(49)));
					} else {
						responseData1.add("shipping_weight_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setShippingWeight(null);
				}

				ProductBarCode productBarCode = new ProductBarCode();
				List<ProductBarCode> productBarCodeList = new ArrayList<>();

				if (StringUtils.isNotEmpty(row.get(50))) {

					LovModel lovModel = lovService
							.getLovModelByLovIdDesc(LovType.ITEM_NUM_TYPE.getLovId(), row.get(50));
					if (lovModel != null) {
						productBarCode.setItemNumType(lovModel.getLovValue());
					} else {
						responseData1.add("itemnumtype_value_incorrect");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (isUpdated) {
					productBarCode.setBarcodeNum(row.get(51));
					Map<String, Object> map1 = new HashMap<>();
					map1.put("barcodenum", row.get(51));
					map1.put(VERSION, "STAGING");
					List<ProductBarcodeModel> productBarCodeModelList = productBarcodeService
							.getProductBarcodeModelByBarcodeNum(map1);
					for (ProductBarcodeModel productBarcodeModel : productBarCodeModelList) {
						productBarCode.setProductCode(String.valueOf(productBarcodeModel.getProductId()));
						productBarCode.setId(productBarcodeModel.getId());
					}
				} else {
					if (StringUtils.isNotEmpty(row.get(51))) {
						productBarCode.setBarcodeNum(row.get(51));
					}
				}

				productBarCodeList.add(productBarCode);
				productInfoVo.setBarcodeList(productBarCodeList);

				if (StringUtils.isNotEmpty(row.get(52))) {
					String value = row.get(52);
					if (value.equalsIgnoreCase(WITH_NUTRITION_LABEL_ON_PACKAGE)) {
						productInfoVo.setNutritionLabel(W);
					} else if (value.equalsIgnoreCase(NUTRITION_LABELING_EXEMPTION)) {
						productInfoVo.setNutritionLabel(E);
					} else {
						productInfoVo.setNutritionLabel(N);
					}
				} else {
					productInfoVo.setNutritionLabel("");
				}

				// if (StringUtils.isNotEmpty(row.get(53))) {
				productInfoVo.setProductUsageEn(StringUtil.repalceMultipleReturnLine(row.get(53)));
				// }

				// if (StringUtils.isNotEmpty(row.get(54))) {
				productInfoVo.setProductUsageTc(StringUtil.repalceMultipleReturnLine(row.get(54)));
				// }

				// if (StringUtils.isNotEmpty(row.get(55))) {
				productInfoVo.setProductUsageSc(StringUtil.repalceMultipleReturnLine(row.get(55)));
				// }

				// if (StringUtils.isNotEmpty(row.get(56))) {
				productInfoVo.setProductWarningsEn(StringUtil.repalceMultipleReturnLine(row.get(56)));
				// }

				// if (StringUtils.isNotEmpty(row.get(57))) {
				productInfoVo.setProductWarningsTc(StringUtil.repalceMultipleReturnLine(row.get(57)));
				// }

				// if (StringUtils.isNotEmpty(row.get(58))) {
				productInfoVo.setProductWarningsSc(StringUtil.repalceMultipleReturnLine(row.get(58)));
				// }

				// if (StringUtils.isNotEmpty(row.get(59))) {
				productInfoVo.setStorageConditionEn(StringUtil.repalceMultipleReturnLine(row.get(59)));
				// }

				// if (StringUtils.isNotEmpty(row.get(60))) {
				productInfoVo.setStorageConditionTc(StringUtil.repalceMultipleReturnLine(row.get(60)));
				// }

				// if (StringUtils.isNotEmpty(row.get(61))) {
				productInfoVo.setStorageConditionSc(StringUtil.repalceMultipleReturnLine(row.get(61)));
				// }

				// if (StringUtils.isNotEmpty(row.get(62))) {
				productInfoVo.setProductIngredientsEn(StringUtil.repalceMultipleReturnLine(row.get(62)));
				// }

				// if (StringUtils.isNotEmpty(row.get(63))) {
				productInfoVo.setProductIngredientsTc(StringUtil.repalceMultipleReturnLine(row.get(63)));
				// }

				// if (StringUtils.isNotEmpty(row.get(64))) {
				productInfoVo.setProductIngredientsSc(StringUtil.repalceMultipleReturnLine(row.get(64)));
				// }

				if (StringUtils.isNotEmpty(row.get(65))) {
					LovModel lovModel = lovService.getLovModelByLovIdDesc(LovType.PACKED_IN.getLovId(), row.get(65));
					if (null == lovModel) {
						responseData1.add("product_manufcountry_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					} else {
						productInfoVo.setManufCountry(lovModel.getLovValue());
					}
				} else {
					productInfoVo.setManufCountry("");
				}

				if (StringUtils.isNotEmpty(row.get(66))) {
					LovModel lovModel = lovService.getLovModelByLovIdDesc(LovType.PACKAGE.getLovId(), row.get(66));
					if (null == lovModel) {
						responseData1.add("product_package_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					} else {
						productInfoVo.setPackAge(lovModel.getLovValue());
					}
				} else {
					productInfoVo.setPackAge("");
				}

				// if (StringUtils.isNotEmpty(row.get(67))) {
				productInfoVo.setProductShelfLife(row.get(67));
				// }

				// if (StringUtils.isNotEmpty(row.get(68))) {
				productInfoVo.setMinShelfLife(row.get(68));
				// }

				// if (StringUtils.isNotEmpty(row.get(70))) {
				productInfoVo.setShippingInfo(StringUtil.repalceMultipleReturnLine(row.get(70)));
				// }

				if (responseData1.getErrorList().isEmpty()) {
					batchUploadProduct(productInfoVo, responseData1, userVo);
				}
				responseData1.setReturnData(productInfoVo);
				responseDataList.add(responseData1);
			}
		}
		return responseDataList;
	}

	private void dealWithProductFieldsForSu(UserVo userVo, List<String> row, ProductInfoVo productInfoVo,
			ResponseData<ProductInfoVo> responseData1, SupplierVo supplierModel) {
		productInfoVo.setConsignmentCalBasis("N");

		// brand
		if (StringUtils.isNotEmpty(row.get(11))) {
			String brand = row.get(11);
			List<BrandModel> brandModelList = new ArrayList<BrandModel>();
			BrandModel brandM = brandService.getBrandModelByDescEn(row.get(11));
			brandModelList = brandService.getBrandsBySupplierId(userVo.getSupplierId());
			brandModelList.add(brandM);
			if (CollectionUtils.isNotEmpty(brandModelList)) {
				// BrandModel brandObj = new BrandModel();
				// boolean flag = false;
				for (BrandModel brandModel : brandModelList) {
					if (brand != null && brandModel != null && brandModel.getDescEn() != null
							&& String.valueOf(brandModel.getDescEn()).equals(brand)) {
						// flag = true;
						// brandObj = brandModel;
						break;
					}
				}
				// if (flag) {
				// productInfoVo.setBrandCode(String.valueOf(brandObj.getBrandCode()));
				// } else {
				// responseData1.add("product_the_brand_not_exists");
				// responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				// }
			} else {
				responseData1.add("product_brand_is_null");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setBrandCode("");
		}

		// eStore category
		if (StringUtils.isNotEmpty(row.get(23))) {
			String value = row.get(23);
			if (value.indexOf(STRING2) != -1) {
				String[] valueArr = value.split(STRING2);
				Map<String, Object> map3 = new HashMap<>();
				map3.put("supplierId", userVo.getSupplierId());
				map3.put("categoryId", valueArr[0]);
				SupplierCategoryModel supplierCategoryModel = selectBySupplierCategoryId(map3);
				if (null == supplierCategoryModel) {
					responseData1.add("product_the_category_not_exists");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				} else {
					productInfoVo.setEstoreCategory(valueArr[0]);
				}
			} else {
				responseData1.add("product_category_format_err");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setEstoreCategory("");
		}

		if (StringUtils.isNotEmpty(row.get(12))) {
			productInfoVo.setVariantOn(row.get(12));
		} else {
			productInfoVo.setVariantOn("");
		}

		if (StringUtils.isNotEmpty(row.get(13))) {
			String colorGroup = row.get(13);
			if (colorGroup.indexOf(STRING2) != -1) {
				String[] colorGroupArr = colorGroup.split(STRING2);
				productInfoVo.setColorGroup(colorGroupArr[0]);
			} else {
				responseData1.add("colorgroup_value_incorrect");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setColorGroup("");
		}

		productInfoVo.setColorHexCode(row.get(14));

		productInfoVo.setColorCode(row.get(15));

		productInfoVo.setColorDesc(row.get(16));

		if (StringUtils.isNotEmpty(row.get(21))) {
			LovModel lovModel = lovService.getLovModelByLovIdDesc("404", row.get(21));
			if (lovModel != null) {
				productInfoVo.setOriginCountry(lovModel.getLovValue());
			} else {
				responseData1.add("origin_country_value_incorrect");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setOriginCountry("");
		}

		// dept,class,subclass
		if (StringUtils.isNotEmpty(row.get(22))) {
			String value = row.get(22);
			if (value.indexOf(STRING2) != -1) {
				String[] valueArr1 = value.split(STRING2);
				if (valueArr1[0].indexOf(STRING) != -1) {
					String[] valueArr2 = valueArr1[0].split(STRING);
					if (valueArr2 != null && valueArr2.length >= 3 && StringUtils.isNotEmpty(valueArr2[0])
							&& StringUtils.isNotEmpty(valueArr2[1]) && StringUtils.isNotEmpty(valueArr2[2])
							&& StringUtils.isNumeric(valueArr2[0]) && StringUtils.isNumeric(valueArr2[1])
							&& StringUtils.isNumeric(valueArr2[2])) {
						DeptModel deptModel = deptService.getDeptByDeptId(new BigDecimal(valueArr2[0]));
						if (null != deptModel) {
							productInfoVo.setDept(deptModel.getId());
							Map<String, Object> classMap = new HashMap<>();
							classMap.put("deptId", deptModel.getId());
							classMap.put("classId", valueArr2[1]);
							ClassModel classModel = classService.getClassByDeptIdClassId(classMap);
							if (null != classModel) {
								productInfoVo.setClazz(classModel.getId());
								Map<String, Object> subClassMap = new HashMap<>();
								subClassMap.put("classId", classModel.getId());
								subClassMap.put("subClassId", valueArr2[2]);
								SubClassModel subClassModel = subClassService
										.getSubClassByClassIdSubClassId(subClassMap);
								if (null != subClassModel) {
									productInfoVo.setSubClass(subClassModel.getId());
									if (StringUtils.isEmpty(productInfoVo.getEstoreCategory())) {
										productInfoVo.setEstoreCategory(subClassModel.getEcategroyId());
									}
								}
							}
						}
					} else {
						responseData1.add("product_dept_class_subclass_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}

				} else {
					responseData1.add("product_dept_class_subclass_format_err");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}
			} else {
				responseData1.add("product_dept_class_subclass_format_err");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setDept(null);
			productInfoVo.setClazz(null);
			productInfoVo.setSubClass(null);
		}

		if (StringUtils.isNotEmpty(row.get(19))) {
			if (ValidateUtils.validateNumber(row.get(19))) {
				productInfoVo.setUnitRetail(new BigDecimal(row.get(19)));
				String smallExpensive = ResourceUtil.getSystemConfig().getProperty("small_expensive");
				if (smallExpensive == null || StringUtils.isEmpty(smallExpensive)) {
					responseData1.add("product_save_small_expensive_err");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				} else if (!StringUtils.isNumeric(smallExpensive)) {
					responseData1.add("product_save_small_expensive_format_err");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				} else {
					if (Double.parseDouble(row.get(19)) > Double.parseDouble(smallExpensive)) {
						productInfoVo.setSmallExpensive(Y);
					} else {
						String value = row.get(69);
						if (StringUtils.isNotEmpty(value)) {
							if (value.equalsIgnoreCase(YES)) {
								productInfoVo.setSmallExpensive(Y);
							} else {
								productInfoVo.setSmallExpensive(N);
							}
						} else {
							productInfoVo.setSmallExpensive(N);
						}
					}
				}
			} else {
				responseData1.add("unit_retail_should_be_numeric");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setUnitRetail(null);
		}

		if (StringUtils.isNotEmpty(row.get(24))) {
			if (SUPPLIER_DIRECT_DELIVERY.equals(row.get(24))) {
				productInfoVo.setDeliveryMode("D");
				productInfoVo.setConsignmentType(C);
				productInfoVo.setSupplierLeadTime(null);
				productInfoVo.setMaxReplenishmentLevel(null);
				productInfoVo.setMinReplenishmentLevel(null);
				productInfoVo.setMinDeliverDate(supplierModel.getMinDeliveryDay());
				productInfoVo.setMaxDeliverDate(supplierModel.getMaxDeliveryDay());
				productInfoVo.setMinOrderQty(null);
				productInfoVo.setPnsOnlineDeliveryType("1");
				String productDefaultOnlineDate = ResourceUtil.getSystemConfig().getProperty(
						"product.default.onlineDate");
				productInfoVo.setOnlineDate(DateUtils.parseDateStr(productDefaultOnlineDate, DateUtils.DATE_FORMAT_5));
				if (StringUtils.isNotEmpty(row.get(29))) {
					if (StringUtils.isNumeric(row.get(29))) {
						productInfoVo.setDailyInventory(row.get(29));
					} else {
						responseData1.add("daily_inventory_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					responseData1.add("product_save_dailyinventory");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}

			} else if (CONSIGNMENT_VIA_WAREHOUSE.equals(row.get(24))) {
				productInfoVo.setDeliveryMode(W);
				productInfoVo.setConsignmentType(W);
				productInfoVo.setMinDeliverDate(null);
				productInfoVo.setMaxDeliverDate(null);
				productInfoVo.setMaxReplenishmentLevel(null);
				productInfoVo.setMinReplenishmentLevel(null);
				productInfoVo.setMinOrderQty("1");
				productInfoVo.setSupplierLeadTime(supplierModel.getWarehouseDeliLeadTime());
				String productDefaultOnlineDate = ResourceUtil.getSystemConfig().getProperty(
						"product.default.onlineDate");
				productInfoVo.setOnlineDate(DateUtils.parseDateStr(productDefaultOnlineDate, DateUtils.DATE_FORMAT_5));

				if (StringUtils.isNotEmpty(row.get(71))) {
					if ("Home delivery only".equals(row.get(71))) {
						productInfoVo.setPnsOnlineDeliveryType("1");
					} else if ("Click & Collect only".equals(row.get(71))) {
						productInfoVo.setPnsOnlineDeliveryType("2");
					} else if ("Both Home Delivery & Click & Collect".equals(row.get(71))) {
						productInfoVo.setPnsOnlineDeliveryType("3");
					}
				} else {
					productInfoVo.setPnsOnlineDeliveryType("");
				}

				if (StringUtils.isNotEmpty(row.get(29))) {
					if (StringUtils.isNumeric(row.get(29))) {
						productInfoVo.setDailyInventory(row.get(29));
					} else {
						responseData1.add("daily_inventory_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					responseData1.add("product_save_dailyinventory");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}

			} else if (CONSIGNMENT.equals(row.get(24))) {
				productInfoVo.setDeliveryMode(C);
				productInfoVo.setConsignmentType(W);
				productInfoVo.setSupplierLeadTime(null);
				productInfoVo.setDailyInventory("");

				String minDeliveryDate = deliveryDateService.getMinDeliveryDate();
				if (StringUtils.isNotEmpty(minDeliveryDate)) {
					productInfoVo.setMinDeliverDate(new BigDecimal(minDeliveryDate));
				}

				String maxDeliveryDate = deliveryDateService.getMaxDeliveryDate();
				if (StringUtils.isNotEmpty(maxDeliveryDate)) {
					productInfoVo.setMaxDeliverDate(new BigDecimal(maxDeliveryDate));
				}

				if (StringUtils.isNotEmpty(row.get(17))) {
					String onlineDateStr = row.get(17);
					if (DateUtils.isValidDate(onlineDateStr)) {
						productInfoVo.setOnlineDate(DateUtils.parseDateStr(onlineDateStr, DateUtils.DATE_FORMAT_6));
					} else {
						responseData1.add("online_date_must_be_date_format");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (StringUtils.isNotEmpty(row.get(71))) {
					if ("Home delivery only".equals(row.get(71))) {
						productInfoVo.setPnsOnlineDeliveryType("1");
					} else if ("Click & Collect only".equals(row.get(71))) {
						productInfoVo.setPnsOnlineDeliveryType("2");
					} else if ("Both Home Delivery & Click & Collect".equals(row.get(71))) {
						productInfoVo.setPnsOnlineDeliveryType("3");
					}
				} else {
					productInfoVo.setPnsOnlineDeliveryType("");
				}

				if (StringUtils.isNotEmpty(row.get(26))) {
					if (!StringUtils.isNumeric(row.get(26))) {
						responseData1.add("replenishment_level(min)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (StringUtils.isNotEmpty(row.get(27))) {
					if (!StringUtils.isNumeric(row.get(27))) {
						responseData1.add("replenishment_level(max)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					responseData1.add("replenishment_level(max)_is_required");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}

				if (StringUtils.isNotEmpty(row.get(28))) {
					if (!StringUtils.isNumeric(row.get(28))) {
						responseData1.add("minimum_order_quantity_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					responseData1.add("minimum_order_quantity_is_required");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}

				if (StringUtils.isNotEmpty(row.get(28)) && StringUtils.isNumeric(row.get(28))
						&& StringUtils.isNotEmpty(row.get(27)) && StringUtils.isNumeric(row.get(27))) {
					if (Long.parseLong(row.get(27)) < Long.parseLong(row.get(28))) {
						responseData1.add("replenishment_level(max)_err_info");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (StringUtils.isNotEmpty(row.get(27)) && StringUtils.isNumeric(row.get(27))
						&& StringUtils.isNotEmpty(row.get(26)) && StringUtils.isNumeric(row.get(26))) {
					if (Long.parseLong(row.get(27)) < Long.parseLong(row.get(26))) {
						responseData1.add("replenishment_level(min)_err_info");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (StringUtils.isNotEmpty(row.get(28)) && StringUtils.isNumeric(row.get(28))
						&& StringUtils.isNotEmpty(row.get(27)) && StringUtils.isNumeric(row.get(27))
						&& StringUtils.isNotEmpty(row.get(26)) && StringUtils.isNumeric(row.get(26))) {
					productInfoVo.setMinOrderQty(row.get(28));
					productInfoVo.setMinReplenishmentLevel(new BigDecimal(row.get(26)));
					productInfoVo.setMaxReplenishmentLevel(new BigDecimal(row.get(27)));
				}

			} else {
				responseData1.add("product_save_deliverymode_err");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setDeliveryMode("");
		}
	}

	@Override
	public List<ResponseData<ProductInfoVo>> generateProductForAp(Sheet sheet) throws SystemException, IOException,
			ServiceException {

		UserVo userVo = sessionService.getCurrentUser();
		List<ResponseData<ProductInfoVo>> responseDataList = new ArrayList<>();
		int t = 0;
		for (Row workbookRow : sheet) {
			String rowNum = workbookRow.getRowNum() + 1 + "";
			List<String> row = new ArrayList();

			if (t == 0) {
				t++;
				continue;
			}
			boolean f = false;
			for (int n = 0; n < 73; n++) {
				// row.add(FileHandle.getCellValue(workbookRow.getCell(n)));
				try {
					row.add(FileHandle.getCellValue(workbookRow.getCell(n)));
				} catch (Exception ex) {

					ResponseData<ProductInfoVo> responseData = (ResponseData<ProductInfoVo>) responseDataService
							.getReturnData(ProductInfoVo.class);
					responseData.setHook(rowNum);
					responseData.add("upload_file_get_cell_failed");
					String[] param = { FileHandle.getCellByNum((n + 1)), ex.getMessage() };
					responseData.putMessagesParamArray("upload_file_get_cell_failed", param);

					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData);
					f = true;
					continue;
				}
			}

			if (f)
				return responseDataList;

			if (CollectionUtils.isNotEmpty(row)) {
				ProductInfoVo productInfoVo = new ProductInfoVo();

				boolean isUpdated = false;

				ResponseData<ProductInfoVo> responseData1 = (ResponseData<ProductInfoVo>) responseDataService
						.getReturnData(ProductInfoVo.class);

				responseData1.setHook(rowNum);
				if (StringUtils.isEmpty(row.get(0))) {
					responseData1.add("product_save_suppliercode");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData1);
					continue;
				}

				SupplierVo supplierModel = supplierService.selectByPrimaryKey(row.get(0));

				if (StringUtils.isEmpty(row.get(2))) {
					responseData1.add("product_save_supplierproductcode");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData1);
					continue;
				} else {
					Map<String, Object> map = new HashMap<>();
					map.put("supplierCode", row.get(0));
					map.put("supplierProductCode", row.get(2));
					ProductInfoModel productInfoModel = productInfoMapper.getProductInfoModelBySupplierProductCode(map);

					if (null == productInfoModel) {
						isUpdated = false;
						productInfoVo.setSupplierProductCode(row.get(2));
					} else {
						isUpdated = true;
						BeanUtils.copyProperties(productInfoModel, productInfoVo);
						// productInfoVo.setBrandCode(String.valueOf(productInfoModel.getBrandCode()));
						productInfoVo.setDailyInventory(String.valueOf(productInfoModel.getDailyInventory()));
						productInfoVo.setMinOrderQty(String.valueOf(productInfoModel.getMinOrderQty()));
					}
				}

				if (isUpdated) {
					if (StringUtils.isNotEmpty(row.get(1))) {
						Map<String, Object> map1 = new HashMap<>();
						map1.put("productCode", row.get(1));
						map1.put(VERSION, STAGING);
						ProductInfoModel productInfoModel = getProductInfoModelByProductCode(map1);
						if (null == productInfoModel) {
							responseData1.add("product_sku_not_found");
							responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseDataList.add(responseData1);
							continue;
						} else {
							if (!productInfoModel.getSupplierCode().equals(row.get(0))) {
								responseData1.add("supplier_code_mismatch");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								responseDataList.add(responseData1);
								continue;
							}
							if (!productInfoModel.getSupplierProductCode().equals(row.get(2))) {
								responseData1.add("product_sku_mismatch_with_product_vpn");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								responseDataList.add(responseData1);
								continue;
							}
							if (StringUtil.checkStringEquals(productInfoModel.getBaseProductCode(), row.get(3))) {
								responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								responseDataList.add(responseData1);
								continue;
							}
						}
					}
					if (StringUtils.isEmpty(productInfoVo.getProductCode())) {
						dealWithProductFieldsForAp(row, productInfoVo, responseData1, supplierModel);
					} else {

						if (!CONSIGNMENT.equals(row.get(25))) {
							if (StringUtils.isNotEmpty(row.get(30))) {
								if (StringUtils.isNumeric(row.get(30))) {
									productInfoVo.setDailyInventory(row.get(30));
								} else {
									responseData1.add("daily_inventory_should_be_numeric");
									responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								}
							} else {
								responseData1.add("product_save_dailyinventory");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							}
						}

						if (StringUtils.isNotEmpty(row.get(72))) {
							if ("Home delivery only".equals(row.get(72))) {
								productInfoVo.setPnsOnlineDeliveryType("1");
							} else if ("Click & Collect only".equals(row.get(72))) {
								productInfoVo.setPnsOnlineDeliveryType("2");
							} else if ("Both Home Delivery & Click & Collect".equals(row.get(72))) {
								productInfoVo.setPnsOnlineDeliveryType("3");
							}
						}

						// eStore category
						if (StringUtils.isNotEmpty(row.get(24))) {
							String value = row.get(24);
							if (value.indexOf(STRING2) != -1) {
								String[] valueArr = value.split(STRING2);
								Map<String, Object> map3 = new HashMap<>();
								map3.put("supplierId", row.get(0));
								map3.put("categoryId", valueArr[0]);
								SupplierCategoryModel supplierCategoryModel = selectBySupplierCategoryId(map3);
								if (null == supplierCategoryModel) {
									responseData1.add("product_the_category_not_exists");
									responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								} else {
									productInfoVo.setEstoreCategory(valueArr[0]);
								}
							} else {
								responseData1.add("product_category_format_err");
								responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							}
						}

					}
				} else {
					productInfoVo.setSupplierCode(row.get(0));

					if (StringUtils.isNotEmpty(row.get(1))) {
						responseData1.add("supplier_product_code_not_found");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseDataList.add(responseData1);
						continue;
					}

					dealWithProductFieldsForAp(row, productInfoVo, responseData1, supplierModel);
				}

				if (StringUtils.isNotEmpty(row.get(3)) && StringUtils.isEmpty(row.get(4))) {
					responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseDataList.add(responseData1);
					continue;
				} else if (StringUtils.isEmpty(row.get(3)) && StringUtils.isNotEmpty(row.get(4))) {
					if (!row.get(2).equals(row.get(4))) {
						Map<String, Object> map3 = new HashMap<>();
						map3.put(SUPPLIER_CODE, row.get(0));
						map3.put(SUPPLIER_PRODUCT_CODE, row.get(4));
						ProductInfoModel productInfoModel3 = getProductInfoModelBySupplierProductCode(map3);
						if (null != productInfoModel3) {
							productInfoVo.setBaseProductId(productInfoModel3.getId());
						} else {
							responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
							responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseDataList.add(responseData1);
							continue;
						}
					}
				} else if (StringUtils.isNotEmpty(row.get(3)) && StringUtils.isNotEmpty(row.get(4))) {
					Map<String, Object> map2 = new HashMap<>();
					map2.put("productCode", row.get(3));
					map2.put(VERSION, STAGING);
					ProductInfoModel productInfoModel2 = getProductInfoModelByProductCode(map2);
					if (null != productInfoModel2) {
						if (!productInfoModel2.getSupplierProductCode().equals(row.get(4))) {
							responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
							responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseDataList.add(responseData1);
							continue;
						} else if (!productInfoModel2.getSupplierCode().equals(row.get(0))) {
							responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
							responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseDataList.add(responseData1);
							continue;
						} else {
							productInfoVo.setBaseProductId(productInfoModel2.getId());
						}
					} else {
						responseData1.add("base_product_sku_vpn_mismatch_with_existing_product");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseDataList.add(responseData1);
						continue;
					}
				}

				// if (StringUtils.isNotEmpty(row.get(6))) {
				productInfoVo.setShortDescEn(StringUtil.repalceMultipleReturnLine(row.get(6)));
				// }

				// if (StringUtils.isNotEmpty(row.get(7))) {
				productInfoVo.setShortDescTc(StringUtil.repalceMultipleReturnLine(row.get(7)));
				// }

				// if (StringUtils.isNotEmpty(row.get(8))) {
				productInfoVo.setShortDescSc(StringUtil.repalceMultipleReturnLine(row.get(8)));
				// }

				// if (StringUtils.isNotEmpty(row.get(9))) {
				productInfoVo.setLongDescEn(StringUtil.repalceMultipleReturnLine(row.get(9)));
				// }

				// if (StringUtils.isNotEmpty(row.get(10))) {
				productInfoVo.setLongDescTc(StringUtil.repalceMultipleReturnLine(row.get(10)));
				// }

				// if (StringUtils.isNotEmpty(row.get(11))) {
				productInfoVo.setLongDescSc(StringUtil.repalceMultipleReturnLine(row.get(11)));
				// }

				if (StringUtils.isNotEmpty(row.get(19))) {
					String offlineDateStr = row.get(19);
					if (DateUtils.isValidDate(offlineDateStr)) {
						productInfoVo.setOfflineDate(DateUtils.parseDateStr(offlineDateStr, DateUtils.DATE_FORMAT_6));
					} else {
						responseData1.add("offline_date_must_be_date_format");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setOfflineDate(null);
				}

				if (StringUtils.isNotEmpty(row.get(21))) {
					String value = row.get(21);
					if (value.indexOf(STRING) != -1) {
						String[] valueArr1 = value.split(STRING);
						productInfoVo.setStandardUom(valueArr1[0]);
					} else {
						responseData1.add("standard_uom_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setStandardUom("");
				}

				if (StringUtils.isNotEmpty(row.get(34))) {
					if (ValidateUtils.validateNumber(row.get(34))) {
						productInfoVo.setConsignmentRate(new BigDecimal(row.get(34)));
					} else {
						responseData1.add("consignment_rate_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setConsignmentRate(null);
				}

				// if (StringUtils.isNotEmpty(row.get(35))) {
				productInfoVo.setSizeDesc(row.get(35));
				// }

				if (StringUtils.isNotEmpty(row.get(36))) {
					if (StringUtils.isNumeric(row.get(36))) {
						productInfoVo.setCasePackInner(new BigDecimal(row.get(36)));
					} else {
						responseData1.add("case_pack_(inner)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCasePackInner(null);
				}

				if (StringUtils.isNotEmpty(row.get(37))) {
					if (StringUtils.isNumeric(row.get(37))) {
						productInfoVo.setCasePackCase(new BigDecimal(row.get(37)));
					} else {
						responseData1.add("case_pack_(case)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCasePackCase(null);
				}

				// Dim Unit
				if (StringUtils.isNotEmpty(row.get(38))) {
					if (row.get(38).indexOf(STRING) != -1) {
						String[] valueArr = row.get(38).split(STRING);
						productInfoVo.setDimUnit(valueArr[0]);
					} else {
						responseData1.add("product_dimunit_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setDimUnit("");
				}

				if (StringUtils.isNotEmpty(row.get(39))) {
					if (ValidateUtils.validateNumber(row.get(39))) {
						productInfoVo.setProductDimHeight(new BigDecimal(row.get(39)));
					} else {
						responseData1.add("product_dimension_(height)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setProductDimHeight(null);
				}

				if (StringUtils.isNotEmpty(row.get(40))) {
					if (ValidateUtils.validateNumber(row.get(40))) {
						productInfoVo.setProductDimWidth(new BigDecimal(row.get(40)));
					} else {
						responseData1.add("product_dimension_(width)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setProductDimWidth(null);
				}

				if (StringUtils.isNotEmpty(row.get(41))) {
					if (ValidateUtils.validateNumber(row.get(41))) {
						productInfoVo.setProductDimLength(new BigDecimal(row.get(41)));
					} else {
						responseData1.add("product_dimension_(length)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setProductDimLength(null);
				}

				if (StringUtils.isNotEmpty(row.get(42))) {
					if (ValidateUtils.validateNumber(row.get(42))) {
						productInfoVo.setShippingDimHeight(new BigDecimal(row.get(42)));
					} else {
						responseData1.add("shipping_dimension_(height)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setShippingDimHeight(null);
				}

				if (StringUtils.isNotEmpty(row.get(43))) {
					if (ValidateUtils.validateNumber(row.get(43))) {
						productInfoVo.setShippingDimWidth(new BigDecimal(row.get(43)));
					} else {
						responseData1.add("shipping_dimension_(width)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setShippingDimWidth(null);
				}

				if (StringUtils.isNotEmpty(row.get(44))) {
					if (ValidateUtils.validateNumber(row.get(44))) {
						productInfoVo.setShippingDimLength(new BigDecimal(row.get(44)));
					} else {
						responseData1.add("shipping_dimension_(length)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setShippingDimLength(null);
				}

				if (StringUtils.isNotEmpty(row.get(45))) {
					if (ValidateUtils.validateNumber(row.get(45))) {
						productInfoVo.setCaseDimHeight(new BigDecimal(row.get(45)));
					} else {
						responseData1.add("case_dimension_(height)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCaseDimHeight(null);
				}

				if (StringUtils.isNotEmpty(row.get(46))) {
					if (ValidateUtils.validateNumber(row.get(46))) {
						productInfoVo.setCaseDimWidth(new BigDecimal(row.get(46)));
					} else {
						responseData1.add("case_dimension_(width)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCaseDimWidth(null);
				}

				if (StringUtils.isNotEmpty(row.get(47))) {
					if (ValidateUtils.validateNumber(row.get(47))) {
						productInfoVo.setCaseDimLength(new BigDecimal(row.get(47)));
					} else {
						responseData1.add("case_dimension_(length)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setCaseDimLength(null);
				}

				// weight unit
				if (StringUtils.isNotEmpty(row.get(48))) {
					if (row.get(48).indexOf(STRING) != -1) {
						String[] valueArr = row.get(48).split(STRING);
						productInfoVo.setWeightUnit(valueArr[0]);
					} else {
						responseData1.add("product_weightunit_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setWeightUnit("");
				}

				if (StringUtils.isNotEmpty(row.get(49))) {
					if (ValidateUtils.validateNumber(row.get(49))) {
						productInfoVo.setGrossWeight(new BigDecimal(row.get(49)));
					} else {
						responseData1.add("gross_weight_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setGrossWeight(null);
				}

				if (StringUtils.isNotEmpty(row.get(50))) {
					if (ValidateUtils.validateNumber(row.get(50))) {
						productInfoVo.setShippingWeight(new BigDecimal(row.get(50)));
					} else {
						responseData1.add("shipping_weight_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					productInfoVo.setShippingWeight(null);
				}

				ProductBarCode productBarCode = new ProductBarCode();
				List<ProductBarCode> productBarCodeList = new ArrayList<>();

				if (StringUtils.isNotEmpty(row.get(51))) {
					LovModel lovModel = lovService.getLovModelByLovIdDesc("405", row.get(51));
					if (lovModel != null) {
						productBarCode.setItemNumType(lovModel.getLovValue());
					} else {
						responseData1.add("itemnumtype_value_incorrect");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (isUpdated) {
					productBarCode.setBarcodeNum(row.get(52));
					Map<String, Object> map1 = new HashMap<>();
					map1.put("barcodenum", row.get(52));
					map1.put(VERSION, "STAGING");
					List<ProductBarcodeModel> productBarCodeModelList = productBarcodeService
							.getProductBarcodeModelByBarcodeNum(map1);
					for (ProductBarcodeModel productBarcodeModel : productBarCodeModelList) {
						productBarCode.setProductCode(String.valueOf(productBarcodeModel.getProductId()));
						productBarCode.setId(productBarcodeModel.getId());
					}
				} else {
					if (StringUtils.isNotEmpty(row.get(52))) {
						productBarCode.setBarcodeNum(row.get(52));
					}
				}

				productBarCodeList.add(productBarCode);
				productInfoVo.setBarcodeList(productBarCodeList);

				if (StringUtils.isNotEmpty(row.get(53))) {
					String value = row.get(53);
					if (value.equalsIgnoreCase(WITH_NUTRITION_LABEL_ON_PACKAGE)) {
						productInfoVo.setNutritionLabel(W);
					} else if (value.equalsIgnoreCase(NUTRITION_LABELING_EXEMPTION)) {
						productInfoVo.setNutritionLabel(E);
					} else {
						productInfoVo.setNutritionLabel(N);
					}
				} else {
					productInfoVo.setNutritionLabel("");
				}

				// if (StringUtils.isNotEmpty(row.get(54))) {
				productInfoVo.setProductUsageEn(StringUtil.repalceMultipleReturnLine(row.get(54)));
				// }

				// if (StringUtils.isNotEmpty(row.get(55))) {
				productInfoVo.setProductUsageTc(StringUtil.repalceMultipleReturnLine(row.get(55)));
				// }

				// if (StringUtils.isNotEmpty(row.get(56))) {
				productInfoVo.setProductUsageSc(StringUtil.repalceMultipleReturnLine(row.get(56)));
				// }

				// if (StringUtils.isNotEmpty(row.get(57))) {
				productInfoVo.setProductWarningsEn(StringUtil.repalceMultipleReturnLine(row.get(57)));
				// }

				// if (StringUtils.isNotEmpty(row.get(58))) {
				productInfoVo.setProductWarningsTc(StringUtil.repalceMultipleReturnLine(row.get(58)));
				// }

				// if (StringUtils.isNotEmpty(row.get(59))) {
				productInfoVo.setProductWarningsSc(StringUtil.repalceMultipleReturnLine(row.get(59)));
				// }

				// if (StringUtils.isNotEmpty(row.get(60))) {
				productInfoVo.setStorageConditionEn(StringUtil.repalceMultipleReturnLine(row.get(60)));
				// }

				// if (StringUtils.isNotEmpty(row.get(61))) {
				productInfoVo.setStorageConditionTc(StringUtil.repalceMultipleReturnLine(row.get(61)));
				// }

				// if (StringUtils.isNotEmpty(row.get(62))) {
				productInfoVo.setStorageConditionSc(StringUtil.repalceMultipleReturnLine(row.get(62)));
				// }

				// if (StringUtils.isNotEmpty(row.get(63))) {
				productInfoVo.setProductIngredientsEn(StringUtil.repalceMultipleReturnLine(row.get(63)));
				// }

				// if (StringUtils.isNotEmpty(row.get(64))) {
				productInfoVo.setProductIngredientsTc(StringUtil.repalceMultipleReturnLine(row.get(64)));
				// }

				// if (StringUtils.isNotEmpty(row.get(65))) {
				productInfoVo.setProductIngredientsSc(StringUtil.repalceMultipleReturnLine(row.get(65)));
				// }

				if (StringUtils.isNotEmpty(row.get(66))) {
					LovModel lovModel = lovService.getLovModelByLovIdDesc(LovType.PACKED_IN.getLovId(), row.get(66));
					if (null == lovModel) {
						responseData1.add("product_manufcountry_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					} else {
						productInfoVo.setManufCountry(lovModel.getLovValue());
					}
				} else {
					productInfoVo.setManufCountry("");
				}

				if (StringUtils.isNotEmpty(row.get(67))) {
					LovModel lovModel = lovService.getLovModelByLovIdDesc(LovType.PACKAGE.getLovId(), row.get(67));
					if (null == lovModel) {
						responseData1.add("product_package_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					} else {
						productInfoVo.setPackAge(lovModel.getLovValue());
					}
				} else {
					productInfoVo.setPackAge("");
				}

				// if (StringUtils.isNotEmpty(row.get(68))) {
				productInfoVo.setProductShelfLife(row.get(68));
				// }

				// if (StringUtils.isNotEmpty(row.get(69))) {
				productInfoVo.setMinShelfLife(row.get(69));
				// }

				// if (StringUtils.isNotEmpty(row.get(71))) {
				productInfoVo.setShippingInfo(StringUtil.repalceMultipleReturnLine(row.get(71)));
				// }

				if (responseData1.getErrorList().isEmpty()) {
					batchUploadProduct(productInfoVo, responseData1, userVo);
				}
				responseData1.setReturnData(productInfoVo);
				responseDataList.add(responseData1);
			}
		}
		return responseDataList;
	}

	private void dealWithProductFieldsForAp(List<String> row, ProductInfoVo productInfoVo,
			ResponseData<ProductInfoVo> responseData1, SupplierVo supplierModel) {

		// brand
		if (StringUtils.isNotEmpty(row.get(12))) {
			String brand = row.get(12);
			List<BrandModel> brandModelList = new ArrayList<BrandModel>();
			BrandModel brandM = brandService.getBrandModelByDescEn(row.get(12));
			brandModelList = brandService.getBrandsBySupplierId(row.get(0));
			brandModelList.add(brandM);
			if (CollectionUtils.isNotEmpty(brandModelList)) {
				// BrandModel brandObj= new BrandModel();
				// boolean flag=false;
				for (BrandModel brandModel : brandModelList) {
					if (String.valueOf(brandModel.getDescEn()).equals(brand)) {
						// flag=true;
						// brandObj=brandModel;
						break;
					}
				}
				// if(flag){
				// productInfoVo.setBrandCode(String.valueOf(brandObj.getBrandCode()));
				// }else{
				// responseData1.add("product_the_brand_not_exists");
				// responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				// }
			} else {
				responseData1.add("product_brand_is_null");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setBrandCode("");
		}

		// eStore category
		if (StringUtils.isNotEmpty(row.get(24))) {
			String value = row.get(24);
			if (value.indexOf(STRING2) != -1) {
				String[] valueArr = value.split(STRING2);
				Map<String, Object> map3 = new HashMap<>();
				map3.put("supplierId", row.get(0));
				map3.put("categoryId", valueArr[0]);
				SupplierCategoryModel supplierCategoryModel = selectBySupplierCategoryId(map3);
				if (null == supplierCategoryModel) {
					responseData1.add("product_the_category_not_exists");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				} else {
					productInfoVo.setEstoreCategory(valueArr[0]);
				}
			} else {
				responseData1.add("product_category_format_err");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setEstoreCategory("");
		}

		if (StringUtils.isNotEmpty(row.get(13))) {
			productInfoVo.setVariantOn(row.get(13));
		} else {
			productInfoVo.setVariantOn("");
		}

		if (StringUtils.isNotEmpty(row.get(33))) {
			if ("Amount(Net)".equals(row.get(33))) {
				productInfoVo.setConsignmentCalBasis("N");
			} else if ("Amount(Gross)".equals(row.get(33))) {
				productInfoVo.setConsignmentCalBasis("G");
			}
		} else {
			productInfoVo.setConsignmentCalBasis("");
		}

		if (StringUtils.isNotEmpty(row.get(14))) {
			String colorGroup = row.get(14);
			if (colorGroup.indexOf(STRING2) != -1) {
				String[] colorGroupArr = colorGroup.split(STRING2);
				productInfoVo.setColorGroup(colorGroupArr[0]);
			} else {
				responseData1.add("colorgroup_value_incorrect");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setColorGroup("");
		}

		productInfoVo.setColorHexCode(row.get(15));

		productInfoVo.setColorCode(row.get(16));

		productInfoVo.setColorDesc(row.get(17));

		if (StringUtils.isNotEmpty(row.get(22))) {
			LovModel lovModel = lovService.getLovModelByLovIdDesc("404", row.get(22));
			if (lovModel != null) {
				productInfoVo.setOriginCountry(lovModel.getLovValue());
			} else {
				responseData1.add("origin_country_value_incorrect");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setOriginCountry("");
		}

		// dept,class,subclass
		if (StringUtils.isNotEmpty(row.get(23))) {
			String value = row.get(23);
			if (value.indexOf(STRING2) != -1) {
				String[] valueArr1 = value.split(STRING2);
				if (valueArr1[0].indexOf(STRING) != -1) {
					String[] valueArr2 = valueArr1[0].split(STRING);
					if (StringUtils.isNotEmpty(valueArr2[0]) && StringUtils.isNotEmpty(valueArr2[1])
							&& StringUtils.isNotEmpty(valueArr2[2]) && StringUtils.isNumeric(valueArr2[0])
							&& StringUtils.isNumeric(valueArr2[1]) && StringUtils.isNumeric(valueArr2[2])) {
						DeptModel deptModel = deptService.getDeptByDeptId(new BigDecimal(valueArr2[0]));
						if (null != deptModel) {
							productInfoVo.setDept(deptModel.getId());
							Map<String, Object> classMap = new HashMap<>();
							classMap.put("deptId", deptModel.getId());
							classMap.put("classId", valueArr2[1]);
							ClassModel classModel = classService.getClassByDeptIdClassId(classMap);
							if (null != classModel) {
								productInfoVo.setClazz(classModel.getId());
								Map<String, Object> subClassMap = new HashMap<>();
								subClassMap.put("classId", classModel.getId());
								subClassMap.put("subClassId", valueArr2[2]);
								SubClassModel subClassModel = subClassService
										.getSubClassByClassIdSubClassId(subClassMap);
								if (null != subClassModel) {
									productInfoVo.setSubClass(subClassModel.getId());
									if (StringUtils.isEmpty(productInfoVo.getEstoreCategory())) {
										productInfoVo.setEstoreCategory(subClassModel.getEcategroyId());
									}
								}
							}
						}
					} else {
						responseData1.add("product_dept_class_subclass_format_err");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}

				} else {
					responseData1.add("product_dept_class_subclass_format_err");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}
			} else {
				responseData1.add("product_dept_class_subclass_format_err");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setDept(null);
			productInfoVo.setClazz(null);
			productInfoVo.setSubClass(null);

		}

		if (StringUtils.isNotEmpty(row.get(20))) {
			if (ValidateUtils.validateNumber(row.get(20))) {
				productInfoVo.setUnitRetail(new BigDecimal(row.get(20)));
				String smallExpensive = ResourceUtil.getSystemConfig().getProperty("small_expensive");
				if (smallExpensive == null || StringUtils.isEmpty(smallExpensive)) {
					responseData1.add("product_save_small_expensive_err");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				} else if (!StringUtils.isNumeric(smallExpensive)) {
					responseData1.add("product_save_small_expensive_format_err");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				} else {
					if (Double.parseDouble(row.get(20)) > Double.parseDouble(smallExpensive)) {
						productInfoVo.setSmallExpensive(Y);
					} else {
						String value = row.get(70);
						if (StringUtils.isNotEmpty(value)) {
							if (value.equalsIgnoreCase(YES)) {
								productInfoVo.setSmallExpensive(Y);
							} else {
								productInfoVo.setSmallExpensive(N);
							}
						} else {
							productInfoVo.setSmallExpensive(N);
						}
					}
				}
			} else {
				responseData1.add("unit_retail_should_be_numeric");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setUnitRetail(null);
		}

		if (StringUtils.isNotEmpty(row.get(25))) {
			if (SUPPLIER_DIRECT_DELIVERY.equals(row.get(25))) {
				productInfoVo.setDeliveryMode("D");
				productInfoVo.setConsignmentType(C);
				productInfoVo.setSupplierLeadTime(null);
				productInfoVo.setMaxReplenishmentLevel(null);
				productInfoVo.setMinReplenishmentLevel(null);
				productInfoVo.setMinDeliverDate(supplierModel.getMinDeliveryDay());
				productInfoVo.setMaxDeliverDate(supplierModel.getMaxDeliveryDay());
				productInfoVo.setMinOrderQty(null);
				productInfoVo.setPnsOnlineDeliveryType("1");
				String productDefaultOnlineDate = ResourceUtil.getSystemConfig().getProperty(
						"product.default.onlineDate");
				productInfoVo.setOnlineDate(DateUtils.parseDateStr(productDefaultOnlineDate, DateUtils.DATE_FORMAT_5));
				if (StringUtils.isNotEmpty(row.get(30))) {
					if (StringUtils.isNumeric(row.get(30))) {
						productInfoVo.setDailyInventory(row.get(30));
					} else {
						responseData1.add("daily_inventory_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					responseData1.add("product_save_dailyinventory");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}

			} else if (CONSIGNMENT_VIA_WAREHOUSE.equals(row.get(25))) {
				productInfoVo.setDeliveryMode(W);
				productInfoVo.setConsignmentType(W);
				productInfoVo.setMinDeliverDate(null);
				productInfoVo.setMaxDeliverDate(null);
				productInfoVo.setMaxReplenishmentLevel(null);
				productInfoVo.setMinReplenishmentLevel(null);
				productInfoVo.setMinOrderQty("1");
				productInfoVo.setSupplierLeadTime(supplierModel.getWarehouseDeliLeadTime());
				String productDefaultOnlineDate = ResourceUtil.getSystemConfig().getProperty(
						"product.default.onlineDate");
				productInfoVo.setOnlineDate(DateUtils.parseDateStr(productDefaultOnlineDate, DateUtils.DATE_FORMAT_5));

				if (StringUtils.isNotEmpty(row.get(72))) {
					if ("Home delivery only".equals(row.get(72))) {
						productInfoVo.setPnsOnlineDeliveryType("1");
					} else if ("Click & Collect only".equals(row.get(72))) {
						productInfoVo.setPnsOnlineDeliveryType("2");
					} else if ("Both Home Delivery & Click & Collect".equals(row.get(72))) {
						productInfoVo.setPnsOnlineDeliveryType("3");
					}
				} else {
					productInfoVo.setPnsOnlineDeliveryType("");
				}

				if (StringUtils.isNotEmpty(row.get(30))) {
					if (StringUtils.isNumeric(row.get(30))) {
						productInfoVo.setDailyInventory(row.get(30));
					} else {
						responseData1.add("daily_inventory_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					responseData1.add("product_save_dailyinventory");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}

			} else if (CONSIGNMENT.equals(row.get(25))) {
				productInfoVo.setDeliveryMode(C);
				productInfoVo.setConsignmentType(W);
				productInfoVo.setSupplierLeadTime(null);
				productInfoVo.setDailyInventory("");

				String minDeliveryDate = deliveryDateService.getMinDeliveryDate();
				if (StringUtils.isNotEmpty(minDeliveryDate)) {
					productInfoVo.setMinDeliverDate(new BigDecimal(minDeliveryDate));
				}

				String maxDeliveryDate = deliveryDateService.getMaxDeliveryDate();
				if (StringUtils.isNotEmpty(maxDeliveryDate)) {
					productInfoVo.setMaxDeliverDate(new BigDecimal(maxDeliveryDate));
				}

				if (StringUtils.isNotEmpty(row.get(18))) {
					String onlineDateStr = row.get(18);
					if (DateUtils.isValidDate(onlineDateStr)) {
						productInfoVo.setOnlineDate(DateUtils.parseDateStr(onlineDateStr, DateUtils.DATE_FORMAT_6));
					} else {
						responseData1.add("online_date_must_be_date_format");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (StringUtils.isNotEmpty(row.get(72))) {
					if ("Home delivery only".equals(row.get(72))) {
						productInfoVo.setPnsOnlineDeliveryType("1");
					} else if ("Click & Collect only".equals(row.get(72))) {
						productInfoVo.setPnsOnlineDeliveryType("2");
					} else if ("Both Home Delivery & Click & Collect".equals(row.get(72))) {
						productInfoVo.setPnsOnlineDeliveryType("3");
					}
				} else {
					productInfoVo.setPnsOnlineDeliveryType("");
				}

				if (StringUtils.isNotEmpty(row.get(27))) {
					if (!StringUtils.isNumeric(row.get(27))) {
						responseData1.add("replenishment_level(min)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (StringUtils.isNotEmpty(row.get(28))) {
					if (!StringUtils.isNumeric(row.get(28))) {
						responseData1.add("replenishment_level(max)_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					responseData1.add("replenishment_level(max)_is_required");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}

				if (StringUtils.isNotEmpty(row.get(29))) {
					if (!StringUtils.isNumeric(row.get(29))) {
						responseData1.add("minimum_order_quantity_should_be_numeric");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				} else {
					responseData1.add("minimum_order_quantity_is_required");
					responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}

				if (StringUtils.isNotEmpty(row.get(29)) && StringUtils.isNumeric(row.get(29))
						&& StringUtils.isNotEmpty(row.get(28)) && StringUtils.isNumeric(row.get(28))) {
					if (Long.parseLong(row.get(28)) < Long.parseLong(row.get(29))) {
						responseData1.add("replenishment_level(max)_err_info");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (StringUtils.isNotEmpty(row.get(28)) && StringUtils.isNumeric(row.get(28))
						&& StringUtils.isNotEmpty(row.get(27)) && StringUtils.isNumeric(row.get(27))) {
					if (Long.parseLong(row.get(28)) < Long.parseLong(row.get(27))) {
						responseData1.add("replenishment_level(min)_err_info");
						responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
				}

				if (StringUtils.isNotEmpty(row.get(29)) && StringUtils.isNumeric(row.get(29))
						&& StringUtils.isNotEmpty(row.get(28)) && StringUtils.isNumeric(row.get(28))
						&& StringUtils.isNotEmpty(row.get(27)) && StringUtils.isNumeric(row.get(27))) {
					productInfoVo.setMinOrderQty(row.get(28));
					productInfoVo.setMinReplenishmentLevel(new BigDecimal(row.get(27)));
					productInfoVo.setMaxReplenishmentLevel(new BigDecimal(row.get(28)));
				}

			} else {
				responseData1.add("product_save_deliverymode_err");
				responseData1.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
		} else {
			productInfoVo.setDeliveryMode("");
		}
	}

	@Override
	public SupplierCategoryModel selectBySupplierCategoryId(Map<String, Object> map) {

		return supplierCategoryModelMapper.selectBySupplierCategoryId(map);
	}

	@Override
	public ProductInfoVo copyOnlineProductBarCodeToStaging(String productId, String onlineProductId,
			ProductInfoVo onlineProductInfoVo) {

		productBarcodeService.deleteProductBarcodeByProductId(productId);
		List<ProductBarcodeModel> onlineProductBarCodeModelList = productBarcodeService
				.getProductBarcodeModelsByProductId(onlineProductId);
		List<ProductBarCode> productBarCodeList = new ArrayList<>();
		for (ProductBarcodeModel productBarCodeModel : onlineProductBarCodeModelList) {
			ProductBarCode productBarCode = new ProductBarCode();
			BeanUtils.copyProperties(productBarCodeModel, productBarCode);
			productBarCode.setProductId(new BigDecimal(productId));
			productBarCode.setId(null);
			productBarCodeList.add(productBarCode);
		}
		onlineProductInfoVo.setBarcodeList(productBarCodeList);

		return onlineProductInfoVo;
	}

	@Override
	public ProductInfoVo copyOnlineProductImagesToStaging(String productId, String onlineProductId,
			ProductInfoVo onlineProductInfoVo) {

		productImagesService.deleteByProductId(new BigDecimal(productId));

		List<ProductImagesVo> productImageList = new ArrayList<>();
		List<ProductImagesVo> onlineProductImageList = productImagesService.getProductImagesByProductId(new BigDecimal(
				onlineProductId), ConstantUtil.PRODUCT_IMAGE_TYPE);
		for (ProductImagesVo image : onlineProductImageList) {
			ProductImagesVo productImagesVo = new ProductImagesVo();
			BeanUtils.copyProperties(image, productImagesVo);
			productImagesVo.setProductId(new BigDecimal(productId));
			productImagesVo.setId(null);
			productImageList.add(productImagesVo);
		}
		onlineProductInfoVo.setImagesList(productImageList);

		List<ProductImagesVo> swatchImageList = new ArrayList<>();
		List<ProductImagesVo> onlineProductSwatchImageList = productImagesService.getProductImagesByProductId(
				new BigDecimal(onlineProductId), ConstantUtil.SWATCH_IMAGE_TYPE);
		for (ProductImagesVo image : onlineProductSwatchImageList) {
			ProductImagesVo productImagesVo = new ProductImagesVo();
			BeanUtils.copyProperties(image, productImagesVo);
			productImagesVo.setProductId(new BigDecimal(productId));
			productImagesVo.setId(null);
			swatchImageList.add(productImagesVo);
		}
		onlineProductInfoVo.setSwatchImagesList(swatchImageList);
		return onlineProductInfoVo;
	}

	@Override
	public ProductInfoModel getSupplierProductInfoModelBySupplierId(String supplierCode) {

		return productInfoMapper.getSupplierProductInfoModelBySupplierId(supplierCode);
	}

	@Override
	public List<ProductInfoModel> getProductBySupplierCode(ProductParameterVo parameterVo) {
		return productInfoMapper.getProductBySupplierCode(parameterVo);
	}

	@Override
	public int updateByPrimaryKey(ProductInfoModel record) {
		return productInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteDeliveryFeeProductBySu(String supplierCode) {
		return productInfoMapper.deleteDeliveryFeeProductBySu(supplierCode);
	}
}
