package com.mall.b2bp.services.impl.product;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.product.ProductImportLogModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.product.ProductImportLogModel;
import com.mall.b2bp.populators.product.ProductImportLogPopulator;
import com.mall.b2bp.services.product.ProductImportLogService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.product.ProductImportLogVo;

@Service("productImportLogService")
public  class ProductImportLogServiceImpl implements ProductImportLogService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductImportLogServiceImpl.class);
	
	ProductImportLogModelMapper productImportLogModelMapper;
	@Resource(name="sessionService")
	SessionService sessionService;
	
	public ProductImportLogModelMapper getProductImportLogModelMapper() {
		return productImportLogModelMapper;
	}
	
	@Autowired
	public void setProductImportLogModelMapper(
			ProductImportLogModelMapper productImportLogModelMapper) {
		this.productImportLogModelMapper = productImportLogModelMapper;
	}

	
	@Override
	public List<ProductImportLogVo> getAllProductImportLog(String importType,String importDateFrStr,String importDateToStr) throws ServiceException{
		ProductImportLogPopulator productImportLogPopulator = new ProductImportLogPopulator();
		List<ProductImportLogVo> productImportLogVos = new ArrayList<>();
		ProductImportLogVo productImportLogVo = new ProductImportLogVo();
		
		Date importDateFr = null;
		Date importDateTo = null;
		
		 try {

			 importDateFr = DateUtils.parseDateStr(importDateFrStr, DateUtils.DATE_FORMAT);
			 importDateTo = DateUtils.parseDateStr(importDateToStr, DateUtils.DATE_FORMAT);
			 if(StringUtils.isNotEmpty(importType)){
			 productImportLogVo.setImportTypeArr(importType.split(","));
			 }
			 productImportLogVo.setImportDateFr(importDateFr);
			 productImportLogVo.setImportDateTo(importDateTo);

	    		List<ProductImportLogModel> productImportLogModels = productImportLogModelMapper.getAllProductImportLog(productImportLogVo);
	    		if(CollectionUtils.isNotEmpty(productImportLogModels)){
	    			for(ProductImportLogModel productImportLogModel : productImportLogModels){
	    				ProductImportLogVo p = productImportLogPopulator.converProductImportModelToVo(productImportLogModel);
	    				productImportLogVos.add(p);
	    			}
	    		}

	        } catch (Exception e) {

	            LOG.error(e.getMessage(), e);
	            throw new ServiceException(e.getMessage(), e);

	        }
		return productImportLogVos;
	}

	@Override
	public void insertProductImportLog(
			ProductImportLogModel productImportLogModel) {
		productImportLogModelMapper.insertSelective(productImportLogModel);
		
	}
}
