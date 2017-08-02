package com.mall.b2bp.services.impl.product;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mall.b2bp.enums.LovType;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.dept.DeptClassSubclassModel;
import com.mall.b2bp.models.product.ProductExportModel;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.dept.DeptService;
import com.mall.b2bp.services.lov.LovService;
import com.mall.b2bp.services.product.ProductExportReportHandler;
import com.mall.b2bp.vos.lov.LovVo;
import com.mall.b2bp.vos.user.SupplierBrandVo;

@Service("productExportReportHandler")
public class ProductExportReportHandlerImpl extends ReportHandler implements
		ProductExportReportHandler {

	private static final Logger log = LoggerFactory
			.getLogger(ProductExportReportHandlerImpl.class);
	@Resource(name = "lovService")
	private LovService lovService;

	@Resource(name = "brandService")
	private BrandService brandService;

	@Resource(name = "deptService")
	private DeptService deptService;

	private static final String COLOR = "Color";
	private static final String SIZE = "Size";

	private static final String SUPPLIER_DIRECT_DELIVERY = "Supplier direct delivery";
	private static final String CONSIGNMENT_VIA_WAREHOUSE = "Consignment via warehouse";
	private static final String CONSIGNMENT = "Consignment";

	private static final String E = "Nutrition Labeling Exemption";
	private static final String W = "With Nutrition Label on package";

	private static final String Y = "Yes";
	private static final String N = "No";

	private static final String AMOUNT_NET = "Amount(Net)";
	private static final String AMOUNT_GROSS = "Amount(Gross)";

	@Override
	public Workbook generateXls(List<ProductExportModel> batchData,
			String templateFile, boolean supplier, String supplierId)
			throws ServiceException {

		try (FileInputStream in = new FileInputStream(new File(templateFile));) {

			Workbook work = new XSSFWorkbook(in);

			buildTable(work, batchData, supplier, supplierId);

			return work;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	private void buildTable(Workbook wb, List<ProductExportModel> batchData,
			boolean supplier, String supplierId) {

		int rowNum = 1;
		Sheet sheet = wb.getSheet("ProductList");

		CellStyle styleL = wb.createCellStyle();
		styleL.setAlignment(CellStyle.ALIGN_LEFT);
		styleL.setWrapText(true);

		CellStyle styleR = wb.createCellStyle();
		styleR.setAlignment(CellStyle.ALIGN_RIGHT);
		styleR.setWrapText(true);

		if (CollectionUtils.isNotEmpty(batchData)) {
			List<List<String>> list = covert(batchData, supplier);
			createDataRow(list, sheet, rowNum, styleL, styleR, supplier);
		}

		Sheet sheet1 = wb.getSheet("LOV");
		createDataRow(sheet1, rowNum, supplierId);

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

	private List<List<String>> covert(List<ProductExportModel> batchData,
			boolean supplier) {

		List<List<String>> list = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(batchData)) {

			for (ProductExportModel m : batchData) {
				List<String> subList = new ArrayList<>();

				if (!supplier) {
					subList.add(m.getSuName());
				}

				subList.add(m.getProductCode());
				subList.add(m.getSupplierProductCode());
				subList.add(m.getBaseProductCode());
				subList.add(m.getBaseSupplierProductCode());
				subList.add(m.getStatus());
				subList.add(m.getShortDescEn());
				subList.add(m.getShortDescTc());
				subList.add(m.getShortDescSc());
				subList.add(replaceDesc(m.getLongDescEn()));
				subList.add(replaceDesc(m.getLongDescTc()));
				subList.add(replaceDesc(m.getLongDescSc()));
				subList.add(m.getBrandDescEn());
				subList.add(m.getVariantType());
				subList.add(m.getColorGroup());
				subList.add(m.getColorHexCode());
				subList.add(m.getColorCode());
				subList.add(m.getColorDesc());
				subList.add(m.getOnlineDate());
				subList.add(m.getOfflineDate());// 20
				subList.add(decimalFormat(m.getUnitRetail()));
				subList.add(m.getStandardUom());
				subList.add(m.getCountry());

				subList.add(replaceDsc(m.getDcs()));
				subList.add(m.getCategory());
				subList.add(m.getDeliveryMode());
				subList.add(decimalFormat(m.getSupplierLeadTime()));
				//Added Replenishment level min & max (column AB, AC)
				subList.add(decimalFormat(m.getMinReplenishmentLevel()));
				subList.add(decimalFormat(m.getMaxReplenishmentLevel()));
				
				subList.add(decimalFormat(m.getMinOrderQty()));
				subList.add(decimalFormat(m.getDailyInventory()));
//				subList.add(m.getConsignmentType());
				subList.add(decimalFormat(m.getMinDeliverDate()));
				subList.add(decimalFormat(m.getMaxDeliverDate()));
				subList.add(m.getConsignmentCalBasis());
				subList.add(m.getConsignmentRate());
				subList.add(m.getSizeDesc());
				subList.add(decimalFormat(m.getCasePackInner()));
				subList.add(decimalFormat(m.getCasePackCase()));
				subList.add(m.getDimUnit());
				subList.add(decimalFormat(m.getProductDimHeight()));
				subList.add(decimalFormat(m.getProductDimWidth()));// 40

				subList.add(decimalFormat(m.getProductDimLength()));
				subList.add(decimalFormat(m.getShippingDimHeight()));
				subList.add(decimalFormat(m.getShippingDimWidth()));
				subList.add(decimalFormat(m.getShippingDimLength()));
				subList.add(decimalFormat(m.getCaseDimHeight()));
				subList.add(decimalFormat(m.getCaseDimWidth()));
				subList.add(decimalFormat(m.getCaseDimLength()));
				subList.add(m.getWeightUnit());
				subList.add(decimalFormat(m.getGrossWeight()));
				subList.add(decimalFormat(m.getShippingWeight()));
				subList.add(m.getBarcodeType());
				subList.add(m.getBarcodeNum());
				subList.add(m.getNutritionLabel());

				subList.add(replaceDesc(m.getProductUsageEn()));
				subList.add(replaceDesc(m.getProductUsageTc()));
				subList.add(replaceDesc(m.getProductUsageSc()));
				subList.add(replaceDesc(m.getProductWarningsEn()));
				subList.add(replaceDesc(m.getProductWarningsTc()));
				subList.add(replaceDesc(m.getProductWarningsSc()));
				subList.add(replaceDesc(m.getStorageEn()));// 60
				subList.add(replaceDesc(m.getStorageTc()));
				subList.add(replaceDesc(m.getStorageSc()));
				subList.add(replaceDesc(m.getProductIngredientsEn()));
				subList.add(replaceDesc(m.getProductIngredientsTc()));
				subList.add(replaceDesc(m.getProductWarningsSc()));
				subList.add(m.getManufCountry());
				subList.add(m.getManufPackage());
				subList.add(m.getProductShelfLife());
				subList.add(m.getMinShelfLife());
				subList.add(m.getSmallExpensive());// 70
				subList.add(m.getShippingInfo());//BT
				subList.add(m.getPnsOnlineDeliveryType()); //BU
				list.add(subList);
			}

		}
		return list;
	}

	private String replaceDesc(String str) {
		if (StringUtils.isNotBlank(str))
			return str.replaceAll("<br/>", "\r\n");
		return "";
	}

	private String replaceDsc(String str) {
		if (StringUtils.isNotBlank(str))
			return str.replaceAll("--:>>", "");
		return "";
	}

	private void createDataRow(List<List<String>> list, Sheet sheet,
			int rowNum, CellStyle styleL, CellStyle styleR, boolean supplier) {

		Map<String, Boolean> mapForSupplier = getMapForSuppliser();
		Map<String, Boolean> mapForAdmin = getMapForAdmin();

		for (List<String> l : list) {

			Row row = sheet.createRow(rowNum++);
			short var2 = 0;

			if (CollectionUtils.isNotEmpty(l)) {

				for (String str : l) {
					Cell cell = row.createCell(var2);
					cell.setCellValue(str);

					if (getStyleMap(mapForSupplier, mapForAdmin, supplier, String.valueOf(var2)))
						cell.setCellStyle(styleR);
					else
						cell.setCellStyle(styleL);
					var2++;
				}
			}
		}
	}

	
	//39 34  36
	private Map<String, Boolean> getMapForSuppliser() {
		Map<String, Boolean> map1 = new HashMap<>();

		map1.put("19", Boolean.TRUE);
		
		map1.put("25", Boolean.TRUE);
		map1.put("26", Boolean.TRUE);
		map1.put("27", Boolean.TRUE);
		map1.put("28", Boolean.TRUE);
		map1.put("29", Boolean.TRUE);
		map1.put("30", Boolean.TRUE);
		map1.put("31", Boolean.TRUE);
		//map1.put("32", Boolean.TRUE);
		
		map1.put("33", Boolean.TRUE);
		map1.put("35", Boolean.TRUE);
		map1.put("36", Boolean.TRUE);


		map1.put("38", Boolean.TRUE);
		map1.put("39", Boolean.TRUE);
		map1.put("40", Boolean.TRUE);
		map1.put("41", Boolean.TRUE);
		map1.put("42", Boolean.TRUE);
		map1.put("43", Boolean.TRUE);
		map1.put("44", Boolean.TRUE);
		map1.put("45", Boolean.TRUE);
		map1.put("46", Boolean.TRUE);
	//	map1.put("47", Boolean.TRUE);
		
		map1.put("48", Boolean.TRUE);
		map1.put("49", Boolean.TRUE);
		return map1;
	}

	private Map<String, Boolean> getMapForAdmin() {
		Map map1 = new HashMap<>();


		map1.put("20", Boolean.TRUE);
		
		map1.put("26", Boolean.TRUE);
		map1.put("27", Boolean.TRUE);
		map1.put("28", Boolean.TRUE);
		map1.put("29", Boolean.TRUE);
		map1.put("30", Boolean.TRUE);
		map1.put("31", Boolean.TRUE);
		map1.put("32", Boolean.TRUE);
		
		map1.put("34", Boolean.TRUE);
		map1.put("36", Boolean.TRUE);
		map1.put("37", Boolean.TRUE);


		map1.put("39", Boolean.TRUE);
		map1.put("40", Boolean.TRUE);
		map1.put("41", Boolean.TRUE);
		map1.put("42", Boolean.TRUE);
		map1.put("43", Boolean.TRUE);
		map1.put("44", Boolean.TRUE);
		map1.put("45", Boolean.TRUE);
		map1.put("46", Boolean.TRUE);
		map1.put("47", Boolean.TRUE);
		
		map1.put("49", Boolean.TRUE);
		map1.put("50", Boolean.TRUE);

		return map1;
	}

	private boolean getStyleMap(Map<String, Boolean> mapForSupplier,
			Map<String, Boolean> mapForAdmin, boolean supplier, String index) {

		if (supplier) {
			Boolean f = mapForSupplier.get(index);
			return f == null ? false : f.booleanValue();
		} else {
			Boolean f = mapForAdmin.get(index);
			return f == null ? false : f.booleanValue();
		}

	}

	private void createDataRow(Sheet sheet, int rowNum, String supplierId) {
		Workbook wb = sheet.getWorkbook();
		List<String> brands = getBrandList(supplierId);
		wb.getName("Brands").setRefersToFormula(
				"LOV!$A$2:$A$" + (brands.size() + 1));

		List<String> variantTypes = getVariantTypesList();

		List<String> colorGroups = getColorGroups();
		wb.getName("ColorGroups").setRefersToFormula(
				"LOV!$C$2:$C$" + (colorGroups.size() + 1));

		List<String> uom = getUomList();
		wb.getName("UOM").setRefersToFormula("LOV!$D$2:$D$" + (uom.size() + 1));

		List<String> countryList = getOriginCountriesList();
		wb.getName("OriginCountries").setRefersToFormula(
				"LOV!$E$2:$E$" + (countryList.size() + 1));

		List<String> deptClassSubclass = getDeptClassSubclassList(supplierId);
		wb.getName("DeptClassSubclass").setRefersToFormula(
				"LOV!$F$2:$F$" + (deptClassSubclass.size() + 1));

		List<String> eCat = geteCatList(supplierId);
		wb.getName("eCat").setRefersToFormula(
				"LOV!$G$2:$G$" + (eCat.size() + 1));

		List<String> deliveryMode = getDeliveryModeList();
		wb.getName("DeliveryMode").setRefersToFormula(
				"LOV!$H$2:$H$" + (deliveryMode.size() + 1));

		List<String> cbmUom = geteCbmUomList();
		wb.getName("CBM_UOM").setRefersToFormula(
				"LOV!$I$2:$I$" + (cbmUom.size() + 1));

		List<String> weightUom = geteWeightUomList();
		wb.getName("CBM_WEIGHT").setRefersToFormula(
				"LOV!$J$2:$J$" + (weightUom.size() + 1));

		List<String> barcodeType = geteBarcodeTypeList();
		wb.getName("BarcodeType").setRefersToFormula(
				"LOV!$K$2:$K$" + (barcodeType.size() + 1));

		List<String> nutriLabel = geteNutriLabelList();
		wb.getName("NutriLabel").setRefersToFormula(
				"LOV!$L$2:$L$" + (nutriLabel.size() + 1));

		List<String> packageList = getPackageList();
		wb.getName("Package").setRefersToFormula(
				"LOV!$O$2:$O$" + (packageList.size() + 1));

		List<String> packedInList = getPackedInList();
		wb.getName("Packed_In").setRefersToFormula(
				"LOV!$P$2:$P$" + (packedInList.size() + 1));
		
		//getPNSDeliveryType
		List<String> deliveryTypes = getPNSDeliveryType();
		wb.getName("PNSDeliveryType").setRefersToFormula(
				"LOV!$Q$2:$Q$" + (deliveryTypes.size() + 1));
		
		
		List<String> expensive = geteExpensiveList();

		List<String> consignCalcBasis = getConsignCalcBasisList();

		List<Integer> numList = new ArrayList<>();
		numList.add(Integer.valueOf(brands.size()));
		numList.add(Integer.valueOf(variantTypes.size()));
		numList.add(Integer.valueOf(colorGroups.size()));
		numList.add(Integer.valueOf(uom.size()));
		numList.add(Integer.valueOf(countryList.size()));
		numList.add(Integer.valueOf(deptClassSubclass.size()));
		numList.add(Integer.valueOf(deliveryMode.size()));
		numList.add(Integer.valueOf(cbmUom.size()));
		numList.add(Integer.valueOf(weightUom.size()));
		numList.add(Integer.valueOf(nutriLabel.size()));
		numList.add(Integer.valueOf(expensive.size()));
		numList.add(Integer.valueOf(eCat.size()));
		numList.add(Integer.valueOf(barcodeType.size()));
		numList.add(Integer.valueOf(consignCalcBasis.size()));
		numList.add(Integer.valueOf(packageList.size()));
		numList.add(Integer.valueOf(packedInList.size()));
		numList.add(Integer.valueOf(deliveryTypes.size()));

		Integer max = (Integer) Collections.max(numList);
		if (max == 0)
			return;

		for (int i = 0; i < max; i++) {

			Row row = sheet.createRow(rowNum++);
			short var2 = 0;

			// Brands
			String v = getValue(brands, i);
			Cell cell = row.createCell(var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			// VariantTypes
			v = getValue(variantTypes, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			// ColorGroups
			v = getValue(colorGroups, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			// UOM
			v = getValue(uom, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			// OriginCountries
			v = getValue(countryList, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			v = getValue(deptClassSubclass, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			v = getValue(eCat, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			v = getValue(deliveryMode, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			// CBM_UOM
			v = getValue(cbmUom, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			// WEIGHT_UOM
			v = getValue(weightUom, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			// BarcodeType
			v = getValue(barcodeType, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			v = getValue(nutriLabel, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			v = getValue(expensive, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			v = getValue(consignCalcBasis, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			v = getValue(packageList, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			v = getValue(packedInList, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);
			
			v = getValue(deliveryTypes, i);
			cell = row.createCell(++var2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(v);

			sheet.protectSheet("pssp123");
		}
	}

	private String getValue(List<String> data, int index) {

		if (CollectionUtils.isEmpty(data))
			return "";

		if (index > data.size() - 1)
			return "";

		return data.get(index);

	}

	private List<String> getBrandList(String supplierId) {
		List<String> listString = new ArrayList<>();
		List<SupplierBrandVo> list = brandService.selectBrandList(supplierId);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SupplierBrandVo vo : list) {
				listString.add(vo.getDescEn());
			}
		}

		return listString;
	}

	private List<String> getVariantTypesList() {
		List<String> listString = new ArrayList<>();

		listString.add(COLOR);
		listString.add(SIZE);

		return listString;
	}

	private List<String> getCatList(String supplierId) {
		List<LovVo> list = lovService.getCatBySupplierId(supplierId);
		return listString(list);
	}

	

	private List<String> listString(List<LovVo> list) {
		List<String> listString = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (LovVo vo : list) {
				if (vo.getLovId().intValue() == 789
//						|| vo.getLovId().intValue() == 776
//						|| vo.getLovId().intValue() == 82
						||vo.getLovId().intValue() == 524
						//||vo.getLovId().intValue() == 405
						) {
					listString.add(vo.getLovValue() + ":" + vo.getLovDesc());
				} else if (vo.getLovId().intValue() == 402) {
					listString.add(vo.getLovValue() + "-" + vo.getLovDesc());
				} else if (vo.getLovId().intValue() == 403) {
					listString.add(vo.getLovValue() + "-" + vo.getLovDesc());
				} else if (vo.getLovId().intValue() == Integer
						.parseInt(LovType.STANDARD_UOM.getLovId())) {
					listString.add(vo.getLovValue() + "-" + vo.getLovDesc());
				} else {
					listString.add(vo.getLovDesc());
				}
			}
		}
		return listString;
	}

	private List<String> getLovList(LovType lovType) {
		// List<String> listString = new ArrayList<>();
		List<LovVo> list = lovService.getLovListByLovType(lovType);

		return listString(list);
	}

	private List<String> getColorGroups() {
		return getLovList(LovType.COLOR_GROUP);
	}

	private List<String> getUomList() {
		return getLovList(LovType.STANDARD_UOM);
	}

	private List<String> getOriginCountriesList() {
		return getLovList(LovType.ORIGIN_COUNTRY);
	}

	private List<String> getDeptClassSubclassList(String supplierId) {
		List<String> listString = new ArrayList<>();

		List<DeptClassSubclassModel> list = deptService.getDepClassSubClassList(supplierId);

		if (CollectionUtils.isNotEmpty(list)) {
			for (DeptClassSubclassModel vo : list) {
				listString.add(vo.getDescription());
			}
		}

		return listString;
	}

	private List<String> geteCatList(String supplierId) {
		return getCatList(supplierId);
	}

	private List<String> geteCbmUomList() {
		return getLovList(LovType.UOM_CBM);
	}

	private List<String> geteWeightUomList() {
		return getLovList(LovType.UOM_WEIGHT);
	}

	private List<String> geteBarcodeTypeList() {
		return getLovList(LovType.ITEM_NUM_TYPE);
	}

	private List<String> getPackageList() {
		return getLovList(LovType.PACKAGE);
	}

	private List<String> getPackedInList() {
		return getLovList(LovType.PACKED_IN);
	}

	private List<String> geteNutriLabelList() {
		List<String> listString = new ArrayList<>();
		listString.add(E);
		listString.add(W);
		return listString;
	}

	private List<String> geteExpensiveList() {
		List<String> listString = new ArrayList<>();
		listString.add(Y);
		listString.add(N);
		return listString;
	}

	private List<String> getDeliveryModeList() {
		List<String> listString = new ArrayList<>();
		listString.add(SUPPLIER_DIRECT_DELIVERY);
		listString.add(CONSIGNMENT_VIA_WAREHOUSE);
		listString.add(CONSIGNMENT);
		return listString;
	}

	private List<String> getConsignCalcBasisList() {
		List<String> listString = new ArrayList<>();
		listString.add(AMOUNT_NET);
		listString.add(AMOUNT_GROSS);
		return listString;
	}
	
	private List<String> getPNSDeliveryType(){		
		return getLovList(LovType.ONLINE_ITEM_DELIVERY_TYPE);
	}

	@Override
	public String generateReportName(String name) {
		return super.getDateXlsxStr(name);
	}

}
