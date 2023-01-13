package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.user.UserAddressDTO;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserAddressMapper
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/13、上午11:50
 */
@Repository
public interface UserAddressMapper {

    /**
     * 新增地址
     * @param userAddressDTO
     * @return
     */
    int insetAddress(UserAddressDTO userAddressDTO);

    /**
     * 修改用戶地址
     * @param userAddressDTO
     * @return
     */
    int updateUserAddress(UserAddressDTO userAddressDTO);

    /**
     * 根據詳細地址統計數量
     * @param detailAddress
     * @return
     */
    int countAddressByDetail(String detailAddress);
}
