package com.mall.b2bp.services.impl.product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sun.net.ftp.FtpProtocolException;

import com.mall.b2bp.daos.product.ProductImagesModelMapper;
import com.mall.b2bp.enums.ImageType;
import com.mall.b2bp.enums.ImportLogType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.product.ProductImagesModel;
import com.mall.b2bp.models.product.ProductImportLogModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.services.ftp.FtpService;
import com.mall.b2bp.services.product.ProductImagesSyncService;
import com.mall.b2bp.services.product.ProductImportLogService;
import com.mall.b2bp.services.product.ProductInfoService;
import com.mall.b2bp.utils.ConstantUtil;

@Service("productImagesSyncService")
public class ProductImagesSyncServiceImpl implements ProductImagesSyncService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProductImagesSyncServiceImpl.class);
	

	private static final String NEW_LINE_SEPARATOR = "\n";
	 
	@Resource
	private ProductImagesModelMapper productImagesModelMapper;
	
//	@Value("${product_image_export_path}")
	private String productImageExportPath;

//	@Value("${upload_file_path}")
	private String uploadFilePath;
	
	@Resource
	private ProductImportLogService productImportLogService;
	
	@Resource
	private ProductInfoService productInfoService;
	
//	@Resource(name="productImageFtpService")
	private FtpService ftpService;
	
	@Override
	public void exportProductImage(File productImageFile) throws ServiceException {
		
		List<ProductImagesModel> imagesList = productImagesModelMapper.getExportImages();

		if(imagesList != null && !imagesList.isEmpty()){
			
			try {
				copyImages(imagesList);
			} catch (IOException | FtpProtocolException e) {
				throw new ServiceException("Copy images failed", e);
			}

			CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(',').withRecordSeparator(NEW_LINE_SEPARATOR).withQuote(null);
			try (
				FileWriter fileWriter = new FileWriter(productImageFile);
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);
			){
				Map itemMap = new HashMap();
				int productCount = 0;
				for(ProductImagesModel productImage : imagesList){
					
//					if(!itemMap.containsKey(productImage.getProductCode())){
//						productCount++;
//						itemMap.put(productImage.getProductCode(), productImage.getProductCode());
//					}
					
					List<String> imageRecord = new ArrayList<>();

					String imageType = getImageType(productImage);
					
					//item
					imageRecord.add("\""+productImage.getProductCode()+"\"");
					
					//image_type
					imageRecord.add("\""+imageType+"\"");
					
					BigDecimal syncCount = productImage.getImageSyncCount();
					int seq = 1;
					if(syncCount != null){
						seq = syncCount.intValue()+1;
					}
					//image_name
					imageRecord.add("\""+productImage.getProductCode()+ "_" + imageType + "_" + seq + ".jpg"+"\"");
					
					//image_desc
//					if(productImage.getDescription() != null){
//						imageRecord.add("\""+productImage.getDescription().replaceAll("\"", "")+"\"");
//					}else{
//						imageRecord.add("\"\"");
//					}
					imageRecord.add("\""+imageType+"\"");
					
					//status
					imageRecord.add("\""+"A"+"\"");
					
					csvPrinter.printRecord(imageRecord);
					
					ProductImagesModel updateRecord = new ProductImagesModel();
					updateRecord.setId(productImage.getId());
					updateRecord.setLastExportedDate(new Date());
                    updateRecord.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
                    updateRecord.setLastUpdatedDate(new Date());
					productImagesModelMapper.updateByPrimaryKeySelective(updateRecord);
					
					if(!itemMap.containsKey(productImage.getProductCode())){
						productCount++;
						itemMap.put(productImage.getProductCode(), productImage.getProductCode());
						
						ProductInfoModel productInfoModel = new ProductInfoModel();
						productInfoModel.setId(productImage.getProductId());
						productInfoModel.setImageSyncCount(BigDecimal.valueOf(seq));
						productInfoService.updateByPrimaryKeySelective(productInfoModel);
					}
					
				}
				
				
				ProductImportLogModel productImportLogModel = new ProductImportLogModel();
				productImportLogModel.setFileName(productImageFile.getName());
				productImportLogModel.setImportDate(new Date());
				productImportLogModel.setImportType(ImportLogType.PRODUCT_IMAGE_EXPORT_TO_RETEK.getImportLogCode());
				productImportLogModel.setMessage("Total of " + imagesList.size() + " images of " + productCount + " products are interfaced to Retek");
				
				productImportLogModel.setCreatedDate(new Date());
				productImportLogModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
				productImportLogModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				productImportLogModel.setLastUpdatedDate(new Date());
				productImportLogService.insertProductImportLog(productImportLogModel);
				
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
			
		}
		
	}
	

	
	private void copyImages(List<ProductImagesModel> imagesList) throws IOException, FtpProtocolException{
		List<File> fileList = new ArrayList();
		File exportDir = new File(productImageExportPath);
		if(!exportDir.exists()){
			exportDir.mkdirs();
		}
		for(ProductImagesModel productImage : imagesList){
			String imageType = getImageType(productImage);
			String imageFile = uploadFilePath + File.separator +  productImage.getFilePath();
			BigDecimal syncCount = productImage.getImageSyncCount();
			int seq = 1;
			if(syncCount != null){
				seq = syncCount.intValue()+1;
			}
			String newFile = productImageExportPath + File.separator + productImage.getProductCode()+ "_" + imageType + "_" + seq + ".jpg";
			File renameFile = new File(newFile);
			FileUtils.copyFile(new File(imageFile), renameFile);
			fileList.add(renameFile);
		}
		ftpService.uploadFiles(fileList);
	}
	
	private String getImageType(ProductImagesModel productImage){
		if(productImage == null){
			return null;
		}
		
		String imageType = productImage.getImageType();
		if(StringUtils.isNotEmpty(imageType) && !"PRODUCT_IMAGE".equals(imageType)){
			return "swatch";
		}else{
			int seq = 1;
			
			if(productImage.getSequence() != null){
				seq = productImage.getSequence().intValue();
			}
			
			return ImageType.getName(seq);
		}
	}
	


}
