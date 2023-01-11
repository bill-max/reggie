package com.bill.reggie.download;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.MessageFormat;

@Component
public class Download {

    public byte[] getFileBytesFromUrl(String remoteUrl) throws Exception {
        byte[] fileBytes = null;
        InputStream inStream = null;
        ByteArrayOutputStream bOutStream = null;
        try {
            URL url = new URL(remoteUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            inStream = httpConn.getInputStream();
            bOutStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                bOutStream.write(buffer, 0, len);
            }
            fileBytes = bOutStream.toByteArray();
        } catch (UnknownHostException e) {
            String msg = MessageFormat.format("网络请求时发生UnknownHostException异常: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } catch (MalformedURLException e) {
            String msg = MessageFormat.format("网络请求时发生MalformedURLException异常: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } catch (IOException e) {
            String msg = MessageFormat.format("从远程Url中获取文件流时发生IOException异常: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } finally {
            if (null != inStream) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    String msg = MessageFormat.format("关闭InputStream时发生异常: {0}", e.getMessage());
                    Exception ex = new Exception(msg);
                    ex.initCause(e);
                    throw ex;
                }
            }
            if (null != bOutStream) {
                try {
                    bOutStream.close();
                } catch (IOException e) {
                    String msg = MessageFormat.format("关闭ByteArrayOutputStream时发生异常: {0}", e.getMessage());
                    Exception ex = new Exception(msg);
                    ex.initCause(e);
                    throw ex;
                }
            }
        }
        return fileBytes;
    }

    public void saveBytesAsFile(byte[] bytes, String filePath, String fileName) throws Exception {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        File dir = new File(filePath);
        if (!dir.exists() && dir.isDirectory()) {
            // 文件目录不存在时先创建目录
            dir.mkdirs();
        }
        file = new File(filePath + fileName);
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (FileNotFoundException e) {
            String msg = MessageFormat.format("文件保存时发生FileNotFoundException异常: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } catch (IOException e) {
            String msg = MessageFormat.format("文件保存时发生IOException异常: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    String msg = MessageFormat.format("文件保存时发生IOException异常: {0}", e.getMessage());
                    Exception ex = new Exception(msg);
                    ex.initCause(e);
                    throw ex;
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    String msg = MessageFormat.format("文件保存时发生IOException异常: {0}", e.getMessage());
                    Exception ex = new Exception(msg);
                    ex.initCause(e);
                    throw ex;
                }
            }
        }
    }
}
