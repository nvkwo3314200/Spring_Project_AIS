package com.mall.b2bp.services.lov;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.lov.DeliveryDateModel;
import com.mall.b2bp.vos.user.UserVo;

/**
 *  DeliveryDateService
 *  Created on 2016年6月29日.
 */
public interface DeliveryDateService {
	int deleteByPrimaryKey(String id) throws ServiceException;
	int insert(DeliveryDateModel deliveryDateModel) throws ServiceException;
	int insertSelective (DeliveryDateModel deliveryDateModel) throws ServiceException;
	
	DeliveryDateModel selectByPrimaryKey(String id) throws ServiceException;
	
	int updateByPrimaryKeySelective(DeliveryDateModel record);
	int updateByPrimaryKey(DeliveryDateModel record) throws ServiceException;
	
	List<DeliveryDateModel> searchAll() throws ServiceException;
	
	int searchCountByPrimaryKey(String segment);
	
	boolean updateMinMaxDeliveryDate(String minDeliverDate, String maxDeliverDate, UserVo userVo);
	
	String getMinDeliveryDate();
	String getMaxDeliveryDate();
	
}
