package com.mall.b2bp.services.impl.lov;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.lov.LovModelMapper;
import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.lov.LovModel;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.lov.LovVo;
import com.mall.b2bp.vos.user.UserVo;

@Service("lovService")
public class LovServiceImpl implements LovService {
	private static final Logger LOG = LoggerFactory
			.getLogger(LovServiceImpl.class);
	private LovModelMapper lovMapper;

	public LovModelMapper getLovMapper() {
		return lovMapper;
	}
	
			
	
	@Override
	public boolean  batchDeleteLov(List<LovVo> lovList, UserVo userVo,
			ResponseData<LovVo> responseData) throws ServiceException {
		
		if(lovList==null||lovList.isEmpty()){
			
			responseData.add("lov_lov_delete_value");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);	
			return false;
		}
			for (LovVo lovVo : lovList) {
				if(lovVo.getId()==null){
					continue;
				}
					try {
						lovMapper.deleteByPrimaryKey(lovVo.getId());
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
						throw new ServiceException(e.getMessage(),e);
					}
					
				
			}
		responseData.add("lov_lov_delete_value");
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		return true;		
		
	}



	@Override
	public boolean addValue(LovVo lov, UserVo userVo,
			ResponseData<LovVo> responseData, BigDecimal lovId)
			throws ServiceException {
		if(lov==null){
			responseData.add("lov_lov_invalid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return false;
		}
		if(validateLov(lov)){
			responseData.add("lov_lov_invalid");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return false;
		}
		
		if(validateLovDataValid(lov, lovId, responseData)){
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return false;
		}
		
		if(!checkExistLov(lov,responseData)){
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return false;
		}
		
		Map map=new HashMap();
		map.put("lovId", lovId);
		Long i=lovMapper.getMaxsequnence(map);
		
			//add
			lov.setCreatedDate(new Date());
			lov.setCreatedBy(userVo.getUserName());
			lov.setLastUpdatedBy(userVo.getUserName());
			lov.setLastUpdatedDate(new Date());
			lov.setLovId(lovId);
			lov.setSequence(Long.valueOf(i+1));
			addLov(lov);
			
			responseData.add("lov_lov_add_new_value");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			return true;			
	}


	@Override
	public List<LovVo> getLovListByLovType(LovType lovType) {
        Map map=new HashMap();
        map.put("lovId", lovType.getLovId());
		List<LovModel> lovModeles=this.lovMapper.getLovsByLovId(map);
		return getLovVoList(lovModeles);
	}	
	
	@Override
	public List<LovVo> getCatBySupplierId(String supplierId) {
		Map map=new HashMap();
        map.put("supplierId", supplierId);
		List<LovModel> lovModeles=this.lovMapper.getCatBySupplierId(map);
		return getLovVoList(lovModeles);
		
	}
	
	private List<LovVo>  getLovVoList(List<LovModel> lovModeles){
		List<LovVo> lovList=new ArrayList<>();
		if(!lovModeles.isEmpty()){
			for (LovModel lovModel : lovModeles) {
				if(lovModel!=null){
					LovVo lov=new LovVo();
					lov.setId(lovModel.getId());
					lov.setLovDesc(lovModel.getLovDesc());
					lov.setLovValue(lovModel.getLovValue());
					lov.setLovId(lovModel.getLovId());
					lovList.add(lov);
				}
			}
		}
		return lovList;
	}
	
		
	@Override
	public boolean updateLov(List<LovVo> lovList,UserVo userVo,ResponseData<LovVo> responseData,BigDecimal lovId) throws ServiceException {
		if(!validaLovList(lovList,responseData,lovId)){
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			return false;
		}
		
		if(lovList!=null&&!lovList.isEmpty()){
			for (int i = 0; i < lovList.size(); i++) {
				LovVo lov=lovList.get(i);
				if(!checkExistLov(lov,responseData)){
					responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
					return false;
				}
				if(lov.getId()!=null){
					//update
					lov.setLastUpdatedBy(userVo.getUserName());
					lov.setLastUpdatedDate(new Date());
					lov.setSequence(Long.valueOf(i));
					updateLov(lov);
					
				}else{
					//add
					lov.setCreatedDate(new Date());
					lov.setCreatedBy(userVo.getUserName());
					lov.setLastUpdatedBy(userVo.getUserName());
					lov.setLastUpdatedDate(new Date());
					lov.setLovId(lovId);
					lov.setSequence(Long.valueOf(i));
					addLov(lov);
					
				}
			}
		}
		
		responseData.add("lov_lov_update_success");
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		return true;
	}
	
	private boolean validaLovList(List<LovVo> lovList,ResponseData<LovVo> responseData,BigDecimal lovId){
		if(lovList!=null&&!lovList.isEmpty()){
			for (LovVo lovVo : lovList) {
				if(validateLov(lovVo)){
					responseData.add("lov_lov_invalid");
					return false;
				}
				
				if(validateLovDataValid(lovVo,lovId,responseData)){
					return false;
				}
	
				
			}
			
		}
		
		return true;
		
	}
	
//	Minimum order quantity
//	Daily inventory
//	Minimum deliver date
//	Maximum deliver date
	
	private boolean validateLovDataValid(LovVo lovVo,BigDecimal lovId,ResponseData<LovVo> responseData){
		boolean result=false;
			if(validNumber(lovId.toString())&&!StringUtils.isNumeric(lovVo.getLovValue())){
				responseData.add("lov_lov_value_number_invalid");
				result=true;
			}
			
			if (validMaxNumber(lovId.toString())) {
				if(StringUtils.isNumeric(lovVo.getLovValue())){
				//long value =  Long.parseLong(lovVo.getLovValue());
				if (lovVo.getLovValue().length()>3  || Long.parseLong(lovVo.getLovValue()) > 999) {
					responseData
							.add("lov_lov_value_number_too_large_invalid");
					result=true;
				}
				}else{
					responseData.add("lov_lov_value_number_invalid");
					result=true;
				}
			}
		
		return result;
		
	}
	
	private boolean validNumber(String lovId){
		boolean result=false;
		if (LovType.ESTORE_CATEGORY.getLovId().equals(lovId)) {
			result = true;
		}
//		if (LovType.MinOrderQty.getLovId().equals(lovId)) {
//			result = true;
//		}
//		if (LovType.DailyInventory.getLovId().equals(lovId)) {
//			result = true;
//		}
//		if (LovType.MinDeliverDate.getLovId().equals(lovId)) {
//			result = true;
//		}
//		if (LovType.MaxDeliverDate.getLovId().equals(lovId)) {
//			result = true;
//		}
		
		return result;

	}
	
	
	private boolean validMaxNumber(String lovId){
		boolean result=false;
		if (LovType.MIN_ORDER_QTY.getLovId().equals(lovId)) {
			result = true;
		}
		if (LovType.DAILY_INVENTORY.getLovId().equals(lovId)) {
			result = true;
		}
		if (LovType.MIN_DELIVER_DATE.getLovId().equals(lovId)) {
			result = true;
		}
		if (LovType.MAX_DELIVER_DATE.getLovId().equals(lovId)) {
			result = true;
		}
		
		return result;

	}
	
	private boolean validateLov(LovVo lov){
		if(StringUtils.isEmpty(lov.getLovValue())||StringUtils.isEmpty(lov.getLovDesc())){
			return true;
		}
		return false;
		
	}
	
	private boolean checkExistLov(LovVo lovVo,ResponseData<LovVo> responseData){
		
		Map map =new HashMap();
		map.put("lovId", lovVo.getLovId());
		map.put("lovValue", lovVo.getLovValue());
		LovModel lovModel=lovMapper.checkLovByCode(map);
		if(lovModel!=null&&(lovVo.getId()!=null&&lovModel.getId().longValue()!=lovVo.getId().longValue()||lovVo.getId()==null)){
			responseData.add("lov_lov_exist");
			String[] value=new String[1];
			value[0]=lovVo.getLovValue();
			responseData.putMessagesParamArray("lov_lov_exist", value);
			return false;
		}
		return true;
	}
	
	private void updateLov(LovVo lov) throws ServiceException{

		LovModel lovModel=lovMapper.selectByPrimaryKey(lov.getId());
		if(null!=lovModel){
			try {
				lovModel.setLovValue(lov.getLovValue());
				lovModel.setLovDesc(lov.getLovDesc());
				lovModel.setLastUpdatedBy(lov.getLastUpdatedBy());
				lovModel.setLastUpdatedDate(new Date());
				lovModel.setSequence(lov.getSequence());
				lovMapper.updateByPrimaryKeySelective(lovModel);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage(),e);
			}
		}
		
	}
	
	private void addLov(LovVo lov) throws ServiceException{
		LovModel lovModel=new LovModel();
			try {
				lovModel.setLovId(lov.getLovId());
				lovModel.setLovValue(lov.getLovValue());
				lovModel.setLovDesc(lov.getLovDesc());
				lovModel.setLastUpdatedBy(lov.getLastUpdatedBy());
				lovModel.setLastUpdatedDate(new Date());
				lovModel.setCreatedDate(new Date());
				lovModel.setCreatedBy(lov.getCreatedBy());
				lovModel.setSequence(lov.getSequence());
				lovMapper.insertSelective(lovModel);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage(),e);
			}
		
	}





	@Autowired
	public void setLovMapper(LovModelMapper lovMapper) {
		this.lovMapper = lovMapper;
	}
    @Override
	public List<LovModel> getLovsByLovId(LovType lovType) {

        if (lovType == null) {
            return Collections.emptyList();
        }
        Map map=new HashMap();
        map.put("lovId", lovType.getLovId());
        List<LovModel> list = lovMapper.getLovsByLovId(map);

        return list;
	}


    @Override
	public List<LovVo> getCategoryBySupplierId(String supplierId,String lovId) {
		List<LovVo> lovList=new ArrayList<>();
		Map map=new HashMap();
		map.put("supplierId", supplierId);
		map.put("lovId", lovId);
		List<LovModel> lovModes= lovMapper.getCategoryBySupplierId(map);
		for (LovModel lovModel : lovModes) {
			LovVo lov=new LovVo();
			lov.setLovValue(lovModel.getLovValue());
			lov.setLovDesc(lovModel.getLovDesc());
			lov.setId(lovModel.getId());
			lovList.add(lov);
		}
		 return lovList;
	}



	@Override
	public LovModel getLovModelByLovIdValue(String lovId, String lovValue) {
		Map map=new HashMap();
		map.put("lovId", lovId);
		map.put("lovValue", lovValue);
		return lovMapper.getLovModelByLovIdValue(map);
	}
	
	@Override
	public LovModel getLovModelByLovIdDesc(String lovId, String lovDesc) {
		Map map=new HashMap();
		map.put("lovId", lovId);
		map.put("lovDesc", lovDesc);
		return lovMapper.getLovModelByLovIdDesc(map);
	}



	@Override
	public LovVo getLovById(String lovId,String lovValue) {
		Map map=new HashMap();
		map.put("lovId", lovId);
		map.put("lovValue", lovValue);
		LovModel lovModel=lovMapper.getLovModelByLovIdValue(map);
		if(lovModel==null)
			return null;
		
		LovVo lov=new LovVo();
		lov.setId(lovModel.getId());
		lov.setLovDesc(lovModel.getLovDesc());
		lov.setLovValue(lovModel.getLovValue());
		lov.setLovId(lovModel.getLovId());
		lov.setTicked(true);
		return lov;
	}

}
