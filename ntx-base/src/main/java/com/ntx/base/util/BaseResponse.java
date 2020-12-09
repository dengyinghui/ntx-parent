package com.ntx.base.util;

import com.ntx.base.constant.ResponseCode;
import lombok.Data;

@Data
public class BaseResponse {

    public int code;

    public String message;

    public static BaseResponse ok(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ResponseCode.OK);
        baseResponse.setMessage(ResponseCode.OK_MESSAGE);
        return baseResponse;
    }

    public static BaseResponse abnormal(int code, String message){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(code);
        baseResponse.setMessage(message);
        return baseResponse;
    }





}
