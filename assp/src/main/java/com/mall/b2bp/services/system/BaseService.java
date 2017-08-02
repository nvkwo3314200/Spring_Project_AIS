package com.mall.b2bp.services.system;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public interface BaseService {
	public BigDecimal getProgress();

	public void setProgress(BigDecimal progress);
	
	public void initProgress();
	
	public void cancelProgress();
	
}
