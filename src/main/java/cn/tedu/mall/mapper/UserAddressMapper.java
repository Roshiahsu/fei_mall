package cn.tedu.mall.mapper;

import cn.tedu.mall.pojo.domain.Address;
import cn.tedu.mall.pojo.user.UserAddressDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    int insertAddress(UserAddressDTO userAddressDTO);

    /**
     * 修改用戶地址
     * @param userAddressDTO
     * @return
     */
    int updateUserAddress(UserAddressDTO userAddressDTO);

    /**
     * 根據userId修改Default的值並排除輸入的id
     * @param userId 用戶id
     * @param id 地址id
     * @return
     */
    int updateAddressDefaultByUserId(Long userId,Long id);

    /**
     * 根據詳細地址統計數量
     * @param id 地址id
     * @return
     */
    int countAddressByDetail(Long id);

    /**
     * 根據用戶id統計數量
     * @param userID 用戶id
     * @return
     */
    int countAddressByUserId(Long userID);

    /**
     * 根據id獲取地址
     * @param id 地址id
     * @return
     */
    Address getAddressById(Long id);

    /**
     * 根據用戶id獲取地址列表
     * @param userId 用戶id
     * @return
     */
    List<Address> listAddress(Long userId);

    /**
     * 根據id刪除資料
     * @param id 主鍵id
     * @return
     */
    int deleteById(Long id);

}


