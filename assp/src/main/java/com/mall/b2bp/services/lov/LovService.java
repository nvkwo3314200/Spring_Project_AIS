package com.mall.b2bp.services.lov;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.lov.LovModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.lov.LovVo;
import com.mall.b2bp.vos.user.UserVo;


public interface LovService {
	List<LovModel> getLovsByLovId(LovType lovType);
	
	boolean  updateLov(List<LovVo> lovList,UserVo userVo,ResponseData<LovVo> responseData,BigDecimal lovId) throws ServiceException;
	boolean  batchDeleteLov(List<LovVo> lovList,UserVo userVo,ResponseData<LovVo> responseData) throws ServiceException;
	List<LovVo> getLovListByLovType(LovType lovType);
	List<LovVo> getCatBySupplierId(String supplierId);
	
	boolean  addValue(LovVo lovVo,UserVo userVo,ResponseData<LovVo> responseData,BigDecimal lovId) throws ServiceException;

	List<LovVo> getCategoryBySupplierId(String supplierId, String lovId);
	
	LovModel getLovModelByLovIdValue( String lovId,String lovValue);
	
	LovModel getLovModelByLovIdDesc( String lovId,String lovDesc);
	
	LovVo getLovById(String lovId,String lovValue);
		
}
