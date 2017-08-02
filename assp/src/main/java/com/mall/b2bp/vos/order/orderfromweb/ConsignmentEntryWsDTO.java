package com.mall.b2bp.vos.order.orderfromweb;

public  class ConsignmentEntryWsDTO  implements java.io.Serializable 
{
		
	private OrderEntryWsDTO orderEntry;
		
	private Long quantity;
	

	private Long shippedQuantity;
	

	public ConsignmentEntryWsDTO()
	{
		// default constructor
	}
	
	public void setOrderEntry(final OrderEntryWsDTO orderEntry)
	{
		this.orderEntry = orderEntry;
	}
	
	
	public OrderEntryWsDTO getOrderEntry() 
	{
		return orderEntry;
	}
	
	public void setQuantity(final Long quantity)
	{
		this.quantity = quantity;
	}
	
	public Long getQuantity() 
	{
		return quantity;
	}
	
	public void setShippedQuantity(final Long shippedQuantity)
	{
		this.shippedQuantity = shippedQuantity;
	}
	
	public Long getShippedQuantity() 
	{
		return shippedQuantity;
	}
	


}