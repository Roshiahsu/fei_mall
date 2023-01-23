package cn.tedu.mall.service.impl;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.mapper.UserAddressMapper;
import cn.tedu.mall.pojo.domain.Address;
import cn.tedu.mall.pojo.user.UserAddressDTO;
import cn.tedu.mall.service.IUserAddressService;
import cn.tedu.mall.utils.ConstUtils;
import cn.tedu.mall.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserAddressServiceImpl
 * @Version 1.0
 * @Description TODO
 * @Date 2023/1/23、下午7:42
 */
@Service
@Slf4j
public class UserAddressServiceImpl implements IUserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 獲取地址詳情
     * @param id 地址id
     * @return 地址詳情
     */
    @Override
    public Address getAddressById(Long id) {
        log.debug("開始獲取地址詳情");
        return userAddressMapper.getAddressById(id);
    }

    /**
     * 根據用戶id獲取地址列表
     * @param userId 用戶id
     * @return
     */
    @Override
    public List<Address> listAddress(Long userId) {
        log.debug("開始獲取地址列表");
        return userAddressMapper.listAddress(userId);
    }

    /**
     * 修改用戶地址
     * @param userAddressDTO
     */
    @Override
    public void updateAddress(UserAddressDTO userAddressDTO) {
        log.debug("開始修改用戶地址");
        //獲取userId
        Long userId = ConstUtils.getUserId();
        userAddressDTO.setUserId(userId);
        int addressCount = userAddressMapper.countAddressByDetail(userAddressDTO.getDetailedAddress());

        if(addressCount >0 ){
            log.debug("數據已存在，進行修改");
            int rows = userAddressMapper.updateUserAddress(userAddressDTO);
            if (rows !=1){
                throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"伺服器忙碌請稍候!");
            }
        }else{
            log.debug("數據不存在，進行新增");
            int rows = userAddressMapper.insetAddress(userAddressDTO);
            if (rows !=1){
                throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"伺服器忙碌請稍候!");
            }
        }
    }
}
