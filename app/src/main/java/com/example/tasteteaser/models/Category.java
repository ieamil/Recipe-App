package com.example.tasteteaser.models;
//Model class representating a Category
public class Category {
    //Constructor
    private String id, name;
    public Category() {

    }

    //Getter and setter methods
    public Category(String id, String name){
        this.id =id;
        this.name =name;
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
}