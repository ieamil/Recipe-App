package com.example.tasteteaser.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.tasteteaser.entities.converter.CategoryListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "Category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "categoryItems")
    @Expose
    @SerializedName("categories")
    @TypeConverters(CategoryListConverter.class)
    private List<CategoryItems> categorieItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CategoryItems> getCategorieItems() {
        return categorieItems;
    }

    public void setCategorieItems(List<CategoryItems> categorieItems) {
        this.categorieItems = categorieItems;
    }
}