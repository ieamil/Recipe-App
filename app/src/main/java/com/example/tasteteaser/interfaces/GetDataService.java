package com.example.tasteteaser.interfaces;

import com.example.tasteteaser.entities.Category;
import com.example.tasteteaser.entities.Meal;
import com.example.tasteteaser.entities.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("categories.php")
    Call<Category> getCategoryList();

    @GET("filter.php")
    Call<Meal> getMealList(@Query("c") String category);

    @GET("lookup.php")
    Call<MealResponse> getSpecificItem(@Query("i") String id);
}
