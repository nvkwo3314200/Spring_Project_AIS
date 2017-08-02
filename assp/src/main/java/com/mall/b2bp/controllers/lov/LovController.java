package com.mall.b2bp.controllers.lov;

import java.util.ArrayList;
import java.util.List;

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
import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.lov.LovListVo;
import com.mall.b2bp.vos.lov.LovVo;
import com.mall.b2bp.vos.user.UserVo;


@Controller("LovController")
@RequestMapping(value = "/listOfValue")
public class LovController extends BaseConroller {
    private static final Logger LOG = LoggerFactory.getLogger(LovController.class);
    @Resource(name = "lovService")
	private LovService lovService;
	@Resource(name = "sessionService")
	SessionService sessionService;
    

	@Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
    @RequestMapping(value = "/getLovList", method = {RequestMethod.POST, RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<LovVo> getLovList(@RequestParam(value = "lovId", required = true) final String lovId) throws ServiceException {
    	return lovService.getLovListByLovType(LovType.getType(lovId));
    } 
    
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/getValuesList", method = {RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<LovVo> getValuesList() throws ServiceException {
    	List<LovVo> list=new ArrayList<>();
    	for (LovType tp : LovType.values()) {
    		LovVo vo=new LovVo();
    		vo.setLovDesc(tp.getLovDesc());
    		vo.setLovValue(tp.getLovId());
    		list.add(vo);
		}
    	
    	return list;
    }
    
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/getEstoreCategory", method = {RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public String getEstoreCategory() throws ServiceException {
	    	return LovType.ESTORE_CATEGORY.getLovId();
    }
    
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/batchDeleteLov", method = {RequestMethod.POST},
    		produces = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseData<LovVo> batchDeleteLov(@RequestBody final LovListVo lovListVo) throws SystemException {
    	
		ResponseData<LovVo> responseData=(ResponseData<LovVo>) responseDataService.getReturnData(LovVo.class);
    	UserVo userVo = sessionService.getCurrentUser();
    	if(userVo==null){
    			responseData.add("user_login_expire");
    			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		return responseData;
    	}
    	
    	try{
    		lovService.batchDeleteLov(lovListVo.getLovList(), userVo, responseData);
    		List<LovVo> returnDataList=lovService.getLovListByLovType(LovType.getType(lovListVo.getLovId().toString()));
    		responseData.setReturnDataList(returnDataList);
    	}catch(Exception e){
    		LOG.error(e.getMessage(),e);
    		throw new SystemException(e.getMessage(),e);
    	}
    	
    	return responseData;
    }
    
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/updateLov", method = {RequestMethod.POST},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseData<LovVo> updateLov(@RequestBody final LovListVo lovListVo) throws SystemException {
    	
    	ResponseData<LovVo> responseData=(ResponseData<LovVo>) responseDataService.getReturnData(LovVo.class);
    	UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
				responseData.add("user_login_expire");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
		try{
	    lovService.updateLov(lovListVo.getLovList(), userVo, responseData, lovListVo.getLovId());
	    List<LovVo> returnDataList=lovService.getLovListByLovType(LovType.getType(lovListVo.getLovId().toString()));
	    responseData.setReturnDataList(returnDataList);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw new SystemException(e.getMessage(),e);
		}
	
    	return responseData;
    }
    
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/addValue", method = {RequestMethod.POST},
    		produces = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseData<LovVo> addValue(@RequestBody final LovVo lovVo) throws SystemException {
    	
		ResponseData<LovVo> responseData=(ResponseData<LovVo>) responseDataService.getReturnData(LovVo.class);
    	UserVo userVo = sessionService.getCurrentUser();
    	if(userVo==null){
    			responseData.add("user_login_expire");
    			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
    		return responseData;
    	}
    	
    	try{
    		lovService.addValue(lovVo, userVo, responseData, lovVo.getLovId());
    		List<LovVo> returnDataList=lovService.getLovListByLovType(LovType.getType(lovVo.getLovId().toString()));
    		responseData.setReturnDataList(returnDataList);
    	}catch(Exception e){
    		LOG.error(e.getMessage(),e);
    		throw new SystemException(e.getMessage(),e);
    	}
    	
    	return responseData;
    }
    
    
}
