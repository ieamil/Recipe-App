package com.example.tasteteaser;

import static java.lang.System.currentTimeMillis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tasteteaser.databinding.ActivityHomeBinding;
import com.example.tasteteaser.models.Recipe;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class RecipeActivity extends AppCompatActivity {
    ActivityHomeBinding
    FirebaseStorage storage = FirebaseStorage.getInstance();
    ProgressDialog dialog;

    RecipeActivity addRecipe;
    Button addRecipeBtn;
    TextInputLayout recipeName , recipeIngredients , recipeInstruction , calories , time;
    private boolean imageSelected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe_main);

        addRecipeBtn = findViewById(R.id.add_recipe_btn);
        recipeName = findViewById(R.id.recipe_name);
        recipeIngredients = findViewById(R.id.recipe_ingredients);
        recipeInstruction = findViewById(R.id.recipe_instructions);
        calories = findViewById(R.id.calories);
        time = findViewById(R.id.time);


        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeNameStr = recipeName.getEditText().getText().toString();
                String recipeIngredientsStr = recipeIngredients.getEditText().getText().toString();
                String recipeInstructionStr = recipeInstruction.getEditText().getText().toString();
                String caloriesStr = calories.getEditText().getText().toString();
                String timeStr = time.getEditText().getText().toString();

                if(!TextUtils.isEmpty(recipeNameStr)){
                    if(!TextUtils.isEmpty(recipeIngredientsStr)){
                        if(!TextUtils.isEmpty(recipeInstructionStr)){
                            if(!TextUtils.isEmpty(caloriesStr) && !TextUtils.isEmpty(timeStr)){
                                addRecipe();
                            }else{
                                Toast.makeText(RecipeActivity.this , "Calories & Time can not be empty" , Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RecipeActivity.this , "Recipe Instruction is empty" , Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RecipeActivity.this , "Recipe Ingredients is empty" , Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RecipeActivity.this , "Recipe Name is empty" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void addRecipe(){
        Intent intent = new Intent(RecipeActivity.this , ProfileFragment.class);
    }

    private void uploadImage(Recipe recipe) {
        new Thread(() -> {
            final String[] url = {""};
            // We will upload the image to the firebase storage.
            binding.imgRecipe.setDrawingCacheEnabled(true);
            Bitmap bitmap = ((BitmapDrawable) binding.imgRecipe.getDrawable()).getBitmap();
            binding.imgRecipe.setDrawingCacheEnabled(false);
            String id = recipe.getId();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("recipePhotos/" + id + "_recipe.jpg");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = storageRef.putBytes(data);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return storageRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    // We need to save this download url in firebase database
                    // So that we can load it in our app
                    url[0] = downloadUri.toString();
                    runOnUiThread(() -> {
                        Toast.makeText(RecipeActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        saveDataInDataBase(recipe, url[0]);
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(RecipeActivity.this, "Error in uploading image", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Log.e("ProfileFragment", "onComplete: " + Objects.requireNonNull(task.getException()).getMessage());
                    });
                }
            });
        }).start();
    }

    private void saveDataInDataBase(Recipe recipe, String url) {
        recipe.setImage(url);
        // 6. We will save the recipe object in the firebase database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Recipes");
        String id = reference.push().getKey();
        recipe.setId(id);
        if (id != null) {
            reference.child(id).setValue(recipe).addOnCompleteListener(task -> {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(RecipeActivity.this, "Recipe Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RecipeActivity.this, "Error in adding recipe", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
