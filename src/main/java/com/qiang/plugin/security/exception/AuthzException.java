package com.qiang.plugin.security.exception;

/**
 * Created by liq on 2018/4/24.
 */
public class AuthzException extends RuntimeException {

    public AuthzException(){
        super();
    }

    public AuthzException(String message){
        super(message);
    }

    public AuthzException(String message, Throwable cause){
        super(message, cause);
    }

    public AuthzException(Throwable cause){
        super(cause);
    }
}
