package cn.tedu.mall.service;

import cn.tedu.mall.pojo.user.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName IUserService
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/21、下午9:33
 */
@Transactional
public interface IUserService {
    /**
     * 用戶註冊
     * @param userRegDTO
     */
    void reg(UserRegDTO userRegDTO);

    /**
     * 用戶登入
     * @param userLoginDTO
     * @return
     */
    String login(UserLoginDTO userLoginDTO);

    /**
     * 更新用戶資料(不包含密碼)
     * @param userUpdateDTO
     */
    void update(UserUpdateDTO userUpdateDTO);

    /**
     * 獲取用戶資料
     * @return
     */
    UserInfoVO userInfo();
}
