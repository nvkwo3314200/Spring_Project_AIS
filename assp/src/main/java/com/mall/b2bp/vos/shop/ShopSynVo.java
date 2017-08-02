package com.mall.b2bp.vos.shop;

import com.mall.b2bp.vos.product.ProductImagesVo;

public class ShopSynVo {

	private String code;
    private String name;

    private String description;
    private ProductImagesVo imageVo;
    
    private String line4;
    private String line1;
    private String line2;
    private String line3;
    
    

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public ProductImagesVo getImageVo() {
		return imageVo;
	}

	public void setImageVo(ProductImagesVo imageVo) {
		this.imageVo = imageVo;
	}

   

}
