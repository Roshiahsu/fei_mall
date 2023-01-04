package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.CartMapper;
import cn.tedu.mall.pojo.Cart.Cart;
import cn.tedu.mall.pojo.Cart.CartAddNewDTO;
import cn.tedu.mall.pojo.Cart.CartInfoVO;
import cn.tedu.mall.service.ICartService;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.web.JsonPage;
import cn.tedu.mall.web.ServiceCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName CartServiceImpl
 * @Version 1.0
 * @Description 購物車Service層
 * @Date 2022/12/30、下午3:40
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public void insert(CartAddNewDTO cartAddNewDTO) {
        log.debug("新增購物車Service層開始");
        //從上下文獲取UserId
        Long userId = ConstUtils.getUserId();
        //根據userId與spuId查詢該商品是否已經加入購物車
        Cart cartInfo = cartMapper.selectExistsCart(userId, cartAddNewDTO.getSpuId());

        Cart cart = new Cart();
        if(cartInfo !=null){
            log.debug("商品已存在，修改購買數量");
            //商品已存在，修改購買數量
            BeanUtils.copyProperties(cartInfo,cart);
            //獲取原始數量
            int originalQuantity = cartInfo.getQuantity();
            //原始數量加上新增的數量
            cart.setQuantity(originalQuantity+cartAddNewDTO.getQuantity());
            //添加修改時間
            cart.setGmtModified(LocalDateTime.now());
            int rows = cartMapper.updateByCartId(cart);
            if(rows !=1){
                throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器忙碌中，請稍後再試!！");
            }
        }else{
            log.debug("商品不存在，新增購物車");
            //商品不存在，新增購物車
            BeanUtils.copyProperties(cartAddNewDTO,cart);
            cart.setUserId(userId);
            cart.setGmtCreate(LocalDateTime.now());
            cart.setGmtModified(LocalDateTime.now());
            int rows = cartMapper.insert(cart);
            if(rows !=1){
                throw new ServiceException(ServiceCode.ERR_INSERT,"伺服器忙碌中，請稍後再試!！");
            }
        }
    }

    //根據用戶id查詢當前用戶購物車中商品訊息
    @Override
    public List<CartInfoVO> listCartByUserId() {
        log.debug("根據用戶id查詢購物車中商品訊息開始");
        //從上下文獲取UserId
        Long userId = ConstUtils.getUserId();
        log.debug("獲取到的ID>>>{}",userId);
        //設定分頁數


        List<CartInfoVO> list = cartMapper.listCartInfoByUserId(userId);
        //小計每件商品價錢
        for (CartInfoVO cartInfoVO : list) {
            cartInfoVO.setSubtotal(cartInfoVO.getPrice() * cartInfoVO.getQuantity());
        }
        return list;
    }

    @Override
    public void deleteCartById(Long id) {
        log.debug("CartService.deleteCartById開始");
        //從上下文獲取id，判斷用戶是否登入，沒有登入則在方法調用時拋異常
        Long userId = ConstUtils.getUserId();
        int rows = cartMapper.deleteCartById(id);
        if(rows==0){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"您要刪除的商品不存在");
        }
    }

    //清空購物車
    @Override
    public void deleteAllCarts() {
        //從上下文獲取id
        Long userId = ConstUtils.getUserId();
        int rows = cartMapper.deleteAllCarts(userId);
        if(rows==0){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"您要刪除的商品不存在");
        }
    }
}
