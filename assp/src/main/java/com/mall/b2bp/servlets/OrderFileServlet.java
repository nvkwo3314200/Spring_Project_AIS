package com.mall.b2bp.servlets;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.utils.ResourceUtil;



public class OrderFileServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(OrderFileServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 250304177885057131L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	
		try(
				OutputStream out = response.getOutputStream();
				){

			String path = ResourceUtil.getSystemConfig().getProperty("upload_file_path");
			
			
			String fileName = request.getParameter("fileName");
			
			if (StringUtils.isEmpty(fileName))
				return;
		
			fileName =  URLEncoder.encode(fileName, "UTF-8");

			String cleanPath = path+File.separator+fileName;
			File fileObj=isExitFile(cleanPath);
			if(fileObj!=null){
				
				byte[] file = FileUtils.readFileToByteArray(fileObj);

				String contentType = getServletContext().getMimeType(fileName);
				if (contentType == null){
					contentType = "image/jpeg;";
				}

				response.setCharacterEncoding("UTF-8");
				response.setContentType(contentType);

				if(file!=null && out!=null) { 
	     			out.write(file);
					out.flush();
				}
			}
		}catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
		} 
		

	}

	private File  isExitFile(String cleanPath) {
		File fileObj=null;
		try {
			 fileObj=new File(cleanPath);
			if(!fileObj.exists()){
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
		return fileObj;
	
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			{

		doGet(request, response);
	}

	

}
