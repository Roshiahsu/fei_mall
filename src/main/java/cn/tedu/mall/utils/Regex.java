package cn.tedu.mall.utils;

/**
 * @ClassName Regex
 * @Version 1.0
 * @Description 正則表達式
 * @Date 2023/1/13、上午9:30
 */
public interface Regex {

    String REGEXP_USER_EMAIL = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    String MESSAGE_USER_EMAIL = "請填寫正確格式電子信箱！";

    String REGEXP_USER_PHONE = "^09[0-9]{8}$";
    String MESSAGE_USER_PHONE = "請填寫正確手機號碼！";

    String REGEXP_USER_NICKNAME = ".{2,16}";
    String MESSAGE_USER_NICKNAME = "暱稱格式錯誤，長度2-6個字";

}
