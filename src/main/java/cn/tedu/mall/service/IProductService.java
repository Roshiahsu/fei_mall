package cn.tedu.mall.service;

import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import cn.tedu.mall.pojo.product.ProductListVO;
import cn.tedu.mall.web.JsonPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName IProductService
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/24、下午11:14
 */
@Transactional
public interface IProductService {

    void insert(ProductAddNewDTO productAddNewDTO);

    void deleteByIds(Long... ids);

    JsonPage<ProductListVO> listProduct(Integer pageNum,Integer pageSize,Long typeId);

    ProductListVO getById(Long id);
}
