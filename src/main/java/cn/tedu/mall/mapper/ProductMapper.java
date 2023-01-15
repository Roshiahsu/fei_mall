package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductVO;
import cn.tedu.mall.pojo.product.ProductUpdateDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ProductMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/24、下午11:15
 */
@Repository
public interface ProductMapper {

    int insert(ProductAddNewDTO productAddNewDTO);

    void deleteByIds(Long... id);

    int countByName(String productName);

    List<ProductVO> listProduct(Long typeId);

    ProductVO getById(Long id);

    int updateById(ProductUpdateDTO productUpdateDTO);

}
