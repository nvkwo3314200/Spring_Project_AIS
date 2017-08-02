package com.mall.b2bp.controllers.user;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.user.UserVo;

@Controller
@RequestMapping(value = "/forget")
public class ForgetController extends BaseConroller {

	@Resource(name = "userService")
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/sendmail", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<UserVo> forgetPassword(
			@RequestParam(value = "email", required = false) final String email,
			@RequestParam(value = "language", required = false) final String language) throws SystemException {

		ResponseData<UserVo> responseData=(ResponseData<UserVo>) responseDataService.getReturnData(UserVo.class);
		responseData.setLanguage(responseDataService.langToLocale(language, "_"));
		if (StringUtils.isEmpty(email)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_email_require");
			return responseData;
		}
		
		if (!isEmail(email)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_invalid_email");
			return responseData;
		}
		
		List<UserVo> voList = userService.selectUserList(null, null, null, email, null, null, null);
		if(voList == null || voList.size() ==0) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_email_not_registered");
			return responseData;
		}
	
			
		boolean result=userService.sendForgetEmail(email);
		if (result) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			responseData.add("forget_password_send_email_success");
		
		} else {

			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_invalid_userid_email");
		}

		return responseData;

	}
	
	@RequestMapping(value = "/changePassword", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<UserVo> resetPassword(
			@RequestParam(value = "token", required = true) final String token,@RequestParam(value = "newPassword", required = false) final String newPassword,@RequestParam(value = "repeatPassword", required = false) final String repeatPassword) throws SystemException {

		ResponseData<UserVo> responseData=(ResponseData<UserVo>) responseDataService.getReturnData(UserVo.class);

	
		if(StringUtils.isEmpty(newPassword)){
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_new_password_require");
			return responseData;
		}
	
		
		if(StringUtils.isEmpty(repeatPassword)){
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_repeat_password_require");
			return responseData;
		}
		//a.	At least 6 characters in length
		if (newPassword.length()<6) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_length_invalid");
			return responseData;
		}
		
		if (!newPassword.matches(".*?[a-z]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_lower_invalid");
			return responseData;
		}
		//b.	Contains upper case letter (A-Z)

		if (!newPassword.matches(".*?[A-Z]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_upper_invalid");
			return responseData;
		}
		//d.	Contains numeral (0-9)
		if (!newPassword.matches(".*?[\\d]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_number_invalid");
			return responseData;
		}
		
		
		if(!newPassword.equals(repeatPassword)){
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_invalid");
			return responseData;
		}
		
			
		boolean result=userService.changePasswordByToken(token, newPassword, responseData);
		if (result) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		
		} else {

			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
	
		}

		return responseData;

	}
	

    private boolean isEmail(String email){
          if (StringUtils.isEmpty(email)) {
        	  return false;  
          }
          Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
          Matcher m = p.matcher(email);
          return m.matches();
         }

}
