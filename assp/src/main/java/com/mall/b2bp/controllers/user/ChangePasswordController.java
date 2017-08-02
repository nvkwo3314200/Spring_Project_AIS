package com.mall.b2bp.controllers.user;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.user.UserVo;

@Controller
@RequestMapping(value = "/changePassword")
public class ChangePasswordController extends BaseConroller {
	   private static final Logger LOG = LoggerFactory.getLogger(ChangePasswordController.class);
	@Resource(name = "userService")
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Resource(name = "sessionService")
	SessionService sessionService;

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/change", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<UserVo> changeUserPassword(
			@RequestParam(value = "newPassword", required = false) final String newPassword,
			@RequestParam(value = "repeatPassword", required = false) final String repeatPassword) throws SystemException {

		ResponseData<UserVo> responseData=(ResponseData<UserVo>) responseDataService.getReturnData(UserVo.class);
		UserVo userVo = sessionService.getCurrentUser();
			if (userVo == null) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("change_password_invalid_login");
				return responseData;
		}

		
		boolean flag=validateUserInfo(newPassword, repeatPassword, responseData);
		if(!flag){
			return responseData;
		}
		

			boolean result = false;
			try {
				result = userService.changePassword(userVo.getId(),
						newPassword, responseData);
			} catch (ServiceException e) {
				LOG.error(e.getMessage(),e);
				throw new SystemException(e.getMessage(),e);
			}
			if (result) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

			} else {

				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);

			}
		

		return responseData;

	}

	private boolean  validateUserInfo(final String newPassword,
			final String repeatPassword, ResponseData<UserVo> responseData) {
		if (StringUtils.isEmpty(newPassword)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_new_password_require");
			return false;
		}

		if (StringUtils.isEmpty(repeatPassword)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_repeat_password_require");
			return false;
		}
		//a.	At least 6 characters in length
		if (newPassword.length()<6) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_length_invalid");
			return false;
		}
		
		if (!newPassword.matches(".*?[a-z]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_lower_invalid");
			return false;
		}
		//b.	Contains upper case letter (A-Z)

		if (!newPassword.matches(".*?[A-Z]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_upper_invalid");
			return false;
		}
		//d.	Contains numeral (0-9)
		if (!newPassword.matches(".*?[\\d]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_number_invalid");
			return false;
		}
		
		
		if (!newPassword.equals(repeatPassword)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_invalid");
			return false;
		}
		return true;
	}

}
