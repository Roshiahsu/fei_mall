package cn.tedu.mall.exception;

/**
 * @ClassName ServiceException
 * @Version 1.0
 * @Description 自定義異常
 * @Date 2022/12/22、上午1:07
 */
public class ServiceException extends RuntimeException{

    private Integer serviceCode;

    public ServiceException(Integer serviceCode,String message) {
        super(message);
        this.serviceCode=serviceCode;
    }

    public Integer getServiceCode() {
        return serviceCode;
    }
}
