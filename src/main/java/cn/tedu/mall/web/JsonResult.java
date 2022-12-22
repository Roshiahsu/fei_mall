package cn.tedu.mall.web;

import cn.tedu.mall.exception.ServiceException;
import lombok.Data;

/**
 * @ClassName JsonResult
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/22、上午1:11
 */
@Data
public class JsonResult {

    private Integer serviceCode;

    private String message;

    private Object data;

    public static JsonResult ok(){
        return ok(null);
    }

    public static JsonResult ok(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.serviceCode=ServiceCode.OK;
        jsonResult.data=data;
        return jsonResult;
    }

    public static JsonResult fail(ServiceException e){
        return JsonResult.fail(e.getServiceCode(),e.getMessage());
    }

    public static JsonResult fail(Integer serviceCode,String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.serviceCode=serviceCode;
        jsonResult.message=message;
        return jsonResult;
    }


}
