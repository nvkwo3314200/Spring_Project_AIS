package com.mall.b2bp.vos.order.orderfromweb;

/**
 * POJO representing a sort option.
 */
public  class SortWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>SortWsDTO.code</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String code;

	/** <i>Generated property</i> for <code>SortWsDTO.name</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String name;

	/** <i>Generated property</i> for <code>SortWsDTO.selected</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Boolean selected;
	
	public SortWsDTO()
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
	
	
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	
	
	public String getName() 
	{
		return name;
	}
	
	
	
	public void setSelected(final Boolean selected)
	{
		this.selected = selected;
	}
	
	
	
	public Boolean getSelected() 
	{
		return selected;
	}
	


}