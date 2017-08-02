package com.mall.b2bp.controllers.parm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.system.BaseController;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.parm.ParmService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.parm.ParmVo;

@Controller
@RequestMapping(value="/parm")
public class ParmController extends BaseController{
	
	//private static final Logger LOG =  LoggerFactory.getLogger(ParmController.class);
	
	@Resource
	ParmService parmService;
	
	@RequestMapping(value="/save", method={RequestMethod.POST}, produces = {"application/json" })
	@ResponseBody
	public ResponseData<ParmVo> save(@RequestBody final ParmVo parmVo) throws SystemException, ServiceException{
		ResponseData<ParmVo> responseData = responseDataService.getResponseData();
		
		
		boolean flag = parmService.checkExist(parmVo);
		if(flag) {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("parm_is_exist");
			return responseData;
		}
		
		try {
			parmService.insertOrUpdate(parmVo);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
			//e.printStackTrace();
		}
		
		return responseData;
	}
	
	@RequestMapping(value="/delete", method = {RequestMethod.POST, RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public ResponseData<ParmVo> delete(@RequestBody final String ids) throws SystemException, ServiceException {
		ResponseData<ParmVo> responseData = responseDataService.getResponseData();
		try {
			parmService.delete(ids);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
			//e.printStackTrace();
		}
		return responseData;
	}
	
	@RequestMapping(value="/search", method = {RequestMethod.POST, RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public ResponseData<ParmVo> search(@RequestBody final ParmVo vo) throws SystemException, ServiceException {
		ResponseData<ParmVo> responseData = responseDataService.getResponseData();
		try {
			List<ParmVo> voList = parmService.search(vo);
			responseData.setReturnDataList(voList);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
		}
		return responseData;
	}
	
	@RequestMapping(value="/segmentList", method = {RequestMethod.POST, RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public ResponseData<ParmVo> getSegmentList(@RequestBody final ParmVo vo) throws SystemException, ServiceException{
		ResponseData<ParmVo> responseData = responseDataService.getResponseData();
		try {
			List<ParmVo> voList = parmService.getSegmentList(vo);
			responseData.setReturnDataList(voList);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
		}
		return responseData;
	}
	
	@RequestMapping(value="/detail", method={RequestMethod.POST}, produces={"application/json"})
	@ResponseBody
	public ResponseData<ParmVo> detail(@RequestBody final String id) throws SystemException, ServiceException {
		ResponseData<ParmVo> responseData = responseDataService.getResponseData();
		try {
			ParmVo vo = parmService.getById(id);
			responseData.setReturnData(vo);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
		}
		return responseData;
	}
}
