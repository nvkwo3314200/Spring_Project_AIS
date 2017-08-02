package com.mall.b2bp.services.product;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.ss.usermodel.Sheet;

import com.mall.b2bp.exception.SystemException;



public interface ProductBatchUploadService {
	Map getFileItem(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException;
	
	boolean checkFilePrefixXlsx(FileItem fileItem);
	
	Sheet createWorkbookSheet(FileItem fileItem)throws SystemException, IOException;
}
