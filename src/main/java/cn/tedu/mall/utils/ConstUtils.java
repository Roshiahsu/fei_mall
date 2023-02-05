package cn.tedu.mall.utils;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.pojo.domain.UserAuthority;
import cn.tedu.mall.security.LoginPrinciple;
import cn.tedu.mall.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * @ClassName ConstUtils
 * @Version 1.0
 * @Description 常量工具包
 * @Date 2022/12/22、上午3:20
 */
@Slf4j
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
     * 狀態描述default
     */
    public static final int IS_DEFAULT = 1;
    /**
     * 狀態描述not default
     */
    public static final int IS_NOT_DEFAULT =0;


    /**
     * 管理員權限
     */
    public static final Integer AUTHORITY_ADMIN_ID = 1;
    /**
     * 一般用戶權限
     */
    public static final Integer AUTHORITY_USER_ID = 2;

    public static final String  CLAIM_KEY_USERNAME = "user";


    /**
     * 從上下文獲取用戶資料
     * @return 用戶資料
     */
    public static LoginPrinciple getUserInfo(){
        log.debug("開始從上下文獲取id");
        UsernamePasswordAuthenticationToken authentication = null;
        try{
            authentication =
                    (UsernamePasswordAuthenticationToken)SecurityContextHolder
                            .getContext().getAuthentication();
        }catch (ClassCastException e){
            throw new ServiceException(ServiceCode.ERR_UNAUTHORIZED, "用戶尚未登入");
        }

        LoginPrinciple loginPrinciple = (LoginPrinciple)authentication.getCredentials();
        return loginPrinciple;
    }
    /**
     * 獲取用戶id
     * @return 用戶id
     */
    public static Long getUserId(){
        return getUserInfo().getId();
    }

    /**
     * 將時間初始化成gmt+8
     * @param localDateTime
     * @return
     */
    public static LocalDateTime initDate(LocalDateTime localDateTime){
        return localDateTime.plusHours(8);
    }
}
