package com.mall.b2bp.services.impl.ftp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import com.mall.b2bp.services.ftp.FtpService;

//@Service("ftpService")
public class FtpServiceImpl implements FtpService {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(FtpServiceImpl.class);
	
//	@Value("${ftp.server}")
	private String ftpServer;
	
//	@Value("${ftp.port}")
	private int port;
	
//	@Value("${ftp.userName}")
	private String userName;
	
//	@Value("${ftp.password}")
	private String password;
	
	private String path;

	@Override
	public void uploadFiles(List<File> fileList) throws IOException, FtpProtocolException {
		
		try(
			FtpClient ftpClient = FtpClient.create(new InetSocketAddress(ftpServer, port));
		){
			LOG.info(ftpServer);
			LOG.info(String.valueOf(port));
			LOG.info(userName);
			LOG.info(password);
			
			
			ftpClient.login(userName, password.toCharArray());  
			ftpClient.setBinaryType();  
						
			ftpClient.changeDirectory(path);
			
			for(File file: fileList){
				uploadFile(ftpClient, file);
			}
			
		} catch (IOException | FtpProtocolException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@Override
	public void uploadFiles(String uploadPath, List<File> fileList) throws IOException, FtpProtocolException {
		
		try(
			FtpClient ftpClient = FtpClient.create(new InetSocketAddress(ftpServer, port));
		){
			LOG.info(ftpServer);
			LOG.info(String.valueOf(port));
			LOG.info(userName);
			LOG.info(password);
			
			
			ftpClient.login(userName, password.toCharArray());  
			ftpClient.setBinaryType();  
						
			ftpClient.changeDirectory(uploadPath);
			
			for(File file: fileList){
				uploadFile(ftpClient, file);
			}
			
		} catch (IOException | FtpProtocolException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@Override
	public void deleteFile(String fileName) throws IOException, FtpProtocolException {
		
		try(
			FtpClient ftpClient = FtpClient.create(new InetSocketAddress(ftpServer, port));
		){
			LOG.info(ftpServer);
			LOG.info(String.valueOf(port));
			LOG.info(userName);
			LOG.info(password);
			
			
			ftpClient.login(userName, password.toCharArray());  
			ftpClient.setBinaryType();  
						
			ftpClient.changeDirectory(path);
			ftpClient.deleteFile(fileName);
			
		} catch (IOException | FtpProtocolException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@Override
	public void downloadFiles(String localPath, String prefix, String postfix) throws IOException, FtpProtocolException {
		
		try(
			FtpClient ftpClient = FtpClient.create(new InetSocketAddress(ftpServer, port));
		){
			LOG.info(ftpServer);
			LOG.info(String.valueOf(port));
			LOG.info(userName);
			LOG.info(password);
			
			
			ftpClient.login(userName, password.toCharArray());  
			ftpClient.setBinaryType();  
			List<String> fileList = getFileList(ftpClient, path);		
			ftpClient.changeDirectory(path);
			for(String file : fileList){
				if(prefix != null && !file.startsWith(prefix)){
					continue;
				}
				if(postfix != null && !file.endsWith(postfix)){
					continue;
				}
				
				String outFile = localPath + File.separator + file;
				try(
					FileOutputStream os = new FileOutputStream(outFile); 
					InputStream is = ftpClient.getFileStream(file);
				){
					byte[] bytes = new byte[1024];
					int c;
					while ((c = is.read(bytes)) != -1) {
						os.write(bytes, 0, c);
					}
				}
			}
		} catch (IOException | FtpProtocolException e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}
	
	
	private List getFileList(FtpClient ftpClient, String path) throws IOException, FtpProtocolException 
	 { 
	    List list = new ArrayList(); 
	    try(
	    	DataInputStream dis = new DataInputStream(ftpClient.nameList(path)); 		
	    )  
	    { 
	       String filename = ""; 
	       while((filename=dis.readLine())!=null)   
	       {   
	    	   filename = filename.substring(filename.lastIndexOf("/")+1);
	    	   LOG.info(filename);
	    	   list.add(filename);         
	       }   
	    
	    } catch (IOException | FtpProtocolException e)  
	    { 
	    	LOG.error(e.getMessage(), e);
			throw e;
	    } 
	    return list; 
	 } 
	
	
	private void uploadFile(FtpClient ftpClient, File file) throws IOException,
			FtpProtocolException {
		
		if(!file.exists()){
			return;
		}
		
		try (FileInputStream is = FileUtils.openInputStream(file);) {
			ftpClient.putFile(file.getName(), is);
		} catch (IOException | FtpProtocolException e) {
			throw e;
		}
	}

	public String getFtpServer() {
		return ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
