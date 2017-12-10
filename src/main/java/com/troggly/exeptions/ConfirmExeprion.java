package com.troggly.exeptions;

public class ConfirmExeprion extends Exception {

    private static final long serialVersionUID = -2112601567391831082L;

    public ConfirmExeprion() {
        super();
    }

    public ConfirmExeprion(String message) {
        super(message);
    }

    public ConfirmExeprion(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmExeprion(Throwable cause) {
        super(cause);
    }

    protected ConfirmExeprion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
