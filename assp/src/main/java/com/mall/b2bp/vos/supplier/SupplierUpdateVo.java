package com.mall.b2bp.vos.supplier;

import java.util.Date;

/**
 * Created by USER on 2016/3/10.
 */
public class SupplierUpdateVo {

    private String id;

    private String name;

    private boolean ticked;

    private String deliveryFee;

    private String freeDeliveryThreshold;

    private String minDeliveryDay;

    private String maxDeliveryDay;

    private String consignmentVia;

    private String warehouseDeliLeadTime;

    private String createdBy;

    private Date createdDate;

    private String lastUpdatedBy;

    private Date lastUpdatedDate;

    private String imageFileName;
    
    private String shopNameEn;
    
    private String shopNameTc;
    
    private String shopNameSc;
    
    private String shopDescEn;
    
    private String shopDescTc;
    
    private String shopDescSc;
    
    private String shopCategory;
   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getFreeDeliveryThreshold() {
        return freeDeliveryThreshold;
    }

    public void setFreeDeliveryThreshold(String freeDeliveryThreshold) {
        this.freeDeliveryThreshold = freeDeliveryThreshold;
    }

    public String getMinDeliveryDay() {
        return minDeliveryDay;
    }

    public void setMinDeliveryDay(String minDeliveryDay) {
        this.minDeliveryDay = minDeliveryDay;
    }

    public String getMaxDeliveryDay() {
        return maxDeliveryDay;
    }

    public void setMaxDeliveryDay(String maxDeliveryDay) {
        this.maxDeliveryDay = maxDeliveryDay;
    }

    public String getConsignmentVia() {
        return consignmentVia;
    }

    public void setConsignmentVia(String consignmentVia) {
        this.consignmentVia = consignmentVia;
    }

    public String getWarehouseDeliLeadTime() {
        return warehouseDeliLeadTime;
    }

    public void setWarehouseDeliLeadTime(String warehouseDeliLeadTime) {
        this.warehouseDeliLeadTime = warehouseDeliLeadTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getShopNameEn() {
		return shopNameEn;
	}

	public void setShopNameEn(String shopNameEn) {
		this.shopNameEn = shopNameEn;
	}

	public String getShopNameTc() {
		return shopNameTc;
	}

	public void setShopNameTc(String shopNameTc) {
		this.shopNameTc = shopNameTc;
	}

	public String getShopNameSc() {
		return shopNameSc;
	}

	public void setShopNameSc(String shopNameSc) {
		this.shopNameSc = shopNameSc;
	}

	public String getShopDescEn() {
		return shopDescEn;
	}

	public void setShopDescEn(String shopDescEn) {
		this.shopDescEn = shopDescEn;
	}

	public String getShopDescTc() {
		return shopDescTc;
	}

	public void setShopDescTc(String shopDescTc) {
		this.shopDescTc = shopDescTc;
	}

	public String getShopDescSc() {
		return shopDescSc;
	}

	public void setShopDescSc(String shopDescSc) {
		this.shopDescSc = shopDescSc;
	}

	public String getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}
	
	
}
