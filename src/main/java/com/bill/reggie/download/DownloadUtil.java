package com.bill.reggie.download;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.MessageFormat;

public class DownloadUtil {

    public static void main(String[] args) {
        // 文件保存目录
        String fileFolder = "D:/";
        // 文件名称
        String fileName = "test.png";
        // 文件远程URL地址
        String url = "https://crpt-cloud-gateway-dev.liuheco.com/crpt-file/file/download/1613010527917883392";
        try {
            // 从远程URL地址读取文件Bytes
            byte[] fileBytes = getFileBytesFromUrl(url);
            // 将获取的文件Bytes保存到本地磁盘
            saveBytesAsFile(fileBytes, fileFolder, fileName);
            System.out.println("下载文件完成, 文件保存在 " + fileFolder + fileName);
        } catch (Exception e) {
            System.out.println("发生异常： " + e.getMessage());
            e.printStackTrace();
        }
    }

    /***
     *
     * @param remoteUrl 文件远程Url地址
     * @return 文件Bytes
     * @throws Exception
     */
    public static byte[] getFileBytesFromUrl(String remoteUrl) throws Exception {
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

    /***
     *
     * @param bytes
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void saveBytesAsFile(byte[] bytes, String filePath, String fileName) throws Exception {
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

