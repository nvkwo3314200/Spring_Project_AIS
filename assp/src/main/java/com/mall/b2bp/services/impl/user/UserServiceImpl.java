package com.mall.b2bp.services.impl.user;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.brand.BrandModelMapper;
import com.mall.b2bp.daos.dept.ClassModelMapper;
import com.mall.b2bp.daos.dept.DeptModelMapper;
import com.mall.b2bp.daos.dept.SubClassModelMapper;
import com.mall.b2bp.daos.product.ProductInfoModelMapper;
import com.mall.b2bp.daos.product.SupplierBrandModelMapper;
import com.mall.b2bp.daos.supplier.SupplierCategoryModelMapper;
import com.mall.b2bp.daos.supplier.SupplierModelMapper;
import com.mall.b2bp.daos.supplier.SupplierSubModelMapper;
import com.mall.b2bp.daos.user.UserModelMapper;
import com.mall.b2bp.daos.user.UserPwdHistoryMapper;
import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.models.product.SupplierBrandModel;
import com.mall.b2bp.models.supplier.SupplierCategoryModel;
import com.mall.b2bp.models.supplier.SupplierModel;
import com.mall.b2bp.models.supplier.SupplierSubModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.models.user.UserModel;
import com.mall.b2bp.models.user.UserPwdHistory;
import com.mall.b2bp.populators.user.UserPopulator;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.services.product.ProductAuditService;
import com.mall.b2bp.services.product.ProductInfoService;
import com.mall.b2bp.services.product.ProductUpFieldService;
import com.mall.b2bp.services.product.RetekService;
import com.mall.b2bp.services.response.ResponseDataService;
import com.mall.b2bp.services.shop.ShopSynService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.services.system.SessionService;

import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.utils.*;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.shop.CategoryListWsDTO;
import com.mall.b2bp.vos.shop.CategoryWsDTO;
import com.mall.b2bp.vos.supplier.SupplierVo;
import com.mall.b2bp.vos.user.SupplierBrandVo;
import com.mall.b2bp.vos.user.SupplierLovVo;
import com.mall.b2bp.vos.user.UserViewVo;
import com.mall.b2bp.vos.user.UserVo;


@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final String STAGING = "STAGING";
	private static final String DRAFT = "DRAFT";
	private static final String Y = "Y";
	
	private SendEmail sendEmail;
	private UserModelMapper userModelMapper;
	private UserPwdHistoryMapper userPwdHistoryMapper;
	private SupplierBrandModelMapper supplierBrandModelMapper;
	private SupplierCategoryModelMapper supplierCategoryModelMapper;
	private ProductInfoModelMapper productInfoMapper;
	private SupplierModelMapper supplierModelMapper;
	private SubClassModelMapper subClassModelMapper;
	private ClassModelMapper classModelMapper;
	private DeptModelMapper deptModelMapper;
	private BrandModelMapper brandModelMapper;

	@Resource(name = "supplierService")
	private SupplierService supplierService;

	@Resource(name="shopSynService")
	private ShopSynService shopSynService;

	@Resource(name="retekService")
	private RetekService retekService;

	@Resource(name = "productInfoService")
	private ProductInfoService productInfoService;
	
	@Resource(name = "productAuditService")
	private ProductAuditService productAuditService;
	
	@Resource(name = "productUpFieldService")
	private ProductUpFieldService productUpFieldService;
	
	public BrandModelMapper getBrandModelMapper() {
		return brandModelMapper;
	}

	@Autowired
	public void setBrandModelMapper(BrandModelMapper brandModelMapper) {
		this.brandModelMapper = brandModelMapper;
	}

	public DeptModelMapper getDeptModelMapper() {
		return deptModelMapper;
	}

	@Autowired
	public void setDeptModelMapper(DeptModelMapper deptModelMapper) {
		this.deptModelMapper = deptModelMapper;
	}

	public ClassModelMapper getClassModelMapper() {
		return classModelMapper;
	}

	@Autowired
	public void setClassModelMapper(ClassModelMapper classModelMapper) {
		this.classModelMapper = classModelMapper;
	}

	public SubClassModelMapper getSubClassModelMapper() {
		return subClassModelMapper;
	}

	@Autowired
	public void setSubClassModelMapper(SubClassModelMapper subClassModelMapper) {
		this.subClassModelMapper = subClassModelMapper;
	}

	public SupplierModelMapper getSupplierModelMapper() {
		return supplierModelMapper;
	}

	@Autowired
	public void setSupplierModelMapper(SupplierModelMapper supplierModelMapper) {
		this.supplierModelMapper = supplierModelMapper;
	}

	@Resource
	protected ResponseDataService responseDataService;

	public ProductInfoModelMapper getProductInfoMapper() {
		return productInfoMapper;
	}

	@Autowired
	public void setProductInfoMapper(ProductInfoModelMapper productInfoMapper) {
		this.productInfoMapper = productInfoMapper;
	}

	public SupplierCategoryModelMapper getSupplierCategoryModelMapper() {
		return supplierCategoryModelMapper;
	}

	@Autowired
	public void setSupplierCategoryModelMapper(SupplierCategoryModelMapper supplierCategoryModelMapper) {
		this.supplierCategoryModelMapper = supplierCategoryModelMapper;
	}

	public SupplierSubModelMapper getSupplierSubModelMapper() {
		return supplierSubModelMapper;
	}

	@Autowired
	public void setSupplierSubModelMapper(SupplierSubModelMapper supplierSubModelMapper) {
		this.supplierSubModelMapper = supplierSubModelMapper;
	}

	private SupplierSubModelMapper supplierSubModelMapper;

	public SupplierBrandModelMapper getSupplierBrandModelMapper() {
		return supplierBrandModelMapper;
	}

	@Autowired
	public void setSupplierBrandModelMapper(SupplierBrandModelMapper supplierBrandModelMapper) {
		this.supplierBrandModelMapper = supplierBrandModelMapper;
	}

	public UserModelMapper getUserModelMapper() {
		return userModelMapper;
	}

	public SendEmail getSendEmail() {
		return sendEmail;
	}

	@Autowired
	public void setSendEmail(SendEmail sendEmail) {
		this.sendEmail = sendEmail;
	}

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

    @Resource(name = "sessionService1")
	SessionService sessionService;

	@Resource(name = "lovService")
	private LovService lovService;

	@Resource(name = "brandService")
	private BrandService brandService;

	@Override
	public UserVo selectUserByUserId(String userId) {
		UserVo userVo = null;
		UserModel userModel = userModelMapper.selectUserByUserId(userId);
		if (userModel != null) {
			userVo = new UserPopulator().converModelToVo(userModel);
		}
		return userVo;
	}

	@Override
	public boolean sendForgetEmail(String email) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap();
		map.put("email", email);
		UserModel userModel = userModelMapper.forgetPassword(map);
		if (userModel == null) {

			return false;
		}
		String token = getRandomString(25);

		String subject = "Supplier Portal Reset Password";
		StringBuffer htmlEmailTemplate = new StringBuffer("Dear Sir/Madam,");

		String url = ResourceUtil.getSystemConfig().getProperty("mail.change.password.url");
		if (url.indexOf("pssp") == -1) {
			url = url + "/pssp/";
		}
		htmlEmailTemplate.append("<br/>");
		htmlEmailTemplate
				.append("Referring to your request, please click the below link to reset your account login password.");
		htmlEmailTemplate.append("<br/>");
		htmlEmailTemplate.append("<a href='").append(url).append("#/forget?token=").append(token).append("'>")
		// .append(url).append("?token=").append(token).append("</a>");
				.append("Reset Password").append("</a>");
		htmlEmailTemplate.append("<br/>");
		htmlEmailTemplate.append("<br/>");
		htmlEmailTemplate.append("If you did not request to reset your password, please contact us.").append("<br/>");
		htmlEmailTemplate.append("<br/>");
		htmlEmailTemplate.append("Thanks,").append("<br/>");
		htmlEmailTemplate.append("A.S. Watson Supplier Portal").append("<br/>");

		boolean result = false;
		try {
			userModel.setToken(token);
			userModelMapper.updateByPrimaryKeySelective(userModel);
			result = sendEmail.sendMail(userModel.getEmail(), userModel.getContactNo(), subject,
					htmlEmailTemplate.toString());

		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage(), e);

		}

		return result;
	}

	@Override
	public boolean changePasswordByToken(String token, String password, ResponseData<UserVo> responseData) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap();
		map.put("token", token);
		UserModel userModel = null;
		// UserVo userVo = sessionService.getCurrentUser();
		// if (userVo == null) {
		// responseData.add("user_login_expire");
		// responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		// return false;
		// }
		try {
			userModel = userModelMapper.forgetPassword(map);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage(), e);
		}

		if (userModel == null) {
			responseData.add("forget_password_password_token");
			return false;
		} else {

			Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
			String pwd = md5PasswordEncoder.encodePassword(password, userModel.getUserId());
			userModel.setPassword(pwd);
			userModel.setToken(null);
			userModel.setLastUpdatedDate(new Date());
			userModel.setLastChangePwdDate(new Date());
			this.userModelMapper.updateByPrimaryKeySelective(userModel);
			responseData.add("forget_password_password_changed_success");
			return true;

		}

	}

	@Override
	public boolean changePassword(BigDecimal pkId, String password, ResponseData<UserVo> responseData)
			throws ServiceException {
		// TODO Auto-generated method stub
		UserInfoModel userVo = sessionService.getCurrentUser();
		if (userVo == null) {
			responseData.add("user_login_expire");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return false;
		}
		UserModel userModel = null;
		try {
			userModel = userModelMapper.selectByPrimaryKey(pkId);
		} catch (Exception e) {

			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

		if (userModel == null) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("change_password_invalid_login");
			return false;
		} else {

			Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
			String pwd = md5PasswordEncoder.encodePassword(password, userModel.getUserId());
			//Check password used @ last 5 times
			//update By Harlan King
			int times = userPwdHistoryMapper.selectByPwdHistory(userModel.getId(),pwd,ConstantUtil.PWD_HISTORY_TIMES_ABLE);
			if (pwd.equals(userModel.getPassword())) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("forget_password_password_recently_invalid");
				return false;
			}else if(times>0){
                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                responseData.add("forget_password_password_used_invalid");
                return false;
            }

            UserPwdHistory userPwdHistory = new UserPwdHistory();
		//	userPwdHistory.setId(userPwdHistoryMapper.selectNextId());
			userPwdHistory.setExpireDate(new Date());
			userPwdHistory.setUserId(userModel.getId());
			userPwdHistory.setUserPassword(userModel.getPassword());
			
			if(sessionService.getCurrentUser()!=null){
				userPwdHistory.setCreatedBy(sessionService.getCurrentUser().getUserCode());
				userPwdHistory.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
			}
			userPwdHistory.setCreatedDate(new Date());
			userPwdHistory.setLastUpdatedDate(new Date());
			
			userPwdHistory.setCreatedDate(new Date());
			
			userPwdHistory.setLastUpdatedDate(new Date());
			this.userPwdHistoryMapper.insertSelective(userPwdHistory);
			
			userModel.setPassword(pwd);
			userModel.setLastUpdatedBy(userVo.getUserName());
			userModel.setLastUpdatedDate(new Date());
			userModel.setLastChangePwdDate(new Date());
			this.userModelMapper.updateByPrimaryKeySelective(userModel);
			responseData.add("change_password_changed_success");
			return true;

		}
	}

	public String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	@Override
	public void cleanUserAuthToken(String id) {
		if (!StringUtils.isEmpty(id)) {
			BigDecimal bd = new BigDecimal(id);
			UserModel userModel = userModelMapper.selectByPrimaryKey(bd);
			if (userModel != null) {
				userModel.setToken("");
				userModelMapper.updateByPrimaryKeySelective(userModel);
			}
		}
	}

	@Override
	public void updateToken(String userId, String token) {
		UserModel userModel = new UserModel();
		userModel.setToken(token);
		userModel.setUserId(userId);
		userModelMapper.updateTokenByUserId(userModel);
	}

	@Override
	public int updateByPrimaryKeySelective(UserModel record) {
		return userModelMapper.updateByPrimaryKeySelective(record);
	}

	private List<ProductInfoModel> isCheckSupplierId(UserViewVo userVo) {
		if (userVo == null || userVo.getId()==null)
			return null;
		
		List<ProductInfoModel> productInfoVos = null;
		UserModel userModel = userModelMapper.selectByPrimaryKey(userVo.getId());
		if (userModel != null && StringUtils.isNotEmpty(userModel.getSupplierId())) {
			productInfoVos = productInfoMapper.getProductInfoModelBySupplierId(userModel.getSupplierId());

		}
		return productInfoVos;
	}

	@Override
	public UserModel selectByPrimaryKey(BigDecimal id) {
		return userModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserVo> selectUserList(String userId, String userName, String userRole, String email, String contactNo,
			String activate, String supplierId) {
		UserPopulator userPopulator = new UserPopulator();
		List<UserVo> userVos = new ArrayList<>();
		UserVo userVo = new UserVo();
		if (StringUtils.isNotEmpty(userId)) {
			userVo.setUserId(userId);
		}
		if (StringUtils.isNotEmpty(userName)) {
			userVo.setUserName(userName);
		}
		if (StringUtils.isNotEmpty(userRole)) {
			userVo.setUserRole(userRole);
		}
		if (StringUtils.isNotEmpty(email)) {
			userVo.setEmail(email);
		}
		if (StringUtils.isNotEmpty(contactNo)) {
			userVo.setContactNo(contactNo);
		}
		if (StringUtils.isNotEmpty(activate)) {
			userVo.setActivate(activate);
		}
		if (StringUtils.isNotEmpty(supplierId)) {
			userVo.setSupplierId(supplierId);
		}
		List<UserModel> userMList = userModelMapper.selectUserList(userVo);
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userMList)) {
			for (UserModel userModel : userMList) {
				if (userModel != null) {
					userVo = userPopulator.converModelToVo(userModel);
					userVos.add(userVo);
				}
			}
		}
		return userVos;
	}

	@Override
	// 新增User
	public int insertSelective(UserViewVo userVo) {
		UserPopulator userPopulator = new UserPopulator();
		UserModel record = userPopulator.converVoToModel(userVo, sessionService, userModelMapper);
		int insertDate = userModelMapper.insertSelective(record);
		userVo.setId(record.getId());
		return insertDate;
	}

	@Override
	public int deleteByPrimaryKey(String ids) throws ServiceException {
		
		
		if (StringUtils.isNotEmpty(ids)) {
			String[] pStrings = StringUtil.getAllProductId(ids);
			if (pStrings != null && pStrings.length > 0) {
				for (String str : pStrings) {
					if (StringUtils.isNotEmpty(str)) {

						BigDecimal id = new BigDecimal(str);
						UserModel usModel = userModelMapper.selectByPrimaryKey(id);
							if(usModel==null)
								continue;
//						if (StringUtils.isNotEmpty(usModel.getSupplierId())) {
//							supplierBrandModelMapper.deleteBySupplierId(usModel.getSupplierId());
//							supplierCategoryModelMapper.deleteBySupplierId(usModel.getSupplierId());
//							supplierSubModelMapper.deleteBySupplierId(usModel.getSupplierId());
//						}
						userModelMapper.deleteByPrimaryKey(id);
						
						deleteAllSupplier(usModel.getSupplierId());

					}
				}
			}
		}
		return 0;
	}
	
	
	private void deleteAllSupplier(String supplierId) throws ServiceException{
		try{
		// delete supplerBrand/supplier category/ supplier sub class
		if(org.apache.commons.collections.CollectionUtils.isEmpty(productInfoMapper.getProductInfoModelBySupplierId(supplierId))){
			
			deleteBrandAndCategoryAndDept(supplierId);
			// delete Delivery Fee product.
			
			productAuditService.deleteBySupplierId(supplierId);
			
			productUpFieldService.deleteBySupplierId(supplierId);
		}
		

		productInfoService.deleteDeliveryFeeProductBySu(supplierId);
		// delete suppler ,
		supplierService.deleteBySupplerIdNotExistsPd(supplierId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public UserViewVo getUserViewVoById(String id) {
		UserPopulator userPopulator = new UserPopulator();
		UserModel userModel = null;
		UserViewVo userViewVo = null;
		if (id != null) {
			userModel = userModelMapper.selectByPrimaryKey(new BigDecimal(id));
		}
		if (userModel != null) {
			userViewVo = userPopulator.converModelToUserViewVo(userModel, sessionService);
		}
		return userViewVo;
	}

	@Override
	public List<UserModel> getUserModeListByEmail(Map<String, Object> map) {
		return userModelMapper.getUserModeListByEmail(map);
	}

	@Override
	public ResponseData<UserViewVo> checkUserSave(UserViewVo userViewVo) throws SystemException {
		ResponseData<UserViewVo> responseData = (ResponseData<UserViewVo>) responseDataService
				.getReturnData(UserServiceImpl.class);
		if (userViewVo != null) {
			if (userViewVo.getId() == null) {
				if (StringUtils.isEmpty(userViewVo.getUserId())) {
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseData.add("user_add_user_id");
				} else {
					UserModel userModel = userModelMapper.selectUserByUserId(userViewVo.getUserId().trim());
					// input user id exist in DB..
					if (userModel != null) {
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseData.add("user_add_user_id_check");
					}
				}
			}

			if (StringUtils.isEmpty(userViewVo.getPassword())) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_add_user_password");
			} else if (checkPassword(userViewVo, responseData)) {
				// ok
			}

//            if (StringUtils.isNotEmpty(userViewVo.getUserRole())
//                    && "SUPPLIER".equals(userViewVo.getUserRole())
//                    && StringUtils.isEmpty(userViewVo.getSupplierId())) {
//                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//                responseData.add("user_add_supplier_id");
//            }


			if (StringUtils.isEmpty(userViewVo.getUserRole())) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_add_user_role");
			} else if ("SUPPLIER".equals(userViewVo.getUserRole()) && StringUtils.isEmpty(userViewVo.getSupplierId())) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_add_supplier_id");
			}

			if (StringUtils.isEmpty(userViewVo.getUserName())) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_add_user_name");
			}

			// check supplier data...
			if (StringUtils.isNotEmpty(userViewVo.getSupplierId()) && "SUPPLIER".equals(userViewVo.getUserRole())) {
				List<UserModel> userModels = userModelMapper.getAllUserBySupplierId(userViewVo);
				if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userModels)) {
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseData.add("user_supplier_id_exist");
				}
				if (userViewVo.getSupplierId().length() != 10) {
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					responseData.add("user_supplier_id_len");
				}

				SupplierVo vo = userViewVo.getSupplierVo();
				if (vo != null) {
					validationSupplierVo(vo, responseData);
				}

			}

			if (StringUtils.isEmpty(userViewVo.getEmail())) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_add_user_email");
			} else {
				if (userViewVo.getId() != null) {
					UserModel userModel = userModelMapper.selectByPrimaryKey(userViewVo.getId());
					if (userModel.getEmail().equals(userViewVo.getEmail())) {

					} else {
						List<UserModel> userModels = userModelMapper.selectUserByUserEmail(userViewVo.getEmail());
						if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userModels)) {
							responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseData.add("user_add_user_email_check");
						}

						Pattern pattern = Pattern
								.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
						Matcher matcher = pattern.matcher(userViewVo.getEmail());
						if (!matcher.matches()) {
							responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
							responseData.add("user_add_user_email_check_error");
						}
					}
				} else {
					List<UserModel> userModels = userModelMapper.selectUserByUserEmail(userViewVo.getEmail());
					if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userModels)) {
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseData.add("user_add_user_email_check");
					}

					Pattern pattern = Pattern
							.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
					Matcher matcher = pattern.matcher(userViewVo.getEmail());
					if (!matcher.matches()) {
						responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
						responseData.add("user_add_user_email_check_error");
					}

				}
			}
		}
		return responseData;
	}

	private boolean validationSupplierVo(SupplierVo data, ResponseData responseData) {

		boolean validation = false;

		if (data != null) {
			// Delivery fee
			// Validation: Blank, or >= 0, with 1 decimal place
			BigDecimal deliveryFee = data.getDeliveryFee();
			if (deliveryFee != null && !ValidateUtils.judgeOneDecimal(deliveryFee.toString())) {
				// ok
				validation = true;
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_edit_update_setting_invalid_delivery_fee");
			}
			// Free delivery threshold
			// Validation: Blank, or >= 0, with 1 decimal place
			BigDecimal freeDeliveryThreshold = data.getFreeDeliveryThreshold();
			if (freeDeliveryThreshold != null && !ValidateUtils.judgeOneDecimal(freeDeliveryThreshold.toString())) {
				// ok
				validation = true;
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_edit_update_setting_invalid_free_delivery_threshold");
			}

			BigDecimal max = data.getMaxDeliveryDay();
			BigDecimal min = data.getMinDeliveryDay();

			if (max != null && min != null && min.intValue() > max.intValue()) {

				validation = true;
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_edit_update_setting_invalid_max_min_value");

			}

			// Mandatory and must be larger than 0.
			BigDecimal warehouseDeliLeadTime = data.getWarehouseDeliLeadTime();
			if (warehouseDeliLeadTime != null && warehouseDeliLeadTime.doubleValue() < 0) {
				validation = true;
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("user_edit_update_setting_invalid_lead_time_value");

			}
		} else {
			validation = true;
			responseData.add("system_error");
		}

		return validation;
	}

	private boolean checkPassword(UserViewVo userVo, ResponseData<UserViewVo> responseData) {

		if (userVo.getId() != null) {
			UserModel usModel = userModelMapper.selectByPrimaryKey(userVo.getId());
			if (usModel != null && StringUtils.isNotEmpty(usModel.getPassword())
					&& StringUtils.isNotEmpty(userVo.getPassword())
					&& !usModel.getPassword().equals(userVo.getPassword())) {
				String newPassword = userVo.getPassword();
				return checkPassword(newPassword, responseData);
			}
		} else {
			if (StringUtils.isNotEmpty(userVo.getPassword())) {
				String newPassword = userVo.getPassword();
				return checkPassword(newPassword, responseData);
			}
		}

		return true;

	}

	private boolean checkPassword(String newPassword, ResponseData<UserViewVo> responseData) {
		if (StringUtils.isEmpty(newPassword)) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_new_password_require");
			return false;
		}

		// a. At least 6 characters in length
		if (newPassword.length() < 6) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_length_invalid");
			return false;
		}

		if (!newPassword.matches(".*?[a-z]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_lower_invalid");
			return false;
		}
		// b. Contains upper case letter (A-Z)

		if (!newPassword.matches(".*?[A-Z]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_upper_invalid");
			return false;
		}
		// d. Contains numeral (0-9)
		if (!newPassword.matches(".*?[\\d]+.*?")) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("forget_password_password_number_invalid");
			return false;
		}

		return true;
	}

	@Override
	public void insertSuppBrand(UserViewVo userViewVo) {
		if (userViewVo.getBrandSelect() != null && userViewVo.getBrandSelect().length > 0) {
			for (String str : userViewVo.getBrandSelect()) {
				SupplierBrandModel supplierBrandModel = new SupplierBrandModel();
				if (StringUtils.isNotEmpty(str)) {
					supplierBrandModel.setBrandCode(new BigDecimal(str));
				}
				if (userViewVo.getSupplierId() != null) {
					supplierBrandModel.setSupplierId(userViewVo.getSupplierId());
				}
				supplierBrandModel.setCreatedBy(sessionService.getCurrentUser().getUserName());
				supplierBrandModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
				supplierBrandModel.setCreatedDate(new Date());
				supplierBrandModel.setLastUpdatedDate(new Date());
				supplierBrandModelMapper.insertSelective(supplierBrandModel);
			}
		} else {
			if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userViewVo.getBrandSelectVos())) {
				for (SupplierBrandVo supplierBrandVo : userViewVo.getBrandSelectVos()) {
					SupplierBrandModel supplierBrandModel = new SupplierBrandModel();
					supplierBrandModel.setBrandCode(supplierBrandVo.getBrandCode());
					if (userViewVo.getSupplierId() != null) {
						supplierBrandModel.setSupplierId(userViewVo.getSupplierId());
					}
					supplierBrandModel.setCreatedBy(sessionService.getCurrentUser().getUserName());
					supplierBrandModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
					supplierBrandModel.setCreatedDate(new Date());
					supplierBrandModel.setLastUpdatedDate(new Date());
					supplierBrandModelMapper.insertSelective(supplierBrandModel);
				}
			}
		}
	}

	@Override
	public void insertSuppCategory(UserViewVo userViewVo) {
		if (userViewVo.getCategorySelect() != null && userViewVo.getCategorySelect().length > 0) {
			for (String str : userViewVo.getCategorySelect()) {
				SupplierCategoryModel supplierCategoryModel = new SupplierCategoryModel();
				if (StringUtils.isNotEmpty(str)) {
					supplierCategoryModel.setCategoryId(str);
				}
				if (userViewVo.getSupplierId() != null) {
					supplierCategoryModel.setSupplierId(new BigDecimal(userViewVo.getSupplierId()));
				}
				supplierCategoryModel.setCreatedBy(sessionService.getCurrentUser().getUserName());
				supplierCategoryModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
				supplierCategoryModel.setCreatedDate(new Date());
				supplierCategoryModel.setLastUpdatedDate(new Date());
				supplierCategoryModelMapper.insertSelective(supplierCategoryModel);
			}
		} else {
			if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userViewVo.getLovSelectVos())) {
				for (SupplierLovVo supplierLovVo : userViewVo.getLovSelectVos()) {
					SupplierCategoryModel supplierCategoryModel = new SupplierCategoryModel();
					supplierCategoryModel.setCategoryId(supplierLovVo.getLovCode());
					if (userViewVo.getSupplierId() != null) {
						supplierCategoryModel.setSupplierId(new BigDecimal(userViewVo.getSupplierId()));
					}
					supplierCategoryModel.setCreatedBy(sessionService.getCurrentUser().getUserName());
					supplierCategoryModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserName());
					supplierCategoryModel.setCreatedDate(new Date());
					supplierCategoryModel.setLastUpdatedDate(new Date());
					supplierCategoryModelMapper.insertSelective(supplierCategoryModel);
				}
			}
		}

	}

	@Override
	public void insertDeptClass(UserViewVo userViewVo) {

		Map<String, String> subClassMap = new HashMap();
		Map<String, String> deptMap = new HashMap();

		List<String> subClassList = new ArrayList<>();
		if (userViewVo.getSubClassSelect() != null && userViewVo.getSubClassSelect().length > 0) {
			for (String str : userViewVo.getSubClassSelect()) {
				SupplierSubModel supplierSubModel = new SupplierSubModel();
				if (StringUtils.isNotEmpty(str) && str.indexOf("_") != -1) {
					String[] arr = str.split("_");
					if (arr != null && arr.length > 0) {
						String deptId = arr[0];
						String classId = arr[1];
						String subClassId = arr[2];

						subClassMap.put(deptId + "_" + classId, deptId + "_" + classId);
						subClassList.add(deptId + "_" + classId);
						supplierSubModel.setDeptId(new BigDecimal(deptId));
						supplierSubModel.setClassId(new BigDecimal(classId));
						supplierSubModel.setSubClassId(new BigDecimal(subClassId));
						supplierSubModel.setSupplierId(userViewVo.getSupplierId());
						supplierSubModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
						supplierSubModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
						supplierSubModel.setCreatedDate(new Date());
						supplierSubModel.setLastUpdatedDate(new Date());
						supplierSubModelMapper.insertSelective(supplierSubModel);
					}
				}
			}

		}

		List<String> classList = new ArrayList<>();

		if (userViewVo.getClassSelect() != null && userViewVo.getClassSelect().length > 0) {
			for (String classStr : userViewVo.getClassSelect()) {
				SupplierSubModel supplierSubModel = new SupplierSubModel();
				if (org.apache.commons.collections.CollectionUtils.isNotEmpty(subClassList)) {
					if (!subClassList.contains(classStr)) {
						if (StringUtils.isNotEmpty(classStr) && classStr.indexOf("_") != -1) {
							String[] arr = classStr.split("_");
							if (arr != null && arr.length > 0) {
								String deptId = arr[0];
								String classId = arr[1];
								String key = deptId + "_" + classId;

								String v = subClassMap.get(key);

								deptMap.put(deptId, deptId);

								if (StringUtils.isEmpty(v)) {
									supplierSubModel.setDeptId(new BigDecimal(deptId));
									supplierSubModel.setClassId(new BigDecimal(classId));
									supplierSubModel.setSupplierId(userViewVo.getSupplierId());
									supplierSubModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
									supplierSubModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
									supplierSubModel.setCreatedDate(new Date());
									supplierSubModel.setLastUpdatedDate(new Date());
									supplierSubModelMapper.insertSelective(supplierSubModel);
								}
							}
						}
					} else {
						if (StringUtils.isNotEmpty(classStr) && classStr.indexOf("_") != -1) {
							String[] arr = classStr.split("_");
							if (arr != null && arr.length > 0) {
								String deptId = arr[0];
								classList.add(deptId);
							}
						}
					}
				} else {
					if (StringUtils.isNotEmpty(classStr) && classStr.indexOf("_") != -1) {
						String[] arr = classStr.split("_");
						if (arr != null && arr.length > 0) {
							String deptId = arr[0];
							String classId = arr[1];
							classList.add(deptId);

							String key = deptId + "_" + classId;

							String v = subClassMap.get(key);

							deptMap.put(deptId, deptId);
							if (StringUtils.isEmpty(v)) {
								supplierSubModel.setDeptId(new BigDecimal(deptId));
								supplierSubModel.setClassId(new BigDecimal(classId));
								supplierSubModel.setSupplierId(userViewVo.getSupplierId());
								supplierSubModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
								supplierSubModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
								supplierSubModel.setCreatedDate(new Date());
								supplierSubModel.setLastUpdatedDate(new Date());
								supplierSubModelMapper.insertSelective(supplierSubModel);

							}
						}
					}
				}
			}
		}

		if (userViewVo.getDeptSelect() != null && userViewVo.getDeptSelect().length > 0) {
			for (String deptStr : userViewVo.getDeptSelect()) {
				SupplierSubModel supplierSubModel = new SupplierSubModel();
				if (org.apache.commons.collections.CollectionUtils.isNotEmpty(classList)) {
					if (!classList.contains(deptStr)) {
						if (StringUtils.isNotEmpty(deptStr)) {

							String key = deptStr;

							String v = deptMap.get(key);

							if (StringUtils.isEmpty(v)) {
								supplierSubModel.setDeptId(new BigDecimal(deptStr));
								supplierSubModel.setSupplierId(userViewVo.getSupplierId());
								supplierSubModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
								supplierSubModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
								supplierSubModel.setCreatedDate(new Date());
								supplierSubModel.setLastUpdatedDate(new Date());
								supplierSubModelMapper.insertSelective(supplierSubModel);
							}
						}
					}
				} else {
					if (StringUtils.isNotEmpty(deptStr)) {

						String key = deptStr;

						String v = deptMap.get(key);

						if (StringUtils.isEmpty(v)) {
							supplierSubModel.setDeptId(new BigDecimal(deptStr));
							supplierSubModel.setSupplierId(userViewVo.getSupplierId());
							supplierSubModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
							supplierSubModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
							supplierSubModel.setCreatedDate(new Date());
							supplierSubModel.setLastUpdatedDate(new Date());
							supplierSubModelMapper.insertSelective(supplierSubModel);
						}
					}
				}
			}
		}
	}

	@Override
	public void insertBrandAndDeptAndLov(UserViewVo userViewVo) {
		if(userViewVo==null)
			return;
		boolean isCheckSupplierId = false;
		List<ProductInfoModel> productInfoModels = isCheckSupplierId(userViewVo);
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(productInfoModels)) {
			for (ProductInfoModel productInfoModel : productInfoModels) {
				if (StringUtils.isNotEmpty(productInfoModel.getProductCode())) {
					isCheckSupplierId = true;
					break;
				}
			}
		}
		userViewVo.setUpdateSupplierId(isCheckSupplierId);
		List<SupplierLovVo> lovUnSelectVos = new ArrayList<>();
		List<BrandModel> brandSelectModels = null;
		List<BrandModel> brandUnSelectModels = null;
		List<SupplierLovVo> lovSelectVos = null;
		List<DeptModel> deptSelect = null;
		List<DeptModel> unDeptSelect = null;
		List<ClassModel> classSelect = null;
		List<SubClassModel> subClassSelect = null;
//		List<SupplierLovVo> lovList = new ArrayList<>();
		if (userViewVo != null && userViewVo.getId() != null) {

			//Add By Harlan King

			CategoryListWsDTO categoryListWsDTO = shopSynService.getAllProduceCateloy();
			for (CategoryWsDTO dto:categoryListWsDTO.getCategories()) {
				SupplierLovVo vo = new SupplierLovVo();
				vo.setLovCode(dto.getCode());
				vo.setLovValue(dto.getName());
				lovUnSelectVos.add(vo);
			}

			lovSelectVos = new ArrayList<>();
			List<SupplierCategoryModel> categoryModelList = new ArrayList<>();
            categoryModelList = supplierCategoryModelMapper.selectBySupplierId(Integer.parseInt(userViewVo.getSupplierId()));
			for (SupplierCategoryModel dto:categoryModelList) {
				SupplierLovVo vo = new SupplierLovVo();
				vo.setLovCode(dto.getCategoryId().toString());
				for (SupplierLovVo lovVo:lovUnSelectVos) {
					if (lovVo.getLovCode().equals(vo.getLovCode())){
						vo.setLovValue(lovVo.getLovValue());
					}
				}
				lovSelectVos.add(vo);
			}
            Iterator<SupplierLovVo> it = lovUnSelectVos.iterator();
			while (it.hasNext()){
			    SupplierLovVo temp = it.next();
                for (SupplierLovVo lovVo:lovSelectVos) {
                    if(temp.getLovCode().equals(lovVo.getLovCode())){
                        it.remove();

                    }
                }
            }

		}


		userViewVo.setLovSelectVos(lovSelectVos);
		userViewVo.setDeptSelectModel(deptSelect);
		userViewVo.setUnDeptSelectModel(unDeptSelect);
		userViewVo.setClassSelectModel(classSelect);
		userViewVo.setSubClassSelectModel(subClassSelect);
		userViewVo.setBrandselectModel(brandSelectModels);
		userViewVo.setBrandUnSelectModel(brandUnSelectModels);
        userViewVo.setLovUnSelectVos(lovUnSelectVos);

	}

	public Map<String, Object> getBrandSelectMap(List<SupplierBrandVo> list) {
		Map<String, Object> brandSelectMap = new HashMap<>();
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
			for (SupplierBrandVo supplierBrandVo : list) {
				brandSelectMap.put(supplierBrandVo.getBrandCode().toString(), supplierBrandVo);
			}
		}
		return brandSelectMap;
	}

	public Map<String, Object> getLovSelectMap(List<SupplierLovVo> list) {
		Map<String, Object> lovSelectMap = new HashMap<>();
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
			for (SupplierLovVo supplierLovVo : list) {
				lovSelectMap.put(supplierLovVo.getLovCode(), supplierLovVo);
			}
		}
		return lovSelectMap;
	}

	@Override
	public void insertBrandAndCategoryAndDept(UserViewVo userVo) {
		// insert brand
		insertSuppBrand(userVo);
		// insert eCategory
		insertSuppCategory(userVo);
		// insert dept
		insertDeptClass(userVo);
	}

	private void deleteBrandAndCategoryAndDept(String supplierId) throws ServiceException {

		try {
			supplierBrandModelMapper.deleteBySupplierId(supplierId);
			supplierCategoryModelMapper.deleteBySupplierId(supplierId);
			supplierSubModelMapper.deleteBySupplierId(supplierId);
		} catch (Exception e) {
			throw e;
		}
	}

	private void updateBrandAndCategoryAndDept(UserViewVo userViewVo) throws ServiceException {

		// frist delete then add
		if (StringUtils.isNotEmpty(userViewVo.getSupplierId())) {
			// delete
			deleteBrandAndCategoryAndDept(userViewVo.getSupplierId());
			// insert
			insertBrandAndCategoryAndDept(userViewVo);

		}

	}

	@Override
	public UserModel converVoToModel(UserViewVo userVo) {
		UserPopulator userPopulator = new UserPopulator();
		UserModel userModel = userPopulator.converVoToModel(userVo, sessionService, userModelMapper);
		return userModel;
	}

	@Override
	public int insertSupplier(UserViewVo userViewVo) {
		SupplierModel supplierModel = new SupplierModel();
		supplierModel.setId(userViewVo.getSupplierId());
		supplierModel.setCreatedBy(sessionService.getCurrentUser().getUserCode());
		supplierModel.setCreatedDate(new Date());
		supplierModel.setLastUpdatedBy(sessionService.getCurrentUser().getUserCode());
		supplierModel.setLastUpdatedDate(new Date());
		return supplierModelMapper.insert(supplierModel);
	}

	private SupplierModel newSupplierModel(UserViewVo userVo) {
		SupplierVo data = userVo.getSupplierVo();

		String userName = null;

		UserInfoModel currentUser = sessionService.getCurrentUser();
		if (currentUser != null) {
			userName = currentUser.getUserCode();
		}

		SupplierModel model = new SupplierModel();
		model.setId(userVo.getSupplierId());
		model.setLastUpdatedBy(userName);
		model.setLastUpdatedDate(new Date());

		if (data != null) {
			BigDecimal deliveryFee = data.getDeliveryFee();
			// Free delivery threshold
			// Validation: Blank, or >= 0, with 1 decimal place
			BigDecimal freeDeliveryThreshold = data.getFreeDeliveryThreshold();

			BigDecimal max = data.getMaxDeliveryDay();
			BigDecimal min = data.getMinDeliveryDay();

			// Mandatory and must be larger than 0.
			BigDecimal warehouseDeliLeadTime = data.getWarehouseDeliLeadTime();

			// Mandatory and default value 0.
			if (deliveryFee == null) {
				//model.setDeliveryFee(BigDecimal.ZERO);
			} else {
				model.setDeliveryFee(deliveryFee);
			}

			// Mandatory and default value 0.
			if (freeDeliveryThreshold == null) {
				//model.setFreeDeliveryThreshold(BigDecimal.ZERO);
			} else {
				model.setFreeDeliveryThreshold(freeDeliveryThreshold);
			}

			if (min != null) {
				model.setMinDeliveryDay(min);
			} else {
				model.setMinDeliveryDay(null);
			}

			if (max != null) {
				model.setMaxDeliveryDay(max);
			} else {
				model.setMaxDeliveryDay(null);
			}

			// Mandatory and must be larger than 0
			model.setWarehouseDeliLeadTime(warehouseDeliLeadTime);

			model.setShopNameEn(data.getShopNameEn());
			model.setShopNameTc(data.getShopNameTc());
			model.setShopNameSc(data.getShopNameSc());
			model.setShopDescEn(data.getShopDescEn());
			model.setShopDescTc(data.getShopDescTc());
			model.setShopDescSc(data.getShopDescSc());
		}
		model.setUpdatedInd("Y");

		return model;

	}

	private void updateSupplier(UserViewVo userViewVo,SupplierModel model ) throws ServiceException {


		List<ProductInfoModel> productInfoModels = isCheckSupplierId(userViewVo);
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(productInfoModels)) {
			for (ProductInfoModel productInfoVo : productInfoModels) {
				if (StringUtils.isEmpty(productInfoVo.getProductCode())) {
					productInfoVo.setSupplierCode(userViewVo.getSupplierId());
					productInfoMapper.updateByPrimaryKeySelective(productInfoVo);
				}
			}
		}

		try {
			
		synchronizationProduct(userViewVo.getSupplierVo());

			if (model != null) {
				
				if(model.getFreeDeliveryThreshold()==null){
					model.setFreeDeliveryThresholdStr("");
				}
				if(model.getDeliveryFee()==null){
					model.setDeliveryFeeStr("");
				}
								
				supplierModelMapper.updateByPrimaryKeySelective(model);

				if (model.getFreeDeliveryThreshold() != null && model.getDeliveryFee() != null) {

					// LOG.info(" update updateByPrimaryKeySelective..");
					ProductInfoModel productInfo = getFreeDeliveryProduct(model, userViewVo.getSupplierId());
					if (productInfo.getId() == null) {
						productInfoService.insertSelective(productInfo);
					} else {
						productInfoService.updateByPrimaryKeySelective(productInfo);
					}

					if (userViewVo.getSupplierVo() != null)
						userViewVo.getSupplierVo().setDisableDeliveryFee(true);

				}

			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		
	} 
	
	private ProductInfoModel getFreeDeliveryProduct(SupplierModel model, String supplierCode) throws ServiceException {

		ProductInfoModel productInfo = productInfoService.getSupplierProductInfoModelBySupplierId(supplierCode);
		try {
			UserInfoModel currentUser = sessionService.getCurrentUser();
			String createdBy = "";
			if (currentUser != null) {
				createdBy = currentUser.getUserCode();
			}
			if (productInfo == null) {
				productInfo = new ProductInfoModel();

				productInfo.setSupplierCode(supplierCode);

				productInfo.setStatus(DRAFT);
				productInfo.setVersion(STAGING);
				productInfo.setAutoApprovalInd(Y);
				productInfo.setCreatedDate(new Date());
				productInfo.setCreatedBy(createdBy);
			}

			if (model.getFreeDeliveryThreshold() != null) {
				productInfo.setSupplierProductCode(StringUtil.decimalFormat(model.getFreeDeliveryThreshold()));
			}
		
			String pCode = retekService.generateSkuId(productInfo.getSupplierCode(),
					productInfo.getSupplierProductCode());
			
			 LOG.info("start get pcod from retekService...ProductCode："+pCode);
			productInfo.setUnitRetail(model.getDeliveryFee());

			productInfo.setProductCode(pCode);
			productInfo.setLastUpdatedDate(new Date());
			productInfo.setDeliveryFeeProductInd("Y");
			productInfo.setLastUpdatedBy(createdBy);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		return productInfo;
	}
	
	private void synchronizationProduct(SupplierVo data) throws ServiceException {

		try {

			String deliveryFee = StringUtil.convert(data.getDeliveryFee());
			String freeDeliveryThreshold = StringUtil.convert(data.getFreeDeliveryThreshold());
			String max = StringUtil.convert(data.getMaxDeliveryDay());
			String min =StringUtil. convert(data.getMinDeliveryDay());
			String via = StringUtil.convert(data.getWarehouseDeliLeadTime());
			SupplierModel oldVo = supplierModelMapper.selectByPrimaryKey(data.getId());

			String userName = null;

			UserInfoModel userVo = sessionService.getCurrentUser();
			if (userVo != null) {
				userName = userVo.getUserCode();
			}

			if (oldVo != null) {
				String oldfreeDeliveryThreshold = StringUtil.convert(oldVo.getFreeDeliveryThreshold());
				String oldDeliveryFee = StringUtil.convert(oldVo.getDeliveryFee());
				String oldMax = StringUtil.convert(oldVo.getMaxDeliveryDay());
				String oldMin = StringUtil.convert(oldVo.getMinDeliveryDay());
				String oldVia = StringUtil.convert(oldVo.getWarehouseDeliLeadTime());

				if (max.equals(oldMax) && min.equals(oldMin) 
						&& via.equals(oldVia) 
						&& deliveryFee.equals(oldDeliveryFee) 
						&& freeDeliveryThreshold.equals(oldfreeDeliveryThreshold)) {

					return;
				}
			}

			ProductInfoModel model = new ProductInfoModel();
			model.setSupplierCode(data.getId());
			model.setLastUpdatedBy(userName);
			model.setLastUpdatedDate(new Date());

			if (StringUtils.isNotEmpty(max)) {
				model.setMaxDeliverDate(new BigDecimal(max));
			} else {
				model.setMaxDeliverDate(null);
			}

			if (StringUtils.isNotEmpty(min)) {
				model.setMinDeliverDate(new BigDecimal(min));
			} else {
				model.setMinDeliverDate(null);
			}

			model.setSupplierLeadTime(data.getWarehouseDeliLeadTime());

			LOG.info(" update user and synchronizationProduct ");
			productInfoService.editSettingSyncProduct(model);

		} catch (Exception e) {

			LOG.error(" synchronizationProduct " + e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}
	
	

	@Override
	public void saveUser(UserViewVo userVo, ResponseData<UserViewVo> responseData) throws ServiceException {

		if (ConstantUtil.ERROR_TYPE_DANGER.equals(responseData.getErrorType())) {
			return;
		}
		try {
			
			String inputSupplierId = userVo.getSupplierId();

			String userRoe = userVo.getUserRole();
			UserModel beforeUpdateUserModel = null;
			if(userVo.getId()!=null)
				beforeUpdateUserModel = userModelMapper.selectByPrimaryKey(userVo.getId());

			boolean deleteFlag = false;
			boolean insertFlag = false;
			boolean updateFlag = false;

			UserInfoModel currentUser = sessionService.getCurrentUser();
			String createdBy = "";
			if (currentUser != null) {
				createdBy = currentUser.getUserCode();
			}
 
			SupplierModel existsSupplierModel = supplierModelMapper.selectByPrimaryKey(inputSupplierId);
			
			
			// supplier
			if ("SUPPLIER".equals(userRoe) && StringUtils.isNotEmpty(inputSupplierId)) {

				// a->b ,delete a,insert b
				if (beforeUpdateUserModel != null && StringUtils.isNotEmpty(beforeUpdateUserModel.getSupplierId())) {

					// delete old suppler
					if (!inputSupplierId.equals(beforeUpdateUserModel.getSupplierId())) {
						deleteFlag = true;

						// insert
						if (existsSupplierModel != null) {
							updateFlag = true;
						} else {
							insertFlag = true;
						}
					} else {
						// update
						updateFlag = true;
					}
				} else {
					// insert
					if (existsSupplierModel != null) {
						updateFlag = true;
					} else {
						insertFlag = true;
					}
				}

			} else {
				if (beforeUpdateUserModel != null && StringUtils.isNotEmpty(beforeUpdateUserModel.getSupplierId())) {
					// delete
					deleteFlag = true;
				}
			}
			
			

			SupplierModel newSupplierModel = newSupplierModel(userVo);
			
			if (insertFlag) {
				newSupplierModel.setCreatedBy(createdBy);
				newSupplierModel.setCreatedDate(new Date());
				supplierModelMapper.insertSelective(newSupplierModel);
				
				updateBrandAndCategoryAndDept(userVo);
			}
			
			if (userVo.getId() == null) {
				// insert user
				insertSelective(userVo);
			} else {
				// update user
				UserModel userModel = converVoToModel(userVo);

				if (StringUtils.isEmpty(userVo.getContactNo())) {
					userModel.setContactNo("");
				}

				if (!"SUPPLIER".equals(userRoe)  || StringUtils.isEmpty(userVo.getSupplierId())) {
					userModel.setSupplierId("");
				}
				
				updateByPrimaryKeySelective(userModel);
			}

			if (updateFlag) {
				updateSupplier(userVo,newSupplierModel);
				updateBrandAndCategoryAndDept(userVo);
			}
			
			
			if (deleteFlag) {
				// delete supplerBrand/supplier category/ supplier sub class
//				if(CollectionUtils.isEmpty(productInfoMapper.getProductInfoModelBySupplierId(beforeUpdateUserModel.getSupplierId()))){
//					
//					deleteBrandAndCategoryAndDept(beforeUpdateUserModel.getSupplierId());
//					// delete Delivery Fee product.
//					
//					productAuditService.deleteBySupplierId(beforeUpdateUserModel.getSupplierId());
//					
//					productUpFieldService.deleteBySupplierId(beforeUpdateUserModel.getSupplierId());
//				}
//				
//
//				productInfoService.deleteDeliveryFeeProductBySu(beforeUpdateUserModel.getSupplierId());
//				// delete suppler ,
//				supplierService.deleteBySupplerIdNotExistsPd(beforeUpdateUserModel.getSupplierId());
				deleteAllSupplier(beforeUpdateUserModel.getSupplierId());
			}
			

			
			
			responseData.setErrorType("success");
			responseData.setReturnData(userVo);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

	}
	


	

	@Override
	public UserModel getUserBySupplierId(String supplierId) {
		return userModelMapper.getUserBySupplierId(supplierId);
	}

	@Override
	public int updateUserForLoginSuccess(UserModel record) throws ServiceException {
		try {
			return userModelMapper.updateUserForLoginSuccess(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int updateUserForLoginFailTime(UserModel record) throws ServiceException {
		try {
			return userModelMapper.updateUserForLoginFailTime(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
}


