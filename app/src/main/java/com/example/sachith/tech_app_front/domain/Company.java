package com.example.sachith.tech_app_front.domain;

public class Company {

    private String name;
    private String address;
    //private String city;

    public Company () {

    }

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
