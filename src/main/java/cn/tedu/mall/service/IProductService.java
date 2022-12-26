package cn.tedu.mall.service;

import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import org.springframework.stereotype.Service;

/**
 * @ClassName IProductService
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/24、下午11:14
 */
public interface IProductService {

    void insert(ProductAddNewDTO productAddNewDTO);

    void deleteByIds(Long... ids);
}
