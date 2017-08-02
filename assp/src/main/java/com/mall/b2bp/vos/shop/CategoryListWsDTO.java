/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 2017-3-17 10:37:34
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


import java.util.List;

public  class CategoryListWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>CategoryListWsDTO.categories</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private List<CategoryWsDTO> categories;
	
	public CategoryListWsDTO()
	{
		// default constructor
	}
	
		
	
	public void setCategories(final List<CategoryWsDTO> categories)
	{
		this.categories = categories;
	}

		
	
	public List<CategoryWsDTO> getCategories() 
	{
		return categories;
	}
	


}