package com.mall.b2bp.vos.order.orderfromweb;

import java.util.Date;
import java.util.List;

public  class OrderEntryWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>OrderEntryWsDTO.entryNumber</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Integer entryNumber;

	/** <i>Generated property</i> for <code>OrderEntryWsDTO.quantity</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long quantity;

	private Long returnQty;

	/** <i>Generated property</i> for <code>OrderEntryWsDTO.basePrice</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private PriceWsDTO basePrice;

	/** <i>Generated property</i> for <code>OrderEntryWsDTO.totalPrice</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private PriceWsDTO totalPrice;

	/** <i>Generated property</i> for <code>OrderEntryWsDTO.product</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private ProductWsDTO product;

	/** <i>Generated property</i> for <code>OrderEntryWsDTO.updateable</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Boolean updateable;

	/** <i>Generated property</i> for <code>OrderEntryWsDTO.deliveryMode</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private DeliveryModeWsDTO deliveryMode;

//	/** <i>Generated property</i> for <code>OrderEntryWsDTO.deliveryPointOfService</code> property defined at extension <code>commercewebservicescommons</code>. */
//		
//	private PointOfServiceWsDTO deliveryPointOfService;
	
	private List<OrderEntrySerialNoWsDTO> serialNos;
	
	private String status ;
	
	private Date pickedDate;
	
	private Date shippedDate;
	
	private Date deliveredDate;
	/**
	 * @return the pickedDate
	 */
	public Date getPickedDate() {
		return pickedDate;
	}

	/**
	 * @param pickedDate the pickedDate to set
	 */
	public void setPickedDate(Date pickedDate) {
		this.pickedDate = pickedDate;
	}

	/**
	 * @return the shippedDate
	 */
	public Date getShippedDate() {
		return shippedDate;
	}

	/**
	 * @param shippedDate the shippedDate to set
	 */
	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	/**
	 * @return the deliveredDate
	 */
	public Date getDeliveredDate() {
		return deliveredDate;
	}

	/**
	 * @param deliveredDate the deliveredDate to set
	 */
	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderEntrySerialNoWsDTO> getSerialNos() {
		return serialNos;
	}
	
	public void setSerialNos(List<OrderEntrySerialNoWsDTO> serialNos) {
		this.serialNos = serialNos;
	}



	public OrderEntryWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setEntryNumber(final Integer entryNumber)
	{
		this.entryNumber = entryNumber;
	}
	
	
	
	public Integer getEntryNumber() 
	{
		return entryNumber;
	}
	
	
	
	public void setQuantity(final Long quantity)
	{
		this.quantity = quantity;
	}
	
	
	
	public Long getQuantity() 
	{
		return quantity;
	}

	public Long getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Long returnQty) {
		this.returnQty = returnQty;
	}

	
	public void setBasePrice(final PriceWsDTO basePrice)
	{
		this.basePrice = basePrice;
	}
	
	
	
	public PriceWsDTO getBasePrice() 
	{
		return basePrice;
	}
	
	
	
	public void setTotalPrice(final PriceWsDTO totalPrice)
	{
		this.totalPrice = totalPrice;
	}
	
	
	
	public PriceWsDTO getTotalPrice() 
	{
		return totalPrice;
	}
	
	
	
	public void setProduct(final ProductWsDTO product)
	{
		this.product = product;
	}
	
	
	
	public ProductWsDTO getProduct() 
	{
		return product;
	}
	
	
	
	public void setUpdateable(final Boolean updateable)
	{
		this.updateable = updateable;
	}
	
	
	
	public Boolean getUpdateable() 
	{
		return updateable;
	}
	
	
	
	public void setDeliveryMode(final DeliveryModeWsDTO deliveryMode)
	{
		this.deliveryMode = deliveryMode;
	}
	
	
	
	public DeliveryModeWsDTO getDeliveryMode() 
	{
		return deliveryMode;
	}
	
//	
//	
//	public void setDeliveryPointOfService(final PointOfServiceWsDTO deliveryPointOfService)
//	{
//		this.deliveryPointOfService = deliveryPointOfService;
//	}
//	
//	
//	
//	public PointOfServiceWsDTO getDeliveryPointOfService() 
//	{
//		return deliveryPointOfService;
//	}
	


}