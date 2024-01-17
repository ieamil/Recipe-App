package com.example.tasteteaser;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tasteteaser.databinding.ActivityRecipeDetailsBinding;
import com.example.tasteteaser.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.example.tasteteaser.room.RecipeRepository;


public class RecipeDetailsActivity extends AppCompatActivity {
    ActivityRecipeDetailsBinding binding;
    ImageView backBtn;
    String recipeKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressed();
            }
        });
    }

    public void getOnBackPressed() {
        super.onBackPressed();
        // Geri tuşuna basıldığında bir önceki sayfaya geçiş yapacak.
        finish();
    }

    private void init() {
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        binding.tvName.setText(recipe.getName());
        binding.tcTime.setText(recipe.getTime());
        binding.tvDescription.setText(recipe.getInstructions());
        Log.d("DENEME" , "DENEME : " + recipe.getName());
        Log.d("DENEME" , "DENEME : " + recipe.getCalories());
        Log.d("DENEME" , "DENEME : " + recipe.getTime());
        Log.d("DENEME" , "DENEME : " + recipe.getInstructions());
        binding.tvCalories.setText(String.format("%s Calories", recipe.getCalories()));
        Glide
                .with(RecipeDetailsActivity.this)
                .load(recipe.getImage())
                .centerCrop()
                .placeholder(R.drawable.meatrecipe)
                .into(binding.imgRecipe);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Recipe is loading...");
        if(!isFinishing()){
            progressDialog.show();
        }
        findKeyOfRecipe(recipe.getName(), new keyFinderListener() {
            @Override
            public void keyFound(String key) {
                updateDataWithFireBase(recipeKey);
                progressDialog.dismiss();
            }
        });
    }

    private void updateDataWithFireBase(String recipeId) {
        Log.d("DENEME2 :" , "ID : " + recipeId);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipes").child(recipeId);
        Log.d("Recipe ID :" , "ID : " + recipeId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                binding.tvName.setText(recipe.getName());
                binding.tcTime.setText(recipe.getCategory());
                //binding.tvDescription.setText(recipe.getDescription());
                Log.d("DENEME2" , "UPDATEDATA KEY :" + recipeKey);
                Log.d("DENEME2" , "DENEME2 : " + recipe.getName());
                Log.d("DENEME2" , "DENEME2 : " + recipe.getCalories());
                Log.d("DENEME2" , "DENEME2 : " + recipe.getTime());
                Log.d("DENEME2" , "DENEME2 : " + recipe.getInstructions());
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

    public void findKeyOfRecipe(String recipeName , keyFinderListener listener){
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Recipes");
    final String[] key = {""};  //its final and Array style String because we can't acces this from onDataChange method.
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                if(recipe.getName().equals(recipeName)){
                    key[0] = dataSnapshot.getKey();
                    recipeKey = key[0];
                    listener.keyFound(key[0]);
                    Log.d("DENEME2" , "DENEME2 key :" + key[0]);
                    Log.d("DENEME2" , "DENEME2 :" + recipe.getName());
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("find key of recipe error " , error.getMessage());
        }
    });
        Log.d("DENEME" , "RECIPE KEY : " + recipeKey);
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

 interface keyFinderListener{
        void keyFound(String key);
    }
}