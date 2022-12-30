package cn.tedu.mall.web;

/**
 * @ClassName ServiceCode
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/22、上午1:24
 */
public class ServiceCode {
    /**
     * 成功
     */
    public static final int OK = 20000;
    /**
     * 錯誤：資料不存在
     */
    public static final int ERR_NOT_FOUND = 40400;
    /**
     * 錯誤：JWT數據錯誤，可能被惡意竄改
     */
    public static final int ERR_JWT_INVALID = 40001;
    /**
     * 錯誤：JWT過期
     */
    public static final int ERR_JWT_EXPIRED = 40002;
    /**
     * 錯誤：客戶端數據輸入錯誤
     */
    public static final int ERR_BAD_REQUEST = 40003;
    /**
     * 錯誤：沒有登入訊息
     */
    public static final int ERR_UNAUTHORIZED =40004;
    /**
     * 錯誤：衝突，數據重複
     */
    public static final int ERR_CONFLICT = 40900;
    /**
     * 錯誤：插入數據失敗
     */
    public static final int ERR_INSERT = 50000;
    /**
     * 錯誤：刪除數據失敗
     */
    public static final int ERR_DELETE = 50001;
    /**
     * 錯誤：更新數據失敗
     */
    public static final int ERR_UPDATE = 50002;
    /**
     * 錯誤：未知異常
     */
    public static final int ERR_UNKNOWN = 59999;

}
