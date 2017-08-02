package com.mall.b2bp.services.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by AIS on 14-5-13.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UploadProductResponse extends WsResponse {
    private String custID;
    private String statCde;

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getStatCde() {
        return statCde;
    }

    public void setStatCde(String statCde) {
        this.statCde = statCde;
    }
}
