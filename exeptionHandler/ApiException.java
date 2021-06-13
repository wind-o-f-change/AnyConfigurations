package ru.ocrv.uad.backend.config.exeptionHandler;

import java.util.Date;

public class ApiException {
    private Date time;
    private int code;
    private String message;

    private ApiException() {
        this.time = new Date(System.currentTimeMillis());
    }

    public ApiException(String message) {
        this();
        this.message = message;
    }

    public ApiException(int code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}