package com.warm.system.service.impl;

import com.warm.system.service.db1.UploadService;
import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Properties;

@Service
public class UploadServiceImpl implements UploadService {

    private static Properties properties;

    @Value("${fileServer.url}")
    String fileUrl;

    @Value("${fileServer.confUrl}")
    String confUrl;

    /*
     * 内容回复,图片上传
     */
    @Override
    public String fileUpload(MultipartFile file) throws IOException, MyException {
        String imgUrl=fileUrl;
        if(file!=null){
            System.out.println("multipartFile = " + file.getName()+"|"+file.getSize());
            //jar包运行时notfound异常，绝对路径
            ClientGlobal.init(confUrl);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getConnection();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String filename= file.getOriginalFilename();
            String extName = StringUtils.substringAfterLast(filename, ".");

            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
            imgUrl=fileUrl ;
            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                imgUrl+="/"+path;
            }
        }
        return imgUrl;
    }


}
