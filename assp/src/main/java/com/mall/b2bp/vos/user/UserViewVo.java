package com.mall.b2bp.vos.user;

import java.math.BigDecimal;
import java.util.List;

import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.vos.BaseVo;
import com.mall.b2bp.vos.supplier.SupplierVo;

public class UserViewVo extends BaseVo {

	private BigDecimal id;

	private String userId;

	private String userName;

	private String userRole;

	private String supplierId;

	private String activate;

	private String password;

	private String email;

	private String contactNo;

	private String[] brandSelect;

	private String[] categorySelect;

	private String[] deptClassSelect;

	private String[] brandUnSelect;

	private String[] categoryUnSelect;

	private String[] deptClassUnSelect;

	private SupplierVo supplierVo;

	private String[] unSelect;

	private String[] classSelect;

	private String[] unClassSelect;

	private String[] subClassSelect;

	private String[] unSubClassSelect;

	private boolean isUpdateSupplierId;

	private List<SupplierBrandVo> brandSelectVos;
	private List<SupplierLovVo> lovSelectVos;

	private List<SubClassModel> selectSubClassModels;

	private List<ClassModel> selectClassModels;

	private List<SubClassModel> subClassModels;

	private List<SubClassModel> subClassSelectModel;
	private List<SubClassModel> unSubClassSelectModel;

	private String[] deptSelect;
	private List<DeptModel> deptSelectModel;
	private List<DeptModel> unDeptSelectModel;
	private List<ClassModel> classSelectModel;
	private List<ClassModel> unClassSelectModel;

	private List<BrandModel> brandselectModel;
	private List<BrandModel> brandUnSelectModel;

	public List<DeptModel> getDeptSelectModel() {
		return deptSelectModel;
	}

	public void setDeptSelectModel(List<DeptModel> deptSelectModel) {
		this.deptSelectModel = deptSelectModel;
	}

	public List<DeptModel> getUnDeptSelectModel() {
		return unDeptSelectModel;
	}

	public void setUnDeptSelectModel(List<DeptModel> unDeptSelectModel) {
		this.unDeptSelectModel = unDeptSelectModel;
	}

	public List<ClassModel> getClassSelectModel() {
		return classSelectModel;
	}

	public void setClassSelectModel(List<ClassModel> classSelectModel) {
		this.classSelectModel = classSelectModel;
	}

	public List<ClassModel> getUnClassSelectModel() {
		return unClassSelectModel;
	}

	public void setUnClassSelectModel(List<ClassModel> unClassSelectModel) {
		this.unClassSelectModel = unClassSelectModel;
	}

	public List<BrandModel> getBrandselectModel() {
		return brandselectModel;
	}

	public void setBrandselectModel(List<BrandModel> brandselectModel) {
		this.brandselectModel = brandselectModel;
	}

	public List<BrandModel> getBrandUnSelectModel() {
		return brandUnSelectModel;
	}

	public void setBrandUnSelectModel(List<BrandModel> brandUnSelectModel) {
		this.brandUnSelectModel = brandUnSelectModel;
	}

	public List<SubClassModel> getSubClassSelectModel() {
		return subClassSelectModel;
	}

	public void setSubClassSelectModel(List<SubClassModel> subClassSelectModel) {
		this.subClassSelectModel = subClassSelectModel;
	}

	public List<SubClassModel> getUnSubClassSelectModel() {
		return unSubClassSelectModel;
	}

	public void setUnSubClassSelectModel(List<SubClassModel> unSubClassSelectModel) {
		this.unSubClassSelectModel = unSubClassSelectModel;
	}

	public String[] getDeptSelect() {
		return deptSelect;
	}

	public void setDeptSelect(String[] deptSelect) {
		this.deptSelect = deptSelect;
	}

	public String[] getUnSelect() {
		return unSelect;
	}

	public void setUnSelect(String[] unSelect) {
		this.unSelect = unSelect;
	}

	public String[] getClassSelect() {
		return classSelect;
	}

	public void setClassSelect(String[] classSelect) {
		this.classSelect = classSelect;
	}

	public String[] getUnClassSelect() {
		return unClassSelect;
	}

	public void setUnClassSelect(String[] unClassSelect) {
		this.unClassSelect = unClassSelect;
	}

	public String[] getSubClassSelect() {
		return subClassSelect;
	}

	public void setSubClassSelect(String[] subClassSelect) {
		this.subClassSelect = subClassSelect;
	}

	public String[] getUnSubClassSelect() {
		return unSubClassSelect;
	}

	public void setUnSubClassSelect(String[] unSubClassSelect) {
		this.unSubClassSelect = unSubClassSelect;
	}

	public List<SupplierBrandVo> getBrandUnSelectVos() {
		return brandUnSelectVos;
	}

	public void setBrandUnSelectVos(List<SupplierBrandVo> brandUnSelectVos) {
		this.brandUnSelectVos = brandUnSelectVos;
	}

	public List<SupplierLovVo> getLovUnSelectVos() {
		return lovUnSelectVos;
	}

	public void setLovUnSelectVos(List<SupplierLovVo> lovUnSelectVos) {
		this.lovUnSelectVos = lovUnSelectVos;
	}

	private List<SupplierBrandVo> brandUnSelectVos;
	private List<SupplierLovVo> lovUnSelectVos;

	public List<SupplierBrandVo> getBrandSelectVos() {
		return brandSelectVos;
	}

	public void setBrandSelectVos(List<SupplierBrandVo> brandSelectVos) {
		this.brandSelectVos = brandSelectVos;
	}

	public List<SupplierLovVo> getLovSelectVos() {
		return lovSelectVos;
	}

	public void setLovSelectVos(List<SupplierLovVo> lovSelectVos) {
		this.lovSelectVos = lovSelectVos;
	}

	public boolean isUpdateSupplierId() {
		return isUpdateSupplierId;
	}

	public void setUpdateSupplierId(boolean isUpdateSupplierId) {
		this.isUpdateSupplierId = isUpdateSupplierId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
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
		this.activate = activate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String[] getBrandUnSelect() {
		return brandUnSelect;
	}

	public void setBrandUnSelect(String[] brandUnSelect) {
		this.brandUnSelect = brandUnSelect;
	}

	public String[] getCategoryUnSelect() {
		return categoryUnSelect;
	}

	public void setCategoryUnSelect(String[] categoryUnSelect) {
		this.categoryUnSelect = categoryUnSelect;
	}

	public String[] getDeptClassUnSelect() {
		return deptClassUnSelect;
	}

	public void setDeptClassUnSelect(String[] deptClassUnSelect) {
		this.deptClassUnSelect = deptClassUnSelect;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public List<SubClassModel> getSelectSubClassModels() {
		return selectSubClassModels;
	}

	public void setSelectSubClassModels(List<SubClassModel> selectSubClassModels) {
		this.selectSubClassModels = selectSubClassModels;
	}

	public List<ClassModel> getSelectClassModels() {
		return selectClassModels;
	}

	public void setSelectClassModels(List<ClassModel> selectClassModels) {
		this.selectClassModels = selectClassModels;
	}

	public List<SubClassModel> getSubClassModels() {
		return subClassModels;
	}

	public void setSubClassModels(List<SubClassModel> subClassModels) {
		this.subClassModels = subClassModels;
	}

	public SupplierVo getSupplierVo() {
		return supplierVo;
	}

	public void setSupplierVo(SupplierVo supplierVo) {
		this.supplierVo = supplierVo;
	}

}
