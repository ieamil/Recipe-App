package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasteteaser.database.RecipeDatabase;
import com.example.tasteteaser.entities.CategoryItems;
import com.example.tasteteaser.entities.MealsItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private ArrayList<CategoryItems> arrMainCategory = new ArrayList<>();
    private ArrayList<MealsItems> arrSubCategory = new ArrayList<>();
    private MainCategoryAdapter mainCategoryAdapter = new MainCategoryAdapter();
    private SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rvMainCategory = findViewById(R.id.rv_main_category);
        RecyclerView rvSubCategory = findViewById(R.id.rv_sub_category);

        TextView tvCategory = findViewById(R.id.tvCategory);

        getDataFromDb(rvMainCategory, tvCategory);

        mainCategoryAdapter.setClickListener(onCLicked);
        subCategoryAdapter.setClickListener(onCLickedSubItem);
    }

    private final MainCategoryAdapter.OnItemClickListener onCLicked = new MainCategoryAdapter.OnItemClickListener() {
        @Override
        public void onClicked(String categoryName) {
            getMealDataFromDb(categoryName, rvSubCategory);
        }
    };

    private final SubCategoryAdapter.OnItemClickListener onCLickedSubItem = new SubCategoryAdapter.OnItemClickListener() {
        @Override
        public void onClicked(String id) {
            Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    };


    private void getDataFromDb(RecyclerView rvMainCategory, TextView tvCategory) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<CategoryItems> cat = RecipeDatabase.getDatabase(HomeActivity.this).recipeDao().getAllCategory();
                        arrMainCategory = new ArrayList<>(cat);
                        Collections.reverse(arrMainCategory);

                        getMealDataFromDb(arrMainCategory.get(0).getStrcategory(), rvSubCategory);
                        mainCategoryAdapter.setData(arrMainCategory);
                        rvMainCategory.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        rvMainCategory.setAdapter(mainCategoryAdapter);
                    }
                });
            }
        }).start();
    }

    private void getMealDataFromDb(String categoryName, RecyclerView rvSubCategory) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<MealsItems> cat = RecipeDatabase.getDatabase(HomeActivity.this).recipeDao().getSpecificMealList(categoryName);
                arrSubCategory = new ArrayList<>(cat);
                subCategoryAdapter.setData(arrSubCategory);
                rvSubCategory.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
                rvSubCategory.setAdapter(subCategoryAdapter);
            }
        });
    }
}
