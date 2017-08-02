package com.mall.b2bp.controllers.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.MenuModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.services.system.MenuService;
import com.mall.b2bp.services.system.RoleMenuService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;

@Controller("MenuManagerController")
@RequestMapping(value = "/menuManager")
public class MenuManagerController extends BaseController {
	//private static final Logger LOG = LoggerFactory.getLogger(MenuManagerController.class);

	@Resource(name = "menuService")
	private MenuService menuService;

	@Resource(name = "roleMenuService")
	private RoleMenuService roleMenuService;
	
	List<MenuModel> list=new ArrayList<>();
	
	
	@RequestMapping(value = "/search", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<MenuModel> search(@RequestBody final MenuModel menuModel)
			throws SystemException {
		ResponseData<MenuModel> responseData = responseDataService.getResponseData();
		UserInfoModel user = sessionService.getCurrentUser();
		MenuModel model = new MenuModel(user.getUserCode());
		try {		
			if(menuModel.getLev1()!=null){
				model.setLev1(menuModel.getLev1());
				
			}
			if(menuModel.getLev2()!=null){
				model.setLev2(menuModel.getLev1());
			
			}
			List<MenuModel> list = menuService.search(model);
			
			if (list != null && list.size() > 0) {
				responseData.setReturnDataList(list);
				responseData.setErrorType("success");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
	}

	@RequestMapping(value = "/searchMenu", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<MenuModel> searchMenu(@RequestBody final MenuModel menuModel)
			throws SystemException {
		ResponseData<MenuModel> responseData = responseDataService.getResponseData();
		UserInfoModel user = sessionService.getCurrentUser();
		if(user!=null)
		menuModel.setOther(user.getUserCode());
		try {		
			
			List<MenuModel> list = menuService.searchMenu(menuModel);
			
			if (list != null && list.size() > 0) {
				responseData.setReturnDataList(list);
				responseData.setErrorType("success");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
	}
	
	
	@RequestMapping(value = "/getMenuName", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<MenuModel> getMenuName()
			throws SystemException {
		ResponseData<MenuModel> responseData = responseDataService.getResponseData();
		UserInfoModel user = sessionService.getCurrentUser();
		if (user == null) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("change_password_invalid_login");
			return responseData;
		}
			
		try {			
			MenuModel menuModel=new MenuModel();
			menuModel.setOther(user.getUserCode());
			menuModel.setType("menu");
			List<MenuModel> list = menuService.getMenuName(menuModel);
			if (list != null && list.size() > 0) {
				responseData.setReturnDataList(list);
				responseData.setErrorType("success");
			} else {
				responseData.setErrorType("danger");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
	}
	
	
	@RequestMapping(value = "/getfuncName", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<MenuModel> getfuncName(@RequestBody final MenuModel model )
			throws SystemException {
		ResponseData<MenuModel> responseData = responseDataService.getResponseData();
		UserInfoModel user = sessionService.getCurrentUser();
		if (user == null) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("change_password_invalid_login");
			return responseData;
		}
		try {			
			MenuModel menuModel=new MenuModel();
			menuModel.setOther(user.getUserCode());
			menuModel.setType("func");
			menuModel.setLev1(model.getLev1());
			List<MenuModel> list = menuService.getMenuName(menuModel);
			if (list != null && list.size() > 0) {
				responseData.setReturnDataList(list);
				responseData.setErrorType("success");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
			
	}
	
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<MenuModel> delete(@RequestBody final MenuModel[] models)
			throws SystemException {

		ResponseData<MenuModel> responseData = responseDataService.getResponseData();
		UserInfoModel user = sessionService.getCurrentUser();

		if (user == null) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("change_password_invalid_login");
			return responseData;
		}

		try {
			int result = 0;
			for (MenuModel model : models) {
				int id = model.getId().intValue();
				result = roleMenuService.deleteRole(id);
				result = menuService.delete(model);

				if (result != 0) {
//					List<MenuModel> list = menuService.search(null);
//					responseData.setReturnDataList(list);
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
				} else {
					responseData.add("email_update_failed");
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				}
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;

	}

	
	@RequestMapping(value = "/save", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<MenuModel> save(@RequestBody final MenuModel model)
			throws SystemException, ServiceException {

		ResponseData<MenuModel> responseData = responseDataService.getResponseData();
		UserInfoModel user = sessionService.getCurrentUser();

		if (user == null) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("change_password_invalid_login");
			return responseData;
		}
		boolean falg=menuService.chack(model);
		if(falg==false){
		model.setLastUpdatedBy(user.getUserCode());
		model.setFuncCode(model.getFuncCode().toUpperCase());
		try {
			int result = 0;
			
			if (model.getId() == null) {
				responseData = menuService.checkMenuSave(model);
				if (responseData.getErrorType() == null) {
					result = menuService.insert(model);
					
				}

			} else {
				responseData = menuService.checkMenuUpdateSave(model);
				if (responseData.getErrorType() == null) {
					if(model.getLev2()==0){
						for(MenuModel menuModel:list){
							menuModel.setLev1(model.getLev1());
							result = menuService.update(menuModel);												
						}
					}
					result = menuService.update(model);
					
				}

			}
			if (result != 0) {
//				List<MenuModel> list = menuService.search(model);
//				list.get(0).setFalg(true);
//				responseData.setReturnData(list.get(0));
				responseData
						.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			} 
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		}
		return responseData;

	}

	
	@RequestMapping(value = "/selectMenu", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public MenuModel selectMenu(@RequestBody final MenuModel model)
			throws SystemException {
		MenuModel menuModel = null;
		try {
			if (model.getId() != null) {
				menuModel = menuService.search(model).get(0);
				menuModel.setFalg(true);
				if(menuModel.getLev2()==0){
					MenuModel model2=new MenuModel();
					model2.setLev1(menuModel.getLev1());
					list=menuService.search(model2);
				}
			} else {
				menuModel = new MenuModel();
				menuModel.setFalg(false);
				menuModel.setType("menu");
				menuModel.setLev2(0);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return menuModel;
	}
	
	
	@RequestMapping(value = "/selectMenulist", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public List<MenuModel> selectMenulist(@RequestBody final MenuModel menuModel)
			throws SystemException {
		List<MenuModel> menuModels = new ArrayList<>();
		UserInfoModel user = sessionService.getCurrentUser();
		menuModel.setOther(user.getUserCode());
		try {
			menuModels = menuService.selectmenu(menuModel);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return menuModels;
	}
}
