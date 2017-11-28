package com.troggly.exeptions;

/**
 * Created by Vlad on 27.07.2017.
 */
public class AuthorizedException extends Exception {

    private static final long serialVersionUID = 1132514298460083082L;

    public AuthorizedException() {

    }

    public AuthorizedException(String message) {
        super(message);
    }

    public AuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizedException(Throwable cause) {
        super(cause);
    }

    protected AuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
