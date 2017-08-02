package com.mall.b2bp.daos.dept;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.dept.ClassModel;

public interface ClassModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(ClassModel record);

    int insertSelective(ClassModel record);

    ClassModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(ClassModel record);

    int updateByPrimaryKey(ClassModel record);
    
    List<ClassModel> getAll();
    
    List<ClassModel> getClassByDeptId(String deptId);

	List<ClassModel> checkClass(Map map);
	
	List<ClassModel> getClasssBySupplierDeptId(Map map);
	
	List<ClassModel> getAllClassByDesc(Map map);
	
	List<ClassModel> getAllSelectClassByUserId(BigDecimal id);	
	
	ClassModel getClassByDeptIdClassId(Map map);
}