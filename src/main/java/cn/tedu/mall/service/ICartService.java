package cn.tedu.mall.service;


import cn.tedu.mall.pojo.Cart.Cart;
import cn.tedu.mall.pojo.Cart.CartAddNewDTO;
import cn.tedu.mall.pojo.Cart.CartInfoVO;
import cn.tedu.mall.pojo.Cart.CartUpdateDTO;
import cn.tedu.mall.web.JsonPage;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName ICartService
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/30、下午3:39
 */
@Transactional
public interface ICartService {

    void insert(CartAddNewDTO cartAddNewDTO);

    void updateCart(List<CartUpdateDTO> cartUpdateDTO);

    List<CartInfoVO> listCartByUserId();

    void deleteCartById(Long id);

    void deleteAllCarts();
}
