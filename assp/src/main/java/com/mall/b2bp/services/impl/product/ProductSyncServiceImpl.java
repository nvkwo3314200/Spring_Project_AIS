package com.mall.b2bp.services.impl.product;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.product.ProductBarcodeModelMapper;
import com.mall.b2bp.daos.product.ProductImagesModelMapper;
import com.mall.b2bp.daos.product.ProductInfoModelMapper;
import com.mall.b2bp.email.SendEmail;
import com.mall.b2bp.enums.ErrorLogType;
import com.mall.b2bp.enums.ImageType;
import com.mall.b2bp.enums.ImportLogType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.models.dept.ClassModel;
import com.mall.b2bp.models.dept.DeptModel;
import com.mall.b2bp.models.dept.SubClassModel;
import com.mall.b2bp.models.product.ProductBarcodeModel;
import com.mall.b2bp.models.product.ProductImagesModel;
import com.mall.b2bp.models.product.ProductImportLogModel;
import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.populators.product.ProductPopulator;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.dept.ClassService;
import com.mall.b2bp.services.dept.DeptService;
import com.mall.b2bp.services.dept.SubClassService;
import com.mall.b2bp.services.product.ProductBarcodeService;
import com.mall.b2bp.services.product.ProductImportLogService;
import com.mall.b2bp.services.product.ProductSyncService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.ProductMasterInfoVo;
import com.mall.b2bp.vos.dept.DeptVo;
import com.mall.b2bp.vos.product.ProductBarCode;
import com.mall.b2bp.vos.product.ProductImagesVo;
import com.mall.b2bp.vos.product.ProductInfoVo;

@Service("productSyncService")
public class ProductSyncServiceImpl implements ProductSyncService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProductSyncServiceImpl.class);
	
	 private static final String [] FILE_HEADER = {"INTERFACE_IND","SUPPLIER","SUP_NAME","ORIGIN_COUNTRY_ID","ITEM","FORMAT_ID","PREFIX","DEPT","CLASS","SUBCLASS",
		 											"CATCH_WEIGHT_IND", "STANDARD_UOM", "BRAND_CHI", "BRAND_CHI_1ST", "BRAND_CHI_LEN", "BRAND_ENG", "BRAND_ENG_1ST", "BRAND_ENG_LEN",
		 											"ITEM_ENG_DESC", "ITEM_CHI_DESC", "BARCODE", "BARCODE_VALID", "BARCODE_ITEM_NUMBER_TYPE", "CASE_LENGTH", "CASE_WIDTH", "CASE_HEIGHT",
		 											"GROSS_CASE_WEIGHT", "FILLER1f", "FILLER2", "SUPP_PACK_SIZE", "INNER_PACK_SIZE", "UNIT_COST", "UNIT_RETAIL", "STORE_ORD_MULT", "DEFAULT_UOP", "STATUS",
		 											"UDA_LOV_ID_1", "UDA_LOV_VALUE_1","UDA_LOV_ID_2", "UDA_LOV_VALUE_2","UDA_LOV_ID_3", "UDA_LOV_VALUE_3","UDA_LOV_ID_4", "UDA_LOV_VALUE_4","UDA_LOV_ID_5", "UDA_LOV_VALUE_5",
		 											"UDA_LOV_ID_6", "UDA_LOV_VALUE_6","UDA_LOV_ID_7", "UDA_LOV_VALUE_7","UDA_LOV_ID_8", "UDA_LOV_VALUE_8","UDA_LOV_ID_9", "UDA_LOV_VALUE_9","UDA_LOV_ID_10", "UDA_LOV_VALUE_10",
		 											"UDA_LOV_ID_11", "UDA_LOV_VALUE_11","UDA_LOV_ID_12", "UDA_LOV_VALUE_12","UDA_LOV_ID_13", "UDA_LOV_VALUE_13","UDA_LOV_ID_14", "UDA_LOV_VALUE_14","UDA_LOV_ID_15", "UDA_LOV_VALUE_15",
		 											"UDA_LOV_ID_16", "UDA_LOV_VALUE_16","UDA_LOV_ID_17", "UDA_LOV_VALUE_17","UDA_LOV_ID_18", "UDA_LOV_VALUE_18","UDA_LOV_ID_19", "UDA_LOV_VALUE_19","UDA_LOV_ID_20", "UDA_LOV_VALUE_20",
		 											"UDA_FF_ID_1", "UDA_FF_VALUE_1","UDA_FF_ID_2", "UDA_FF_VALUE_2","UDA_FF_ID_3", "UDA_FF_VALUE_3","UDA_FF_ID_4", "UDA_FF_VALUE_4","UDA_FF_ID_5", "UDA_FF_VALUE_5",
		 											"UDA_FF_ID_6", "UDA_FF_VALUE_6","UDA_FF_ID_7", "UDA_FF_VALUE_7","UDA_FF_ID_8", "UDA_FF_VALUE_8","UDA_FF_ID_9", "UDA_FF_VALUE_9","UDA_FF_ID_10", "UDA_FF_VALUE_10",
		 											"UDA_FF_ID_11", "UDA_FF_VALUE_11","UDA_FF_ID_12", "UDA_FF_VALUE_12","UDA_FF_ID_13", "UDA_FF_VALUE_13","UDA_FF_ID_14", "UDA_FF_VALUE_14","UDA_FF_ID_15", "UDA_FF_VALUE_15",
		 											"UDA_FF_ID_16", "UDA_FF_VALUE_16","UDA_FF_ID_17", "UDA_FF_VALUE_17","UDA_FF_ID_18", "UDA_FF_VALUE_18","UDA_FF_ID_19", "UDA_FF_VALUE_19","UDA_FF_ID_20", "UDA_FF_VALUE_20",
		 											"UNIT_LENGTH", "UNIT_WIDTH", "UNIT_HEIGHT", "LEAD_TIME", "PHY_SUPP_PACK_SIZE", "PHY_INNER_PACK_SIZE", "SOURCE_METHOD", "RETURN_POLICY", "VPN", "ORIGIN_COUNTRY_ID2", "ORIGIN_COUNTRY_ID3",
		 											"ORIGIN_COUNTRY_ID4", "ORIGIN_COUNTRY_ID5", "CONSIGNMENT_RATE", "CONSIGNMENT_FROM_DATE", "CONSIGNMENT_TO_DATE", "PACK_IND", "COMP_ITEM", "PACK_QTY", "BASE_ITEM", "ITEM_SIM_CHI_DESC",
		 											"ITEM_ENG_DESC_LONG", "ITEM_CHI_DESC_LONG", "ITEM_SIM_CHI_DESC_LONG", "PRODUCT_USAGE_ENG", "PRODUCT_USAGE_CHI","PRODUCT_USAGE_SIM_CHI", "PRODUCT_WARNINGS_ENG", "PRODUCT_WARNINGS_CHI", "PRODUCT_WARNINGS_SIM_CHI",
		 											"STORAGE_CONDITION_ENG", "STORAGE_CONDITION_CHI", "STORAGE_CONDITION_SIM_CHI", "PRODUCT_INGREDIENTS_ENG", "PRODUCT_INGREDIENTS_CHI", "PRODUCT_INGREDIENTS_SIM_CHI", "GROSS_UNIT_WEIGHT",
		 											"CONSIGNMENT_TYPE", "CONSIGNMENT_CALC_BASIS", "SHIPPING_HEIGHT", "SHIPPING_WIDTH", "SHIPPING_LENGTH", "SHIPPING_STANDARD_UOM", "SHIPPING_UNIT_WEIGHT", "SHIPPING_UNIT_WEIGHT_UOM", "REPL_MIN_STOCK", "REPL_MAX_STOCK", "RECORD_TYPE_IND"
	 												};
	 
	 private static final String [] IMPORT_FILE_HEADER = {"ITEM","STATUS","ERROR"};
	 
	 private static final String [] IMAGE_FILE_HEADER = {"ITEM","IMAGE_TYPE","IMAGE_NAME","IMAGE_DESC","STATUS"};
	 
	 private static final String [] BARCODE_FILE_HEADER = {"ITEM","BARCODE","BARCODE_ITEM_NUMBER_TYPE","PRIMARY_REF_ITEM_IND","ACTION_ITEM"};

	@Resource
	private ProductInfoModelMapper productInfoMapper;
	
	@Resource
	private ProductBarcodeModelMapper productBarcodeModelMapper;
	
	@Resource
	private ProductImagesModelMapper productImagesModelMapper;

//	@Resource
//	private ProductInfoService productInfoService;
	
	@Resource
	SendEmail sendEmail;
	
	@Resource
	private ProductBarcodeService productBarcodeService;
	
	@Resource
	private ProductImportLogService productImportLogService;
	
	@Resource
	private BrandService brandService;
	
	@Resource
	private DeptService deptService;
	
	@Resource
	private ClassService classService;
	
	@Resource
	private SubClassService subclassService;
	

	private static final String NEW_LINE_SEPARATOR = "\n";

	@Override
	public void exportProductMaster(File productMasterFile) {

		CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter('|').withQuote(null)
				.withRecordSeparator(NEW_LINE_SEPARATOR);

		List<ProductInfoModel> productList = productInfoMapper
				.getExportProducts();
		
		if (productList != null && !productList.isEmpty()) {
			LOG.info("product size:"+ productList.size());
			try (
					FileWriter fileWriter = new FileWriter(productMasterFile);
					CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);
				){
				
				//print header
//				List<String> header = Arrays.asList(FILE_HEADER);
//				csvPrinter.printRecord(header);
				
				for (ProductInfoModel productInfo : productList) {
					List<String> productRecord = new ArrayList<>();
					
					boolean insertInd = true;
					if(productInfo.getRetekLastApprovedDate() != null){
						insertInd = false;
					}
					
					// INTERFACE_IND
					productRecord.add("PNSHK");

					// SUPPLIER
					productRecord.add(productInfo.getSupplierCode());

					// SUP_NAME
					if(insertInd){
						productRecord.add(productInfo.getSupplierName());
					}else{
						productRecord.add("");
					}

					// ORIGIN_COUNTRY_ID
					if(insertInd){
						productRecord.add(productInfo.getOriginCountry());
					}else{
						productRecord.add("");
					}

					// ITEM
					productRecord.add(productInfo.getProductCode());

					// FORMAT_ID
					productRecord.add("");

					// PREFIX
					productRecord.add("");

					// DEPT
					if(insertInd && productInfo.getDept() != null){
						DeptVo dept = deptService.getDeptById(productInfo.getDept());
						if(dept != null){
							productRecord.add(String.valueOf(dept.getDeptId()));
						}else{
							productRecord.add("");
						}
					}else{
						productRecord.add("");
					}
	
					// CLASS
					if(insertInd && productInfo.getClazz() != null){
						ClassModel clazz = classService.selectByPrimaryKey(productInfo.getClazz());
						if(clazz != null){
							productRecord.add(String.valueOf(clazz.getClassId()));
						}else{
							productRecord.add("");
						}
					}else{
						productRecord.add("");
					}
	
					// SUBCLASS
					if(insertInd && productInfo.getSubClass() != null){
						SubClassModel subClass = subclassService.selectByPrimaryKey(productInfo.getSubClass());
						if(subClass != null){
							productRecord.add(String.valueOf(subClass.getSubClassId()));
						}else{
							productRecord.add("");
						}
					}else{
						productRecord.add("");
					}

					// CATCH_WEIGHT_IND
					if(insertInd){
						productRecord.add("2");
					}else{
						productRecord.add("");
					}

					// STANDARD_UOM
					productRecord.add(productInfo.getStandardUom());

					BrandModel brand = brandService.selectByPrimaryKey(productInfo.getBrandCode());

					// BRAND_CHI
					if(insertInd && brand != null){
						productRecord.add(brand.getDescTc());
					}else{
						productRecord.add("");
					}
					
					// BRAND_CHI_1ST
					productRecord.add("");

					// BRAND_CHI_LEN
					productRecord.add("");

					// BRAND_ENG
					if(insertInd && brand != null){
						productRecord.add(brand.getDescEn());
					}else{
						productRecord.add("");
					}

					// BRAND_ENG_1ST
					productRecord.add("");

					// BRAND_ENG_LEN
					productRecord.add("");

					// ITEM_ENG_DESC
					productRecord.add(StringUtil.removeReturnLine(productInfo.getShortDescEn()));

					// ITEM_CHI_DESC
					productRecord.add(StringUtil.removeReturnLine(productInfo.getShortDescTc()));

					// BARCODE
					if(insertInd){
						List<ProductBarcodeModel> productBarcodeList = productBarcodeService.getProductBarcodeModelsByProductId(productInfo.getId().toString());
						if(productBarcodeList != null && !productBarcodeList.isEmpty()){
							ProductBarcodeModel productBarcodeModel= productBarcodeList.get(0);
							
							// BARCODE
							productRecord.add(productBarcodeModel.getBarcodeNum());
	
							// BARCODE_VALID
							productRecord.add("");
	
							// BARCODE_ITEM_NUMBER_TYPE
							productRecord.add(productBarcodeModel.getItemNumType());
	
						}else{
							// BARCODE
							productRecord.add("");
		
							// BARCODE_VALID
							productRecord.add("");
		
							// BARCODE_ITEM_NUMBER_TYPE
							productRecord.add("");
						}
					}else{
						// BARCODE
						productRecord.add("");
	
						// BARCODE_VALID
						productRecord.add("");
	
						// BARCODE_ITEM_NUMBER_TYPE
						productRecord.add("");
					}

					// CASE_LENGTH
					if (productInfo.getCaseDimLength() != null) {
						productRecord.add(String.valueOf(productInfo
								.getCaseDimLength()));
					} else {
						productRecord.add("");
					}

					// CASE_WIDTH
					if (productInfo.getCaseDimWidth() != null) {
						productRecord.add(String.valueOf(productInfo
								.getCaseDimWidth()));
					} else {
						productRecord.add("");
					}

					// CASE_HEIGHT
					if (productInfo.getCaseDimHeight() != null) {
						productRecord.add(String.valueOf(productInfo
								.getCaseDimHeight()));
					} else {
						productRecord.add("");
					}

					// GROSS_CASE_WEIGHT
					if (insertInd && productInfo.getGrossWeight() != null) {
						productRecord.add(String.valueOf(productInfo
								.getGrossWeight()));
					} else {
						productRecord.add("");
					}

					// FILLER1f
					productRecord.add("");

					// FILLER2
					productRecord.add("");

					// SUPP_PACK_SIZE
					if (productInfo.getCasePackCase() != null) {
						productRecord.add(String.valueOf(productInfo
								.getCasePackCase()));
					} else {
						productRecord.add("");
					}

					// INNER_PACK_SIZE
					if (productInfo.getCasePackInner() != null) {
						productRecord.add(String.valueOf(productInfo
								.getCasePackInner()));
					} else {
						productRecord.add("");
					}

					// UNIT_COST
//					if (productInfo.getUnitCost() != null) {
//						productRecord.add(String.valueOf(productInfo
//								.getUnitCost()));
//					} else {
//						productRecord.add("");
//					}
					// Unit Cost should always put 0.01
					if(insertInd){
						productRecord.add("0.01");
					}else{
						productRecord.add("");
					}
					
					// UNIT_RETAIL
					if (insertInd && productInfo.getUnitRetail() != null) {
						productRecord.add(String.valueOf(productInfo
								.getUnitRetail()));
					} else {
						productRecord.add("");
					}

					// STORE_ORD_MULT
					if(insertInd){
						productRecord.add("E");
					}else{
						productRecord.add("");
					}

					// DEFAULT_UOP
					if(insertInd){
						productRecord.add("CS");
					}else{
						productRecord.add("");
					}

					// STATUS
//					if(insertInd){
//						productRecord.add("N");
//					}else{
//						productRecord.add("");
//					}
					productRecord.add("N");

					// UDA_LOV_ID_1
					if(insertInd && StringUtils.isNotEmpty(productInfo.getColorGroup())){
						productRecord.add("524");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_1
					if(insertInd && StringUtils.isNotEmpty(productInfo.getColorGroup())){
						productRecord.add(productInfo.getColorGroup());
					}else{
						productRecord.add("");
					}

					// UDA_LOV_ID_2
					if(insertInd && "Y".equals(productInfo.getVariantColor())){
						productRecord.add("501");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_2
					if(insertInd && "Y".equals(productInfo.getVariantColor())){
						productRecord.add("1");
					}else{
						productRecord.add("");
					}
					
					// UDA_LOV_ID_3
					if(insertInd && "Y".equals(productInfo.getVariantSize())){
						productRecord.add("502");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_3
					if(insertInd && "Y".equals(productInfo.getVariantSize())){
						productRecord.add("1");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_ID_4
					productRecord.add("789");

					// UDA_LOV_VALUE_4
					productRecord.add(productInfo.getEstoreCategory());

					// UDA_LOV_ID_5
					if(insertInd && StringUtils.isNotEmpty(productInfo.getDeliveryMode())){
						productRecord.add("573");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_5
					if(insertInd){
						if("D".equals(productInfo.getDeliveryMode())){
							productRecord.add("1");
						}else if("C".equals(productInfo.getDeliveryMode()) || "S".equals(productInfo.getDeliveryMode())){
							productRecord.add("2");
						}else if("W".equals(productInfo.getDeliveryMode()) || "N".equals(productInfo.getDeliveryMode())){
							productRecord.add("3");
						}else{
							productRecord.add("");
						}
					}else{
						productRecord.add("");
					}
					

					// UDA_LOV_ID_6
					if (productInfo.getMinOrderQty() != null) {
						productRecord.add("569");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_6
					if (productInfo.getMinOrderQty() != null) {
						productRecord.add(String.valueOf(productInfo
								.getMinOrderQty()));
					}else{
						productRecord.add("");
					}
					

					// UDA_LOV_ID_7
					if (productInfo.getDailyInventory()!= null) {
						productRecord.add("784");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_7
					if (productInfo.getDailyInventory()!= null) {
						productRecord.add(String.valueOf(productInfo
								.getDailyInventory()));
					}else{
						productRecord.add("");
					}

					// UDA_LOV_ID_8
					if (productInfo.getManufCountry() != null) {
						productRecord.add("82");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_8
					if (productInfo.getManufCountry() != null) {
						productRecord.add(productInfo.getManufCountry());
					}else {
						productRecord.add("");
					}

					// UDA_LOV_ID_9
					if (StringUtils.isNotEmpty(productInfo.getPackAge())) {
						productRecord.add("776");
					}else {
						productRecord.add("");
					}

					// UDA_LOV_VALUE_9
					if (StringUtils.isNotEmpty(productInfo.getPackAge())) {
						productRecord.add(productInfo.getPackAge());
					} else {
						productRecord.add("");
					}

					// UDA_LOV_ID_10
					if(insertInd){
						productRecord.add("576");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_10
					if(insertInd){
						productRecord.add("1");
					}else{
						productRecord.add("");
					}
//					if (productInfo.getConsignmentType() != null) {
//						productRecord.add(productInfo.getConsignmentType());
//					} else {
//						productRecord.add("");
//					}

					// UDA_LOV_ID_11
					if (StringUtils.isNotEmpty(productInfo.getSmallExpensive())) {
						productRecord.add("574");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_11
//					productRecord.add(productInfo.getDimUnit());
					if("Y".equals(productInfo.getSmallExpensive())){
						productRecord.add("1");
					}else if("N".equals(productInfo.getSmallExpensive())){
						productRecord.add("0");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_ID_12
					if (StringUtils.isNotEmpty(productInfo.getNutritionLabel())) {
						productRecord.add("575");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_12
					if("E".equals(productInfo.getNutritionLabel())){
						productRecord.add("1");
					}else if("W".equals(productInfo.getNutritionLabel())){
						productRecord.add("2");
					}else if("N".equals(productInfo.getNutritionLabel())){
						productRecord.add("3");
					}else{
						productRecord.add("");
					}
					
					
//					productRecord.add(productInfo.getWeightUnit());
//					if (productInfo.getShippingWeight() != null) {
//						productRecord.add(productInfo.getShippingWeight()
//								.toString());
//					} else {
//						productRecord.add("");
//					}

					// UDA_LOV_ID_13
					if (StringUtils.isNotEmpty(productInfo.getPnsOnlineDeliveryType())) {
						productRecord.add("782");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_13
					if (StringUtils.isNotEmpty(productInfo.getPnsOnlineDeliveryType())) {
						productRecord.add(productInfo.getPnsOnlineDeliveryType());
					}else{
						productRecord.add("");
					}

					// UDA_LOV_ID_14
					if(insertInd){
						productRecord.add("36");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_14
					if(insertInd){
						productRecord.add("92");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_ID_15
					if(insertInd){
						productRecord.add("28");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_VALUE_15
					if(insertInd){
						productRecord.add("1");
					}else{
						productRecord.add("");
					}

					// UDA_LOV_ID_16
					productRecord.add("");

					// UDA_LOV_VALUE_16
					productRecord.add("");

					// UDA_LOV_ID_17
					productRecord.add("");

					// UDA_LOV_VALUE_17
					productRecord.add("");

					// UDA_LOV_ID_18
					productRecord.add("");

					// UDA_LOV_VALUE_18
					productRecord.add("");

					// UDA_LOV_ID_19
					productRecord.add("");

					// UDA_LOV_VALUE_19
					productRecord.add("");

					// UDA_LOV_ID_20
					productRecord.add("");

					// UDA_LOV_VALUE_20
					productRecord.add("");

					// UDA_FF_ID_1
					if(insertInd){
						productRecord.add("618");
					}else{
						productRecord.add("");
					}

					// UDA_FF_VALUE_1
					if(insertInd){
						productRecord.add("Y");
					}else{
						productRecord.add("");
					}

					// UDA_FF_ID_2
					if (StringUtils.isNotEmpty(productInfo.getSizeDesc())) {
						productRecord.add("27");
					}else{
						productRecord.add("");
					}

					// UDA_FF_VALUE_2
					if (StringUtils.isNotEmpty(productInfo.getSizeDesc())) {
						productRecord.add(productInfo.getSizeDesc());
					}else{
						productRecord.add("");
					}

					// UDA_FF_ID_3
					if (insertInd && StringUtils.isNotEmpty(productInfo.getColorCode())) {
						productRecord.add("521");
					}else{
						productRecord.add("");
					}

					// UDA_FF_VALUE_3
					if (insertInd && StringUtils.isNotEmpty(productInfo.getColorCode())) {
						productRecord.add(productInfo.getColorCode());
					}else{
						productRecord.add("");
					}

					// UDA_FF_ID_4
					if (insertInd && StringUtils.isNotEmpty(productInfo.getColorDesc())) {
						productRecord.add("522");
					}else{
						productRecord.add("");
					}

					// UDA_FF_VALUE_4
					if (insertInd && StringUtils.isNotEmpty(productInfo.getColorDesc())) {
						productRecord.add(productInfo.getColorDesc());
					}else{
						productRecord.add("");
					}

					// UDA_FF_ID_5
					if (insertInd && StringUtils.isNotEmpty(productInfo.getColorHexCode())) {
						productRecord.add("523");
					}else{
						productRecord.add("");
					}

					// UDA_FF_VALUE_5
					if (insertInd && StringUtils.isNotEmpty(productInfo.getColorHexCode())) {
						productRecord.add(productInfo.getColorHexCode());
					}else{
						productRecord.add("");
					}

					// UDA_FF_ID_6
					if (productInfo.getMinDeliverDate() != null) {
						productRecord.add("785");
					}else{
						productRecord.add("");
					}

					// UDA_FF_VALUE_6
					if (productInfo.getMinDeliverDate() != null) {
						productRecord.add(productInfo.getMinDeliverDate()
								.toString());
					} else {
						productRecord.add("");
					}

					// UDA_FF_ID_7
					if (productInfo.getMaxDeliverDate() != null) {
						productRecord.add("786");
					} else {
						productRecord.add("");
					}

					// UDA_FF_VALUE_7
					if (productInfo.getMaxDeliverDate() != null) {
						productRecord.add(productInfo.getMaxDeliverDate()
								.toString());
					} else {
						productRecord.add("");
					}

					// UDA_FF_ID_8
					if (productInfo.getOnlineDate() != null) {
						productRecord.add("571");
					} else {
						productRecord.add("");
					}

					// UDA_FF_VALUE_8
					if (productInfo.getOnlineDate() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						productRecord.add(sdf.format(productInfo
								.getOnlineDate()));
					} else {
						productRecord.add("");
					}

					// UDA_FF_ID_9
					if (productInfo.getOfflineDate() != null) {
						productRecord.add("572");
					}else {
						productRecord.add("");
					}

					// UDA_FF_VALUE_9
					if (productInfo.getOfflineDate() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						productRecord.add(sdf.format(productInfo
								.getOfflineDate()));
					} else {
						productRecord.add("");
					}

					// UDA_FF_ID_10
					if (StringUtils.isNotEmpty(productInfo.getProductShelfLife())) {
						productRecord.add("45");
					}else {
						productRecord.add("");
					}

					// UDA_FF_VALUE_10
					if (StringUtils.isNotEmpty(productInfo.getProductShelfLife())) {
						productRecord.add(productInfo.getProductShelfLife());
					}else {
						productRecord.add("");
					}

					// UDA_FF_ID_11
					if (StringUtils.isNotEmpty(productInfo.getMinShelfLife())) {
						productRecord.add("35");
					}else {
						productRecord.add("");
					}

					// UDA_FF_VALUE_11
					if (StringUtils.isNotEmpty(productInfo.getMinShelfLife())) {
						productRecord.add(productInfo.getMinShelfLife());
					}else {
						productRecord.add("");
					}

					// UDA_FF_ID_12
					if (StringUtils.isNotEmpty(productInfo.getShippingInfo())) {
						productRecord.add("530");
					}else {
						productRecord.add("");
					}

					// UDA_FF_VALUE_12
					if (StringUtils.isNotEmpty(productInfo.getShippingInfo())) {
						productRecord.add(productInfo.getShippingInfo());
					}else {
						productRecord.add("");
					}

					// UDA_FF_ID_13
					productRecord.add("");

					// UDA_FF_VALUE_13
					productRecord.add("");

					// UDA_FF_ID_14
					productRecord.add("");

					// UDA_FF_VALUE_14
					productRecord.add("");

					// UDA_FF_ID_15
					productRecord.add("");

					// UDA_FF_VALUE_15
					productRecord.add("");

					// UDA_FF_ID_16
					productRecord.add("");

					// UDA_FF_VALUE_16
					productRecord.add("");

					// UDA_FF_ID_17
					productRecord.add("");

					// UDA_FF_VALUE_17
					productRecord.add("");

					// UDA_FF_ID_18
					productRecord.add("");

					// UDA_FF_VALUE_18
					productRecord.add("");

					// UDA_FF_ID_19
					productRecord.add("");

					// UDA_FF_VALUE_19
					productRecord.add("");

					// UDA_FF_ID_20
					productRecord.add("");

					// UDA_FF_VALUE_20
					productRecord.add("");

					// UNIT_LENGTH
					if (productInfo.getProductDimLength() != null) {
						productRecord.add(productInfo.getProductDimLength()
								.toString());
					} else {
						productRecord.add("");
					}

					// UNIT_WIDTH
					if (productInfo.getProductDimWidth() != null) {
						productRecord.add(productInfo.getProductDimWidth()
								.toString());
					} else {
						productRecord.add("");
					}

					// UNIT_HEIGHT
					if (productInfo.getProductDimHeight() != null) {
						productRecord.add(productInfo.getProductDimHeight()
								.toString());
					} else {
						productRecord.add("");
					}

					// LEAD_TIME
					if (productInfo.getSupplierLeadTime() != null) {
						productRecord.add(String.valueOf(productInfo
								.getSupplierLeadTime()));
					} else {
						productRecord.add("");
					}

					// PHY_SUPP_PACK_SIZE
					productRecord.add("");

					// PHY_INNER_PACK_SIZE
					productRecord.add("");

					// SOURCE_METHOD
					productRecord.add("");

					// RETURN_POLICY
					productRecord.add("");

					// VPN
					if(insertInd){
						productRecord.add(productInfo.getSupplierProductCode());
					}else{
						productRecord.add("");
					}

					// ORIGIN_COUNTRY_ID2
					productRecord.add("");

					// ORIGIN_COUNTRY_ID3
					productRecord.add("");

					// ORIGIN_COUNTRY_ID4
					productRecord.add("");

					// ORIGIN_COUNTRY_ID5
					productRecord.add("");

					// CONSIGNMENT_RATE
					if (insertInd && productInfo.getConsignmentRate() != null) {
						productRecord.add(productInfo.getConsignmentRate()
								.toString());
					} else {
						productRecord.add("");
					}

					// CONSIGNMENT_FROM_DATE
					productRecord.add("");

					// CONSIGNMENT_TO_DATE
					productRecord.add("");

					// PACK_IND
					productRecord.add("");

					// COMP_ITEM
					productRecord.add("");

					// PACK_QTY
					productRecord.add("");

					// BASE_ITEM
					if(insertInd){
						if(productInfo.getBaseProductId() != null){
							ProductInfoModel productModel = productInfoMapper.selectByPrimaryKey(productInfo.getBaseProductId());
							if(productModel != null){
								productRecord.add(productModel.getProductCode());
							}else{
								productRecord.add("");
							}
						}else{
							productRecord.add(productInfo.getProductCode());
						}
					}else{
						productRecord.add("");
					}

					// ITEM_SIM_CHI_DESC
					productRecord.add(StringUtil.removeReturnLine(productInfo.getShortDescSc()));

					// ITEM_ENG_DESC_LONG
					productRecord.add(StringUtil.removeReturnLine(productInfo.getLongDescEn()));

					// ITEM_CHI_DESC_LONG
					productRecord.add(StringUtil.removeReturnLine(productInfo.getLongDescTc()));

					// ITEM_SIM_CHI_DESC_LONG
					productRecord.add(StringUtil.removeReturnLine(productInfo.getLongDescSc()));

					// PRODUCT_USAGE_ENG
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductUsageEn()));

					// PRODUCT_USAGE_CHI
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductUsageTc()));

					// PRODUCT_USAGE_SIM_CHI
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductUsageSc()));

					// PRODUCT_WARNINGS_ENG
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductWarningsEn()));

					// PRODUCT_WARNINGS_CHI
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductWarningsTc()));

					// PRODUCT_WARNINGS_SIM_CHI
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductWarningsSc()));

					// STORAGE_CONDITION_ENG
					productRecord.add(StringUtil.removeReturnLine(productInfo.getStorageConditionEn()));

					// STORAGE_CONDITION_CHI
					productRecord.add(StringUtil.removeReturnLine(productInfo.getStorageConditionTc()));

					// STORAGE_CONDITION_SIM_CHI
					productRecord.add(StringUtil.removeReturnLine(productInfo.getStorageConditionSc()));

					// PRODUCT_INGREDIENTS_ENG
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductIngredientsEn()));

					// PRODUCT_INGREDIENTS_CHI
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductIngredientsTc()));

					// PRODUCT_INGREDIENTS_SIM_CHI
					productRecord.add(StringUtil.removeReturnLine(productInfo.getProductIngredientsSc()));

					// GROSS_UNIT_WEIGHT
					if (productInfo.getGrossWeight() != null) {
						productRecord.add(productInfo.getGrossWeight()
								.toString());
					} else {
						productRecord.add("");
					}

					// CONSIGNMENT_TYPE
					if(insertInd){
						productRecord.add(productInfo.getConsignmentType());
					}else{
						productRecord.add("");
					}

					// CONSIGNMENT_CALC_BASIS
					if(insertInd){
						if("G".equalsIgnoreCase(productInfo.getConsignmentCalBasis())){
							productRecord.add("1");
						}else if("N".equalsIgnoreCase(productInfo.getConsignmentCalBasis())){
							productRecord.add("2");
						}else{
							productRecord.add("");
						}
					}else{
						productRecord.add("");
					}

					// SHIPPING_HEIGHT
					if (productInfo.getShippingDimHeight() != null) {
						productRecord.add(productInfo.getShippingDimHeight()
								.toString());
					} else {
						productRecord.add("");
					}

					// SHIPPING_WIDTH
					if (productInfo.getShippingDimWidth() != null) {
						productRecord.add(productInfo.getShippingDimWidth()
								.toString());
					} else {
						productRecord.add("");
					}

					// SHIPPING_LENGTH
					if (productInfo.getShippingDimLength() != null) {
						productRecord.add(productInfo.getShippingDimLength()
								.toString());
					} else {
						productRecord.add("");
					}

					// SHIPPING_STANDARD_UOM
					productRecord.add(productInfo.getDimUnit());

					// SHIPPING_UNIT_WEIGHT
					if (productInfo.getShippingWeight() != null) {
						productRecord.add(productInfo.getShippingWeight()
								.toString());
					} else {
						productRecord.add("");
					}

					// SHIPPING_UNIT_WEIGHT_UOM
					productRecord.add(productInfo.getWeightUnit());
					
					//REPL_MIN_STOCK
					if(productInfo.getMinReplenishmentLevel() != null){
						productRecord.add(productInfo.getMinReplenishmentLevel().toString());
					}else{
						productRecord.add("");
					}
					
					//REPL_MAX_STOCK
					if(productInfo.getMaxReplenishmentLevel() != null){
						productRecord.add(productInfo.getMaxReplenishmentLevel().toString());
					}else{
						productRecord.add("");
					}

					if(insertInd){
						productRecord.add("C");
					}else{
						productRecord.add("U");
					}

					ProductInfoModel updateProduct = productInfoMapper.selectByPrimaryKey(productInfo.getId());
					updateProduct.setId(productInfo.getId());
					updateProduct.setLastExportedDate(new Date());

                    updateProduct.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
                    updateProduct.setLastUpdatedDate(new Date());
					productInfoMapper
							.updateByPrimaryKeySelective(updateProduct);
					csvPrinter.printRecord(productRecord);
				}
				
				ProductImportLogModel productImportLogModel = new ProductImportLogModel();
				productImportLogModel.setFileName(productMasterFile.getName());
				productImportLogModel.setImportDate(new Date());
				productImportLogModel.setImportType(ImportLogType.PRODUCT_MASTER_EXPORT_TO_RETEK.getImportLogCode());
				productImportLogModel.setMessage("Total of "+ productList.size() +" products are interfaced to Retek");
				
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

	
//	//import data from csv
//	@Override
//	public boolean importProductMastor(String fileName,ErrorLog errorLog) throws ServiceException{
//		boolean flag=true;
//		List<ProductInfoVo> list=readImportCsvFile(fileName);
//
//		errorLog.setCreateTime(new Date());
//		errorLog.setErrorLogType(ErrorLogType.PRODUCT_MASTER_SYNC_IMPORT);
//		errorLog.setFileName(fileName);
//		errorLog.setMethodName("ProductSyncServiceImpl.importProductMastor");
//		if(list!=null && !list.isEmpty()){
//
//			int accepted=0;
//			int failed=0;
//			for (ProductInfoVo productInfoVo : list) {
//				Map map=new HashMap();
//				map.put("version", ConstantUtil.VERSION_STAGING);
//				map.put("productCode", productInfoVo.getProductCode());
//				ProductInfoModel productInfoModel=productInfoMapper.getProductInfoModelByProductCode(map);
//				if(productInfoModel==null){
//					errorLog.add("can not find product by version:["+ConstantUtil.VERSION_STAGING+"] product code:["+productInfoVo.getProductCode()+"]");
//					flag= false;
//					continue;
//				}
//				ProductInfoModel newModel=new ProductInfoModel();
//				newModel.setId(productInfoModel.getId());
//					Date date=new Date();
//					newModel.setLastUpdatedDate(date);
//					newModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
//					newModel.setRetekLastUpdatedDate(date);
//					newModel.setFailedReason(productInfoVo.getFailedReason());
//					newModel.setRetekStatus(productInfoVo.getStatus());
//					if(ConstantUtil.PRODUCT_STATUS_APPROVED.equals(productInfoModel.getStatus())){
//					if(ConstantUtil.PRODUCT_STATUS_RETEK_A.equals(productInfoVo.getStatus())){
//						newModel.setStatus(ConstantUtil.PRODUCT_STATUS_APPROVED_IN_RETEK);
//						accepted++;
//					}else if(ConstantUtil.PRODUCT_STATUS_RETEK_F.equals(productInfoVo.getStatus())){
//						newModel.setStatus(ConstantUtil.PRODUCT_STATUS_UNAPPROVED_IN_RETEK);
//						failed++;
//					}
//					}
//					
//					try{
//					productInfoMapper.updateByPrimaryKeySelective(newModel);
//				}catch (Exception e){
//					LOG.error(e.getMessage(),e);
//					errorLog.add("Throw Excepton:"+e.getMessage());
//					return false;
//				}
//				
//			}
//			
//			ProductImportLogModel productImportLogModel = new ProductImportLogModel();
//			productImportLogModel.setFileName(fileName);
//			productImportLogModel.setImportDate(new Date());
//			productImportLogModel.setImportType(ImportLogType.PRODUCT_MASTER_ACK_FROM_RETEK.getImportLogCode());
//			productImportLogModel.setMessage("Out of "+list.size()+" products interfaced to Retek,"+accepted+" are accepted and "+failed+" arefailed");
//
//			productImportLogModel.setCreatedDate(new Date());
//			productImportLogModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
//			productImportLogModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
//			productImportLogModel.setLastUpdatedDate(new Date());
//			try{
//			productImportLogService.insertProductImportLog(productImportLogModel);
//			}catch (Exception e){
//				LOG.error(e.getMessage(),e);
//				errorLog.add("Throw Excepton:"+e.getMessage());
//				return false;
//			}
//			
//		}
//		
//
//		return flag;
//		
//	}
	

	//import data from csv
	@Override
	public boolean importProductMastor(String fileName,ErrorLog errorLog) throws ServiceException{
		boolean flag=true;
		List<ProductMasterInfoVo> list=readCsvFile(fileName);

		errorLog.setCreateTime(new Date());
		errorLog.setErrorLogType(ErrorLogType.PRODUCT_MASTER_SYNC_IMPORT);
		errorLog.setFileName(fileName);
		errorLog.setMethodName("ProductSyncServiceImpl.importProductMastor");
		if(list!=null && !list.isEmpty()){

			int productTotal=0;
			int productAccepted=0;
			int productFailed=0;
			
			int imageTotal=0;
			int imageAccepted=0;
			int imageFailed=0;
			
			int barcodeTotal=0;
			int barcodeAccepted=0;
			int barcodeFailed=0;
			
			Map itemMap = new HashMap();
			
			Map<String, String> reasonMap = new HashMap();
			for (ProductMasterInfoVo productInfoVo : list) {
				if ("PM".equals(productInfoVo.getAckType())) {
					if (ConstantUtil.PRODUCT_STATUS_RETEK_F
							.equals(productInfoVo.getStatus()) || ConstantUtil.PRODUCT_STATUS_RETEK_P
							.equals(productInfoVo.getStatus())) {
						if(reasonMap.get(productInfoVo.getItem()) != null){
							String reason = reasonMap.get(productInfoVo.getItem()) + "<br/>" + productInfoVo.getError();
							reasonMap.put(productInfoVo.getItem(), reason);
						}else{
							reasonMap.put(productInfoVo.getItem(), productInfoVo.getError());
						}
					} 
				}
			}
			
			for (ProductMasterInfoVo productInfoVo : list) {
				boolean sameItem = false;
				String key = productInfoVo.getAckType() + "_" + productInfoVo.getItem() + "_" + productInfoVo.getStatus();
				if(!itemMap.containsKey(key)){
					itemMap.put(key, key);
					sameItem = false;
				}else{
					sameItem = true;
				}
				
				boolean handle=false;
				if ("PM".equals(productInfoVo.getAckType())) {
					if(!sameItem){
						productTotal++;
					}
					
					handle = true;
					Map map = new HashMap();
					map.put("version", ConstantUtil.VERSION_STAGING);
					map.put("productCode", productInfoVo.getItem());
					ProductInfoModel productInfoModel = productInfoMapper
							.getProductInfoModelByProductCode(map);
					if (productInfoModel == null) {
						errorLog.add("can not find product by version:["
								+ ConstantUtil.VERSION_STAGING
								+ "] product code:[" + productInfoVo.getItem()
								+ "]");
						flag = false;
						continue;
					}
					

					
					ProductInfoModel newModel = new ProductInfoModel();
					newModel.setId(productInfoModel.getId());
					Date date = new Date();
					newModel.setLastUpdatedDate(date);
					newModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
					newModel.setRetekLastUpdatedDate(date);
					newModel.setFailedReason(reasonMap.get(productInfoVo.getItem()));
					newModel.setRetekStatus(productInfoVo.getStatus());
					if (ConstantUtil.PRODUCT_STATUS_APPROVED
							.equals(productInfoModel.getStatus())
							|| "AUTO_APPROVED".equals(productInfoModel
									.getStatus())) {
						if (ConstantUtil.PRODUCT_STATUS_RETEK_A
								.equals(productInfoVo.getStatus())) {
							newModel.setStatus(ConstantUtil.PRODUCT_STATUS_APPROVED_IN_RETEK);
							newModel.setRetekLastApprovedDate(new Date());
							if(!sameItem){
								productAccepted++;
							}
						} else if (ConstantUtil.PRODUCT_STATUS_RETEK_F
								.equals(productInfoVo.getStatus())) {
							newModel.setStatus(ConstantUtil.PRODUCT_STATUS_UNAPPROVED_IN_RETEK);
//							newModel.setFailedReason(reasonMap.get(productInfoVo.getItem()));
							if(!sameItem){
								productFailed++;
							}
						} 		
						else if (ConstantUtil.PRODUCT_STATUS_RETEK_P
								.equals(productInfoVo.getStatus())) {
							newModel.setStatus(ConstantUtil.PRODUCT_STATUS_UNAPPROVED_IN_RETEK);
							newModel.setRetekLastApprovedDate(new Date());
							if(!sameItem){
								productFailed++;
							}
						}
					}else{
						if (ConstantUtil.PRODUCT_STATUS_RETEK_A
								.equals(productInfoVo.getStatus()) || ConstantUtil.PRODUCT_STATUS_RETEK_P
								.equals(productInfoVo.getStatus())) {
							newModel.setRetekLastApprovedDate(new Date());
						}
					}

					try {
						productInfoMapper.updateByPrimaryKeySelective(newModel);
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
						errorLog.add("Throw Excepton:" + e.getMessage());
						return false;
					}
				}
				
				if("BC".equals(productInfoVo.getAckType())){
					if(!sameItem){
						barcodeTotal++;
					}
					handle=true;
					Map map=new HashMap();
					map.put("version", ConstantUtil.VERSION_STAGING);
					map.put("productCode", productInfoVo.getItem());
					map.put("barcodenum", productInfoVo.getBarcode());
					ProductBarcodeModel productBarcodeModel=productBarcodeModelMapper.getProductBarcodeByProductCode(map);
					if(productBarcodeModel==null){
						errorLog.add("can not find product barcode by version:["+ConstantUtil.VERSION_STAGING+"] product code:["+productInfoVo.getItem()+"] barcodenum:["+productInfoVo.getBarcode()+"]");

						flag= false;
						continue;
					}
					
					ProductBarcodeModel newModel=new ProductBarcodeModel();
					newModel.setId(productBarcodeModel.getId());
					newModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
					newModel.setLastUpdatedDate(new Date());
					newModel.setFailedReason(productInfoVo.getError());
						
					
						if(ConstantUtil.PRODUCT_STATUS_RETEK_A.equals(productInfoVo.getStatus())){
							newModel.setStatus(ConstantUtil.PRODUCT_STATUS_APPROVED_IN_RETEK);
							if(!sameItem){
								barcodeAccepted++;
							}
						}else if(ConstantUtil.PRODUCT_STATUS_RETEK_F.equals(productInfoVo.getStatus())){
							newModel.setStatus(ConstantUtil.PRODUCT_STATUS_UNAPPROVED_IN_RETEK);
							if(!sameItem){
								barcodeFailed++;
							}
						}
						try{
							productBarcodeModelMapper.updateByPrimaryKeySelective(newModel);
						}catch (Exception e){
							LOG.error(e.getMessage(),e);
							errorLog.add("Throw Excepton:"+e.getMessage());
							return false;
						}
				}
				
				if("IM".equals(productInfoVo.getAckType())){
					if(!sameItem){
						imageTotal++;
					}
					handle=true;
					Map map=new HashMap();
					map.put("version", ConstantUtil.VERSION_STAGING);
					map.put("productCode", productInfoVo.getItem());
					String name = productInfoVo.getImageFileName();
					String fileType = name.substring(name.indexOf("_")+1, name.lastIndexOf("."));
					if(fileType.indexOf("_")>-1){
						fileType = fileType.substring(0, fileType.indexOf("_"));
					}
					int seq = getImageSeq(fileType);
					map.put("seq", seq);
					String imageType = "PRODUCT_IMAGE";
					if("swatch".equals(fileType)){
						imageType = "SWATCH_IMAGE";
					}
					map.put("imageType", imageType);
					ProductImagesModel productInfoModel=productImagesModelMapper.getProductImagesByProductCode(map);
					if(productInfoModel==null)
					{
						LOG.info("can not find product image by version:["+ConstantUtil.VERSION_STAGING+"] product code:["+productInfoVo.getItem()+"] seq:["+seq+"]");
						errorLog.add("can not find product image by version:["+ConstantUtil.VERSION_STAGING+"] product code:["+productInfoVo.getItem()+"] seq:["+seq+"]");
						flag= false;
						continue;
					}
					
					ProductImagesModel newModel=new ProductImagesModel();
					newModel.setId(productInfoModel.getId());
					newModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
					newModel.setLastUpdatedDate(new Date());
					newModel.setFailedReason(productInfoVo.getError());
						
						if(ConstantUtil.PRODUCT_STATUS_RETEK_A.equals(productInfoVo.getStatus())){
							newModel.setStatus(ConstantUtil.PRODUCT_STATUS_APPROVED_IN_RETEK);
							if(!sameItem){
								imageAccepted++;
							}
						}
						
						if(ConstantUtil.PRODUCT_STATUS_RETEK_F.equals(productInfoVo.getStatus())){
							newModel.setStatus(ConstantUtil.PRODUCT_STATUS_UNAPPROVED_IN_RETEK);
							if(!sameItem){
								imageFailed++;
							}
						}
						
					try{
						productImagesModelMapper.updateByPrimaryKeySelective(newModel);
					}catch (Exception e){
						LOG.error(e.getMessage(),e);
						errorLog.add("Throw Excepton:"+e.getMessage());
						return false;
					}
				}
				
				if(!handle){
					errorLog.add("Invalid AckType:["+productInfoVo.getAckType()+"]");
					flag= false;
				}
				
				
			}
			
			ProductImportLogModel productImportLogModel = new ProductImportLogModel();
			if(fileName.lastIndexOf(File.separator)>-1){
				productImportLogModel.setFileName(fileName.substring(fileName.lastIndexOf(File.separator) + 1));
			}else{
				productImportLogModel.setFileName(fileName);
			}
			productImportLogModel.setImportDate(new Date());
			productImportLogModel.setImportType(ImportLogType.PRODUCT_MASTER_ACK_FROM_RETEK.getImportLogCode());
			String productMessage = "Out of "+productTotal+" products interfaced to Retek, "+productAccepted+" are accepted and "+productFailed+" are failed;";
			String imageMessage = "Out of "+imageTotal+" images interfaced to Retek, "+imageAccepted+" are accepted and "+imageFailed+" are failed;";
			String barcodeMessage = "Out of "+barcodeTotal+" barcodes interfaced to Retek, "+barcodeAccepted+" are accepted and "+barcodeFailed+" are failed;";
			
			StringBuffer msg = new StringBuffer();
			if(productTotal > 0){
				msg.append(productMessage);
			}
			
			if(imageTotal > 0){
				msg.append(imageMessage);
			}
			
			if(barcodeTotal > 0){
				msg.append(barcodeMessage);
			}
			
			productImportLogModel.setMessage(msg.toString());

			productImportLogModel.setCreatedDate(new Date());
			productImportLogModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
			productImportLogModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
			productImportLogModel.setLastUpdatedDate(new Date());
			try{
				productImportLogService.insertProductImportLog(productImportLogModel);
			}catch (Exception e){
				LOG.error(e.getMessage(),e);
				errorLog.add("Throw Excepton:"+e.getMessage());
				return false;
			}
			
		}
		

		return flag;
		
	}
	
	
	private int getImageSeq(String fileType){
		if("swatch".equals(fileType)){
			return 1;
		}else{			
			return ImageType.getIndex(fileType);
		}
	}
	
//     
//	    /**
//	     * @param fileName
//	     */
//	
//	 private List<ProductInfoVo> readImportCsvFile(String fileName) {
//	    	List<ProductInfoVo> list=new ArrayList<>();
//
//	        ProductInfoVo productInfo=null;
//	        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(IMPORT_FILE_HEADER);
//	        try(
//	        		FileReader fileReader = new FileReader(fileName);
//	        		CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat);
//	        ){
//	            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
//	            for (int i = 1; i < csvRecords.size(); i++) {
//	                CSVRecord record = csvRecords.get(i);
//	                productInfo=new ProductInfoVo();
//	                productInfo.setProductCode(record.get(0));
//	                productInfo.setStatus(record.get(1));
//	                productInfo.setFailedReason(record.get(2));
//	                list.add(productInfo);
//	                
//	            }
//	       
//	        } 
//	        catch (Exception e) {
//	            LOG.error(e.getMessage(),e);
//	        }
//			return list;
//	    }
	 
	 /**
	  * @param fileName
	  */
	 
	 private List<ProductMasterInfoVo> readCsvFile(String fileName) {
		 List<ProductMasterInfoVo> list=new ArrayList<>();
		 
		 ProductMasterInfoVo productInfo=null;
		 CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(IMPORT_FILE_HEADER).withDelimiter('|');
		 try(
				 FileReader fileReader = new FileReader(fileName);
				 CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat);
		 ){
			 List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
			 for (int i = 0; i < csvRecords.size(); i++) {
				 CSVRecord record = csvRecords.get(i);
				 productInfo=new ProductMasterInfoVo();
				 productInfo.setItem(record.get(0));
				 productInfo.setAckType(record.get(1));
				 productInfo.setStatus(record.get(2));
				 productInfo.setBarcode(record.get(3));
				 productInfo.setImageFileName(record.get(4));
				 productInfo.setError(record.get(5));
				 list.add(productInfo);
				 
			 }
			 
		 } 
		 catch (Exception e) {
			 LOG.error(e.getMessage(),e);
		 }
		 return list;
	 }

	 
		@Override
		public boolean importProductMasterFromRetek(String fileName) {
			List<ProductInfoVo> productInfoVos = null;
			try {
				productInfoVos = readImportCsvFileFromRetek(fileName);
				if(CollectionUtils.isNotEmpty(productInfoVos)){
					insertProductFormRetek(productInfoVos);
				}else{
					return false;	
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
			return true;	
		}
		
		
		private List<ProductInfoVo> readImportCsvFileFromRetek(String fileName) {
	    	List<ProductInfoVo> list= null;
	        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withDelimiter('|');
	        try(
        		FileReader fileReader = new FileReader(fileName);
        		CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat);
	        ){
	            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
	            list = getProductInvoListFromCsv(csvRecords);
	        } 
	        catch (Exception e) {
	            LOG.error(e.getMessage(),e);
	        }
			return list;
	    }
		
		private List<ProductBarCode> readBarcodeImportCsvFileFromRetek(String fileName,ErrorLog errorLog) {
	    	List<ProductBarCode> list= null;
	        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(BARCODE_FILE_HEADER);
	        try(
        		FileReader fileReader = new FileReader(fileName);
        		CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat);
	        ){
	        	List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
	            list = getProductBarcodeListFromCsv(csvRecords);
	        } 
	        catch (Exception e) {
	            LOG.error(e.getMessage(),e);
	            errorLog.add(e.getMessage());
	        }
			return list;
	    }
		
	 private List<ProductInfoVo> getProductInvoListFromCsv( List<CSVRecord> csvRecords){
		 List<ProductInfoVo> list=new ArrayList<>();
		 if(CollectionUtils.isNotEmpty(csvRecords)){
//			 int i=0;
			  for(CSVRecord csvRecord : csvRecords){
//				  if(i==0){
//					  i++;
//					  continue;
//				  }
				  ProductInfoVo productInfoVo = new ProductInfoVo();
				  //SUPPLIER
				  productInfoVo.setSupplierCode(csvRecord.get("SUPPLIER"));
				  //ORIGIN_COUNTRY_ID
				  productInfoVo.setOriginCountry(csvRecord.get("ORIGIN_COUNTRY_ID"));
				  //ITEM
				  productInfoVo.setProductCode(csvRecord.get("ITEM"));
				  //DEPT
				  if(StringUtils.isNotEmpty(csvRecord.get("DEPT"))){
					  productInfoVo.setDept(new BigDecimal(csvRecord.get("DEPT")));
				  }
				  //CLASS
				  if(StringUtils.isNotEmpty(csvRecord.get("CLASS"))){
					  productInfoVo.setClazz(new BigDecimal(csvRecord.get("CLASS")));
				  }
				  //SUBCLASS
				  if(StringUtils.isNotEmpty(csvRecord.get("SUBCLASS"))){
					  productInfoVo.setSubClass(new BigDecimal(csvRecord.get("SUBCLASS")));
				  }
				  //CATCH_WEIGHT_IND
				  if(StringUtils.isNotEmpty(csvRecord.get("CATCH_WEIGHT_IND"))){
					  if("Y".equals(csvRecord.get("CATCH_WEIGHT_IND"))){
						  productInfoVo.setCaseDimWidth(BigDecimal.valueOf(1));
					  }else if("N".equals(csvRecord.get("CATCH_WEIGHT_IND"))){
						  productInfoVo.setCaseDimWidth(BigDecimal.valueOf(2));
					  }
				  }
				  //STANDARD_UOM
				  productInfoVo.setStandardUom(csvRecord.get("STANDARD_UOM"));
				  //BRAND_ENG
				  productInfoVo.setBrandCode(csvRecord.get("BRAND_ENG"));
				  //ITEM_ENG_DESC
				  productInfoVo.setShortDescEn(csvRecord.get("ITEM_ENG_DESC"));
				  //BARCODE
				  productInfoVo.setBarcode(csvRecord.get("BARCODE"));
				  //BARCODE_ITEM_NUMBER_TYPE
				  productInfoVo.setItemNumType(csvRecord.get("BARCODE_ITEM_NUMBER_TYPE"));
				  //CASE_LENGTH
				  if(StringUtils.isNotEmpty(csvRecord.get("CASE_LENGTH"))){
					  productInfoVo.setCaseDimLength(new BigDecimal(csvRecord.get("CASE_LENGTH")));
				  }
				  //CASE_WIDTH
				  if(StringUtils.isNotEmpty(csvRecord.get("CASE_WIDTH"))){
					  productInfoVo.setCaseDimWidth(new BigDecimal(csvRecord.get("CASE_WIDTH")));
				  }
				  //CASE_HEIGHT
				  if(StringUtils.isNotEmpty(csvRecord.get("CASE_HEIGHT"))){
					  productInfoVo.setCaseDimHeight(new BigDecimal(csvRecord.get("CASE_HEIGHT")));
				  }
				  //SUPP_PACK_SIZE
				  if(StringUtils.isNotEmpty(csvRecord.get("SUPP_PACK_SIZE"))){
					  productInfoVo.setCasePackCase(new BigDecimal(csvRecord.get("SUPP_PACK_SIZE")));
				  }
				  //INNER_PACK_SIZE
				  if(StringUtils.isNotEmpty(csvRecord.get("INNER_PACK_SIZE"))){
					  productInfoVo.setCasePackInner(new BigDecimal(csvRecord.get("INNER_PACK_SIZE")));
				  }
				  //UNIT_COST
				  if(StringUtils.isNotEmpty(csvRecord.get("UNIT_COST"))){
					  productInfoVo.setUnitCost(new BigDecimal(csvRecord.get("UNIT_COST")));
				  }
				  //UNIT_RETAIL
				  if(StringUtils.isNotEmpty(csvRecord.get("UNIT_RETAIL"))){
					  productInfoVo.setUnitRetail(new BigDecimal(csvRecord.get("UNIT_RETAIL")));
				  }	
				  //UDA_LOV_VALUE_1
				  productInfoVo.setColorGroup(csvRecord.get("UDA_LOV_VALUE_1"));
				  //UDA_LOV_VALUE_2
				  productInfoVo.setVariantColor(csvRecord.get("UDA_LOV_VALUE_2"));
				  //UDA_LOV_VALUE_3
				  productInfoVo.setVariantSize(csvRecord.get("UDA_LOV_VALUE_3"));
				  //UDA_LOV_VALUE_4
				  productInfoVo.setEstoreCategory(csvRecord.get("UDA_LOV_VALUE_4"));
				  //UDA_LOV_VALUE_5
				  productInfoVo.setDeliveryMode(csvRecord.get("UDA_LOV_VALUE_5"));
				  //UDA_LOV_VALUE_6
				  productInfoVo.setMinOrderQty(csvRecord.get("UDA_LOV_VALUE_6"));
				  //UDA_LOV_VALUE_7
				  productInfoVo.setDailyInventory(csvRecord.get("UDA_LOV_VALUE_7"));
				  //UDA_LOV_VALUE_8
				  if(StringUtils.isNotEmpty(csvRecord.get("UDA_LOV_VALUE_8"))){
					  productInfoVo.setShippingDimHeight(new BigDecimal(csvRecord.get("UDA_LOV_VALUE_8")));
				  }
				//UDA_LOV_VALUE_9
				  if(StringUtils.isNotEmpty(csvRecord.get("UDA_LOV_VALUE_9"))){
					  productInfoVo.setShippingDimWidth(new BigDecimal(csvRecord.get("UDA_LOV_VALUE_9")));
				  }
				//UDA_LOV_VALUE_10
				  if(StringUtils.isNotEmpty(csvRecord.get("UDA_LOV_VALUE_10"))){
					  productInfoVo.setShippingDimLength(new BigDecimal(csvRecord.get("UDA_LOV_VALUE_10")));
				  }
				  //UDA_LOV_VALUE_11
				  productInfoVo.setDimUnit(csvRecord.get("UDA_LOV_VALUE_11"));
				  //UDA_LOV_VALUE_12
				  productInfoVo.setWeightUnit(csvRecord.get("UDA_LOV_VALUE_12"));
				  //UDA_LOV_VALUE_13
				  productInfoVo.setManufCountry(csvRecord.get("UDA_LOV_VALUE_13"));
				  //UDA_LOV_VALUE_14
				  productInfoVo.setPackAge(csvRecord.get("UDA_LOV_VALUE_14"));
				  //UDA_FF_VALUE_2
				  productInfoVo.setSizeDesc(csvRecord.get("UDA_FF_VALUE_2"));
				  //UDA_FF_VALUE_3
				  productInfoVo.setColorCode(csvRecord.get("UDA_FF_VALUE_3"));
				  //UDA_FF_VALUE_4
				  productInfoVo.setColorGroup(csvRecord.get("UDA_FF_VALUE_4"));
				  //UDA_FF_VALUE_5
				  productInfoVo.setColorHexCode(csvRecord.get("UDA_FF_VALUE_5"));
				  //UDA_FF_VALUE_6
				  if(StringUtils.isNotEmpty(csvRecord.get("UDA_FF_VALUE_6"))){
					  productInfoVo.setMaxDeliverDate(new BigDecimal(csvRecord.get("UDA_FF_VALUE_6")));
				  }
				  //UDA_FF_VALUE_7
				  if(StringUtils.isNotEmpty(csvRecord.get("UDA_FF_VALUE_7"))){
					  productInfoVo.setMinDeliverDate(new BigDecimal(csvRecord.get("UDA_FF_VALUE_7")));
				  }
				  //UDA_FF_VALUE_8
				  if(StringUtils.isNotEmpty(csvRecord.get("UDA_FF_VALUE_8"))){
					  productInfoVo.setOnlineDate(DateUtils.parseDateStr(csvRecord.get("UDA_FF_VALUE_8"), DateUtils.DATE_FORMATE_YYYYMMDD));
				  }
				  //UDA_FF_VALUE_9
				  if(StringUtils.isNotEmpty(csvRecord.get("UDA_FF_VALUE_9"))){
					  productInfoVo.setOfflineDate(DateUtils.parseDateStr(csvRecord.get("UDA_FF_VALUE_9"), DateUtils.DATE_FORMATE_YYYYMMDD));
				  }
				  //UDA_FF_VALUE_10
				  productInfoVo.setProductShelfLife(csvRecord.get("UDA_FF_VALUE_10"));
				  
				  //UDA_FF_VALUE_11
				  productInfoVo.setMinShelfLife(csvRecord.get("UDA_FF_VALUE_11"));
				  
				  //UDA_FF_VALUE_12
				  productInfoVo.setShippingInfo(csvRecord.get("UDA_FF_VALUE_12"));

				  //UNIT_LENGTH
				  if(StringUtils.isNotEmpty(csvRecord.get("UNIT_LENGTH"))){
					  productInfoVo.setProductDimLength(new BigDecimal(csvRecord.get("UNIT_LENGTH")));
				  }
				  //UNIT_WIDTH
				  if(StringUtils.isNotEmpty(csvRecord.get("UNIT_WIDTH"))){
					  productInfoVo.setProductDimWidth(new BigDecimal(csvRecord.get("UNIT_WIDTH")));
				  }
				  //UNIT_HEIGHT
				  if(StringUtils.isNotEmpty(csvRecord.get("UNIT_HEIGHT"))){
					  productInfoVo.setProductDimHeight(new BigDecimal(csvRecord.get("UNIT_HEIGHT")));
				  }
				  //VPN
				  productInfoVo.setSupplierProductCode(csvRecord.get("VPN"));
				  //CONSIGNMENT_RATE
				  if(StringUtils.isNotEmpty(csvRecord.get("CONSIGNMENT_RATE"))){
					  productInfoVo.setConsignmentRate(new BigDecimal(csvRecord.get("CONSIGNMENT_RATE")));
				  }
				  //BASE_ITEM
				  productInfoVo.setBaseProductCode(csvRecord.get("BASE_ITEM"));
				  //ITEM_SIM_CHI_DESC
				  productInfoVo.setShortDescSc(csvRecord.get("ITEM_SIM_CHI_DESC"));
				//ITEM_ENG_DESC_LONG
				  productInfoVo.setLongDescEn(csvRecord.get("ITEM_ENG_DESC_LONG"));
				  //TEM_CHI_DESC_LONG
				  productInfoVo.setShortDescSc(csvRecord.get("ITEM_CHI_DESC_LONG"));
				  //ITEM_SIM_CHI_DESC_LONG
				  productInfoVo.setLongDescSc(csvRecord.get("ITEM_SIM_CHI_DESC_LONG"));
				  //PRODUCT_USAGE_ENG
				  productInfoVo.setProductUsageEn(csvRecord.get("PRODUCT_USAGE_ENG"));
				  //PRODUCT_USAGE_CHI
				  productInfoVo.setProductUsageTc(csvRecord.get("PRODUCT_USAGE_CHI"));
				  //PRODUCT_USAGE_SIM_CHI
				  productInfoVo.setProductUsageSc(csvRecord.get("PRODUCT_USAGE_SIM_CHI"));
				  //PRODUCT_WARNINGS_ENG
				  productInfoVo.setProductWarningsEn(csvRecord.get("PRODUCT_WARNINGS_ENG"));
				  //PRODUCT_WARNINGS_CHI
				  productInfoVo.setProductWarningsTc(csvRecord.get("PRODUCT_WARNINGS_CHI"));
				  //PRODUCT_WARNINGS_SIM_CHI
				  productInfoVo.setProductWarningsSc(csvRecord.get("PRODUCT_WARNINGS_SIM_CHI"));
				  //STORAGE_CONDITION_ENG
				  productInfoVo.setStorageConditionEn(csvRecord.get("STORAGE_CONDITION_ENG"));
				  //STORAGE_CONDITION_CHI
				  productInfoVo.setStorageConditionTc(csvRecord.get("STORAGE_CONDITION_CHI"));
				  //STORAGE_CONDITION_SIM_CHI
				  productInfoVo.setStorageConditionSc(csvRecord.get("STORAGE_CONDITION_SIM_CHI"));
				  //PRODUCT_INGREDIENTS_ENG
				  productInfoVo.setProductIngredientsEn(csvRecord.get("PRODUCT_INGREDIENTS_ENG"));
				  //PRODUCT_INGREDIENTS_CHI
				  productInfoVo.setProductIngredientsTc(csvRecord.get("PRODUCT_INGREDIENTS_CHI"));
				  //PRODUCT_INGREDIENTS_SIM_CHI
				  productInfoVo.setProductIngredientsSc(csvRecord.get("PRODUCT_INGREDIENTS_SIM_CHI"));
				  //GROSS_UNIT_WEIGHT
				  if(StringUtils.isNotEmpty(csvRecord.get("GROSS_UNIT_WEIGHT"))){
					  productInfoVo.setGrossWeight(new BigDecimal(csvRecord.get("GROSS_UNIT_WEIGHT")));
				  }
				  //CONSIGNMENT_TYPE
				  productInfoVo.setConsignmentType(csvRecord.get("CONSIGNMENT_TYPE"));
				  //CONSIGNMENT_CALC_BASIS
				  productInfoVo.setConsignmentCalBasis(csvRecord.get("CONSIGNMENT_CALC_BASIS"));
				  //SHIPPING_HEIGHT
				  if(StringUtils.isNotEmpty(csvRecord.get("SHIPPING_HEIGHT"))){
					  productInfoVo.setShippingDimHeight(new BigDecimal(csvRecord.get("SHIPPING_HEIGHT")));
				  }
				  //SHIPPING_WIDTH
				  if(StringUtils.isNotEmpty(csvRecord.get("SHIPPING_WIDTH"))){
					  productInfoVo.setShippingDimWidth(new BigDecimal(csvRecord.get("SHIPPING_WIDTH")));
				  }
				  //SHIPPING_LENGTH
				  if(StringUtils.isNotEmpty(csvRecord.get("SHIPPING_LENGTH"))){
					  productInfoVo.setShippingDimLength(new BigDecimal(csvRecord.get("SHIPPING_LENGTH")));
				  }
				  //SHIPPING_STANDARD_UOM
				  productInfoVo.setStandardUom(csvRecord.get("SHIPPING_STANDARD_UOM"));
				  //SHIPPING_UNIT_WEIGHT
				  if(StringUtils.isNotEmpty(csvRecord.get("SHIPPING_UNIT_WEIGHT"))){
					  productInfoVo.setShippingWeight(new BigDecimal(csvRecord.get("SHIPPING_UNIT_WEIGHT_UOM")));
				  }
				  //SHIPPING_UNIT_WEIGHT_UOM
				  productInfoVo.setWeightUnit(csvRecord.get("SHIPPING_UNIT_WEIGHT_UOM"));
				  
				  if(StringUtils.isNotEmpty(csvRecord.get("REPL_MIN_STOCK"))){
					  productInfoVo.setMinReplenishmentLevel(new BigDecimal(csvRecord.get("REPL_MIN_STOCK")));
				  }
				  
				  if(StringUtils.isNotEmpty(csvRecord.get("REPL_MAX_STOCK"))){
					  productInfoVo.setMaxReplenishmentLevel(new BigDecimal(csvRecord.get("REPL_MAX_STOCK")));
				  }
				  list.add(productInfoVo);
			  }
		 }
		 return list;
	 }
	 
	 private List<ProductBarCode> getProductBarcodeListFromCsv( List<CSVRecord> csvRecords){
		 List<ProductBarCode> list=new ArrayList<>();
	            if(CollectionUtils.isNotEmpty(csvRecords)){
	   			 int i=0;
	   			  for(CSVRecord csvRecord : csvRecords){
	   				  if(i==0){
	   					  i++;
	   					  continue;
	   				  }
	   				  ProductBarCode productBarCode = new ProductBarCode();
	   				  //ITEM
	   				  productBarCode.setProductCode(csvRecord.get("ITEM"));
	   				  //BARCODE
	   				  productBarCode.setBarcodeNum(csvRecord.get("BARCODE"));
	   				  //BARCODE_ITEM_NUMBER_TYPE
	   				  productBarCode.setItemNumType(csvRecord.get("BARCODE_ITEM_NUMBER_TYPE"));
	   				  //PRIMARY_REF_ITEM_IND
	   				  productBarCode.setPrimaryRefItemInd(csvRecord.get("PRIMARY_REF_ITEM_IND"));
	   				  //ACTION_ITEM
	   				  productBarCode.setActionItem(csvRecord.get("ACTION_ITEM"));
	   				  list.add(productBarCode);
	   			  }
		 }
		 return list;
	 }
	 
	 public void insertProductFormRetek(List<ProductInfoVo> productInfoVos){
		    ProductPopulator populator = new ProductPopulator();
		    if(CollectionUtils.isNotEmpty(productInfoVos)){
		    	for(ProductInfoVo productInfoVo :productInfoVos){
		    		if(StringUtils.isNotEmpty(productInfoVo.getProductCode())){
		    			//ONLINE
		    			Map map=new HashMap();
		    			map.put("version", ConstantUtil.VERSION_ONLINE);
		    			map.put("productCode", productInfoVo.getProductCode());
		    			ProductInfoModel productInfoModel=productInfoMapper.getProductInfoModelByProductCode(map);
		    			//STAGING
		    			Map map2=new HashMap();
		    			map2.put("version", ConstantUtil.VERSION_STAGING);
		    			map2.put("productCode", productInfoVo.getProductCode());
		    			ProductInfoModel productInfoModelStage=productInfoMapper.getProductInfoModelByProductCode(map);
		    			if(productInfoModelStage != null){
		    				productInfoModelStage.setOnlineUpdatedInd("Y");
		    				productInfoMapper.updateByPrimaryKeySelective(productInfoModelStage);
		    			}
		    			
		    			//begin get dept, class, subclass id
		    			DeptModel deptModel = deptService
								.getDeptByDeptId(productInfoVo.getDept());
						if (null != deptModel) {
							productInfoVo.setDept(deptModel.getId());
							Map<String, Object> classMap = new HashMap<>();
							classMap.put("deptId", deptModel.getId());
							classMap.put("classId", productInfoVo.getClazz());
							ClassModel classModel = classService
									.getClassByDeptIdClassId(classMap);
							if (null != classModel) {
								productInfoVo.setClazz(classModel.getId());
								Map<String, Object> subClassMap = new HashMap<>();
								subClassMap.put("classId", classModel.getId());
								subClassMap.put("subClassId", productInfoVo.getSubClass());
								SubClassModel subClassModel = subclassService
										.getSubClassByClassIdSubClassId(subClassMap);
								if (null != subClassModel) {
									productInfoVo.setSubClass(subClassModel.getId());
								}
							}
						}
						//end
		    			
		    			if(productInfoModel != null){
			    			BigDecimal pId = productInfoModel.getId();
			    			ProductInfoModel productInfoModel2 = populator.converProductVoToModelForCsv(productInfoVo);
							productInfoModel2.setId(pId);
							productInfoModel2.setOnlineUpdatedInd("Y");
							productInfoModel2.setVersion(ConstantUtil.VERSION_ONLINE);
							productInfoModel2.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
							productInfoModel2.setLastUpdatedDate(new Date());
							productInfoMapper.updateByPrimaryKeySelective(productInfoModel2);
		    			}else{
		    				ProductInfoModel productInfoModel2 = populator.converProductVoToModelForCsv(productInfoVo);
							productInfoModel2.setOnlineUpdatedInd("Y");
							productInfoModel2.setVersion(ConstantUtil.VERSION_ONLINE);
							productInfoModel2.setCreatedDate(new Date());
							productInfoModel2.setCreatedBy(ConstantUtil.JOB_USER_NAME);
							productInfoModel2.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
							productInfoModel2.setLastUpdatedDate(new Date());
							productInfoMapper.insertSelective(productInfoModel2);
		    			}
		    		}
		    	}
		    }
	 }
	 
	 public void insertProductBarcodeFormRetek(List<ProductBarCode> productBarCodes,ErrorLog errorLog){
		    if(CollectionUtils.isNotEmpty(productBarCodes)){
		    	for(ProductBarCode productBarCode:productBarCodes){	
		    		if(StringUtils.isNotEmpty(productBarCode.getProductCode())){
		    			Map map=new HashMap();
		    			map.put("version", ConstantUtil.VERSION_ONLINE);
		    			map.put("productCode", productBarCode.getProductCode());
		    			ProductInfoModel productInfoModel=productInfoMapper.getProductBySkuAndVersion(map);
		    			if(productInfoModel != null){
		    				//A
		    				if(StringUtils.isNotEmpty(productBarCode.getActionItem()) && "A".equals(productBarCode.getActionItem())){
		    					errorLog.add("product action is A");
		    					String barCode = productBarCode.getBarcodeNum();
		    					if(StringUtils.isNotEmpty(barCode)){
		    						Map map2=new HashMap();
		    		    			map2.put("version", ConstantUtil.VERSION_ONLINE);
		    		    			map2.put("barcodenum", productBarCode.getBarcodeNum());
		    						List<ProductBarcodeModel> productBarCodes2 =	productBarcodeModelMapper.getProductBarcodeModelByBarcodeNum(map2);
		    						if(CollectionUtils.isNotEmpty(productBarCodes2)){
		    							ProductBarcodeModel productBarcodeModel = productBarCodes2.get(0);
		    							if(StringUtils.isNotEmpty(productBarcodeModel.getStatus()) && "APPROVED_IN_RETEK".equals(productBarcodeModel.getStatus())){
		    								errorLog.add("repeating barcode is already accepted by Retek");
		    							}else if(StringUtils.isNotEmpty(productBarcodeModel.getStatus()) && "UNAPPROVED_IN_RETEK".equals(productBarcodeModel.getStatus())){
		    								BigDecimal barCodeId = productBarcodeModel.getId();
		    			    				ProductBarcodeModel productBarcodeModel2 = coverBarcodeVoToModel(productBarCode);
		    			    				productBarcodeModel2.setId(barCodeId);
		    			    				productBarcodeModel2.setCreatedDate(new Date());
		    			    				productBarcodeModel2.setCreatedBy(ConstantUtil.JOB_USER_NAME);
		    			    				productBarcodeModel2.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
		    			    				productBarcodeModel2.setLastUpdatedDate(new Date());
		    			    				productBarcodeModelMapper.updateByPrimaryKeySelective(productBarcodeModel2);
		    							}else{
		    								ProductBarcodeModel productBarcodeModel2 = coverBarcodeVoToModel(productBarCode);
		    			    				productBarcodeModel2.setCreatedDate(new Date());
		    			    				productBarcodeModel2.setCreatedBy(ConstantUtil.JOB_USER_NAME);
		    			    				productBarcodeModel2.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
		    			    				productBarcodeModel2.setLastUpdatedDate(new Date());
		    			    				productBarcodeModelMapper.insertSelective(productBarcodeModel2);
		    							}
		    						}
		    					}
		    				}
		    				//D
		    				if(StringUtils.isNotEmpty(productBarCode.getActionItem()) && "D".equals(productBarCode.getActionItem())){
		    					Map map2=new HashMap();
	    		    			map2.put("version", ConstantUtil.VERSION_ONLINE);
	    		    			map2.put("barcodenum", productBarCode.getBarcodeNum());
	    						List<ProductBarcodeModel> prBarcodeModels =	productBarcodeModelMapper.getProductBarcodeModelByBarcodeNum(map2);
	    						if(CollectionUtils.isNotEmpty(prBarcodeModels)){
	    							ProductBarcodeModel productBarcodeModel = prBarcodeModels.get(0);
	    							productBarcodeModelMapper.deleteByPrimaryKey(productBarcodeModel.getId());
	    						}
		    				}else{
		    					errorLog.add("product action is D");
		    				}
		    			}else{
		    				errorLog.add("product code is not exists");
		    			}
		    		}
		    	}
		    }
	 }
	 
	 public ProductBarcodeModel coverBarcodeVoToModel(ProductBarCode productBarCode){
		 ProductBarcodeModel productBarcodeModel = new ProductBarcodeModel();
		 productBarcodeModel.setBarcodeNum(productBarCode.getBarcodeNum());
		 productBarcodeModel.setPrimaryInd(productBarCode.getPrimaryRefItemInd());
		 productBarcodeModel.setItemNumType(productBarCode.getItemNumType());
		 productBarcodeModel.setProductCode(productBarCode.getProductCode());
		 return productBarcodeModel;
	 }


	@Override
	public boolean importProductBarcodeFromRetek(String fileName,ErrorLog errorLog) {
		List<ProductBarCode> productBarCodes = null;
		try {
			productBarCodes = readBarcodeImportCsvFileFromRetek(fileName,errorLog);
			if(CollectionUtils.isNotEmpty(productBarCodes)){
				insertProductBarcodeFormRetek(productBarCodes,errorLog);
			}else{
				return false;	
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			errorLog.add(e.getMessage());
		}
		return true;	
	}

	@Override
	public boolean importProductImagesFromRetek(String fileName,ErrorLog errorLog) {
		try {
			List<ProductImagesVo> productImagesVos = readImportImagesCsvFileFromRetek(fileName,errorLog);
			if(CollectionUtils.isNotEmpty(productImagesVos)){
				insertProductImagesFormRetek(productImagesVos,errorLog);
			}else{
				return false;	
			}
		} catch (Exception e) {
			errorLog.add(e.getMessage());
			LOG.error(e.getMessage(),e);
		}
		return true;	
	}
	
	private List<ProductImagesVo> readImportImagesCsvFileFromRetek(String fileName,ErrorLog errorLog) {
    	List<ProductImagesVo> list= null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(IMAGE_FILE_HEADER);
        try(
    		FileReader fileReader = new FileReader(fileName);
    		CSVParser csvFileParser = new CSVParser(fileReader, csvFileFormat);
        ){
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
            list = getProductImagesVoListFromCsv(csvRecords);
        } 
        catch (Exception e) {
        	errorLog.add(e.getMessage());
            LOG.error(e.getMessage(),e);
        }
		return list;
    }
	
	 private List<ProductImagesVo> getProductImagesVoListFromCsv( List<CSVRecord> csvRecords){
		 List<ProductImagesVo> list=new ArrayList<>();
		 if(CollectionUtils.isNotEmpty(csvRecords)){
//			 int i=0;
			  for(CSVRecord csvRecord : csvRecords){
//				  if(i==0){
//					  i++;
//					  continue;
//				  }
				  ProductImagesVo productImagesVo = new ProductImagesVo();
				  //product Code
				  productImagesVo.setProductCode(csvRecord.get("ITEM"));
				  
				  //Image Type
				  if("swatch".equals(csvRecord.get("IMAGE_TYPE"))){
					  productImagesVo.setSequence(Long.valueOf(1));
					  productImagesVo.setImageType("SWATCH_IMAGE");
				  }else{
					  int index=ImageType.getIndex(csvRecord.get("IMAGE_TYPE"));
					  productImagesVo.setSequence(Long.valueOf(index));	
					  productImagesVo.setImageType("PRODUCT_IMAGE");
				  }

				  //Image Name
				  productImagesVo.setFileName(csvRecord.get("IMAGE_NAME"));
				  
				  //Image Desc
				  productImagesVo.setDescription(csvRecord.get("IMAGE_DESC"));
				  
				  //Status
				  productImagesVo.setStatus(csvRecord.get("STATUS"));
				  list.add(productImagesVo);
			  }
		 }
		 return list;
	 } 
	 
	 public void insertProductImagesFormRetek(List<ProductImagesVo> productImagesVos,ErrorLog errorLog){
		    if(CollectionUtils.isNotEmpty(productImagesVos)){
		    	for(ProductImagesVo imageObj :productImagesVos){
		    		if(StringUtils.isNotEmpty(imageObj.getProductCode())){
		    			Map<String , Object> map = new HashMap<>();
		    			map.put("productCode", imageObj.getProductCode());
		    			map.put("version", "ONLINE");
		    			ProductInfoModel productInfoModel=productInfoMapper.getProductInfoModelByProductCode(map);
		    			String imagesPath = ResourceUtil.getSystemConfig().getProperty("product_images_import_path");
		    			String uploadPath = ResourceUtil.getSystemConfig().getProperty("upload_file_path");
		    			if(null!=productInfoModel){
		    				Map<String , Object> map2 = new HashMap<>();
		    				map2.put("productId", productInfoModel.getId());
		    				map2.put("sequence", imageObj.getSequence());
		    				map2.put("imageType", imageObj.getImageType());
			    			ProductImagesModel productImagesModel=productImagesModelMapper.getProductImagesByProductIdSequence(map2);
			    			if("A".equals(imageObj.getStatus().trim())){
				    			if(null!=productImagesModel){
				    				File imageFile = new File(imagesPath + File.separator + imageObj.getFileName());
				    				if(!imageFile.exists()){
				    					productImagesModel.setFailedReason("Image file not found.");
				    				}
				    				productImagesModel.setFileName(imageObj.getFileName());
				    				productImagesModel.setDescription(imageObj.getDescription());
				    				productImagesModel.setLastUpdatedDate(new Date());
//				    				productImagesModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
				    				productImagesModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				    				productImagesModelMapper.updateByPrimaryKey(productImagesModel);
				    				
				    				productInfoModel.setOnlineUpdatedInd("Y");
				    				productInfoMapper.updateByPrimaryKeySelective(productInfoModel);
				    				
				    				if(imageFile.exists()){
				    					FileHandle.copyFile(imagesPath, uploadPath, imageObj.getFileName());
				    					FileHandle.deleteFile(imagesPath+File.separator+imageObj.getFileName());
				    				}
				    			}else{
				    				productImagesModel=new ProductImagesModel();
				    				productImagesModel.setFileName(imageObj.getFileName());
				    				productImagesModel.setSequence(imageObj.getSequence());
				    				productImagesModel.setDescription(imageObj.getDescription());
				    				productImagesModel.setProductId(productInfoModel.getId());
				    				productImagesModel.setImageType("PRODUCT_IMAGE");
				    				productImagesModel.setCreatedDate(new Date());
				    				productImagesModel.setLastUpdatedDate(new Date());
				    				productImagesModel.setCreatedBy(ConstantUtil.JOB_USER_NAME);
				    				productImagesModel.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				    				File imageFile = new File(imagesPath + File.separator + imageObj.getFileName());
				    				if(!imageFile.exists()){
				    					productImagesModel.setFailedReason("Image file not found.");
				    				}
				    				productImagesModelMapper.insertSelective(productImagesModel);
				    				
				    				productInfoModel.setOnlineUpdatedInd("Y");
				    				productInfoMapper.updateByPrimaryKeySelective(productInfoModel);
				    				
				    				if(imageFile.exists()){
				    					FileHandle.copyFile(imagesPath, uploadPath, imageObj.getFileName());
				    					FileHandle.deleteFile(imagesPath+File.separator+imageObj.getFileName());
				    				}
				    				
				    			}
			    			}else if("D".equals(imageObj.getStatus().trim())){
			    				if(null!=productImagesModel){
				    				productImagesModelMapper.deleteByPrimaryKey(productImagesModel.getId());
				    				productInfoModel.setOnlineUpdatedInd("Y");
				    				productInfoMapper.updateByPrimaryKeySelective(productInfoModel);
				    				FileHandle.deleteFile(uploadPath+File.separator+imageObj.getFileName());
				    				
				    			}else{
				    				errorLog.add("product_product_image_not_exists");		
				    			}
			    			}
		    			}else{
		    			  errorLog.add("product_product_code_not_exists");
		    			}
		    		}
		    		else{
		    		      errorLog.add("product_product_code_require");
		    		}
		    	}
		    }
	 }
}
