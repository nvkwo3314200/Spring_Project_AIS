package com.mall.b2bp.vos.order.orderfromweb;

public  class ImageWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>ImageWsDTO.imageType</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private ImageDataType imageType;

	/** <i>Generated property</i> for <code>ImageWsDTO.format</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String format;

	/** <i>Generated property</i> for <code>ImageWsDTO.url</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String url;

	/** <i>Generated property</i> for <code>ImageWsDTO.altText</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String altText;

	/** <i>Generated property</i> for <code>ImageWsDTO.galleryIndex</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Integer galleryIndex;
	
	public ImageWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setImageType(final ImageDataType imageType)
	{
		this.imageType = imageType;
	}
	
	
	
	public ImageDataType getImageType() 
	{
		return imageType;
	}
	
	
	
	public void setFormat(final String format)
	{
		this.format = format;
	}
	
	
	
	public String getFormat() 
	{
		return format;
	}
	
	
	
	public void setUrl(final String url)
	{
		this.url = url;
	}
	
	
	
	public String getUrl() 
	{
		return url;
	}
	
	
	
	public void setAltText(final String altText)
	{
		this.altText = altText;
	}
	
	
	
	public String getAltText() 
	{
		return altText;
	}
	
	
	
	public void setGalleryIndex(final Integer galleryIndex)
	{
		this.galleryIndex = galleryIndex;
	}
	
	
	
	public Integer getGalleryIndex() 
	{
		return galleryIndex;
	}
	


}