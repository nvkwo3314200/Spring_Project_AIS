package com.mall.b2bp.vos.order.orderfromweb;

/**
 * POJO representation of search query pagination.
 */
public  class PageableWsDTO  implements java.io.Serializable 
{


	/** The number of results per page. A page may have less results if there are less than a full page
				of results, only on the last page in the results.
			<br/><br/><i>Generated property</i> for <code>PageableWsDTO.pageSize</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Integer pageSize;

	/** The current page number. The first page is number zero (0), the second page is number one (1),
				and so on.
			<br/><br/><i>Generated property</i> for <code>PageableWsDTO.currentPage</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Integer currentPage;

	/** The selected sort code.<br/><br/><i>Generated property</i> for <code>PageableWsDTO.sort</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String sort;
	
	public PageableWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setPageSize(final Integer pageSize)
	{
		this.pageSize = pageSize;
	}
	
	
	
	public Integer getPageSize() 
	{
		return pageSize;
	}
	
	
	
	public void setCurrentPage(final Integer currentPage)
	{
		this.currentPage = currentPage;
	}
	
	
	
	public Integer getCurrentPage() 
	{
		return currentPage;
	}
	
	
	
	public void setSort(final String sort)
	{
		this.sort = sort;
	}
	
	
	
	public String getSort() 
	{
		return sort;
	}
	


}