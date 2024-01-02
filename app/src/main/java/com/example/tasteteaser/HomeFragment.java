package com.example.tasteteaser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tasteteaser.adapter.RecipeAdapter;
import com.example.tasteteaser.databinding.ActivityHomeBinding;
import com.example.tasteteaser.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ActivityHomeBinding binding;
    List<Recipe> foodCategories;
    List<Recipe> popularRecipes;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFoodCategories();
        loadPopularRecipes();
    }

    private void loadPopularRecipes() {
        binding.rvPopulars.setAdapter(new RecipeAdapter()); //Category Adapter yapacağız burayı
        popularRecipes = new ArrayList<>();
        popularRecipes.add(new Recipe("1","Popular One","meatrecipe","null","Popular",
                "null","","","",""));
        popularRecipes.add(new Recipe("2","Popular 2","meatrecipe","null","Popular",
                "null","","","",""));
        popularRecipes.add(new Recipe("3","Popular 3","meatr","null","Popular",
                "null","","","",""));
        RecipeAdapter adapter =(RecipeAdapter) binding.rvPopulars.getAdapter();
        if (adapter != null){
            adapter.setRecipeList(popularRecipes);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadFoodCategories() {
        foodCategories =new ArrayList<>();
        foodCategories.add(new Recipe("1", "Favorite One","meatrecipe","null",
                "Favourite", "","", "","",""));
        foodCategories.add(new Recipe("2", "Favorite 2","meatr","null",
                "Favourite", "","", "","",""));
        foodCategories.add(new Recipe("3", "Favorite 3","meatrecipe","null",
                "Favourite", "","", "","",""));
        foodCategories.add(new Recipe("4", "Favorite 4","meatrecipe","null",
                "Favourite", "","", "","",""));
        binding.rvFoodCategories.setAdapter(new RecipeAdapter());
        RecipeAdapter adapter = (RecipeAdapter) binding.rvFoodCategories.getAdapter();
        if (adapter != null){
            adapter.setRecipeList(foodCategories);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}