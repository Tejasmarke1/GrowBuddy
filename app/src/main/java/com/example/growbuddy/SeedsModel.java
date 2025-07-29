package com.example.growbuddy;

public class SeedsModel {
    private String seed_name;
    private String seed_price;
    private String seed_description;
    private String seed_image;

    public String getSeed_name() {
        return seed_name;
    }

    public void setSeed_name(String seed_name) {
        this.seed_name = seed_name;
    }

    public String getSeed_price() {
        return seed_price;
    }

    public void setSeed_price(String seed_price) {
        this.seed_price = seed_price;
    }

    public String getSeed_description() {
        return seed_description;
    }

    public void setSeed_description(String seed_description) {
        this.seed_description = seed_description;
    }

    public String getSeed_image() {
        return seed_image;
    }

    public void setSeed_image(String seed_image) {
        this.seed_image = seed_image;
    }

    public String getSeed_quntity() {
        return seed_quntity;
    }

    public void setSeed_quntity(String seed_quntity) {
        this.seed_quntity = seed_quntity;
    }

    private String seed_quntity;

    public SeedsModel() {
    }

    public SeedsModel(String seed_name, String seed_price, String seed_description, String seed_image, String seed_quntity) {
        this.seed_name = seed_name;
        this.seed_price = seed_price;
        this.seed_description = seed_description;
        this.seed_image = seed_image;
        this.seed_quntity = seed_quntity;
    }
}
