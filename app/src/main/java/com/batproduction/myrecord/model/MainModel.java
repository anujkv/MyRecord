package com.batproduction.myrecord.model;

public class MainModel {
    int image;
    String optionName;

    public MainModel(int image, String optionName) {
        this.image = image;
        this.optionName = optionName;
    }

    public MainModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
