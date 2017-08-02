package com.mall.b2bp.csv;

import com.mall.b2bp.exception.ServiceException;



public interface CsvFileBuilder {

 
    
    //String getTemporaryFolder();

    //void setTemporaryFolder(String temporaryFolder);

    String getFileName();

    void setFileName(String fileName);

    void writeCsvFile() throws ServiceException;

}
