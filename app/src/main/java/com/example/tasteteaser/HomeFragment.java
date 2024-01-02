package com.example.tasteteaser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tasteteaser.adapter.CategoryAdapter;
import com.example.tasteteaser.adapter.RecipeAdapter;
import com.example.tasteteaser.databinding.ActivityHomeBinding;
import com.example.tasteteaser.models.Category;
import com.example.tasteteaser.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ActivityHomeBinding binding;
    List<Category> foodCategories;
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
        popularRecipes = new ArrayList<>() ;
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
        binding.rvFoodCategories.setAdapter(new CategoryAdapter());
        foodCategories =new ArrayList<>();
        foodCategories.add(new Category("1" , "Meat" ));
        CategoryAdapter categoryAdapter = (CategoryAdapter) binding.rvFoodCategories.getAdapter();
        if (categoryAdapter != null){
            categoryAdapter.setCategoryList(foodCategories);
            categoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null ;
    }
}