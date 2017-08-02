package com.mall.b2bp.utils;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final Logger LOG =Logger.getLogger(StringUtil.class);
	private StringUtil(){
		
	}
		
	public static String repalceAllNewLineToBr(String str){
		if(StringUtils.isEmpty(str)){
			return str;
		}
		String result = str.replaceAll("\r\n", "<br/>");
		result = result.replaceAll("\n", "<br/>");
		result = result.replaceAll("\r", "<br/>");
		return result;
	}

	public static String repalceAllBrToNewLine(String str){
		if(StringUtils.isEmpty(str)){
			return str;
		}
		return str.replaceAll("<br/>", "\n");
	}
	
	public static String repalceMultipleReturnLine(String str){
		if(StringUtils.isEmpty(str)){
			return str;
		}
		return str.replaceAll("(?m)^\\s*$(\\r|\\n|\\r\\n)", "");
	}
	
	public static String removeReturnLine(String str){
		if(StringUtils.isEmpty(str)){
			return str;
		}
		return str.replaceAll("[\\t\\n\\r]", "");
	}
	
	
	public static boolean checkItemNameEn(String itemNameEn){  	 
		
		String regExp ="^[A-Z0-9 `~!@#$%^&*()_+-={}\\:;'<>?./]+$";  
		  
		Pattern pattern = Pattern.compile(regExp);  
		   
	    return pattern.matcher(itemNameEn).matches();  
		
	 } 

	public static boolean checkSpecialCharacter(String content){  	 
		if(content == null)
			return false;
		  
		if(content.indexOf("|") > -1){
			return true;
		}else{
			return false;
		}
	} 

	public static String[] getAllProductId(String pIds) {
		String[] ids = null;
		if(StringUtils.isNotEmpty(pIds)){
			if(pIds.indexOf(",") != -1){
				ids = pIds.split(",");
			}else{
				ids = new String[]{pIds};
			}
		}
		return ids;
	}
	
	 public static boolean checkEmail(String email){
	        boolean flag = false;
	        try{
	                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	                Pattern regex = Pattern.compile(check);
	                Matcher matcher = regex.matcher(email);
	                flag = matcher.matches();
	            }catch(Exception e){
	                flag = false;
	                LOG.error(e.getMessage(), e);
	            }
	        return flag;
	    }
	 
	 public static boolean checkStringEquals(String str1,String str2){
		 
		 
		 if(StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2))
		 {
			 return false;
		 }else if(StringUtils.isEmpty(str1) && StringUtils.isNotEmpty(str2)){
			 return true;
		 }else if(StringUtils.isNotEmpty(str1) && StringUtils.isEmpty(str2)){
			 return true;
		 }else if(StringUtils.isNotEmpty(str1)&&StringUtils.isNotEmpty(str2)&&!str1.equals(str2)){
			 return true;
		 }
		 return false;
	 }
	 
	public static boolean checkBigDecimalEquals(BigDecimal a, BigDecimal b) {

		if (a == null && b == null) {
			return false;
		} else if (a == null) {
			return true;
		} else if (b == null) {
			return true;
		} else if (a.compareTo(b) != 0) {
			return true;
		}
		
		return false;
	}
	
	
	public static String convert(String val) {
		String str = "";
		if (StringUtils.isNotEmpty(val)) {
			str = val;
		}
		return str;
	}

	public static String convert(BigDecimal val) {
		String str = "";
		if (val != null) {
			str = val.toString();
		}
		return str;
	}
	
	public static String decimalFormat(BigDecimal b) {

		if (b == null)
			return "";
		try {
			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();

			df.applyPattern("##.##");
			return df.format(b.doubleValue());
		} catch (Exception ex) {
			return "";
		}
	}

}
