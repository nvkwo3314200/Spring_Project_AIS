package com.mall.b2bp.vos.order.orderfromweb;

/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN!
 * --- Generated at 2016-8-30 18:49:13
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


import java.util.Date;

public  class SupplierCollectionWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>SupplierCollectionWsDTO.pickedConfirmDate</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private Date pickedConfirmDate;

	/** <i>Generated property</i> for <code>SupplierCollectionWsDTO.shippedConfirmDate</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private Date shippedConfirmDate;

	/** <i>Generated property</i> for <code>SupplierCollectionWsDTO.supplier</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private String supplier;

	/** <i>Generated property</i> for <code>SupplierCollectionWsDTO.orderSupplierStatus</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private String orderSupplierStatus;

	/** <i>Generated property</i> for <code>SupplierCollectionWsDTO.order</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private String order;
	
	public SupplierCollectionWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setPickedConfirmDate(final Date pickedConfirmDate)
	{
		this.pickedConfirmDate = pickedConfirmDate;
	}
	
	
	
	public Date getPickedConfirmDate() 
	{
		return pickedConfirmDate;
	}
	
	
	
	public void setShippedConfirmDate(final Date shippedConfirmDate)
	{
		this.shippedConfirmDate = shippedConfirmDate;
	}
	
	
	
	public Date getShippedConfirmDate() 
	{
		return shippedConfirmDate;
	}
	
	
	
	public void setSupplier(final String supplier)
	{
		this.supplier = supplier;
	}
	
	
	
	public String getSupplier() 
	{
		return supplier;
	}
	
	
	
	public void setOrderSupplierStatus(final String orderSupplierStatus)
	{
		this.orderSupplierStatus = orderSupplierStatus;
	}
	
	
	
	public String getOrderSupplierStatus() 
	{
		return orderSupplierStatus;
	}
	
	
	
	public void setOrder(final String order)
	{
		this.order = order;
	}
	
	
	
	public String getOrder() 
	{
		return order;
	}
	


}