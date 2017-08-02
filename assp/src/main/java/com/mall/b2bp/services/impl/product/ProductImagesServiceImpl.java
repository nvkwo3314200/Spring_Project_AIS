package com.mall.b2bp.services.impl.product;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.product.ProductImagesModelMapper;
import com.mall.b2bp.models.product.ProductImagesModel;
import com.mall.b2bp.populators.product.ProductImagesPopulator;
import com.mall.b2bp.services.product.ProductImagesService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductImagesVo;
import com.mall.b2bp.vos.product.ProductInfoVo;

@Service("productImagesService")
public class ProductImagesServiceImpl implements ProductImagesService{
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductImagesServiceImpl.class);

	@Autowired
	private ProductImagesModelMapper productImagesModelMapper;
	@Override
	public int deleteByPrimaryKey(BigDecimal id) {
		String path = ResourceUtil.getSystemConfig().getProperty("upload_file_path");
		ProductImagesModel model= productImagesModelMapper.selectByPrimaryKey(id);
		
		if(model!=null){
			String sPath=path+File.separator+model.getFilePath();
			if(StringUtils.isNotEmpty(sPath)){
				try {
					FileUtils.forceDelete(new File(sPath));
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		
		
		return productImagesModelMapper.deleteByPrimaryKey(id);
	}
	

	@Override
	public boolean validateImages(BigDecimal productId,List<ProductImagesVo> list,ResponseData<ProductInfoVo> responseData) {
		// TODO Auto-generated method stub
		if(productId==null)
			return true;
		
		boolean flag=false;
		
		   Map map=new HashMap();
		   map.put("productId", productId);
		   map.put("imageType", ConstantUtil.PRODUCT_IMAGE_TYPE);
		List<ProductImagesModel> listModel=productImagesModelMapper.getProductImagesByProductId(map);
		if(listModel==null||listModel.isEmpty()){
			return true;
		}
		if(list!=null&&list.size()>=listModel.size()){
			flag=true;
		}else{
			responseData.add("product_images_invalid_picture_number");
			String []args=new String[1];
			args[0]=Integer.toString(listModel.size());
	
			responseData.putMessagesParamArray("product_images_invalid_picture_number", args);
		}
		return flag;
	}
	
	@Override
	public boolean validateSwatchImages(BigDecimal productId,List<ProductImagesVo> list,ResponseData<ProductInfoVo> responseData) {
		if(productId==null)
			return true;
		
		boolean flag=false;
		
		Map map=new HashMap();
		map.put("productId", productId);
		map.put("imageType", ConstantUtil.SWATCH_IMAGE_TYPE);
		List<ProductImagesModel> listModel=productImagesModelMapper.getProductImagesByProductId(map);
		if(listModel.isEmpty()){
			return true;
		}
		if(list!=null&&list.size()>=listModel.size()){
			flag=true;
		}else{
			responseData.add("product_images_swatch_invalid_picture_number");
			String []args=new String[1];
			args[0]=Integer.toString(listModel.size());
			
			responseData.putMessagesParamArray("product_images_swatch_invalid_picture_number", args);
		}
		return flag;
	}



	
	@Override
	public int deleteByProductId(BigDecimal id) {
		String path = ResourceUtil.getSystemConfig().getProperty("upload_file_path");
		   Map map=new HashMap();
		   map.put("productId", id);
		   map.put("imageType", ConstantUtil.PRODUCT_IMAGE_TYPE);
		List<ProductImagesModel> listModel=productImagesModelMapper.getProductImagesByProductId(map);
		if(listModel.isEmpty()){
			return 1;
		}
		try{
			for (ProductImagesModel productImagesModel : listModel) {
				String sPath=path+File.separator+productImagesModel.getFilePath();
				if(StringUtils.isNotEmpty(sPath)  && new File(sPath).exists()){
					try {
						FileUtils.forceDelete(new File(sPath));
					} catch (IOException e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
		}catch(Exception ex){
			LOG.warn("error:"+ex.getMessage(),ex);
		}
		
		
		return productImagesModelMapper.deleteByProductId(id);
	}
	
	@Override
	public List<ProductImagesVo> getProductImagesByProductId(BigDecimal productId,String imageType) {
		 List<ProductImagesVo> listVo=new ArrayList<>();
			ProductImagesPopulator populator=new ProductImagesPopulator();
			   Map map=new HashMap();
			   map.put("productId", productId);
			   map.put("imageType",imageType);
		List<ProductImagesModel> listModel=productImagesModelMapper.getProductImagesByProductId(map);
		if(listModel!=null&&!listModel.isEmpty()){
			for (ProductImagesModel productImagesModel : listModel) {
				listVo.add(populator.converModelToVo(productImagesModel));
			}
			
		}
 		return listVo;
	}
	
	@Override
	public List<ProductImagesVo> getProductImagesBySkutId(String productCode,String imageType) {
		List<ProductImagesVo> listVo=new ArrayList<>();
		ProductImagesPopulator populator=new ProductImagesPopulator();
		Map map=new HashMap();
		map.put("productCode", productCode);
		map.put("imageType",imageType);
		List<ProductImagesModel> listModel=productImagesModelMapper.getProductImagesBySkutId(map);
		if(listModel!=null&&!listModel.isEmpty()){
			for (ProductImagesModel productImagesModel : listModel) {
				listVo.add(populator.converModelToVo(productImagesModel));
			}
			
		}
		return listVo;
	}

	public ProductImagesModelMapper getProductImagesModelMapper() {
		return productImagesModelMapper;
	}

	public void setProductImagesModelMapper(
			ProductImagesModelMapper productImagesModelMapper) {
		this.productImagesModelMapper = productImagesModelMapper;
	}
	@Override
	public boolean addAllProductImages(String createdBy,BigDecimal productId,List<ProductImagesVo> productImages,String imageType) {
		boolean flag=true;
		if(productImages!=null&&!productImages.isEmpty()){
			Date currentDate=new Date();
			ProductImagesPopulator populator=new ProductImagesPopulator();
			int sequence=1;
			for (ProductImagesVo productImagesVo : productImages) {
				if(productImagesVo.getId()==null){
					
					productImagesVo.setProductId(productId);
					productImagesVo.setCreatedBy(createdBy);
					productImagesVo.setCreatedDate(currentDate);
					productImagesVo.setLastUpdatedBy(createdBy);
					productImagesVo.setLastUpdatedDate(currentDate);
					productImagesVo.setSequence(Long.valueOf(sequence));
					productImagesVo.setImageType(imageType);
					
					ProductImagesModel imageModel=populator.converVoToModel(productImagesVo);
					this.productImagesModelMapper.insertSelective(imageModel);
			
				}else{
					ProductImagesModel imageModel=productImagesModelMapper.selectByPrimaryKey(productImagesVo.getId());
					if(imageModel != null){
						if(imageModel.getSequence().intValue() == sequence && StringUtils.equals(imageModel.getDescription(), productImagesVo.getDescription())){
							sequence++;
							continue;
						}
						
						if(imageModel.getSequence().intValue() != sequence){
							imageModel.setSequence(Long.valueOf(sequence));
							imageModel.setLastUpdatedDate(currentDate);
						}
						imageModel.setLastUpdatedBy(createdBy);
						imageModel.setDescription(productImagesVo.getDescription());
						imageModel.setImageType(imageType);
						this.productImagesModelMapper.updateByPrimaryKeySelective(imageModel);
					}
				}
				sequence++;
		

				
			}
		}
		
		return flag;
	}
					
}
