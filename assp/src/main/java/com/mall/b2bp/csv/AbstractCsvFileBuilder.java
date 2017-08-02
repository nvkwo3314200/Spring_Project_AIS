package com.mall.b2bp.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.b2bp.exception.ServiceException;
import com.mall.b2bp.utils.ResourceUtil;
import com.mall.b2bp.vos.order.OrderEntryVo;
import com.mall.b2bp.vos.order.OrderVo;

/**
 * Created by USER on 2016/3/21.
 */
public abstract class AbstractCsvFileBuilder implements CsvFileBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCsvFileBuilder.class);
    private static final String NEW_LINE_SEPARATOR = "\n";
    private String fileName;
    private static final String PATH= ResourceUtil.getSystemConfig().getProperty("home_order_path");
//    private String temporaryFolder = "";

    protected List<OrderVo> orderVoList;

    protected abstract String[] getHeader();

    protected abstract String generateFileName();


    public List<List<String>> userDataRecord() {

        List<List<String>> allLines = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(orderVoList)) {


            for (OrderVo vo : orderVoList) {
                List<OrderEntryVo> entrys = vo.getEntryVoList();
                if (CollectionUtils.isEmpty(entrys)) {
                    break;
                }

                sort(entrys);

                generateRecordList(vo, allLines);
            }
        }
        return allLines;
    }

    private void sort(List<OrderEntryVo> entrys) {
        Collections.sort(entrys, new Comparator<OrderEntryVo>() {
            @Override
            public int compare(OrderEntryVo arg0, OrderEntryVo arg1) {
                if (arg0.getSeqNum() != null && arg1.getSeqNum() != null)
                    return arg0.getSeqNum().compareTo(arg1.getSeqNum());
                else
                    return -1;
            }
        });
    }

    public abstract void generateRecordList(OrderVo vo, List<List<String>> allLines);

//    private void handelFolder() {
//        if (StringUtils.isEmpty(temporaryFolder))
//            temporaryFolder = PATH;
//
//        if (StringUtils.isNotEmpty(temporaryFolder)) {
//            File dirFile = new File(temporaryFolder);
//            if (!dirFile.exists())
//                new File(temporaryFolder).mkdirs();
//        }
//
//    }


    @Override
    public void writeCsvFile() throws ServiceException {

        fileName = generateFileName();

     //   handelFolder();

        List<List<String>> userList = userDataRecord();
        if(CollectionUtils.isEmpty(userList))
            return ;


        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter('|').withRecordSeparator(NEW_LINE_SEPARATOR);
        try (
                FileWriter fileWriter = new FileWriter(PATH + File.separator + getFileName());
                CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
        ) {

            wirteHeader(csvFilePrinter, getHeader());



            wirteDataRecord(csvFilePrinter, userList);

        } catch (Exception e) {
            LOG.error("AbstractCsvFileBuilder error:" + e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    private void wirteHeader(CSVPrinter csvFilePrinter, String[] header) throws IOException {
        if (header != null && header.length > 0) {
            try {
                csvFilePrinter.printRecord(getHeader());
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
                throw e;

            }
        }
    }

    private void wirteDataRecord(CSVPrinter csvFilePrinter, List<List<String>> userList) throws IOException {
        if (CollectionUtils.isNotEmpty(userList)) {
            for (List<String> list : userList) {
                try {
                    csvFilePrinter.printRecord(list);
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                    throw e;
                }
            }
        }
    }


    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


//    @Override
//    public String getTemporaryFolder() {
//        return temporaryFolder;
//    }
//
//    @Override
//    public void setTemporaryFolder(String temporaryFolder) {
//        this.temporaryFolder = temporaryFolder;
//    }
}
