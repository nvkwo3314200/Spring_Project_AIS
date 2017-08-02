package com.mall.b2bp.oxm.order;

import java.util.ArrayList;
import java.util.List;

public class OrderBeans {
private List<OrderBean> orderBeanList;

public OrderBeans(){
	
	orderBeanList=new ArrayList<>();
}

public List<OrderBean> getOrderBeanList() {
	return orderBeanList;
}

public void setOrderBeanList(List<OrderBean> orderBeanList) {
	this.orderBeanList = orderBeanList;
}



}
