package com.mall.b2bp.daos.lov;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.models.lov.LovModel;

public interface LovModelMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(LovModel record);

    int insertSelective(LovModel record);

    LovModel selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(LovModel record);

    int updateByPrimaryKey(LovModel record);
    
    List<LovModel> getLovsByLovId(Map map);
    List<LovModel> getCatBySupplierId(Map map);
    
    LovModel checkLovByCode(Map map);//param udaId and udaValue
    Long getMaxsequnence(Map map);//param udaId 

	List<LovModel> getCategoryBySupplierId(Map map);
	
	LovModel getLovModelByLovIdValue(Map map);
	
	LovModel getLovModelByLovIdDesc(Map map);
	
	LovModel getLovModelByLovDesc(String lovDesc);
}