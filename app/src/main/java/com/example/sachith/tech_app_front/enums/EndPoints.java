package com.example.sachith.tech_app_front.enums;

public enum EndPoints {

    CREATE_COMPANY("http://192.168.8.100:8080/tech/companies/create"),
    GET_ALL_COMPANY("http://192.168.8.100:8080/tech/companies");

    private String url;

    EndPoints(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
