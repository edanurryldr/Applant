package com.edanuryildirim.applant.Activities.Model.Model;

import java.util.List;

public class Request {
    private String phone;
    private String address;
    private String totals;
    private String status;
    private List<Order> plants;


public Request(String phone, String name, String s, String toString, List<Order> cart){

}

    public Request(String phone, String address, String totals, List<Order> plants) {
        this.phone = phone;
        this.address = address;
        this.totals = totals;
        this.plants = plants;
        this.status="0";

    }
    public  String getStatus(){
    return status;
    }

    public void setStatus(String status){
    this.status=status;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }

    public List<Order> getPlants() {
        return plants;
    }

    public void setPlants(List<Order> plants) {
        this.plants = plants;
    }
}
