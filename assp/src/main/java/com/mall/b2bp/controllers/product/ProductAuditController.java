package com.mall.b2bp.controllers.product;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.product.ProductAuditService;
import com.mall.b2bp.vos.product.ProductAuditVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by USER on 2016/4/7.
 */
@Controller("ProductAuditController")
@RequestMapping(value = "/productUpdateHist")
public class ProductAuditController extends BaseConroller {


    private static final Logger LOG = LoggerFactory.getLogger(ProductAuditController.class);

    @Resource(name = "productAuditService")
    private ProductAuditService productAuditService;

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
    @RequestMapping(value = "/viewUpHist", method = {RequestMethod.POST, RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<ProductAuditVo> viewHistory(@RequestParam(value = "productId", required = false) final String productId,
                            @RequestParam(value = "userId", required = false) final String userId,
                            @RequestParam(value = "action", required = false) final String action,
                            @RequestParam(value = "actionTimeFr", required = false) final String actionTimeFr,
                            @RequestParam(value = "actionTimeTo", required = false) final String actionTimeTo) throws SystemException {

        try {
            return  productAuditService.viewHistory(productId, userId, action, actionTimeFr, actionTimeTo);

        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            throw new SystemException(e.getMessage(), e);
        }


    }


}
