package com.example.deliveryguy;

public class CategoryModel {
    private String Name;
    private String Image;

    public CategoryModel(){

    }

    public CategoryModel(String Name, String Image) {
        this.Name = Name;
        this.Image = Image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
}
