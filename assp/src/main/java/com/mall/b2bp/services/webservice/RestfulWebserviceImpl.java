package com.mall.b2bp.services.webservice;

import com.mall.b2bp.vos.product.ProductInfoVo;

import static com.mall.b2bp.utils.CommonUtil.*;

import org.springframework.web.client.RestTemplate;

/**
 *  TODO
 *  Created on 2016年8月1日.
 */
public class RestfulWebserviceImpl implements RestfulWebservice {


	@Override
	public void upload(ProductInfoVo productInfoVo) {
		print(toJsonString(productInfoVo));
		

		  final String uri = "http://192.168.1.47:9001/emarketplacewebservices/v2/electronics/products/uploadProduct";
		  

		  ProductInfoVo productInfoVo2 = new ProductInfoVo();

		  RestTemplate restTemplate = new RestTemplate();
		  ProductInfoVo result = restTemplate.postForObject( uri, productInfoVo2, ProductInfoVo.class);

		  System.out.println(result);
		
	}
}
