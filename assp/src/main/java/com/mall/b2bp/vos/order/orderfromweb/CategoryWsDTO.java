package com.mall.b2bp.vos.order.orderfromweb;

public  class CategoryWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>CategoryWsDTO.code</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String code;

	/** <i>Generated property</i> for <code>CategoryWsDTO.url</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String url;

	/** <i>Generated property</i> for <code>CategoryWsDTO.image</code> property defined at extension <code>commercewebservicescommons</code>. */
		
//	private ImageWsDTO image;

	/** <i>Generated property</i> for <code>CategoryWsDTO.name</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private String name;
	
	public CategoryWsDTO()
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
	
	
	
	public void setUrl(final String url)
	{
		this.url = url;
	}
	
	
	
	public String getUrl() 
	{
		return url;
	}
	
	
	
//	public void setImage(final ImageWsDTO image)
//	{
//		this.image = image;
//	}
//	
//	
//	
//	public ImageWsDTO getImage() 
//	{
//		return image;
//	}
	
	
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	
	
	public String getName() 
	{
		return name;
	}
	


}