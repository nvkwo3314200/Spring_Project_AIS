package com.mall.b2bp.vos.order.orderfromweb;

import java.util.List;

/**
 * List of errors
 */
public  class ErrorListWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>ErrorListWsDTO.errors</code> property defined at extension <code>webservicescommons</code>. */
		
	private List<ErrorWsDTO> errors;
	
	public ErrorListWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setErrors(final List<ErrorWsDTO> errors)
	{
		this.errors = errors;
	}
	
	
	
	public List<ErrorWsDTO> getErrors() 
	{
		return errors;
	}
	


}