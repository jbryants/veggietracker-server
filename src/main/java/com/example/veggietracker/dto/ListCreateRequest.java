package com.example.veggietracker.dto;


import com.example.veggietracker.model.User;

public class ListCreateRequest {
    private String name;
    private String shop;

    public ListCreateRequest(String name, String shop) {
        this.name = name;
        this.shop = shop;
    }

    public ListCreateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}
