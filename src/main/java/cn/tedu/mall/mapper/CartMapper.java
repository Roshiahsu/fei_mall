package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.Cart.Cart;
import cn.tedu.mall.pojo.Cart.CartInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName CartMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/28、下午3:33
 */
@Repository
public interface CartMapper {

    /**
     * 新增購物車
     * @param cart 欲新增的商品訊息
     * @return
     */
    int insert(Cart cart);

    /**
     * 修改商品訊息
     * @param cart 欲修改的訊息
     * @return
     */
    int updateByCartId(Cart cart);

    /**
     * 根據userId與spuId查詢商品是否已經加入購物車
     * @param userId 用戶id
     * @param spuId 商品id
     * @return
     */
    Cart selectExistsCart(@Param("userId") Long userId,@Param("spuId")Long spuId);

    /**
     * 根據用戶id查詢購物車列表
     * @param userId
     * @return
     */
    List<CartInfoVO> listCartInfoByUserId(Long userId);

    /**
     * 根據id刪除購物車
     * @param id
     * @return
     */
    int deleteCartById(Long id);

    /**
     * 清空購物車
     * @return
     */
    int deleteAllCarts(Long userId);
}
