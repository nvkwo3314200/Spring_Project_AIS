/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 2017-3-17 10:37:36
 * ----------------------------------------------------------------
 *
 * [y] hybris Platform
 * 
 * Copyright (c) 2000-2016 SAP SE
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of SAP 
 * Hybris ("Confidential Information"). You shall not disclose such 
 * Confidential Information and shall use it only in accordance with the 
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.mall.b2bp.vos.shop;



public  class CategoryWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>CategoryWsDTO.code</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String code;

	/** <i>Generated property</i> for <code>CategoryWsDTO.url</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String url;

	/** <i>Generated property</i> for <code>CategoryWsDTO.image</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	//private ImageWsDTO image;

	/** <i>Generated property</i> for <code>CategoryWsDTO.name</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private String name;
	
	private String parentId;
	
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



	public String getParentId() {
		return parentId;
	}



	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	


}