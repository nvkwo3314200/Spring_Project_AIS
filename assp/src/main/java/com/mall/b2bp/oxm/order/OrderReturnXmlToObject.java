package com.mall.b2bp.oxm.order;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

public class OrderReturnXmlToObject {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
	public Marshaller getMarshaller() {
		return marshaller;
	}
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
    
    
}
