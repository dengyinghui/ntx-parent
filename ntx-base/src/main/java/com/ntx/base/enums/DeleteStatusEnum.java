package com.ntx.base.enums;

public enum DeleteStatusEnum {

    UNDELETE(0, "未删除"), DELETEED(1, "以删除");

    private int code;

    private String message;

    private DeleteStatusEnum(int code, String message){
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
