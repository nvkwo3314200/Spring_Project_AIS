package com.mall.b2bp.services.ftp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import sun.net.ftp.FtpProtocolException;

public interface FtpService {
	void uploadFiles(List<File> fileList) throws IOException, FtpProtocolException;
	void uploadFiles(String uploadPath, List<File> fileList) throws IOException, FtpProtocolException;
	void downloadFiles(String localPath, String prefix, String postfix) throws IOException, FtpProtocolException;
	void deleteFile(String fileName) throws IOException, FtpProtocolException;
}
