package com.mall.b2bp.vos.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.models.lov.LovModel;
import com.mall.b2bp.vos.BaseVo;

public class UserVo extends BaseVo {
    private BigDecimal id;

    private String userId;

    private String userName;

    private String userRole;

    private String supplierId;

    private String activate;

    private Date deactivateDate;
    
    private String password;
    
    private String token;
    
    private String email;
    
    private String contactNo;
    
    private String supplierName;
    
    private String[] userRoleName;
    
    private String udaId;
    
    private String brandId;
    
    private List<LovModel> lovList;
    
    private List<BrandModel> brandList;
    
    private String[] brandSelect;
    
    private String[] categorySelect;
    
    private String[] deptClassSelect;
    
    private Date lastLoginDate;
    private String sessionLang;
    
    private BigDecimal loginFailTimes;
    
    
    
    public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}



	public String[] getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String[] userRoleName) {
		this.userRoleName = userRoleName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate == null ? null : activate.trim();
    }

   
    public Date getDeactivateDate() {
		return deactivateDate;
	}

	public void setDeactivateDate(Date deactivateDate) {
		this.deactivateDate = deactivateDate;
	}

	
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	

	public List<BrandModel> getBranList() {
		return brandList;
	}

	public void setBranList(List<BrandModel> brandList) {
		this.brandList = brandList;
	}

	public String getUdaId() {
		return udaId;
	}

	public void setUdaId(String udaId) {
		this.udaId = udaId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public List<LovModel> getLovList() {
		return lovList;
	}

	public void setLovList(List<LovModel> lovList) {
		this.lovList = lovList;
	}

	public String[] getBrandSelect() {
		return brandSelect;
	}

	public void setBrandSelect(String[] brandSelect) {
		this.brandSelect = brandSelect;
	}

	public String[] getCategorySelect() {
		return categorySelect;
	}

	public void setCategorySelect(String[] categorySelect) {
		this.categorySelect = categorySelect;
	}

	public String[] getDeptClassSelect() {
		return deptClassSelect;
	}

	public void setDeptClassSelect(String[] deptClassSelect) {
		this.deptClassSelect = deptClassSelect;
	}

	public String getSessionLang() {
		return sessionLang;
	}

	public void setSessionLang(String sessionLang) {
		this.sessionLang = sessionLang;
	}

	public BigDecimal getLoginFailTimes() {
		return loginFailTimes;
	}

	public void setLoginFailTimes(BigDecimal loginFailTimes) {
		this.loginFailTimes = loginFailTimes;
	}


    
}
