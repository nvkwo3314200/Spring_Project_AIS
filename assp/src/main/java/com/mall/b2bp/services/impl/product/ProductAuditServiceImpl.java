package com.mall.b2bp.services.impl.product;

import com.mall.b2bp.daos.product.ProductAuditModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.product.ProductAuditModel;
import com.mall.b2bp.populators.product.ProductAuditPopulator;
import com.mall.b2bp.services.product.ProductAuditService;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.product.ProductAuditVo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by USER on 2016/4/7.
 */

@Service("productAuditService")
public class ProductAuditServiceImpl implements ProductAuditService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductAuditServiceImpl.class);
    private ProductAuditModelMapper productAuditModelMapper;

    public ProductAuditModelMapper getProductAuditModelMapper() {
        return productAuditModelMapper;
    }

    @Autowired
    public void setProductAuditModelMapper(ProductAuditModelMapper productAuditModelMapper) {
        this.productAuditModelMapper = productAuditModelMapper;
    }

    @Override
    public List<ProductAuditVo> viewHistory(String productId, String userId,
                                            String action,
                                            String actionTimeFr,String actionTimeTo
    ) throws ServiceException {


        ProductAuditVo productAuditVo = new ProductAuditVo();
        productAuditVo.setProductId(StringUtils.isNotEmpty(productId) ?new BigDecimal(productId):null);
        productAuditVo.setUserId(userId);
        productAuditVo.setAction(action);

        Date from = DateUtils.parseDateStr(actionTimeFr, DateUtils.DATE_FORMAT);
        Date to = DateUtils.parseDateStr(actionTimeTo, DateUtils.DATE_FORMAT);

        productAuditVo.setActionTimeFrom(from);
        productAuditVo.setActionTimeTo(to);



        try {
            List<ProductAuditModel> productAuditModelList = productAuditModelMapper.viewHistory(productAuditVo);
            return populator(productAuditModelList);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private List<ProductAuditVo> populator(List<ProductAuditModel> productAuditModels) {


        List<ProductAuditVo> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(productAuditModels)) {
            return list;
        }

        ProductAuditPopulator po = new ProductAuditPopulator();

        for (ProductAuditModel productAuditModel : productAuditModels) {
            list.add(po.converModelToVo(productAuditModel));
        }

        return list;
    }

	@Override
	public int deleteByProductId(String productId) {
		return productAuditModelMapper.deleteByProductId(productId);
	}

	@Override
	public int deleteBySupplierId(String supplierCode) {
		return productAuditModelMapper.deleteBySupplierId(supplierCode);
	}

}