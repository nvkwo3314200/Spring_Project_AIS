package com.mall.b2bp.services.impl.product;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Service;

import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.services.product.ProductBatchUploadService;
import com.mall.b2bp.utils.FileHandle;

import org.apache.poi.ss.usermodel.Sheet;

@Service("productBatchUploadService")
public class ProductBatchUploadcServiceImpl implements ProductBatchUploadService {

	@Override
	public Map getFileItem(HttpServletRequest request)
			throws FileUploadException, UnsupportedEncodingException {
		return FileHandle.getFileItem(request);
	}

	@Override
	public boolean checkFilePrefixXlsx(FileItem fileItem) {
		
		return FileHandle.checkFilePrefixXlsx(fileItem);
	}

	@Override
	public Sheet createWorkbookSheet(FileItem fileItem) throws SystemException, IOException {
		return FileHandle.createWorkbookSheet(fileItem);
	}
	


	
}
