package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.product.ProductAddNewDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName ProductMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/24、下午11:15
 */
@Repository
@Transactional
public interface ProductMapper {

    int insert(ProductAddNewDTO productAddNewDTO);

    void deleteByIds(Long... id);



    int countByName(String productName);


}
