package com.etiya.onlinebilet.dto;

import com.etiya.onlinebilet.enums.HataMesaji;

public class BaseResponse<T> {

    private String code = HataMesaji.SUCCESS.getCode();
    private String message = HataMesaji.SUCCESS.getMessage();
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
