package com.example.tasteteaser;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tasteteaser.databinding.ActivityRecipeDetailsBinding;
import com.example.tasteteaser.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.example.tasteteaser.room.RecipeRepository;


public class RecipeDetailsActivity extends AppCompatActivity {
    ActivityRecipeDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        binding.tvName.setText(recipe.getName());
        binding.tcCategory.setText(recipe.getCategory());
        binding.tvDescription.setText(recipe.getDescription());
        binding.tvCalories.setText(String.format("%s Calories", recipe.getCalories()));
        Glide
                .with(RecipeDetailsActivity.this)
                .load(recipe.getImage())
                .centerCrop()
                .placeholder(R.drawable.meatrecipe)
                .into(binding.imgRecipe);

        if (recipe.getId().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())) {
            binding.imgEdit.setVisibility(View.VISIBLE);
            binding.btnDelete.setVisibility(View.VISIBLE);
        } else {
            binding.imgEdit.setVisibility(View.GONE);
            binding.btnDelete.setVisibility(View.GONE);
        }

        binding.imgEdit.setOnClickListener(view -> {
            Intent intent = new Intent(binding.getRoot().getContext(), RecipeActivity.class);
            intent.putExtra("recipe", recipe);
            intent.putExtra("isEdit", true);
            binding.getRoot().getContext().startActivity(intent);
        });
        //checkFavorite(recipe);
        //binding.imgFvrt.setOnClickListener(view -> favouriteRecipe(recipe));

        binding.btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Recipe")
                    .setMessage("Are you sure you want to delete this recipe?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        ProgressDialog dialog = new ProgressDialog(this);
                        dialog.setMessage("Deleting...");
                        dialog.setCancelable(false);
                        dialog.show();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipes");
                        reference.child(recipe.getId()).removeValue().addOnCompleteListener(task -> {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Recipe Deleted Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "Failed to delete recipe", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                    .show();
        });

        updateDataWithFireBase(recipe.getId());
    }

//private void checkFavorite(Recipe recipe) {
//    RecipeRepository repository = new RecipeRepository(getApplication());
//    boolean isFavourite = repository.isFavourite(recipe.getId());
//    if (isFavourite) {
//        binding.imgFvrt.setColorFilter(getResources().getColor(R.color.accent));
//    } else {
//        binding.imgFvrt.setColorFilter(getResources().getColor(R.color.black));
//    }
//}

    //private void favouriteRecipe(Recipe recipe) {
    //    RecipeRepository repository = new RecipeRepository(getApplication());
    //    boolean isFavourite = repository.isFavourite(recipe.getId());
    //    if (isFavourite) {
    //        repository.delete(new FavouriteRecipe(recipe.getId()));
    //        binding.imgFvrt.setColorFilter(getResources().getColor(R.color.black));
    //    } else {
    //        repository.insert(new FavouriteRecipe(recipe.getId()));
    //        binding.imgFvrt.setColorFilter(getResources().getColor(R.color.accent));
    //    }
    //}

    private void updateDataWithFireBase(String recipeId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipes").child("recipeId");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                binding.tvName.setText(recipe.getName());
                binding.tcCategory.setText(recipe.getCategory());
                binding.tvDescription.setText(recipe.getDescription());
                binding.tvCalories.setText(String.format("%s Calories", recipe.getCalories()));
                Glide
                        .with(RecipeDetailsActivity.this)
                        .load(recipe.getImage())
                        .centerCrop()
                        .placeholder(R.drawable.meatrecipe)
                        .into(binding.imgRecipe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: ", error.toException());
            }
        });
    }
}