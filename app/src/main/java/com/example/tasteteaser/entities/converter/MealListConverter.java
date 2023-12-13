package com.example.tasteteaser.entities.converter;

import androidx.room.TypeConverter;
import com.example.tasteteaser.entities.MealsItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MealListConverter {
    @TypeConverter
    public String fromCategoryList(List<MealsItems> category) {
        if (category == null) {
            return null;
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<MealsItems>>() {
            }.getType();
            return gson.toJson(category, type);
        }
    }

    @TypeConverter
    public List<MealsItems> toCategoryList(String categoryString) {
        if (categoryString == null) {
            return null;
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<MealsItems>>() {
            }.getType();
            return gson.fromJson(categoryString, type);
        }
    }
}
