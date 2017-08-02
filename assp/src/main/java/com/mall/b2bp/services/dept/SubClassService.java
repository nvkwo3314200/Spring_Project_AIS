package com.mall.b2bp.services.dept;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.dept.SubClassVo;
import com.mall.b2bp.vos.user.UserVo;


public interface SubClassService {
	List<SubClassModel> getSubClassByClassId(String classId);
	boolean addSubClass(SubClassVo subClassVo,UserVo user,ResponseData<DeptVo> responseData) throws ServiceException;
//	boolean deleteSubClass(BigDecimal deptId, BigDecimal classId,BigDecimal subClassId,
//			ResponseData<SubClassVo> responseData) throws ServiceException;
	List<SubClassVo> getSubClassList(Map map);

	boolean updateSubClass(SubClassVo subClassVo, UserVo user,
			ResponseData<DeptVo> responseData) throws ServiceException;
	
	boolean updateEstoreCategory(SubClassVo subClassVo, UserVo user,
			ResponseData<DeptVo> responseData) throws ServiceException;
	boolean deleteSubClass(BigDecimal pkId, ResponseData<DeptVo> responseData)
			throws ServiceException;
	
	SubClassModel selectByPrimaryKey(BigDecimal id);
	
	List<SubClassModel> getSubClasssBySupplierClassId(Map map);
	
	List<SubClassModel> getAllSubClassByDesc(String pId);
	
	SubClassModel getSubClassByClassIdSubClassId(Map map);
}
