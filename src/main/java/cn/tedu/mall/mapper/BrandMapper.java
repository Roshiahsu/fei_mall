package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.brand.Brand;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName BrandMapper
 * @Version 1.0
 * @Description 品牌模組Mapper
 * @Date 2023/1/9、下午9:11
 */
@Repository
public interface BrandMapper {

    /**
     * 新增品牌
     * @param brand 接收到的請求資料(id,brandName)
     * @return 影響行數
     */
    int insert(Brand brand);

    /**
     * 根據品牌名稱統計數量
     * @param brand 接收到的請求資料(id,brandName)
     * @return 影響行數
     */
    int countByBrandName(Brand brand);

    /**
     * 獲取品牌列表
     * @return 品牌列表
     */
    List<Brand> listBrand();

    /**
     * 根據id刪除品牌
     * @param id 品牌id
     * @return 影響行數
     */
    int deleteBrandById(Long id);

    /**
     * 根據id修改品牌
     * @param brand (id productName)
     * @return
     */
    int updateById(Brand brand);
}
