package com.mall.b2bp.services.impl.dept;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.dept.ClassModelMapper;
import com.mall.b2bp.daos.dept.SubClassModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.services.dept.ClassService;
import com.mall.b2bp.services.dept.DeptService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.dept.ClassVo;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.user.UserVo;

@Service("classService")
public class ClassServiceImpl implements ClassService {
	private ClassModelMapper classMapper;
	private SubClassModelMapper subClassMapper;
	private DeptService deptService;
	public ClassModelMapper getClassMapper() {
		return classMapper;
	}
	
	public DeptService getDeptService() {
		return deptService;
	}
	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setClassMapper(ClassModelMapper classMapper) {
		this.classMapper = classMapper;
	}

	public List<ClassModel> getAll() {
		return classMapper.getAll();
	}

	@Override
	public List<ClassModel> getClassByDeptId(String deptId) {
		return classMapper.getClassByDeptId(deptId);
	}


	public SubClassModelMapper getSubClassMapper() {
		return subClassMapper;
	}
	@Autowired
	public void setSubClassMapper(SubClassModelMapper subClassMapper) {
		this.subClassMapper = subClassMapper;
	}

	@Override
	public List<ClassVo> getClassList(BigDecimal deptId) {
		List<ClassVo> classList=new ArrayList<>();
		List<ClassModel> classModeList=classMapper.getClassByDeptId(deptId.toString());
		for (ClassModel classModel : classModeList) {
			ClassVo classVo=new ClassVo();
			classVo.setId(classModel.getId());
			classVo.setClassId(classModel.getClassId());
			classVo.setDeptId(classModel.getDeptId());
			classVo.setDescription(classModel.getDescription());
			classList.add(classVo);
		}
		return classList;
	}

	@Override
	public boolean deleteClass(BigDecimal pkId,
			ResponseData<DeptVo> responseData) throws ServiceException {
//		if(checkDeleteClass(pkId,responseData)){
//			return false;
//		}
		int result=classMapper.deleteByPrimaryKey(pkId);
		if(result==-1){
			responseData.add("class_delete_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
//			List<DeptVo> list=this.deptService.getDeptTree();
//			responseData.setReturnDataList(list);
			
			return true;
		}
		
		return false;
	}
	
//	private boolean checkDeleteClass(BigDecimal pkId,
//			ResponseData<ClassVo> responseData){
//		List<SubClassModel> list=subClassMapper.getSubClassByClassId(String.valueOf(pkId));
//		if(list!=null&&!list.isEmpty()){
//			responseData.add("class_check_delete");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return true;
//		}
//		return false;
//	}

	@Override
	public boolean updateClass(ClassVo classVo, UserVo user,ResponseData<DeptVo> responseData) throws ServiceException {
		if(validateUpdateClassEmpty(classVo,responseData)){
			return false;
		}
//		if(validateUpdatgeCheckClass(classVo,responseData)){
		if(validateCheckClass(classVo,responseData)){
			return false;
		}
	try{	
		ClassModel classModel=new ClassModel();
		classModel.setId(classVo.getId());
		classModel.setClassId(classVo.getClassId());
	
		classModel.setDescription(classVo.getDescription());
		classModel.setLastUpdatedBy(user.getUserName());
		classModel.setLastUpdatedDate(new Date());
		//int result=
				classMapper.updateByPrimaryKeySelective(classModel);
		//if(1==result){
			responseData.add("class_update_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return true;
//		}else{
//			responseData.add("class_update_failed");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return false;
//		}
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage(),ex);
		}
	}

	@Override
	public boolean addClass(ClassVo classVo, UserVo user,ResponseData<DeptVo> responseData) throws ServiceException {
		if(validateClassEmpty(classVo,responseData)){
			return false;
		}
		if(validateCheckClass(classVo,responseData)){
			return false;
		}
	try{	
		ClassModel classModel=new ClassModel();
		classModel.setClassId(classVo.getClassId());
		classModel.setDeptId(classVo.getDeptId());
		classModel.setDescription(classVo.getDescription());
		classModel.setCreatedBy(user.getUserName());
		classModel.setLastUpdatedBy(user.getUserName());
		classModel.setCreatedDate(new Date());
		classModel.setLastUpdatedDate(new Date());
		//int result=
				classMapper.insertSelective(classModel);
		//if(1==result){
			responseData.add("class_add_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			
			List<DeptVo> deptList=deptService.getOneDeptById(classModel.getId());
			if(deptList!=null&&!deptList.isEmpty()){
			responseData.setReturnData((DeptVo)deptList.get(0));
			}
			return true;
//		}else{
//			responseData.add("class_add_failed");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return false;
//		}
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage(),ex);
		}
	}
	
	private boolean validateClassEmpty(ClassVo classVo,ResponseData<DeptVo> responseData){
		if(classVo==null||StringUtils.isEmpty(classVo.getDescription())||classVo.getClassId()==null){
			responseData.add("class_description_invalid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		return true;
		}
		return false;
	}
	
	private boolean validateUpdateClassEmpty(ClassVo classVo,ResponseData<DeptVo> responseData){
		if(classVo==null||(StringUtils.isEmpty(classVo.getDescription())&&classVo.getClassId()==null)){
			responseData.add("class_description_invalid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return true;
		}
		return false;
	}
	
	private boolean validateCheckClass(ClassVo classVo,ResponseData<DeptVo> responseData){
		if(classVo.getDeptId()==null){
			return false;
		}
		Map map=new HashMap();
		map.put("classId", classVo.getClassId());
		map.put("id", classVo.getId());
		map.put("deptId", classVo.getDeptId());
		List<ClassModel> classModelList=classMapper.checkClass(map);
		
//		
//		System.out.println("====check class");
//		
//		System.out.println("classId:"+ classVo.getClassId() );
//		System.out.println("id:" +classVo.getId());
//		System.out.println("deptId:"+classVo.getDeptId());
		//BigDecimal pkId=getClassId(classModelList);
		//if(classVo.getId()==null&&pkId!=null){
		if(CollectionUtils.isNotEmpty(classModelList)){
			responseData.add("class_description_exist");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		return true;
		}
//		else{
//			if(pkId!=null&&pkId.longValue()!=classVo.getId().longValue()){
//				responseData.add("class_description_exist");
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return true;
//			}
//		}
		return false;
	}	
//	private boolean validateUpdatgeCheckClass(ClassVo classVo,ResponseData<DeptVo> responseData){
//		if(classVo.getClassId()==null){
//			return false;
//		}
//		Map map=new HashMap();
//		map.put("classId", classVo.getClassId());
//		map.put("deptId", classVo.getDeptId());
//		List<ClassModel> classModelList=classMapper.checkClass(map);
//		BigDecimal pkId=getClassId(classModelList);
//		if(classVo.getId()==null&&pkId!=null){
//			responseData.add("class_description_exist");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return true;
//		}else{
//			if(pkId!=null&&pkId.longValue()!=classVo.getId().longValue()){
//				responseData.add("class_description_exist");
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//				return true;
//			}
//		}
//		return false;
//	}	

//	private BigDecimal getClassId(List<ClassModel> classModelList){
//		if(classModelList!=null&&!classModelList.isEmpty()){
//			return classModelList.get(0).getId();
//		}else{
//			return null;
//		}
//	}

	@Override
	public ClassModel selectByPrimaryKey(BigDecimal id) {
		
		return classMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ClassModel> getClasssBySupplierDeptId(Map map) {

		return classMapper.getClasssBySupplierDeptId(map);
	}

	@Override
	public List<ClassModel> getAllClassByDesc(String deptId) {
		Map<String, Object> map = new HashMap<>();
		map.put("deptId", deptId.split(","));
		return classMapper.getAllClassByDesc(map);
	}

	@Override
	public ClassModel getClassByDeptIdClassId(Map map) {
		return classMapper.getClassByDeptIdClassId(map);
	}





}
