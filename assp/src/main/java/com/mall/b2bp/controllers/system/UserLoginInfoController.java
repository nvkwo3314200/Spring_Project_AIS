package com.mall.b2bp.controllers.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.services.system.UserLoginInfoService;

@Controller
@RequestMapping(value = "/userLoginInfo")
public class UserLoginInfoController extends BaseController{
	//private static final Logger LOG = LoggerFactory.getLogger(UserLoginInfoController.class);
	 
	@Resource(name = "userLoginInfoService")
	 private UserLoginInfoService userLoginInfoService;
	 
	 @RequestMapping(value = "/search", method = { RequestMethod.POST,
				RequestMethod.GET }, produces = { "application/xml",
				"application/json" })
	 @ResponseBody
	public List<UserInfoModel> search(@RequestBody final UserInfoModel userInfoModel) throws SystemException{
	 	try {
	 		if(userInfoModel.getRoleCode()!=null){
	 			userInfoModel.setRoleCode(userInfoModel.getRoleCode().toUpperCase());	 			
	 		}
	 		if(userInfoModel.getPlantCode()!=null){
	 			userInfoModel.setPlantCode(userInfoModel.getPlantCode().toUpperCase());
	 		}
	 		if(userInfoModel.getProdLn()!=null){
	 			userInfoModel.setProdLn(userInfoModel.getProdLn().toUpperCase());
	 		}
	 		List<UserInfoModel> list=userLoginInfoService.search(userInfoModel);
	 		return list;				
		} catch (Exception e) {
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	 }
}
