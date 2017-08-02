package com.mall.b2bp.schedule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import sun.net.ftp.FtpProtocolException;
import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ErrorLogType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.supplier.SupplierModel;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.supplier.SupplierService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.brand.BrandVo;

/**
 * Created by USER on 2016/4/6.
 */
public class SupplierBrandExportJob {

	private static final Logger LOG = LoggerFactory.getLogger(SupplierBrandExportJob.class);
	


	@Resource(name = "supplierService")
	private SupplierService supplierService;
	
	@Resource(name = "brandService")
	private BrandService brandService;
	

//	private FtpService ftpService;
	
	@Resource
	private SendEmail sendEmail;

	private ErrorLog createLog(ErrorLogType errorLogType,String fileName, String log) {

		if (StringUtils.isEmpty(log))
			return null;

		ErrorLog errorLog = new ErrorLog();
		errorLog.setFileName(fileName);
		errorLog.setErrorLogType(errorLogType);
		errorLog.setCreateTime(new Date());
		errorLog.setMethodName("executeInternal");
		errorLog.add(log);

		return errorLog;
	}

	public void executeInternal() throws ServiceException {

		LOG.info("#####start interface SP-IS54SU and interface SP-IS20MD   ####");		
		
		
		List<SupplierModel> supplierList= supplierService.getSupplierByUpdatedInd();
	
		if (CollectionUtils.isEmpty(supplierList)){
			LOG.info("#####end  interface SP-IS54SU and  interface  SP-IS20MD ,empty data  ####");
			return;
		}
		
		handleSupplier(supplierList);
		handleBrand(supplierList);
		
		updateSupplierStatus(supplierList);
		
		LOG.info("#####end interface SP-IS54SU and interface SP-IS20MD   ####");
	}
	
	private void handleSupplier(List<SupplierModel> supplierList){
		List<ErrorLog> errorLogList = new ArrayList<>();
		//File Name:	IS54SU_[SysCode]_[BUCode]_[YYYYMMDDHHMISS].xml				
		//(SysCode = "SP", BUCode = "PNSHK")
		String newPath = ResourceUtil.getSystemConfig().getProperty("export_supplier_file_path");
		if(!new File(newPath).exists()){
			new File(newPath).mkdir();
		}
		
		String fileName =newPath + File.separator+ "IS54SU_SP_PNSHK"+ DateUtils.formatDate(new Date(),DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + ".xml";
		
		String archivePath = ResourceUtil.getSystemConfig().getProperty("export_supplier_file_path")+File.separator+ConstantUtil.ARCHIVE;
		File archiveDir = new File(archivePath);
		if(!archiveDir.exists()){
			archiveDir.mkdir();
		}
		
		try{
			
			buildSupplierXMLDoc(supplierList,fileName);
		} catch (Exception e) {
			errorLogList.add(createLog(ErrorLogType.IS54SU,fileName, "Throw Excepton:" + e.getMessage()));
			LOG.error(e.getMessage(), e);
		}
		
		try {
			this.sendEmail.sendErrorLogMail(errorLogList);
		} catch (UnsupportedEncodingException e) {
			LOG.error("BrandMasterJob send "+e.getMessage(), e);
		}
		
//		File file = new File(fileName);
//		List<File> fileList = new ArrayList<>();
//        fileList.add(file);
//          try {
//				if(file.exists()) {
//					ftpService.uploadFiles(fileList);
//					FileUtils.moveToDirectory(file, archiveDir, true);
//				}
//			} catch (IOException | FtpProtocolException e) {
//				LOG.error(e.getMessage(), e);
//			}
          
          
	}
	
	private void handleBrand(List<SupplierModel> supplierList){
		String newPath = ResourceUtil.getSystemConfig().getProperty("export_brand_file_path");
		if(!new File(newPath).exists()){
			new File(newPath).mkdir();
		}
		
		List<ErrorLog> errorLogList = new ArrayList<>();
		//	File Name:	IS20MD_[SysCode]_[BUCode]_[YYYYMMDDHHMISS].xml				
//		(SysCode = "SP", BUCode = "PNSHK")				
		String fileName = newPath + File.separator+"IS20MD_SP_PNSHK"+ DateUtils.formatDate(new Date(),DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + ".xml";
		
		String archivePath = ResourceUtil.getSystemConfig().getProperty("export_brand_file_path")+File.separator+ConstantUtil.ARCHIVE;
		File archiveDir = new File(archivePath);
		if(!archiveDir.exists()){
			archiveDir.mkdir();
		}
		
		
		List<BrandVo> listAll= new ArrayList<>();
		try{
			for(SupplierModel model: supplierList){
				listAll.addAll(brandService.getAllBrandsBySupplierId(model.getId()));
			}
			
			if (CollectionUtils.isEmpty(listAll)){
				return;
			}
			
			buildBrandXMLDoc(listAll,fileName);
			
		} catch (Exception e) {
			e.printStackTrace();
			errorLogList.add(createLog(ErrorLogType.IS20MD,fileName, "Throw Excepton:" + e.getMessage()));
			LOG.error(e.getMessage(), e);
		}
		
		try {
			this.sendEmail.sendErrorLogMail(errorLogList);
		} catch (UnsupportedEncodingException e) {
			LOG.error("BrandMasterJob send "+e.getMessage(), e);

		}
		
		
//		 File file = new File(fileName);
//		 List<File> fileList = new ArrayList<>();
//           fileList.add(file);
//           try {
//				if(file.exists()) {
//					ftpService.uploadFiles(fileList);
//					FileUtils.moveToDirectory(file, archiveDir, true);
//				}
//			} catch (IOException | FtpProtocolException e) {
//				LOG.error(e.getMessage(), e);
//			}
           
	}
	
	private void updateSupplierStatus(List<SupplierModel> supplierList) throws ServiceException{
		if (CollectionUtils.isNotEmpty(supplierList)){
			for(SupplierModel m:supplierList){
				SupplierModel record = new SupplierModel();
				record.setId(m.getId());
				record.setLastUpdatedDate(new Date());
				record.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				record.setUpdatedInd("N");
				supplierService.updateByPrimaryKeySelective(record);
			}
		}
		
	}
	
	
	private  String addZeroForNum(String str, int strLength) {
		if(StringUtils.isEmpty(str))
			str= "";
		 
	     int strLen = str.length();
	     StringBuffer sb = null;
	     while (strLen < strLength) {
	           sb = new StringBuffer();
	           sb.append("0").append(str);// 左(前)补0
	           str = sb.toString();
	           strLen = str.length();
	     }
	     return str;
	 }
	
	//UDA ID 3 digits + UDA value (3 digits)
	public void buildBrandXMLDoc(List<BrandVo> listAll,String fileName) throws IOException, JDOMException {
		
		 Element root = new Element("Brands");     
	        // 将根节点添加到文档中；     
	        Document Doc = new Document(root);     
	          
	        for (BrandVo model: listAll) {    
	           // 创建节点 book;     
	           Element elements = new Element("Brand");       
	           // 给 book 节点添加子节点并赋值；     
	           elements.addContent(new Element("BrandCode").setText(addZeroForNum(model.getMasterIdStr(),3)+addZeroForNum(model.getCodeStr(),3)));    
	           
	           	   Element brandInfos = new Element("BrandInfos");
	           	   
	        	   Element brandInfoEn = new Element("BrandInfo");
	        	   brandInfoEn.addContent(new Element("Language").setText("EN"));
	        	   brandInfoEn.addContent(new Element("BrandName").setText(model.getBrandNameEn()));
//	        	   brandInfoEn.addContent(new Element("BrandCategory").setText(""));
	        	   brandInfoEn.addContent(new Element("TagLine").setText(model.getBrandTaglineEn()));
	        	   brandInfoEn.addContent(new Element("BrandDescription").setText(model.getBrandDescEn()));
	        	
	        	   
	        	   Element brandInfoZT = new Element("BrandInfo");
	        	   brandInfoZT.addContent(new Element("Language").setText("ZT"));
	        	   brandInfoZT.addContent(new Element("BrandName").setText(model.getBrandNameTc()));
//	        	   brandInfoZT.addContent(new Element("BrandCategory").setText(""));
	        	   brandInfoZT.addContent(new Element("TagLine").setText(model.getBrandTaglineTc()));
	        	   brandInfoZT.addContent(new Element("BrandDescription").setText(model.getBrandDescTc()));
	        	   
	        	   Element brandInfoZH = new Element("BrandInfo");
	        	   brandInfoZH.addContent(new Element("Language").setText("ZH"));
	        	   brandInfoZH.addContent(new Element("BrandName").setText(model.getBrandNameSc()));
//	        	   brandInfoZH.addContent(new Element("BrandCategory").setText(""));
	        	   brandInfoZH.addContent(new Element("TagLine").setText(model.getBrandTaglineSc()));
	        	   brandInfoZH.addContent(new Element("BrandDescription").setText(model.getBrandDescSc()));
	        	   
	        	   brandInfos.addContent(brandInfoEn);
	        	   brandInfos.addContent(brandInfoZT);
	        	   brandInfos.addContent(brandInfoZH);
	           
	           
	           elements.addContent(brandInfos);    
	           root.addContent(elements);    
	       }    
	      
	        Format format = Format.getPrettyFormat();  
	        XMLOutputter XMLOut = new XMLOutputter(format);  
	        XMLOut.output(Doc, new FileOutputStream(fileName)); 
		
		
	}
	
	public void buildSupplierXMLDoc(List<SupplierModel> supplierList,String fileName) throws IOException, JDOMException {     
        // 创建根节点 并设置它的属性 ;     
        Element root = new Element("Suppliers");     
        // 将根节点添加到文档中；     
        Document Doc = new Document(root);     
          
        for (SupplierModel model: supplierList) {    
           // 创建节点 book;     
           Element elements = new Element("Supplier");       
           // 给 book 节点添加子节点并赋值；     
           elements.addContent(new Element("SupplierCode").setText(model.getId()));    
           elements.addContent(new Element("DeliveryCode").setText(model.getDeliveryCode()));   
           
           BigDecimal freeDeliveryThreshold= model.getFreeDeliveryThreshold();
           BigDecimal deliveryFee = model.getDeliveryFee();
           
           String freeDeliveryThresholdStr = null;
           String deliveryFeeStr = null;
           if(freeDeliveryThreshold!=null)
        	   freeDeliveryThresholdStr = decimalFormat(freeDeliveryThreshold);
           
           if(deliveryFee!=null)
        	   deliveryFeeStr = decimalFormat(deliveryFee);
           
           elements.addContent(new Element("DeliveryCharge").setText(deliveryFeeStr));    
           elements.addContent(new Element("DeliveryThreshold").setText(freeDeliveryThresholdStr));    
           elements.addContent(new Element("EffectiveDate").setText(DateUtils.formatDate(new Date(),DateUtils.DATE_FORMAT_8)));    
           
           
           	Element supplierInfos = new Element("SupplierInfos");
        	   Element supplierInfoEn = new Element("SupplierInfo");
        	   supplierInfoEn.addContent(new Element("Language").setText("EN"));
        	   supplierInfoEn.addContent(new Element("SupplierName").setText(model.getShopNameEn()));
        	   supplierInfoEn.addContent(new Element("SupplierDescription").setText(model.getShopDescEn()));
        	   supplierInfoEn.addContent(new Element("SupplierCategory").setText(""));
        	   supplierInfoEn.addContent(new Element("AddressLine1").setText(""));
        	   supplierInfoEn.addContent(new Element("AddressLine2").setText(""));
        	   supplierInfoEn.addContent(new Element("AddressLine3").setText(""));
        	   supplierInfoEn.addContent(new Element("City").setText(""));
        	   supplierInfoEn.addContent(new Element("State").setText(""));
        	   supplierInfoEn.addContent(new Element("Country").setText(""));
        	   supplierInfoEn.addContent(new Element("ContactPerson").setText(""));
        	   supplierInfoEn.addContent(new Element("Email").setText(model.getEmail()));
        	   supplierInfoEn.addContent(new Element("Fax").setText(""));
        	   supplierInfoEn.addContent(new Element("Phone").setText(""));
        	   supplierInfoEn.addContent(new Element("Remark").setText(""));
        	   
        	   Element supplierInfoZT = new Element("SupplierInfo");
        	   supplierInfoZT.addContent(new Element("Language").setText("ZT"));
        	   supplierInfoZT.addContent(new Element("SupplierName").setText(model.getShopNameTc()));
        	   supplierInfoZT.addContent(new Element("SupplierDescription").setText(model.getShopDescTc()));
        	   supplierInfoZT.addContent(new Element("SupplierCategory").setText(""));
        	   supplierInfoZT.addContent(new Element("AddressLine1").setText(""));
        	   supplierInfoZT.addContent(new Element("AddressLine2").setText(""));
        	   supplierInfoZT.addContent(new Element("AddressLine3").setText(""));
        	   supplierInfoZT.addContent(new Element("City").setText(""));
        	   supplierInfoZT.addContent(new Element("State").setText(""));
        	   supplierInfoZT.addContent(new Element("Country").setText(""));
        	   supplierInfoZT.addContent(new Element("ContactPerson").setText(""));
        	   supplierInfoZT.addContent(new Element("Email").setText(model.getEmail()));
        	   supplierInfoZT.addContent(new Element("Fax").setText(""));
        	   supplierInfoZT.addContent(new Element("Phone").setText(""));
        	   supplierInfoZT.addContent(new Element("Remark").setText(""));
        	   
        	   Element supplierInfoTc = new Element("SupplierInfo");
        	   supplierInfoTc.addContent(new Element("Language").setText("ZH"));
        	   supplierInfoTc.addContent(new Element("SupplierName").setText(model.getShopDescSc()));
        	   supplierInfoTc.addContent(new Element("SupplierDescription").setText(model.getShopDescSc()));
        	   supplierInfoTc.addContent(new Element("SupplierCategory").setText(""));
        	   supplierInfoTc.addContent(new Element("AddressLine1").setText(""));
        	   supplierInfoTc.addContent(new Element("AddressLine2").setText(""));
        	   supplierInfoTc.addContent(new Element("AddressLine3").setText(""));
        	   supplierInfoTc.addContent(new Element("City").setText(""));
        	   supplierInfoTc.addContent(new Element("State").setText(""));
        	   supplierInfoTc.addContent(new Element("Country").setText(""));
        	   supplierInfoTc.addContent(new Element("ContactPerson").setText(""));
        	   supplierInfoTc.addContent(new Element("Email").setText(model.getEmail()));
        	   supplierInfoTc.addContent(new Element("Fax").setText(""));
        	   supplierInfoTc.addContent(new Element("Phone").setText(""));
        	   supplierInfoTc.addContent(new Element("Remark").setText(""));
        	   
        	   supplierInfos.addContent(supplierInfoEn);
        	   supplierInfos.addContent(supplierInfoZT);
        	   supplierInfos.addContent(supplierInfoTc);
           
           
           elements.addContent(supplierInfos);    
           root.addContent(elements);    
       }    
      
        Format format = Format.getPrettyFormat();  
        XMLOutputter XMLOut = new XMLOutputter(format);  
        XMLOut.output(Doc, new FileOutputStream(fileName));  
    }   

	
	
	public SendEmail getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(SendEmail sendEmail) {
		this.sendEmail = sendEmail;
	}

	
	private String decimalFormat(BigDecimal b) {

		if (b == null)
			return "";
		try {
			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();

			df.applyPattern("##.##");
			return df.format(b.doubleValue());
		} catch (Exception ex) {
			return "";
		}
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public BrandService getBrandService() {
		return brandService;
	}

	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

//	public FtpService getFtpService() {
//		return ftpService;
//	}
//
//	public void setFtpService(FtpService ftpService) {
//		this.ftpService = ftpService;
//	}
	
}
