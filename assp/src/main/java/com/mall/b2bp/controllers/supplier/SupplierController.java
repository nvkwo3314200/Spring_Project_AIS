package com.mall.b2bp.controllers.supplier;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;





import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.models.shop.ShopInfoModel;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.product.ProductInfoService;
import com.mall.b2bp.services.shop.ShopInfoService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.brand.BrandVo;
import com.mall.b2bp.vos.supplier.SupplierUpdateVo;
import com.mall.b2bp.vos.supplier.SupplierVo;
import com.mall.b2bp.vos.supplier.UploadDataVo;
//import com.mall.b2bp.vos.supplier.UserSupplierVo;
import com.mall.b2bp.vos.user.UserVo;



/**
 * Created by USER on 2016/3/28.
 */

@Controller("SupplierController")
@RequestMapping(value = "/supplier")
public class SupplierController extends BaseConroller {

	private static final String JPG = ".jpg";

	private static final Logger LOG = LoggerFactory
			.getLogger(SupplierController.class);

	@Resource(name = "supplierService")
	private SupplierService supplierService;

	@Resource(name = "sessionService")
	SessionService sessionService;

	@Resource(name = "brandService")
	private BrandService brandService;

	@Resource(name = "productInfoService")
	private ProductInfoService productInfoService;

	
	@Resource
	private ShopInfoService shopInfoService;
	


	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER" })
	@RequestMapping(value = "/editSetting", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData editSetting(@RequestBody SupplierUpdateVo data)
			throws SystemException {
		ResponseData responseData = responseDataService
				.getReturnData(SupplierVo.class);
		responseData.setResourceBundleMessageSource(messageSource);
		try {

			boolean success = supplierService.editSetting(data, responseData);
			if (success) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				return responseData;
			} else {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
		}

	}

	@Secured({ "ROLE_SYSTEMADMIN","ROLE_SUPPLIER" })
	@RequestMapping(value = "/view", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<SupplierVo> view(String supplierId) throws SystemException {

		ResponseData<SupplierVo> responseData = (ResponseData<SupplierVo>) responseDataService
				.getReturnData(SupplierVo.class);
		responseData.setResourceBundleMessageSource(messageSource);

	//	String supplierId = null;
		try {
			//UserVo userVo = sessionService.getCurrentUser();
//			if (userVo != null) {
//				supplierId = userVo.getSupplierId();
//			}

			if (StringUtils.isNotEmpty(supplierId)) {
				SupplierVo vo = supplierService.selectByPrimaryKey(supplierId);

				if (vo == null) {
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseData.add("user_invalid_supplier_id");
					return responseData;
				}

				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				List<BrandVo> list = brandService
						.getAllBrandsBySupplierId(supplierId);

				// show error reason from retek....
				ProductInfoModel productInfoModel = productInfoService.getSupplierProductInfoModelBySupplierId(supplierId);
				if (productInfoModel != null) {

					vo.setDisableDeliveryFee(true);
					String failedReason = productInfoModel.getFailedReason();
					if (StringUtils.isNotEmpty(failedReason)) {
						vo.setFailedReason(failedReason);
					}
				}else{
					vo.setDisableDeliveryFee(false);
				}

				vo.setBrandVo(list);
				responseData.setReturnData(vo);
				return responseData;
			} else {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_edit_update_setting_invalid_role");
				return responseData;
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
		}

	}

	@RequestMapping(value = "/listUserSupplier", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<ShopInfoModel> listUserSupplier() throws SystemException {
		List<ShopInfoModel> usersuList = null;
		try {
//			usersuList = supplierService.getAllUserSupplier();
			usersuList =shopInfoService.getAllShop();
			
		} catch (Exception e) {
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return usersuList;
	}

	private String addZeroForNum(String str, int strLength) {
		if (StringUtils.isEmpty(str))
			str = "";

		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < strLength) {
			sb = new StringBuffer();
			sb.append("0").append(str);// 左(前)补0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public ResponseData<BrandModel> upload(HttpServletRequest request)
			throws SystemException, UnsupportedEncodingException,
			FileUploadException {
		LOG.info("-----start upload------");
		ResponseData<BrandModel> rd = (ResponseData<BrandModel>) responseDataService
				.getReturnData(SupplierVo.class);

		Map map = supplierService.getFileItem(request);

		FileItem fileItem = (FileItem) map.get("file");
		String path = ResourceUtil.getSystemConfig().getProperty("upload_brand_image_path");

//
//		LOG.info("upload image path:"+path);

		File dir = new File(path);

		try {

			if (!dir.exists()) {
				dir.mkdirs();
			}

			UserVo userVo = sessionService.getCurrentUser();
			String name = null;
			if (userVo != null)
				name = userVo.getUserId();

			String masterId = (String) map.get("masterId");
			String code = (String) map.get("code");
			// Brand ID (UDA ID + UDA Value)
			String uploadFileName = addZeroForNum(masterId, 3)
					+ addZeroForNum(code, 3) + JPG;
			upload(uploadFileName, fileItem, path, rd);

			if (CollectionUtils.isEmpty(rd.getErrorList())) {
				// SupplierVo
				// supplierVo=supplierService.selectByPrimaryKey(userVo.getSupplierId());
				// SupplierModel supplierModel=new SupplierModel();
				// supplierVo.setImageFileName(userVo.getSupplierId()+JPG);
				// BeanUtils.copyProperties(supplierVo,supplierModel);
				// supplierService.updateByPrimaryKeySelective(supplierModel);

				BrandModel brand = new BrandModel();
				brand.setLastUpdatedBy(name);
				brand.setLastUpdatedDate(new Date());
				if (StringUtils.isNotEmpty(masterId))
					brand.setMasterId(new BigDecimal(masterId));
				if (StringUtils.isNotEmpty(code))
					brand.setCode(new BigDecimal(code));
				brand.setImageFileName(uploadFileName);

				brandService.updateByPrimaryKeySelective(brand);

				rd.add("user_edit_images_successful");
				rd.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				rd.setReturnData(brand);
			} else {
				String pathImage = ResourceUtil.getSystemConfig().getProperty(
						"upload_brand_image_path");
				FileHandle.deleteFile(pathImage + uploadFileName);
			}
			LOG.info("-----end upload product------");
			return rd;
		} catch (Exception e) {
			rd.setErrorMessage(e.getMessage());
			rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			LOG.error("uplod product error:"+e.getMessage(), e);
			LOG.info("-----start upload product------");
			throw new SystemException(e.getMessage(), e);
		}

	}


	private void upload(String uploadFileName, FileItem fileItem, String path,
			ResponseData<BrandModel> rd) throws SystemException {
		// SupplierVo supplierVo = new SupplierVo();
		String sname = fileItem.getName();

		if (sname == null)
			sname = "";
		String fileType = sname.substring(sname.indexOf("."), sname.length());
		if (!JPG.equals(fileType)) {
			rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			rd.add("user_edit_images_invalid_picture_type");
			return;
		}
		// String uploadFileName = userVo.getSupplierId() + "" + fileType;

		String filepath = path + File.separator + uploadFileName;
		// supplierVo.setImageFileName(sname);

		File savedFileName = new File(filepath);
		try (InputStream is = fileItem.getInputStream();
				FileOutputStream fos = new FileOutputStream(savedFileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {

			byte[] buffer = new byte[1024];
			int len;

			while ((len = is.read(buffer)) >= 0) {
				bos.write(buffer, 0, len);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}

		try {
			String formatPx = "60x60";
			String str = FileHandle.getResolution1(savedFileName);
			if (!formatPx.equals(str)) {
				rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				rd.add("user_edit_images_invalid_picture_element");
			}

		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}

		// return supplierVo;
		return;
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<SupplierVo> deleteImage(
			@RequestBody UploadDataVo uploadDataVo) throws SystemException {
		ResponseData<SupplierVo> rd = (ResponseData<SupplierVo>) responseDataService
				.getReturnData(SupplierVo.class);
		try {

			UserVo userVo = sessionService.getCurrentUser();
			String name = null;
			if (userVo != null)
				name = userVo.getUserId();
			String masterId = null;
			String code = null;
			if (uploadDataVo != null) {
				masterId = uploadDataVo.getMasterId();
				code = uploadDataVo.getCode();

			}
			BrandModel brand = new BrandModel();
			brand.setLastUpdatedBy(name);
			brand.setLastUpdatedDate(new Date());
			if (StringUtils.isNotEmpty(masterId))
				brand.setMasterId(new BigDecimal(masterId));
			if (StringUtils.isNotEmpty(code))
				brand.setCode(new BigDecimal(code));
			brand.setImageFileName("");

			// SupplierVo
			// supplierVo=supplierService.selectByPrimaryKey(userVo.getSupplierId());
			// SupplierModel supplierModel=new SupplierModel();
			// supplierVo.setImageFileName("");
			// BeanUtils.copyProperties(supplierVo,supplierModel);
			// supplierService.updateByPrimaryKeySelective(supplierModel);

			brandService.updateByPrimaryKeySelective(brand);
			String path = ResourceUtil.getSystemConfig().getProperty(
					"upload_brand_image_path");

			FileHandle.deleteFile(path + userVo.getSupplierId() + JPG);

			rd.add("user_delete_images_successful");
			rd.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

		} catch (Exception e) {
			rd.setErrorMessage(e.getMessage());
			rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return rd;
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER" })
	@RequestMapping(value = "/updateBrand", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData updateBrand(@RequestBody final BrandVo brandVo)
			throws SystemException {
		ResponseData responseData = responseDataService
				.getReturnData(SupplierVo.class);
		responseData.setResourceBundleMessageSource(messageSource);

		try {
			if (brandService.updateBrand(brandVo, responseData)) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				return responseData;

			} else {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER" })
	@RequestMapping(value = "/categorys", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData updateProdCat( String [] categorys,BigDecimal suplierId)
			throws SystemException {
		ResponseData responseData = responseDataService
				.getReturnData(SupplierVo.class);
		responseData.setResourceBundleMessageSource(messageSource);

        try {
            supplierService.saveProductCategory(categorys, suplierId);
            responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
            responseData.add("save_supplier_mall_prod_cat_success");

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            responseData.add("save_supplier_mall_prod_cat_error");
            throw new SystemException(e.getMessage(), e);

        }
        return responseData;
    }

}
