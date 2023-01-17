package cn.tedu.mall.repository.impl;

import cn.tedu.mall.mapper.ProductMapper;
import cn.tedu.mall.mapper.ProductTypeMapper;
import cn.tedu.mall.pojo.product.ProductTypeListVO;
import cn.tedu.mall.pojo.product.ProductVO;
import cn.tedu.mall.repository.IProductRepository;
import cn.tedu.mall.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ProductRepositoryImpl
 * @Version 1.0
 * @Description 商品repository
 * @Date 2023/1/15、下午2:47
 */
@Repository
@Slf4j
public class ProductRepositoryImpl implements IProductRepository {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 根據typeId將資料寫入redis
     * @param typeId 類別id
     */
    @Override
    public void putList(Integer typeId) {
        deleteList(typeId);
        log.debug("向Redis中寫入商品數據");
        List<ProductVO> productVOS = productMapper.listProduct(typeId);

        String key = getRedisKey(typeId);

        for (ProductVO productVO : productVOS) {
            redisTemplate.opsForList().rightPush(key,productVO);
        }
    }

    @Override
    public void deleteList(Integer typeId) {
        log.debug("開始刪除redis中的Product List數據");
        String key = getRedisKey(typeId);
        redisTemplate.delete(key);
    }

    @Override
    public List<ProductVO> getList(Integer typeId,Integer pageNum, Integer pageSize) {
        log.debug("從Redis中獲取Product資料");
        String key = getRedisKey(typeId);

        List<Object> productVO = redisTemplate.opsForList().range(key, 0, pageSize);
        List<ProductVO> productListVO = new ArrayList<>();

        for (Object vo : productVO) {
            productListVO.add((ProductVO)vo);
        }
        return productListVO;
    }

    public String getRedisKey(Integer typeId){
        String key ;
        //根據輸入的typeId決定redis中的key
        if (typeId.equals(RedisUtils.NEW_PRODUCT)){
            key = RedisUtils.getProductKey(RedisUtils.NEW_PRODUCT_NAME);
        }else if (typeId.equals(RedisUtils.HOT_PRODUCT)){
            key = RedisUtils.getProductKey(RedisUtils.HOT_PRODUCT_NAME);
        }else
            key = RedisUtils.getProductKey(RedisUtils.DISCOUNTED_PRODUCT_NAME);

        return key;
    }

    @Override
    public void putProductTypeList() {
        deleteProductTypeList();
        List<ProductTypeListVO> productTypeListVOS = productTypeMapper.listProductType();
        for (ProductTypeListVO productTypeListVO : productTypeListVOS) {
            redisTemplate.opsForList().rightPush
                    (RedisUtils.KEY_PREFIX_PRODUCT_TYPE_LIST,productTypeListVO);
        }
    }

    @Override
    public void deleteProductTypeList() {
        redisTemplate.delete(RedisUtils.KEY_PREFIX_PRODUCT_TYPE_LIST);
    }

    @Override
    public List<ProductTypeListVO> getProductTypeList() {
        List<Object> list = redisTemplate.opsForList().range
                (RedisUtils.KEY_PREFIX_PRODUCT_TYPE_LIST, 0, -1);

        List<ProductTypeListVO> listVO = new ArrayList<>();
        for (Object o : list) {
            listVO.add((ProductTypeListVO)o);
        }
        return listVO;
    }
}
