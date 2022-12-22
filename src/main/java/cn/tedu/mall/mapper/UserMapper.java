package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.User;
import org.apache.ibatis.annotations.Param;
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

    User getByUsername(String username);

}
