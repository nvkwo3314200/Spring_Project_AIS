package com.mall.b2bp.daos.dept;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.dept.DeptClassSubclassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.models.dept.TreeModel;

public interface DeptModelMapper {
    int deleteByPrimaryKey(BigDecimal deptId);

    int insert(DeptModel record);

    int insertSelective(DeptModel record);

    DeptModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(DeptModel record);

    int updateByPrimaryKey(DeptModel record);
    
    List<DeptModel> checkDept(Map map);
    List<DeptModel> getDeptTree();
	List<DeptClassSubclassModel> getDepClassSubClassList(Map map);
    List<DeptModel> getOneDept(Map map);
    List<DeptModel> getOneDeptById(Map map);
    List<DeptModel> getOneDeptByClassId(Map map);

    List<DeptModel> getDeptsBySupplierId(String supplierId);
    
    List<DeptModel> getAllDeptByDesc(String description);
    
    List<TreeModel>  getDeptTreeList();
    
    List<DeptModel> getAllSelectDeptByUserId(BigDecimal id);
    
    List<DeptModel> getAllUnSelectDeptByUserId(BigDecimal id);
    
    List<DeptModel> getAllDeptByUserId(BigDecimal id);
    
    DeptModel getDeptByDeptId(BigDecimal deptId);
    
    
}