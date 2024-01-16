package com.example.tasteteaser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tasteteaser.adapter.CategoryAdapter;
import com.example.tasteteaser.adapter.HorizontalRecipeAdapter;
import com.example.tasteteaser.databinding.ActivityHomeBinding;
import com.example.tasteteaser.models.Category;
import com.example.tasteteaser.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ActivityHomeBinding binding;
    List<Category> foodCategories = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFoodCategories();
        //loadRecipes();
    }

//private void loadPopularRecipes() {
//    binding.rvPopulars.setAdapter(new RecipeAdapter()); //Category Adapter yapacağız burayı
//    popularRecipes = new ArrayList<>();
//    popularRecipes.add(new Recipe("1","Popular One","meatrecipe","null","Popular",
//            "null","","",""));
//    popularRecipes.add(new Recipe("2","Popular 2","meatrecipe","null","Popular",
//            "null","","",""));
//    popularRecipes.add(new Recipe("3","Popular 3","meatr","null","Popular",
//            "null","","",""));
//    RecipeAdapter adapter =(RecipeAdapter) binding.rvPopulars.getAdapter();
//    if (adapter != null){
//        adapter.setRecipeList(popularRecipes);
//        adapter.notifyDataSetChanged();
//    }
//}

    private void loadRecipes() {
        // We will load recipes from our database
        binding.rvPopulars.setAdapter(new HorizontalRecipeAdapter());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                loadPopularRecipes(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", error.getMessage());
            }
        });
    }

    private void loadPopularRecipes(List<Recipe> recipes) {
        List<Recipe> popularRecipes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * recipes.size());
            popularRecipes.add(recipes.get(random));
        }
        HorizontalRecipeAdapter adapter = (HorizontalRecipeAdapter) binding.rvPopulars.getAdapter();
        if (adapter != null) {
            adapter.setRecipeList(popularRecipes);
        }
    }


    private void loadFoodCategories() {
        binding.rvFoodCategories.setAdapter(new CategoryAdapter());
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Categories are loading...");
        progressDialog.show();

        getCategories(new FunctionLoadedListener() {
            @Override
            public void onCategoriesLoaded(List<Category> loadedCategories) {
                progressDialog.dismiss();

                // Elde edilen yeni liste ile istediğiniz işlemleri yapabilirsiniz
                CategoryAdapter categoryAdapter = (CategoryAdapter) binding.rvFoodCategories.getAdapter();
                categoryAdapter.setCategoryList(loadedCategories);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRecipesLoaded(List<Recipe> recipes) {
                return;
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null ;
    }

    private void getCategories(FunctionLoadedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoriesData = database.getReference("Category");

        categoriesData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    foodCategories.add(category);
                    Log.d("foodcat eklendi : " , "eklenen : " + category.getName());
                }
                Log.d("foodcat" , "1 : " + foodCategories.get(1).getName());
                listener.onCategoriesLoaded(foodCategories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Get Categories Error " ,error.getMessage());
            }
        });
    }



    private List<Recipe> getRecipesOfCategory(String category , FunctionLoadedListener listener){
        List<Recipe> recipesOfCategory = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Category").child(category);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    recipesOfCategory.add(recipe);
                }
                listener.onRecipesLoaded(recipesOfCategory);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("get recipes of category error " , error.getMessage());
            }
        });
        return recipesOfCategory;
    }
}

interface FunctionLoadedListener{
    void onCategoriesLoaded(List<Category> categories);
    void onRecipesLoaded(List<Recipe> recipes);
}