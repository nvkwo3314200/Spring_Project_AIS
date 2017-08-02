package com.mall.b2bp.services.impl.product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.controllers.product.ProductController;
import com.mall.b2bp.daos.product.ProductBarcodeModelMapper;
import com.mall.b2bp.models.product.ProductBarcodeModel;
import com.mall.b2bp.services.product.ProductBarcodeService;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductBarCode;
import com.mall.b2bp.vos.product.ProductInfoVo;

@Service("productBarcodeService")
public class ProductBarcodeServiceImpl implements ProductBarcodeService {
	private static final Logger LOG = LoggerFactory
			.getLogger(ProductController.class);
	private ProductBarcodeModelMapper productBarcodeMapper;

	public ProductBarcodeModelMapper getProductBarcodeMapper() {
		return productBarcodeMapper;
	}

	@Autowired
	public void setProductBarcodeMapper(
			ProductBarcodeModelMapper productBarcodeMapper) {
		this.productBarcodeMapper = productBarcodeMapper;
	}

	
	public List<ProductBarcodeModel> getAll() {
		return productBarcodeMapper.getAll();
	}

	@Override
	public int insert(ProductBarcodeModel productBarcode) {
		if (productBarcodeMapper.insert(productBarcode) == 1) {
			return 1;
		}
		return 0;
	}

	
	public int insertSelective(ProductBarcodeModel productBarcode) {
		if (productBarcodeMapper.insertSelective(productBarcode) == 1) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<ProductBarcodeModel> getProductBarcodeModelByBarcodeNum(
			Map<String,Object> map) {
		return productBarcodeMapper
				.getProductBarcodeModelByBarcodeNum(map);
	}

	/**
	 * 
	 * validate product bar code data
	 * 
	 * @param productInfoVo
	 * @param responseData
	 * @return boolean
	 */
	@Override
	public boolean validateProductBarCode(ProductInfoVo productInfoVo,
			ResponseData<ProductInfoVo> responseData) {

		boolean validateResult = true;

		if(productInfoVo==null){
			responseData.add("product_productInfovo_null");
			validateResult = false;
		}else{
			
			List<ProductBarCode> productBarCodeList = productInfoVo.getBarcodeList();
	
			for (ProductBarCode productBarCode : productBarCodeList) {
				
				if(StringUtils.isEmpty(productBarCode.getItemNumType())&&StringUtils.isEmpty(productBarCode.getBarcodeNum())){
					continue;
				}else if(StringUtils.isEmpty(productBarCode.getItemNumType())&& StringUtils.isNotEmpty(productBarCode.getBarcodeNum())) {
					responseData.add("product_save_itemnumbertype");
					validateResult = false;
				}else if (StringUtils.isNotEmpty(productBarCode.getItemNumType())&&StringUtils.isEmpty(productBarCode.getBarcodeNum())) {
					responseData.add("product_save_productbarcode");
					validateResult = false;
				}
				
				try {
						if(!validateResult){
							break;
						}
						Map<String , Object> map = new HashMap<>();
						
						if(productBarCode.getId()==null){
							map.put("barcodenum", productBarCode.getBarcodeNum());
						}else{
							map.put("id", productBarCode.getId());
							map.put("barcodenum", productBarCode.getBarcodeNum());
						}
						map.put("version", "STAGING");
						
						List<ProductBarcodeModel> productBarCodeModelList = productBarcodeMapper.getProductBarcodeModelByBarcodeNum(map);
						if (!productBarCodeModelList.isEmpty()) {
							responseData.add("product_save_barcodenum_exist");
							validateResult = false;
						}
				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
					validateResult = false;
					return validateResult;
				}
			}
		
		}

		return validateResult;

	}
	
	
	public boolean checkAllNumbers(String number){
		int length = number.length();
		for(int i=0; i<length; i++){
			byte ascii = (byte)number.charAt(i);
			if(ascii > 57 || ascii < 48){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public List<ProductBarcodeModel> getProductBarcodeModelsByProductId(
			String productId) {
		
		return productBarcodeMapper.getProductBarcodeModelsByProductId(productId);
	}

	@Override
	public void deleteProductBarcodeByProductId(String productId) {
           		productBarcodeMapper.deleteProductBarcodeByProductId(productId);
	}

	@Override
	public int updateByPrimaryKeySelective(ProductBarcodeModel record) {
		return productBarcodeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal id) {
		
		return productBarcodeMapper.deleteByPrimaryKey(id);
	}

}
