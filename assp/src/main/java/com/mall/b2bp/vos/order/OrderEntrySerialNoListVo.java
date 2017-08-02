package com.mall.b2bp.vos.order;

import java.util.List;

/**
 *  TODO
 *  Created on 2016年8月19日.
 */
public class OrderEntrySerialNoListVo {

	private String orderId;
	private String entryId;
	private List<OrderEntrySerialNoVo> entrySerialNos;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public List<OrderEntrySerialNoVo> getEntrySerialNos() {
		return entrySerialNos;
	}

	public void setEntrySerialNos(List<OrderEntrySerialNoVo> entrySerialNos) {
		this.entrySerialNos = entrySerialNos;
	}
	
}
