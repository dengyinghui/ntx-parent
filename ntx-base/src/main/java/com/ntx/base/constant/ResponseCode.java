package com.ntx.base.constant;

/**
 * 基础业务响应码
 * 如需扩展响应码,继承该类即可
 */
public class ResponseCode {

    public static final int OK = 200;

    public static final String OK_MESSAGE = "success";

    public static final int INCORRENT_REQUEST_METHOD_CODE = 405;

    public static final String INCORRENT_REQUEST_METHOD_CODE_MESSAGE = "请求方式不正确";

    public static final int PARAMETER_MISS_CODE = 406;

    public static final String PARAMETER_MISS_CODE_MESSAGE = "参数缺失";
}
