package com.example.sachith.tech_app_front.enums;

public enum Status {

    ACTIVE("ACTIVE"),
    IN_ACTIVE("INACTIVE");

    private String status;

    private Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
