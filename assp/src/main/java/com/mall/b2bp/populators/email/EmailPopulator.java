package com.mall.b2bp.populators.email;
import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.email.EmailTemplateVo;

public class EmailPopulator {
	public static final String HOME_ORDER_INTERFACE="HOME_ORDER";
	public static final String RVS_ARRIVES="RVS";
	
	public EmailTemplateVo getEmailTemplateHtml(String emailType,String userName,String hybridOrderId,String orderReturnId){
		EmailTemplateVo emailTemplateVo = new EmailTemplateVo();
		String subject = "";
		String url = ResourceUtil.getSystemConfig().getProperty("pssp.url");
		StringBuffer htmlEmailTemplate = new StringBuffer("Dear Mr/Mis"+userName);
		if(StringUtils.isNotEmpty(emailType)){
			   if(emailType.equals(EmailPopulator.HOME_ORDER_INTERFACE)){
				    subject = "New Watsons Mall order is placed";
				    htmlEmailTemplate.append("<br/>");
					htmlEmailTemplate.append("New Watsons Mall order is just placed! Please check from the ASW Supplier Portal:"+url);
					htmlEmailTemplate.append("<br/>");
					htmlEmailTemplate.append("Order ID:"+hybridOrderId);
		       }else if(emailType.equals(EmailPopulator.RVS_ARRIVES)){
		    	   subject = "New Watsons Mall Return Request is received";   
		    	    htmlEmailTemplate.append("<br/>");
				    htmlEmailTemplate.append("New Watsons Mall order Return Request is just received! Please check from the ASW Supplier Portal:"+url);
				    htmlEmailTemplate.append("<br/>");
				    htmlEmailTemplate.append("Order ID:"+hybridOrderId);
				    htmlEmailTemplate.append("<br/>");
				    htmlEmailTemplate.append("Return ID:"+orderReturnId);
			   }else{
				   
			   }
		}	
		emailTemplateVo.setSubject(subject);
		emailTemplateVo.setHtmlEmailTemplate(htmlEmailTemplate.toString());
        return emailTemplateVo;
	}
}
