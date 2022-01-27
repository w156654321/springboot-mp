package com.xiaojiuwo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiaojiuwo.exception.ErrorType;
import com.xiaojiuwo.exception.SystemErrorType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Api请求的返回模型，所有Api正常都返回该类的对象")
@Data
public class ApiResult<T> {

    public static final String SUCCESSFUL_CODE = "200";
    public static final String SUCCESSFUL_MSG = "操作成功";

    @ApiModelProperty(value = "处理结果code", required = true)
    private String code;
    @ApiModelProperty(value = "处理结果描述信息")
    private String message;
    @ApiModelProperty(value = "请求结果生成时间戳")
    private long time;
    @ApiModelProperty(value = "处理结果数据信息")
    private T data;

    public ApiResult() {
        this.time = System.currentTimeMillis();
    }


    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param message
     * @param data
     */
    private ApiResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, data);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, null);
    }

    public static  <T> ApiResult<T> error(String code, String msg) {
        return new ApiResult(code, msg, null);
    }


    /**
     * 成功code=000000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.code);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }


    /**
     * 生成返回结果
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static <T> ApiResult result(String code, String msg, Object data) {
        return new ApiResult<>(code, msg, data);
    }


    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static <T> ApiResult<T> fail(ApiResult r) {
        return new ApiResult(r.getCode(), r.getMessage(), r.getData());
    }

    public static <T> ApiResult<T> fail(ErrorType errorType) {
        return new ApiResult(errorType.getCode(), errorType.getMsg(),"");
    }

    public static <T> ApiResult<T> fail() {
        return new ApiResult(SystemErrorType.SYSTEM_BUSY.getCode(), SystemErrorType.SYSTEM_BUSY.getMsg(), "");
    }

}
