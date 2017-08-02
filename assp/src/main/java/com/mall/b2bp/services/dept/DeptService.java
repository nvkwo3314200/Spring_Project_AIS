package com.mall.b2bp.services.dept;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.dept.DeptClassSubclassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.dept.ClassVo;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.user.UserVo;

public interface DeptService {
	
	boolean deleteDept(BigDecimal deptId,ResponseData<DeptVo> responseData) throws ServiceException;
	boolean addDept(DeptVo dept,UserVo user,ResponseData<DeptVo> responseData) throws ServiceException;
	boolean updateData(int pid,String rid,String valData,UserVo user,ResponseData<DeptVo> responseData) throws ServiceException;

	boolean updateDept( DeptVo dept, UserVo user,
			ResponseData<DeptVo> responseData) throws ServiceException;
	List<DeptClassSubclassModel> getDepClassSubClassList(String supplierId);
	
	List<DeptVo> getOneDept(BigDecimal id);
	List<DeptVo> getOneDeptById(BigDecimal classId);
	List<DeptVo> getOneDeptByClassId(BigDecimal subClassId);
	DeptVo getDeptById(BigDecimal deptId);

	ClassVo getClassById(BigDecimal classId, BigDecimal deptId);
	
	List<DeptModel> getDeptsBySupplierId(String supplierId);
	
	 List<DeptModel> getAllDeptByDesc(String description);
	 
	 
	 List<DeptVo> getDeptTreeList();
	 
	 DeptModel getDeptByDeptId(BigDecimal deptId);
	 
	 DeptModel selectByPrimaryKey(BigDecimal id);
}
