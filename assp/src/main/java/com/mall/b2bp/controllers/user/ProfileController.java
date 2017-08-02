package com.mall.b2bp.controllers.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.controllers.product.ProductController;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.user.UserModel;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.user.UserVo;

@Controller("ProfileController")
@RequestMapping(value = "/profile")
public class ProfileController extends BaseConroller {
	private static final Logger LOG = LoggerFactory
			.getLogger(ProductController.class);
	
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/editUser", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<UserModel> editUser(@RequestBody final UserModel userModel) {

		ResponseData<UserModel> responseData = new ResponseData<>();
		responseData.setResourceBundleMessageSource(messageSource);
		UserVo userVo= sessionService.getCurrentUser();

        if (userVo == null) {
        	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		responseData.add("change_password_invalid_login");
    		return responseData;
        }
        
        if(!StringUtil.checkEmail(userModel.getEmail())){
        	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("email_invalid_email");
			return responseData;
        }
	

		userModel.setLastUpdatedBy(userVo.getUserName());
		userModel.setLastUpdatedDate(new Date());

		Map<String, Object> map = new HashMap();
		map.put("id", userVo.getId());
		map.put("email", userModel.getEmail());
		List<UserModel> userModelList = userService.getUserModeListByEmail(map);
		if (userModelList != null && !userModelList.isEmpty()) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("email_exist");
			return responseData;
		}

		int result = userService.updateByPrimaryKeySelective(userModel);
		if (result != 0) {
			UserModel user = userService.selectByPrimaryKey(userVo.getId());
			responseData.setReturnData(user);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} else {
			responseData.add("email_update_failed");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		}


		return responseData;

	}
	
	
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getUser", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<UserModel> getUser()throws SystemException {
		ResponseData<UserModel> responseData = new ResponseData<>();
		responseData.setResourceBundleMessageSource(messageSource);
		UserVo userVo= sessionService.getCurrentUser();

        if (userVo == null) {
        	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		responseData.add("change_password_invalid_login");
    		return responseData;
        }
        
		try {
			UserModel user = userService.selectByPrimaryKey(userVo.getId());
			if (user != null) {
				responseData.setReturnData(user);
				responseData.setErrorType("success");
			} else {
				responseData.setErrorType("danger");
			}
			return responseData;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}
	
}
