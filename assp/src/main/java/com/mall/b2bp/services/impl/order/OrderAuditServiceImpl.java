package com.mall.b2bp.services.impl.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.order.OrderAuditModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderAuditModel;
import com.mall.b2bp.populators.order.OrderAuditPopulator;
import com.mall.b2bp.services.order.OrderAuditService;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderAuditVo;

/**
 * Created by USER on 2016/4/7.
 */

@Service("orderAuditService")
public class OrderAuditServiceImpl implements OrderAuditService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderAuditServiceImpl.class);
    private OrderAuditModelMapper orderAuditModelMapper;

    public OrderAuditModelMapper getOrderAuditModelMapper() {
        return orderAuditModelMapper;
    }

    @Autowired
    public void setOrderAuditModelMapper(OrderAuditModelMapper orderAuditModelMapper) {
        this.orderAuditModelMapper = orderAuditModelMapper;
    }

    @Override
    public List<OrderAuditVo> viewHistory(String orderId, String userId,
                                            String action,
                                            String actionTimeFr,String actionTimeTo
    ) throws ServiceException {


        OrderAuditVo orderAuditVo = new OrderAuditVo();
        orderAuditVo.setOrderId(StringUtils.isNotEmpty(orderId) ?orderId:null);
        orderAuditVo.setUserId(userId);
        orderAuditVo.setAction(action);

        Date from = DateUtils.parseDateStr(actionTimeFr, DateUtils.DATE_FORMAT);
        Date to = DateUtils.parseDateStr(actionTimeTo, DateUtils.DATE_FORMAT);

        orderAuditVo.setActionTimeFrom(from);
        orderAuditVo.setActionTimeTo(to);



        try {
            List<OrderAuditModel> orderAuditModelList = orderAuditModelMapper.viewHistory(orderAuditVo);
            return populator(orderAuditModelList);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private List<OrderAuditVo> populator(List<OrderAuditModel> orderAuditModels) {


        List<OrderAuditVo> list = new ArrayList<>();

        if (CollectionUtils.isEmpty(orderAuditModels)) {
            return list;
        }

        OrderAuditPopulator po = new OrderAuditPopulator();

        for (OrderAuditModel orderAuditModel : orderAuditModels) {
            list.add(po.converModelToVo(orderAuditModel));
        }

        return list;
    }

	@Override
	public int deleteByOrderId(String orderId) {
		return orderAuditModelMapper.deleteByOrderId(orderId);
	}

}