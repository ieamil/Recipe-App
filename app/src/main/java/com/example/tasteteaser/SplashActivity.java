package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tasteteaser.database.RecipeDatabase;
import com.example.tasteteaser.entities.Category;
import com.example.tasteteaser.entities.CategoryItems;
import com.example.tasteteaser.entities.Meal;
import com.example.tasteteaser.entities.MealsItems;
import com.example.tasteteaser.interfaces.GetDataService;
import com.example.tasteteaser.retrofitclient.RetrofitClientInstance;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity implements EasyPermissions.RationaleCallbacks,
        EasyPermissions.PermissionCallbacks {

    private static final int READ_STORAGE_PERM = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        readStorageTask();

        findViewById(R.id.btnGetStarted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getCategories() {
        GetDataService service = RetrofitClientInstance.Companion.getRetrofitInstance().create(GetDataService.class);
        Call<Category> call = service.getCategoryList();
        call.enqueue(new Callback<Category>() {
            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                List<CategoryItems> categories = response.body().getCategorieitems();
                for (CategoryItems arr : categories) {
                    getMeal(arr.getStrcategory());
                }
                insertDataIntoRoomDb(response.body());
            }
        });
    }

    private void getMeal(String categoryName) {
        GetDataService service = RetrofitClientInstance.Companion.getRetrofitInstance().create(GetDataService.class);
        Call<Meal> call = service.getMealList(categoryName);
        call.enqueue(new Callback<Meal>() {
            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                findViewById(R.id.loader).setVisibility(View.INVISIBLE);
                Toast.makeText(SplashActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                insertMealDataIntoRoomDb(categoryName, response.body());
            }
        });
    }

    private void insertDataIntoRoomDb(Category category) {
        if (category != null) {
            for (CategoryItems arr : category.getCategorieitems()) {
                RecipeDatabase.Companion.getDatabase(SplashActivity.this).recipeDao().insertCategory(arr);
            }
        }
    }

    private void insertMealDataIntoRoomDb(String categoryName, Meal meal) {
        if (meal != null) {
            for (MealsItems arr : meal.getMealsItem()) {
                MealsItems mealItemModel = new MealsItems(
                        arr.getId(),
                        arr.getIdMeal(),
                        categoryName,
                        arr.getStrMeal(),
                        arr.getStrMealThumb()
                );
                RecipeDatabase.Companion.getDatabase(SplashActivity.this).recipeDao().insertMeal(mealItemModel);
                Log.d("mealData", arr.toString());
            }
        }
        findViewById(R.id.btnGetStarted).setVisibility(View.VISIBLE);
    }

    private void clearDataBase() {
        RecipeDatabase.Companion.getDatabase(SplashActivity.this).recipeDao().clearDb();
    }

    private boolean hasReadStoragePermission() {
        return EasyPermissions.hasPermissions(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        );
    }

    private void readStorageTask() {
        if (hasReadStoragePermission()) {
            clearDataBase();
            getCategories();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs access to your storage,",
                    READ_STORAGE_PERM,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }
}