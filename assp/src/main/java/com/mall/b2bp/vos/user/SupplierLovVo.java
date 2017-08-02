package com.mall.b2bp.vos.user;
import com.mall.b2bp.vos.BaseVo;

public class SupplierLovVo extends BaseVo {

	public String getLovCode() {
		return lovCode;
	}
	public void setLovCode(String lovCode) {
		this.lovCode = lovCode;
	}
	public String getLovValue() {
		return lovValue;
	}
	public void setLovValue(String lovValue) {
		this.lovValue = lovValue;
	}
	public boolean isTicked() {
		return ticked;
	}
	public void setTicked(boolean ticked) {
		this.ticked = ticked;
	}
	private String lovCode;
	private String lovValue;
	private boolean ticked;
	


	
}
