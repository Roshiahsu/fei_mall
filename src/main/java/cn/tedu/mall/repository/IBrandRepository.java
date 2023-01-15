package cn.tedu.mall.repository;

import cn.tedu.mall.pojo.brand.Brand;

import java.util.List;

/**
 * @ClassName IBrandRepository
 * @Version 1.0
 * @Description 品牌列表redis操作
 * @Date 2023/1/15、下午1:05
 */
public interface IBrandRepository {

    // 存數據
    void putList();
    //刪除數據
    void deleteList();
    // 獲取數據
    List<Brand> getList();

}
