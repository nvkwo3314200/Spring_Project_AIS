package com.mall.b2bp.services.mall;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.mall.MallModel;
import com.mall.b2bp.vos.mall.MallVo;

public interface MallService {
	public boolean insertOrUpdate(MallVo vo) throws ServiceException;
	
	public boolean insert(MallVo vo) throws ServiceException;
	
	public boolean update(MallVo vo) throws ServiceException;
	
	public MallVo getById(String id);
	
	public MallModel getById(BigDecimal id);
	
	public List<MallVo> getListByCriteria(MallVo vo);
	
	public List<MallVo> search(MallVo vo);
	
	public boolean delete(String id) throws ServiceException;
	
	public boolean checkExist(MallVo vo);
}
