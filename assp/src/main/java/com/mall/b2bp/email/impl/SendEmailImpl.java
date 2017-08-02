package com.mall.b2bp.email.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.models.user.UserModel;
import com.mall.b2bp.populators.email.EmailPopulator;
import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.email.EmailTemplateVo;
import com.mall.b2bp.vos.email.NotificationEmailVo;

@Service("sendEmail")
public class SendEmailImpl implements SendEmail{
    private static final Logger LOG = LoggerFactory.getLogger(SendEmailImpl.class);
	@Resource(name="userService")
	UserService userService;
    private Properties sysProperties=null;
    
	public static final String HOME_ORDER_INTERFACE="HOME_ORDER";
	public static final String RVS_ARRIVES="RVS";
	
	
 public SendEmailImpl(){
	 sysProperties=ResourceUtil.getSystemConfig();
	 
 };
 @Override
public boolean sendMail(String toEmial,String name,String subject,String htmlEmailTemplate){
	boolean flag=false;
	  ImageHtmlEmail email = new ImageHtmlEmail();
	  try {
		
		  email.setHostName(sysProperties.getProperty("mail.smtp.server"));
		  email.setFrom(sysProperties.getProperty("mail.smtp.useremail"),sysProperties.getProperty("mail.smtp.user"));
		  
		  String auth = sysProperties.getProperty("mail.smtp.auth");
		  if("true".equalsIgnoreCase(auth)){
			  email.setAuthentication(sysProperties.getProperty("mail.smtp.user"), sysProperties.getProperty("mail.smtp.password"));
		  }
		  email.setSmtpPort(Integer.valueOf(sysProperties.getProperty("mail.smtp.port")));
		  email.addTo(toEmial,name);
		  email.setSubject(subject);
		  email.setHtmlMsg(htmlEmailTemplate);
		  email.send();
		  flag=true;
	} catch (EmailException e) {
		LOG.error(e.getMessage(),e);
		return false;
	}
	return flag;

}

public Properties getSysProperties() {
	return sysProperties;
}

public void setSysProperties(Properties sysProperties) {
	this.sysProperties = sysProperties;
}
@Override
public boolean sendErrorLogMail(List<ErrorLog> errorLogList) throws UnsupportedEncodingException {
	
	if(CollectionUtils.isEmpty(errorLogList))
		return false;
	
	boolean flag=false;
	  ImageHtmlEmail email = new ImageHtmlEmail();
	  try {
		String toEmial=sysProperties.getProperty("sys_support_email");
		String name=sysProperties.getProperty("sys_support_name");
		String []mailStr=toEmial.split(";");
		String []nameStr=name.split(";");
		List<InternetAddress> emailList=new ArrayList<>();
		for (int i=0;i<nameStr.length;i++) {
			if(StringUtils.isNotEmpty(mailStr[i])){
				InternetAddress address=new InternetAddress();
				address.setAddress(mailStr[i]);
				address.setPersonal(nameStr[i]);
				emailList.add(address);
			}
		}

		String subject=sysProperties.getProperty("sys_support_subject");
		String htmlEmailTemplate="Dear All,<br/>"+getErrorMsg(errorLogList);
		  email.setHostName(sysProperties.getProperty("mail.smtp.server"));
		  email.setFrom(sysProperties.getProperty("mail.smtp.useremail"),sysProperties.getProperty("mail.smtp.name"));
		  String auth = sysProperties.getProperty("mail.smtp.auth");
		  if("true".equalsIgnoreCase(auth)){
			  email.setAuthentication(sysProperties.getProperty("mail.smtp.username"), sysProperties.getProperty("mail.smtp.password"));
		  }
		  email.setSmtpPort(Integer.valueOf(sysProperties.getProperty("mail.smtp.port")));
		  email.setTo(emailList);
		  email.setSubject(subject);
		  email.setHtmlMsg(htmlEmailTemplate);
		  email.send();
		  flag=true;
	} catch (EmailException e) {
		LOG.error(e.getMessage(),e);
		return false;
	}
	return flag;
}

private String getErrorMsg(List<ErrorLog> errorLogList){
	StringBuffer sb=new StringBuffer("");
	if(errorLogList!=null&&!errorLogList.isEmpty()){
		for (ErrorLog errorLog : errorLogList) {
			sb.append("Method Name:"+errorLog.getMethodName());
			sb.append("<br/>");
			sb.append("Error Type:"+errorLog.getErrorLogType().getErrorType());
			sb.append("<br/>");
			sb.append("FileName:"+errorLog.getFileName());
			sb.append("<br/>");
			sb.append("Time:"+errorLog.getCreateTime());
			sb.append("<br/>");
			sb.append("<br/>");
			
			sb.append("Error Message:");
			sb.append("<br/>");
			List<String> errorList=errorLog.getErrorList();
			if(errorList!=null&&!errorList.isEmpty()){
				for (int i = 0; i < errorList.size(); i++) {
					sb.append(Integer.valueOf(i+1)+":"+errorList.get(i));
					sb.append("<br/>");
				}
		
			}
		}
	}
	
	return sb.toString();
}

@Override
public boolean sendNotificationEmail(List<NotificationEmailVo> notifyList){
	if(CollectionUtils.isEmpty(notifyList))
		return false;
	  boolean flag=false;
	  try {
		  EmailPopulator emailPopulator = new EmailPopulator();
		  for (NotificationEmailVo vo: notifyList) {  
			  UserModel userModel = userService.getUserBySupplierId(vo.getSupplierId());
			  if(userModel != null){
				  EmailTemplateVo emailTemplateVo = 
						  emailPopulator.getEmailTemplateHtml(vo.getEmailType(),userModel.getUserName(), vo.getHybridOrderId(), vo.getOrderReturnId()); 
			  flag = sendMail(userModel.getEmail(), userModel.getUserName(), emailTemplateVo.getSubject(), emailTemplateVo.getHtmlEmailTemplate());
			  }  
		  }  
	  } catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return false;
		}
		return flag;
	
}

//@Override
//public boolean sendNotificationEmail(List<OrderBean> orderVoList,String emailType,List<OrderReturnBean> orderReturnVos){
//	if(CollectionUtils.isEmpty(orderVoList))
//		return false;
//	  boolean flag=false;
//	  try {
//		  if("HomeOrder interface".equals(emailType)){	  
//			  if(CollectionUtils.isNotEmpty(orderVoList)){
//				  for(OrderBean orderVo : orderVoList){
//					  EmailPopulator emailPopulator = new EmailPopulator();
//					  if(orderVo != null){
//					  Map<String,OrderEntryBean> map = getAllOrderEntrySupplierId(orderVo.getEntryBeanList());
//					  if(map != null && map.size() > 0){
//						  for (Map.Entry<String,OrderEntryBean> entry : map.entrySet()) {  
//							  if(StringUtils.isNotEmpty(entry.getKey())){
//								  UserModel userModel = userService.getUserBySupplierId(entry.getKey());
//								  if(userModel != null){
//							    EmailTemplateVo emailTemplateVo = emailPopulator.getEmailTemplateHtml(emailType,userModel.getUserName(), orderVo.getHybrisOrderId(), null); 
//								 flag = sendMail(userModel.getEmail(), userModel.getUserName(), emailTemplateVo.getSubject(), emailTemplateVo.getHtmlEmailTemplate());
//								 }
//							  }
//							  }  
//					  }
//					  }
//				  }
//			  }
//		  }else{
//			  if(CollectionUtils.isNotEmpty(orderReturnVos)){
//				  for(OrderReturnBean orderReturnVo : orderReturnVos){
//					  EmailPopulator emailPopulator = new EmailPopulator();
//					
//					  if(orderReturnVo.getSupplierId() != null){
//						 UserModel userModel = userService.getUserBySupplierId(orderReturnVo.getSupplierId());
//						 if(userModel != null){
//					     EmailTemplateVo emailTemplateVo = emailPopulator.getEmailTemplateHtml(emailType,userModel.getUserName(), null, orderReturnVo.getId()); 
//						 flag = sendMail(userModel.getEmail(), userModel.getUserName(), emailTemplateVo.getSubject(), emailTemplateVo.getHtmlEmailTemplate());
//						 }
//					  }
//				  }
//			  } 
//		  }
//	} catch (Exception e) {
//		LOG.error(e.getMessage(),e);
//		return false;
//	}
//	return flag;
//}

//private Map<String,OrderEntryBean> getAllOrderEntrySupplierId(List<OrderEntryBean> orderEntryBeans){
//	Map<String,OrderEntryBean> map = new HashMap<String, OrderEntryBean>();
//	if(CollectionUtils.isNotEmpty(orderEntryBeans)){
//		for(OrderEntryBean orderEntryBean : orderEntryBeans){
//			if(StringUtils.isNotEmpty(orderEntryBean.getSupplierId())){
//				map.put(orderEntryBean.getSupplierId(), orderEntryBean);
//			}
//		}
//	}
//	return map;
//}

}
