package com.ftp;

import org.apache.commons.io.IOUtils; 
import org.apache.commons.net.ftp.FTPClient; 
import org.apache.commons.net.ftp.FTPFile;
import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.FileOutputStream;
import java.net.SocketException;

/** 
* @author : JOE 
*/ 
public class FtpTest { 
    public static void main(String[] args) { 
        testUpload(); 
     //   testDownload(); 
     //    showFiles();
    } 
    
    public static void showFiles(){   	
    	FTPClient ftpclient =new FTPClient();
    	try {
			ftpclient.connect("10.20.58.25");
			ftpclient.login("zoulong", "8015");
			ftpclient.setControlEncoding("GBK"); 		
			FTPFile [] files = ftpclient.listFiles();
			
			for(FTPFile file:files){
			//	System.out.println(file.getName()+ file.getType()+file.getTimestamp()+ file.getUser()+file.getSize());	
			
			 String bname = file.getName();
			
		//	String bname=new String(file.getName().getBytes("iso-8859-1"),"GBK");  
			
			System.out.println(bname);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 

    /** 
     * FTP上传单个文件测试 
     */ 
    public static void testUpload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 

        try { 
            ftpClient.connect("10.19.42.98"); 
            ftpClient.login("zoulong", "8015"); 
            ftpClient.setControlEncoding("GBK"); 

          //  File srcFile = new File("C:\\new.gif"); 
            File srcFile = new File("C:\\Users\\long\\Desktop\\海关对接.xml");
            
            
            fis = new FileInputStream(srcFile); 
            //设置上传目录 
        //    ftpClient.changeWorkingDirectory("/admin/pic"); 
            ftpClient.setBufferSize(1024); 
       //     ftpClient.setControlEncoding("GBK"); 
            //设置文件类型（二进制） 
            
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            
            String name = "zoulong.xml";          
            name=new String(name.getBytes("GBK"),"iso-8859-1");           
            ftpClient.storeFile(name, fis); 
            
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 

    /** 
     * FTP下载单个文件测试 
     */ 
    public static void testDownload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileOutputStream fos = null; 

        try { 
            ftpClient.connect("10.20.58.25"); 
            ftpClient.login("zoulong", "8015"); 

            String remoteFileName = "/test.pdf"; 
            fos = new FileOutputStream("D:/Test/down.pdf"); 

            ftpClient.setBufferSize(1024); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.retrieveFile(remoteFileName, fos); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fos); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 
} 
