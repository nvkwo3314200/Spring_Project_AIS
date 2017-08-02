package com.mall.b2bp.vos.order;



import java.util.Date;

public  class SupplierCollectionData  implements java.io.Serializable 
{

	private Date pickedConfirmDate;
	private Date shippedConfirmDate;
	private String supplier;	
	private String orderSupplierStatus;
	private String order;
	private String flag;
	private String status;
	
	private boolean disableCheckbox;
	
	
	public SupplierCollectionData()
	{
		// default constructor
	}
	
	
	
	
	
	public void setPickedConfirmDate(final Date pickedConfirmDate)
	{
		this.pickedConfirmDate = pickedConfirmDate;
	}
	
	
	
	public Date getPickedConfirmDate() 
	{
		return pickedConfirmDate;
	}
	
	
	
	public void setShippedConfirmDate(final Date shippedConfirmDate)
	{
		this.shippedConfirmDate = shippedConfirmDate;
	}
	
	
	
	public Date getShippedConfirmDate() 
	{
		return shippedConfirmDate;
	}
	
	
	
	public void setSupplier(final String supplier)
	{
		this.supplier = supplier;
	}
	
	
	
	public String getSupplier() 
	{
		return supplier;
	}
	
	
	
	public void setOrderSupplierStatus(final String orderSupplierStatus)
	{
		this.orderSupplierStatus = orderSupplierStatus;
	}
	
	
	
	public String getOrderSupplierStatus() 
	{
		return orderSupplierStatus;
	}
	
	
	
	public void setOrder(final String order)
	{
		this.order = order;
	}
	
	
	
	public String getOrder() 
	{
		return order;
	}





	public String getFlag() {
		return flag;
	}





	public void setFlag(String flag) {
		this.flag = flag;
	}





	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}





	public boolean isDisableCheckbox() {
		return disableCheckbox;
	}





	public void setDisableCheckbox(boolean disableCheckbox) {
		this.disableCheckbox = disableCheckbox;
	}





	


}