package com.mall.b2bp.services.supplier;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



import org.apache.commons.fileupload.FileUploadException;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.supplier.SupplierModel;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.supplier.SupplierUpdateVo;
import com.mall.b2bp.vos.supplier.SupplierVo;
import com.mall.b2bp.vos.supplier.UserSupplierVo;

/**
 * Created by USER on 2016/3/10.
 */
public interface SupplierService {
	int deleteByPrimaryKey(String id) throws ServiceException;
	int deleteBySupplerIdNotExistsPd(String id) throws ServiceException;

	int insert(SupplierModel record) throws ServiceException;

	int insertSelective(SupplierModel record) throws ServiceException;

	SupplierVo selectByPrimaryKey(String id) throws ServiceException;

	int updateByPrimaryKeySelective(SupplierModel record) throws ServiceException;

	int updateByPrimaryKey(SupplierModel record) throws ServiceException;

	List<SupplierVo> searchAll() throws ServiceException;

	boolean editSetting(SupplierUpdateVo data, ResponseData responseData) throws ServiceException;
	
	 List<UserSupplierVo> getAllUserSupplier();
	 
	 UserSupplierVo getUserSupplierVoBySupplierId(String supplierId);

	 Map getFileItem(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException;
	 
	 List<SupplierModel> getSupplierByUpdatedInd();

	int saveProductCategory(String[] catgorys,BigDecimal suplierId);
}
