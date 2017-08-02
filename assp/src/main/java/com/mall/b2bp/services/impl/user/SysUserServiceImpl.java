package com.mall.b2bp.services.impl.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.system.UserRoleManagerMapper;
import com.mall.b2bp.daos.user.SysUserModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.system.RoleModel;
import com.mall.b2bp.models.user.SysUserModel;
import com.mall.b2bp.populators.user.SysUserPopulator;
import com.mall.b2bp.services.system.RoleManagerService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.services.user.SysUserService;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.vos.user.SysUserViewVo;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService{
	
	private SysUserModelMapper sysUserModelMapper;
	
	private UserRoleManagerMapper userRoleManagerMapper;

	@Resource(name = "roleManagerService")
	private RoleManagerService roleManagerService;
	
	
	@Resource(name="sessionService")
	private SessionService sessionService;
	
	
	public SysUserModelMapper getSysUserModelMapper() {
		return sysUserModelMapper;
	}
	
	public UserRoleManagerMapper getUserRoleManagerMapper() {
		return userRoleManagerMapper;
	}
	
	@Autowired
	public void setUserRoleManagerMapper(UserRoleManagerMapper userRoleManagerMapper) {
		this.userRoleManagerMapper = userRoleManagerMapper;
	}



	@Resource
	public void setSysUserModelMapper(SysUserModelMapper sysUserModelMapper) {
		this.sysUserModelMapper = sysUserModelMapper;
	}

	@Override
	public int save(SysUserViewVo sysUserViewVo) {
		if(StringUtils.isNotEmpty(sysUserViewVo.getPassword())) {
			changePassword(sysUserViewVo);
		}
		Integer id;
		if(sysUserViewVo.getId() == null) {
			id = insert(sysUserViewVo);
		} else {
			update(sysUserViewVo);
			id = sysUserViewVo.getId().intValue();
		}
		
		if(sysUserViewVo.getRoleId() != null && id != null) {
			List<RoleModel> roleListDB = roleManagerService.getRoleUserlist(id);
			if(roleListDB != null && !roleListDB.isEmpty()) {
				for (RoleModel roleModelDB : roleListDB) {
					userRoleManagerMapper.delete(roleModelDB);
				}
			}
			RoleModel model = new RoleModel();
			model.setUserId(id);
			model.setRoleId(sysUserViewVo.getRoleId().intValue());
			model.setRoleActive("Y");
			model.setCreatedBy("admin");
			model.setCreatedDate(new Date());
			model.setLastUpdatedBy("admin");
			model.setLastUpdatedDate(new Date());
			userRoleManagerMapper.insert(model);
		}
		return 0;
	}

	@Override
	public int delete(String ids) throws ServiceException {
		if (StringUtils.isNotEmpty(ids)) {
			String[] pStrings = StringUtil.getAllProductId(ids);
			if (pStrings != null && pStrings.length > 0) {
				for (String str : pStrings) {
					if (StringUtils.isNotEmpty(str)) {
						BigDecimal id = new BigDecimal(str);
						
						SysUserModel sysUserModel = sysUserModelMapper.selectByPrimaryKey(id);
						if(sysUserModel == null) continue;
						List<RoleModel> roleListDB = roleManagerService.getRoleUserlist(sysUserModel.getId().intValue());
						if(roleListDB != null && !roleListDB.isEmpty()) {
							for (RoleModel roleModelDB : roleListDB) {
								userRoleManagerMapper.delete(roleModelDB);
							}
						}
						sysUserModelMapper.deleteByPrimaryKey(id);
					}
				}
			}
		}
		return 0;
	}

	@Override
	public int update(SysUserViewVo sysUserViewVo) {
		SysUserModel sysUserModel = new SysUserPopulator().converVoToModel(sysUserViewVo, sessionService);
		sysUserModelMapper.updateByPrimaryKeySelective(sysUserModel);
		return 0;
	}

	@Override
	public SysUserViewVo selectById(String id) {
		SysUserViewVo sysUserViewVo = new SysUserViewVo();
		if(StringUtils.isBlank(id)) return null;
		SysUserModel sysUserModel = sysUserModelMapper.selectByPrimaryKey(new BigDecimal(id));
		sysUserViewVo = new SysUserPopulator().converModelToVo(sysUserModel);
		return sysUserViewVo;
	}

	@Override
	public List<SysUserViewVo> selectList(String idStr, String shopIdStr, String userCode, String userName, String active) {
		SysUserPopulator populator = new SysUserPopulator();
		List<SysUserViewVo> voList = new ArrayList<>();
		SysUserViewVo vo = new SysUserViewVo();
		vo.setId(idStr == null? null : new BigDecimal(idStr));
		vo.setShopId(shopIdStr == null? null : new BigDecimal(shopIdStr));
		vo.setUserCode(userCode);
		vo.setUserName(userName);
		vo.setActiveInd(active);
		List<SysUserModel> sysUserModelList = sysUserModelMapper.selectListByVo(vo);
		for(SysUserModel model : sysUserModelList) {
			voList.add(populator.converModelToVo(model));
		}
		return voList;
	}

	@Override
	public int insert(SysUserViewVo sysUserViewVo) {
		SysUserModel sysUserModel = new SysUserPopulator().converVoToModel(sysUserViewVo, sessionService);
		sysUserModelMapper.insertSelective(sysUserModel);
		int id = sysUserModel.getId().intValue();
		return id;
	}
	
	@Override
	public void changePassword (SysUserViewVo userInfoVo) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		String pwd = md5PasswordEncoder.encodePassword(userInfoVo.getPassword(),userInfoVo.getUserCode());
		userInfoVo.setPassword(pwd);
	}
	
}
