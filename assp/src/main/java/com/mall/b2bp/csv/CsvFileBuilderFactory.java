package com.mall.b2bp.csv;

import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.order.OrderVo;

import java.util.List;

/**
 * Created by USER on 2016/3/23.
 */
public class CsvFileBuilderFactory {
	
	private CsvFileBuilderFactory(){
		
	}

    public static CsvFileBuilder getCsvFileBuilder(String waitForUpdateStatus,List<OrderVo> orderVoList){

        CsvFileBuilder csvFileBuilder = null;
        if (ConstantUtil.PICKED.equals(waitForUpdateStatus)) {
            csvFileBuilder = new HomePickFileWrite(orderVoList);
        }

        if (ConstantUtil.SHIPPED.equals(waitForUpdateStatus)) {
            csvFileBuilder = new HomeShipFileWrite(orderVoList);
        }

        if (ConstantUtil.DELIVERY.equals(waitForUpdateStatus)) {
            csvFileBuilder = new HomeDeliverFileWrite(orderVoList);
        }

        return csvFileBuilder;
    }
}
