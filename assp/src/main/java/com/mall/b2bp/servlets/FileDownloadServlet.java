package com.mall.b2bp.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.mall.b2bp.enums.ImportLogPath;

public class FileDownloadServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	// Process the HTTP Get request
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		// 得到下载文件的名字
		// String filename=request.getParameter("filename");

		// 解决中文乱码问题
		String fileName = new String(request.getParameter("fileName").getBytes(
				"iso-8859-1"), "gbk");
		String importType = request.getParameter("importType");
		String importPath = "";
		if (StringUtils.isNotEmpty(importType)) {
			importPath = ImportLogPath.getLogType(importType)
					.getImportLogPathDesc();
		}

		// 创建file对象
		File file = new File(importPath+ File.separator + fileName);
		if(!file.exists()){
			importPath = ImportLogPath.PRODUCT_ACK_FROM_RETEK_ERROR_PATH
					.getImportLogPathDesc();
			file = new File(importPath+ File.separator + fileName);
		}
		
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");
		// 写明要下载的文件的大小
		response.setContentLength((int) file.length());
		// 设置附加文件名
		// response.setHeader("Content-Disposition","attachment;filename="+filename);

		// 解决中文乱码
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("gbk"), "iso-8859-1"));
		// 读出文件到i/o流
		try(
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);
		){
		byte[] b = new byte[1024];// 相当于我们的缓存
		long k = 0;// 该值用于计算当前实际下载了多少字节
		// 从response对象中得到输出流,准备下载
		ServletOutputStream myout = response.getOutputStream();
		// 开始循环下载
		while (k < file.length()) {
			int j = buff.read(b, 0, 1024);
			k += j;
			// 将b中的数据写到客户端的内存
			myout.write(b, 0, j);
		}
		// 将写入到客户端的内存的数据,刷新到磁盘
		myout.flush();
		}
	}

	// Process the HTTP Post request
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	
}
