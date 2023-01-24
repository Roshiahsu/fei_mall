package cn.tedu.mall.service;

import cn.tedu.mall.pojo.domain.Address;
import cn.tedu.mall.pojo.user.UserAddressDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName IUserAddressService
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/23、下午7:41
 */
@Transactional
public interface IUserAddressService {

    void insert(UserAddressDTO userAddressDTO);

    void updateAddress(UserAddressDTO userAddressDTO);

    Address getAddressById(Long id);

    List<Address> listAddress(Long userId);

    void deleteById(Long id);
}
