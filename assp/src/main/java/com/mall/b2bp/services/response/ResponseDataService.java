package com.mall.b2bp.services.response;

import java.util.Locale;

import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.vos.ResponseData;


public interface ResponseDataService {

	ResponseData<?> getReturnData(Class<?> returnType)
			throws  SystemException;

	public Locale langToLocale(String lang, String separator);
	
}
