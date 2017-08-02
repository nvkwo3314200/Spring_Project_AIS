package com.mall.b2bp.services.system;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.mall.b2bp.vos.ResponseData;

@Service("responseDataService")
public interface ResponseDataService {
	public <T> ResponseData<T> getResponseData();

	public Locale langToLocale(String lang, String separator);
}