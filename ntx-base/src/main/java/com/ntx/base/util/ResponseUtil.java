package com.ntx.base.util;

import com.alibaba.fastjson.JSONObject;
import com.ntx.base.constant.ResponseCode;

public class ResponseUtil extends BaseResponse {

    private Object data;

    public static ResponseUtil ok(Object obj){
        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setCode(ResponseCode.OK);
        responseUtil.setMessage(ResponseCode.OK_MESSAGE);
        responseUtil.setData(obj);
        return responseUtil;
    }

    public static ResponseUtil ok(int code, String message, Object obj){
        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setCode(code);
        responseUtil.setMessage(message);
        responseUtil.setData(obj);
        return responseUtil;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(ResponseUtil.ok("d")));
    }

}
