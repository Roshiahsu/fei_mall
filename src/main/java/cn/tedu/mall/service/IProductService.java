package cn.tedu.mall.service;

import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductListVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName IProductService
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/24、下午11:14
 */
public interface IProductService {

    void insert(ProductAddNewDTO productAddNewDTO);

    void deleteByIds(Long... ids);

    List<ProductListVO> listProduct(Long typeId);

    ProductListVO getById(Long id);
}
