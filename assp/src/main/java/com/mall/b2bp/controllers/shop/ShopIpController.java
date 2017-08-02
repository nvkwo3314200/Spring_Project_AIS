package com.mall.b2bp.controllers.shop;

import java.math.BigDecimal;
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
import com.mall.b2bp.services.shop.ShopIpService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.shop.ShopIpViewVo;

@Controller
@RequestMapping(value = "/shopIp")
public class ShopIpController {
	//private static final Logger LOG = LoggerFactory.getLogger(ShopInfoController.class);
	
	@Resource
	private ShopIpService shopIpService;
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/saveShopIp", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<ShopIpViewVo> save(@RequestBody final ShopIpViewVo shopIpViewVo) throws SystemException, ServiceException {
		ResponseData<ShopIpViewVo> responseDate = new ResponseData<ShopIpViewVo>();
		
		try {
			shopIpService.save(shopIpViewVo);
			responseDate.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(),e);
		}
		return responseDate;
	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/shopIpViewList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public List<ShopIpViewVo> shopViewList(
			@RequestParam(value = "shopId", required = false) final String shopId,
			@RequestParam(value = "exIncludeInd", required = false) final String exIncludeInd
			) throws SystemException {
		List<ShopIpViewVo> ShopIpViewVos = null;
		ShopIpViewVo vo = new ShopIpViewVo();
		vo.setShopId(new BigDecimal(shopId));
		vo.setExIncludeInd(exIncludeInd);
		try {
			ShopIpViewVos = shopIpService.selectListByShopIpViewVo(vo);
		} catch (Exception e) {
			//LOG.error("productInfoVos error:" + e.getMessage(), e);
			throw new SystemException(e.getMessage(),e);
		}
		return ShopIpViewVos;
	}
	
	@Secured({"ROLE_SYSTEMADMIN","ROLE_SUPPLIER"})
	@RequestMapping(value = "/deleteShopIp", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData deleteShop(
			@RequestParam(value = "shopIpIds", required = false) final String shopIpIds) throws SystemException {
		ResponseData responseData = new ResponseData();
		try {
			shopIpService.delete(shopIpIds);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			//LOG.error(e.getMessage(), e);
		    throw new SystemException(e.getMessage(),e);
		}

		return responseData;

	}
}
