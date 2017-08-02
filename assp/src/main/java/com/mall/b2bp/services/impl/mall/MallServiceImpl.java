package com.mall.b2bp.services.impl.mall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.mall.MallModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.mall.MallModel;
import com.mall.b2bp.populators.mall.MallPopulator;
import com.mall.b2bp.services.mall.MallService;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.vos.mall.MallVo;

@Service("mallService")
public class MallServiceImpl implements MallService{
	
	@Resource
	MallModelMapper mallModelMapper;
	
	@Resource(name="sessionService1")
	SessionService sessionService;
	
	@Override
	public boolean insertOrUpdate(MallVo vo) throws ServiceException {
		return vo.getId() == null? insert(vo) : update(vo);
	}

	@Override
	public boolean insert(MallVo vo) throws ServiceException {
		MallModel model = new MallPopulator().convertVToM(vo, sessionService);
		mallModelMapper.insertSelective(model);
		return true;
	}

	@Override
	public boolean update(MallVo vo) throws ServiceException {
		MallModel model = new MallPopulator().convertVToM(vo, sessionService);
		mallModelMapper.updateByPrimaryKey(model);
		return true;
	}

	@Override
	public MallVo getById(String id) {
		MallModel model = mallModelMapper.selectByPrimaryKey(new BigDecimal(id));
		if(model != null) return new MallPopulator().convertMToV(model);
		return  null;
	}
	
	@Override
	public MallModel getById(BigDecimal id) {
		return mallModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<MallVo> getListByCriteria(MallVo vo) {
		List<MallModel> list = mallModelMapper.selectByCriteria(vo);
		List<MallVo> voList = new ArrayList<>();
		MallPopulator pop = new MallPopulator();
		for(MallModel model : list) {
			voList.add(pop.convertMToV(model));
		}
		return voList;
	}

	@Override
	public boolean delete(String ids) throws ServiceException {
		String[] idArr = null;
		if(StringUtils.isNotBlank(ids)) {
			idArr = ids.split(";");
		}
		for(String id : idArr) {
			if(StringUtils.isNotBlank(id)) {
				mallModelMapper.deleteByPrimaryKey(new BigDecimal(id));
			}
		}
		return true;
	}

	@Override
	public boolean checkExist(MallVo vo) {
		List<MallModel> list = mallModelMapper.selectByCriteria(vo);
		if(list != null && list.size()>0) return true;
		return false;
	}

	@Override
	public List<MallVo> search(MallVo vo) {
		if(StringUtils.isEmpty(vo.getCode())) vo.setCode(null);
		if(StringUtils.isEmpty(vo.getName())) vo.setName(null);
		List<MallModel> list = mallModelMapper.search(vo);
		List<MallVo> voList = new ArrayList<>();
		MallPopulator pop = new MallPopulator();
		for(MallModel model : list) {
			voList.add(pop.convertMToV(model));
		}
		return voList;
	}

}
