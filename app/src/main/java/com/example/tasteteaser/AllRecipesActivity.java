package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.tasteteaser.adapter.RecipeAdapter;
import com.example.tasteteaser.databinding.ActivityAllRecipesBinding;
import com.example.tasteteaser.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllRecipesActivity extends AppCompatActivity {
    ActivityAllRecipesBinding binding;
    DatabaseReference reference;
    String type;
    ImageView backBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reference = FirebaseDatabase.getInstance().getReference("Recipes");
        binding.rvRecipes.setLayoutManager(new GridLayoutManager(this,2));
        binding.rvRecipes.setAdapter(new RecipeAdapter());
        type = getIntent().getStringExtra("type");
        backBttn = findViewById(R.id.backBttn);
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllRecipesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (type.equalsIgnoreCase("category")) {
            filterByCategory();
        } else if (type.equalsIgnoreCase("search")) {
            loadByRecipes();
        } else {
            loadAllRecipes();
        }
    }

    private void loadByRecipes() {
        // Search Recipes by Name
        String query = getIntent().getStringExtra("query");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    if (recipe.getName().toLowerCase().contains(query.toLowerCase()))
                        recipes.add(recipe);
                }
                RecipeAdapter adapter = (RecipeAdapter) binding.rvRecipes.getAdapter();
                if (adapter != null) {
                    adapter.setRecipeList(recipes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", error.getMessage());
            }
        });
    }

    private void loadAllRecipes() {
        // Load All Recipes
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                Collections.shuffle(recipes);
                RecipeAdapter adapter = (RecipeAdapter) binding.rvRecipes.getAdapter();
                if (adapter != null) {
                    adapter.setRecipeList(recipes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", error.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Sayfadan geriye çıkma işlemini buraya ekleyebilirsiniz.
        finish(); // Bu,mevcut aktiviteyi kapatır ve bir önceki aktiviteye geri döner.
    }



    private void filterByCategory() {
        String category = getIntent().getStringExtra("category");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipes");
        reference.orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                RecipeAdapter adapter = (RecipeAdapter) binding.rvRecipes.getAdapter();
                if (adapter != null) {
                    adapter.setRecipeList(recipes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", error.getMessage());
            }
        });
    }

}