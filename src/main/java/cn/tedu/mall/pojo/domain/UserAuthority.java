package cn.tedu.mall.pojo.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @ClassName UserAuthority
 * @Version 1.0
 * @Description 用戶權限
 * @Date 2022/12/22、上午3:08
 */
@Data
@Deprecated
public class UserAuthority implements Serializable, GrantedAuthority {
    private String authority;

    public UserAuthority(String authority) {
        this.authority = authority;
    }
}
