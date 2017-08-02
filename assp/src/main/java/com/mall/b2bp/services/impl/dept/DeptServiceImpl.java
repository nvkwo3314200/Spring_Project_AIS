package com.mall.b2bp.services.impl.dept;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.dept.ClassModelMapper;
import com.mall.b2bp.daos.dept.DeptModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.models.dept.DeptClassSubclassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.models.dept.TreeModel;
import com.mall.b2bp.services.dept.ClassService;
import com.mall.b2bp.services.dept.DeptService;
import com.mall.b2bp.services.dept.SubClassService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.dept.ClassVo;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.dept.SubClassVo;
import com.mall.b2bp.vos.user.UserVo;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	private DeptModelMapper deptMapper;
	private ClassModelMapper classModelMapper;
	private ClassService classService;
	private SubClassService subClassService;
	public DeptModelMapper getDeptMapper() {
		return deptMapper;
	}
	
	public ClassService getClassService() {
		return classService;
	}
	@Autowired
	public void setClassService(ClassService classService) {
		this.classService = classService;
	}

	public SubClassService getSubClassService() {
		return subClassService;
	}
	@Autowired
	public void setSubClassService(SubClassService subClassService) {
		this.subClassService = subClassService;
	}

	@Autowired
	public void setDeptMapper(DeptModelMapper deptMapper) {
		this.deptMapper = deptMapper;
	}
	

	public ClassModelMapper getClassModelMapper() {
		return classModelMapper;
	}
	@Autowired
	public void setClassModelMapper(ClassModelMapper classModelMapper) {
		this.classModelMapper = classModelMapper;
	}

	
//	@Override
//	public List<DeptVo> getDeptTree() {
//		List<DeptVo> deptList=new ArrayList<>();
//		long t=new Date().getTime();
//		System.out.println("start time:"+t);
//		List<DeptModel> deptModelList=deptMapper.getDeptTree();
//		long b=new Date().getTime()-t;
//		System.out.println("end time:"+b);
//		for (DeptModel deptModel : deptModelList) {
//			
//			DeptVo dept=new DeptVo();
//			dept.setId(deptModel.getId());
//			dept.setDeptId(deptModel.getDeptId());
//			dept.setDescription(deptModel.getDescription());
//			//classlist
//			List<ClassModel> classModelList=deptModel.getClassList();
//			List<ClassVo> classList=new ArrayList<>();
//			for (ClassModel classModel : classModelList) {
//				ClassVo classVo=new ClassVo();
//				classVo.setId(classModel.getId());
//				classVo.setClassId(classModel.getClassId());
//				classVo.setDeptId(classModel.getDeptId());
//				classVo.setDescription(classModel.getDescription());
//				List<SubClassModel> subClassModelList=classModel.getSubClassList();
//				List<SubClassVo> subClassList=new ArrayList<>();
//				for (SubClassModel subClassModel : subClassModelList) {
//					SubClassVo subClass=new SubClassVo();
//					subClass.setId(subClassModel.getId());
//					subClass.setClassId(subClassModel.getClassId());
//					subClass.setSubClassId(subClassModel.getSubClassId());
//					subClass.setDescription(subClassModel.getDescription());
//					subClass.setEcategroyId(subClassModel.getEcategroyId());
//					subClass.setLovDesc(subClassModel.getLovDesc());
//					subClassList.add(subClass);
//				}
//				classVo.setSubClassList(subClassList);
//				classList.add(classVo);
//			}
//			dept.setClassList(classList);
//			deptList.add(dept);
//		}
//		return deptList;
//	}
	


	@Override
	public boolean deleteDept(BigDecimal pkId,
			ResponseData<DeptVo> responseData) throws ServiceException{
//		if(checkDeleteDept(pkId,responseData)){
//			return false;
//		}
		try{
		int result=deptMapper.deleteByPrimaryKey(pkId);
		if(result==-1){
			responseData.add("dept_delete_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
//			List<DeptVo> list=getDeptTreeList();
//			responseData.setReturnDataList(list);
			return true;
		}
		}catch(Exception e){
			
			throw new ServiceException(e.getMessage(),e);
		}
		return false;
		 
	}
	
//	private boolean checkDeleteDept(BigDecimal deptId,
//			ResponseData<DeptVo> responseData){
//		List<ClassModel> list=classModelMapper.getClassByDeptId(deptId.toString());
//		if(list!=null&&!list.isEmpty()){
//			responseData.add("dept_check_delete");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return true;
//		}
//		return false;
//	}
//	

	@Override
	public boolean updateDept(DeptVo dept, UserVo user,
			ResponseData<DeptVo> responseData) throws ServiceException{
		if(validateUpdateDeptEmpty(dept,responseData)){
			return false;
		}
		if(validateCheckDept(dept,responseData)){
			return false;
		}
		DeptModel deptModel=new DeptModel();
		deptModel.setId(dept.getId());
		deptModel.setDeptId(dept.getDeptId());
		deptModel.setDescription(dept.getDescription());
		deptModel.setLastUpdatedBy(user.getUserName());
		deptModel.setLastUpdatedDate(new Date());
		int result=deptMapper.updateByPrimaryKeySelective(deptModel);
		if(1==result){
			responseData.add("dept_update_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return true;
		}else{
			responseData.add("dept_update_failed");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return false;
		}
	}

	@Override
	public boolean addDept(DeptVo dept, UserVo user,
			ResponseData<DeptVo> responseData)throws ServiceException {
		try{
		if(validateDeptEmpty(dept,responseData)){
			return false;
		}
		if(validateCheckDept(dept,responseData)){
			return false;
		}
		DeptModel deptModel=new DeptModel();
		deptModel.setDeptId(dept.getDeptId());
		deptModel.setDescription(dept.getDescription());
		deptModel.setCreatedBy(user.getUserName());
		deptModel.setLastUpdatedBy(user.getUserName());
		deptModel.setCreatedDate(new Date());
		deptModel.setLastUpdatedDate(new Date());
		//int result=
				deptMapper.insertSelective(deptModel);
		//if(1==result){
			responseData.add("dept_add_success");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			DeptVo resutlDept=new DeptVo();
			resutlDept.setId(deptModel.getId());
			resutlDept.setDeptId(deptModel.getDeptId());
			resutlDept.setDescription(deptModel.getDescription());
			responseData.setReturnData(resutlDept);
			return true;
		//
//			responseData.add("dept_add_failed");
//			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return false;
		//}
			
		}catch(Exception ex){
			throw new ServiceException(ex.getMessage(),ex);
		}
	}
	
	private boolean validateDeptEmpty(DeptVo dept,ResponseData<DeptVo> responseData){
		if(dept==null||StringUtils.isEmpty(dept.getDescription())||dept.getDeptId()==null){
			responseData.add("dept_description_invalid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		return true;
		}

		
		return false;
	}
	
	private boolean validateUpdateDeptEmpty(DeptVo dept,ResponseData<DeptVo> responseData){
		if(dept==null||(StringUtils.isEmpty(dept.getDescription())&&dept.getDeptId()==null)){
			responseData.add("dept_description_invalid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return true;
		}
		return false;
	}
	
	private boolean validateCheckDept(DeptVo dept,ResponseData<DeptVo> responseData){
		if(dept.getDeptId()==null){
			return false;
		}
		Map map=new HashMap();
		map.put("deptId", dept.getDeptId());
		map.put("id", dept.getId());
		
//		
//		System.out.println("id:" +dept.getId());
//		System.out.println("deptId:"+dept.getDeptId());
		
		List<DeptModel> deptModelList=deptMapper.checkDept(map);
		//BigDecimal pkId=getDeptId(deptModelList);
		//if(dept.getId()==null&&pkId!=null){
		if(CollectionUtils.isNotEmpty(deptModelList)){
			responseData.add("dept_description_exist");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
		return true;
		}
//	else{
//			if(pkId!=null&&pkId.longValue()!=dept.getId().longValue()){
//				responseData.add("dept_description_exist");
//				responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//			return true;
//			}
//		}
		return false;
	}	
	@Override
	public List<DeptClassSubclassModel> getDepClassSubClassList(String supplierId) {
		
		Map map=new HashMap();
        map.put("supplierId", supplierId);
		return deptMapper.getDepClassSubClassList(map);
	}
	
	
//	private BigDecimal getDeptId(List<DeptModel> deptModelList){
//		if(deptModelList!=null&&!deptModelList.isEmpty()){
//			return deptModelList.get(0).getId();
//		}else{
//			return null;
//		}
//	}

	@Override
	public boolean updateData(int cid, String rid, String valData,
			UserVo user, ResponseData<DeptVo> responseData) throws ServiceException {
				if(cid==2){
					//update Id
					return updateId(rid,valData,user,responseData);
				}else if(cid==3){
					//update description
					return updateDescripton(rid,valData,user,responseData);
				}
		return false;
	}
	

	
	private boolean updateId(String rid, String valData,
			UserVo user, ResponseData<DeptVo> responseData) throws ServiceException{
				String flag=rid.substring(0,1);
				String id=rid.substring(1, rid.length());
				switch (flag) {
				case "D":
					DeptVo dept=new DeptVo();
					dept.setId(new BigDecimal(id));
					dept.setDeptId(new BigDecimal(valData));
					updateDept(dept, user, responseData);
					break;
					
			case "C":
					ClassVo classVo=new ClassVo();
					classVo.setId(new BigDecimal(id));
					classVo.setClassId(new BigDecimal(valData));
					classService.updateClass(classVo, user, responseData);
					break;
					
			case "S":
				SubClassVo subclass=new SubClassVo();
				subclass.setId(new BigDecimal(id));
				subclass.setSubClassId(new BigDecimal(valData));
				subClassService.updateSubClass(subclass, user, responseData);
				break;

				default:
					return false;
				}
		
				return true;
		
	}
	
	private boolean updateDescripton(String rid, String valData,
			UserVo user, ResponseData<DeptVo> responseData) throws ServiceException{
		String flag=rid.substring(0,1);
		String id=rid.substring(1, rid.length());
		switch (flag) {
		case "D":
			DeptVo dept=new DeptVo();
			dept.setId(new BigDecimal(id));
			dept.setDescription(valData);
			updateDept(dept, user, responseData);
			break;
			
		case "C":
			ClassVo classVo=new ClassVo();
			classVo.setId(new BigDecimal(id));
			classVo.setDescription(valData);
			classService.updateClass(classVo, user, responseData);
			break;
			
		case "S":
			SubClassVo subclass=new SubClassVo();
			subclass.setId(new BigDecimal(id));
			subclass.setDescription(valData);
			subClassService.updateSubClass(subclass, user, responseData);
			break;
			
		default:
			return false;
		}
		
		return true;
		
	}

	@Override
	public List<DeptVo> getOneDept(BigDecimal id) {

		List<DeptVo> deptList=new ArrayList<>();
		Map map=new HashMap();
		map.put("deptId", id);
		List<DeptModel> deptModelList=deptMapper.getOneDept(map);
		for (DeptModel deptModel : deptModelList) {
			
			DeptVo dept=new DeptVo();
			dept.setId(deptModel.getId());
			dept.setDeptId(deptModel.getDeptId());
			dept.setDescription(deptModel.getDescription());
			//classlist
			List<ClassModel> classModelList=deptModel.getClassList();
			List<ClassVo> classList=new ArrayList<>();
			if(classModelList!=null&&!classModelList.isEmpty()){
			for (ClassModel classModel : classModelList) {
				ClassVo classVo=new ClassVo();
				classVo.setId(classModel.getId());
				classVo.setClassId(classModel.getClassId());
				classVo.setDeptId(classModel.getDeptId());
				classVo.setDescription(classModel.getDescription());
				List<SubClassModel> subClassModelList=classModel.getSubClassList();
				List<SubClassVo> subClassList=new ArrayList<>();
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
				classVo.setSubClassList(subClassList);
				classList.add(classVo);
			}
			}
			dept.setClassList(classList);
			deptList.add(dept);
		}
		return deptList;
	
	}
	
	@Override
	public List<DeptVo> getOneDeptById(BigDecimal id) {
		
		List<DeptVo> deptList=new ArrayList<>();
		Map map=new HashMap();
		map.put("classId", id);
		List<DeptModel> deptModelList=deptMapper.getOneDeptById(map);
		for (DeptModel deptModel : deptModelList) {
			
			DeptVo dept=new DeptVo();
			dept.setId(deptModel.getId());
			dept.setDeptId(deptModel.getDeptId());
			dept.setDescription(deptModel.getDescription());
			//classlist
			List<ClassModel> classModelList=deptModel.getClassList();
			List<ClassVo> classList=new ArrayList<>();
			for (ClassModel classModel : classModelList) {
				ClassVo classVo=new ClassVo();
				classVo.setId(classModel.getId());
				classVo.setClassId(classModel.getClassId());
				classVo.setDeptId(classModel.getDeptId());
				classVo.setDescription(classModel.getDescription());
				List<SubClassModel> subClassModelList=classModel.getSubClassList();
				List<SubClassVo> subClassList=new ArrayList<>();
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
				classVo.setSubClassList(subClassList);
				classList.add(classVo);
			}
			dept.setClassList(classList);
			deptList.add(dept);
		}
		return deptList;
		
	}
	
	@Override
	public List<DeptVo> getOneDeptByClassId(BigDecimal subClassId) {
		
		List<DeptVo> deptList=new ArrayList<>();
		Map map=new HashMap();
		map.put("subClassId", subClassId);
		List<DeptModel> deptModelList=deptMapper.getOneDeptByClassId(map);
		for (DeptModel deptModel : deptModelList) {
			
			DeptVo dept=new DeptVo();
			dept.setId(deptModel.getId());
			dept.setDeptId(deptModel.getDeptId());
			dept.setDescription(deptModel.getDescription());
			//classlist
			List<ClassModel> classModelList=deptModel.getClassList();
			List<ClassVo> classList=new ArrayList<>();
			for (ClassModel classModel : classModelList) {
				ClassVo classVo=new ClassVo();
				classVo.setId(classModel.getId());
				classVo.setClassId(classModel.getClassId());
				classVo.setDeptId(classModel.getDeptId());
				classVo.setDescription(classModel.getDescription());
				List<SubClassModel> subClassModelList=classModel.getSubClassList();
				List<SubClassVo> subClassList=new ArrayList<>();
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
				classVo.setSubClassList(subClassList);
				classList.add(classVo);
			}
			dept.setClassList(classList);
			deptList.add(dept);
		}
		return deptList;
		
	}

	@Override
	public DeptVo getDeptById(BigDecimal deptId) {
		DeptVo dept=new DeptVo();
		DeptModel deptModel=this.deptMapper.selectByPrimaryKey(deptId);
		if(deptModel!=null){
			dept.setId(deptModel.getId());
			dept.setDeptId(deptModel.getDeptId());
			dept.setDescription(deptModel.getDescription());
		}
		return dept;
	}

	@Override
	public ClassVo getClassById(BigDecimal classId,BigDecimal deptId) {
		ClassVo classVo=new ClassVo();
		DeptModel deptModel=this.deptMapper.selectByPrimaryKey(deptId);
		if(deptModel!=null){
			classVo.setDeptId(deptModel.getDeptId());
			classVo.setDeptDesc(deptModel.getDescription());
		}
		ClassModel classModel=this.classModelMapper.selectByPrimaryKey(classId);
		if(classModel!=null){
			classVo.setClassId(classModel.getClassId());
			classVo.setDescription(classModel.getDescription());
		}
		return classVo;
	}

	@Override
	public List<DeptModel> getDeptsBySupplierId(String supplierId) {
		return this.deptMapper.getDeptsBySupplierId(supplierId);
	}

	@Override
	public List<DeptModel> getAllDeptByDesc(String description) {
		return deptMapper.getAllDeptByDesc(description);
	}

	@Override
	public List<DeptVo> getDeptTreeList() {

		List<TreeModel> treeModelList=deptMapper.getDeptTreeList();
		Map<BigDecimal,DeptVo> map=new LinkedHashMap<>();
		for (TreeModel treeModel : treeModelList) {
			if(map.containsKey(treeModel.getPdId())){
				DeptVo dept=map.get(treeModel.getPdId());
				map.put(treeModel.getPdId(), createDept(treeModel,dept));
			}else{
				map.put(treeModel.getPdId(), createDept(treeModel,null));
			}
		}

		return getDeptListByMap(map);
	}
	
	private List<DeptVo> getDeptListByMap(Map<BigDecimal,DeptVo> map){
		List<DeptVo> deptList=new ArrayList<>();
		if(!map.isEmpty()){
			
			for ( Map.Entry<BigDecimal,DeptVo> entry: map.entrySet()) {
				deptList.add(entry.getValue());
			}
		}
		return deptList;
	}
	
	private DeptVo createDept(TreeModel treeModel,DeptVo dept){
		if(dept==null){
		 dept=new DeptVo();
		 dept.setId(treeModel.getPdId());
		 dept.setDeptId(treeModel.getDeptId());
		 dept.setDescription(treeModel.getPdDescription());
		}
		
		if(treeModel.getPcId()!=null){
			createClasss(treeModel,dept);
		}
		
		return dept;
	}
	
	private void createClasss(TreeModel treeModel,DeptVo dept){
		ClassVo classVo=null;
		List<ClassVo> classList=dept.getClassList();
		List<ClassVo> newClassList=new ArrayList<>();
		if(classList!=null&&!classList.isEmpty()){
		for (ClassVo tempClass : classList) {
			if(treeModel.getPcId().compareTo(tempClass.getId())==0){
				
				if(treeModel.getPsId()!=null){
				tempClass.addSubClass(createSubClasss(treeModel));
				}
				
				classVo=tempClass;
			}
			newClassList.add(tempClass);
		}
		}
		
		if(classVo==null){
			classVo=new ClassVo();
			classVo.setId(treeModel.getPcId());
			classVo.setClassId(treeModel.getClassId());
			classVo.setDescription(treeModel.getPcDescription());
			classVo.setDeptId(treeModel.getPdId());
			if(treeModel.getPsId()!=null){
				classVo.addSubClass(createSubClasss(treeModel));
			}
			newClassList.add(classVo);
		}
		
		dept.setClassList(newClassList);

	}
	
	private SubClassVo createSubClasss(TreeModel treeModel){
		SubClassVo subClassVo=new SubClassVo();
		subClassVo.setId(treeModel.getPsId());
		subClassVo.setSubClassId(treeModel.getSubClassId());
		subClassVo.setDescription(treeModel.getPsDescription());
		subClassVo.setClassId(treeModel.getPcId());
		subClassVo.setEcategroyId(treeModel.getEcategroyId());
		subClassVo.setLovDesc(treeModel.getLovDesc());
		return subClassVo;
	}

	@Override
	public DeptModel getDeptByDeptId(BigDecimal deptId) {
		
		return deptMapper.getDeptByDeptId(deptId);
	}

	@Override
	public DeptModel selectByPrimaryKey(BigDecimal id) {
		
		return deptMapper.selectByPrimaryKey(id);
	}

}
