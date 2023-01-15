package cn.tedu.mall.service.impl;

import cn.tedu.mall.service.IUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.ServiceMode;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName UploadServiceImpl
 * @Version 1.0
 * @Description 圖片上傳
 * @Date 2023/1/13、下午3:18
 */
@Service
@Slf4j
public class UploadServiceImpl implements IUploadService {

    @Value("${dirPath}")
    private String dirPath;

    /**
     * 上傳圖片
     * @param picFile 上傳的圖片
     * @param picture 圖片名稱 UUID
     */
    public void upload(MultipartFile picFile,String picture,String oldPicture) {
        //刪除舊的圖片
        remove(oldPicture);
        //準備圖片存放路徑
        File dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        //得到文件的完整路徑
        String filePath=dirPath+"/"+picture;
        try {
            //把文件保存到此路徑
            picFile.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("文件保存完成"+filePath);
    }

    /**
     * 刪除圖片
     * @param name
     */
    public void remove(String name){
        String filePath=dirPath+"/"+name;
        log.debug("刪除>>>{}",filePath);
        new File(filePath).delete();
    }

}
