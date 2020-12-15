package com.ntx.base.enums;

public enum ConsumerStatusEnum {

    CONSUMERING(0, "消费中"), CONSUMER_SUCCESS(1, "消费成功"),CONSUMER_FAIL(2, "消费失败");

    private int code;

    private String message;

    private ConsumerStatusEnum(int code, String message){
        this.code = code;
        this.message = message;
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
