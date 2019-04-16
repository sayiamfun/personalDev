package com.warm.system.service.db1;

import org.csource.common.MyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    String fileUpload(MultipartFile file)throws IOException, MyException;
}
