package com.ntx.base.util;

import com.ntx.base.constant.ResponseCode;

public class BaseResponse {

    public int code;

    public String message;

    public BaseResponse ok(){
        this.code = ResponseCode.OK;
        this.message = ResponseCode.OK_MESSAGE;
        return this;
    }

    public static BaseResponse abnormal(int code, String message){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        return baseResponse;
    }




    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
