package cn.tedu.mall.service;

import cn.tedu.mall.pojo.UserInfoVO;
import cn.tedu.mall.pojo.UserLoginDTO;
import cn.tedu.mall.pojo.UserRegDTO;
import cn.tedu.mall.pojo.UserUpdateDTO;

/**
 * @ClassName IUserService
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/21、下午9:33
 */
public interface IUserService {

    void reg(UserRegDTO userRegDTO);

    String login(UserLoginDTO userLoginDTO);

    void update(UserUpdateDTO userUpdateDTO);

    UserInfoVO userInfo(Long id);
}
