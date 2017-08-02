package com.mall.b2bp.email;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.email.NotificationEmailVo;


public interface SendEmail {
	boolean sendMail(String toEmial,String name,String subject,String htmlEmailTemplate);
	boolean sendErrorLogMail(List<ErrorLog> errorLogList) throws UnsupportedEncodingException;
//	boolean sendNotificationEmail(List<OrderBean> orderVoList,String emailType,List<OrderReturnBean> orderReturnVos);
	boolean sendNotificationEmail(List<NotificationEmailVo> notifyList);
	
	
}
