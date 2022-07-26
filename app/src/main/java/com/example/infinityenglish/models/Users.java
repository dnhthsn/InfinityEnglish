package com.example.infinityenglish.models;

import java.io.Serializable;

public class Users implements Serializable {
    private String name, phone, password, address, gender;

    public Users() {
    }

    public Users(String name, String phone, String password, String address, String gender) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.gender = gender;
    }

    public Users(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }
}
