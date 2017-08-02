package com.mall.b2bp.services.impl.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.system.RoleMenuModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.system.RoleMenuModel;
import com.mall.b2bp.services.system.RoleMenuService;


@Service("roleMenuService")
public class RoleMenuServiceImpl extends BaseService implements RoleMenuService{
	private static final Logger LOG = LoggerFactory
			.getLogger(RoleMenuServiceImpl.class);	
	
	private RoleMenuModelMapper roleMenuModelMapper;
	public RoleMenuModelMapper getRoleMenuModelMapper() {
		return roleMenuModelMapper;
	}
	@Autowired
	public void setRoleMenuModelMapper(RoleMenuModelMapper roleMenuModelMapper) {
		this.roleMenuModelMapper = roleMenuModelMapper;
	}
	
	@Override
	public List<RoleMenuModel> selectMenuModelList(Integer id) throws ServiceException{
		try {
			List<RoleMenuModel> list=roleMenuModelMapper.selectMenuModelList(id);
			if (list != null && list.size() > 0) {
				for(RoleMenuModel model : list) {
					model.setCanSelect("Y");
				}
				return list;
			}
			return null;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}		
	}
	
	@Override
	public List<RoleMenuModel> selectList(RoleMenuModel menuModel) throws ServiceException{
		try {
			List<RoleMenuModel> list=roleMenuModelMapper.selectlist(menuModel);
			if (list != null && list.size() > 0) {
				return list;
			}
			return null;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}	
	}
	@Override
	public int creatRolePermission(RoleMenuModel menuModel)throws ServiceException{
		try {
			menuModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
			menuModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
			return roleMenuModelMapper.creatRolePermission(menuModel);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public int deleteRoleMenu(Integer roleId)throws ServiceException{
		try {
			return roleMenuModelMapper.deleteRoleMenu(roleId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public int deleteRole(Integer funcId)throws ServiceException{
		try {
			return roleMenuModelMapper.deleteRole(funcId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public int updateRolePermission(RoleMenuModel menuModel) throws ServiceException{
		try {
			menuModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
			return roleMenuModelMapper.updateRolePermission(menuModel);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}
}
