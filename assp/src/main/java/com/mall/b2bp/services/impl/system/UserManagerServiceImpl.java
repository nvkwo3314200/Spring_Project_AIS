  package com.mall.b2bp.services.impl.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.system.UserManagerMapper;
import com.mall.b2bp.daos.system.UserRoleManagerMapper;
import com.mall.b2bp.daos.user.UserModelMapper;
import com.mall.b2bp.daos.user.UserPwdHistoryMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.shop.ShopInfoModel;
import com.mall.b2bp.models.system.MenuModel;
import com.mall.b2bp.models.system.PowerModel;
import com.mall.b2bp.models.system.RoleModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.models.user.UserModel;
import com.mall.b2bp.models.user.UserPwdHistory;
import com.mall.b2bp.services.shop.ShopInfoService;
import com.mall.b2bp.services.system.RoleManagerService;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.services.system.UserManagerService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.vos.ResponseData;


@Service("userManagerService")
public class UserManagerServiceImpl extends BaseService implements UserManagerService{
	private static final Logger LOG = LoggerFactory.getLogger(UserManagerServiceImpl.class);
	
	// the static update by lh -16-10-12
	private static UserManagerMapper userManagerMapper;
	
	private UserRoleManagerMapper userRoleManagerMapper;

	@Resource(name = "roleManagerService")
	private RoleManagerService roleManagerService;
	
	@Resource(name="sessionService1")
	private SessionService sessionService;
	
	private UserModelMapper userModelMapper;
	private UserPwdHistoryMapper userPwdHistoryMapper;
	
	@Resource
	private ShopInfoService shopInfoService;
	
	@Autowired
	public void setUserModelMapper(UserModelMapper userModelMapper) {
		this.userModelMapper = userModelMapper;
	}

    public UserPwdHistoryMapper getUserPwdHistoryMapper() {
        return userPwdHistoryMapper;
    }

    @Autowired
    public void setUserPwdHistoryMapper(UserPwdHistoryMapper userPwdHistoryMapper) {
        this.userPwdHistoryMapper = userPwdHistoryMapper;
    }
    
    public UserModelMapper getUserModelMapper() {
		return userModelMapper;
	}
	
	public UserManagerMapper getUserManagerMapper() {
		return userManagerMapper;
	}
	
	@Autowired
	public void UserManagerMapper(UserManagerMapper userManagerMapper) {
		this.userManagerMapper = userManagerMapper;
	}
	
	public UserRoleManagerMapper getUserRoleManagerMapper() {
		return userRoleManagerMapper;
	}
	
	@Autowired
	public void setUserRoleManagerMapper(UserRoleManagerMapper userRoleManagerMapper) {
		this.userRoleManagerMapper = userRoleManagerMapper;
	}
	
	@Override
	public List<UserInfoModel> selectUserList(UserInfoModel searchUserVo) throws ServiceException {
		try {
			List<UserInfoModel> list = userManagerMapper.selectUserList(searchUserVo);
			if(list != null && list.size() > 0) {
				for(UserInfoModel model : list) {
					model.setSubRoleModelList(roleManagerService.getRoleUserlist(model.getId()));
				}
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return new ArrayList<UserInfoModel>();
	}
	
	@Override
	public List<UserInfoModel> search(UserInfoModel searchUserVo) throws ServiceException {
		try {
			UserInfoModel currUser = sessionService.getCurrentUser();
			searchUserVo.setMallId(currUser.getMallId());
			List<UserInfoModel> list = userManagerMapper.search(searchUserVo);
			if(list != null && list.size() > 0) {
				list = setSupplier(list);
				return list;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return new ArrayList<UserInfoModel>();
	}
	
	
	@SuppressWarnings("unchecked")
	private List<UserInfoModel> setSupplier(List<UserInfoModel> list) throws ServiceException {
		Map<String, ShopInfoModel> map = (Map<String, ShopInfoModel>) sessionService.getAttribute(ConstantUtil.SYSTEM_SHOP_MAP);
		if(map == null) shopInfoService.getAllShop();
		map = (Map<String, ShopInfoModel>) sessionService.getAttribute(ConstantUtil.SYSTEM_SHOP_MAP);
		for(UserInfoModel user : list) {
			if(user.getShopId() != null) {
				ShopInfoModel shop = map.get(user.getShopId().toString());
				user.setShop(shop == null? new ShopInfoModel():shop);
			}
		}
		return list;
	}

	@Override
	public boolean chack(UserInfoModel userInfoModel){
		boolean falg=false;
		if(userInfoModel.getUserCode()==null){
			falg=true;
		}else if (userInfoModel.getId()==null) {
			if(userInfoModel.getUserPwd()==null){				
				falg=true;
			}
		}
				
		return falg;
	}
	
	@Override
	public boolean checkUserSave(UserInfoModel userInfoVo,ResponseData<UserInfoModel> responseData) throws SystemException {
		//ResponseData<UserInfoModel> responseData = responseDataService.getResponseData();
		boolean error = false;
		if (userInfoVo != null) {
//			if (StringUtils.isEmpty(userInfoVo.getUserCode())) {
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//				responseData.add("user_add_user_code");
//			}else {
//				userInfoVo.setUserCode(userInfoVo.getUserCode().toUpperCase());
				Integer existNum = null;
				if(userInfoVo.getId() == null) {
					existNum = userManagerMapper.createExsitUser(userInfoVo);
				}
				if(existNum != null && existNum !=0) {
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseData.add("user_code_unique");
					error = true;
				}
			}
//			if (StringUtils.isEmpty(userInfoVo.getUserCode())) {
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//				responseData.add("user_add_user_name");
//			}
		//check password
			if (StringUtils.isNotEmpty(userInfoVo.getUserPwd())) {
				error = !validateUserInfo(userInfoVo.getUserPwd(),responseData);
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//				responseData.add("user_add_user_password");
			}
//		}
		return error;
	}
	
	private boolean  validateUserInfo(final String newPassword, ResponseData<UserInfoModel> responseData) {
		if (StringUtils.isEmpty(newPassword)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_new_password_require");
			return false;
		}

		
		//a.	At least 6 characters in length
		if (newPassword.length()<6) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_length_invalid");
			return false;
		}
		
		if (!newPassword.matches(".*?[a-z]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_lower_invalid");
			return false;
		}
		//b.	Contains upper case letter (A-Z)

		if (!newPassword.matches(".*?[A-Z]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_upper_invalid");
			return false;
		}
		//d.	Contains numeral (0-9)
		if (!newPassword.matches(".*?[\\d]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_number_invalid");
			return false;
		}
		
	
		return true;
	}

	
	@Override
	public void addUser(UserInfoModel userInfoVo,ResponseData<UserInfoModel> responseData)
			throws ServiceException {
		try {
			if (responseData.getErrorType() == null || !responseData.getErrorType().equals(ConstantUtil.ERROR_TYPE_DANGER)) {
				if(!StringUtils.isEmpty(userInfoVo.getUserPwd())) {
					changePassword(userInfoVo);
				}
				
				if (userInfoVo.getId() == null) {
					int id = insertUser(userInfoVo);
					userInfoVo.setId(id);
				} else {
					
					UserModel userModel = null;
					try {
						int pkId = userInfoVo.getId() ;
						userModel = userModelMapper.selectByPrimaryKey(new BigDecimal( pkId));
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
						throw new ServiceException(e.getMessage(), e);
					}

			
					String pwd = userInfoVo.getUserPwd();
						if(StringUtils.isNotEmpty(pwd)){
						
						int times = userPwdHistoryMapper.selectByPwdHistory(userModel.getId(),pwd,ConstantUtil.PWD_HISTORY_TIMES_ABLE);
						if (pwd.equals(userModel.getPassword())) {
							responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseData.add("forget_password_password_recently_invalid");
							return;
						}else if(times>0){
			                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			                responseData.add("forget_password_password_used_invalid");
			               return;
			            }
	
			            UserPwdHistory userPwdHistory = new UserPwdHistory();

						userPwdHistory.setExpireDate(new Date());
						userPwdHistory.setUserId(userModel.getId());
						userPwdHistory.setUserPassword(userModel.getPassword());
						if(sessionService.getCurrentUser()!=null){
							userPwdHistory.setCreatedBy(sessionService.getCurrentUser().getUserCode());
							userPwdHistory.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
						}
						userPwdHistory.setCreatedDate(new Date());
						userPwdHistory.setLastUpdatedDate(new Date());
						this.userPwdHistoryMapper.insertSelective(userPwdHistory);
					}
						
						updateUser(userInfoVo);
					
				}
				userRoleListSaveAll(userInfoVo);
				responseData.setErrorType("success");
			}
			responseData.setReturnData(userInfoVo);
		} catch (Exception e) {
			LOG.error("roleViewVo not save:" + e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	

	
	@Override
	public int insertUser(UserInfoModel userVo) {
		userVo.setCreatedBy(sessionService.getCurrentUser().getUserCode());
		userVo.setCreatedDate(new Date());
		userVo.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
		userVo.setLastUpdatedDate(new Date());
		userManagerMapper.insert(userVo);
		int id =userVo.getId();
		return id;
	}
	

	@Override
	public int updateUser(UserInfoModel userVo) {
		int inserSupper = 0;
		userVo.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
		userVo.setLastUpdatedDate(new Date());
		inserSupper = userManagerMapper.update(userVo);
		return inserSupper;
	}
	
	@Override
	public UserInfoModel getUserViewVoById(Integer userId){
		UserInfoModel userInfoVo = new UserInfoModel();
		if(userId != null)  {
			userInfoVo.setId(userId);
			List<UserInfoModel> roleList = userManagerMapper.selectUserList(userInfoVo);
			if(roleList != null && roleList.size() > 0) {
				try {
					roleList = setSupplier(roleList);
				} catch (ServiceException e) {
					LOG.error(e.getMessage(), e);
				}
				userInfoVo = roleList.get(0);
			}
		}
		return userInfoVo;
	}

	@Override
	public int deleteByPrimaryKey(String ids) {
		if (StringUtils.isNotEmpty(ids)) {
			String[] pStrings = StringUtil.getAllProductId(ids);
			if (pStrings != null && pStrings.length > 0) {
				for (String strId : pStrings) {
					if (StringUtils.isNotEmpty(strId)) {
						userManagerMapper.deleteByPrimaryKey(Integer.valueOf(strId));
						userManagerMapper.deleteUser(Integer.valueOf(strId));
					}
				}
			}
		}
		return 0;
	}
	
	//login	by lh-6-29
	@Override
	public UserInfoModel getUserInfo(String userCode) {
		return userManagerMapper.selectUser(userCode);
	}

	//login	by lh-7-1
	@Override
	public List<MenuModel> getCurrentMenu(String userCode) {
		return userManagerMapper.selectMenu(userCode);
	}
	
	//login	by lh-9-8
	@Override
	public List<PowerModel> getUserAllFuncPower(String userCode) {
		return userManagerMapper.getUserAllFuncPower(userCode);
	}
	
	//login	by lh-6-30 PS:Update Login successful information
	@Override
	public int updateUserLoginSuccess(UserInfoModel record) throws ServiceException {
		try {
			return userManagerMapper.updateUserLoginSuccess(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	//login	by lh-10-12 PS:Save Login successful data(static)
	public static int saveUserLoginSuccess(UserInfoModel record) throws ServiceException {
		try {
			return userManagerMapper.saveUserLoginSuccess(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	//login	by lh-6-30 PS:Update Login fail information
	@Override
	public int updateUserLoginFail(UserInfoModel record) throws ServiceException {
		try {
			return userManagerMapper.updateUserLoginFail(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public void changePassword (UserInfoModel userInfoVo) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		String pwd = md5PasswordEncoder.encodePassword(userInfoVo.getUserPwd(),userInfoVo.getUserCode());
		userInfoVo.setUserPwd(pwd);
	}
	

	@Override
	public ResponseData<UserInfoModel> checkUserRoleSave(UserInfoModel userInfoVo) throws SystemException {
		ResponseData<UserInfoModel> responseData = responseDataService.getResponseData();
		RoleModel selectedRoleModel = userInfoVo.getSelectedRoleModel();
		RoleModel bufferRoleModel = userInfoVo.getBufferRoleModel();
		List<RoleModel> bufferRoleList = userInfoVo.getSubRoleModelList();
		
		if (selectedRoleModel != null) {
			if (StringUtils.isEmpty(selectedRoleModel.getRoleCode())) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_role_add_role_code");
			}else {
				if(bufferRoleList != null && !bufferRoleList.isEmpty()) {
					if(bufferRoleModel != null && !StringUtils.isEmpty(bufferRoleModel.getRoleCode())) {
						int index = 0;
						boolean flag = false;
						for (RoleModel roleModel : bufferRoleList) {
							if(bufferRoleModel.getRoleCode().equals(roleModel.getRoleCode())) {
								index = bufferRoleList.indexOf(roleModel);
								break;
							}
						}
						if(selectedRoleModel.getRoleCode().equals(bufferRoleModel.getRoleCode())) {
							flag = true;
						}else {
							flag = true;
							for (RoleModel roleModel : bufferRoleList) {
								if(selectedRoleModel.getRoleCode().equals(roleModel.getRoleCode())) {
									responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
									responseData.add("user_role_add_role_code_unique");
									flag = false;
									break;
								}
							}
						}
						if(flag) {
							selectedRoleModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
							selectedRoleModel.setLastUpdatedDate(new Date());
							bufferRoleList.set(index, selectedRoleModel);
						}
					}else {
						boolean flag = false;
						for (RoleModel roleModel : bufferRoleList) {
							if(selectedRoleModel.getRoleCode().equals(roleModel.getRoleCode())) {
								responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								responseData.add("user_role_add_role_code_unique");
								flag = true;
								break;
							}
						}
						if(!flag) {
							selectedRoleModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
							selectedRoleModel.setCreatedDate(new Date());
							selectedRoleModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
							selectedRoleModel.setLastUpdatedDate(new Date());
							bufferRoleList.add(selectedRoleModel);
						}
					}
				}else {
					selectedRoleModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
					selectedRoleModel.setCreatedDate(new Date());
					selectedRoleModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
					selectedRoleModel.setLastUpdatedDate(new Date());
					bufferRoleList = new ArrayList<RoleModel>();
					bufferRoleList.add(selectedRoleModel);
				}
			}
		}
		userInfoVo.setSubRoleModelList(bufferRoleList);
		responseData.setReturnData(userInfoVo);
		return responseData;
	}

	
	@Override
	public void userRoleListSaveAll(UserInfoModel userInfoVo) {
		int userId = userInfoVo.getId();
		List<RoleModel> bufferRoleList = userInfoVo.getSubRoleModelList();
		List<RoleModel> roleListDB = roleManagerService.getRoleUserlist(userId);
		if(bufferRoleList != null && !bufferRoleList.isEmpty()) {
			if(roleListDB != null && !roleListDB.isEmpty()) {
				for (RoleModel roleModelDB : roleListDB) {
					userRoleManagerMapper.delete(roleModelDB);
				}
				for (RoleModel roleModel : bufferRoleList) {
					roleModel.setUserId(userId);
					userRoleManagerMapper.insert(roleModel);
				}
			}else {
				for (RoleModel roleModel : bufferRoleList) {
					roleModel.setUserId(userId);
					userRoleManagerMapper.insert(roleModel);
				}
			}
		}else {
			if(roleListDB != null && !roleListDB.isEmpty()) {
				for (RoleModel roleModelDB : roleListDB) {
					userRoleManagerMapper.delete(roleModelDB);
				}
			}
		}
	}
	
	@Override
	public ResponseData<UserInfoModel> deletebufferRoles(UserInfoModel userInfoVo) throws SystemException {
		ResponseData<UserInfoModel> responseData = responseDataService.getResponseData();
		String RoleCodes = userInfoVo.getBufferRoleCodes();
		List<RoleModel> roleList = userInfoVo.getSubRoleModelList();
		if (StringUtils.isNotEmpty(RoleCodes)) {
			String[] pStrings = StringUtil.getAllProductId(RoleCodes);
			if (pStrings != null && pStrings.length > 0) {
				for (String strCode : pStrings) {
					if (StringUtils.isNotEmpty(strCode)) {
						if(roleList != null && !roleList.isEmpty()) {
							int index  = 0;
							boolean flag = false;
							for (RoleModel roleModel : roleList) {
								if(roleModel.getRoleCode().equals(strCode)) {
									flag = true;
									index = roleList.indexOf(roleModel);
									break;
								}
							}
							if(flag) {
								roleList.remove(index);
							}
						}
					}
				}
			}
		}
		userInfoVo.setSubRoleModelList(roleList);
		responseData.setReturnData(userInfoVo);
		return responseData;
	}
	
	//update the user selected language by Keven in July 20, 2016
	@Override
	public int updateUserLang(UserInfoModel model) throws ServiceException {
		try{
			return userManagerMapper.updateUserLang(model);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
}
