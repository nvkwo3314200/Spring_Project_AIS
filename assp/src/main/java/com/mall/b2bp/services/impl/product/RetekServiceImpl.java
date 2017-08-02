package com.mall.b2bp.services.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.product.RetekService;

@Service("retekService")
public class RetekServiceImpl implements RetekService {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(RetekServiceImpl.class);
	
//	@Value("${sku.webservice.url}")
	private String skuWsUrl;
	
//	@Value("${system.env}")
	private String systemEnv;
	
	@Resource
	private RestTemplate restTemplate;

	@Override
	public String generateSkuId(String suplierId, String vpn) throws SystemException {
		
		String skuId = null;
		if("LOCAL".equalsIgnoreCase(systemEnv)){
			int randomNum = (int) (1 + Math.random() * (9999 - 1000 + 1));
			skuId = suplierId + "-" + randomNum + "-" + vpn;
			if (skuId.length() > 25) {
				skuId = skuId.substring(0, 25);
			}
		}else{
			LOG.info(skuWsUrl);

			HttpHeaders headers = new HttpHeaders();
			headers.add("i_supplier", suplierId);
			headers.add("i_vpn", vpn);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			LOG.info("===============begin call ws");
			try{
				ResponseEntity<String> result = restTemplate.exchange(skuWsUrl,HttpMethod.GET, entity, String.class);
				
				LOG.info(result.getBody());
				List<String> item = result.getHeaders().get("o_item");
				if(item != null && !item.isEmpty()){
					LOG.info("o_item:"+item.get(0));
					
					String returnCode = item.get(0);
					//20160707 
					if(StringUtils.isNotEmpty(returnCode)){
						if(returnCode.startsWith("-")){
							String[] results = returnCode.split("\\|");
							if(results.length == 2){
								LOG.error(results[0]);
								LOG.error("Cannot get skuId from Retek WS - Supplier ID["+suplierId+"]:"+results[1]);
								throw new SystemException("Cannot get skuId from Retek WS - "+results[1]);
							}else if(results.length == 3){
								LOG.error(results[0]);
								LOG.error("Cannot get skuId from Retek WS - Supplier ID["+suplierId+"]:"+results[1] + " " +results[2]);
								throw new SystemException("Cannot get skuId from Retek WS - " + results[1] + " " + results[2]);
							}else{
								LOG.error("Cannot get skuId from Retek WS - Supplier ID["+suplierId+"]: no response code, " + returnCode);
								throw new SystemException("Cannot get sku from Retek WS - " + returnCode);
							}
						}else{
							return returnCode;
						}
					}else{
						LOG.error("Cannot get skuId from Retek WS - Supplier ID["+suplierId+"], no response code");
						throw new SystemException("Cannot get sku from Retek WS, no response code.");
					}
					//20160707
					
					/*
					if("-99999".equals(item.get(0))){
						LOG.error("Cannot get skuId from Retek WS - Supplier ID["+suplierId+"]");
						throw new SystemException("Cannot get sku from Retek WS, Please make sure Supplier ID ["+suplierId+"] is valid.");
					}else{
						skuId = item.get(0);
					}
					*/
				}
			}catch(RestClientException e){
				throw new SystemException("Cannot get skuId from Retek WS.", e);
			}finally{
				LOG.info("===============end call ws");
			}
		}
		return skuId;
	}

}
