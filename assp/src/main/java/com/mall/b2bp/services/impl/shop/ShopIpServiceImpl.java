package com.mall.b2bp.services.impl.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.shop.ShopIpModelMapper;
import com.mall.b2bp.models.shop.ShopIpModel;
import com.mall.b2bp.populators.shop.ShopIpPopulator;
import com.mall.b2bp.services.shop.ShopIpService;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.vos.shop.ShopIpViewVo;

@Service("shopIpService")
public class ShopIpServiceImpl implements ShopIpService {
	private ShopIpModelMapper shopIpModelMapper;
	
	@Resource(name="sessionService1")
	SessionService sessionService;
	
	public ShopIpModelMapper getShopIpModelMapper() {
		return shopIpModelMapper;
	}
	
	@Resource(name="shopIpModelMapper")
	public void setShopIpModelMapper(ShopIpModelMapper shopIpModelMapper) {
		this.shopIpModelMapper = shopIpModelMapper;
	}
	
	@Override
	public int delete(String ids) {
		if (StringUtils.isNotEmpty(ids)) {
			String[] pStrings = StringUtil.getAllProductId(ids);
			if (pStrings != null && pStrings.length > 0) {
				for (String str : pStrings) {
					if (StringUtils.isNotEmpty(str)) {
						BigDecimal id = new BigDecimal(str);
						
						ShopIpModel shopIpModel = shopIpModelMapper.selectByPrimaryKey(id);
						if(shopIpModel==null)
							continue;
//							if (StringUtils.isNotEmpty(usModel.getSupplierId())) {
//								supplierBrandModelMapper.deleteBySupplierId(usModel.getSupplierId());
//								supplierCategoryModelMapper.deleteBySupplierId(usModel.getSupplierId());
//								supplierSubModelMapper.deleteBySupplierId(usModel.getSupplierId());
//							}
						shopIpModelMapper.deleteByPrimaryKey(id);
						//delete reference table
					}
				}
			}
		}
		return 0;
	}

	@Override
	public int save(ShopIpViewVo record) {
		ShopIpModel shopIpModel = new ShopIpPopulator().converVoToModel(record, sessionService);
		if(record.getId() == null) {
			shopIpModelMapper.insertSelective(shopIpModel);
		} else {
			shopIpModelMapper.updateByPrimaryKey(shopIpModel);
		}
		return 0;
	}

	@Override
	public ShopIpModel selectByPrimaryKey(BigDecimal id) {
		return shopIpModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ShopIpViewVo> selectListByShopIpViewVo(ShopIpViewVo record) {
		List<ShopIpModel> shopIpList = null;
		List<ShopIpViewVo> voList = new ArrayList<ShopIpViewVo>();
		ShopIpPopulator populator = new ShopIpPopulator();
		shopIpList = shopIpModelMapper.selectListByShopIpViewVo(record);
		for(ShopIpModel model : shopIpList) {
			voList.add(populator.converModelToVo(model));
		}
		return voList;
	} 

}
