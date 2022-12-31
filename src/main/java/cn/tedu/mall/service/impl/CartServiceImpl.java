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
 * @Description TODO
 * @Date 2022/12/30、下午3:40
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public void insert(CartAddNewDTO cartAddNewDTO) {
        log.debug("CartService.insert開始");
        Long userId =null;
        try{
             userId = ConstUtils.getUserId();
        }catch (ClassCastException e){
            log.debug("捕獲異常");
            throw new ServiceException(ServiceCode.ERR_UNAUTHORIZED,"用戶尚未登入!");
        }

        //根據userId與spuId查詢該商品是否已經加入購物車
        Cart cartInfo = cartMapper.selectExistsCart(userId, cartAddNewDTO.getSpuId());


        Cart cart = new Cart();
        if(cartInfo !=null){
            log.debug("商品已存在，修改購買數量");
            //商品已存在，修改購買數量
            BeanUtils.copyProperties(cartInfo,cart);
            int originalQuantity = cartInfo.getQuantity();
            cart.setQuantity(originalQuantity+cartAddNewDTO.getQuantity());
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

    @Override
    public JsonPage<CartInfoVO> listCartByUserId(Integer pageNum, Integer pageSize) {
        log.debug("CartService.listCartByUserId開始");
        //從上下文獲取UserId
        Long userId = ConstUtils.getUserId();
        //設定分頁數
        PageHelper.startPage(pageNum,pageSize);

        List<CartInfoVO> list = cartMapper.listCartInfoByUserId(userId);

        return JsonPage.restPage(new PageInfo<>(list));
    }
}
