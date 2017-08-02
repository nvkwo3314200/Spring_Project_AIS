package com.mall.b2bp.services.order;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.vos.order.OrderReturnExportVo;

public interface OrderReturnExportReportHandler {

	String generateReportName(String name);

	Workbook generateXls(List<OrderReturnExportVo> data) throws ServiceException;

}
