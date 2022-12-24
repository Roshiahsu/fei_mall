package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.user.User;
import cn.tedu.mall.pojo.user.UserInfoVO;
import cn.tedu.mall.pojo.user.UserUpdateDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/21、下午9:44
 */
@Repository
@Transactional
public interface UserMapper {

    void insert(User user);

    int countByUsername(String username);

    UserInfoVO getByUsername(String username);

    int update(UserUpdateDTO userUpdateDTO);

    UserInfoVO userInfo(Long id);

}
