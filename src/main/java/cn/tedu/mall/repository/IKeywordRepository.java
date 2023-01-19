package cn.tedu.mall.repository;

import cn.tedu.mall.pojo.product.ProductVO;

import java.util.List;

/**
 * @ClassName IKeywordRepository
 * @Version 1.0
 * @Description TODO Search for redis
 * @Date 2023/1/19、下午5:02
 */
public interface IKeywordRepository {

    // 存數據
    void putList();
    //刪除數據
    void deleteList();
    // 獲取關鍵字
    List<String> getList();
}
