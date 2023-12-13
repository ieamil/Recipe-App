package com.example.tasteteaser.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tasteteaser.entities.CategoryItems;
import com.example.tasteteaser.entities.MealsItems;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM categoryitems ORDER BY id DESC")
    List<CategoryItems> getAllCategory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(CategoryItems categoryItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealsItems mealsItems);

    @Query("DELETE FROM categoryitems")
    void clearDb();

    @Query("SELECT * FROM MealItems WHERE categoryName = :categoryName ORDER BY id DESC")
    List<MealsItems> getSpecificMealList(String categoryName);
}
