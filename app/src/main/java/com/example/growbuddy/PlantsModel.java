package com.example.growbuddy;

public class PlantsModel {
    private String plant_name;
    private String plant_description;
    private String plant_image;
    private String plant_price;

    public String getPlant_name() {
        return plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public String getPlant_description() {
        return plant_description;
    }

    public void setPlant_description(String plant_description) {
        this.plant_description = plant_description;
    }

    public String getPlant_image() {
        return plant_image;
    }

    public void setPlant_image(String plant_image) {
        this.plant_image = plant_image;
    }

    public String getPlant_price() {
        return plant_price;
    }

    public void setPlant_price(String plant_price) {
        this.plant_price = plant_price;
    }


    public PlantsModel(){

    }
    public PlantsModel(String plant_name, String plant_description, String plant_image, String plant_price) {
        this.plant_name = plant_name;
        this.plant_description = plant_description;
        this.plant_image = plant_image;
        this.plant_price = plant_price;
    }
}
