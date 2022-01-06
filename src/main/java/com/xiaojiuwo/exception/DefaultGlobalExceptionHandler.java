package com.xiaojiuwo.exception;

import com.xiaojiuwo.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
@Slf4j
public class DefaultGlobalExceptionHandler {

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ApiResult missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("missing servlet request parameter exception:{}", ex.getMessage());
        return ApiResult.fail(SystemErrorType.ARGUMENT_NOT_VALID);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ApiResult serviceException(MethodArgumentNotValidException ex) {
        log.error("service exception:{}", ex.getMessage());
        String error = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResult.result(SystemErrorType.ARGUMENT_NOT_VALID.getCode(), error, null);
    }

    @ExceptionHandler(value = {BaseException.class})
    public ApiResult baseException(BaseException ex) {
        log.error("base exception:{}", ex.getMessage());
        return ApiResult.fail(ex.getErrorType());
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ApiResult methodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        log.error("method not allowed exception:{}", ex.getMessage());
        return ApiResult.fail(SystemErrorType.GATEWAY_METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {UserInvalidException.class})
    public ApiResult userTypeMismatchException(UserInvalidException ex) {
        log.error("user invalid exception");
        return ApiResult.fail(SystemErrorType.TOKEN_INVALID);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult exception(Exception ex) {
		log.error("internal server error", ex);
    	return ApiResult.fail();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult throwable(Throwable t) {
		log.error("internal server error", t);
		return ApiResult.fail();
    }

    @ExceptionHandler(value = {ServiceException.class})
    public ApiResult serviceException(ServiceException e) {
        log.error("service exception:{}", e.getErrorType().getMsg());
        return ApiResult.fail(e.getErrorType());
    }



}