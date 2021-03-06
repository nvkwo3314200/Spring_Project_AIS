package com.mall.b2bp.vos.order.orderfromweb;

/**
 * Error message
 */
public  class ErrorWsDTO  implements java.io.Serializable 
{


	/** Type of the error e.g. 'LowStockError'.<br/><br/><i>Generated property</i> for <code>ErrorWsDTO.type</code> property defined at extension <code>webservicescommons</code>. */
		
	private String type;

	/** Additional classification specific for each error type e.g. 'noStock'.<br/><br/><i>Generated property</i> for <code>ErrorWsDTO.reason</code> property defined at extension <code>webservicescommons</code>. */
		
	private String reason;

	/** Descriptive, human readable error message.<br/><br/><i>Generated property</i> for <code>ErrorWsDTO.message</code> property defined at extension <code>webservicescommons</code>. */
		
	private String message;

	/** Type of the object related to the error e.g. 'entry'.<br/><br/><i>Generated property</i> for <code>ErrorWsDTO.subjectType</code> property defined at extension <code>webservicescommons</code>. */
		
	private String subjectType;

	/** Identifier of the related object e.g. '1'.<br/><br/><i>Generated property</i> for <code>ErrorWsDTO.subject</code> property defined at extension <code>webservicescommons</code>. */
		
	private String subject;
	
	public ErrorWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setType(final String type)
	{
		this.type = type;
	}
	
	
	
	public String getType() 
	{
		return type;
	}
	
	
	
	public void setReason(final String reason)
	{
		this.reason = reason;
	}
	
	
	
	public String getReason() 
	{
		return reason;
	}
	
	
	
	public void setMessage(final String message)
	{
		this.message = message;
	}
	
	
	
	public String getMessage() 
	{
		return message;
	}
	
	
	
	public void setSubjectType(final String subjectType)
	{
		this.subjectType = subjectType;
	}
	
	
	
	public String getSubjectType() 
	{
		return subjectType;
	}
	
	
	
	public void setSubject(final String subject)
	{
		this.subject = subject;
	}
	
	
	
	public String getSubject() 
	{
		return subject;
	}
	


}