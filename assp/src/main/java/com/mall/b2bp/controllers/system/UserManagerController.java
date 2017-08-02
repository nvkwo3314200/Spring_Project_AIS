package com.mall.b2bp.controllers.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.services.system.RoleManagerService;
import com.mall.b2bp.services.system.UserManagerService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;

@Controller
@RequestMapping(value = "/userManager")
public class UserManagerController extends BaseController{
	//private static final Logger LOG = LoggerFactory.getLogger(UserManagerController.class);
    private UserInfoModel userInfoModel =new UserInfoModel();
	@Resource(name = "userManagerService")
	private UserManagerService userManagerService;
	@Resource(name = "roleManagerService")
	private RoleManagerService roleManagerService;
	
	@RequestMapping(value = "/userList", 
					method = { RequestMethod.POST,RequestMethod.GET }, 
					produces = { "application/json" })
	@ResponseBody
	public List<UserInfoModel> userViewList(@RequestBody final UserInfoModel userVo) 
		throws SystemException {
		List<UserInfoModel> userVos = null;
		try {
			userInfoModel=userVo;
			userVos = userManagerService.selectUserList(userVo);
		} catch (Exception e) {
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return userVos;
	}
	
	@RequestMapping(value = "/search", 
			method = { RequestMethod.POST,RequestMethod.GET }, 
			produces = { "application/json" })
	@ResponseBody
	public List<UserInfoModel> search(@RequestBody final UserInfoModel userVo) 
		throws SystemException {
		List<UserInfoModel> userVos = null;
		try {
			userInfoModel=userVo;
			userVos = userManagerService.search(userVo);
		} catch (Exception e) {
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return userVos;
	}
	
	@RequestMapping(value = "/saveUser", method = {RequestMethod.POST,
			RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<UserInfoModel> saveUser(
			@RequestBody final UserInfoModel user) 
		throws SystemException, ServiceException {
		ResponseData<UserInfoModel> responseData = responseDataService.getResponseData();
		 	boolean falg=userManagerService.chack(user);
		 	if(falg==false){
			 	boolean error = userManagerService.checkUserSave(user,responseData);
				 	if(!error){
				 	//liyuan 20160929
				 	if("N".equals(user.getAccountLock())) {
				 		user.setLoginFailTimes(0);
				 	}else {
				 		user.setLoginFailTimes(5);
				 	}
				 	userManagerService.addUser(user, responseData);
				 	// by lh -10-12
	//			 	sessionService.initCurrentUser();
				 	// by lh -8-19
	//				sessionService.initCurrentUserPower();
			 	
			 	}
		 	}
			return responseData;
	}
	
	
	@RequestMapping(value = "/userDetail", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml","application/json" })
	@ResponseBody
	public UserInfoModel userDetail(
			@RequestParam(value = "userId", required = false) final Integer userId) throws SystemException {
		UserInfoModel userVo = null;
		try{
			if(userId==null){
				userVo=new UserInfoModel();
				userVo.setUserActiveInd("Y");
				userVo.setAccountLock("N"); //liyuan 20160929
			}else{
				userVo = userManagerService.getUserViewVoById(userId);		
				userVo.setSubRoleModelList(roleManagerService.getRoleUserlist(userId));	
				if(userVo != null  && userVo.getLoginFailTimes() != null && userVo.getLoginFailTimes() >= 5) {
					userVo.setAccountLock("Y");
				}else {
					userVo.setAccountLock("N");
				}
		}
		
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			 throw new SystemException(e.getMessage(),e);
		}
		return userVo;
	}
	

	

	
	@RequestMapping(value = "/deleteUser", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData deleteUser(
			@RequestParam(value = "userIds", required = false) final String userIds) throws SystemException {
		ResponseData responseData = responseDataService.getResponseData();
		try {
			userManagerService.deleteByPrimaryKey(userIds);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		    throw new SystemException(e.getMessage(),e);
		}

		return responseData;

	}
	
	
	@RequestMapping(value = "/saveAddRole", method = {RequestMethod.POST,
			RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<UserInfoModel> saveAddRole(
			@RequestBody final UserInfoModel userVo) 
		throws SystemException, ServiceException {
			ResponseData<UserInfoModel> responseData = userManagerService.checkUserRoleSave(userVo);
			if(responseData.getErrorType() == null || !responseData.getErrorType().equals(ConstantUtil.ERROR_TYPE_DANGER)) {
				responseData.setErrorType("success");
			}
			return responseData;
	}
	
	@RequestMapping(value = "/cancel", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public UserInfoModel cancel(){
		return userInfoModel;
	}
	
	@RequestMapping(value = "/deleteRolePop", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<UserInfoModel> deleteRolePop(
			@RequestBody final UserInfoModel userVo) throws SystemException {
		ResponseData<UserInfoModel> responseData = userManagerService.deletebufferRoles(userVo);
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		return responseData;

	}
	
}
