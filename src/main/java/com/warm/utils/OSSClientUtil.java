package com.warm.utils;


import com.aliyun.oss.OSSClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Date;

public class OSSClientUtil {
    private static Log logger = LogFactory.getLog(OSSClientUtil.class);

    /** 阿里云API的密钥Access Key ID */
    private static String accessKeyId =  "LTAIqcsZ8tpOgogO";
    /** 阿里云API的密钥Access Key Secret */
    private static String accessKeySecret = "Ectr9K1lRgX9T2w6d5TWYUDOiWseo9";
    /** 阿里云API的内或外网域名 */
    private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
    /** 阿里云API的bucket名称 */
    private static String bucketName  = "lwj-personal-zc";
    /** 阿里云API的文件夹名称 */
    private static String folder = "test";

    /**
     * 上传图片
     * @return ossClient
     */
    public static String UpdatePicToOSS(MultipartFile uploadFile) throws Exception {
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
        // https://ram.console.aliyun.com 创建
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传
        long time = new Date().getTime();
        int i1 = uploadFile.getOriginalFilename().lastIndexOf(".");
        ossClient.putObject(bucketName, folder + "/" + time + uploadFile.getOriginalFilename().substring(i1), new ByteArrayInputStream(uploadFile.getBytes()));
        // 关闭client
        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, folder + "/" + time + uploadFile.getOriginalFilename().substring(i1), expiration).toString();
        int i = url.lastIndexOf("?Expires=");
        String substring = url.substring(0,i);
        return substring;
    }


}
