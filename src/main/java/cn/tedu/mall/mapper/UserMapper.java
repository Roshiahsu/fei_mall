package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.user.User;
import cn.tedu.mall.pojo.user.UserInfoVO;
import cn.tedu.mall.pojo.user.UserUpdateDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/21、下午9:44
 */
@Repository
@Transactional
public interface UserMapper {
    /**
     * 新增用戶/插入用戶
     * @param user 新增的用資料
     * @return 影響行數
     */
    int insert(User user);

    /**
     * 使用用戶名統計用戶
     * @param username 用戶名
     * @return 用戶數
     */
    int countByUsername(String username);

    /**
     * 根據用戶名獲取用戶訊息
     * @param username 用戶名
     * @return 查詢到的用戶訊息
     */
    UserInfoVO getByUsername(String username);

    /**
     * 根據id獲取用戶訊息
     * @param id 用戶id
     * @return 查詢到的用戶訊息
     */
    List<UserInfoVO> userInfo(Long id);

    /**
     * 修改用戶訊息
     * @param userUpdateDTO 欲修改的資料
     * @return 影響行數
     */
    int update(UserUpdateDTO userUpdateDTO);

}
