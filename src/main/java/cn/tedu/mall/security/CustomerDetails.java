package cn.tedu.mall.security;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.Collection;

/**
 * @ClassName CustomerDetails
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/22、下午3:38
 */

@Setter
@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class CustomerDetails extends User  {

    private  Long id;

    public CustomerDetails(
            Long id,
            String username,
            String password,
            boolean enabled,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled,
                true,
                true,
                true,
                authorities);
        this.id = id;
    }
}
