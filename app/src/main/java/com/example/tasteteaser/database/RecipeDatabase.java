package com.example.tasteteaser.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tasteteaser.dao.RecipeDao;
import com.example.tasteteaser.entities.Category;
import com.example.tasteteaser.entities.CategoryItems;
import com.example.tasteteaser.entities.Meal;
import com.example.tasteteaser.entities.MealsItems;
import com.example.tasteteaser.entities.Recipes;
import com.example.tasteteaser.entities.converter.CategoryListConverter;
import com.example.tasteteaser.entities.converter.MealListConverter;

@Database(entities = {Recipes.class, CategoryItems.class, Category.class, Meal.class, MealsItems.class}, version = 1, exportSchema = false)
@TypeConverters({CategoryListConverter.class, MealListConverter.class})
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase recipesDatabase;

    public static synchronized RecipeDatabase getDatabase(Context context) {
        if (recipesDatabase == null) {
            recipesDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RecipeDatabase.class,
                    "recipe.db"
            ).build();
        }
        return recipesDatabase;
    }

    public abstract RecipeDao recipeDao();
}