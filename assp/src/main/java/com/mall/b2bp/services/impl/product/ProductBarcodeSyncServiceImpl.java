package com.mall.b2bp.services.impl.product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.product.ProductBarcodeModelMapper;
import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ImportLogType;
import com.mall.b2bp.models.product.ProductBarcodeModel;
import com.mall.b2bp.models.product.ProductImportLogModel;
import com.mall.b2bp.services.product.ProductBarcodeSyncService;
import com.mall.b2bp.services.product.ProductImportLogService;
import com.mall.b2bp.utils.ConstantUtil;

@Service("productBarcodeSyncService")
public class ProductBarcodeSyncServiceImpl implements ProductBarcodeSyncService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProductBarcodeSyncServiceImpl.class);


	private static final String NEW_LINE_SEPARATOR = "\n";

	@Resource
	private ProductBarcodeModelMapper productBarcodeModelMapper;

	@Resource
	private ProductImportLogService productImportLogService;

	@Resource
	SendEmail sendEmail;

	@Override
	public void exportProductBarcode(File productBarcodeFile) {
		List<ProductBarcodeModel> barcodeList = productBarcodeModelMapper
				.getExportBarcodes();
		if (barcodeList != null && !barcodeList.isEmpty()) {
			CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter('|')
					.withRecordSeparator(NEW_LINE_SEPARATOR);

			try (FileWriter fileWriter = new FileWriter(productBarcodeFile);
					CSVPrinter csvPrinter = new CSVPrinter(fileWriter,
							csvFormat);) {
				Map itemMap = new HashMap();
				int productCount = 0;
				for (ProductBarcodeModel productBarcode : barcodeList) {
					if(!itemMap.containsKey(productBarcode.getProductCode())){
						productCount++;
						itemMap.put(productBarcode.getProductCode(), productBarcode.getProductCode());
					}
					
					List<String> barcodeRecord = new ArrayList<>();
					barcodeRecord.add(productBarcode.getProductCode());
					barcodeRecord.add(productBarcode.getBarcodeNum());
					barcodeRecord.add(productBarcode.getItemNumType());
					barcodeRecord.add(productBarcode.getPrimaryInd());
					csvPrinter.printRecord(barcodeRecord);

					ProductBarcodeModel updateRecord = new ProductBarcodeModel();
					updateRecord.setId(productBarcode.getId());
					updateRecord.setLastExportedDate(new Date());
					updateRecord.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
					updateRecord.setLastUpdatedDate(new Date());
					productBarcodeModelMapper
							.updateByPrimaryKeySelective(updateRecord);
				}
				
				ProductImportLogModel productImportLogModel = new ProductImportLogModel();
				productImportLogModel.setFileName(productBarcodeFile.getName());
				productImportLogModel.setImportDate(new Date());
				productImportLogModel.setImportType(ImportLogType.PRODUCT_BARCODE_EXPORT_TO_RETEK.getImportLogCode());
				productImportLogModel.setMessage("Total of " + barcodeList.size() + " barcodes of " + productCount + " products are interfaced to Retek");
				
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

	public ProductBarcodeModelMapper getProductBarcodeModelMapper() {
		return productBarcodeModelMapper;
	}

	public void setProductBarcodeModelMapper(
			ProductBarcodeModelMapper productBarcodeModelMapper) {
		this.productBarcodeModelMapper = productBarcodeModelMapper;
	}

}
