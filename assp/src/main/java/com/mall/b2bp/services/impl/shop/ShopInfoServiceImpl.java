package com.mall.b2bp.services.impl.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.shop.ShopInfoModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.shop.ShopInfoModel;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.populators.shop.ShopInfoPopulator;
import com.mall.b2bp.services.shop.ShopInfoService;
import com.mall.b2bp.services.shop.ShopIpService;
import com.mall.b2bp.services.shop.ShopSynService;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductImagesVo;
import com.mall.b2bp.vos.shop.CategoryListWsDTO;
import com.mall.b2bp.vos.shop.CategoryWsDTO;
import com.mall.b2bp.vos.shop.ShopInfoViewVo;
import com.mall.b2bp.vos.shop.ShopIpViewVo;
import com.mall.b2bp.vos.shop.ShopSynVo;

@Service("shopInfoService")
public  class ShopInfoServiceImpl implements ShopInfoService{
	private static final Logger LOG = LoggerFactory.getLogger(ShopInfoServiceImpl.class);
	
	private ShopInfoModelMapper shopInfoModelMapper;
	
	@Resource(name = "sessionService1")
	SessionService sessionService;
	
	@Resource
	ShopSynService shopSynService;
	
	@Resource
	ShopIpService shopIpService;
	
	public ShopInfoModelMapper getShopInfoModelMapper() {
		return shopInfoModelMapper;
	}
	
	@Resource(name="shopInfoModelMapper")
	public void setShopInfoModelMapper(ShopInfoModelMapper shopInfoModelMapper) {
		this.shopInfoModelMapper = shopInfoModelMapper;
	}

	@Override
	public void saveShopInfo(ShopInfoViewVo shopInfoViewVo, ResponseData<ShopInfoViewVo> responseData)
			throws ServiceException {
		ShopInfoModel shopInfoModel = new ShopInfoPopulator().converVoToModel(shopInfoViewVo, sessionService);
		shopInfoViewVo.setSaveSuccess(false);
		
		if(shopInfoModel.getMallId() == null) {
			responseData.setReturnData(shopInfoViewVo);
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			responseData.add("no_mall_account_should_not_open_shop");
			return ; 
		}
		
		/* save local start */
		if(shopInfoModel.getId() == null) {
			shopInfoModelMapper.insertSelective(shopInfoModel);
		} else {
			shopInfoModelMapper.updateByPrimaryKeySelective(shopInfoModel);
		}
		responseData.setReturnData(shopInfoViewVo);
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		responseData.add("save_success");
		shopInfoViewVo.setSaveSuccess(true);
		/* save local end */
		
		/**
		 * sysn to 
		 */
		ShopSynVo vo = convertModeltoShopSynVo(shopInfoModel);
		boolean flag = false;
			try {
				flag = shopSynService.uppdateShop(vo);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				//e.printStackTrace();
			}
		if(!flag) {
			responseData.add("syn_hybris_fail");
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
			shopInfoViewVo.setSyncSuccess(false);
		} else {
			responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
			responseData.add("syn_hybris_success");
			shopInfoViewVo.setSyncSuccess(true);
		}
	}

	@Override
	public int deleteByPrimaryKey(String ids, ResponseData<ShopInfoViewVo> responseData) throws ServiceException {
		boolean flag = false;
		if (StringUtils.isNotEmpty(ids)) {
			String[] pStrings = StringUtil.getAllProductId(ids);
			if (pStrings != null && pStrings.length > 0) {
				for (String str : pStrings) {
					if (StringUtils.isNotEmpty(str)) {
						BigDecimal id = new BigDecimal(str);
						
						ShopInfoModel shopModel = shopInfoModelMapper.selectByPrimaryKey(id);
							if(shopModel==null)
								continue;
//							if (StringUtils.isNotEmpty(usModel.getSupplierId())) {
//								supplierBrandModelMapper.deleteBySupplierId(usModel.getSupplierId());
//								supplierCategoryModelMapper.deleteBySupplierId(usModel.getSupplierId());
//								supplierSubModelMapper.deleteBySupplierId(usModel.getSupplierId());
//							}
						try {
							shopInfoModelMapper.deleteByPrimaryKey(id);
						} catch (Exception e) {
							flag = true;
							String errText = e.getMessage();
							int index = errText.indexOf("ORA-");
							String oracleErrCode = errText.substring(index, index + 9);
							if("ORA-02292".equals(oracleErrCode)) {
								responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
								responseData.add("child_record_exists");
								}else {
									throw e;
								}
						}
						if(!flag) {
							responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
						}
						//delete reference table
					}
				}
			}
		}
		return 0;
	}

	@Override
	public ShopInfoModel selectByPrimaryKey(BigDecimal id) throws ServiceException {
		return shopInfoModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ShopInfoModel> selectShopList(String shopId,String mallId, String shopCode, String shopName, String respPerson, String contactPerson,
			String contactEmail, String websiteName, String telNo) throws ServiceException{
		ShopInfoViewVo vo = new ShopInfoViewVo();
		if(StringUtils.isNotBlank(shopId)) vo.setId(new BigDecimal(shopId));
		if(StringUtils.isNotBlank(mallId)) vo.setMallId(new BigDecimal(mallId));
		if(StringUtils.isNotBlank(shopCode)) vo.setShopCode(shopCode);
		if(StringUtils.isNotBlank(shopName)) vo.setShopName(shopName);
		if(StringUtils.isNotBlank(respPerson)) vo.setRespPerson(respPerson);
		if(StringUtils.isNotBlank(contactPerson)) vo.setContactPerson(contactPerson);
		if(StringUtils.isNotBlank(contactEmail)) vo.setContactEmail(contactEmail);
		if(StringUtils.isNotBlank(websiteName)) vo.setWebsiteName(websiteName);
		if(StringUtils.isNotBlank(telNo)) vo.setTelNo(telNo);
		return shopInfoModelMapper.selectShopList(vo);
	}

	@Override
	public ShopInfoViewVo getShopViewVoById(String id) throws ServiceException{
		ShopInfoViewVo viewVo = new ShopInfoViewVo();
		BigDecimal shopId = new BigDecimal(id);
		ShopInfoModel shopInfoModel = shopInfoModelMapper.selectByPrimaryKey(shopId);
		viewVo = new ShopInfoPopulator().converModelToVo(shopInfoModel);
		return viewVo;
	}

	@Override
	public int updateShopInfo(ShopInfoViewVo shopInfoViewVo, ResponseData<ShopInfoViewVo> responseData)
			throws ServiceException {
		ShopInfoModel shopInfoModel = new ShopInfoPopulator().converVoToModel(shopInfoViewVo, sessionService);
		shopInfoModelMapper.updateByPrimaryKeySelective(shopInfoModel);
		responseData.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
		return 0;
	}

	@Override
	public ShopInfoViewVo selectInterfaceByKey(String id, String path) throws ServiceException {
		ShopInfoModel model = shopInfoModelMapper.selectInterfaceByKey(new BigDecimal(id));
		ShopInfoViewVo viewVo = null;
		if(model != null) viewVo = new ShopInfoPopulator().converModelToVo(model, path);
		return viewVo;
	}
	
	@Override
	/**
	 * return false 表示不存在 
	 */
	public boolean checkExist(ShopInfoViewVo shopInfoViewVo) {
		boolean flag = false;
		BigDecimal id = shopInfoViewVo.getId();
		shopInfoViewVo.setId(null);
		List<ShopInfoModel> modelList = shopInfoModelMapper.selectIdList(shopInfoViewVo);
		if(modelList != null && modelList.size() > 0) {
			if(id == null) {
				flag = true;
			} else if(!id.equals(modelList.get(0).getId())){
				flag = true;
			}
		}
		return flag;
	}

	private ShopSynVo convertModeltoShopSynVo(ShopInfoModel shopInfoModel) {
		ShopSynVo vo = new ShopSynVo();
		ProductImagesVo imageVo = new ProductImagesVo ();
		imageVo.setFilePath(shopInfoModel.getLogoName());
		vo.setCode(shopInfoModel.getShopCode());
		vo.setName(shopInfoModel.getShopName());
		vo.setDescription(shopInfoModel.getShopDesc());
		vo.setLine1(shopInfoModel.getAddress());
		vo.setLine2(shopInfoModel.getAddress2());
		vo.setLine3(shopInfoModel.getAddress3());
		vo.setLine4(shopInfoModel.getAddress4());
		vo.setImageVo(imageVo);
		return vo;
	}

	@Override
	public boolean validateIP(UserInfoModel user) {
		boolean flagAllow = true;
		boolean flagDeny = true;
		String add = user.getIp();
		LOG.info("Client ip:" + add);
		ShopIpViewVo ipVo = new ShopIpViewVo();
		ipVo.setShopId(new BigDecimal(user.getShopId()));
		List<ShopIpViewVo> ipVoList = shopIpService.selectListByShopIpViewVo(ipVo);
		for(ShopIpViewVo item : ipVoList) {
			String ipStart = new StringBuffer().append(item.getIpStart1()).append(".").append(item.getIpStart2()).append(".").
					append(item.getIpStart3()).append(".").append(item.getIpStart4()).toString();
			String ipEnd = new StringBuffer().append(item.getIpEnd1()).append(".").append(item.getIpEnd2()).append(".").
					append(item.getIpEnd3()).append(".").append(item.getIpEnd4()).toString();
			if("E".equalsIgnoreCase(item.getExIncludeInd())) {
				boolean flag = checkInclude(ipStart,ipEnd,add);
				if(flag) flagDeny = false;
			} else if ("I".equalsIgnoreCase(item.getExIncludeInd())) {
				boolean flag = checkInclude(ipStart,ipEnd,add);
				if(!flag) flagAllow = false;
			}
		}
		return flagAllow && flagDeny;
	}
	
	
	private boolean checkInclude (String start, String end, String add) {
		if(compare(start,add) <= 0 && compare(end,add) >=0) return true;
		return false;
	}
	/**
	 * @param o1
	 * @param o2
	 * @return if(o1>o2) return>0 else (o1<o2) return<0
	 */
	private int compare(String o1, String o2) {
		/*format ip*/
         String ip1 = o1.replaceAll("(\\d+)", "00$1").replaceAll("0*(\\d{3})", "$1");
         String ip2 = o2.replaceAll("(\\d+)", "00$1").replaceAll("0*(\\d{3})", "$1");
         return ip1.compareTo(ip2);
	 }

	@Override
	public List<CategoryWsDTO> getCategoryList() {
		CategoryListWsDTO dto = shopSynService.getAllCategoriesForTreeGrid();
		if(dto != null) return dto.getCategories();
		return null;
	}

	@Override
	public List<ShopInfoViewVo> search(ShopInfoViewVo vo) {
		List<ShopInfoModel> list = shopInfoModelMapper.search(vo);
		List<ShopInfoViewVo> voList = new ArrayList<>();
		ShopInfoPopulator pop = new ShopInfoPopulator();
		for(ShopInfoModel model : list) {
			voList.add(pop.converModelToVo(model));
		}
		return voList;
	}

	@Override
	public List<ShopInfoModel> getAllShop() throws ServiceException{
		@SuppressWarnings("unchecked")
		List<ShopInfoModel> list = (List<ShopInfoModel>) sessionService.getAttribute(ConstantUtil.SYSTEM_SHOP_LIST);
		if(list == null) {
			list = shopInfoModelMapper.selectShopList(new ShopInfoViewVo());
			sessionService.setAttribute(ConstantUtil.SYSTEM_SHOP_LIST, list);
			Map<String, ShopInfoModel> map = new HashMap<>();
			for(ShopInfoModel model : list) {
				map.put(model.getId().toString(), model);
			}
			sessionService.setAttribute(ConstantUtil.SYSTEM_SHOP_MAP, map);
		}
		return list;
	}
}
