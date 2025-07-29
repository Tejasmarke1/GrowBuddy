package com.example.growbuddy;

public class ToolsModel {
    private String tool_image;
    private String tool_price;
    private String tool_description;
    private String tool_name;

    public String getTool_image() {
        return tool_image;
    }

    public void setTool_image(String tool_image) {
        this.tool_image = tool_image;
    }

    public String getTool_price() {
        return tool_price;
    }

    public void setTool_price(String tool_price) {
        this.tool_price = tool_price;
    }

    public String getTool_description() {
        return tool_description;
    }

    public void setTool_description(String tool_description) {
        this.tool_description = tool_description;
    }

    public String getTool_name() {
        return tool_name;
    }

    public void setTool_name(String tool_name) {
        this.tool_name = tool_name;
    }


    public ToolsModel() {
    }

    public ToolsModel(String tool_image, String tool_price, String tool_description, String tool_name) {
        this.tool_image = tool_image;
        this.tool_price = tool_price;
        this.tool_description = tool_description;
        this.tool_name = tool_name;
    }



}
