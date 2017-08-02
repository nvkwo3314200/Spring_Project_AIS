package com.mall.b2bp.services.impl.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.services.impl.product.ReportHandler;
import com.mall.b2bp.services.order.OrderExportReportHandler;
import com.mall.b2bp.vos.order.OrderExportVo;
@Service("orderExportReportHandler")
public class OrderExportReportHandlerImpl extends ReportHandler implements
		OrderExportReportHandler {
	private final static String [] header = { "Order ID",
			"Order consignment ID", "Order type", "Order status",
			"Recipient Name", "Recipient Contact number", "Recipient Address",
			"Customer Name", "Customer Contact number", "Order create date",
			"Order shipped date", "Order delivered date", "SKU", "VPN",
			"Brand name", "Product description", "Size Description",
			"Ordered quantity", "Picked/Shipped quantity",
			"Refunded quantity", "Price" };

	@Override
	public String generateReportName(String name) {
		return super.getDateXlsxStr(name);
	}

	@Override
	public Workbook generateXls(List<OrderExportVo> data)
			throws ServiceException {
		// 工作区
		XSSFWorkbook wb = new XSSFWorkbook();
		addStyle(wb);
		XSSFSheet sheet = wb.createSheet("Order");
		
		int rownum=0;
		XSSFRow row=sheet.createRow(rownum);
		//header
			 
		for(int i=0;i<header.length;i++){
			
			 sheet.setColumnWidth(i, 4000);
			 
			Cell cell=getCell(row,i);
			cell.setCellValue(header[i]);
		}	
		//content
		for (OrderExportVo order : data) {
			row=sheet.createRow(++rownum);
			createRowData(row,order);
		}
		return wb;
	}
	
	
    //添加居中的样式
    public CellStyle addStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直  
        style.setAlignment(CellStyle.ALIGN_CENTER);// 水平  
        return style;
    }
	private void  createRowData(XSSFRow row,OrderExportVo order){
		
		Cell cell0=getCell(row,0);
		cell0.setCellValue(order.getHybrisOrderId());
		
		Cell cell1=getCell(row,1);
		cell1.setCellValue(order.getPickOrderId());
		
		Cell cell2=getCell(row,2);
		cell2.setCellValue(order.getOrderTypeDesc());
		
		Cell cell3=getCell(row,3);
		cell3.setCellValue(order.getStatus());
		
		Cell cell4=getCell(row,4);
		cell4.setCellValue(order.getReceiverName());
		
		Cell cell5=getCell(row,5);
		cell5.setCellValue(StringUtils.isNotEmpty(order.getReceiverPhoneNo())&&StringUtils.isNotEmpty(order.getReceiverMobileNo())?(order.getReceiverPhoneNo()+"/"+order.getReceiverMobileNo()):(order.getReceiverPhoneNo()+order.getReceiverMobileNo()));
		
		Cell cell6=getCell(row,6);
		cell6.setCellValue(order.getDeliveryAddress());
		
		Cell cell7=getCell(row,7);
		cell7.setCellValue(order.getCustomerName());
		
		Cell cell8=getCell(row,8);
		cell8.setCellValue(StringUtils.isNotEmpty(order.getCustomerPhoneNo())&&StringUtils.isNotEmpty(order.getCustomerMobileNo())?(order.getCustomerPhoneNo()+"/"+order.getCustomerMobileNo()):(order.getCustomerPhoneNo()+order.getCustomerMobileNo()));

		Cell cell9=getCell(row,9);
		cell9.setCellValue(order.getOrderDatetime());
		
		Cell cell10=getCell(row,10);
		cell10.setCellValue(order.getConsignmentShippedDate());
		
		Cell cell11=getCell(row,11);
		cell11.setCellValue(order.getDeliveryDate());
		
		Cell cell12=getCell(row,12);
		cell12.setCellValue(order.getSkuId());
		
		Cell cell13=getCell(row,13);
		cell13.setCellValue(order.getSupplierProductCode());
		
		Cell cell14=getCell(row,14);
		cell14.setCellValue(order.getBrandSec());
		
		Cell cell15=getCell(row,15);
		cell15.setCellValue(order.getProductName());
		
		Cell cell16=getCell(row,16);
		cell16.setCellValue(order.getSizeDesc());
		
		Cell cell17=getCell(row,17);
		cell17.setCellValue(order.getQty()!=null?order.getQty().toString():"");
		
		Cell cell18=getCell(row,18);
		cell18.setCellValue((order.getPickedQty()!=null?order.getPickedQty().toString():"")+"/"+(order.getShippedQty()!=null?order.getShippedQty().toString():""));
		
		Cell cell19=getCell(row,19);
		cell19.setCellValue(order.getRefundedQty()!=null?order.getRefundedQty().toString():"");
		
		Cell cell20=getCell(row,20);
		cell20.setCellValue(order.getUnitPrice()!=null?order.getUnitPrice().toString():"");
		
	}
	
	private Cell getCell(XSSFRow row,int cellNum){
		Cell cell=row.createCell(cellNum);
		return cell;
		
	}

}
