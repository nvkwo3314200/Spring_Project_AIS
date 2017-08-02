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
public class HomeShipFileWrite extends AbstractCsvFileBuilder {

   


    public HomeShipFileWrite(List<OrderVo> orderVoList) {
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

        return "Infinity_SP_HomeShip_" + stroeId + "_" + DateUtils.formatDate(new Date(), DateUtils.DATE_TIME_FORMATE_YYYMMDD_HHMMSS) + ".csv";
    }





    /**
     * 1	PickStore
     * 2	PickOrderId
     * 3	OrderId
     * 4	TrckId
     * 5	Packer
     * 6	PackInfo
     * 7	RouteInfo
     * 8	SequenceNumber
     * 9	SkuId
     * 10	ProductName
     * 11	Quantity
     * 12	Date
     * 13	BoxNumber
     * 14	Carrier
     * 15	TenderType
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


            recordList.add(vo.getPickStore() != null ? vo.getPickStore().toString() : "");
            recordList.add(vo.getPickOrderId());
            recordList.add(vo.getHybrisOrderId());
            recordList.add(vo.getTrackId());
            recordList.add("Supplier"); //o	Packer
            recordList.add(null);//o	PackInfo
            recordList.add(null);//o	RouteInfo
            recordList.add(entry.getSeqNum() != null ? entry.getSeqNum().toString() : "");//o	SequenceNumber
            recordList.add(entry.getSkuId());
            recordList.add(entry.getProductName());//10	ProductName
            recordList.add(entry.getPickedQty() != null ? entry.getPickedQty().toString() : "");//11	Quantity	Picked quantity
            recordList.add(DateUtils.formatDate(vo.getConsignmentShippedDate(), DateUtils.DATE_FORMATE_YYYYMMDD)); //Format: YYYYMMDD
            recordList.add(vo.getBoxNum());
            recordList.add(null);
            recordList.add(vo.getTenderType());

            allLines.add(recordList);
        }

    }

}
