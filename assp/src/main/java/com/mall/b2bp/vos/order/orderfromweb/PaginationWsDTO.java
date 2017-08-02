package com.mall.b2bp.vos.order.orderfromweb;

/**
 * POJO representation of search results pagination.
 */
public  class PaginationWsDTO extends PageableWsDTO 
{


	/** The total number of pages. This is the number of pages, each of pageSize, required to display
				the totalResults.
			<br/><br/><i>Generated property</i> for <code>PaginationWsDTO.totalPages</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Integer totalPages;

	/** The total number of matched results across all pages.<br/><br/><i>Generated property</i> for <code>PaginationWsDTO.totalResults</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long totalResults;
	
	public PaginationWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setTotalPages(final Integer totalPages)
	{
		this.totalPages = totalPages;
	}
	
	
	
	public Integer getTotalPages() 
	{
		return totalPages;
	}
	
	
	
	public void setTotalResults(final Long totalResults)
	{
		this.totalResults = totalResults;
	}
	
	
	
	public Long getTotalResults() 
	{
		return totalResults;
	}
	


}