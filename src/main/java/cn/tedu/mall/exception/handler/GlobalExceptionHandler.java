package cn.tedu.mall.exception.handler;

import cn.tedu.mall.exception.ServiceException;
import cn.tedu.mall.web.JsonResult;
import cn.tedu.mall.web.ServiceCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;

/**
 * @ClassName GlobalExceptionHandler
 * @Version 1.0
 * @Description TODO
 * @Date 2022/12/22、上午1:09
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public JsonResult handlerServiceException(ServiceException e){
        return JsonResult.fail(e);
    }

    @ExceptionHandler
    public JsonResult handlerThrowableException(Throwable e){
        e.printStackTrace();
        return JsonResult.fail(ServiceCode.ERR_UNKNOWN,e.getMessage());
    }

    @ExceptionHandler
    public JsonResult handlerBindException(BindException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringJoiner stringJoiner = new StringJoiner(";","錯誤:","!");
        for (FieldError fieldError : fieldErrors) {
            stringJoiner.add(fieldError.getDefaultMessage());
        }
        return JsonResult.fail(ServiceCode.ERR_BAD_REQUEST,stringJoiner.toString());
    }

    @ExceptionHandler
    public JsonResult handlerAccessDeniedException(AccessDeniedException e){
        e.printStackTrace();
        return JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED,e.getMessage());
    }


}
