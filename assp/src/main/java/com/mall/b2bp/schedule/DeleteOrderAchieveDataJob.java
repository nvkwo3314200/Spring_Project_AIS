package com.mall.b2bp.schedule;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.services.order.OrderService;

public class DeleteOrderAchieveDataJob {

	private static final Logger LOG = LoggerFactory.getLogger(DeleteOrderAchieveDataJob.class);


    @Resource(name = "orderService")
    private OrderService orderService;

	protected void executeInternal() throws ServiceException {
		LOG.info(" DeleteOrderAchieveDataJob start " + new Date());
		orderService.deleteOutOfDateOrderData();
		LOG.info(" DeleteOrderAchieveDataJob end " + new Date());
	}




	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}


}
