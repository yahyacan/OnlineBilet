package com.etiya.onlinebilet.enums;

public enum HataMesaji {

    SUCCESS("0000","İşleminiz tamamlandı!"),
    ERROR("0001","İşleminiz esnasında hata oluştu!");

    private String code;
    private String message;

    private HataMesaji(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
