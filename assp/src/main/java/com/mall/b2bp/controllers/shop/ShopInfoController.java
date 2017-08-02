package com.mall.b2bp.controllers.shop;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.system.BaseController;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.shop.ShopInfoModel;
import com.mall.b2bp.services.shop.ShopInfoService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.shop.CategoryWsDTO;
import com.mall.b2bp.vos.shop.ShopInfoViewVo;

@Controller
@RequestMapping(value = "/shop")
										
public class ShopInfoController extends BaseController{
	//private static final Logger LOG = LoggerFactory.getLogger(ShopInfoController.class);
	
	@Resource
	private ShopInfoService shopInfoService;
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/saveShop", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ShopInfoViewVo> saveShopInfo(@RequestBody final ShopInfoViewVo shopInfoViewVo) throws SystemException, ServiceException {
		ResponseData<ShopInfoViewVo> responseData = null ;
		
		try {
			responseData = responseDataService.getResponseData();
			shopInfoService.saveShopInfo(shopInfoViewVo, responseData);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(),e);
		}
		return responseData;
		
	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/shopViewList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<ShopInfoModel> shopViewList(
			@RequestParam(value = "shopId", required = false) final String shopId,
			@RequestParam(value = "mallId", required = false) final String mallId,
			@RequestParam(value = "shopCode", required = false) final String shopCode,
			@RequestParam(value = "shopName", required = false) final String shopName,
			@RequestParam(value = "respPerson", required = false) final String respPerson,
			@RequestParam(value = "contactPerson", required = false) final String contactPerson,
			@RequestParam(value = "contactEmail", required = false) final String contactEmail,
			@RequestParam(value = "websiteName", required = false) final String websiteName,
			@RequestParam(value = "telNo", required = false) final String telNo) throws SystemException {
		List<ShopInfoModel> shopInfoModels = null;
		try {
			shopInfoModels = shopInfoService.selectShopList(shopId,mallId,shopCode, shopName, respPerson, contactPerson, contactEmail, websiteName, telNo);
		} catch (Exception e) {
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return shopInfoModels;
	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/getAllShop", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<ShopInfoModel> getAllShop() throws SystemException {
		List<ShopInfoModel> shopInfoModels = null;
		try {
			shopInfoModels = shopInfoService.getAllShop();
		} catch (Exception e) {
			LOG.error("shopInfoModels error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return shopInfoModels;
	}
	
	@RequestMapping(value = "/search", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<ShopInfoViewVo> search(@RequestBody final ShopInfoViewVo shopVo)
			throws SystemException {
		ResponseData<ShopInfoViewVo> responseData = responseDataService.getResponseData();
		try {		
			
			List<ShopInfoViewVo> list = shopInfoService.search(shopVo);
			responseData.setReturnDataList(list);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS );
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/deleteShop", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<ShopInfoViewVo> deleteShop(
			@RequestParam(value = "shopIds", required = false) final String shopIds) throws SystemException {
		ResponseData<ShopInfoViewVo> responseData = null; 
		try {
			responseData = responseDataService.getResponseData();
			shopInfoService.deleteByPrimaryKey(shopIds, responseData);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		    throw new SystemException(e.getMessage(),e);
		}

		return responseData;

	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/shopDetail", method = { RequestMethod.POST,RequestMethod.GET }, produces = { "application/xml","application/json" })
	@ResponseBody
	public ShopInfoViewVo shopDetail(
			@RequestParam(value = "shopId", required = false) final String shopId) throws SystemException {
		ShopInfoViewVo shopVo = null;
		try{
			shopVo = shopInfoService.getShopViewVoById(shopId);
		}catch(Exception e){
			 LOG.error(e.getMessage(), e);
			 throw new SystemException(e.getMessage(),e);
		}
		
		return shopVo;
	}
	
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/shopInterface", method = { RequestMethod.POST,RequestMethod.GET }, produces = { "application/xml","application/json" })
	@ResponseBody
	public ShopInfoViewVo shopInterface(
			@RequestParam(value = "shopId", required = false) final String shopId, HttpServletRequest request) throws SystemException {
		ShopInfoViewVo shopVo = null;
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		try{
			shopVo = shopInfoService.selectInterfaceByKey(shopId, basePath);
		}catch(Exception e){
			 LOG.error(e.getMessage(), e);
			 throw new SystemException(e.getMessage(),e);
		}
		return shopVo;
	}
	
	@RequestMapping(value = "/getCategoryList", method = { RequestMethod.POST,RequestMethod.GET }, produces = { "application/xml","application/json" })
	@ResponseBody
	public ResponseData<CategoryWsDTO> getCategoryList() {
		ResponseData<CategoryWsDTO> responseData = responseDataService.getResponseData();
		List<CategoryWsDTO> voList = shopInfoService.getCategoryList();
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		responseData.setReturnDataList(voList);
		return responseData;
	}
	
	
	@RequestMapping(value = "/check", method = { RequestMethod.POST,RequestMethod.GET }, produces = { "application/xml","application/json" })
	@ResponseBody
	public ResponseData<ShopInfoViewVo> check(@RequestBody final ShopInfoViewVo vo) {
		ResponseData<ShopInfoViewVo> responseData = responseDataService.getResponseData();
		boolean flag = shopInfoService.checkExist(vo);
		if(flag) responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		return responseData;
	}
	
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
    @RequestMapping(value = "/uploadLogo", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public ResponseData<ShopInfoViewVo> upload(HttpServletRequest request) {
		
		ShopInfoViewVo viewVo = new ShopInfoViewVo();
        ResponseData<ShopInfoViewVo> responseData = null;
        String temporaryDirectory = ResourceUtil.getSystemConfig().getProperty("upload_file_path");
        String filePath = temporaryDirectory; 
       
        try {
            responseData = responseDataService.getResponseData();
            // Update User Search Restrictions
            
            responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
            responseData.add("upload_success");
            
            Map map = FileHandle.getFileItem(request);
            FileItem fileItem = (FileItem) map.get("file");
            String shopCode = (String)map.get("shopCode");
            String shopId = (String)map.get("shopId");
            
            if(StringUtils.isEmpty(shopId) || StringUtils.isEmpty(shopCode) || "undefined".equals(shopId) || "undefined".equals(shopCode)) {
            	responseData.add("unknown_shop");
            	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            	return responseData;
            }
            if (!new File(filePath).exists()) {
                new File(filePath).mkdir();
            }
            
            if (null == fileItem) {
                responseData.add("file_not_fount_err");
                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                return responseData;
            }

            
            String name = fileItem.getName();

            String suffix = null;
            if(name != null && name.indexOf(".")> -1) {
            	int start = name.indexOf(".");
            	suffix = name.substring(start, name.length());
            }
            filePath = filePath +"//"+shopCode+"_"+shopId+"_logo"+suffix;
            
            File file = new File(filePath);
            byte[] bytes = null;
            if (!fileItem.isFormField()) {
                bytes = fileItem.get();
                fileItem.getName();
            }
            if (bytes != null) {
                FileUtils.writeByteArrayToFile(file, bytes);
            }
            viewVo.setLogoName(shopCode+"_"+shopId+"_logo"+suffix);
            responseData.setReturnData(viewVo);
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
            responseData.add("system_error");
            responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            return responseData;
        }
        return responseData;
    }
	
}
