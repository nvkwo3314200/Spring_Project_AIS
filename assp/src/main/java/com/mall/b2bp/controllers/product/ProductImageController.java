package com.mall.b2bp.controllers.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.FileHandle;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.product.ProductImagesVo;

/**
 * Created by USER on 2016/8/20.
 */
public class ProductImageController extends BaseConroller {

    private static final Logger log = LoggerFactory.getLogger(ProductImageController.class);

    public ProductImagesVo upload(String fileItemName, InputStream is, String path, ResponseData rd) throws SystemException {
        ProductImagesVo image = new ProductImagesVo();
        String sname =fileItemName;// fileItem.getName();
        if (sname == null)
            sname = "";
        String fileType = sname.substring(sname.indexOf("."), sname.length());
        String uploadFileName = Long.toString(System.currentTimeMillis()) + "" + fileType;

        File uploadPath = new File(path);
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        String filepath = path + File.separator + uploadFileName;
        image.setFileName(sname);
        image.setFilePath(uploadFileName);
        File savedFileName = new File(filepath);
        try (
                //InputStream is = new BufferedInputStream(fileItem.getInputStream());
                FileOutputStream fos = new FileOutputStream(savedFileName);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
        ){
        		boolean oktype= false;
            if (sname != null && sname.indexOf(".") != -1) {
                if (sname.lastIndexOf(".jpg") != -1){
//            final String mimeType = URLConnection.guessContentTypeFromStream(is);
//            if(!"image/jpeg".equalsIgnoreCase(mimeType)){
               oktype = true;
            }
            }
            if(!oktype) {
                rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                rd.add("product_images_invalid_picture_type");
                return image;
            }

            byte[] buffer = new byte[1024];
            int len;

            while ((len = is.read(buffer)) >= 0) {
                bos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new SystemException(e.getMessage(),e);
        }

        try {
            String formatPx = "1200x1200";
            String str = FileHandle.getResolution1(savedFileName);
            if (!formatPx.equals(str)) {
                rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                rd.add("product_images_invalid_picture_element");
                rd.putMessagesParamArray(
                        "product_images_invalid_picture_element",
                        new String[]{ //fileItem.getName() });
                                fileItemName});

                log.error("fileItemName:"+fileItemName +"  Image size should be 1200px * 1200px.");
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            rd.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            rd.add("product_images_invalid_picture_type");
        }

        return image;
    }


    private String mime = "jpeg";
    private String[] filenames = new String[]{"1200Wx1200H", "515Wx515H", "300Wx300H", "96Wx96H", "65Wx65H", "30Wx30H"};
    private Integer[] widths = new Integer[]{1200, 515, 300, 96, 65, 30};
    protected void sizeImage(List<ProductImagesVo> imagesList) throws IOException {

        if(CollectionUtils.isEmpty(imagesList))
            return;


            String outPath = ResourceUtil.getSystemConfig().getProperty("upload_file_resizepath");
            for(ProductImagesVo imagesVo : imagesList){
                for(int i=0 ; i<filenames.length ; i++){

                    //���貢����ͼƬ

                    String ext = "jpeg";
                    String mediaCode = imagesVo.getFilePath();
                    if(imagesVo.getFilePath().indexOf(".")>-1){
                        mediaCode = imagesVo.getFilePath().substring(0,imagesVo.getFilePath().lastIndexOf("."));
                        ext = imagesVo.getFilePath().substring(imagesVo.getFilePath().lastIndexOf(".")+1);
                        // LOG.info("ext:"+ext);
                    }

                    //����ͼƬ����
                    String newfilename=mediaCode+"_"+ filenames[i].toLowerCase()+"."+ext;

                    FileHandle.resizeAndSetImage(imagesVo.getFilePath(), newfilename, (int) widths[i], (int) widths[i], mime, outPath);
                }
            }
        }

}
