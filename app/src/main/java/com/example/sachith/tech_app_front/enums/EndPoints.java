package com.example.sachith.tech_app_front.enums;

public enum EndPoints {

    CREATE_COMPANY("https://ctse-tech-com-app.herokuapp.com/tech/companies/create"),
    GET_ALL_COMPANY("https://ctse-tech-com-app.herokuapp.com/tech/companies"),
    COMPANY("https://ctse-tech-com-app.herokuapp.com/tech/companies/");

    private String url;

    EndPoints(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
