package com.mall.b2bp.controllers.dept;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.services.dept.ClassService;
import com.mall.b2bp.services.dept.DeptService;
import com.mall.b2bp.services.dept.SubClassService;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.dept.ClassVo;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.dept.SubClassVo;
import com.mall.b2bp.vos.lov.LovVo;
import com.mall.b2bp.vos.user.UserVo;


@Controller("DeptController")
@RequestMapping(value = "/dept")
public class DeptController extends BaseConroller {
    private static final Logger LOG = LoggerFactory.getLogger(DeptController.class);
    @Resource(name = "deptService")
	private DeptService deptService;
	@Resource(name = "sessionService")
	SessionService sessionService;
	@Resource(name = "lovService")
	private LovService lovService;
	
    @Resource(name = "subClassService")
	private SubClassService subClassService;
    
    @Resource(name = "classService")
    private ClassService classService;
	
    //delete subClassDelete
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value="/subClassDelete", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<DeptVo> subClassDelete(@RequestParam(value = "id", required = true) final BigDecimal id)throws SystemException, ServiceException{
		
		ResponseData<DeptVo> responseData=(ResponseData<DeptVo>) responseDataService.getReturnData(DeptVo.class);
    	UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
				responseData.add("user_login_expire");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
    	this.subClassService.deleteSubClass(id, responseData);
		return responseData;
		
	}
	
	//delete classDelete
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value="/classDelete", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<DeptVo> classDelete(@RequestParam(value = "id", required = true) final BigDecimal id)throws SystemException, ServiceException{
		
		ResponseData<DeptVo> responseData=(ResponseData<DeptVo>) responseDataService.getReturnData(DeptVo.class);
		UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
			responseData.add("user_login_expire");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
		this.classService.deleteClass(id, responseData);
		return responseData;
		
	}
	
	//delete deptDelete
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value="/deptDelete", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<DeptVo> deptDelete(@RequestParam(value = "id", required = true) final BigDecimal id)throws SystemException, ServiceException{
		
		ResponseData<DeptVo> responseData=(ResponseData<DeptVo>) responseDataService.getReturnData(DeptVo.class);
		UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
			responseData.add("user_login_expire");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
		this.deptService.deleteDept(id, responseData);
		return responseData;
		
	}
    
//    addDept
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/addDept", method = {RequestMethod.POST},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public ResponseData<DeptVo> addDept(@RequestBody final DeptVo deptVo) throws SystemException {
    	
    	ResponseData<DeptVo> responseData=(ResponseData<DeptVo>) responseDataService.getReturnData(DeptVo.class);
    	UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
				responseData.add("user_login_expire");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
		try{
			this.deptService.addDept(deptVo, userVo, responseData);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw new SystemException(e.getMessage(),e);
		}
	
    	return responseData;
    }
	
//    addClass
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value = "/addClass", method = {RequestMethod.POST},
	produces = {"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<DeptVo> addClass(@RequestBody final ClassVo classVo) throws SystemException {
		
		ResponseData<DeptVo> responseData=(ResponseData<DeptVo>) responseDataService.getReturnData(DeptVo.class);
		UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
			responseData.add("user_login_expire");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
		try{
			this.classService.addClass(classVo, userVo, responseData);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw new SystemException(e.getMessage(),e);
		}
		
		return responseData;
	}
	
//    addSubClass
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value = "/addSubClass", method = {RequestMethod.POST},
	produces = {"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<DeptVo> addSubClass(@RequestBody final SubClassVo subClassVo) throws SystemException {
		
		ResponseData<DeptVo> responseData=(ResponseData<DeptVo>) responseDataService.getReturnData(DeptVo.class);
		UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
			responseData.add("user_login_expire");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
		try{
			this.subClassService.addSubClass(subClassVo, userVo, responseData);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw new SystemException(e.getMessage(),e);
		}
		
		return responseData;
	}
    
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value="/updateData", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<DeptVo> updateData(
			@RequestParam(value = "pid", required = true) final int pid,
			@RequestParam(value = "rid", required = true) final String rid,
			@RequestParam(value = "valData", required = false) final String valData)throws SystemException, ServiceException{
		
		ResponseData<DeptVo> responseData=(ResponseData<DeptVo>) responseDataService.getReturnData(DeptVo.class);
    	UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
				responseData.add("user_login_expire");
				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		
    	deptService.updateData(pid, rid, valData, userVo, responseData);
		return responseData;
		
	}
	
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value="/saveEstoreCategory", method = {RequestMethod.POST,RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ResponseData<DeptVo> saveEstoreCategory(@RequestParam(value = "ecategroyId", required = false) final String ecategroyId,@RequestParam(value = "subClassId", required = true) final String subClassId)throws SystemException, ServiceException{
		
		ResponseData<DeptVo> responseData=(ResponseData<DeptVo>) responseDataService.getReturnData(DeptVo.class);
		UserVo userVo = sessionService.getCurrentUser();
		if(userVo==null){
			responseData.add("user_login_expire");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		if(StringUtils.isEmpty(ecategroyId)){
			responseData.add("sub_class_estore_category_empty");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return responseData;
		}
		SubClassVo subClassVo=new SubClassVo();
		subClassVo.setId(new BigDecimal(subClassId));
		subClassVo.setEcategroyId(ecategroyId);
		subClassService.updateEstoreCategory(subClassVo, userVo, responseData);
		return responseData;
		
	}
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value="/getDeptDetail", method = {RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public DeptVo getDeptDetail(@RequestParam(value = "deptId", required = true) final BigDecimal deptId)throws SystemException, ServiceException{
		return this.deptService.getDeptById(deptId);
		
	}
	
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value="/getClassDetail", method = {RequestMethod.GET}, produces={"application/xml", "application/json"})
	@ResponseBody
	public ClassVo getClassDetail(@RequestParam(value = "deptId", required = true) final BigDecimal deptId,@RequestParam(value = "classId", required = true) final BigDecimal classId)throws SystemException, ServiceException{
		return this.deptService.getClassById(classId, deptId);
		
	}
    
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/getDeptTree", method = {RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<DeptVo> getDeptTree() {
		return deptService.getDeptTreeList();
//		return deptService.getDeptTree();
    }
	
	@Secured({"ROLE_SYSTEMADMIN"})
	@RequestMapping(value = "/getCategorys", method = {RequestMethod.GET},
	produces = {"application/xml", "application/json"})
	@ResponseBody
	public List<LovVo> getCategorys() {
		return lovService.getLovListByLovType(LovType.ESTORE_CATEGORY);
	}
	
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/getAllDeptByDesc", method = {RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<DeptModel> getAllDeptByDesc(@RequestParam(value = "description", required = false) final String description) {
		return deptService.getAllDeptByDesc(description);
    }
	
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/getAllClassByDesc", method = {RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<ClassModel> getAllClassByDesc(@RequestParam(value = "deptId", required = false) final String deptId) {
		List<ClassModel> csClassModels = null;
		if(StringUtils.isNotEmpty(deptId)){
			csClassModels = classService.getAllClassByDesc(deptId);
		}
		return csClassModels;
    }
	
	@Secured({"ROLE_SYSTEMADMIN"})
    @RequestMapping(value = "/getAllSubClassByDesc", method = {RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<SubClassModel> getAllSubClassByDesc(@RequestParam(value = "classId", required = false) final String classId) {
		List<SubClassModel> subClassModels = null;
		if(StringUtils.isNotEmpty(classId)){
		subClassModels = subClassService.getAllSubClassByDesc(classId);
		}
		return subClassModels;
    }
}
