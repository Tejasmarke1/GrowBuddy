package com.example.growbuddy;

public class ItemModel {
    private String itm_id;
    private String itm_headline;
    private String itm_description;
    private String itm_price;
    private String itm_productBrand;
    private String itm_category;
    private String itm_productImage;


    public ItemModel(String itm_headline, String itm_description, String itm_price,
                     String itm_productBrand, String itm_category, String itm_productImage) {
        this.itm_headline = itm_headline;
        this.itm_description = itm_description;
        this.itm_price = itm_price;
        this.itm_productBrand = itm_productBrand;
        this.itm_category = itm_category;
        this.itm_productImage = itm_productImage;
    }


    public String getItm_headline() {
        return itm_headline;
    }

    public void setItm_headline(String itm_headline) {
        this.itm_headline = itm_headline;
    }

    public String getItm_description() {
        return itm_description;
    }

    public void setItm_description(String itm_description) {
        this.itm_description = itm_description;
    }

    public String getItm_price() {
        return itm_price;
    }

    public void setItm_price(String itm_price) {
        this.itm_price = itm_price;
    }

    public String getItm_productBrand() {
        return itm_productBrand;
    }

    public void setItm_productBrand(String itm_productBrand) {
        this.itm_productBrand = itm_productBrand;
    }

    public String getItm_category() {
        return itm_category;
    }

    public void setItm_category(String itm_category) {
        this.itm_category = itm_category;
    }

    public String getItm_productImage() {
        return itm_productImage;
    }

    public void setItm_productImage(String itm_productImage) {
        this.itm_productImage = itm_productImage;
    }

    public String getItm_id() {
        return itm_id;
    }

    public void setItm_id(String itm_id) {
        this.itm_id = itm_id;
    }

    public ItemModel() {
    }

}