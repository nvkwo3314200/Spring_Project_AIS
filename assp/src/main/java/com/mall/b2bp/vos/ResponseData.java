package com.mall.b2bp.vos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ResponseData<T> {
	private String errorType;// success or error

	private T returnData;
	private List<T> returnDataList;
	private List<String> errorList = new ArrayList<>();
	private String errorMessage;
	private ResourceBundleMessageSource resourceBundleMessageSource;
	private Locale language;
	private byte[] fileContent;
	private MessageSource messageSource;
	
	private String hook;

	private Map<String, String[]> argsMap =new HashMap<>();

	public void  putMessagesParamArray(String key,String[] value){
		argsMap.put(key, value);
	}
	
	public Map<String, String[]> getArgsMap() {
		return argsMap;
	}

	public void setArgsMap(Map<String, String[]> argsMap) {
		this.argsMap = argsMap;
	}



	public Locale getLanguage() {
		return language;
	}

	public void setLanguage(Locale language) {
		this.language = language;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public void add(String errorMsg) {
		errorList.add(errorMsg);
	}

	public ResourceBundleMessageSource getResourceBundleMessageSource() {
	return resourceBundleMessageSource;
}

	
public void setResourceBundleMessageSource(
		ResourceBundleMessageSource resourceBundleMessageSource) {
	this.resourceBundleMessageSource = resourceBundleMessageSource;
}




	public String getErrorMessage() {
//		Locale locale=LocaleContextHolder.getLocale();  
		
		StringBuffer sb=new StringBuffer("");

		for (int i = 0; i < errorList.size(); i++) {
			String msg=getResourceBundleMessageSource().getMessage(errorList.get(i), argsMap.get(errorList.get(i)), getLanguage());
			sb.append(msg);
			if(i+1!=errorList.size()){
				sb.append("<br/>");
			}

		}
			String msg=sb.toString();
			if("".equals(msg)){
				msg=this.errorMessage;
			}
			
			
		return msg;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getReturnData() {
		return returnData;
	}

	public void setReturnData(T returnData) {
		this.returnData = returnData;
	}


	public List<T> getReturnDataList() {
		return returnDataList;
	}

	public void setReturnDataList(List<T> returnDataList) {
		this.returnDataList = returnDataList;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public String getHook() {
		return hook;
	}

	public void setHook(String hook) {
		this.hook = hook;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public Locale initLangByString(String lang) {
		if(StringUtils.isBlank(lang)) return Locale.ENGLISH;
		if(lang.toLowerCase().indexOf("cn") > -1) return Locale.CHINA;
		if(lang.toLowerCase().indexOf("tw") > -1) return Locale.TAIWAN;
		return Locale.ENGLISH;
	}
	
}
