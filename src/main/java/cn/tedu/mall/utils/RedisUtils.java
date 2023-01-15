package cn.tedu.mall.utils;

/**
 * @ClassName RedisUtils
 * @Version 1.0
 * @Description redis 工具包
 * @Date 2023/1/15、下午1:12
 */
public class RedisUtils {
    /**
     * 查詢商品列表推播種類，所有類型ID
     */
    public static final Integer ALL_PRODUCT = 1;
    /**
     * 查詢商品列表推播種類，所有類型名稱
     */
    public static final String ALL_PRODUCT_NAME = "allProduct";

    /**
     * 查詢商品列表推播種類，新品上市ID
     */
    public static final Integer NEW_PRODUCT = 2;
    /**
     * 查詢商品列表推播種類，新品上市名稱
     */
    public static final String NEW_PRODUCT_NAME = "newProduct";
    /**
     * 查詢商品列表推播種類，熱門類型ID
     */
    public static final Integer HOT_PRODUCT = 3;
    /**
     * 查詢商品列表推播種類，熱門類型名稱
     */
    public static final String HOT_PRODUCT_NAME = "hotProduct";
    /**
     * 查詢商品列表推播種類，優惠類型ID
     */
    public static final Integer DISCOUNTED_PRODUCT = 4;
    /**
     * 查詢商品列表推播種類，優惠類型名稱
     */
    public static final String DISCOUNTED_PRODUCT_NAME = "discountedProduct";

    /**
     * 品牌列表前綴
     */
    public static final String KEY_PREFIX_BRAND_LIST = "brand:list";

    /**
     * 商品列表前綴
     */
    public static String getProductKey(String type){
        return "product:"+type+":list";
    }
}
