package com.mall.b2bp.services.impl.lov;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.lov.DeliveryDateMapper;
import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.lov.DeliveryDateModel;
import com.mall.b2bp.services.lov.DeliveryDateService;
import com.mall.b2bp.vos.user.UserVo;

/**
 *  TODO
 *  Created on 2016年6月29日.
 */
@Service("deliveryDateService")
public class DeliveryDateServiceImpl implements DeliveryDateService {


	DeliveryDateMapper deliveryDateMapper;
	
	public DeliveryDateMapper getDeliveryDateMapper() {
		return deliveryDateMapper;
	}

	@Autowired
	public void setDeliveryDateMapper(DeliveryDateMapper deliveryDateMapper) {
		this.deliveryDateMapper = deliveryDateMapper;
	}
	@Override
	public int insert(DeliveryDateModel deliveryDateModel)  throws ServiceException{
		deliveryDateMapper.insert(deliveryDateModel);
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(String id) throws ServiceException {
		int i=deliveryDateMapper.deleteByPrimaryKey(id);
		return i;
	}

	@Override
	public int insertSelective(DeliveryDateModel deliveryDateModel) throws ServiceException {
		int i=deliveryDateMapper.insertSelective(deliveryDateModel);
		return i;
	}

	@Override
	public DeliveryDateModel selectByPrimaryKey(String id) throws ServiceException {
		DeliveryDateModel deliveryDateModel=deliveryDateMapper.selectBySegment(id);
		return deliveryDateModel;
	}
	
	@Override
	public int updateByPrimaryKeySelective(DeliveryDateModel record){
		int i=deliveryDateMapper.updateByPrimaryKeySelective(record);
		return i;
	}

	@Override
	public int updateByPrimaryKey(DeliveryDateModel record) throws ServiceException {
		int i=deliveryDateMapper.updateByPrimaryKey(record);
		return i;
	}

	@Override
	public List<DeliveryDateModel> searchAll() throws ServiceException {
		 List<DeliveryDateModel> list = deliveryDateMapper.searchAll();
		return list;
	}
	@Override
	public int searchCountByPrimaryKey(String segment) {
		int i=deliveryDateMapper.searchCountByPrimaryKey(segment);
		return i;
	}
	@Override
	public boolean updateMinMaxDeliveryDate(String minDeliverDate, String maxDeliverDate, UserVo userVo) {
		
//		String minFlag=selectByPrimaryKey(LovType.MIN_DELIVER_DATE.getLovId().toString());
		int minFlag=searchCountByPrimaryKey(LovType.MIN_DELIVER_DATE.getLovId().toString());
		int maxFlag=searchCountByPrimaryKey(LovType.MAX_DELIVER_DATE.getLovId().toString());
		
		DeliveryDateModel minDateModel=new DeliveryDateModel();
		DeliveryDateModel maxDateModel=new DeliveryDateModel();
		
		minDateModel.setSegmentByLovType(LovType.MIN_DELIVER_DATE);
		minDateModel.setCode(minDeliverDate);
		maxDateModel.setSegmentByLovType(LovType.MAX_DELIVER_DATE);;
		maxDateModel.setCode(maxDeliverDate);
		
		//minDateModel
		try {
				if(minFlag == 1){
					minDateModel.setLastUpdatedBy(userVo.getUserName());
					minDateModel.setLastUpdatedDate(new Date());
					updateByPrimaryKeySelective(minDateModel);
				}else{
					minDateModel.setCreatedBy(userVo.getUserName());
					minDateModel.setCreatedDate(new Date());
					insertSelective(minDateModel);
				}
			} catch (ServiceException e) {
			e.printStackTrace();
			return false;
		}
		
		//maxDateModel
		try {
			if(maxFlag == 1){
				maxDateModel.setLastUpdatedBy(userVo.getUserName());
				maxDateModel.setLastUpdatedDate(new Date());
				updateByPrimaryKeySelective(maxDateModel);
			}else{
				maxDateModel.setCreatedBy(userVo.getUserName());
				maxDateModel.setCreatedDate(new Date());
				insertSelective(maxDateModel);
			}
		} catch (ServiceException e) {
		//	e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public String getMinDeliveryDate() {
		String minDeliverDateDefault=null;
		try {
			DeliveryDateModel deliveryMinDefaultMode= selectByPrimaryKey(LovType.MIN_DELIVER_DATE.getLovId().toString());
			if(deliveryMinDefaultMode !=null)
				minDeliverDateDefault=deliveryMinDefaultMode.getCode();
			else 
				return null;
		} catch (ServiceException e) {
		//	e.printStackTrace();
			return null;
		}
		return minDeliverDateDefault;
	}

	@Override
	public String getMaxDeliveryDate() {
		String maxDeliverDateDefault=null;
		try {
			DeliveryDateModel deliveryMaxDefaultMode= selectByPrimaryKey(LovType.MAX_DELIVER_DATE.getLovId().toString());
			if(deliveryMaxDefaultMode !=null)
				maxDeliverDateDefault=deliveryMaxDefaultMode.getCode();
			else 
				return null;
		} catch (ServiceException e) {
//			e.printStackTrace();
			return null;
		}
		return maxDeliverDateDefault;
	}
	
}
