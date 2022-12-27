package cn.tedu.mall;

import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductListVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

/**
 * @ClassName ProductTest
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/26、上午2:18
 */
@SpringBootTest
@Slf4j
public class ProductTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void insertTest(){
        ProductAddNewDTO p = new ProductAddNewDTO();
        p.setBrandId(1L);
        p.setCategoryId(1L);
        p.setDescription("測試");
        p.setKeywords("魚油");
        p.setProductName("魚油");
        p.setStock(99);
        p.setGmtExp(LocalDate.now());
        productMapper.insert(p);
        log.debug("insert成功");
    }

    @Test
    public void deleteByIdsTest(){
        productMapper.deleteByIds(2L,3L,4L);
    }

    @Test
    public void listProductTest(){
        List<ProductListVO> vos = productMapper.listProduct(2L);
        for (ProductListVO vo : vos) {
            System.out.println("獲取到的資料>>"+vo.toString());
        }
    }
}
