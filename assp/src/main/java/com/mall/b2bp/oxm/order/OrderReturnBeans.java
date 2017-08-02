package com.mall.b2bp.oxm.order;

import java.util.ArrayList;
import java.util.List;

public class OrderReturnBeans {

	
	private List<OrderReturnBean> orderReturnBeanList;

	public OrderReturnBeans(){
		
		orderReturnBeanList=new ArrayList<>();
	}

	public List<OrderReturnBean> getOrderReturnBeanList() {
		return orderReturnBeanList;
	}

	public void setOrderReturnBeanList(List<OrderReturnBean> orderReturnBeanList) {
		this.orderReturnBeanList = orderReturnBeanList;
	}
	
	
	

	
	
}
