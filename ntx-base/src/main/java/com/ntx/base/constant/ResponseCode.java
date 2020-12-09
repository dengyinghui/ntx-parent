package com.ntx.base.constant;

/**
 * 基础业务响应码
 * 如需扩展响应码,继承该类即可
 */
public class ResponseCode {

    public static final int OK = 200;

    public static final String OK_MESSAGE = "success.";

    public static final int INCORRENT_REQUEST_METHOD_CODE = 405;

    public static final String INCORRENT_REQUEST_METHOD_CODE_MESSAGE = "请求方式不正确.";

    public static final int PARAMETER_MISS_CODE = 406;

    public static final String PARAMETER_MISS_CODE_MESSAGE = "参数缺失.";

    public static final int PARAMETER_TYPE_MISMATCH_CODE = 407;

    public static final String PARAMETER_TYPE_MISMATCH_MESSAGE = "类型转换异常.";

    public static final int PARAMETER_SERIALIZE_CODE = 408;

    public static final String PARAMETER_SERIALIZE_MESSAGE = "参数序列化异常";

    public static final int BUSINESS_CODE = 500;

    public static final String BUSSINESS_MESSAGE = "业务异常.";


}
