package cn.tedu.mall.utils;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.pojo.domain.UserAuthority;
import cn.tedu.mall.security.LoginPrinciple;
import cn.tedu.mall.web.ServiceCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName ConstUtils
 * @Version 1.0
 * @Description 常量工具包
 * @Date 2022/12/22、上午3:20
 */
public class ConstUtils {

    /**
     * 狀態描述Enable
     */
    public static final int IS_ENABLE = 1;
    /**
     * 狀態描述Disable
     */
    public static final int IS_DISABLE =0;

    /**
     * 管理員權限
     */
    public static final Integer AUTHORITY_ADMIN_ID = 1;
    /**
     * 一般用戶權限
     */
    public static final Integer AUTHORITY_USER_ID = 2;

    public static final String  CLAIM_KEY_USERNAME = "user";

    //從上下文獲取用戶訊息
    public static LoginPrinciple getUserInfo(){
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new ServiceException(ServiceCode.ERR_UNAUTHORIZED, "沒有登入訊息");
        }

        LoginPrinciple loginPrinciple = (LoginPrinciple)authentication.getCredentials();
        return loginPrinciple;
    }

    public static Long getUserId(){
        return getUserInfo().getId();
    }
}
