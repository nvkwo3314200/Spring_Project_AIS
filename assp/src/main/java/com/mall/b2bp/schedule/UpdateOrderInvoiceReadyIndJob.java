package com.mall.b2bp.schedule;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.services.order.OrderService;
import com.mall.b2bp.utils.ResourceUtil;


public class UpdateOrderInvoiceReadyIndJob {

	private static final Logger LOG = LoggerFactory.getLogger(UpdateOrderInvoiceReadyIndJob.class);

	@Resource
	private OrderService orderService;
  
	public void updateOrderInvoiceReadyInd(){
		List<OrderModel> orderList = null;
		String[] files = null;
		UpdateOrderInvoiceReadyIndJob updateOrderInvoiceReadyIndJob = new UpdateOrderInvoiceReadyIndJob();
		String filePath = ResourceUtil.getSystemConfig().getProperty(
				"pdf_for_order_invoice_ready_ind");	
		try {
			orderList = orderService.getOrderListByInvoiceInd();
		//	log.info(" log orderList :" + orderList.size());
			
			files = updateOrderInvoiceReadyIndJob.getFilesByPath(filePath);
		//	log.info(" log orderList :" + files.length);
			List<OrderModel> modeList = getAllInvoiceForN(files, orderList);
			if(CollectionUtils.isNotEmpty(modeList)){
				for(OrderModel orModel : modeList){
					orModel.setInvoiceReadyInd("Y");
					orderService.updateByPrimaryKeySelective(orModel);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private String[] getFilesByPath(String filePath) {
		String[] filelist = null;
		File file = new File(filePath);
		if (!file.isDirectory()) {
			LOG.info("path=" + file.getPath());
			LOG.info("absolutepath=" + file.getAbsolutePath());
			LOG.info("name=" + file.getName());
		} else if (file.isDirectory()) {
			filelist = file.list();
		} else {
			LOG.error("path------" + filePath);
		}
		return filelist;
	}
	
	protected void executeOrderInvoiceInd() {
		LOG.info(" log updateOrderInvoiceReadyIndJob start:" + new Date());
		updateOrderInvoiceReadyInd();
		LOG.info(" log updateOrderInvoiceReadyIndJob end:" + new Date());
	}
	
	private List<OrderModel> getAllInvoiceForN(String[] files,List<OrderModel> rList){
		List<OrderModel> idList = new ArrayList<>();
		if(files != null && files.length > 0){
			for(String str : files){
				for(OrderModel orderModel : rList){
					if(StringUtils.isNotEmpty(orderModel.getInvoiceFilename()) && str.contains(orderModel.getInvoiceFilename())){
							idList.add(orderModel);
					}
				}
			}
		}
		return idList;
	}
	


//	public static void main(String[] args) {
//		/*String files[] = new String[]{"DN_16301248661_201602151632489.pdf","Invoice_16301248661_201602151632356.pdf"};
//		String str = "201602151632489";
//		List<String> fileList = new ArrayList<String>();
//		if(files != null && files.length > 0){
//			for(String strf : files){
//				if(StringUtils.isNotEmpty(strf)){
//					if(strf.contains(str)){
//						fileList.add(strf);
//					}
//				}
//			}
//		}
//
//		System.out.println(fileList.size());*/
//
//	}
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

}
