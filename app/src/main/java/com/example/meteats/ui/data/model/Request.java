package com.example.meteats.ui.data.model;

import com.example.meteats.data.model.Order;

import java.util.List;

public class Request {
    private String phone;
    private String total;
    private  String name;
    private List<Order>foods;

    public Request(){

    }

    public Request(String phone, String total, String name, List<Order> foods) {
        this.phone = phone;
        this.total = total;
        this.name = name;
        this.foods = foods;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}