package com.example.tasteteaser.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.tasteteaser.entities.converter.MealListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "Meal")
public class Meal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "meals")
    @Expose
    @SerializedName("meals")
    @TypeConverters(MealListConverter.class)
    private List<MealsItems> mealsItem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MealsItems> getMealsItem() {
        return mealsItem;
    }

    public void setMealsItem(List<MealsItems> mealsItem) {
        this.mealsItem = mealsItem;
    }
}