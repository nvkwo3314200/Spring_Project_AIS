package com.mall.b2bp.services.impl.product;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.utils.DateUtils;

public class ReportHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ReportHandler.class);
	
	final static int MAX_SIZE = 100000;
	final static int MIN_SIZE = 1;
	public String getDateXlsxStr(String typeName) {
		StringBuffer sb = new StringBuffer();
		sb.append(typeName);
		sb.append("_");
		sb.append(random());
		sb.append(DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS));
		sb.append(".xlsx");
		return sb.toString();
	}


	public int random() {
		try {
			int max = MAX_SIZE;
			int min = MIN_SIZE;
			Random random = new Random();
			return random.nextInt(max) % (max - min + 1) + min;
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return 1;
		}
	}
}
