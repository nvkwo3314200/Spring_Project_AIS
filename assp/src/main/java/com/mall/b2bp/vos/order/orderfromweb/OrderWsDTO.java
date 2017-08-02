package com.mall.b2bp.vos.order.orderfromweb;

import java.util.Date;
import java.util.List;

public  class OrderWsDTO extends AbstractOrderWsDTO 
{


	/** <i>Generated property</i> for <code>OrderWsDTO.created</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Date created;

	/** <i>Generated property</i> for <code>OrderWsDTO.status</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String status;

	/** <i>Generated property</i> for <code>OrderWsDTO.statusDisplay</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String statusDisplay;

	/** <i>Generated property</i> for <code>OrderWsDTO.guestCustomer</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Boolean guestCustomer;

	/** <i>Generated property</i> for <code>OrderWsDTO.consignments</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private List<ConsignmentWsDTO> consignments;

	/** <i>Generated property</i> for <code>OrderWsDTO.deliveryStatus</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String deliveryStatus;

	/** <i>Generated property</i> for <code>OrderWsDTO.deliveryStatusDisplay</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String deliveryStatusDisplay;

	/** <i>Generated property</i> for <code>OrderWsDTO.unconsignedEntries</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private List<OrderEntryWsDTO> unconsignedEntries;
	
	private String trackId;
	
	private String deliveryFlag;
	
	
	public OrderWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setCreated(final Date created)
	{
		this.created = created;
	}
	
	
	
	public Date getCreated() 
	{
		return created;
	}
	
	
	
	public void setStatus(final String status)
	{
		this.status = status;
	}
	
	
	
	public String getStatus() 
	{
		return status;
	}
	
	
	
	public void setStatusDisplay(final String statusDisplay)
	{
		this.statusDisplay = statusDisplay;
	}
	
	
	
	public String getStatusDisplay() 
	{
		return statusDisplay;
	}
	
	
	
	public void setGuestCustomer(final Boolean guestCustomer)
	{
		this.guestCustomer = guestCustomer;
	}
	
	
	
	public Boolean getGuestCustomer() 
	{
		return guestCustomer;
	}
	
	
	
	public void setConsignments(final List<ConsignmentWsDTO> consignments)
	{
		this.consignments = consignments;
	}
	
	
	
	public List<ConsignmentWsDTO> getConsignments() 
	{
		return consignments;
	}
	
	
	
	public void setDeliveryStatus(final String deliveryStatus)
	{
		this.deliveryStatus = deliveryStatus;
	}
	
	
	
	public String getDeliveryStatus() 
	{
		return deliveryStatus;
	}
	
	
	
	public void setDeliveryStatusDisplay(final String deliveryStatusDisplay)
	{
		this.deliveryStatusDisplay = deliveryStatusDisplay;
	}
	
	
	
	public String getDeliveryStatusDisplay() 
	{
		return deliveryStatusDisplay;
	}
	
	
	
	public void setUnconsignedEntries(final List<OrderEntryWsDTO> unconsignedEntries)
	{
		this.unconsignedEntries = unconsignedEntries;
	}
	
	
	
	public List<OrderEntryWsDTO> getUnconsignedEntries() 
	{
		return unconsignedEntries;
	}



	public String getTrackId() {
		return trackId;
	}



	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}



	public String getDeliveryFlag() {
		return deliveryFlag;
	}



	public void setDeliveryFlag(String deliveryFlag) {
		this.deliveryFlag = deliveryFlag;
	}
	


}