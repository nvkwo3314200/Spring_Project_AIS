package com.mall.b2bp.controllers.order;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.order.OrderAuditService;
import com.mall.b2bp.vos.order.OrderAuditVo;

@Controller("OrderAuditController")
@RequestMapping(value = "/orderUpdateHist")
public class OrderAuditController extends BaseConroller {


    private static final Logger LOG = LoggerFactory.getLogger(OrderAuditController.class);

    @Resource(name = "orderAuditService")
    private OrderAuditService orderAuditService;

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
    @RequestMapping(value = "/viewUpHist", method = {RequestMethod.POST, RequestMethod.GET},
            produces = {"application/xml", "application/json"})
    @ResponseBody
    public List<OrderAuditVo> viewHistory(@RequestParam(value = "orderId", required = false) final String orderId,
                            @RequestParam(value = "userId", required = false) final String userId,
                            @RequestParam(value = "action", required = false) final String action,
                            @RequestParam(value = "actionTimeFr", required = false) final String actionTimeFr,
                            @RequestParam(value = "actionTimeTo", required = false) final String actionTimeTo) throws SystemException {

        try {
            return  orderAuditService.viewHistory(orderId, userId, action, actionTimeFr, actionTimeTo);

        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            throw new SystemException(e.getMessage(), e);
        }


    }


}
