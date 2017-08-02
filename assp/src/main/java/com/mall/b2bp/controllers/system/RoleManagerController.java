package com.mall.b2bp.controllers.system;

import java.util.Date;
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
import com.mall.b2bp.models.system.RoleMenuModel;
import com.mall.b2bp.models.system.RoleModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.services.system.RoleManagerService;
import com.mall.b2bp.services.system.RoleMenuService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;

@Controller
@RequestMapping(value = "/roleManager")
public class RoleManagerController extends BaseController{
	//private static final Logger LOG = LoggerFactory.getLogger(RoleManagerController.class);
	public int id=0;

	@Resource(name = "roleManagerService")
	private RoleManagerService roleManagerService;
	@Resource(name = "roleMenuService")
	private RoleMenuService roleMenuService;
	
	@RequestMapping(value = "/roleList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<RoleModel> roleViewList(
			@RequestBody final RoleModel model)
		throws SystemException {
		List<RoleModel> roleVos = null;
		try {
			if(model.getRoleName()!=null){
				
				model.setRoleName(model.getRoleName().toUpperCase());
			}
			roleVos = roleManagerService.selectRoleList(model);
		} catch (Exception e) {
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return roleVos;
	}
	
	@RequestMapping(value = "/searchRole", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<RoleModel> searchRole(
			@RequestBody final RoleModel model)
		throws SystemException {
		List<RoleModel> roleVos = null;
		try {
			if(model.getRoleName()!=null){				
				model.setRoleName(model.getRoleName().toUpperCase());
			}
			roleVos = roleManagerService.searchRole(model);
		} catch (Exception e) {
			LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return roleVos;
	}
	
	@RequestMapping(value = "/saveRole", method = {RequestMethod.POST,
			RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<RoleModel> saveRole(
			@RequestBody final RoleModel roleVo) 
		throws SystemException, ServiceException {
			UserInfoModel user= sessionService.getCurrentUser();
			ResponseData<RoleModel> responseData = responseDataService.getResponseData();
		 	
		 	RoleModel rolevo=new RoleModel();
		 	if(roleVo.getRoleActive()==null&&roleVo.getInactiveDate()==null){
		 		roleVo.setRoleActive("N");
		 		roleVo.setInactiveDate(new Date());
		 	}else if(roleVo.getRoleActive()==null&&roleVo.getInactiveDate()!=null){
		 		roleVo.setRoleActive("N");
		 	}else if(roleVo.getRoleActive().equals("Y")){
		 		roleVo.setInactiveDate(null);
		 	}
		 	if(roleVo.getRoleCode()!=null){
		 		roleVo.setRoleCode(roleVo.getRoleCode().toUpperCase());
		 	}
		 		boolean chacknull=roleManagerService.chack(roleVo);		 	
		 	if(roleVo.getId()==null){
		 		responseData=roleManagerService.checkroleSave(roleVo);
		 		
		 	}		 			 	
		 	if(responseData.getErrorType()==null&&chacknull==false){			 		
		 		id=roleManagerService.addRole(roleVo, responseData);
		 		List<RoleMenuModel> list=roleVo.getList();
			 	for(RoleMenuModel roleMenuModel: list){
			 		if(roleMenuModel.getCanSelect()==null){
			 			roleMenuModel.setCanSelect("N");
			 		}
			 		if(roleMenuModel.getCanDelete()==null){
			 			roleMenuModel.setCanDelete("N");
			 		}
			 		if(roleMenuModel.getCanInsert()==null){
			 			roleMenuModel.setCanInsert("N");
			 		}
			 		if(roleMenuModel.getCanUpdate()==null){
			 			roleMenuModel.setCanUpdate("N");
			 		}
			 		if(roleMenuModel.getCanAudit()==null){
			 			roleMenuModel.setCanAudit("N");
			 		}
			 		if(roleMenuModel.getCanView()==null){
			 			roleMenuModel.setCanView("N");
			 		}
			 		int i=0;
					if(roleMenuModel.getRoleId()==null){					
						roleMenuModel.setRoleId(id);
					}else{
						roleMenuModel.setRoleId(roleVo.getId());
					}
				if(roleMenuModel.getId()==null){
					 i=roleMenuService.creatRolePermission(roleMenuModel);
				}else{
					 i=roleMenuService.updateRolePermission(roleMenuModel);
				}
				if (i != 0) {				
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				} 
			 	}
		 	}
		 	
		 	if(roleVo.getId()!=null){
		 	rolevo=roleManagerService.getRoleViewVoById(roleVo.getId());
		 	rolevo.setFalg(true);
		 	rolevo.setLanguage(user.getSessionLang());
		 	rolevo.setList(roleMenuService.selectMenuModelList(roleVo.getId()));
		 	}
			responseData.setReturnData(rolevo);
			return responseData;
	}
	
	
	@RequestMapping(value = "/roleDetail", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml","application/json" })
	@ResponseBody
	public RoleModel roleDetail(@RequestParam(value = "roleId", required = false) final Integer roleId) throws SystemException {
		UserInfoModel user= sessionService.getCurrentUser();
		RoleModel roleVo = null;
		try{
			if(roleId!=null){
				roleVo = roleManagerService.getRoleViewVoById(roleId);
				roleVo.setFalg(true);
			}else{
				roleVo = new RoleModel();
				roleVo.setRoleActive("Y");
				roleVo.setFalg(false);
			}			
			roleVo.setList(roleMenuService.selectMenuModelList(roleId));
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			 throw new SystemException(e.getMessage(),e);
		}
		roleVo.setLanguage(user.getSessionLang());
		return roleVo;
	}
	
	@RequestMapping(value = "/deleteRole", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<RoleModel> deleteRole(
			@RequestParam(value = "roleIds", required = false) final String roleIds) throws SystemException {
		ResponseData<RoleModel> responseData = responseDataService.getResponseData();
		try {
			String[] a=roleIds.split(",");			
				for(int b=0;b<a.length;b++){
					roleMenuService.deleteRoleMenu(Integer.parseInt(a[b]));
				}		
					responseData=roleManagerService.deleteByPrimaryKey(roleIds);
					List<RoleModel> list=roleManagerService.selectRoleList(null);
					responseData.setReturnDataList(list);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					throw new SystemException(e.getMessage(),e);
		}

		return responseData;

	}
	
	
	
}
