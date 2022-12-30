package cn.tedu.mall.utils;

import cn.tedu.mall.pojo.domain.UserAuthority;

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

    /*
     *權限常量
     */
    /**
     * 管理員權限
     */
    public static final Integer AUTHORITY_ADMIN_ID = 1;
    /**
     * 一般用戶權限
     */
    public static final Integer AUTHORITY_USER_ID = 2;

    public static final String  CLAIM_KEY_USERNAME = "user";


}
