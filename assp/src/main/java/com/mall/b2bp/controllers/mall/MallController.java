package com.mall.b2bp.controllers.mall;

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
import com.mall.b2bp.services.mall.MallService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.mall.MallVo;

@Controller
@RequestMapping(value = "/mall")
public class MallController extends BaseController {
	//private static final Logger LOG = LoggerFactory.getLogger(MallController.class);
	
	@Resource
	private MallService mallService;
	
	
	@RequestMapping(value = "/search", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<MallVo> search(@RequestBody final MallVo mallVo)
			throws SystemException {
		ResponseData<MallVo> responseData = responseDataService.getResponseData();
		try {		
			
			List<MallVo> list = mallService.search(mallVo);
			
			responseData.setReturnDataList(list);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS );
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
	}
	
	@RequestMapping(value = "/mallList", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<MallVo> getMallList(@RequestBody final MallVo mallVo)
			throws SystemException {
		ResponseData<MallVo> responseData = responseDataService.getResponseData();
		try {		
			List<MallVo> list = mallService.getListByCriteria(mallVo);
			responseData.setReturnDataList(list);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS );
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<MallVo> delete(@RequestBody final String ids)
			throws SystemException {

		ResponseData<MallVo> responseData = responseDataService.getResponseData();

		try {
			boolean flag = mallService.delete(ids);
			responseData.setErrorType(flag? ConstantUtil.ERROR_TYPE_SUCCESS:ConstantUtil.ERROR_TYPE_DANGER);
		} catch (Exception e) {
			String errText = e.getMessage();
			int index = errText.indexOf("ORA-");
			String oracleErrCode = errText.substring(index, index + 9);
			if("ORA-02292".equals(oracleErrCode)) {
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
				responseData.add("child_record_exists");
			} else {
				LOG.error(e.getMessage(), e);
				throw new SystemException(e.getMessage(), e);
			}
		}
		return responseData;

	}
	
	@RequestMapping(value = "/mallDetail", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/xml",
			"application/json" })
	@ResponseBody
	public ResponseData<MallVo> search(@RequestBody final String id)
			throws SystemException {
		ResponseData<MallVo> responseData = responseDataService.getResponseData();
		try {		
			MallVo vo = mallService.getById(id);
			responseData.setReturnData(vo);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS );
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
	}

	
	@RequestMapping(value = "/save", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = { "application/json" })
	@ResponseBody
	public ResponseData<MallVo> save(@RequestBody final MallVo vo)
			throws SystemException, ServiceException {

		ResponseData<MallVo> responseData = responseDataService.getResponseData();
		try {
			boolean flag = mallService.insertOrUpdate(vo);
			responseData.setErrorType(flag? ConstantUtil.ERROR_TYPE_SUCCESS:ConstantUtil.ERROR_TYPE_DANGER);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;

	}
	
	@RequestMapping(value="/check", method={RequestMethod.POST}, produces={"application/json"})
	@ResponseBody
	public ResponseData<MallVo> checkExist(@RequestBody final MallVo vo) throws SystemException, ServiceException {
		ResponseData<MallVo> responseData = responseDataService.getResponseData();
		try {
			boolean flag = mallService.checkExist(vo);
			responseData.setErrorType(flag? ConstantUtil.ERROR_TYPE_SUCCESS:ConstantUtil.ERROR_TYPE_DANGER);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
		return responseData;
	}
}
