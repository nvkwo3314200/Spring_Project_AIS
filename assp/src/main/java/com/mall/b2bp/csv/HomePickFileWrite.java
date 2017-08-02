package com.mall.b2bp.csv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderEntryVo;
import com.mall.b2bp.vos.order.OrderVo;

/**
 * Created by USER on 2016/3/21.
 */
public class HomePickFileWrite extends AbstractCsvFileBuilder {


    public HomePickFileWrite(List<OrderVo> orderVoList) {
        super.orderVoList = orderVoList;
    }

    @Override
    protected String[] getHeader() {
        return new String[0];
    }

    @Override
    protected String generateFileName() {
    	String stroeId = "";
		if (CollectionUtils.isNotEmpty(orderVoList)) {
			OrderVo vo = orderVoList.get(0);
			stroeId = vo.getPickStore() != null ? vo.getPickStore().toString() : "";
		}

		return "Infinity_SP_HomePick_" + stroeId + "_" + DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + ".csv";
    }




    /**
     * 1	PickStore	Picking centre / eStore code
     * 2	PickOrderId	Picking order id for
     * 3	OrderId	Original Hybris order no
     * 4	TrckId	Tacking id from external system
     * 5	Packer	Packer name
     * 6	PackInfo	Packing UOM description
     * 7	RouteInfo	Route Information
     * 8	SequenceNumber	Product sequence number / counter
     * 9	SkuId	Sku code
     * 10	ProductName	Sku name
     * 11	Quantity	Picked quantity
     * 12	Date	Pick date
     * 13	BoxNumber	BoxNumber
     * 14	Carrier	Carrier Name
     * 15	TenderType	List of value of the payment Type
     *
     * @return
     */
    @Override
    public void generateRecordList(OrderVo vo, List<List<String>> allLines) {

        List<OrderEntryVo> entrys = vo.getEntryVoList();
        if (CollectionUtils.isEmpty(entrys)) {
            return;
        }


        for (OrderEntryVo entry : vo.getEntryVoList()) {
            List<String> recordList = new ArrayList<>();

            recordList.add(vo.getPickStore() != null ? vo.getPickStore().toString() : "");//1	PickStore
            recordList.add(vo.getPickOrderId());//2	PickOrderId
            recordList.add(vo.getHybrisOrderId());//3	OrderId
            recordList.add(vo.getTrackId());//4	TrckId
            recordList.add("Supplier"); //o	Packer
            recordList.add(null);//o	PackInfo
            recordList.add(null);//o	RouteInfo
            recordList.add(entry.getSeqNum() != null ? entry.getSeqNum().toString() : "");//8	SequenceNumber	Product sequence number / counter
            recordList.add(entry.getSkuId() );//9	SkuId
            recordList.add(entry.getProductName());//10	ProductName
            recordList.add(entry.getPickedQty() != null ? entry.getPickedQty().toString() : "");//11	Quantity
            recordList.add(DateUtils.formatDate(vo.getPickDate(), DateUtils.DATE_FORMATE_YYYYMMDD)); //12	Date	Pick date	Date	x	Format: YYYYMMDD
            recordList.add(vo.getBoxNum());//13	BoxNumber
            recordList.add(null);//14	Carrier
            recordList.add(vo.getTenderType());//15	TenderType

            allLines.add(recordList);
        }
    }




}
