package com.mall.b2bp.vos.order;

import java.util.List;

/**
 * Created by USER on 2016/3/21.
 */
public class OrderUpdateParameterVo {


    private String waitForUpdateStatus;
    private String trackId;
    private List<OrderVo> orderVoList;


    
    public String getWaitForUpdateStatus() {
        return waitForUpdateStatus;
    }

    public void setWaitForUpdateStatus(String waitForUpdateStatus) {
        this.waitForUpdateStatus = waitForUpdateStatus;
    }

    public List<OrderVo> getOrderVoList() {
        return orderVoList;
    }

    public void setOrderVoList(List<OrderVo> orderVoList) {
        this.orderVoList = orderVoList;
    }

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
}
