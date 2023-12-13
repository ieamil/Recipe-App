package com.example.tasteteaser.entities.converter;

import androidx.room.TypeConverter;
import com.example.tasteteaser.entities.CategoryItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CategoryListConverter {

    @TypeConverter
    public String fromCategoryList(List<CategoryItems> category) {
        if (category == null) {
            return null;
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CategoryItems>>() {
            }.getType();
            return gson.toJson(category, type);
        }
    }

    @TypeConverter
    public List<CategoryItems> toCategoryList(String categoryString) {
        if (categoryString == null) {
            return null;
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CategoryItems>>() {
            }.getType();
            return gson.fromJson(categoryString, type);
        }
    }
}