package com.mall.b2bp.vos.user;

import java.math.BigDecimal;

import com.mall.b2bp.vos.BaseVo;

public class SysUserViewVo extends BaseVo{
	private BigDecimal id;

    private String userCode;

    private String userName;

    private String password;

    private BigDecimal shopId;
    
    private BigDecimal roleId;

    private String activeInd;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public BigDecimal getShopId() {
        return shopId;
    }

    public void setShopId(BigDecimal shopId) {
        this.shopId = shopId;
    }

    public String getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(String activeInd) {
        this.activeInd = activeInd == null ? null : activeInd.trim();
    }

	public BigDecimal getRoleId() {
		return roleId;
	}

	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}
    
    
}
