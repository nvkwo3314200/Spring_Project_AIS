package com.mall.b2bp.vos.email;

public class EmailTemplateVo {
	private String subject;
	private String htmlEmailTemplate;

	public String getHtmlEmailTemplate() {
		return htmlEmailTemplate;
	}

	public void setHtmlEmailTemplate(String htmlEmailTemplate) {
		this.htmlEmailTemplate = htmlEmailTemplate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
