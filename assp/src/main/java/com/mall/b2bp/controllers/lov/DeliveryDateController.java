package com.mall.b2bp.controllers.lov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.controllers.product.ProductController;
import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.lov.DeliveryDateModel;
import com.mall.b2bp.models.lov.LovModel;
import com.mall.b2bp.services.lov.DeliveryDateService;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.user.UserVo;

@Controller("DeliveryDateController")
@RequestMapping(value = "/deliveryDate")
public class DeliveryDateController extends BaseConroller {
	private static final Logger LOG = LoggerFactory
			.getLogger(ProductController.class);
	
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Resource(name = "deliveryDateService")
	private DeliveryDateService deliveryDateService;
	
    @Resource(name = "lovService")
	private LovService lovService;
	
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value = "/saveDeliveryDate", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<DeliveryDateModel> saveDeliveryDate(
			@RequestParam(value = "minDeliverDate", required = false) final String minDeliverDate,
			@RequestParam(value = "maxDeliverDate", required = false) final String maxDeliverDate)  throws SystemException {

		ResponseData<DeliveryDateModel> responseData = new ResponseData<>();
		responseData.setResourceBundleMessageSource(messageSource);
		UserVo userVo= sessionService.getCurrentUser();
		
		if(minDeliverDate==null || maxDeliverDate==null){
			responseData.add("delivery_date_update_unselected");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
        if (userVo == null) {
        	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		responseData.add("change_password_invalid_login");
    		return responseData;
        }
        
        boolean flag=deliveryDateService.updateMinMaxDeliveryDate(minDeliverDate , maxDeliverDate , userVo);
		
		if (!flag) {
				responseData.add("delivery_date_update_failed");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				return responseData;
		}
		
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		return responseData;

	}
	
	
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getDeliveryDate", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public Map<String,Object> getDeliveryDate()throws SystemException {
		ResponseData responseData = new ResponseData<>();
		responseData.setResourceBundleMessageSource(messageSource);
//		UserVo userVo= sessionService.getCurrentUser();

 /*       if (userVo == null) {
        	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		responseData.add("change_password_invalid_login");
    		return responseData;
        }*/
        
		Map<String,Object> map=new HashMap<>();
		
		try {
			List<DeliveryDateModel> deliveryDate=deliveryDateService.searchAll();
			List<LovModel> minDeliverDate= lovService.getLovsByLovId(LovType.MIN_DELIVER_DATE);
			List<LovModel> maxDeliverDate= lovService.getLovsByLovId(LovType.MAX_DELIVER_DATE);
			map.put("deliveryDate", deliveryDate);
			map.put("minDeliverDate", minDeliverDate);
			map.put("maxDeliverDate", maxDeliverDate);
			if (deliveryDate != null) {
				responseData.setErrorType("success");
			} 
			return map;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}
	
	
	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
	@RequestMapping(value="/getDeliveryDateDefault", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public Map<String,Object> getDeliveryDateDefault()throws SystemException {
		ResponseData responseData = new ResponseData<>();
		responseData.setResourceBundleMessageSource(messageSource);
//		UserVo userVo= sessionService.getCurrentUser();
		
		/*       if (userVo == null) {
        	responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		responseData.add("change_password_invalid_login");
    		return responseData;
        }*/
		
		Map<String,Object> map=new HashMap<>();
		
		try {
			String minDeliverDateDefault=deliveryDateService.getMinDeliveryDate();
			String maxDeliverDateDefault=deliveryDateService.getMaxDeliveryDate();
					
			map.put("minDeliverDateDefault", minDeliverDateDefault);
			map.put("maxDeliverDateDefault", maxDeliverDateDefault);
			if (minDeliverDateDefault != null || maxDeliverDateDefault != null) {
				responseData.setErrorType("success");
			} else {
				responseData.setErrorType("danger");
			}
			return map;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}
	
}
