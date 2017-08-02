package com.mall.b2bp.services.brand;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.brand.BrandVo;
import com.mall.b2bp.vos.user.SupplierBrandVo;

public interface BrandService {

	boolean importBrandMastor(String fileName,ErrorLog errorLog) throws ServiceException;
	BrandModel selectByPrimaryKey(BigDecimal brandCode);
	int updateByPrimaryKeySelective(BrandModel record);
	List<BrandVo> view(
			String supplierId,
		  String brandCode,
          String descEn,
          String descTc,
          String descSc,
          String sysRef,
          String masterId,String watsonsMallInd)throws ServiceException;
	List<BrandVo> selectByPk(String brandCode,String masterId)throws ServiceException;

	int insertSelective(BrandModel record)throws ServiceException;
	
	List<BrandModel> getBrandsBySupplierId(String supplierId);
	List<SupplierBrandVo> selectBrandList(String supplierId);
    List<BrandModel> selectAllBrandList();
    
    BrandModel getBrandModelByDescEn(String descEn);
    
    List<BrandVo> getAllBrandsBySupplierId(String supplierId);
    
    public boolean updateBrand(BrandVo brandVo, ResponseData<BrandVo> responseData)throws ServiceException ;
	public BrandVo view(String brandCode) throws ServiceException;
    
	public Map<String,Object> updateWatsonsMallInd(String ind, List<BrandVo> list) throws ServiceException ;
}
