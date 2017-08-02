package com.mall.b2bp.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.mall.b2bp.exception.SystemException;


public class FileHandle {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileHandle.class);
	private static final String FILE_XLSX ="xlsx";
	private FileHandle() {
		
	}
	
	public static boolean deleteFile(final String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			//LOG.info("deleteFile error:" + fileName + " not exitst.");
			return false;
		} else {
			if (file.isFile())
				return file.delete();
			else
				return false;
		}
	}

	public static void copyFile(final String newPath, final String historyPath, final String fileName){
		
		try {
			
			if(!new File(newPath).exists()){
				new File(newPath).mkdir();
			}
			
			if(!new File(historyPath).exists()){
				new File(historyPath).mkdir();
			}
			
			if(new File(newPath + File.separator + fileName).exists() 
					) 
				FileUtils.copyFile(new File(newPath + File.separator + fileName), new File(historyPath + File.separator+ fileName));
		}


			catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
	}

	public static String[] getFilesByPath(final String filepath) {
		
		if(StringUtils.isEmpty(filepath))
			return null;
		
		String[] filelist = null;
		File file = new File(filepath);
		if (file.isDirectory()) {

			filelist = file.list();

		} 
		
		//String [] fileNameList={};
        List<String> list = new ArrayList<>();
		if(filelist!=null){
            for (int i = 0; i < filelist.length; i++) {
                if(!isFolder(filepath+File.separator+filelist[i])){
                    list.add(filelist[i]);
                }
            }
		}
		return listChangeToArray(list);
	}

    private static String[] listChangeToArray(List<String> list){
        String [] fileNameList = null;
        if(CollectionUtils.isNotEmpty(list)){
             fileNameList= new String[list.size()];
            int i=0;
            for(String str:list){
                fileNameList[i] = str;
                i++;
            }
        }
        return fileNameList;
    }
	
	private static boolean isFolder(String path){
		File file=new File(path);
		if(file.isDirectory()){
			return true;
		}
		return false;
		
	}
	
    public static Sheet getSheet(java.io.InputStream stream) throws IOException{
            Workbook wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook(stream);
            Sheet sheet1 = wb.getSheetAt(0);
        return sheet1;
    }
    
    
    public static  Map getFileItem(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException {
        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setHeaderEncoding("UTF-8");
         Map params =new HashMap();
        List<FileItem> fileList = upload.parseRequest(request);
        for (FileItem fileItem:fileList){
            if("file".equals(fileItem.getFieldName())){
                params.put(fileItem.getFieldName(), fileItem);
            }else{
                params.put(fileItem.getFieldName(),fileItem.getString());}
        }
        return params;
    }
    
    public static boolean checkFilePrefixXlsx(FileItem fileItem){
        boolean f = false;
        if(null==fileItem)
        	return f;
        String name=fileItem.getName();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(name)){
            String prefix=name.substring(name.lastIndexOf(".") + 1);
            if(FILE_XLSX.equals(prefix)){
                f = true;
            }
        }
        return f;
    }

    
    public static Sheet createWorkbookSheet(FileItem fileItem) throws SystemException, IOException {


        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setHeaderEncoding("UTF-8");
        byte[] bytes = null;

        if (!fileItem.isFormField()) {
            bytes = fileItem.get();
            fileItem.getName();
        }

        if (bytes != null) {
            
           try(
            	ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             )
            {
        	   return FileHandle.getSheet(inputStream);
            }catch(Exception ex){
            	throw new SystemException(ex.getMessage(), ex);
            }
        }

        return null;

    }
    
    public static  String getCellValue(Cell cell) {
    	try{
	        if (cell == null) {
	            return null;
	        }
	        switch (cell.getCellType()) {
	            case Cell.CELL_TYPE_BLANK:
	                return null;
	            case Cell.CELL_TYPE_BOOLEAN:
	                return String.valueOf(cell.getBooleanCellValue());
	            case Cell.CELL_TYPE_ERROR:
	            	 return null;
	            case Cell.CELL_TYPE_FORMULA:
	                	return String.valueOf(cell.getStringCellValue());
	            case Cell.CELL_TYPE_NUMERIC:
	                if (DateUtil.isCellDateFormatted(cell)) {
	                    Date theDate = cell.getDateCellValue();
	                    return DateUtils.formatDate(theDate,DateUtils.DATE_FORMAT_6);
	                } else {
	                	return NumberToTextConverter.toText(cell.getNumericCellValue());
	                }
	            case Cell.CELL_TYPE_STRING:
	            	return cell.getRichStringCellValue().getString();
	            default:
	            	 return null;
	        }
    	}catch(Exception ex){
    		LOG.error("getCellValue error:"+ex.getMessage(),ex);
    		throw ex;
    	}

    }
    
    public static String getResolution1(File file) throws IOException {
		BufferedImage image = ImageIO.read(file);
		return image.getWidth() + "x" + image.getHeight();
	}
    
    
    
    
	public static String getCellByNum(int num){
		
		if(num<=0)
			return null;
		String[] array = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

		
		int count = 26;
		
		String out = "";
		if (num / count != 0) {
			out = array[num / count - 1];
			if (num % count == 0) {
				out = out + array[num % count];
				
			} else {
				out = out + array[num % count - 1];
		
			}
		} else {
			out = array[num - 1];
		
		}
		
		return out;
	}
	
	
//	public byte[] resizeImage(byte[] imageByte, int size, String format) throws IOException {
//		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
//				InputStream is = new ByteArrayInputStream(imageByte);) {
//
//			BufferedImage prevImage = ImageIO.read(is);
//			double width = prevImage.getWidth();
//			double height = prevImage.getHeight();
//			double percent = size / width;
//			int newWidth = (int) (width * percent);
//			int newHeight = (int) (height * percent);
//
//			java.awt.Image image = prevImage.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
//			BufferedImage bufferImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
//			java.awt.Graphics graphics = bufferImage.getGraphics();
//			graphics.drawImage(image, 0, 0, null);
//			graphics.dispose();
//			ImageIO.write(bufferImage, format, os);
//			byte[] resizeImage = os.toByteArray();
//			os.write(resizeImage);
//			os.flush();
//			// is.close();
//			// os.close();
//			return resizeImage;
//		} catch (Exception ex) {
//			throw ex;
//		}
//
//	}


	//getImage , resizeImage , setImage
	public static void resizeAndSetImage(String fileName , String newfilename ,int newWidth , int newHight , String mime , String outPath)
			throws IOException{
		
		byte[] imageByte=getImage(fileName);
		
		if(imageByte==null)
			return;
		
		byte[] resizeImageByte=resizeImage(imageByte, newWidth, newHight, mime);
		
		if(resizeImageByte!=null)
			FileHandle.imageToDisk(resizeImageByte , newfilename , outPath);
	}

	//getImage
	public static byte[] getImage(String filename) throws IOException{
		
		String path = ResourceUtil.getSystemConfig().getProperty("upload_file_path");
		byte[] imageByte = null;
File file = new File(path + File.separator + filename);
if(file.exists()){
		imageByte = FileUtils.readFileToByteArray(new File(path + File.separator + filename));
}

		return imageByte;
	}

	//resizeImage
	public static byte[] resizeImage(byte[] imageByte, int newWidth, int newHight, String mime) throws IOException{
		
		if(imageByte==null)
			return null;

		try(InputStream is = new ByteArrayInputStream(imageByte);
			ByteArrayOutputStream os = new ByteArrayOutputStream();) {

			
			BufferedImage prevImage = ImageIO.read(is);
			

			Image image = prevImage.getScaledInstance(newWidth, newHight, Image.SCALE_SMOOTH);
			
			BufferedImage bufferImage = new BufferedImage(newWidth, newHight, BufferedImage.TYPE_INT_BGR);
			
			Graphics graphics = bufferImage.getGraphics();
			
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();
			
			ImageIO.write(bufferImage, mime, os);
			byte[] resizeImage = os.toByteArray();

//			os.flush();
//			is.close();
//			os.close();

			return resizeImage;
		} catch (IOException ex) {
			LOG.error("resizeImage error:" + ex.getMessage(), ex);
			throw ex;
		}catch(Exception ex){
			throw ex;
		}
	}

	//imageToDisk(image , imageName , path)
	public static void imageToDisk(byte[] resizeImageByte , String newfilename , String outPath) throws IOException{
		try(
				ByteArrayInputStream inputStream = new ByteArrayInputStream(resizeImageByte);
				FileOutputStream fos = new FileOutputStream(outPath+File.separator+newfilename);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
		)
		{
			byte[] buffer = new byte[1024];
			int len;

			while ((len = inputStream.read(buffer)) >= 0) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
			bos.close();
			inputStream.close();

		}catch(IOException ex){

			LOG.error("imageToDisk error:" + ex.getMessage(), ex);
		}
	}
	

}