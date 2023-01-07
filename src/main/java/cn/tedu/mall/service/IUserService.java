package cn.tedu.mall.service;

import cn.tedu.mall.pojo.user.UserInfoVO;
import cn.tedu.mall.pojo.user.UserLoginDTO;
import cn.tedu.mall.pojo.user.UserRegDTO;
import cn.tedu.mall.pojo.user.UserUpdateDTO;
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

    void reg(UserRegDTO userRegDTO);

    String login(UserLoginDTO userLoginDTO);

    void update(UserUpdateDTO userUpdateDTO);

    void updatePassword(UserUpdateDTO userUpdateDTO);

    UserInfoVO userInfo();
}
