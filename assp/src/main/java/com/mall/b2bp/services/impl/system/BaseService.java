package com.mall.b2bp.services.impl.system;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mall.b2bp.services.system.ResponseDataService;
import com.mall.b2bp.services.system.SessionService;

@Service
public class BaseService {
	
	// -lh-10-24
	protected BigDecimal progress;
	
	protected boolean cancel;
	
	@Resource(name="responseDataService1")
	protected  ResponseDataService responseDataService;
	
	@Resource(name="sessionService1")
	protected SessionService sessionService;
	
	public BigDecimal getProgress() {
		if(progress==null) new BigDecimal("0");
		return progress;
	}

	public void setProgress(BigDecimal progress) {
		this.progress = progress;
	}
	
	public void initProgress() {
		this.progress = new BigDecimal("0");
		this.cancel = false;
	}
	
	public void cancelProgress() {
		this.cancel = true;
	}
	
}
