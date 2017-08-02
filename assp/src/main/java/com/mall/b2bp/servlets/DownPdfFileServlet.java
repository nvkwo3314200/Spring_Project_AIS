package com.mall.b2bp.servlets;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.utils.PdfUtils;
import com.mall.b2bp.utils.ResourceUtil;



public class DownPdfFileServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(DownPdfFileServlet.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String invoiceInds = request.getParameter("invoiceInds");
		String filePath = ResourceUtil.getSystemConfig().getProperty("pdf_for_order_invoice_ready_ind");
		PdfUtils pdfUtils = new PdfUtils();
		String[] files = pdfUtils.getFilesByPath(filePath);
		String newPath = ResourceUtil.getSystemConfig().getProperty("pdf_for_order_invoice_ready_ind_new_save");
		try {
			printPdf(files, invoiceInds, response, filePath, newPath);
		} catch (Exception e) {
			LOG.error("pdf error:" + e.getMessage(), e);
		}	

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			{

		doGet(request, response);
	}
	
	public void printPdf(String[] files, String invoiceInds,HttpServletResponse response,String filePath,String newPath) throws IOException {
		PdfUtils pdfUtils = new PdfUtils();
		List<String> pdfList = new ArrayList<>();
		if(invoiceInds.contains(",")){
			String[] invoiceStr = invoiceInds.split(",");
			if(files != null && files.length > 0){
				for(String code : files){
					   if(invoiceStr != null && invoiceStr.length > 0){
						   for(String invoice : invoiceStr){
							   if(code.contains(invoice)){
								   pdfList.add(filePath+code);
							   }
						   }
					   }
				}
			}
			
		if(CollectionUtils.isNotEmpty(pdfList)){
			if(pdfList.size()==1){
				pdfUtils.getGeneratePdf(filePath,pdfList.get(0),response);
			}else{
				int size = pdfList.size();  
				String[] file = (String[])pdfList.toArray(new String[size]);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
				String newFileName = sdf.format(new Date());
				String fileName = pdfUtils.getPDF(newPath, newFileName, file);
				pdfUtils.getGeneratePdf(newPath,fileName,response);
				pdfUtils.deleteFile(newPath+newFileName+".pdf");
			}
		}
			
		}else{
			if(files != null && files.length > 0){
				for(String file : files){
					if(file.contains(invoiceInds)){
						pdfUtils.getGeneratePdf(filePath,file,response);
					}
				}
			}
		}
		
	}


	
}
