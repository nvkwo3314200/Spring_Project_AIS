package com.mall.b2bp.controllers.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.user.SysUserService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.user.SysUserViewVo;

@Controller
@RequestMapping(value = "/sysUser")
public class SysUserController {
	
	private SysUserService sysUserService;

	public SysUserService getSysUserService() {
		return sysUserService;
	}
	
	@Resource(name = "sysUserService")
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/saveSysUser", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<SysUserViewVo> saveSysUser(@RequestBody final SysUserViewVo userVo) throws SystemException, ServiceException {
			ResponseData<SysUserViewVo> responseData = new ResponseData<>();
			sysUserService.save(userVo);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return responseData;
	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/deleteSysUser", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData deleteUser(
			@RequestParam(value = "userIds", required = false) final String userIds) throws SystemException {
		ResponseData responseData = new ResponseData();
		try {
			sysUserService.delete(userIds);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			//LOG.error(e.getMessage(), e);
		    throw new SystemException(e.getMessage(),e);
		}
		return responseData;

	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/sysUserViewList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<SysUserViewVo> userViewList(
			@RequestParam(value = "id", required = false) final String id,
			@RequestParam(value = "shopId", required = false) final String shopId,
			@RequestParam(value = "userCode", required = false) final String userCode,
			@RequestParam(value = "userName", required = false) final String userName,
			@RequestParam(value = "activate", required = false) final String activate) throws SystemException {
		List<SysUserViewVo> userVos = null;
		try {
			userVos = sysUserService.selectList(id, shopId, userCode, userName, activate);
		} catch (Exception e) {
			//LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return userVos;
	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/sysUserDetail", method = { RequestMethod.POST,RequestMethod.GET }, produces = { "application/xml","application/json" })
	@ResponseBody
	public SysUserViewVo userDetail(
			@RequestParam(value = "userId", required = false) final String userId) throws SystemException {	
		SysUserViewVo viewVo = new SysUserViewVo();
		viewVo = sysUserService.selectById(userId);
		return viewVo;
	}
}
