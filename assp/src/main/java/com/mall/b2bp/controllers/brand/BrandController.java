package com.mall.b2bp.controllers.brand;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.brand.BrandUpdateVo;
import com.mall.b2bp.vos.brand.BrandVo;

@Controller("BrandController")
@RequestMapping(value = "/brand")
public class BrandController extends BaseConroller {

	private static final Logger LOG = LoggerFactory
			.getLogger(BrandController.class);

	@Resource(name = "brandService")
	private BrandService brandService;

	@Secured({ "ROLE_SYSTEMADMIN" })
	@RequestMapping(value = "/view", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml","application/json","plain/text; charset=UTF-8" })
	@ResponseBody
	public List<BrandVo> view(
			@RequestParam(value = "brandCode", required = false) final String brandCode,
			@RequestParam(value = "descEn", required = false) final String descEn,
			@RequestParam(value = "descTc", required = false) final String descTc,
			@RequestParam(value = "descSc", required = false) final String descSc,
			@RequestParam(value = "sysRef", required = false) final String sysRef,
			@RequestParam(value = "masterId", required = false) final String masterId,
			@RequestParam(value = "watsonsMallInd", required = false) final String watsonsMallInd,
			@RequestParam(value = "supplierId", required = false) final String supplierId)
			throws SystemException {

		try {
			return brandService.view(supplierId,brandCode, descEn, descTc, descSc, sysRef,
					masterId, watsonsMallInd);

		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}

	}

	@Secured({ "ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER" })
	@RequestMapping(value = "/getBrandBycode", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public BrandVo getBrandByCode(
			@RequestParam(value = "brandCode", required = false) final String brandCode)
			throws SystemException {

		try {
			return brandService.view(brandCode);

		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}

	@Secured({ "ROLE_SYSTEMADMIN" })
	@RequestMapping(value = "/updateWatsonsMallInd", method = {	RequestMethod.POST, RequestMethod.GET }, produces = {"application/xml", "application/json" })
	@ResponseBody
	public ResponseData update(@RequestBody BrandUpdateVo data)
			throws SystemException {
		ResponseData responseData = (ResponseData<BrandVo>) responseDataService.getReturnData(BrandVo.class);
		responseData.setResourceBundleMessageSource(messageSource);
		try {
			if (data == null || data.getBrandList() == null) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("system_error");
				return responseData;
			}

			Map<String,Object> map=brandService.updateWatsonsMallInd(data.getFlag(),data.getBrandList());
			
			if ((int)map.get("failure")>0) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			}
//			if((int)map.get("success")>0){
//			}
//			if(map.get("failureBrand").toString().length()>0){
				responseData.add("watsonsmall_brands_change_failure");
				String[] param = {map.get("failureBrandCode").toString()+"  "};
				responseData.putMessagesParamArray("watsonsmall_brands_change_failure", param);
			}else{
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			}
			
			return responseData;

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
		}

	}

}
