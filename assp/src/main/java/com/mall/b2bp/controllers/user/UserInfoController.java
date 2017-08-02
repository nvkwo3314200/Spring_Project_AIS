package com.mall.b2bp.controllers.user;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.models.lov.LovModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.services.product.ProductInfoService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.supplier.SupplierVo;
import com.mall.b2bp.vos.user.SupplierLovVo;
import com.mall.b2bp.vos.user.UserViewVo;
import com.mall.b2bp.vos.user.UserVo;



@Controller
@RequestMapping(value = "/user")
public class UserInfoController extends BaseConroller{
	private static final Logger LOG = LoggerFactory.getLogger(UserInfoController.class);

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "lovService")
	private LovService lovService;



	@Resource(name="brandService")
	private BrandService brandService;


	@Resource(name = "supplierService")
	private SupplierService supplierService;

	@Resource(name = "productInfoService")
	private ProductInfoService productInfoService;

	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value = "/userViewList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<UserVo> userViewList(
			@RequestParam(value = "supplierId", required = false) final String supplierId,
			@RequestParam(value = "userName", required = false) final String userName,
			@RequestParam(value = "userId", required = false) final String userId,
			@RequestParam(value = "userRole", required = false) final String userRole,
			@RequestParam(value = "email", required = false) final String email,
			@RequestParam(value = "activate", required = false) final String activate,
			@RequestParam(value = "contactNo", required = false) final String contactNo) throws SystemException {
		List<UserVo> userVos = null;
		try {
			userVos = userService.selectUserList(userId, userName, userRole, email, contactNo, activate, supplierId);
		} catch (Exception e) {
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return userVos;
	}

	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value = "/saveUser", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<UserViewVo> saveUser(@RequestBody final UserViewVo userVo) throws SystemException, ServiceException {
			ResponseData<UserViewVo> responseData = userService.checkUserSave(userVo);
		 	responseData.setResourceBundleMessageSource(messageSource);
		 	userService.saveUser(userVo, responseData);
			return responseData;
	}

	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value = "/deleteUser", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData deleteUser(
			@RequestParam(value = "userIds", required = false) final String userIds) throws SystemException {
		ResponseData responseData = new ResponseData();
		try {
			userService.deleteByPrimaryKey(userIds);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		    throw new SystemException(e.getMessage(),e);
		}

		return responseData;

    }

    @Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/userDetail", method = {RequestMethod.POST, RequestMethod.GET}, produces = {"application/xml", "application/json"})
    @ResponseBody
    public UserViewVo userDetail(
            @RequestParam(value = "userId", required = false) final String userId, @RequestParam(value = "supplierId", required = false) final String supplierId) throws SystemException {
        UserViewVo userVo = null;
        try {
            userVo = userService.getUserViewVoById(userId);
            if (userVo == null)
                return null;
            userVo.setSupplierId(supplierId);


            if (userVo != null) {

                if (StringUtils.isNotEmpty(userVo.getSupplierId())) {
                    SupplierVo vo = supplierService.selectByPrimaryKey(supplierId);

                    // error reason
                    ProductInfoModel productInfoModel = productInfoService.getSupplierProductInfoModelBySupplierId(supplierId);
                    if (productInfoModel != null) {

                        vo.setDisableDeliveryFee(true);
                        String failedReason = productInfoModel.getFailedReason();
                        if (StringUtils.isNotEmpty(failedReason)) {
                            vo.setFailedReason(failedReason);
                        }
                    } else {
                        vo.setDisableDeliveryFee(false);
                    }

                    userVo.setSupplierVo(vo);
                }
                userService.insertBrandAndDeptAndLov(userVo);
            }


        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SystemException(e.getMessage(), e);
        }

        return userVo;
    }

    @Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/categoryList", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    public List<SupplierLovVo> categoryList() throws SystemException {
        List<SupplierLovVo> lovList = new ArrayList<>();
        List<LovModel> list = null;
        try {
            list = lovService.getLovsByLovId(LovType.ESTORE_CATEGORY);
            if (CollectionUtils.isNotEmpty(list)) {
                for (LovModel lovModel : list) {
                    SupplierLovVo supplierLovVo = new SupplierLovVo();
                    supplierLovVo.setLovCode(lovModel.getLovValue());
                    supplierLovVo.setLovValue(lovModel.getLovValue() + "-" + lovModel.getLovDesc());
                    lovList.add(supplierLovVo);
                }
            }
        } catch (Exception e) {
            LOG.error("lovList error:" + e.getMessage(), e);
            throw new SystemException(e.getMessage(), e);
        }
        return lovList;
    }

    @Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/brandList", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    public List<BrandModel> brandList() throws SystemException {
        List<BrandModel> brandList = null;
        try {
            brandList = brandService.selectAllBrandList();
        } catch (Exception e) {
            LOG.error("brandList error:" + e.getMessage(), e);
            throw new SystemException(e.getMessage(), e);
        }
        return brandList;
    }
}
