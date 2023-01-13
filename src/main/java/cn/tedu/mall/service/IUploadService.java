package cn.tedu.mall.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName IUploadService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/13、下午3:17
 */
public interface IUploadService {

    void upload(MultipartFile picFile,String picture);
}
