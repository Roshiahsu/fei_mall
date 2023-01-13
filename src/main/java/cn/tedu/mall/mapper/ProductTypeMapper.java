package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.product.ProductTypeListVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ProductType
 * @Version 1.0
 * @Description 商品推播類別
 * @Date 2023/1/13、下午11:22
 */
@Repository
public interface ProductTypeMapper {

    List<ProductTypeListVO> listProductType();
}
