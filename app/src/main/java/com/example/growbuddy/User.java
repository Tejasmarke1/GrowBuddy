package com.example.growbuddy;

import java.util.List;

public class User {

    private String email;

    private  String price;
    private  String address;
    private  String mobile;
    private  String name;
    private  String pincode;

    private  String image;





     // Add this field\



    public User() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User(String email, String price, String address, String mobile, String name, String pincode, String image) {
        this.email = email;
        this.price = price;
        this.address = address;
        this.mobile = mobile;
        this.name = name;
        this.pincode = pincode;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    // Constructor, getters, and setters
}


