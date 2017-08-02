package com.mall.b2bp.services.dept;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.dept.ClassVo;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.user.UserVo;


public interface ClassService {
	List<ClassModel> getClassByDeptId(String deptId);
	
	List<ClassVo> getClassList(BigDecimal deptId);
	boolean updateClass(ClassVo classVo,UserVo user,ResponseData<DeptVo> responseData) throws ServiceException;
	boolean addClass(ClassVo classVo,UserVo user,ResponseData<DeptVo> responseData) throws ServiceException;


//	boolean deleteClass(BigDecimal deptId, BigDecimal classId,
//			ResponseData<ClassVo> responseData) throws ServiceException;

//	boolean deleteClass(BigDecimal pkId, ResponseData<ClassVo> responseData)
//			throws ServiceException;

	boolean deleteClass(BigDecimal pkId, ResponseData<DeptVo> responseData)
			throws ServiceException;

	
    ClassModel selectByPrimaryKey(BigDecimal id);
    
    ClassModel getClassByDeptIdClassId(Map map);
    
    List<ClassModel> getClasssBySupplierDeptId(Map map);
    
    List<ClassModel> getAllClassByDesc(String deptId);

}
