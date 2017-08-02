package com.mall.b2bp.services.impl.order;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.services.impl.product.ReportHandler;
import com.mall.b2bp.services.order.OrderReturnExportReportHandler;
import com.mall.b2bp.vos.order.OrderReturnExportVo;
@Service("orderReturnExportReportHandler")
public class OrderReturnExportReportHandlerImpl extends ReportHandler implements
		OrderReturnExportReportHandler {
	private final static String [] header = { "Order ID",
			"Return ID", "Return create date", "Return request status",
			"Return request update date", "Customer ID", "Customer Type",
			"Customer Name", "Customer Phone No", "customer mobile no","Tender Type",
			"Payment Ref", "Collect Date", "Collect Timeslot", "Contact Name",
			"Contact Phone No", "Contact Mobile No", "Collect District",
			"Collect Address", "Remark","Special Instructions","SKU ID","Brand",
			"Brand Sec","Product Name","Product Name Sec","Size Desc","Order Quantity",
			"Return Request Quantity","Expected Collect Quantity","Actual Collected Quantity",
			"Write Off Quantity", "Remarks" };

	@Override
	public String generateReportName(String name) {
		return super.getDateXlsxStr(name);
	}

	@Override
	public Workbook generateXls(List<OrderReturnExportVo> data)
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
		for (OrderReturnExportVo order : data) {
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
	private void  createRowData(XSSFRow row,OrderReturnExportVo order){
		
		int n=0;
		
		
		Cell cell0=getCell(row,n++);
		cell0.setCellValue(order.getOrderId()!=null?order.getOrderId().toString():"");
		
		Cell cell1=getCell(row,n++);
		cell1.setCellValue(order.getHybrisReturnId()!=null?order.getHybrisReturnId().toString():"");
		
		Cell cell2=getCell(row,n++);
		cell2.setCellValue(order.getReturnCreateDate());
		
		Cell cell3=getCell(row,n++);
		cell3.setCellValue(order.getReturnRequestStatus());
		
		Cell cell4=getCell(row,n++);
		cell4.setCellValue(order.getReturnRequestUpdateDate());
		
		Cell cell5=getCell(row,n++);
		cell5.setCellValue(order.getCustomerId()!=null?order.getCustomerId().toString():"");
		
		Cell cell6=getCell(row,n++);
		cell6.setCellValue(order.getCustomerType());
		
		Cell cell7=getCell(row,n++);
		cell7.setCellValue(order.getCustomerName());
		
		Cell cell8=getCell(row,n++);
//		cell8.setCellValue(StringUtils.isNotEmpty(order.getCustomerPhoneNo())&&StringUtils.isNotEmpty(order.getCustomerMobileNo())?(order.getCustomerPhoneNo()+"/"+order.getCustomerMobileNo()):(order.getCustomerPhoneNo()+order.getCustomerMobileNo()));
		cell8.setCellValue(order.getCustomerPhoneNo());
		
		Cell cell9=getCell(row,n++);
		cell9.setCellValue(order.getCustomerMobileNo());

		Cell cell10=getCell(row,n++);
		cell10.setCellValue(order.getTenderType());
		
		Cell cell11=getCell(row,n++);
		cell11.setCellValue(order.getPaymentRef());
		
		Cell cell12=getCell(row,n++);
		cell12.setCellValue(order.getCollectDate());
		
		Cell cell13=getCell(row,n++);
		cell13.setCellValue(order.getCollectTimeSlot());
		
		Cell cell14=getCell(row,n++);
		cell14.setCellValue(order.getContactName());
		
		//	Contact Phone No
		Cell cell15=getCell(row,n++);
		cell15.setCellValue(order.getContactPhoneNo());
		//cell14.setCellValue(StringUtils.isNotEmpty(order.getContactPhoneNo())&&StringUtils.isNotEmpty(order.getContactMobileNo())?(order.getContactPhoneNo()+"/"+order.getContactMobileNo()):(order.getContactPhoneNo()+order.getContactMobileNo()));
		
		//int n=15;
		//o	Contact Mobile No
		 cell15=getCell(row,n++);
		 cell15.setCellValue(order.getContactMobileNo());
		
		Cell cell16=getCell(row,n++);
		cell16.setCellValue(order.getCollectDistrict());
		
		Cell cell17=getCell(row,n++);
		cell17.setCellValue(order.getCollectAddress());
		
		Cell cell18=getCell(row,n++);
		cell18.setCellValue(order.getRemark());
		
		Cell cell19=getCell(row,n++);
		cell19.setCellValue(order.getSpecialinstruction());
		
		Cell cell20=getCell(row,n++);
		cell20.setCellValue(order.getSkuId());
		
		Cell cell21=getCell(row,n++);
		cell21.setCellValue(order.getBrand());
		
		Cell cell22=getCell(row,n++);
		cell22.setCellValue(order.getBrandSec());
		
		Cell cell23=getCell(row,n++);
		cell23.setCellValue(order.getProductName());
		
		Cell cell24=getCell(row,n++);
		cell24.setCellValue(order.getProductNameSec());
		
		Cell cell25=getCell(row,n++);
		cell25.setCellValue(order.getSizeDesc());
		
		Cell cell26=getCell(row,n++);
		cell26.setCellValue(order.getOrderQty()!=null?order.getOrderQty().toString():"");
		
		Cell cell27=getCell(row,n++);
		cell27.setCellValue(order.getReturnReqQty()!=null?order.getReturnReqQty().toString():"");
		
		Cell cell28=getCell(row,n++);
		cell28.setCellValue(order.getExpectedQty()!=null?order.getExpectedQty().toString():"");
		
		Cell cell29=getCell(row,n++);
		cell29.setCellValue(order.getActualCollectedQty()!=null?order.getActualCollectedQty().toString():"");
		
		Cell cell30=getCell(row,n++);
		cell30.setCellValue(order.getWriteOffQty()!=null?order.getWriteOffQty().toString():"");
		
		Cell cell31=getCell(row,n++);
		cell31.setCellValue(order.getSkuCollectRmk());
		
	}
	
	private Cell getCell(XSSFRow row,int cellNum){
		Cell cell=row.createCell(cellNum);
		return cell;
		
	}

}
