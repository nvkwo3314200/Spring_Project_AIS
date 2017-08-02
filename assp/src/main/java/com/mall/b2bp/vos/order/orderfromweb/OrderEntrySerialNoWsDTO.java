package com.mall.b2bp.vos.order.orderfromweb;

public  class OrderEntrySerialNoWsDTO  implements java.io.Serializable 
{


	/** <i>Generated property</i> for <code>OrderEntrySerialNoWsDTO.serialNo</code> property defined at extension <code>emarketplacewebservices</code>. */
		
	private String serialNo;
	private String returnInd;
	
	
	
	public OrderEntrySerialNoWsDTO()
	{
		// default constructor
	}
	
	
	
	public void setSerialNo(final String serialNo)
	{
		this.serialNo = serialNo;
	}
	
	
	
	public String getSerialNo() 
	{
		return serialNo;
	}



	public String getReturnInd() {
		return returnInd;
	}



	public void setReturnInd(String returnInd) {
		this.returnInd = returnInd;
	}
	


}