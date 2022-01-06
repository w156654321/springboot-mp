package com.xiaojiuwo.exception;

import lombok.Getter;

@Getter
public enum SystemErrorType implements ErrorType {

    SYSTEM_ERROR("500", "系统异常"),
    SYSTEM_BUSY("99999999", "系统繁忙,请稍候再试"),
    GATEWAY_METHOD_NOT_ALLOWED("99990405", "服务调用方法不支持"),
    ARGUMENT_NOT_VALID("99990400", "请求参数校验不通过"),
    TOKEN_INVALID("99990401", "无效token"),
    TOKEN_EXPIRED("99990407", "token已过期"),
    TOKEN_REMOVE_ERROR("200100006","用户禁用失败"),
    TOKEN_NOT_AUTHORIZED("99990403", "无访问权限");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    SystemErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
