package com.mall.b2bp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;



public class ValidateUtils {

	private ValidateUtils() {
	}
	
    
	public static boolean  validateNumber(String str)
	{
	    boolean flag=false;
	    if(StringUtils.isEmpty(str)){
	    	return flag;
	    }
		Pattern p = Pattern.compile("^[0-9]\\d*(\\.\\d+)?$");
		Matcher m =p.matcher(str);
		if(m.find()){
		  flag=true;
		}
		return flag;
	}
	
	public static boolean validateInteger(String str){
			boolean flag=false;
		    if(StringUtils.isEmpty(str)){
		    	return flag;
		    }
			Pattern p = Pattern.compile("^[1-9]\\d*$");
			Matcher m =p.matcher(str);
			if(m.find()){
			  flag=true;
			}
			return flag;
		
	}

	
	public static boolean judgeOneDecimal(String number) {
		boolean flag = false;

		try {
			if (StringUtils.isNotEmpty(number)) {

				Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1})?)$");

				if (pattern.matcher(number).matches()) {
					flag = true;
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
