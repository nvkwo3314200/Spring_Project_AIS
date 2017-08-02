package com.mall.b2bp.services.parm;

import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.vos.parm.ParmVo;

public interface ParmService {
public boolean insertOrUpdate(ParmVo vo) throws ServiceException;
	
	public boolean insert(ParmVo vo) throws ServiceException;
	
	public boolean update(ParmVo vo) throws ServiceException;
	
	public ParmVo getById(String id);
	
	public List<ParmVo> getListByCriteria(ParmVo vo);
	
	public List<ParmVo> search(ParmVo vo);
	
	public boolean delete(String id) throws ServiceException;
	
	public boolean checkExist(ParmVo vo);
	
	public List<ParmVo> getSegmentList(ParmVo vo);
	
	public String getWebserviceUrl();
	public String getWebSite() ;
}
