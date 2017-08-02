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

import com.mall.b2bp.daos.dept.DeptModelMapper;
import com.mall.b2bp.daos.dept.SubClassModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.services.dept.ClassService;
import com.mall.b2bp.services.dept.DeptService;
import com.mall.b2bp.services.dept.SubClassService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.dept.SubClassVo;
import com.mall.b2bp.vos.user.UserVo;

@Service("subClassService")
public class SubClassServiceImpl implements SubClassService {
	private SubClassModelMapper subClassMapper;
	private DeptService deptService;
	private DeptModelMapper deptModelMapper;
	public DeptModelMapper getDeptModelMapper() {
		return deptModelMapper;
	}
	@Autowired
	public void setDeptModelMapper(DeptModelMapper deptModelMapper) {
		this.deptModelMapper = deptModelMapper;
	}
	private ClassService classService;
	public ClassService getClassService() {
		return classService;
	}
	@Autowired
	public void setClassService(ClassService classService) {
		this.classService = classService;
	}
	public SubClassModelMapper getSubClassMapper() {
		return subClassMapper;
	}
	public DeptService getDeptService() {
		return deptService;
	}
	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	@Autowired
	public void setSubClassMapper(SubClassModelMapper subClassMapper) {
		this.subClassMapper = subClassMapper;
	}

	public List<SubClassModel> getAll() {
		return subClassMapper.getAll();
	}

	@Override
	public List<SubClassModel> getSubClassByClassId(String classId) {
		return subClassMapper.getSubClassByClassId(classId);
	}

	@Override
	public List<SubClassVo> getSubClassList(Map map) {
		List<SubClassVo> subClassList=new ArrayList<>();
		List<SubClassModel> subClassModelList=this.subClassMapper.getSubClassList(map);
		for (SubClassModel subClassModel : subClassModelList) {
			SubClassVo subClass=new SubClassVo();
			subClass.setId(subClassModel.getId());
			subClass.setClassId(subClassModel.getClassId());
			subClass.setSubClassId(subClassModel.getSubClassId());
			subClass.setDescription(subClassModel.getDescription());
			subClass.setEcategroyId(subClassModel.getEcategroyId());
			subClass.setLovDesc(subClassModel.getLovDesc());
			subClassList.add(subClass);
		}
		return subClassList;
	}
	
			
	
	@Override
	public boolean updateEstoreCategory(SubClassVo subClassVo, UserVo user,
			ResponseData<DeptVo> responseData) throws ServiceException {

		SubClassModel subClassModel=new SubClassModel();
		subClassModel.setId(subClassVo.getId());
		subClassModel.setEcategroyId(subClassVo.getEcategroyId());
		subClassModel.setLastUpdatedBy(user.getUserName());
		try{subClassModel.setLastUpdatedDate(new Date());
		//int result=
				subClassMapper.updateByPrimaryKeySelective(subClassModel);
		//if(1==result){
			responseData.add("sub_class_update_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			List<DeptVo> deptList=deptService.getOneDeptByClassId(subClassModel.getId());
			if(deptList!=null&&!deptList.isEmpty()){
			responseData.setReturnData((DeptVo)deptList.get(0));
			}
			
			return true;
//		}else{
//			responseData.add("sub_class_update_failed");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return false;
//		}
			
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage(),ex);
		}
	}

	@Override
	public boolean updateSubClass(SubClassVo subClassVo, UserVo user,
			ResponseData<DeptVo> responseData) throws ServiceException {
		if(validateUpdateSubClassEmpty(subClassVo,responseData)){
			return false;
		}
		//if(validateUpdateCheckSubClass(subClassVo,responseData)){
			if(validateCheckSubClass(subClassVo,responseData)){
			return false;
		}
		try{SubClassModel subClassModel=new SubClassModel();
		subClassModel.setId(subClassVo.getId());
		subClassModel.setEcategroyId(subClassVo.getEcategroyId());
		subClassModel.setSubClassId(subClassVo.getSubClassId());
		subClassModel.setClassId(subClassVo.getClassId());
		subClassModel.setDescription(subClassVo.getDescription());
		subClassModel.setLastUpdatedBy(user.getUserName());
		subClassModel.setLastUpdatedDate(new Date());
		//int result=
				subClassMapper.updateByPrimaryKeySelective(subClassModel);
		//if(1==result){
			responseData.add("sub_class_update_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return true;
//		}else{
//			responseData.add("sub_class_update_failed");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return false;
//		}
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage(),ex);
		}
	}

	@Override
	public boolean addSubClass(SubClassVo subClassVo, UserVo user,
			ResponseData<DeptVo> responseData) throws ServiceException {
		if(validateSubClassEmpty(subClassVo,responseData)){
			return false;
		}
		if(validateCheckSubClass(subClassVo,responseData)){
			return false;
		}
		try{
			SubClassModel subClassModel=new SubClassModel();
		subClassModel.setSubClassId(subClassVo.getSubClassId());
		subClassModel.setClassId(subClassVo.getClassId());
		subClassModel.setDescription(subClassVo.getDescription());
		subClassModel.setCreatedBy(user.getUserName());
		subClassModel.setLastUpdatedBy(user.getUserName());
		subClassModel.setCreatedDate(new Date());
		subClassModel.setLastUpdatedDate(new Date());
		//int result=
			subClassMapper.insertSelective(subClassModel);
		//if(1==result){
			responseData.add("sub_class_add_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			List<DeptVo> deptList=deptService.getOneDeptByClassId(subClassModel.getId());
			if(deptList!=null&&!deptList.isEmpty()){
			responseData.setReturnData((DeptVo)deptList.get(0));
			}
			return true;
//		}else{
//			responseData.add("sub_class_add_failed");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return false;
//		}
			
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage(),ex);
		}
	}
	
	private boolean validateSubClassEmpty(SubClassVo subClassVo,ResponseData<DeptVo> responseData){
		if(subClassVo==null||StringUtils.isEmpty(subClassVo.getDescription())||subClassVo.getSubClassId()==null){
			responseData.add("sub_class_description_invalid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		return true;
		}
		
		
		return false;
	}
	
	private boolean validateUpdateSubClassEmpty(SubClassVo subClassVo,ResponseData<DeptVo> responseData){
		if(subClassVo==null||(StringUtils.isEmpty(subClassVo.getDescription())&&subClassVo.getSubClassId()==null)){
			responseData.add("sub_class_description_invalid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return true;
		}
		
		
		return false;
	}
	
	private boolean validateCheckSubClass(SubClassVo subClassVo,ResponseData<DeptVo> responseData){
		Map map=new HashMap();
		map.put("subClassId", subClassVo.getSubClassId());
		map.put("classId", subClassVo.getClassId());
		map.put("id", subClassVo.getId());
		
		
//		System.out.println("subClassId:"+ subClassVo.getSubClassId() );
//		System.out.println("id:" +subClassVo.getId());
//		System.out.println("classId:"+subClassVo.getClassId());
		
		List<SubClassModel> classModelList=subClassMapper.checkSubClass(map);
		//BigDecimal pkId=getSubClassId(classModelList);
		//if(subClassVo.getId()==null&&pkId!=null)
		if(CollectionUtils.isNotEmpty(classModelList)){
			responseData.add("sub_class_description_exist");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return true;
		}
		//else{
//			if(pkId!=null&&pkId.longValue()!=subClassVo.getId().longValue()){
//				responseData.add("sub_class_description_exist");
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return true;
//			}
//		}
		return false;
	}	
	
//	private boolean validateUpdateCheckSubClass(SubClassVo subClassVo,ResponseData<DeptVo> responseData){
//		if(subClassVo.getSubClassId()==null){
//			return false;
//		}
//		Map map=new HashMap();
//		map.put("subClassId", subClassVo.getSubClassId());
////		map.put("classId", subClassVo.getClassId());
//		List<SubClassModel> classModelList=subClassMapper.checkSubClass(map);
//		BigDecimal pkId=getSubClassId(classModelList);
//		if(subClassVo.getId()==null&&pkId!=null){
//			responseData.add("sub_class_description_exist");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return true;
//		}else{
//			if(pkId!=null&&pkId.longValue()!=subClassVo.getId().longValue()){
//				responseData.add("sub_class_description_exist");
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//				return true;
//			}
//		}
//		return false;
//	}	

//	     
	
	

	@Override
	public boolean deleteSubClass(BigDecimal pkId, ResponseData<DeptVo> responseData)
			throws ServiceException {
		int result=this.subClassMapper.deleteByPrimaryKey(pkId);
		if(result==1){
//			List<DeptVo> list=this.deptService.getDeptTree();
//			responseData.setReturnDataList(list);
			responseData.add("sub_class_delete_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return true;
		}
		
		return false;
	}
	
	@Override
	public SubClassModel selectByPrimaryKey(BigDecimal id) {
		
		return this.subClassMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<SubClassModel> getSubClasssBySupplierClassId(Map map) {
		return this.subClassMapper.getSubClasssBySupplierClassId(map);
	}
	@Override
	public List<SubClassModel> getAllSubClassByDesc(String pId) {
		Map<String, Object> map = new HashMap<>();
		map.put("pId", pId.split(","));
		List<SubClassModel> subClassModels =  subClassMapper.getAllSubClassByDesc(map);
		return subClassModels;
	}
	@Override
	public SubClassModel getSubClassByClassIdSubClassId(Map map) {
		
		return subClassMapper.getSubClassByClassIdSubClassId(map);
	}


}
