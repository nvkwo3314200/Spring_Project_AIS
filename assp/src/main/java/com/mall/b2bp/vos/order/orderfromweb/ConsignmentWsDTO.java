package com.mall.b2bp.vos.order.orderfromweb;

import java.util.Date;
import java.util.List;

public  class ConsignmentWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>ConsignmentWsDTO.code</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String code;

	/** <i>Generated property</i> for <code>ConsignmentWsDTO.trackingID</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String trackingID;

	/** <i>Generated property</i> for <code>ConsignmentWsDTO.status</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String status;

	/** <i>Generated property</i> for <code>ConsignmentWsDTO.statusDate</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Date statusDate;

	/** <i>Generated property</i> for <code>ConsignmentWsDTO.entries</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private List<ConsignmentEntryWsDTO> entries;

	/** <i>Generated property</i> for <code>ConsignmentWsDTO.shippingAddress</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private AddressWsDTO shippingAddress;

	/** <i>Generated property</i> for <code>ConsignmentWsDTO.deliveryPointOfService</code> property defined at extension <code>commercewebservicescommons</code>. */
	
	
	private Date shippingDate;

	private Date pickedDate;
	
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
	 * @return the shippingDate
	 */
	public Date getShippingDate() {
		return shippingDate;
	}


	/**
	 * @param shippingDate the shippingDate to set
	 */
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}



	/**
	 * @return the namedDeliveryDate
	 */
	public Date getNamedDeliveryDate() {
		return namedDeliveryDate;
	}



	/**
	 * @param namedDeliveryDate the namedDeliveryDate to set
	 */
	public void setNamedDeliveryDate(Date namedDeliveryDate) {
		this.namedDeliveryDate = namedDeliveryDate;
	}



	private Date namedDeliveryDate;

		
	public ConsignmentWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setCode(final String code)
	{
		this.code = code;
	}
	
	
	
	public String getCode() 
	{
		return code;
	}
	
	
	
	public void setTrackingID(final String trackingID)
	{
		this.trackingID = trackingID;
	}
	
	
	
	public String getTrackingID() 
	{
		return trackingID;
	}
	
	
	
	public void setStatus(final String status)
	{
		this.status = status;
	}
	
	
	
	public String getStatus() 
	{
		return status;
	}
	
	
	
	public void setStatusDate(final Date statusDate)
	{
		this.statusDate = statusDate;
	}
	
	
	
	public Date getStatusDate() 
	{
		return statusDate;
	}
	
	
	
	public void setEntries(final List<ConsignmentEntryWsDTO> entries)
	{
		this.entries = entries;
	}
	
	
	
	public List<ConsignmentEntryWsDTO> getEntries() 
	{
		return entries;
	}
	
	
	
	public void setShippingAddress(final AddressWsDTO shippingAddress)
	{
		this.shippingAddress = shippingAddress;
	}
	
	
	
	public AddressWsDTO getShippingAddress() 
	{
		return shippingAddress;
	}
	


}