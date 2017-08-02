package com.mall.b2bp.vos.order.orderfromweb;

import java.math.BigDecimal;

public  class PriceWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>PriceWsDTO.currencyIso</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String currencyIso;

	/** <i>Generated property</i> for <code>PriceWsDTO.value</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private BigDecimal value;

	/** <i>Generated property</i> for <code>PriceWsDTO.priceType</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private PriceDataType priceType;

	/** <i>Generated property</i> for <code>PriceWsDTO.formattedValue</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private String formattedValue;

	/** <i>Generated property</i> for <code>PriceWsDTO.minQuantity</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long minQuantity;

	/** <i>Generated property</i> for <code>PriceWsDTO.maxQuantity</code> property defined at extension <code>commercewebservicescommons</code>. */
		
	private Long maxQuantity;
	
	public PriceWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setCurrencyIso(final String currencyIso)
	{
		this.currencyIso = currencyIso;
	}
	
	
	
	public String getCurrencyIso() 
	{
		return currencyIso;
	}
	
	
	
	public void setValue(final BigDecimal value)
	{
		this.value = value;
	}
	
	
	
	public BigDecimal getValue() 
	{
		return value;
	}
	
	
	
	public void setPriceType(final PriceDataType priceType)
	{
		this.priceType = priceType;
	}
	
	
	
	public PriceDataType getPriceType() 
	{
		return priceType;
	}
	
	
	
	public void setFormattedValue(final String formattedValue)
	{
		this.formattedValue = formattedValue;
	}
	
	
	
	public String getFormattedValue() 
	{
		return formattedValue;
	}
	
	
	
	public void setMinQuantity(final Long minQuantity)
	{
		this.minQuantity = minQuantity;
	}
	
	
	
	public Long getMinQuantity() 
	{
		return minQuantity;
	}
	
	
	
	public void setMaxQuantity(final Long maxQuantity)
	{
		this.maxQuantity = maxQuantity;
	}
	
	
	
	public Long getMaxQuantity() 
	{
		return maxQuantity;
	}
	


}