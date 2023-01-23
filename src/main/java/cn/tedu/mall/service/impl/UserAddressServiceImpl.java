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
        //檢查要修改的地址是否存在
        int addressCount = userAddressMapper.countAddressByDetail(userAddressDTO.getId());
        if(addressCount ==0){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"地址不存在!");
        }
        //判斷該地址是否是預設
        if(userAddressDTO.getIsDefault() == ConstUtils.IS_DEFAULT){
            //是預設，先將該使用者的其他保存地址的isDefault設定為0
            Long userId = ConstUtils.getUserId();
            userAddressMapper.updateAddressDefaultByUserId(userId);
        }
        int rows = userAddressMapper.updateUserAddress(userAddressDTO);
        if (rows !=1){
            throw new ServiceException(ServiceCode.ERR_UPDATE,"伺服器忙碌請稍候!");
        }
    }
}
