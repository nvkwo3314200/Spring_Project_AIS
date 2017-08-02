package com.mall.b2bp.services.impl.parm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.parm.ParmModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.parm.ParmModel;
import com.mall.b2bp.populators.parm.ParmPopulator;
import com.mall.b2bp.services.parm.ParmService;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.vos.parm.ParmVo;

@Service("parmService")
public class ParmServiceImpl implements ParmService{
	
	@Resource
	ParmModelMapper parmModelMapper;
	
	@Resource(name="sessionService1")
	SessionService sessionService;
	
	
	@Override
	public boolean insertOrUpdate(ParmVo vo) throws ServiceException {
		return vo.getId() == null? insert(vo):update(vo);
	}

	@Override
	public boolean insert(ParmVo vo) throws ServiceException {
		parmModelMapper.insertSelective(new ParmPopulator().convertVToM(vo, sessionService));
		return true;
	}

	@Override
	public boolean update(ParmVo vo) throws ServiceException {
		parmModelMapper.updateByPrimaryKeySelective(new ParmPopulator().convertVToM(vo, sessionService));
		return false;
	}

	@Override
	public ParmVo getById(String id) {
		ParmVo vo = new ParmPopulator().convertMToV(parmModelMapper.selectByPrimaryKey(new BigDecimal(id)));
		return vo; 
	}
//	private static String GOHAPPY_MALL = "GOHAPPY_MALL";
	private static String URL = "URL";
	private static String WEB_SERVICES  = "WEB_SERVICES";
	private static String WEB_SITE  = "WEB_SITE";
	
	
	@Override
	public String getWebserviceUrl() {
		String vString = "";
		ParmVo vo = new ParmVo();
		vo.setSegment(URL);
		vo.setCode(WEB_SERVICES);
//		vo.setMallId(mallId);
		
		List<ParmVo>  pList =	getListByCriteria(vo); 
		if(CollectionUtils.isNotEmpty(pList)){
			ParmVo vo1 =pList.get(0);
			if(vo1!=null){
				vString = vo1.getValue(); 
			}
			
		}
		return vString;
	}
	
	@Override
	public String getWebSite() {
		String vString = "";
		ParmVo vo = new ParmVo();
		vo.setSegment(URL);
		vo.setCode(WEB_SITE);
//		vo.setMallId(mallId);
		
		List<ParmVo>  pList =	getListByCriteria(vo); 
		if(CollectionUtils.isNotEmpty(pList)){
			ParmVo vo1 =pList.get(0);
			if(vo1!=null){
				vString = vo1.getValue(); 
			}
			
		}
		return vString;
	}
	
	

	@Override
	public List<ParmVo> getListByCriteria(ParmVo vo) {
		List<ParmVo> voList = new ArrayList<>();
		List<ParmModel> modelList = parmModelMapper.selectByCriteria(vo);
		ParmPopulator pop = new ParmPopulator();
		for(ParmModel model : modelList) {
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
				parmModelMapper.deleteByPrimaryKey(new BigDecimal(id));
			}
		}
		return true;
	}

	@Override
	public boolean checkExist(ParmVo vo) {
		ParmVo ckVo = new ParmVo();
		ckVo.setSegment(vo.getSegment());
		ckVo.setCode(vo.getCode());
		ckVo.setMallId(vo.getMallId());
		List<ParmModel> modelList = parmModelMapper.selectByCriteria(ckVo);
		if(modelList != null && modelList.size() > 0) {
			if(vo.getId() == null)	{
				return true;
			} else {
				if(!modelList.get(0).getId().equals(vo.getId())) return true;
			}
		}
		return false;
	}

	@Override
	public List<ParmVo> getSegmentList(ParmVo vo) {
		List<ParmVo> voList = new ArrayList<>();
		List<ParmModel> modelList = parmModelMapper.selectSegmentList(vo);
		ParmPopulator pop = new ParmPopulator();
		for(ParmModel model : modelList) {
			voList.add(pop.convertMToV(model));
		}
		return voList;
	}

	@Override
	public List<ParmVo> search(ParmVo vo) {
		List<ParmVo> voList = new ArrayList<>();
		List<ParmModel> modelList = parmModelMapper.search(vo);
		ParmPopulator pop = new ParmPopulator();
		for(ParmModel model : modelList) {
			voList.add(pop.convertMToV(model));
		}
		return voList;
	}

}
