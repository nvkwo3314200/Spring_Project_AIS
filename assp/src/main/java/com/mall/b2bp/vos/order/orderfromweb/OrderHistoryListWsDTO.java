package com.mall.b2bp.vos.order.orderfromweb;

import java.util.List;

public  class OrderHistoryListWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>OrderHistoryListWsDTO.orders</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private List<OrderHistoryWsDTO> orders;

	/** <i>Generated property</i> for <code>OrderHistoryListWsDTO.sorts</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private List<SortWsDTO> sorts;

	/** <i>Generated property</i> for <code>OrderHistoryListWsDTO.pagination</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private PaginationWsDTO pagination;
	
	public OrderHistoryListWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setOrders(final List<OrderHistoryWsDTO> orders)
	{
		this.orders = orders;
	}
	
	
	
	public List<OrderHistoryWsDTO> getOrders() 
	{
		return orders;
	}
	
	
	
	public void setSorts(final List<SortWsDTO> sorts)
	{
		this.sorts = sorts;
	}
	
	
	
	public List<SortWsDTO> getSorts() 
	{
		return sorts;
	}
	
	
	
	public void setPagination(final PaginationWsDTO pagination)
	{
		this.pagination = pagination;
	}
	
	
	
	public PaginationWsDTO getPagination() 
	{
		return pagination;
	}
	


}