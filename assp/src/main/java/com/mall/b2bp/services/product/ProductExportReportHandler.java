package com.mall.b2bp.services.product;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.product.ProductExportModel;

public interface ProductExportReportHandler {
	
	String generateReportName(String name);

	Workbook generateXls(List<ProductExportModel>  data,String templateFile,boolean supplier,String supplierId) throws ServiceException;
	
	
}
