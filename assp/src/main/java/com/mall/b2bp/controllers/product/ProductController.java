package com.mall.b2bp.controllers.product;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.mall.b2bp.enums.ImportLogPath;
import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.models.lov.LovModel;
import com.mall.b2bp.models.product.ProductExportModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.dept.ClassService;
import com.mall.b2bp.services.dept.DeptService;
import com.mall.b2bp.services.dept.SubClassService;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.services.product.ProductAuditService;
import com.mall.b2bp.services.product.ProductBarCodeUpFieldService;
import com.mall.b2bp.services.product.ProductBarcodeService;
import com.mall.b2bp.services.product.ProductExportReportHandler;
import com.mall.b2bp.services.product.ProductImagesService;
import com.mall.b2bp.services.product.ProductImagesUpFieldService;
import com.mall.b2bp.services.product.ProductImportLogService;
import com.mall.b2bp.services.product.ProductInfoService;
import com.mall.b2bp.services.product.ProductUpFieldService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.lov.LovVo;
import com.mall.b2bp.vos.product.ProductBarCode;
import com.mall.b2bp.vos.product.ProductImagesVo;
import com.mall.b2bp.vos.product.ProductImportLogVo;
import com.mall.b2bp.vos.product.ProductInfoVo;
import com.mall.b2bp.vos.product.SubProductInfo;
import com.mall.b2bp.vos.supplier.SupplierVo;
import com.mall.b2bp.vos.user.UserVo;

@Controller("ProductController")
@RequestMapping(value = "/product")
public class ProductController extends ProductImageController {
	private static final String PRODUCT_SAVE_PRODUCTID = "product_save_productid";

	private static final String PRODUCT_SAVE_SUPPLIER_ID = "product_save_supplierId";

	private static final String PRODUCT_PRODUCT_INFOVO_NULL = "product_productInfovo_null";
	private static final String SUPPLIER = "SUPPLIER";
	private String baseProductId = "";

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Resource(name = "productInfoService")
	private ProductInfoService productInfoService;

	@Resource(name = "lovService")
	private LovService lovService;

	@Resource(name = "productBarcodeService")
	private ProductBarcodeService productBarcodeService;

	@Resource(name = "subClassService")
	private SubClassService subClassService;


	@Resource(name = "deptService")
	private DeptService deptService;


	@Resource(name = "classService")
	private ClassService classService;

	@Resource(name = "brandService")
	private BrandService brandService;


	@Resource(name = "supplierService")
	private SupplierService supplierService;


	@Resource(name = "sessionService")
	SessionService sessionService;

	@Resource(name = "productUpFieldService")
	private ProductUpFieldService productUpFieldService;

	@Resource(name = "productBarCodeUpFieldService")
	private ProductBarCodeUpFieldService productBarCodeUpFieldService;

	@Resource(name = "productImagesUpFieldService")
	private ProductImagesUpFieldService productImagesUpFieldService;

	@Resource(name = "productImportLogService")
	private ProductImportLogService productImportLogService;

	@Resource(name = "productAuditService")
	private ProductAuditService productAuditService;


	@Resource(name = "productExportReportHandler")
	private ProductExportReportHandler productExportReportHandler;

	@RequestMapping(value="/getImage/{filename:.*}")
	public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
		String path = ResourceUtil.getSystemConfig().getProperty("upload_file_path");
		byte[] content = FileUtils.readFileToByteArray(new File(path+File.separator+filename));

	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);

	    return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
	}

	//lwh start
		@Resource(name = "productImagesService")
		private ProductImagesService productImagesService;

		@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
		@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public ResponseData<ProductImagesVo> upload(HttpServletRequest request) throws SystemException {

			ResponseData<ProductImagesVo> rd=(ResponseData<ProductImagesVo>) responseDataService.getReturnData(ProductImagesVo.class);
			rd.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			try {
				getImages(request, rd);

			} catch (Exception e) {
				rd.setErrorMessage(e.getMessage());
				rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			log.error(e.getMessage(),e);
			throw new SystemException(e.getMessage(),e);
			}

			return rd;

		}

		@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
		@RequestMapping(value = "/uploadSwatch", method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public ResponseData<ProductImagesVo> uploadSwatch(HttpServletRequest request) throws SystemException {

			ResponseData<ProductImagesVo> rd=(ResponseData<ProductImagesVo>) responseDataService.getReturnData(ProductImagesVo.class);

			rd.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			try {
				getSwatchImages(request, rd);

			} catch (Exception e) {
				rd.setErrorMessage(e.getMessage());
				rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				log.error(e.getMessage(),e);
				throw new SystemException(e.getMessage(),e);
			}

			return rd;

		}





		//lwh end
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/save", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ProductInfoVo> saveProduct(@RequestBody final ProductInfoVo productjson) throws ServiceException, SystemException {
			ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);

			boolean productFlag=productInfoService.validateProduct(productjson, responseData);
		    boolean barcodeFlag=productBarcodeService.validateProductBarCode(productjson, responseData);
		    boolean approveImagesNumFlag=true;
		    boolean approveSwatchImagesNumFlag=true;
		    UserVo userVo = sessionService.getCurrentUser();
		    if(ConstantUtil.PRODUCT_STATUS_APPROVED.equals(productjson.getStatus())){
		     approveImagesNumFlag=productImagesService.validateImages(productjson.getId(), productjson.getImagesList(),responseData);
		    }

		    if(ConstantUtil.PRODUCT_STATUS_APPROVED.equals(productjson.getStatus())){
		    	approveSwatchImagesNumFlag=productImagesService.validateSwatchImages(productjson.getId(), productjson.getImagesList(),responseData);
			}


			if(productFlag&&barcodeFlag&&approveImagesNumFlag&&approveSwatchImagesNumFlag){
				boolean isEdit=false;
				boolean isAdd=false;
				if(productjson.getId()!=null){
					isEdit=productInfoService.updateProduct(productjson, responseData,userVo);
				}else{
					isAdd=productInfoService.insertProduct(productjson,responseData,userVo);
				}

				if(isAdd){
					responseData.add("product_added_info");
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
					return responseData;
				}else if(isEdit){
					responseData.add("product_updated_info");
					responseData.setErrorType("warning");
					return responseData;
				}
				else{
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					return responseData;
				}
			}else{
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
			}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/deleteProductByProductId", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ProductInfoVo> deleteProductByProductId(@RequestParam(value = "productId", required = false) final String productId)throws SystemException{
		ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);


		if(StringUtils.isEmpty(productId)){
			responseData.add(PRODUCT_SAVE_PRODUCTID);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}

		try {

			ProductInfoModel productInfoModel=productInfoService.selectByPrimaryKey(new BigDecimal(productId));
		    if(productInfoModel==null){
		    	responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
		    	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
		    }

		    if(productInfoModel.getBaseProductId()==null){
		    	   baseProductId=productId;
		    	   List<SubProductInfo> subProductInfoList=productInfoService.getSubProductListByBaseProductId(String.valueOf(productInfoModel.getId()));
		    	   if(subProductInfoList.size()!=1){
		    		   responseData.add("product_delete_err");
		    		   responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					   return responseData;
		    	   }
		    }else{
		    	  baseProductId=String.valueOf(productInfoModel.getBaseProductId());
		    }

		    productAuditService.deleteByProductId(productId);
		    productBarCodeUpFieldService.deleteByProductId(productId);
		    productImagesUpFieldService.deleteByProductId(productId);
		    productUpFieldService.deleteByProductId(productId);
			productBarcodeService.deleteProductBarcodeByProductId(productId);
			productImagesService.deleteByProductId(new BigDecimal(productId));

		    productInfoService.deleteProductByProductId(productId);





			ProductInfoVo productInfoVo=productInfoService.getProductInfoVo(new BigDecimal(baseProductId), new BigDecimal(baseProductId));
			responseData.setReturnData(productInfoVo);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/initEditProductDetail", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ProductInfoVo> initEditProductDetail(@RequestParam(value = "productId", required = false) final String productId)throws SystemException{
		ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);


		if(StringUtils.isEmpty(productId)){
			responseData.add(PRODUCT_SAVE_PRODUCTID);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		ProductInfoModel productInfoModel=productInfoService.selectByPrimaryKey(new BigDecimal(productId));
	    if(productInfoModel==null){
	    	responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
			return responseData;
	    }
	    if(productInfoModel.getBaseProductId()==null){
	    	   baseProductId=productId;
	    }else{
	    	  baseProductId=String.valueOf(productInfoModel.getBaseProductId());
	    }

		try {
			ProductInfoVo productInfoVo=productInfoService.getProductInfoVo(new BigDecimal(productId), new BigDecimal(baseProductId));
			if(productInfoVo!=null){
                productInfoVo.setSupplierProfileUpdateInd(productInfoModel.getSupplierProfileUpdateInd());
				responseData.setReturnData(productInfoVo);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}

			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/ignoreUpdate", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ProductInfoVo> ignoreUpdate(@RequestParam(value = "productId", required = false) final String productId)throws SystemException{
		ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);


		if(StringUtils.isEmpty(productId)){
			responseData.add(PRODUCT_SAVE_PRODUCTID);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		ProductInfoModel productInfoModel=productInfoService.selectByPrimaryKey(new BigDecimal(productId));
	    if(null==productInfoModel){
	    	responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
	    	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
	    }

	    try {
	    	Map<String , Object> map = new HashMap<>();
			map.put("productCode", productInfoModel.getProductCode());
			map.put("version", "ONLINE");
			ProductInfoModel onlineProductInfoModel=productInfoService.getProductInfoModelByProductCode(map);
			if(null==onlineProductInfoModel){
		    	responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
		    	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
		    }

			onlineProductInfoModel.setOnlineUpdatedInd("N");
		    productInfoService.updateByPrimaryKey(onlineProductInfoModel);
		    responseData.add("product_updated_info");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/confirmUpdate", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ProductInfoVo> confirmUpdate(@RequestParam(value = "productId", required = false) final String productId)throws SystemException{
		ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);


		UserVo userVo = sessionService.getCurrentUser();

		if(StringUtils.isEmpty(productId)){
			responseData.add(PRODUCT_SAVE_PRODUCTID);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		ProductInfoModel productInfoModel=productInfoService.selectByPrimaryKey(new BigDecimal(productId));
	    if(null==productInfoModel){
	    	responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
			return responseData;
	    }

	    try {
			Map<String , Object> map = new HashMap<>();
			map.put("productCode", productInfoModel.getProductCode());
			map.put("version", "ONLINE");
			ProductInfoModel onlineProductInfoModel=productInfoService.getProductInfoModelByProductCode(map);
			if(null==onlineProductInfoModel){
		    	responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
				return responseData;
		    }

			ProductInfoVo onlineProductInfoVo=new ProductInfoVo();
			String onlineProductId=String.valueOf(onlineProductInfoModel.getId());

			//copy online barcode to staging
			productInfoService.copyOnlineProductBarCodeToStaging(productId, onlineProductId,onlineProductInfoVo);

			//copy online image to staging
			productInfoService.copyOnlineProductImagesToStaging(productId,onlineProductId, onlineProductInfoVo);


			//copy object
			BeanUtils.copyProperties(onlineProductInfoModel,onlineProductInfoVo);
			onlineProductInfoVo.setBaseProductId(null);
			onlineProductInfoVo.setOnlineUpdatedInd("N");
			onlineProductInfoVo.setId(new BigDecimal(productId));
			onlineProductInfoVo.setVersion("STAGING");
			onlineProductInfoVo.setStatus("APPROVED_IN_RETEK");
			onlineProductInfoVo.setDealType("COPY");
			onlineProductInfoVo.setLastOverWriteDate(new Date());



			productInfoService.updateProduct(onlineProductInfoVo, responseData, userVo);

			onlineProductInfoModel.setOnlineUpdatedInd("N");
			productInfoService.updateByPrimaryKey(onlineProductInfoModel);

			responseData.add("product_updated_info");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}





	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/initNewProduct", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ProductInfoVo> displayProductDetail(@RequestParam(value = "productId", required = false) final String productId)throws SystemException{
		ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);


		if(StringUtils.isEmpty(productId)){
			responseData.add(PRODUCT_SAVE_PRODUCTID);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}

		try {
			ProductInfoVo productInfoVo=productInfoService.getProductInfoVo(new BigDecimal(productId), new BigDecimal(productId));
			if(productInfoVo!=null){
				List<ProductBarCode>  productBarcodeList=new ArrayList<>();
				List<ProductBarCode>  newProductBarcodeList=new ArrayList<>();
				for (ProductBarCode productBarCode : productBarcodeList) {
					productBarCode.setId(null);
					newProductBarcodeList.add(productBarCode);
				}

				productInfoVo.setBarcodeList(productBarcodeList);
				productInfoVo.setSupplierProductCode("");
				productInfoVo.setId(null);
				productInfoVo.setStatus("");
				productInfoVo.setProductCode("");
				responseData.setReturnData(productInfoVo);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}

			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getAllItemNumType", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<LovModel> getItemNumTypeInfoAll()throws SystemException{
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);

		try {
			List<LovModel> list = lovService.getLovsByLovId(LovType.ITEM_NUM_TYPE);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add("product_item_num_type_data_err");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			 throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getSubClasssBySupplierClassId", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<SubClassModel> getSubClasssBySupplierClassId(@RequestParam(value = "classId", required = false) final String classId,@RequestParam(value = "supplierId", required = false) final String supplierId)throws SystemException{
		ResponseData<SubClassModel> responseData=(ResponseData<SubClassModel>) responseDataService.getReturnData(SubClassModel.class);
	 	boolean validateResult = true;

		if(StringUtils.isEmpty(classId)){
			responseData.add("product_save_classId");
			validateResult=false;

		}
		if(StringUtils.isEmpty(supplierId)){
			responseData.add("product_save_supplierId");
			validateResult=false;

		}

		if(!validateResult){
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}

		try {

			Map<String , Object> map = new HashMap<>();
			map.put("supplierId",supplierId);
			map.put("classId", classId);
			List<SubClassModel> list = subClassService.getSubClasssBySupplierClassId(map);
			if(CollectionUtils.isNotEmpty(list)){
				List<SubClassModel> subClassModelList=new ArrayList<>();
				for (SubClassModel obj : list) {
					obj.setDescriptionCode(obj.getDescription()+"("+obj.getSubClassId()+")");
					subClassModelList.add(obj);
				}
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				List<SubClassModel> subClassList = subClassService.getSubClassByClassId(classId);
				if(CollectionUtils.isNotEmpty(subClassList)){
					List<SubClassModel> subClassModelList=new ArrayList<>();
					for (SubClassModel obj : subClassList) {
						obj.setDescriptionCode(obj.getDescription()+"("+obj.getSubClassId()+")");
						subClassModelList.add(obj);
					}
					responseData.setReturnDataList(subClassModelList);
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

				}else{
					responseData.add("product_subclass_data_err");
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}
			}




			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			 throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getSupplierBySupplierId", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<SupplierVo> getSupplierBySupplierId(@RequestParam(value = "supplierId", required = false) final String supplierId)throws SystemException {
		ResponseData<SupplierVo> responseData=(ResponseData<SupplierVo>) responseDataService.getReturnData(SupplierVo.class);

		if(StringUtils.isEmpty(supplierId)){
			responseData.add(PRODUCT_SAVE_SUPPLIER_ID);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}

		try {
			SupplierVo supplierModel = supplierService.selectByPrimaryKey(supplierId);
//		 	String productDefaultOnlineDate=ResourceUtil.getSystemConfig().getProperty("product.default.onlineDate");
		 	supplierModel.setProductDefaultOnlineDate(new Date());
			responseData.setReturnData(supplierModel);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getDeptsBySupplierId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<DeptModel> getDeptsBySupplierId(@RequestParam(value = "supplierId", required = false) final String supplierId,@RequestParam(value = "deptId", required = false) final String deptId)throws SystemException {
		ResponseData<DeptModel> responseData=(ResponseData<DeptModel>) responseDataService.getReturnData(DeptModel.class);
		boolean validateResult = true;
		try {
			if(StringUtils.isEmpty(supplierId)){
				responseData.add("product_save_supplierId");
				validateResult=false;

			}

			if(!validateResult){
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
			}


			List<DeptModel> list =new ArrayList<>();

			if(StringUtils.isNotEmpty(deptId)){
				list= deptService.getDeptsBySupplierId(supplierId);
				if(CollectionUtils.isNotEmpty(list)){
					DeptModel deptModel=deptService.selectByPrimaryKey(new BigDecimal(deptId));
					boolean flag=false;
					for (DeptModel dept : list) {
						if(deptModel!=null && dept!=null &&
								dept.getId()!=null && deptModel.getId() !=null
								&& dept.getId().intValue()==deptModel.getId().intValue()){
							flag=true;
							break;
						}
					}

					if(!flag){
						list.add(deptModel);
					}

					List<DeptModel> deptModelList=new ArrayList<>();
					for (DeptModel obj : list) {
						obj.setDescriptionCode(obj.getDescription()+"("+obj.getDeptId()+")");
						deptModelList.add(obj);
					}
					responseData.setReturnDataList(deptModelList);
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				}else{
					DeptModel deptObj=deptService.getDeptByDeptId(new BigDecimal(deptId));
					deptObj.setDescriptionCode(deptObj.getDescription()+"("+deptObj.getDeptId()+")");
					responseData.getReturnDataList().add(deptObj);
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				}
			}else{
				list= deptService.getDeptsBySupplierId(supplierId);
				if(CollectionUtils.isNotEmpty(list)){
					List<DeptModel> deptModelList=new ArrayList<>();
					for (DeptModel obj : list) {
						obj.setDescriptionCode(obj.getDescription()+"("+obj.getDeptId()+")");
						deptModelList.add(obj);
					}
					responseData.setReturnDataList(deptModelList);
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				}else{
					responseData.add("product_dept_data_err");
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}
			}




			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="getStandardUom", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<LovModel> getAllUnit()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);
		try {
			List<LovModel> list = lovService.getLovsByLovId(LovType.STANDARD_UOM);
			if(CollectionUtils.isNotEmpty(list)){
				List<LovModel> lovModelList=new ArrayList<>();
				for (LovModel obj : list) {
					obj.setLovDesc(obj.getLovValue()+"-"+obj.getLovDesc());
					lovModelList.add(obj);
				}
				responseData.setReturnDataList(lovModelList);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add("product_lov_data_err");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
			return responseData;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="getUomCbm", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<LovModel> getUomCbm()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);
		try {
			List<LovModel> list = lovService.getLovsByLovId(LovType.UOM_CBM);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add("product_lov_data_err");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
			return responseData;

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="getUomWeight", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<LovModel> getUomWeight()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);
		try {
			List<LovModel> list = lovService.getLovsByLovId(LovType.UOM_WEIGHT);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add("product_lov_data_err");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
			return responseData;

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getAllCountry", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<LovModel> getCountryInfoAll()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);
		try {
			List<LovModel> list = lovService.getLovsByLovId(LovType.ORIGIN_COUNTRY);
				if(CollectionUtils.isNotEmpty(list)){
					responseData.setReturnDataList(list);
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

			}else{
				responseData.add("product_country_data_err");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
			return responseData;

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getPnsOnlineDeliveryType", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<LovModel> getPnsOnlineDeliveryType()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);
		try {
			List<LovModel> list = lovService.getLovsByLovId(LovType.ONLINE_ITEM_DELIVERY_TYPE);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add("product_country_data_err");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
			return responseData;

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getProductApprovalLeadTime", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<String> getProductApprovalLeadTime() throws SystemException{
		ResponseData<String> responseData=(ResponseData<String>) responseDataService.getReturnData(String.class);
	 	String productApprovalLeadTime=ResourceUtil.getSystemConfig().getProperty("product_approval_lead_time");
	 	if(productApprovalLeadTime==null ||StringUtils.isEmpty(productApprovalLeadTime)){
			responseData.add("product_save_product_approval_lead_time_err");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}else if(!StringUtils.isNumeric(productApprovalLeadTime)){
			responseData.add("product_save_product_approval_lead_time_format_err");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
	 	responseData.setReturnData(productApprovalLeadTime);
	 	responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		return responseData;
	}


	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getSmallExpensive", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<String> getSmallExpensive() throws SystemException{
		ResponseData<String> responseData=(ResponseData<String>) responseDataService.getReturnData(String.class);
	 	String smallExpensive=ResourceUtil.getSystemConfig().getProperty("small_expensive");
	 	if(smallExpensive==null ||StringUtils.isEmpty(smallExpensive)){
			responseData.add("product_save_small_expensive_err");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}else if(!StringUtils.isNumeric(smallExpensive)){
			responseData.add("product_save_small_expensive_format_err");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
	 	responseData.setReturnData(smallExpensive);
	 	responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		return responseData;
	}





	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getClassByDeptId", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ClassModel> getClassByDeptId(@RequestParam(value = "supplierId", required = false) final String supplierId,@RequestParam(value = "deptId", required = false) final String deptId)throws SystemException {
		ResponseData<ClassModel> responseData=(ResponseData<ClassModel>) responseDataService.getReturnData(ClassModel.class);

		if(StringUtils.isEmpty(deptId)){
			responseData.add("product_save_deptid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}

		try {
			Map<String , Object> map = new HashMap<>();
			map.put("supplierId",supplierId);
			map.put("deptId", deptId);
			List<ClassModel> list = classService.getClasssBySupplierDeptId(map);
			if(CollectionUtils.isNotEmpty(list)){
				List<ClassModel> classModelList=new ArrayList<>();
				for (ClassModel obj : list) {
					obj.setDescriptionCode(obj.getDescription()+"("+obj.getClassId()+")");
					classModelList.add(obj);
				}
				responseData.setReturnDataList(classModelList);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				List<ClassModel> classList=classService.getClassByDeptId(deptId);
				if(CollectionUtils.isNotEmpty(classList)){
					List<ClassModel> classModelList=new ArrayList<>();
					for (ClassModel obj : classList) {
						obj.setDescriptionCode(obj.getDescription()+"("+obj.getClassId()+")");
						classModelList.add(obj);
					}
				responseData.setReturnDataList(classModelList);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				}else{
					responseData.add("product_class_data_err");
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}
			}

			return responseData;
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}


	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getMinOrderQty", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<LovModel> getMinOrderQty()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);
		try {

			List<LovModel> list =lovService.getLovsByLovId(LovType.MIN_ORDER_QTY);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				    responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}

			return responseData;
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getPackages", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<LovModel> getPackages()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);
		try {

			List<LovModel> list =lovService.getLovsByLovId(LovType.PACKAGE);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				    responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}

			return responseData;
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getManufCountrys", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<LovModel> getManufCountrys()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);
		try {

			List<LovModel> list =lovService.getLovsByLovId(LovType.PACKED_IN);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				    responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}

			return responseData;
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getColorGroups", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<LovModel> getColorGroups()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);

		try {

			List<LovModel> list =lovService.getLovsByLovId(LovType.COLOR_GROUP);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				    responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}

			return responseData;
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getDailyInventory", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<LovModel> getDailyInventory()throws SystemException {
		ResponseData<LovModel> responseData=(ResponseData<LovModel>) responseDataService.getReturnData(LovModel.class);

		try {

			List<LovModel> list =lovService.getLovsByLovId(LovType.DAILY_INVENTORY);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				    responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}

			return responseData;
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}


	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getBrandsBySupplierId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<BrandModel> getBrandsBySupplierId(@RequestParam(value = "supplierId", required = false) final String supplierId)throws SystemException {
		ResponseData<BrandModel> responseData=(ResponseData<BrandModel>) responseDataService.getReturnData(BrandModel.class);

		if(StringUtils.isEmpty(supplierId)){
			responseData.add(PRODUCT_SAVE_SUPPLIER_ID);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}

		try {

			List<BrandModel> list = brandService.getBrandsBySupplierId(supplierId);
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add("product_brand_data_err");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
			return responseData;
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getDefaultCategoryBySubClassId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<LovVo> getDefaultCategoryBySubClassId(@RequestParam(value = "subClassId", required = false) final String subClassId)throws SystemException {
		ResponseData<LovVo> responseData=(ResponseData<LovVo>) responseDataService.getReturnData(LovVo.class);

		if(StringUtils.isEmpty(subClassId)){
			responseData.add("product_save_subClassId");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}

		try {
			SubClassModel subClassModel = subClassService.selectByPrimaryKey(new BigDecimal(subClassId));
			if(null!=subClassModel){
				if(StringUtils.isNotEmpty(subClassModel.getEcategroyId())){
					LovVo lovVo=lovService.getLovById(LovType.ESTORE_CATEGORY.getLovId(), subClassModel.getEcategroyId());
					responseData.setReturnData(lovVo);
				}
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add("product_subClass_not_found");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}
			return responseData;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}


	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getCategorysBySupplierId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseData<LovVo> getCategorysBySupplierId(@RequestParam(value = "supplierId", required = false) final String supplierId)throws SystemException {
		ResponseData<LovVo> responseData=(ResponseData<LovVo>) responseDataService.getReturnData(LovVo.class);

	 	if(StringUtils.isEmpty(supplierId)){
			responseData.add(PRODUCT_SAVE_SUPPLIER_ID);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}

		try {

			List<LovVo> list = lovService.getCategoryBySupplierId(supplierId,LovType.ESTORE_CATEGORY.getLovId());
			if(list!=null){
				responseData.setReturnDataList(list);
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}else{
				responseData.add("product_category_data_err");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			}

			return responseData;
		} catch (Exception e) {

			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}


	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getLovById", method = RequestMethod.GET)
	@ResponseBody
	public LovVo getLovById(@RequestParam(value = "lovValue", required = true) final String lovValue)throws SystemException {
		try {
		return this.lovService.getLovById(LovType.ESTORE_CATEGORY.getLovId(), lovValue);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getBrandByBrandCode", method = RequestMethod.GET)
	@ResponseBody
	public BrandModel getBrandByBrandCode(@RequestParam(value = "brandCode", required = true) final String brandCode)throws SystemException {
		try {

			if(StringUtils.isNotEmpty(brandCode) && !brandCode.equals("null")){

		return this.brandService.selectByPrimaryKey(new BigDecimal(brandCode));
			}else {
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getDeptByDeptId", method = RequestMethod.GET)
	@ResponseBody
	public DeptVo getDeptByDeptId(@RequestParam(value = "deptId", required = true) final String deptId)throws SystemException {
		try {
			DeptVo  deptVo=this.deptService.getDeptById(new BigDecimal(deptId));
			if(null!=deptVo){
				deptVo.setDescriptionCode(deptVo.getDescription()+"("+deptVo.getDeptId()+")");
			}
		return deptVo;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getClassByClassId", method = RequestMethod.GET)
	@ResponseBody
	public ClassModel getClassByclassId(@RequestParam(value = "classId", required = true) final String classId)throws SystemException {
		try {
			ClassModel classModel=this.classService.selectByPrimaryKey(new BigDecimal(classId));
			if(null!=classModel){
			    classModel.setDescriptionCode(classModel.getDescription()+"("+classModel.getClassId()+")");
			}
		return classModel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getSubClassBySubClassId", method = RequestMethod.GET)
	@ResponseBody
	public SubClassModel getSubClassBySubClassId(@RequestParam(value = "subClassId", required = true) final String subClassId)throws SystemException {
		try {
			SubClassModel subClassModel=this.subClassService.selectByPrimaryKey(new BigDecimal(subClassId));
			if(null!=subClassModel){
				subClassModel.setDescriptionCode(subClassModel.getDescription()+"("+subClassModel.getSubClassId()+")");
			}
		return subClassModel;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		     throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/productViewList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<ProductInfoVo> productViewList(
			@RequestParam(value = "onlineUpdatedInd", required = false) final String onlineUpdatedInd,
			@RequestParam(value = "supplier", required = false) final String supplier,
			@RequestParam(value = "supplierProductCode", required = false) final String supplierProductCode,
			@RequestParam(value = "productCode", required = false) final String productCode,
			@RequestParam(value = "status", required = false) final String status,
			@RequestParam(value = "deliveryMode", required = false) final String deliveryMode,
			@RequestParam(value = "shortDescEn", required = false) final String shortDescEn) {
		UserVo userVo = sessionService.getCurrentUser();
		List<ProductInfoVo> productInfoVos = null;
		try {
			productInfoVos = productInfoService.selectProductList(onlineUpdatedInd,supplier,
					supplierProductCode, productCode, status, deliveryMode,
					shortDescEn, userVo);
		} catch (Exception e) {
			log.error("productInfoVos error:" + e.getMessage(), e);
		}
		return productInfoVos;
	}
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/updateProductStatus", method = {
			RequestMethod.POST, RequestMethod.GET }, produces = {
			"application/xml", "application/json" })
	@ResponseBody
	public ResponseData updateProductStatus(
			@RequestParam(value = "pIds", required = false) final String pIds,
			@RequestParam(value = "pStatus", required = false) final String pStatus,
			@RequestParam(value = "pType", required = false) final String pType) throws SystemException {
		ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		ProductInfoVo productInfoVo = null;
		try {
			productInfoService.updateProductStatus(pIds, pStatus, pType);

             if(StringUtils.isNotEmpty(pIds) && pIds.indexOf(",") == -1){
					ProductInfoModel productInfoModel = productInfoService
							.selectByPrimaryKey(new BigDecimal(pIds));
					if (productInfoModel == null) {
						responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
						return responseData;
					}

					if (productInfoModel.getBaseProductId() == null) {
						baseProductId = pIds;
					} else {
						baseProductId = String.valueOf(productInfoModel
								.getBaseProductId());
					}

					productInfoVo = productInfoService.getProductInfoVo(
							new BigDecimal(pIds), new BigDecimal(baseProductId));

					if (productInfoVo != null) {
						responseData.setReturnData(productInfoVo);
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
					} else {
						responseData.add(PRODUCT_PRODUCT_INFOVO_NULL);
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					}
             }
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			     throw new SystemException(e.getMessage(), e);
			}

		return responseData;

	}
	
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/deleteProduct", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData deleteProduct(
			@RequestParam(value = "pIds", required = false) final String pIds) throws SystemException {
		ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);
		try {
			productInfoService.deleteProductByPId(pIds);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			responseData.setErrorMessage(e.getMessage());
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			log.error(e.getMessage(), e);
		}

		return responseData;

	}


	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/uploadProduct", method = { RequestMethod.POST,RequestMethod.GET }, produces = { "application/xml","application/json" })
	@ResponseBody
	public ResponseData uploadProductToHybris(@RequestParam(value = "pIds", required = false) final String pIds) throws SystemException, IOException {

		log.info("*************************Start ASSP upload product to AA hybris*******************");
		
		ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);
		try {
			productInfoService.uploadProductByPId(pIds);
			//productInfoService.deleteProductByPId(pIds);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {

			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			log.error(e.getMessage(), e);
			 throw new SystemException(e.getMessage(), e);
		}
		log.info("*************************End upload product to AA hybris*******************");
		return responseData;

	}


	  protected void getImages(HttpServletRequest request,
				ResponseData<ProductImagesVo> rd) throws FileUploadException,
				UnsupportedEncodingException, SystemException {
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			List<FileItem> fileList = upload.parseRequest(request);
			String path = ResourceUtil.getSystemConfig().getProperty("upload_file_path");

			List<ProductImagesVo> imageList = new ArrayList<>();
			for (FileItem fileItem : fileList) {

				if ("file".equals(fileItem.getFieldName())) {

					ProductImagesVo image = upload(fileItem, path, rd);
					imageList.add(image);

				}

			}

			rd.setReturnDataList(imageList);

		}

	  protected void getSwatchImages(HttpServletRequest request,
			  ResponseData<ProductImagesVo> rd) throws FileUploadException,
			  UnsupportedEncodingException, SystemException {
		  DiskFileItemFactory fac = new DiskFileItemFactory();
		  ServletFileUpload upload = new ServletFileUpload(fac);
		  upload.setHeaderEncoding("UTF-8");
		  List<FileItem> fileList = upload.parseRequest(request);
		  String path = ResourceUtil.getSystemConfig().getProperty(
				  "upload_file_path");

		  List<ProductImagesVo> imageList = new ArrayList<>();
		  for (FileItem fileItem : fileList) {

			  if ("file".equals(fileItem.getFieldName())) {

				  ProductImagesVo image = uploadStatch(fileItem, path, rd);
				  imageList.add(image);

			  }

		  }

		  rd.setReturnDataList(imageList);

	  }

	//InputStream
	public ProductImagesVo upload(FileItem fileItem, String path, ResponseData<ProductImagesVo> rd) throws SystemException {
		try {
            String fileItemName = fileItem.getName();
		    InputStream is = new BufferedInputStream(fileItem.getInputStream());
         return   upload(  fileItemName, is  ,  path, rd);
		}catch(Exception e){
            log.info(e.getMessage(),e);
            throw new SystemException(e.getMessage(),e);
		}
	}

//		public ProductImagesVo upload(String  fileItemName,InputStream is  , String path,ResponseData<ProductImagesVo> rd) throws SystemException {
//			ProductImagesVo image = new ProductImagesVo();
//			String sname =fileItemName;// fileItem.getName();
//			if (sname == null)
//				sname = "";
//			String fileType = sname.substring(sname.indexOf("."), sname.length());
//			String uploadFileName = Long.toString(System.currentTimeMillis()) + "" + fileType;
//
//			File uploadPath = new File(path);
//			if(!uploadPath.exists()){
//				uploadPath.mkdirs();
//			}
//
//			String filepath = path + File.separator + uploadFileName;
//			image.setFileName(sname);
//			image.setFilePath(uploadFileName);
//			File savedFileName = new File(filepath);
//			try (
//					//InputStream is = new BufferedInputStream(fileItem.getInputStream());
//					FileOutputStream fos = new FileOutputStream(savedFileName);
//					BufferedOutputStream bos = new BufferedOutputStream(fos);
//			){
//				final String mimeType = URLConnection.guessContentTypeFromStream(is);
//				if(!"image/jpeg".equalsIgnoreCase(mimeType)){
//					rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//					rd.add("product_images_invalid_picture_type");
//					return image;
//				}
//
//				byte[] buffer = new byte[1024];
//				int len;
//
//				while ((len = is.read(buffer)) >= 0) {
//					bos.write(buffer, 0, len);
//				}
//
//			} catch (Exception e) {
//				log.error(e.getMessage(),e);
//				throw new SystemException(e.getMessage(),e);
//			}
//
//			try {
//				String formatPx = "1200x1200";
//				String str = FileHandle.getResolution1(savedFileName);
//				if (!formatPx.equals(str)) {
//					rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//					rd.add("product_images_invalid_picture_element");
//					rd.putMessagesParamArray(
//							"product_images_invalid_picture_element",
//							new String[] { //fileItem.getName() });
//									fileItemName});
//				}
//
//			} catch (IOException e) {
//				log.error(e.getMessage(), e);
//				rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//				rd.add("product_images_invalid_picture_type");
//			}
//
//			return image;
//		}

		public ProductImagesVo uploadStatch(FileItem fileItem, String path,
				ResponseData<ProductImagesVo> rd) throws SystemException {
			ProductImagesVo image = new ProductImagesVo();
			String sname = fileItem.getName();
			if (sname == null)
				sname = "";
			String fileType = sname.substring(sname.indexOf("."), sname.length());
			String uploadFileName = Long.toString(System.currentTimeMillis()) + "" + fileType;

			File uploadPath = new File(path);
			if(!uploadPath.exists()){
				uploadPath.mkdirs();
			}

			String filepath = path + File.separator + uploadFileName;
			image.setFileName(sname);
			image.setFilePath(uploadFileName);
			File savedFileName = new File(filepath);
			try (
					InputStream is = fileItem.getInputStream();
					FileOutputStream fos = new FileOutputStream(savedFileName);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					){


				byte[] buffer = new byte[1024];
				int len;

				while ((len = is.read(buffer)) >= 0) {
					bos.write(buffer, 0, len);
				}

			}  catch (Exception e) {
				log.error(e.getMessage(),e);
				throw new SystemException(e.getMessage(),e);
			}

			try {
				String formatPx = "32x32";
				String str = FileHandle.getResolution1(savedFileName);
				if (!formatPx.equals(str)) {
					rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					rd.add("product_images_swatch_invalid_picture_element");
					rd.putMessagesParamArray(
							"product_images_swatch_invalid_picture_element",
							new String[] { fileItem.getName() });
				}

			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new SystemException(e.getMessage(),e);
			}

			return image;
		}

		@Secured({"ROLE_SYSTEMADMIN", "ROLE_APPROVER"})
		@RequestMapping(value = "/productImportLogViewList", method = { RequestMethod.POST,
				RequestMethod.GET }, produces = { "application/json" })
		@ResponseBody
		public List<ProductImportLogVo> productImportLogViewList(@RequestParam(value = "importTypeArr", required = false) final String importTypeArr,
				@RequestParam(value = "importDateFrStr", required = false) final String importDateFrStr,
				@RequestParam(value = "importDateToStr", required = false) final String importDateToStr) throws SystemException {
			List<ProductImportLogVo> productImportLogVos = null;
			try {
				productImportLogVos = productImportLogService.getAllProductImportLog(importTypeArr,importDateFrStr,importDateToStr);
			} catch (Exception e) {
				log.error("productImportLogVos error:" + e.getMessage(), e);
				throw new SystemException(e.getMessage(),e);
			}
			return productImportLogVos;
		}



		@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
		@RequestMapping(value = "/export", method = { RequestMethod.POST,RequestMethod.GET }, produces = { "application/json" })
		@ResponseBody
		public ResponseData productExport(
				@RequestParam(value = "onlineUpdatedInd", required = false) final String onlineUpdatedInd,
				@RequestParam(value = "supplier", required = false) final String supplier,
				@RequestParam(value = "supplierProductCode", required = false) final String supplierProductCode,
				@RequestParam(value = "productCode", required = false) final String productCode,
				@RequestParam(value = "status", required = false) final String status,
				@RequestParam(value = "deliveryMode", required = false) final String deliveryMode,
				@RequestParam(value = "shortDescEn", required = false) final String shortDescEn) throws SystemException {
			UserVo userVo = sessionService.getCurrentUser();

			try  {

	            List<ProductExportModel> data = productInfoService.exportProductList(onlineUpdatedInd,supplier,
	            		supplierProductCode, productCode, status, deliveryMode,
	            		shortDescEn, userVo);

	            byte[] fileContent = exportExcel(data ) ;
	            ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);
	            responseData.setFileContent(fileContent);
	            return responseData;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new SystemException(e.getMessage(),e);
			}
		}

		@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
		@RequestMapping(value = "/downloadProductTemplate", method = { RequestMethod.POST,RequestMethod.GET })
		@ResponseBody
		public ResponseData downloadProductTemplate() throws SystemException {
			try  {
				byte[] fileContent = exportExcel(null) ;
				ResponseData<ProductInfoVo> responseData=(ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);
	            responseData.setFileContent(fileContent);
	            return responseData;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new SystemException(e.getMessage(),e);
			}
		}


		private byte[] exportExcel(List<ProductExportModel> data ) throws SystemException{

			UserVo userVo = sessionService.getCurrentUser();

			boolean supplierRole = false;
			String supplierId= null;
			if(userVo!=null && SUPPLIER.equals(userVo.getUserRole())){
				supplierRole = true;
				supplierId = userVo.getSupplierId();
			}

			String exportReportPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/report");
			String templatePath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/template");
            File reportFile = new File(exportReportPath);
            if (!reportFile.exists()) {
                 reportFile.mkdir();
            }
            String templateFile = getTemplateFile(supplierRole,templatePath);
           // String outFileName = productExportReportHandler.generateReportName("Product");

            try (
            	ByteArrayOutputStream os = new ByteArrayOutputStream();
            ) {
	            Workbook work= productExportReportHandler.generateXls(data, templateFile, supplierRole,supplierId);
//	            response.setContentType("application/msexcel");  
//	            response.setHeader("Content-disposition","attachment;filename="+outFileName);  
//	            response.setContentType("application/x-msdownload");
	            work.write(os);

	            return os.toByteArray();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new SystemException(e.getMessage(),e);
			}

		}

		private String getTemplateFile(boolean supplierRole,String templatePath ){
            if(supplierRole){
            	return templatePath +File.separator +"product_template_for_supplier.xlsx";
            }else
            	return templatePath +File.separator +"product_template.xlsx";
		}

		@Secured({"ROLE_SYSTEMADMIN", "ROLE_APPROVER"})
		@RequestMapping(value = "/checkFileExcit", method = { RequestMethod.POST,
				RequestMethod.GET }, produces = { "application/json" })
		@ResponseBody
		public boolean checkFileExist(@RequestParam(value = "fileName", required = false) final String fileName,
				@RequestParam(value = "importType", required = false) final String importType) throws SystemException {
			String importPath = "";
			if (StringUtils.isNotEmpty(importType)) {
				importPath = ImportLogPath.getLogType(importType)
						.getImportLogPathDesc();
			}

			File logFile = new File(importPath + File.separator + fileName);
			if(!logFile.exists()){
				importPath = ImportLogPath.PRODUCT_ACK_FROM_RETEK_ERROR_PATH
						.getImportLogPathDesc();
				logFile = new File(importPath + File.separator + fileName);
				return logFile.exists();
			}else{
				return true;
			}

//			boolean isFileExist = false;
//		    String[] fileArray = FileHandle.getFilesByPath(importPath);
//		    if(fileArray != null && fileArray.length > 0){
//		    	 for(String filePath : fileArray){
//		    		 if(StringUtils.isNotEmpty(fileName) && filePath.equals(fileName)){
//		    				 isFileExist = true;
//		    				 break;
//		    		 }
//		    	 }
//		    }
//			return isFileExist;
		}

		@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
		@RequestMapping(value = "/checkApproveProduct", method = { RequestMethod.POST,
				RequestMethod.GET }, produces = { "application/json" })
		@ResponseBody
		public List<String> checkApproveProduct(@RequestParam(value = "pIds", required = false) final String pIds) throws SystemException {
			List<String> idList = new ArrayList<>();
		    String[] pStrings = StringUtil.getAllProductId(pIds);
		    if(pStrings != null && pStrings.length > 0){
				for(String str : pStrings){
					if(StringUtils.isNotEmpty(str)){
						ProductInfoModel productInfoModel = productInfoService.selectByPrimaryKey(new BigDecimal(str));
						if(productInfoModel != null){
//							 if(productInfoModel.getDept() == null|| productInfoModel.getClass() == null|| productInfoModel.getSubClass() == null|| productInfoModel.getCasePackInner() == null || ("W".equals(productInfoModel.getDeliveryMode())&&productInfoModel.getSupplierLeadTime() == null)){
								 idList.add(productInfoModel.getSupplierProductCode());
//							 }
						}
					}
				}
			}
			return idList;
		}

}