package com.ntx.base.advice;

import com.ntx.base.constant.ResponseCode;
import com.ntx.base.util.BaseResponse;
import com.ntx.base.util.ResponseUtil;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public BaseResponse handlerException(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if(e instanceof HttpMessageNotReadableException){
            return ResponseUtil.abnormal(ResponseCode.PARAMETER_SERIALIZE_CODE, ResponseCode.PARAMETER_SERIALIZE_MESSAGE, e.getMessage());
        } else if(e instanceof HttpRequestMethodNotSupportedException){
            return BaseResponse.abnormal(ResponseCode.INCORRENT_REQUEST_METHOD_CODE, ResponseCode.INCORRENT_REQUEST_METHOD_CODE_MESSAGE);
        } else if(e instanceof MethodArgumentNotValidException){
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            List<String> list = this.getErrorMessage(bindingResult);
            return ResponseUtil.abnormal(ResponseCode.PARAMETER_MISS_CODE, ResponseCode.PARAMETER_MISS_CODE_MESSAGE, list);
        }  else if(e instanceof MissingServletRequestParameterException){
            String paramName = ((MissingServletRequestParameterException) e).getParameterName();
            return ResponseUtil.abnormal(ResponseCode.PARAMETER_MISS_CODE, ResponseCode.PARAMETER_MISS_CODE_MESSAGE, paramName + ResponseCode.PARAMETER_MISS_CODE_MESSAGE);
        } else if(e instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> set = ((ConstraintViolationException) e).getConstraintViolations();
            List<String> messageList = new ArrayList<>();
            if(set != null && !set.isEmpty()){
                for(ConstraintViolation<?> constraintViolation : set){
                    messageList.add(constraintViolation.getMessage());
                }
            }
            return ResponseUtil.abnormal(ResponseCode.PARAMETER_MISS_CODE, ResponseCode.PARAMETER_MISS_CODE_MESSAGE, messageList);
        } else if(e instanceof MethodArgumentTypeMismatchException){
            String paramName = ((MethodArgumentTypeMismatchException) e).getName();
            return ResponseUtil.abnormal(ResponseCode.PARAMETER_TYPE_MISMATCH_CODE, ResponseCode.PARAMETER_TYPE_MISMATCH_MESSAGE, paramName + ResponseCode.PARAMETER_TYPE_MISMATCH_MESSAGE);
        } else if(e instanceof NoHandlerFoundException){
            NoHandlerFoundException noHandlerFoundException = (NoHandlerFoundException)e;
            String resultURL = noHandlerFoundException.getRequestURL();
            return ResponseUtil.abnormal(ResponseCode.NOT_FOUND_CODE, ResponseCode.NOT_FOUND_MESSAGE, resultURL);
        } else{
            return ResponseUtil.abnormal(ResponseCode.BUSINESS_CODE, ResponseCode.BUSSINESS_MESSAGE);
        }
    }

    /**
     * 获取报错信息
     * @param bindingResult
     * @return
     */
    private List<String> getErrorMessage(BindingResult bindingResult){
        List<String> list = new ArrayList<>();
        List<ObjectError> objectErrorList = bindingResult.getAllErrors();
        for(ObjectError objectError : objectErrorList){
            String message = objectError.getDefaultMessage();
            list.add(message);
        }
        return list;
    }

}
