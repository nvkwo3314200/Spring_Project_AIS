package com.mall.b2bp.controllers.product;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.services.product.ProductBatchUploadService;
import com.mall.b2bp.services.product.ProductImagesService;
import com.mall.b2bp.services.product.ProductInfoService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductImagesVo;
import com.mall.b2bp.vos.product.ProductInfoVo;
import com.mall.b2bp.vos.product.ProductParameterVo;
import com.mall.b2bp.vos.user.UserVo;


/**
 * Created by USER on 2016/4/7.
 */
@Controller("ProductBatchUploadController")
@RequestMapping(value = "/productBatchUpload")
public class ProductBatchUploadController extends ProductImageController {

    private static final String SUPPLIER = "SUPPLIER";

    private static final Logger LOG = LoggerFactory.getLogger(ProductBatchUploadController.class);

    @Resource(name = "productInfoService")
    private ProductInfoService productInfoService;

    @Resource(name = "sessionService")
    SessionService sessionService;

    @Resource(name = "productBatchUploadService")
    ProductBatchUploadService productBatchUploadService;
    

    @Resource(name = "productImagesService")
    ProductImagesService productImagesService;


    /**
     * upload zip file
     */
//    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
//    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = {"application/json"})
//    @ResponseBody
//    public List<ResponseData<ProductInfoVo>> upload(HttpServletRequest request) {
    public List<ResponseData<ProductInfoVo>> upload(java.io.InputStream inputStream) {
        ResponseData<ProductInfoVo> responseData = null;
        List<ResponseData<ProductInfoVo>> responseDataList1 = new ArrayList<>();
        try {
            // Error Msg
            responseData = (ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);
//
//
//            // Update User Search Restrictions
//
            //  Map map = productBatchUploadService.getFileItem(request);
            //  FileItem fileItem = (FileItem) map.get("file");
//            if (null == fileItem) {
//                responseData.add("file_not_fount_err");
//                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//                responseDataList1.add(responseData);
//                return responseDataList1;
//            }
//
//
            //Boolean flag = productBatchUploadService.checkFilePrefixXlsx(fileItem);
//            if (!flag) {
//                responseData.add("not_xlsx_file");
//                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
//                responseDataList1.add(responseData);
//                return responseDataList1;
//            }
            Sheet sheet = FileHandle.getSheet(inputStream);
            ;//productBatchUploadService.createWorkbookSheet(fileItem);

            if (sheet == null) {
                responseData.add("not_xlsx_file");
                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                responseDataList1.add(responseData);
                return responseDataList1;
            }
            String sheetName = sheet.getSheetName();
            if (!"ProductList".equals(sheetName)) {
                responseData.add("product_upload_sheetname_err");
                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                responseDataList1.add(responseData);
                return responseDataList1;
            }
            if (sheet.getLastRowNum() == 0) {
                responseData.add("product_upload_list_is_null");
                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                responseDataList1.add(responseData);
                return responseDataList1;
            }

            UserVo userVo = sessionService.getCurrentUser();
            int columnNum = sheet.getRow(0).getPhysicalNumberOfCells();


            if (SUPPLIER.equals(userVo.getUserRole())) {
                if (columnNum != 72) {
                    responseData.add("product_column_num_err");
                    responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                    responseDataList1.add(responseData);
                    return responseDataList1;
                }
                sheet.getLastRowNum();

                responseDataList1 = productInfoService.generateProductForSu(sheet);
            } else {
                //int columnNum=sheet.getRow(0).getPhysicalNumberOfCells();
                if (columnNum != 73) {
                    responseData.add("product_column_num_err");
                    responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                    responseDataList1.add(responseData);
                    return responseDataList1;
                }
                responseDataList1 = productInfoService.generateProductForAp(sheet);
            }
        } catch (Exception e) {

            responseData.add("system_error");
            responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            responseDataList1.add(responseData);
            LOG.error(e.getMessage(), e);
            // throw new SystemException(e.getMessage(), e);
        }
        return responseDataList1;
    }

  
    private final static String ZIP = "zip";

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER", "ROLE_APPROVER"})
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public List<ResponseData<ProductInfoVo>> unZip(HttpServletRequest request) {

        ResponseData<ProductInfoVo> responseData = null;
        List<ResponseData<ProductInfoVo>> responseDataList1 = new ArrayList<>();
        String temporaryDirectory = ResourceUtil.getSystemConfig().getProperty("temporaryDirectory");
        if (!new File(temporaryDirectory).exists()) {
            new File(temporaryDirectory).mkdir();
        }
        try {
            responseData = (ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);
            // Update User Search Restrictions

            Map map = productBatchUploadService.getFileItem(request);
            FileItem fileItem = (FileItem) map.get("file");
            if (null == fileItem) {
                responseData.add("file_not_fount_err");
                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                responseDataList1.add(responseData);
                return responseDataList1;
            }


            String name = fileItem.getName();
            boolean flag = false;
            if (org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
                String prefix = name.substring(name.lastIndexOf(".") + 1);
                if (ZIP.equals(prefix)) {
                    flag = true;
                }
            }
            if (!flag) {
                responseData.add("not_zip_file");
                responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                responseDataList1.add(responseData);
                return responseDataList1;
            }


            //LOG.info("Upload file name:" + fileItem.getFieldName());
            LOG.info("Upload file name:" + fileItem.getName());
            String filepath = temporaryDirectory + File.separator + "zip_" + System.currentTimeMillis() + ".zip";
            File file = new File(filepath);

            byte[] bytes = null;
            if (!fileItem.isFormField()) {
                bytes = fileItem.get();
                fileItem.getName();
            }
            if (bytes != null) {
                FileUtils.writeByteArrayToFile(file, bytes);
            }

            List<ResponseData<ProductInfoVo>>  ls = zipFileRead(filepath);
//            if ("danger".equals(responseData.getErrorType())) {
//                responseDataList1.add(responseData);
//            }
            
            if(ls!=null && ls.size()>0)
            	responseDataList1.addAll(ls);

        } catch (Exception e) {
            responseData.add("system_error");
            responseData.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            responseDataList1.add(responseData);
            LOG.error(e.getMessage(), e);
        }
        return responseDataList1;
    }


    private     List<ResponseData<ProductInfoVo>>  zipFileRead(String file) {
    	List<ResponseData<ProductInfoVo>> list = new ArrayList<ResponseData<ProductInfoVo>>();
        try {
            ZipFile zipFile = new ZipFile(file);
            @SuppressWarnings("unchecked")
            Enumeration<ZipEntry> enu = (Enumeration<ZipEntry>) zipFile.entries();

            ResponseData<ProductInfoVo> responseData =  (ResponseData<ProductInfoVo>) responseDataService.getReturnData(ProductInfoVo.class);
            LOG.info("******************* START ***************");
            List<ProductImagesVo> imagesList = new ArrayList<>();

            String path = ResourceUtil.getSystemConfig().getProperty("upload_file_path");
            while (enu.hasMoreElements()) {
                ZipEntry zipElement = (ZipEntry) enu.nextElement();
                InputStream read = zipFile.getInputStream(zipElement);
                String fileName = zipElement.getName();
                LOG.info("=============== fileName :" + fileName);
                if (fileName != null && fileName.indexOf(".") != -1) {

                    if (fileName.lastIndexOf(".jpg") != -1) {
                        //upload image...
                      //  LOG.info("handle image..... ");
                        ProductImagesVo vo = upload(fileName, read, path, responseData);
                        if (!"danger".equals(responseData.getErrorType())) {
                            imagesList.add(vo);
                        }
                    } else if (fileName.lastIndexOf(".xlsx") != -1) {
                    //  LOG.info("handle xmls.... ");
                        LOG.info("===========START UPLOAD XLSX====:"+fileName);
                      list.addAll(  upload(read));
                        LOG.info("===========END UPLOAD XLSX====:" + fileName);
                    }
                }
            }



            UserVo userVo = sessionService.getCurrentUser();
            String supplierCode = null;
            if (userVo != null)
                supplierCode = userVo.getSupplierId();
            LOG.info("===========START UPDATE IMAGE====");
            
            Map<String,List<ProductImagesVo>> map  = new HashedMap();
			for (ProductImagesVo vo : imagesList) {

				String fileName = vo.getFileName();
				String supplierProductCode = fileName.substring(0, fileName.indexOf("_"));
				
				if(StringUtils.isNotEmpty(supplierProductCode)  && supplierProductCode.lastIndexOf("/")!=-1){
					supplierProductCode =supplierProductCode.substring(supplierProductCode.lastIndexOf("/"));
				}
				if (map.get(supplierProductCode) == null) {
					List<ProductImagesVo> productImages = new ArrayList<>();
					productImages.add(vo);
					map.put(supplierProductCode, productImages);
				} else {
					List<ProductImagesVo> ls = map.get(supplierProductCode);
					ls.add(vo);
				
				}
			}
                
			if (map != null && map.size() > 0) {

				for (Map.Entry<String, List<ProductImagesVo>> entry : map.entrySet()) {

					String supplierProductCode = entry.getKey();

					List<ProductImagesVo> productImages = entry.getValue();
					
					ProductParameterVo parameterVo = new ProductParameterVo();
					parameterVo.setSupplierProductCode(supplierProductCode);
					parameterVo.setSupplierCode(supplierCode);
					List<ProductInfoModel> productInfoModelList = productInfoService
							.getProductBySupplierCode(parameterVo);

					if (CollectionUtils.isNotEmpty(productInfoModelList)) {
						for (ProductInfoModel model : productInfoModelList) {

							try {
								this.productImagesService.deleteByProductId(model.getId());
							} catch (Exception ex) {
								LOG.error("delete product error:" + ex.getMessage(), ex);
							}
							LOG.info("addAllProductImages ,product id:" + model.getId().toString() + ",supplierProductCode:"
									+ supplierProductCode);

							productImagesService.addAllProductImages(userVo.getUserId(), model.getId(), productImages,
									ConstantUtil.PRODUCT_IMAGE_TYPE);

						}
					}
				}

				LOG.info("===========END UPDATE IMAGE====");

			}
            LOG.info("******************* END ***************");
        } catch (Exception e) {
            LOG.error("zipFileRead erorr:" + e.getMessage(), e);
        }
        
        return list;
    }


}
