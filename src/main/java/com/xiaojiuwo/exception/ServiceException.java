package com.xiaojiuwo.exception;


public class ServiceException extends BaseException {

    public ServiceException(ErrorType errorType) {
        super(errorType);
    }

    public ServiceException(String code,String message) {
        super(code,message);
    }

    public ServiceException(String message) {
        super(SystemErrorType.SYSTEM_ERROR.getCode(),message);
    }

    public ServiceException(ErrorType errorType,String message) {
        super(errorType.getCode(),message);
    }

}
