package com.mall.b2bp.controllers.user;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.user.UserModel;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.user.UserVo;

@Controller
@RequestMapping(value = "/loginOld")
public class OLdLoginController extends BaseConroller {

	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "userService")
	private UserService userService;

	private static final Logger LOG = LoggerFactory 		.getLogger(OLdLoginController.class);
	
	
	private final static int LOGIN_FAIL_TIMES= 5;

	@RequestMapping(value = "/success", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<UserVo> success() {
		ResponseData<UserVo> responseData = new ResponseData<>();
		responseData.setReturnData(sessionService.getCurrentUser());
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		
		UserVo user = sessionService.getCurrentUser();
		if(user !=null){
			//clear loging fail times..
			UserModel record = new UserModel();
			record.setUserId(user.getUserId());
			record.setLastLoginDate(new Date());
			record.setLastUpdatedBy(user.getUserId());
			record.setLastUpdatedDate(new Date());
			try {
				userService.updateUserForLoginSuccess(record);
			} catch (ServiceException e) {
				LOG.error("updateUserForLoginSuccess error:"+e.getMessage(),e);
			}
			
		}
		
		return responseData;
	}

	@RequestMapping(value = "/failure", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<UserVo> failure(final HttpSession session) throws SystemException {
		String type = (String) session.getAttribute(ConstantUtil.LOGIN_FAILURE_TYPE);
		String userId = (String) session.getAttribute(ConstantUtil.LOGIN_FAILURE_NAME);
		String language = (String) session.getAttribute(ConstantUtil.LOGIN_LANGUAGE);
		
		ResponseData<UserVo> responseData=(ResponseData<UserVo>) responseDataService.getReturnData(UserVo.class);
		responseData.setLanguage(responseData.initLangByString(language));
		if (ConstantUtil.LOGIN_FAILURE_TYPE_DISABLED.equals(type)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			//responseData.setErrorMessage("User account is disabled.");
			responseData.add("username_disabled");
		} else if (ConstantUtil.LOGIN_FAILURE_TYPE_BADCREDENTIALS.equals(type)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			//responseData.setErrorMessage("Username or password invalid.");
			responseData.add("username_password_invalid");
		} else {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.setErrorMessage(type);
		}

		
		//updateUserForLoginFailTime
				if(StringUtils.isNotEmpty(userId)){
					UserModel record = new UserModel();
					record.setUserId(userId);
					record.setLastUpdatedBy(userId);
					record.setLastUpdatedDate(new Date());
					try {
						userService.updateUserForLoginFailTime(record);
					} catch (ServiceException e) {
						LOG.error("updateUserForLoginFailTime error:"+e.getMessage(),e);
					}
					
					
					UserVo userVo =userService.selectUserByUserId(userId);
					
					//debug
//					if(userVo!=null){
//						LOG.info(" Login fail times :"+ (userVo.getLoginFailTimes()!=null?userVo.getLoginFailTimes().intValue():null));
//					}
					if(userVo!=null && userVo.getLoginFailTimes()!=null && userVo.getLoginFailTimes().intValue()>=LOGIN_FAIL_TIMES){
					//	LOG.info(" Login fail times >=5....");
						
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//						responseData.setErrorMessage("Login times greater than 5 times, the account has been locked.");
						responseData.add("user_login_fail_times");
					}
					
				}
				
		return responseData;
	}

	@RequestMapping(value = "/currentUser", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public UserVo currentUser() {
		return sessionService.getCurrentUser();
	}
	
	@RequestMapping(value = "/updateLoginMsg", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData updateLoginMsg(@RequestParam(value = "lastLanguage", required = true) final String lastLanguage) throws SystemException {
		ResponseData<UserVo> responseData=(ResponseData<UserVo>) responseDataService.getReturnData(UserVo.class);
    	UserVo userVo = sessionService.getCurrentUser();
    	if(userVo==null){
    		responseData.add("user_login_expire");
    		responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		return responseData;
    	}
    	UserModel userModel=new UserModel();
    	userModel.setId(userVo.getId());
    	userModel.setSessionLang(lastLanguage);
    	userModel.setLastLoginDate(new Date());
    	userService.updateByPrimaryKeySelective(userModel);
		return responseData;
	}
	
	
}
