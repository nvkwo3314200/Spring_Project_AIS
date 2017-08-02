package com.mall.b2bp.daos.dept;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.dept.SubClassModel;

public interface SubClassModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(SubClassModel record);

    int insertSelective(SubClassModel record);

    SubClassModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(SubClassModel record);

    int updateByPrimaryKey(SubClassModel record);
    
    List<SubClassModel> getAll();
    
    List<SubClassModel> getSubClassByClassId(String classId);
    List<SubClassModel> getSubClassList(Map map);
	List<SubClassModel> checkSubClass(Map map);
	
	List<SubClassModel> getSubClasssBySupplierClassId(Map map);
	
	List<SubClassModel> getAllSubClassByDesc(Map map);
	
	List<SubClassModel> getAllSelectSubClassByUserId(BigDecimal id);
	
	SubClassModel getSubClassByClassIdSubClassId(Map map);
}
