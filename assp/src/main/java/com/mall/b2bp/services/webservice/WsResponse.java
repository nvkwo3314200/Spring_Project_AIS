package com.mall.b2bp.services.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Alan on 13/5/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WsResponse {

    @XmlElement(required=true)
    private String responseCode;

    @XmlElement(required=false)
    private String errorDescription;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
