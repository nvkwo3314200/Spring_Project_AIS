package com.mall.b2bp.vos.supplier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mall.b2bp.vos.brand.BrandVo;

/**
 * Created by USER on 2016/3/10.
 */
public class SupplierVo {

    private String id;

    private String name;

    private boolean ticked;

    private BigDecimal deliveryFee;

    private BigDecimal freeDeliveryThreshold;

    private BigDecimal minDeliveryDay;

    private BigDecimal maxDeliveryDay;

    private BigDecimal consignmentVia;

    private BigDecimal warehouseDeliLeadTime;

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
    
    private Date productDefaultOnlineDate;
    
    private List<BrandVo> brandVo; 
    private String failedReason;
    private boolean disableDeliveryFee;
    
    
    
    public Date getProductDefaultOnlineDate() {
		return productDefaultOnlineDate;
	}

	public void setProductDefaultOnlineDate(Date productDefaultOnlineDate) {
		this.productDefaultOnlineDate = productDefaultOnlineDate;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getFreeDeliveryThreshold() {
        return freeDeliveryThreshold;
    }

    public void setFreeDeliveryThreshold(BigDecimal freeDeliveryThreshold) {
        this.freeDeliveryThreshold = freeDeliveryThreshold;
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }

    public BigDecimal getMinDeliveryDay() {
        return minDeliveryDay;
    }

    public void setMinDeliveryDay(BigDecimal minDeliveryDay) {
        this.minDeliveryDay = minDeliveryDay;
    }

    public BigDecimal getMaxDeliveryDay() {
        return maxDeliveryDay;
    }

    public void setMaxDeliveryDay(BigDecimal maxDeliveryDay) {
        this.maxDeliveryDay = maxDeliveryDay;
    }

    public BigDecimal getConsignmentVia() {
        return consignmentVia;
    }

    public void setConsignmentVia(BigDecimal consignmentVia) {
        this.consignmentVia = consignmentVia;
    }

    public BigDecimal getWarehouseDeliLeadTime() {
        return warehouseDeliLeadTime;
    }

    public void setWarehouseDeliLeadTime(BigDecimal warehouseDeliLeadTime) {
        this.warehouseDeliLeadTime = warehouseDeliLeadTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
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

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public List<BrandVo> getBrandVo() {
		return brandVo;
	}

	public void setBrandVo(List<BrandVo> brandVo) {
		this.brandVo = brandVo;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public boolean isDisableDeliveryFee() {
		return disableDeliveryFee;
	}

	public void setDisableDeliveryFee(boolean disableDeliveryFee) {
		this.disableDeliveryFee = disableDeliveryFee;
	}


    
	
}
