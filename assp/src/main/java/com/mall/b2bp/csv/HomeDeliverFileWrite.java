package com.mall.b2bp.csv;

import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.order.OrderEntryVo;
import com.mall.b2bp.vos.order.OrderVo;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by USER on 2016/3/21.
 */
public class HomeDeliverFileWrite extends AbstractCsvFileBuilder {

    
    public HomeDeliverFileWrite(List<OrderVo> orderVoList) {
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

        return "Infinity_SP_HomeDeliver_" + stroeId + "_" + DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + ".csv";
    }




    /**
     * 1	PickStore	Picking centre / eStore code
     * 2	PickOrderId	Picking order id for
     * 3	OrderId	Original Hybris order no
     * 4	DeliverSuccess	Y/N
     * 5	DeliverDate	Delivered Date
     * 6	CODOrderAmount	COD Total Order Amount (Null -> non-COD)
     * 7	CODActualReceived	COD Actual received amount(Null -> non-COD)
     * 8	CODDifferentRemark	Cash discrepancy remark
     * 9	SequenceNumber	Product sequence number / counter
     * 10	SkuId	Sku code
     * 11	OrderedQuantity	Order quantity
     * 12	ShippedQuantity	Actual Shipped Quantity
     * 13	DeliveredQuantity	Actual Delivered Quantity
     * 14	SKUCODActualReceived	SKU COD Actual Received amount(Null -> non-COD)
     * 15	SKUCODDifferentRemark	SKU Cash discrepancy remark
     * 16	TenderType	List of value of the payment Type
     *
     * @return
     */
    @Override
    public void generateRecordList(OrderVo vo, List<List<String>> allLines) {

        List<OrderEntryVo> entrys = vo.getEntryVoList();
        if (CollectionUtils.isEmpty(entrys)) {
            return;
        }

        for (OrderEntryVo entry : entrys) {
            List<String> recordList = new ArrayList<>();
            recordList.add(vo.getPickStore() != null ? vo.getPickStore().toString() : "");
            recordList.add(vo.getPickOrderId());//2	PickOrderId	Picking order id for
            recordList.add(vo.getHybrisOrderId());//3	OrderId	Original Hybris order no
            recordList.add(vo.getDeliverySuccess());//4	DeliverSuccess	Y/N
            recordList.add(DateUtils.formatDate(vo.getDeliveryDate(), DateUtils.DATE_FORMATE_YYYYMMDD)); //5	DeliverDate	Delivered Date	Date	x	Format: YYYYMMDD
            recordList.add(vo.getTotalAmount() != null ? vo.getTotalAmount().toString() : null); //o	CODOrder Amount
            recordList.add(""); //7	CODActualReceived
            recordList.add(""); //8	CODDifferentRemark
            recordList.add(entry.getSeqNum() != null ? entry.getSeqNum().toString() : "");//9	SequenceNumber
            recordList.add(entry.getSkuId() );//10	SkuId
            recordList.add(entry.getQty() != null ? entry.getQty().toString() : "");//11	OrderedQuantity
            recordList.add(entry.getPickedQty() != null ? entry.getPickedQty().toString() : "");//12	ShippedQuantity
            recordList.add(entry.getDeliveryQty() != null ? entry.getDeliveryQty().toString() : "");//13	DeliveredQuantity
            recordList.add(""); //	14	SKUCODActualReceived
            recordList.add(""); //	15	SKUCODDifferentRemark
            recordList.add(vo.getTenderType()); //16	TenderType
            allLines.add(recordList);
        }
    }


}
