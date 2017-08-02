  package com.mall.b2bp.services.impl.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.system.RoleManagerMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.RoleModel;
import com.mall.b2bp.services.system.RoleManagerService;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;

@Service("roleManagerService")
public class RoleManagerServiceImpl extends BaseService implements RoleManagerService{
	private static final Logger LOG = LoggerFactory.getLogger(RoleManagerServiceImpl.class);
	
	private RoleManagerMapper roleManagerMapper;
		
	
	public RoleManagerMapper getRoleManagerMapper() {
		return roleManagerMapper;
	}
	@Autowired
	public void setRoleManagerMapper(RoleManagerMapper roleManagerMapper) {
		this.roleManagerMapper = roleManagerMapper;
	}

	@Resource(name = "sessionService1")
	SessionService sessionService;

	@Override
	public List<RoleModel> selectRoleList(RoleModel roleModel) {
		List<RoleModel> roleVos = new ArrayList<>();	
		roleVos = roleManagerMapper.selectRoleList(roleModel);
		return roleVos;
	}
	
	@Override
	public List<RoleModel> searchRole(RoleModel roleModel) {
		List<RoleModel> roleVos = new ArrayList<>();	
		roleVos = roleManagerMapper.searchRole(roleModel);
		return roleVos;
	}
	
	
	@Override
	public ResponseData<RoleModel> checkRoleSave(RoleModel roleVo) throws SystemException {
		ResponseData<RoleModel> responseData = responseDataService.getResponseData();
		return responseData;
	}
	
	@Override
	public ResponseData<RoleModel> checkroleSave(RoleModel roleModel) throws SystemException{
		ResponseData<RoleModel> responseData=responseDataService.getResponseData();
		if(roleModel!=null){
			List<RoleModel> roleModels=roleManagerMapper.selectRoleList(null);
			for(RoleModel model:roleModels){
				if(model.getRoleCode().equals(roleModel.getRoleCode())){
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseData.add("role_add_role_Code");
					break;
				}
			}
		}
		return responseData;
	}
	
	@Override
	public boolean chack(RoleModel roleVo){
		boolean falg=false;
//		if(roleVo.getBusUnitCode()==null){
//			falg=true;
//	 	} else 
	 	if(roleVo.getRoleCode()==null){
	 		falg=true;
	 	}else if(roleVo.getRoleName()==null){
	 		falg=true;
	 	}else if(roleVo.getDispSeq()==null){
	 		falg=true;
	 	}
		return falg;
	}
	
	@Override
	public int addRole(RoleModel roleVo,ResponseData<RoleModel> responseData)
			throws ServiceException {
		try {			
		
			if (roleVo.getId() == null) {												
				insertRole(roleVo);									
			} else {
				updateRole(roleVo);
			}		
			responseData.setErrorType("success");
			responseData.setReturnData(roleVo);
		} catch (Exception e) {
			LOG.error("roleViewVo not save:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return roleVo.getId();
	}
	
	@Override
	public int insertRole(RoleModel roleVo) {
		roleVo.setCreatedBy(sessionService.getCurrentUser().getUserCode());
		roleVo.setCreatedDate(new Date());
		roleVo.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
		roleVo.setLastUpdatedDate(new Date());
		return roleManagerMapper.insert(roleVo);
	}
	

	@Override
	public int updateRole(RoleModel roleVo) {
		int inserSupper = 0;
		roleVo.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
		roleVo.setLastUpdatedDate(new Date());
		inserSupper = roleManagerMapper.update(roleVo);
		return inserSupper;
	}
	
	@Override
	public RoleModel getRoleViewVoById(Integer roleId) {
		RoleModel roleVo = new RoleModel();
		if(roleId != null)  {
			roleVo.setId(roleId);
			List<RoleModel> roleList = roleManagerMapper.selectRoleList(roleVo);
			if(roleList != null && roleList.size() > 0) {
				roleVo = roleList.get(0);
			}
		}
		return roleVo;
	}
	
	@Override
	public List<RoleModel> searchUserRole(Integer roleId){
		List<RoleModel> list=new ArrayList<>();
		list=roleManagerMapper.searchUserRole(roleId);
		if(list!=null){
			return list;			
		}
		return null;
	}
	
	@Override
	public ResponseData<RoleModel> deleteByPrimaryKey(String ids) throws SystemException {
		ResponseData<RoleModel> responseData = responseDataService.getResponseData();
			boolean flag = false;
			String[] a=ids.split(",");
		for (String roleid : a) {
			if (StringUtils.isNotEmpty(roleid)) {
				try {
					roleManagerMapper.deleteByPrimaryKey(Integer.valueOf(roleid));
				}catch(Exception e) {
					flag = true;
					String errText = e.getMessage();
					int index = errText.indexOf("ORA-");
					String oracleErrCode = errText.substring(index, index + 9);
					if("ORA-02292".equals(oracleErrCode)) {
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseData.add("child_record_exists");
						}else {
							throw e;
						}
				}	
			}
		}  
		if(!flag) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		}
			
		return responseData;
		
	}
	
	@Override
	public List<RoleModel> getRoleUserlist(Integer userId){
		List<RoleModel> list=new ArrayList<RoleModel>();
		if(userId!=null){
			list=roleManagerMapper.getRoleUserlist(userId);
		}
		return list;
	}
}
