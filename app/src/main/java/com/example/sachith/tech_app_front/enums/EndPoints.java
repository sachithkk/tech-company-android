/**
 *
 * Created by Sachith Tharaka
 *
 **/
package com.example.sachith.tech_app_front.enums;

// this enum helps to manage set of named values.
// we add the API urls fo enum to manage URL from one place
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
