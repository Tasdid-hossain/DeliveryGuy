package com.example.deliveryguy;

import java.util.ArrayList;

public class Request {
    public String address;
    public String status;

    public Request() {
    }

    public Request(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
