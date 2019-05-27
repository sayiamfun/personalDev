package com.warm.system.upload;

import com.warm.entity.R;
import com.warm.system.service.db1.UploadService;
import com.warm.utils.ImageUpload.ImageUtils;
import com.warm.utils.OSSClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin //跨域
@Api(description = "文件上传")
@RestController
@RequestMapping("/upload")
public class UploadController {

    public static Log log = LogFactory.getLog(UploadController.class);

    @Autowired
    private UploadService uploadService;


    @ApiOperation(value = "上传语音")
    @PostMapping(value = "messageUpload")
    public R test(
            @ApiParam(name = "file", value = "上传的语音消息", required = true)
            @RequestBody MultipartFile file
    ){
        try {
            //默认头像
            String s = null;
            try {
                System.err.println(file.getOriginalFilename());
                System.err.println(file.getName());
                s = OSSClientUtil.UpdatePicToOSS(file);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("上传语音消息发生IO错误");
            } catch (MyException e) {
                e.printStackTrace();
                log.info("上传语音消息发生MyException错误");
            }
            log.info("上传成功");
            return R.ok().data(s);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "imageUpload")
    public R test(
            @ApiParam(name = "imageSrc", value = "上传照片的base64字符串", required = true)
            @RequestBody String imageSrc
    ){
        try {
            MultipartFile multipartFile = ImageUtils.base64ToMultipart(imageSrc);
            log.info("开始上传照片");
            //默认头像
            String s = "https://jiazhangjia-1.oss-cn-beijing.aliyuncs.com/jiazhanghome.jpg";
            try {
                s = OSSClientUtil.UpdatePicToOSS(multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("上传照片发生IO错误");
            } catch (MyException e) {
                e.printStackTrace();
                log.info("上传照片发生MyException错误");
            }
            log.info("上传成功");
            return R.ok().data(s);
        }catch (Exception e){
            e.printStackTrace();
        	return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

}
