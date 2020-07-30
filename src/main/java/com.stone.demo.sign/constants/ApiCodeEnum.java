package com.stone.demo.sign.constants;

public enum ApiCodeEnum {


    SUCCESS("10000", "success"),

    UNKOWN_ERROR("ERR0001", "未知错误"),

    PARAMETER_ERROR("ERR0002", "参数错误"),

    TOKEN_EXPIRE("ERR0003", "认证过期"),

    REQUEST_TIMEOUT("ERR0004", "请求超时"),

    SIGN_ERROR("ERR0005", "签名错误"),

    REPEAT_SUBMIT("ERR0006", "请不要频繁操作");


    private String code;

    private String value;

    ApiCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}
