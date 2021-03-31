package com.edanuryildirim.applant.Activities.Model.Model;

public class User {

    private String Name;
    private String Phone;

    public User(String s, String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
