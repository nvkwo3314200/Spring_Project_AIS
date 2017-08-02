package com.mall.b2bp.services.impl.orderFromDB;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.b2bp.daos.order.OrderTLogModelMapper;
import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.models.order.OrderModel;
import com.mall.b2bp.models.order.OrderTLogModel;
import com.mall.b2bp.populators.order.OrderTLogPopulator;
import com.mall.b2bp.services.orderFromDB.OrderEntryService;
import com.mall.b2bp.services.orderFromDB.OrderService;
import com.mall.b2bp.services.orderFromDB.OrderTLogService;
import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.utils.DateUtils;
import com.mall.b2bp.vos.ErrorLog;
import com.mall.b2bp.vos.order.OrderParameterVo;
import com.mall.b2bp.vos.order.OrderTLogVo;
import com.mall.b2bp.vos.user.UserVo;

@Service("orderTLogServiceFromDB")
public class OrderTLogServiceImpl implements OrderTLogService {

	private static final Logger LOG = LoggerFactory
			.getLogger(OrderTLogServiceImpl.class);

	/**
	 * 1 TransactionDate 2 OrderNumber 3 CreatedDate 4 Amount 5 ItemCode 6 Qty 7
	 * NetSales 8 TypeOfDistribution
	 */
	private static final String[] FILE_HEADER = { "TransactionDate",
			"OrderNumber", "CreatedDate", "Amount", "ItemCode", "Qty",
			"NetSales", "TypeOfDistribution" };

	private OrderTLogModelMapper orderTLogModelMapper;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "orderServiceFromDB")
	private OrderService orderService;
	
	
	@Resource(name = "orderEntryServiceFromDB")
	private OrderEntryService orderEntryService;

	@Override
	public int insertSelective(OrderTLogModel record) throws ServiceException {
		try {
			orderTLogModelMapper.insertSelective(record);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);

		}
		return 0;
	}

	@Override
	public List<OrderTLogVo> selectByOrderId(BigDecimal id)
			throws ServiceException {

		OrderParameterVo vo = new OrderParameterVo();
		String supplierId = null;

		UserVo userVo = sessionService.getCurrentUser();
		if (userVo != null) {
			if ("SUPPLIER".equals(userVo.getUserRole())) {
				supplierId = userVo.getSupplierId();
			}
		}

		vo.setSupplierId(supplierId);
		vo.setOrderId(id != null ? id.toString() : null);
		List<OrderTLogVo> list = new ArrayList<>();
		try {
			List<OrderTLogModel> lists = orderTLogModelMapper
					.selectByOrderId(vo);

			if (CollectionUtils.isNotEmpty(lists)) {

				OrderTLogPopulator p = new OrderTLogPopulator();
				for (OrderTLogModel m : lists) {
					list.add(p.converModelToVo(m));
				}
			}

			return list;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public boolean updateOrderCompleted(String fileName, ErrorLog errorLog)
			throws ServiceException {
		try {
			boolean success = true;
			List<OrderTLogModel> list = readCsvFile(fileName, errorLog);

			for (OrderTLogModel m : list) {

				if (StringUtils.isEmpty(m.getHybrisOrderId())|| StringUtils.isEmpty( m.getSkuId() )) {
					
					LOG.error("hybris order id is empty or skuid is empty.");
					success = false;
					continue;
				}

				m.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
				m.setLastUpdatedDate(new Date());
				m.setCreatedBy(ConstantUtil.JOB_USER_NAME);
				m.setCreatedDate(new Date());

				
				insertSelective(m);

				LOG.info("import PSSP_ORDER_TLOG,skid:"+m.getSkuId()+",hybris order id:"+m.getHybrisOrderId());
				
				OrderModel orderModel = orderService.getOrderByidAndskuId(
						m.getHybrisOrderId(), m.getSkuId());
				
				if (orderModel != null) {
					// udpate order status
					OrderModel record = new OrderModel();
					record.setId(orderModel.getId());
					record.setStatus(ConstantUtil.COMPLETED);
					record.setLastUpdatedBy(ConstantUtil.JOB_USER_NAME);
					record.setLastUpdatedDate(new Date());
					orderService.updateByPrimaryKeySelective(record);
					
					
					//update orderEntry-returned_qty ,delivery_qty 
					orderEntryService.updateTotalDeliveryQtyFromTlog(orderModel.getId());
					
				}
				
			}

			return success;
		} catch (Exception e) {
			errorLog.add("Throw Excepton:" + e.getMessage());
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);

		}

	}

	/**
	 * 1 TransactionDate 2 OrderNumber 3 CreatedDate 4 Amount 5 ItemCode 6 Qty 7
	 * NetSales 8 TypeOfDistribution
	 * 
	 * 
	 * @param fileName
	 * @param errorLog
	 * @return
	 * @throws ServiceException
	 */
	private List<OrderTLogModel> readCsvFile(String fileName, ErrorLog errorLog)
			throws ServiceException {
		List<OrderTLogModel> list = new ArrayList<>();

		OrderTLogModel model = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter('|')
				.withHeader(FILE_HEADER).withQuote(null)
				.withIgnoreEmptyLines(true);
		try (FileReader fileReader = new FileReader(fileName);
				CSVParser csvFileParser = new CSVParser(fileReader,
						csvFileFormat);) {
			List<CSVRecord> csvRecords = csvFileParser.getRecords();
			for (int i = 0; i < csvRecords.size(); i++) {

				CSVRecord record = csvRecords.get(i);
				model = new OrderTLogModel();

				String transactionDate = record.get("TransactionDate");
				String orderId = record.get("OrderNumber");
				String createDate = record.get("CreatedDate");
				String amount = record.get("Amount");
				String itemCode = record.get("ItemCode");
				String qty = record.get("Qty");
				String netSales = record.get("NetSales");
				String trypeDis = record.get("TypeOfDistribution");

				
				if(StringUtils.isNotEmpty(transactionDate)){
					model.setTransactionDate(DateUtils.parseDateStr(transactionDate, DateUtils.DATE_FORMATE_YYYYMMDD));//Format: YYYYMMDD
				}
				if(StringUtils.isNotEmpty(createDate)){
					model.setOrderCreatedDate(DateUtils.parseDateStr(createDate, DateUtils.DATE_FORMATE_YYYYMMDD));//Format: YYYYMMDD
				}

				model.setTypeOfDistribution(trypeDis);
				model.setNetSales(netSales);
				
				if (StringUtils.isEmpty(orderId)) {
					errorLog.add("Line :" + i + ",OrderNumber is empty.");
				} else
					model.setHybrisOrderId(orderId);

				if (StringUtils.isEmpty(itemCode)) {
					errorLog.add("Line :" + i + ",ItemCode is empty.");
				} else {
						model.setSkuId(itemCode);
				}

				if (StringUtils.isNotEmpty(qty)) {
					if (NumberUtils.isNumber(qty)) {
						model.setQty(new BigDecimal(qty));
					} else {
						errorLog.add("Line :" + i + ",Qty:" + qty+ " is not a valid number");
					}
				}
				if (StringUtils.isNotEmpty(amount)) {
					if (NumberUtils.isNumber(amount)) {
						model.setAmount(new BigDecimal(amount));
					} 
				}

				list.add(model);
			}

		} catch (Exception e) {

			LOG.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return list;
	}

	public OrderTLogModelMapper getOrderTLogModelMapper() {
		return orderTLogModelMapper;
	}

	@Autowired
	public void setOrderTLogModelMapper(
			OrderTLogModelMapper orderTLogModelMapper) {
		this.orderTLogModelMapper = orderTLogModelMapper;
	}

}
