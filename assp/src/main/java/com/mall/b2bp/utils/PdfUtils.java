package com.mall.b2bp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class PdfUtils {
	private static final Logger LOG =  LoggerFactory
			.getLogger(PdfUtils.class);
	
	
	public String getPDF(String path, String rName, String[] files) {
		String getResultName = null;
		String savePath = path + rName + ".pdf";
		
		if (files.length > 1) {
		
			mergePdfFiles(files, savePath);
			getResultName = savePath.subSequence(savePath.lastIndexOf("\\"),
					savePath.length()).toString();
		} else {
			if (reNameFile(files, savePath)) {
				getResultName = rName + ".pdf";
			}
		}
		return getResultName;
	}

	
	public static boolean mergePdfFiles(String[] files, String savepath){
		try {
			Document document = new Document(
					new PdfReader(files[0]).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));
			document.open();
			for (int i = 0; i < files.length; i++) {
				PdfReader reader = new PdfReader(files[i]);
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
			document.close();
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
	}

	
	public static boolean reNameFile(String[] oldName, String newName) {
		File oldfile = null;
		if (oldName != null) {
			for (int i = 0; i < oldName.length; i++) {
				oldfile = new File(oldName[i]);
			}
			if (oldfile == null) {
				return false;
			}
			File newfile = new File(newName);
			if (oldfile.renameTo(newfile)) {
				return true;
			}
		}
		return false;
	}


	public String getGeneratePdf(String filepath, String fileName,
			HttpServletResponse response){
		
		 try(
//				 FileInputStream fis = new FileInputStream(filepath+fileName);
					OutputStream os = response.getOutputStream();
//					 BufferedInputStream input = new BufferedInputStream(fis);
		)
		 
		 {
		   response.setContentType("application/pdf");
		   response.addHeader("Content-Disposition", "attachment;filename="+ new String (fileName.getBytes("gb2312"),"iso-8859-1"));
//		   byte[] buffBytes = new byte[1024];
//		   int read = 0;  
//		   while ((read = input.read(buffBytes)) != -1) {     
//		    os.write(buffBytes, 0, read);  
//		    }

		   File file = new File(filepath+fileName);
		   byte[] fileBytes = FileUtils.readFileToByteArray(file);
		   os.write(fileBytes);
		  } catch (IOException e) {
		   
			  LOG.error(e.getMessage(), e);
		  }
		  return null;
	}
	
	public String[] getFilesByPath(String filepath) {
		String[] filelist = null;
		File file = new File(filepath);
		if (!file.isDirectory()) {
		} else if (file.isDirectory()) {
			filelist = file.list();
		} else {
		}
		return filelist;
	}
	
	public void deleteFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
		} else {
			if (file.isFile())
				file.delete();
		}
	}

	

}
