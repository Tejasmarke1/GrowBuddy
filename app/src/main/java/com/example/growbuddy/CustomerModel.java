package com.example.growbuddy;

public class CustomerModel {
    private String name,mobile,price,image;

    public CustomerModel() {
    }

    public CustomerModel(String name, String mobile, String price, String image) {
        this.name = name;
        this.mobile = mobile;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String photo) {
        this.image = photo;
    }
}
