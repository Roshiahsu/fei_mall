package cn.tedu.mall.preload;

import cn.tedu.mall.pojo.brand.Brand;
import cn.tedu.mall.repository.IBrandRepository;
import cn.tedu.mall.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName CachePreLoad
 * @Version 1.0
 * @Description Redis 預熱
 * @Date 2023/1/15、下午1:00
 */
@Component
@Slf4j
public class CachePreLoad implements ApplicationRunner {

    @Autowired
    private IBrandRepository brandRepository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //加載品牌資料
        brandRepository.putList();
        //加載商品資料
        //TODO 之後更新與資料庫對應目前寫死
        for (int i = 2; i <=4 ; i++) {
            productRepository.putList(i);
        }
        //加載推播種類
        productRepository.putProductTypeList();
    }
}
