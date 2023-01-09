package cn.tedu.mall;

import cn.tedu.mall.mapper.BrandMapper;
import cn.tedu.mall.pojo.brand.Brand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @ClassName BrandTest
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/9、下午10:50
 */
@SpringBootTest
@Slf4j
public class BrandTest {

    @Autowired
    private BrandMapper brandMapper;

    @Test
    public void listTest(){
        List<Brand> brands = brandMapper.listBrand();
        for (Brand brand : brands) {
            log.debug("獲取的品牌>>>{}",brand);
        }
    }
}
