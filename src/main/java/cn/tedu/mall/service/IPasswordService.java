package cn.tedu.mall.service;

import cn.tedu.mall.pojo.user.UserUpdateDTO;

/**
 * @ClassName IPasswordService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/2/10、上午10:25
 */
public interface IPasswordService {

    /**
     * 用戶修改密碼
     * @param userUpdateDTO
     */
    void updatePassword(UserUpdateDTO userUpdateDTO);

    /**
     * 重置密碼
     * @param username 用戶名稱
     */
    void resetPassword(String username);
}
