package com.mall.b2bp.vos.order;

import java.math.BigDecimal;

/**
 *  TODO
 *  Created on 2016年8月22日.
 */
public class PickQtyVo {
	
	private BigDecimal pickedQty ;

	private BigDecimal entryNumber;

	public BigDecimal getEntryNumber() {
		return entryNumber;
	}

	public void setEntryNumber(BigDecimal entryNumber) {
		this.entryNumber = entryNumber;
	}

	public void setPickedQty(BigDecimal pickedQty) {
		this.pickedQty = pickedQty;
	}

	/**
	 * @return the pickedQty
	 */
	public BigDecimal getPickedQty() {
		return pickedQty;
	}
}
