package com.example.tasteteaser.models;
//Model class representating a Category
public class Category {
    //Constructor
    private String id, name , image;

    //Getter and setter methods
    public Category(String id, String name , String image){
        this.id =id;
        this.name =name;
        this.image = image;
    }

    public Category(){

    }

    public Category(String id, String name){
        this.id =id;
        this.name =name;
        this.image = image; //önceden meatrecipe koymuştuk
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString(){
        return this.id + " : " + this.name;
    }
}