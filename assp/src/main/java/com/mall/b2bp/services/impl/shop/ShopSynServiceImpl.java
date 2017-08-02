package com.mall.b2bp.services.impl.shop;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mall.b2bp.services.parm.ParmService;
import com.mall.b2bp.services.shop.ShopSynService;
import com.mall.b2bp.services.webservice.UploadProductResponse;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.product.ProductImagesVo;
import com.mall.b2bp.vos.shop.CategoryListWsDTO;
import com.mall.b2bp.vos.shop.ShopSynVo;

@Service("shopSynService")
public class ShopSynServiceImpl implements ShopSynService {

	private static final Logger LOG = LoggerFactory.getLogger(ShopSynServiceImpl.class);

	@Resource(name="parmService")
	ParmService parmService;
	
	 public static void main(String[] args) {
	 
	 ShopSynServiceImpl serviceImpl = new ShopSynServiceImpl();
	 //
	 CategoryListWsDTO categoryListWsDTO= serviceImpl.getAllCategoriesForTreeGrid();
	 System.out.println(categoryListWsDTO);
//	 ShopSynVo shopVo = new ShopSynVo ();
//	
//	 shopVo.setCode("city2");//id
//	 shopVo.setName("city2");
//	 shopVo.setDescription(" City 2");
//	 shopVo.setLine1("line1===");
//	 shopVo.setLine2("line2===");
//	 shopVo.setLine3("line3==");
//	 shopVo.setLine4("line4===");
//	
//	 ProductImagesVo imageVo = new ProductImagesVo ();
//	 imageVo.setFilePath("1469687347293.jpg");
//	 shopVo.setImageVo(imageVo);
//	
//	 serviceImpl.uppdateShop(shopVo);
	 }
	
	@Override
	public boolean uppdateShop(ShopSynVo shopVo) {

		StringBuffer uri  = new StringBuffer();
		uri.append(parmService.getWebserviceUrl());
		uri.append("shops/updateShop");

		LOG.info("*end*******************************");
		LOG.info("call webservice:" + uri);

		if (shopVo == null)
			return false;

		/**
		 * 上传多格式图片到图片服务器 要求 FilePath 格式 例如(xxx.jpeg)
		 */

		ProductImagesVo imagesVo = shopVo.getImageVo();
		if (imagesVo != null && StringUtils.isNotEmpty(imagesVo.getFilePath())) {
			try {
				resizepathImage(imagesVo);
			} catch (Exception ex) {
				LOG.error(ex.getMessage(), ex);
			}
		}

		RestTemplate restTemplate = new RestTemplate();

		UploadProductResponse result = restTemplate.postForObject(uri.toString(), shopVo, UploadProductResponse.class);
		if (result != null)
			LOG.info("end call webservice result:" + result.getResponseCode());

		LOG.info("*end*******************************");
		if (result.getResponseCode().equals("S00001"))
			return true;
		return false;
	}

	private String mime = "jpeg";
	private String[] filenames = new String[] { "1200Wx1200H", "515Wx515H", "300Wx300H", "96Wx96H", "65Wx65H",
			"30Wx30H" };
	private Integer[] widths = new Integer[] { 1200, 515, 300, 96, 65, 30 };

	private void resizepathImage(ProductImagesVo imagesVo) throws IOException {

	
		String outPath = ResourceUtil.getSystemConfig().getProperty("upload_file_resizepath");

		if (imagesVo == null)
			return;

		for (int i = 0; i < filenames.length; i++) {
			// 重设并保存图片
			String ext = "jpeg";
			String mediaCode = imagesVo.getFilePath();
			if (imagesVo.getFilePath().indexOf(".") > -1) {
				mediaCode = imagesVo.getFilePath().substring(0, imagesVo.getFilePath().lastIndexOf("."));
				ext = imagesVo.getFilePath().substring(imagesVo.getFilePath().lastIndexOf(".") + 1);
			}

			// 设置图片名称
			String newfilename = mediaCode + "_" + filenames[i].toLowerCase() + "." + ext;

			FileHandle.resizeAndSetImage(imagesVo.getFilePath(), newfilename, (int) widths[i], (int) widths[i], mime,
					outPath);
		}
	}

	@Override
	public CategoryListWsDTO getAllProduceCateloy() {

		 StringBuffer uri  = new StringBuffer();
			uri.append(parmService.getWebserviceUrl());
			uri.append("categories/all");
			
		LOG.info("*start*******************************");
		LOG.info("call webservice:" + uri);

		RestTemplate restTemplate = new RestTemplate();

		CategoryListWsDTO result = restTemplate.getForObject(uri.toString(), CategoryListWsDTO.class);

		LOG.info("return CategoryListWsDTO :" + result);
		LOG.info("*end*******************************");

		return result;
	}

	@Override
	public CategoryListWsDTO getAllCategoriesForTreeGrid() {
			

			 StringBuffer uri  = new StringBuffer();
				uri.append(parmService.getWebserviceUrl());
				uri.append("categories/treeGrid");
				
			LOG.info("*start*******************************");
			LOG.info("call webservice:" + uri);

			RestTemplate restTemplate = new RestTemplate();

			CategoryListWsDTO result = restTemplate.getForObject(uri.toString(), CategoryListWsDTO.class);

			LOG.info("return CategoryListWsDTO :" + result);
			LOG.info("*end*******************************");

			return result;
	}

	public ParmService getParmService() {
		return parmService;
	}

	public void setParmService(ParmService parmService) {
		this.parmService = parmService;
	}
	
	

}
