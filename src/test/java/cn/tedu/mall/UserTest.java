package cn.tedu.mall;

import cn.tedu.mall.mapper.UserMapper;
import cn.tedu.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


/**
 * @ClassName UserTest
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/21、下午9:54
 */
@SpringBootTest
@Slf4j
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("roshia");
        user.setNickname("roshia");
        user.setIsEnable(0);
        user.setBod(LocalDate.of(1985,8,4));

        userMapper.insert(user);
    }

    @Test
    public void testGetByUsername(){
        String username = "roshia1";
        User user = userMapper.getByUsername(username);
        System.out.println(user.toString());

    }
}
