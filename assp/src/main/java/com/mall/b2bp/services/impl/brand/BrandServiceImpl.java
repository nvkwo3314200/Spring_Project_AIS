package com.mall.b2bp.services.impl.brand;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.brand.BrandModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.brand.BrandModel;
import com.mall.b2bp.populators.brand.BrandPopulater;
import com.mall.b2bp.services.brand.BrandService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.StringUtil;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.brand.BrandVo;
import com.mall.b2bp.vos.user.SupplierBrandVo;
import com.mall.b2bp.vos.user.UserVo;

@Service("brandService")
public class BrandServiceImpl implements BrandService {

	private static final Logger LOG = LoggerFactory
			.getLogger(BrandServiceImpl.class);
	private static final String[] FILE_HEADER = { "ID", "Req_Flag", "Req_Date",
			"Sys_Ref", "Brand_Code", "ENDescription", "TCDescription",
			"SCDescription", "MasterId" };

	private BrandModelMapper brandModelMapper;

	public BrandModelMapper getBrandModelMapper() {
		return brandModelMapper;
	}

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Autowired
	public void setBrandModelMapper(BrandModelMapper brandModelMapper) {
		this.brandModelMapper = brandModelMapper;
	}

	@Override
	public BrandModel selectByPrimaryKey(BigDecimal brandCode) {
		return brandModelMapper.selectByPrimaryKey(brandCode);
	}

	@Override
	public int updateByPrimaryKeySelective(BrandModel record) {
		return brandModelMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(BrandModel record) throws ServiceException {
		try {
			return brandModelMapper.insertSelective(record);
		} catch (Exception e) {

			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<BrandVo> view(String supplierId, String brandCode,
			String descEn, String descTc, String descSc, String sysRef,
			String masterId, String watsonsMallInd) throws ServiceException {
		List<BrandVo> brandList = new ArrayList<>();

		BrandVo brandVo = new BrandVo();
		brandVo.setBrandCodeStr(brandCode);
		brandVo.setDescEn(descEn);
		brandVo.setDescTc(descTc);
		brandVo.setDescSc(descSc);
		brandVo.setSysRef(sysRef);
		brandVo.setMasterIdStr(masterId);
		brandVo.setWatsonsMallInd(watsonsMallInd);
		try {
			List<BrandModel> list = brandModelMapper
					.view_brand_supplier(brandVo);
			BrandPopulater po = new BrandPopulater();
			if (CollectionUtils.isNotEmpty(list)) {
				for (BrandModel b : list) {
					brandList.add(po.converModelToVo(b));
				}
			}
			return brandList;

		} catch (Exception e) {

			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);

		}
	}

	@Override
	public BrandVo view(String brandCode) throws ServiceException {

		if (StringUtils.isNotEmpty(brandCode)) {
			try {
				BrandModel b = selectByPrimaryKey(new BigDecimal(brandCode));

				BrandPopulater po = new BrandPopulater();
				return po.converModelToVo(b);
			} catch (Exception e) {

				LOG.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage(), e);

			}
		}

		return null;

	}

	@Override
	public boolean importBrandMastor(String fileName, ErrorLog errorLog)
			throws ServiceException {
		try {
			boolean success = true;
			List<BrandModel> list = readCsvFile(fileName, errorLog);

			for (BrandModel brand : list) {

				if (brand.getCode() == null || brand.getMasterId() == null) {
					success = false;
					continue;
				}

				brand.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				brand.setLastUpdatedDate(new Date());

				if (CollectionUtils.isNotEmpty(selectByPk(brand.getCode()
						.toString(), brand.getMasterId().toString()))) {
					// update
					updateByPrimaryKeySelective(brand);

				} else {
					// insert

					brand.setCreatedBy(ConstantUtil.JOB_USER_NAME);
					brand.setCreatedDate(new Date());
					insertSelective(brand);
				}

			}

			return success;
		} catch (Exception e) {

			LOG.error(e.getMessage(), e);
			errorLog.add("Throw Excepton:" + e.getMessage());
			throw new ServiceException(e.getMessage(), e);

		}

	}

	/**
	 * @param fileName
	 * 
	 *            ID|Req Flag|Req Date|Sys Ref|Brand
	 *            Code|ENDescription|TCDescription|SCDescription|MasterId
	 * 
	 *            20|C|05052016|RMS|36|BABY ORGANIX|||102
	 * @throws Exception
	 */

	private List<BrandModel> readCsvFile(String fileName, ErrorLog errorLog)
			throws ServiceException {
		List<BrandModel> list = new ArrayList<>();

		BrandModel brand = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter('|')
				.withHeader(FILE_HEADER).withQuote(null)
				.withIgnoreEmptyLines(true);
		try (FileReader fileReader = new FileReader(fileName);
				CSVParser csvFileParser = new CSVParser(fileReader,
						csvFileFormat);) {
			List<CSVRecord> csvRecords = csvFileParser.getRecords();
			for (int i = 1; i < csvRecords.size(); i++) {

				CSVRecord record = csvRecords.get(i);
				brand = new BrandModel();

				// String brandCode = record.get("ID");
				String sysRef = record.get("Sys_Ref");
				String code = record.get("Brand_Code");
				String en = record.get("ENDescription");
				String tc = record.get("TCDescription");
				String sc = record.get("SCDescription");
				String masterId = record.get("MasterId");

				brand.setSysRef(sysRef);

				// if (StringUtils.isNotEmpty(brandCode)) {
				// if (NumberUtils.isNumber(brandCode)) {
				// brand.setBrandCode(new BigDecimal(brandCode));
				// } else {
				// errorLog.add("Line :" + i + "ID:" + brandCode
				// + " is not a valid number");
				// }
				// } else {
				// errorLog.add("Line :" + i + ", ID is empty");
				// }

				if (StringUtils.isNotEmpty(code)) {
					if (NumberUtils.isNumber(code)) {
						brand.setCode(new BigDecimal(code));
					} else {
						errorLog.add("Line :" + i + ",Brand Code:" + code
								+ " is not a valid number");
					}
				} else {
					errorLog.add("Line :" + i + ",Brand Code is empty");
				}

				brand.setDescEn(en);
				brand.setDescSc(sc);
				brand.setDescTc(tc);
				if (StringUtils.isNotEmpty(masterId)) {
					if (NumberUtils.isNumber(masterId)) {
						brand.setMasterId(new BigDecimal(masterId));
					} else {
						errorLog.add("Line :" + i + ",MasterId:" + masterId
								+ " is not a valid number");
					}
				} else {
					errorLog.add("Line :" + i + ",MasterId is empty");
				}
				list.add(brand);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<BrandModel> getBrandsBySupplierId(String supplierId) {
		return brandModelMapper.getBrandsBySupplierId(supplierId);
	}

	@Override
	public List<BrandModel> selectAllBrandList() {
		List<BrandModel> list = brandModelMapper.selectAllBrandList();
		return list;
	}

	@Override
	public List<SupplierBrandVo> selectBrandList(String supplierId) {

		BrandVo vo = new BrandVo();
		vo.setSupplierId(supplierId);
		List<SupplierBrandVo> supplierBrandVos = new ArrayList<>();
		List<BrandModel> list = brandModelMapper.selectBrandList(vo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (BrandModel brandModel : list) {
				SupplierBrandVo supplierBrandVo = new SupplierBrandVo();
				supplierBrandVo.setBrandCode(brandModel.getBrandCode());
				supplierBrandVo.setDescEn(brandModel.getDescEn());
				supplierBrandVo.setCode(brandModel.getCode());
				supplierBrandVo.setMasterId(brandModel.getMasterId());

				supplierBrandVos.add(supplierBrandVo);
			}
		}
		return supplierBrandVos;
	}

	@Override
	public List<BrandVo> selectByPk(String brandCode, String masterId)
			throws ServiceException {
		List<BrandVo> brandList = new ArrayList<>();
		BrandVo brandVo = new BrandVo();
		brandVo.setBrandCodeStr(brandCode);

		brandVo.setMasterIdStr(masterId);
		try {
			List<BrandModel> list = brandModelMapper.selectByPk(brandVo);
			BrandPopulater po = new BrandPopulater();
			if (CollectionUtils.isNotEmpty(list)) {
				for (BrandModel b : list) {
					brandList.add(po.converModelToVo(b));
				}
			}
			return brandList;

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BrandModel getBrandModelByDescEn(String descEn) {
		List<BrandModel> brandList = brandModelMapper
				.getBrandModelByDescEn(descEn);
		if (brandList != null && !brandList.isEmpty()) {
			return brandList.get(0);
		}
		return null;
	}

	@Override
	public List<BrandVo> getAllBrandsBySupplierId(String supplierId) {
		List<BrandVo> brandList = new ArrayList<>();
		List<BrandModel> list = brandModelMapper
				.getAllBrandsBySupplierId(supplierId);

		BrandPopulater po = new BrandPopulater();
		if (CollectionUtils.isNotEmpty(list)) {
			for (BrandModel b : list) {
				brandList.add(po.converModelToVo(b));
			}
		}
		return brandList;
	}

	private boolean checkByteLeng(String v, int leng) {

		boolean f = false;
		if (StringUtils.isNotEmpty(v)
				&& (StringUtil.repalceAllNewLineToBr(v).length() > leng || StringUtil
						.repalceAllNewLineToBr(v).getBytes().length > leng)) {
			f = true;
		}

		return f;

	}
	
	private boolean checkBrandDescName(BrandVo brandVo, ResponseData<BrandVo> responseData){
		boolean validateResult = true;
		
		//  Brand tagline input in EN, TC and SC, which is a free text entry limited to 50 characters
					if (checkByteLeng(brandVo.getBrandTaglineEn(), 50)) {
						responseData.add("brand_tagline_Warningsen_length_err");
						validateResult = false;
					}
					
					if (checkByteLeng(brandVo.getBrandTaglineTc(), 50)) {
						responseData.add("brand_tagline_Warningstc_length_err");
						validateResult = false;
					}
					
					if (checkByteLeng(brandVo.getBrandTaglineSc(), 50)) {
						responseData.add("brand_tagline_Warningssc_length_err");
						validateResult = false;
					}
					
					//	Brand description in EN, TC and SC, multiple line text field, limited to 4000 bytes
					
					if (checkByteLeng(brandVo.getBrandDescEn(), 4000)) {
						responseData.add("brand_description_Warningsen_length_err");
						validateResult = false;
					}
					
					if (checkByteLeng(brandVo.getBrandDescTc(), 4000)) {
						responseData.add("brand_description_Warningstc_length_err");
						validateResult = false;
					}
					
					if (checkByteLeng(brandVo.getBrandDescSc(), 4000)) {
						responseData.add("brand_description_Warningssc_length_err");
						validateResult = false;
					}
					
					
					return validateResult;
					
	}

	@Override
	public boolean updateBrand(BrandVo brandVo, ResponseData<BrandVo> responseData)throws ServiceException {
		if (brandVo == null)
			return false;
		
		if(!checkBrandDescName( brandVo,  responseData)){
			return false;
		}
		
		try {
			UserVo userVo = sessionService.getCurrentUser();
			String name = null;
			if (userVo != null)
				name = userVo.getUserId();
			

			BrandModel brand = new BrandModel();
			brand.setLastUpdatedBy(name);
			brand.setLastUpdatedDate(new Date());

			if (StringUtils.isNotEmpty(brandVo.getMasterIdStr()))
				brand.setMasterId(new BigDecimal(brandVo.getMasterIdStr()));

			if (StringUtils.isNotEmpty(brandVo.getCodeStr()))
				brand.setCode(new BigDecimal(brandVo.getCodeStr()));
			brand.setImageFileName(null);

			
			

			brand.setBrandNameEn(brandVo.getBrandNameEn());
			brand.setBrandNameTc(brandVo.getBrandNameTc());
			brand.setBrandNameSc(brandVo.getBrandNameSc());

			brand.setBrandDescEn(brandVo.getBrandDescEn());
			brand.setBrandDescTc(brandVo.getBrandDescTc());
			brand.setBrandDescSc(brandVo.getBrandDescSc());

			brand.setBrandTaglineEn(brandVo.getBrandTaglineEn());
			brand.setBrandTaglineTc(brandVo.getBrandTaglineTc());
			brand.setBrandTaglineSc(brandVo.getBrandTaglineSc());

			updateByPrimaryKeySelective(brand);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		
		return true;

	}

	@Override
	public Map<String, Object> updateWatsonsMallInd(String ind,
			List<BrandVo> list) throws ServiceException {

		Map<String, Object> map = new HashMap<String, Object>();
		int success = 0;
		int failure = 0;
		boolean pun = false;
		StringBuffer str = new StringBuffer();

		if (CollectionUtils.isEmpty(list))

			return null;

		if (StringUtils.isEmpty(ind))
			ind = "N";

		UserVo userVo = sessionService.getCurrentUser();
		String name = null;
		if (userVo != null)
			name = userVo.getUserId();

		try {
			for (BrandVo vo : list) {

				if (StringUtils.isNotEmpty(vo.getMasterIdStr())
						&& StringUtils.isNotEmpty(vo.getCodeStr())) {

					BrandModel brand = new BrandModel();
					brand.setLastUpdatedBy(name);
					brand.setLastUpdatedDate(new Date());
					brand.setWatsonsMallInd(ind);

					brand.setMasterId(new BigDecimal(vo.getMasterIdStr()));
					brand.setCode(new BigDecimal(vo.getCodeStr()));
					int flag = brandModelMapper.updateWatsonsMallInd(brand);
					// string :0 code ,mastid un/set.... successful..
					if (flag == 1) {
						success++;
					} else {
						failure++;
						if (pun) {
							str.append(" , ");
						}
						;
						str.append(vo.getCodeStr() + "-" + vo.getMasterIdStr());
						pun = true;
					}

				}
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

		// System.out.println("success："+success+"  ；  "+"failure："+failure);
		// System.out.println("FailureName："+str);

		map.put("success", success);
		map.put("failure", failure);
		map.put("failureBrandCode", str);

		return map;
	}

}
