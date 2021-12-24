package com.cetc10.automaticaccess.config;

import com.cetc10.automaticaccess.util.FtpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@EnableScheduling
@Configuration
@Slf4j
public class FileUploadScheduled {

    @Value("${file.inPathParent}")
    private String inPathParent;
    @Value("${file.outPathParent}")
    private String outPathParent;
    @Value("${file.errorPathParent}")
    private String errorPathParent;

    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;


    /**
     * 文件上传FTP定时任务
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void uploadFileToFtp() {
        File file = new File(inPathParent);
        if (!file.exists()) {
            file.mkdirs();
        }

        fileHandle(file);

        FTPClient ftpClient = FtpUtils.loginFTP(host, port, username, password);
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
                log.info("===============ftp关闭===============");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 文件处理
     * @param filePath
     */
    public void fileHandle(File filePath){
        File[] fileList = filePath.listFiles();
        for(File file : fileList){
            if(file.isDirectory()){
                File path = new File(file.getPath());
                fileHandle(path);
            }else{
                try {
                    //获取文件修改时间
                    Date updateTime = new Date(file.lastModified());
                    String inPath = file.getPath().replaceAll("\\\\", "/");
                    if (inPath.endsWith(".doc") || inPath.endsWith(".docx")) {
                        boolean flag = true;
                        if (flag) { //解析成功
                            copyFile(inPath, outPathParent);
                            log.info("文件" + inPath + "上传成功");
                        } else {
                            copyFile(inPath, errorPathParent);
                            log.error("文件" + inPath + "上传失败");
                        }
                    } else if (inPath.endsWith(".txt")) {
                        boolean flag = false;
                        if (flag) { //解析成功
                            copyFile(inPath, outPathParent);
                            log.info("文件" + inPath + "上传成功");
                        } else {
                            copyFile(inPath, errorPathParent);
                            log.error("文件" + inPath + "上传失败");
                        }
                    } else {
                        copyFile(inPath, errorPathParent);
                        log.error("文件" + inPath + "上传失败");
                    }
                } catch (Exception e) {
                    log.error("=====================error=====================");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件复制
     * @param cPath 复制文件路径
     * @param vPath 粘贴路径
     */
    public void copyFile(String cPath,String vPath) {
        File cFile = new File(cPath);
        try {
            File vFileParent = new File(vPath);
            if (!vFileParent.exists()) {
                vFileParent.mkdirs();
            }
            String fileName = cPath.substring(cPath.lastIndexOf("/")+1);
            File vfile = new File(vPath + fileName);
            if (vfile.exists()) {
                log.info("文件名已存在");
            }
            FileCopyUtils.copy(cFile,vfile);
        } catch (IOException e) {
            log.error("=====================500=====================");
            e.printStackTrace();
        } finally {
            cFile.delete();
            File parent = new File(cFile.getParent());
            File[] files = parent.listFiles();
            if (files.length == 0 && !cFile.getParent().equals(new File(inPathParent).getPath())) {
                parent.delete();
            }
        }
    }

}
