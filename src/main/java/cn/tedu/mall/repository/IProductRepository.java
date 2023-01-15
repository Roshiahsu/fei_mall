package cn.tedu.mall.repository;

import cn.tedu.mall.pojo.brand.Brand;
import cn.tedu.mall.pojo.product.ProductVO;

import java.util.List;

/**
 * @ClassName IProductRepository
 * @Version 1.0
 * @Description 商品列表Redis操作
 * @Date 2023/1/15、下午1:05
 */
public interface IProductRepository {

    // 存數據
    void putList(Integer typeId);
    //刪除數據
    void deleteList(Integer typeId);
    // 獲取商品
    List<ProductVO> getList(Integer typeId,Integer pageNum, Integer pageSize);

}
