package com.mall.b2bp.services.product;

import com.mall.b2bp.exception.SystemException;

public interface RetekService {
	String generateSkuId(String suplierId, String vpn) throws SystemException;
}
