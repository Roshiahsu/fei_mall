package cn.tedu.mall;

import cn.tedu.mall.mapper.CartMapper;
import cn.tedu.mall.pojo.Cart.Cart;
import cn.tedu.mall.pojo.Cart.CartInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @ClassName CartTest
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/30、下午3:10
 */
@SpringBootTest
@Slf4j
public class CartTest {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void inserTest(){
        Cart cart = new Cart();
        cart.setPrice(100);
        cart.setQuantity(10);
        cart.setSpuId(2L);
        cart.setUserId(1L);
        cartMapper.insert(cart);
    }

    @Test
    public void existTest() {
        Cart cart = cartMapper.selectExistsCart(2L, 1L);
        log.debug("cart>>{}", cart);
    }
    @Test
    public void updateTest() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setPrice(1000);
        cart.setQuantity(100);
//        cart.setSpuId(1L);
//        cart.setUserId(1L);
        cartMapper.updateByCartId(cart);
    }
    @Test
    public void listByUserIdTest() {
        List<CartInfoVO> list = cartMapper.listCartInfoByUserId(13L);

        for (CartInfoVO cartInfoVO : list) {
            log.debug("list>>>{}",cartInfoVO);
        }
    }

}
