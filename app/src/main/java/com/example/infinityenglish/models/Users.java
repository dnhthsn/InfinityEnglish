package com.example.infinityenglish.models;

import java.io.Serializable;

public class Users implements Serializable {
    private int id;
    private String name;
    private String password;
    private String address;
    private String email;
    private String phone;
    private String gender;
    private String avatar;

    public Users() {
    }

    public Users(String name, String password, String address, String email, String phone, String gender, String avatar) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.avatar = avatar;
    }

    public Users(int id, String name, String password, String address, String email, String phone, String gender, String avatar) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public String getAvatar() {
        return avatar;
    }
}
