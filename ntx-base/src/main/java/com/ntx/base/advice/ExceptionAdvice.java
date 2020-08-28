package com.ntx.base.advice;

import com.ntx.base.constant.ResponseCode;
import com.ntx.base.util.BaseResponse;
import com.ntx.base.util.ResponseUtil;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public BaseResponse handlerException(HttpServletRequest request, Exception exception){
        exception.printStackTrace();
        if(exception instanceof HttpMessageNotReadableException || exception instanceof HttpRequestMethodNotSupportedException){
            return BaseResponse.abnormal(ResponseCode.INCORRENT_REQUEST_METHOD_CODE, ResponseCode.INCORRENT_REQUEST_METHOD_CODE_MESSAGE);
        } else if(exception instanceof MethodArgumentNotValidException){
            BindingResult bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
            String error = this.getErrorMessage(bindingResult);
            return ResponseUtil.abnormal(ResponseCode.PARAMETER_MISS_CODE, ResponseCode.PARAMETER_MISS_CODE_MESSAGE, error);
        }
        return null;
    }

    /**
     * 获取报错信息
     * @param bindingResult
     * @return
     */
    private String getErrorMessage(BindingResult bindingResult){
        List<ObjectError> objectErrorList = bindingResult.getAllErrors();
        if(objectErrorList != null && !objectErrorList.isEmpty()){
            ObjectError objectError = objectErrorList.get(0);
            String message = objectError.getDefaultMessage();
            return message;
        }
        return null;
    }

}
