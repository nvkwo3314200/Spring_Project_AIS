package com.mall.b2bp.services.system;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.BusUnitModel;
import com.mall.b2bp.vos.ResponseData;

public interface BusUnitService{

	public List<BusUnitModel> search(BusUnitModel model) throws ServiceException;
	
	public List<BusUnitModel> select(BusUnitModel model) throws ServiceException;
	
	public String run(BusUnitModel busUnitModel);
	
	public ResponseData<BusUnitModel> checkBusSave(BusUnitModel busUnitModel) throws SystemException;
	
	public boolean chacknull(BusUnitModel model);
	
	public int update(BusUnitModel model) throws ServiceException;

	public int delete(BusUnitModel model) throws ServiceException;

	public int insert(BusUnitModel model) throws ServiceException;
}
