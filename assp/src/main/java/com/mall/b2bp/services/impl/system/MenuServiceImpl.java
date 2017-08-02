package com.mall.b2bp.services.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.system.MenuModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.MenuModel;
import com.mall.b2bp.services.system.MenuService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;

@Service("menuService")
public class MenuServiceImpl extends BaseService implements MenuService{
	private static final Logger LOG = LoggerFactory
			.getLogger(MenuServiceImpl.class);
	private MenuModelMapper menuModelMapper;

	public MenuModelMapper getMenuModelMapper() {
		return menuModelMapper;
	}

	@Autowired
	public void setMenuModelMapper(MenuModelMapper menuModelMapper) {
		this.menuModelMapper = menuModelMapper;
	}

	@Override
	public List<MenuModel> search(MenuModel model) throws ServiceException {
		try {
			List<MenuModel> list = menuModelMapper.search(model);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		return null;
	}

	@Override
	public List<MenuModel> searchMenu(MenuModel model) throws ServiceException {
		try {
			List<MenuModel> list = menuModelMapper.searchMenu(model);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		return null;
	}
	
	@Override
	public ResponseData<MenuModel> checkMenuSave(MenuModel menuModel)
			throws SystemException {
		ResponseData<MenuModel> responseData = responseDataService.getResponseData();
		if (menuModel != null) {
			MenuModel model2 = new MenuModel();
			model2.setType(menuModel.getType());
			List<MenuModel> menuModels = menuModelMapper.selectmenu(model2);
			for (MenuModel menu : menuModels) {
				if (menuModel.getFuncCode().equals(menu.getFuncCode())) {
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseData.add("menu_add_Function_Code");
					break;
				}
				if (menuModel.getType().equals("menu")) {
					if (menuModel.getLev1().equals(menu.getLev1())) {
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseData.add("menu_add_Level_1_Serial_Number");
						break;
					}
				} else if (menuModel.getType().equals("func")) {
					if (menuModel.getLev1().equals(menu.getLev1())&&menuModel.getLev2().equals(menu.getLev2())) {
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseData.add("menu_add_Level_2_Serial_Number");
						break;
					}
				}

			}
		}

		return responseData;
	}
	
	@Override
	public List<MenuModel> getMenuName(MenuModel model) throws SystemException, ServiceException{
		try {
			List<MenuModel> list = menuModelMapper.getMenuname(model);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		return null;
		
	}
	
	@Override
	public ResponseData<MenuModel> checkMenuUpdateSave(MenuModel menuModel)
			throws SystemException {
		ResponseData<MenuModel> responseData = responseDataService.getResponseData();
		
		if (menuModel != null) {
			MenuModel model2 = new MenuModel();
			model2.setType(menuModel.getType());
			List<MenuModel> menuModels = menuModelMapper.selectmenu(model2);
			for (MenuModel menu : menuModels) {
				if (menuModel.getType().equals("menu")) {
					if (!menuModel.getId().equals(menu.getId())&&menuModel.getLev1().equals(menu.getLev1())) {
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseData.add("menu_add_Level_1_Serial_Number");					
						}												
					}
				if(menuModel.getType().equals("func")){
					if(!menuModel.getId().equals(menu.getId())&&menuModel.getLev1().equals(menu.getLev1())&&menuModel.getLev2().equals(menu.getLev2())){
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseData.add("menu_add_Level_2_Serial_Number");	
					}
				}
				}
					
		}

		return responseData;
	}
	
	@Override
	public boolean chack(MenuModel menuModel)throws ServiceException{
		boolean falg=false;
		if(menuModel.getFuncCode()==null){
			falg=true;			
		}else if (menuModel.getFuncNameEN()==null) {
			falg=true;
		}
//		else if (menuModel.getFuncNameCN()==null) {
//			falg=true;
//		}
		else if (menuModel.getFuncNameTW()==null) {
			falg=true;
		}else if (menuModel.getLev1()==null) {
			falg=true;
		}else if (menuModel.getLev2()==null) {
			falg=true;
		}
		return falg;
	}
	@Override
	public int update(MenuModel model) throws ServiceException {
		try {
			return menuModelMapper.update(model);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int delete(MenuModel model) throws ServiceException {
		try {
			return menuModelMapper.delete(model);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int insert(MenuModel model) throws ServiceException {
		try {
			return menuModelMapper.insert(model);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<MenuModel> selectmenu(MenuModel model) throws ServiceException {
		try {
			List<MenuModel> list = new ArrayList<MenuModel>();
			list = menuModelMapper.selectmenu(model);
			return list;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}

	}
}
