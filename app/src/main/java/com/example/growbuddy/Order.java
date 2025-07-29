package com.example.growbuddy;

public class Order {
    private String address;
    private String pincode;
    private String price;
    private String email;
    private String number;

    public Order() {
    }

    public Order(String address, String pincode, String price, String email,String number) {
        this.address = address;
        this.pincode = pincode;
        this.price = price;
        this.email = email;
        this.number=number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // Constructor, getters, and setters
}

