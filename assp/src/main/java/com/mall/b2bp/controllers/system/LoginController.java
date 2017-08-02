package com.mall.b2bp.controllers.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.PowerModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.services.shop.ShopInfoService;
import com.mall.b2bp.services.system.UserManagerService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	
	@Resource(name = "userManagerService")
	private UserManagerService userManagerService;
	
	@Resource
	private ShopInfoService shopInfoService;
	
	/*
	@Resource(name = "systemParametersService")
	private SystemParametersService systemParametersService;
	*/
	//private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	private final static int LOGIN_FAIL_TIMES= 5;

	@RequestMapping(value = "/success", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<Map<String, Object>> success(HttpServletRequest request) {
		ResponseData<Map<String, Object>> responseData = responseDataService.getResponseData();
		UserInfoModel user = sessionService.getCurrentUserInfo();
		String language = (String) request.getSession().getAttribute(ConstantUtil.LOGIN_LANGUAGE);
		responseData.setLanguage(responseDataService.langToLocale(language, "_"));
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		if(user !=null){
			//init login ip lh-10-13
			user.setIp(request.getRemoteAddr());
			
			if(user.getShopId() != null) {
				boolean flag = shopInfoService.validateIP(user);
				if(!flag) {
					responseData = (ResponseData<Map<String, Object>>) updateFailTimes(user, responseData);
					SecurityContextHolder.getContext().setAuthentication(null);
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseData.add("ip_restriction");
					return responseData; 
				}
				sessionService.setSupplier(user);
			}
			
			if(user.getMallId() != null) {
				sessionService.setMall(user);
			}
			
			sessionService.initCurrentUser(user);
			sessionService.initCurrentUserPower(user.getUserCode());
			
			
			map.put("user", user);
			map.put("powers", sessionService.getCurrentUserAllFuncPower(user.getUserCode()));
			//clear loging fail times..(6-30)
			UserInfoModel record = new UserInfoModel();
			record.setUserCode(user.getUserCode());
			record.setLastLoginDate(new Date());
			record.setLastUpdatedBy(user.getUserCode());
			record.setLastUpdatedDate(new Date());
			try {
				userManagerService.updateUserLoginSuccess(record);
			} catch (ServiceException e) {
				LOG.error("updateUserLoginSuccess error:"+e.getMessage(),e);
			}
		}
		responseData.setReturnData(map);
		return responseData;
	}

	@RequestMapping(value = "/failure", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<UserInfoModel> failure(final HttpSession session) throws SystemException {
		String type = (String) session.getAttribute(ConstantUtil.LOGIN_FAILURE_TYPE);
		String userCode = (String) session.getAttribute(ConstantUtil.LOGIN_FAILURE_NAME);
		String language = (String) session.getAttribute(ConstantUtil.LOGIN_LANGUAGE);
		ResponseData<UserInfoModel> responseData = responseDataService.getResponseData();
		responseData.setLanguage(responseDataService.langToLocale(language, "_"));
		
		//responseData.setLanguage(responseData.initLangByString(language));
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
		if(StringUtils.isNotEmpty(userCode)){
			//Determine whether the user has been locked
			UserInfoModel user =userManagerService.getUserInfo(userCode);
			if(user != null){
				responseData = (ResponseData<UserInfoModel>) updateFailTimes(user, responseData);
			}
			
			//	old
//			if(userVo!=null && userVo.getLoginFailTimes()!=null && userVo.getLoginFailTimes().intValue()>=LOGIN_FAIL_TIMES){
//				LOG.info(" Login fail times >=5....");
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//				responseData.setErrorMessage("Login times greater than 5 times, the account has been locked.");
//			}		
		}		
		return responseData;
	}

	@RequestMapping(value = "/currentUser", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public UserInfoModel currentUser() {
		return sessionService.getCurrentUser();
	}
	
	@RequestMapping(value = "/updateLoginMsg", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<UserInfoModel> updateLoginMsg(@RequestParam(value = "lastLanguage", required = true) final String lastLanguage) throws SystemException {
		ResponseData<UserInfoModel> responseData = responseDataService.getResponseData();
		UserInfoModel user = sessionService.getCurrentUser();
    	if(user==null){
    		responseData.add("user_login_expire");
    		responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		return responseData;
    	}
//    	UserModel userModel=new UserModel();
//    	userModel.setId(user.getId());
//    	userModel.setSessionLang(lastLanguage);
//    	userModel.setLastLoginDate(new Date());
//    	userService.updateByPrimaryKeySelective(userModel);
		user.setSessionLang(lastLanguage);
		user.setLastLoginDate(new Date());
		try {
			userManagerService.updateUserLang(user);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseData;
	}
	
	@RequestMapping(value="/getCurrentPowers", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<PowerModel> getCurrentPowers(@RequestBody final PowerModel model) throws ServiceException {
		
		ResponseData<PowerModel> responseData = responseDataService.getResponseData();
		
		UserInfoModel user = sessionService.getCurrentUser();
		
        if (user == null) {
        	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		responseData.add("change_password_invalid_login");
    		return responseData;
        }
        
        model.setUserId(user.getId());
        
		PowerModel power = sessionService.getCurrentUserPower(model.getUrl());
		responseData.setReturnData(power);
        
		return responseData;
	}
	
	@RequestMapping(value="/initSysDateFormat", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<String> initSysDateFormat() throws ServiceException {
		
		ResponseData<String> responseData = responseDataService.getResponseData();
		/*
		SystemParametersModel model = new SystemParametersModel();
		model.setCode("SYS_DATE_FORMAT");
		
		String sysDateFormat = "yyyy-MM-dd";
		
		try {
			
			List<SystemParametersModel> modelList = systemParametersService.selectList(model);
			
			if(modelList != null){
				sysDateFormat = modelList.get(0).getAttrib01();
			}
			
			responseData.setReturnData(sysDateFormat);
			
		} catch (ServiceException e) {
			
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
			
		}
		*/
		return responseData;
	}
	
	public ResponseData<?> updateFailTimes(UserInfoModel user, ResponseData<?> responseData) {
		UserInfoModel record = new UserInfoModel();
		record.setUserCode(user.getUserName());
		record.setLastUpdatedBy(user.getUserName());
		record.setLastUpdatedDate(new Date());
		try {
			userManagerService.updateUserLoginFail(record);
		} catch (ServiceException e) {
			LOG.error("updateUserForLoginFailTime error:"+e.getMessage(),e);
		}
		if(user.getLoginFailTimes().intValue()+1>=LOGIN_FAIL_TIMES){
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("user_login_fail_times");
		}
		LOG.info(" Login fail times :"+ (user.getLoginFailTimes().intValue()+1));
		return responseData;
	}
	
}
