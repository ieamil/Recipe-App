package com.example.tasteteaser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tasteteaser.models.Recipe;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class RecipeActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    ProgressDialog dialog;
    boolean isEdit;
    String recipeId;


    Button addRecipeBtn , addPhotoBtn;
    ImageView backBtn;
    TextInputLayout recipeName , recipeCategory , recipeIngredients , recipeInstruction , calories , time;
    private boolean imageSelected = false;

    //SERDAR YILDIZ , IŞIL ÇOLAK
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe_main);

        addRecipeBtn = findViewById(R.id.add_recipe_btn);
        backBtn = findViewById(R.id.backBtn);
        addPhotoBtn = findViewById(R.id.add_recipe_image_btn);
        recipeName = findViewById(R.id.recipe_name);
        recipeIngredients = findViewById(R.id.recipe_ingredients);
        recipeInstruction = findViewById(R.id.recipe_instructions);
        recipeCategory = findViewById(R.id.recipe_categories);
        calories = findViewById(R.id.calories);
        time = findViewById(R.id.time);


        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeNameStr = recipeName.getEditText().getText().toString();
                String recipeIngredientsStr = recipeIngredients.getEditText().getText().toString();
                String recipeInstructionStr = recipeInstruction.getEditText().getText().toString();
                String category = recipeCategory.getEditText().getText().toString();
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

        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Add Image "  , "calisti");

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this , HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //SERDAR YILDIZ
    private void addRecipe() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // UI işlemlerini güncellemek için Handler kullan
                Handler handler = new Handler(Looper.getMainLooper());

                // Arka plan işlemlerini burada yap
                String userId = FirebaseAuth.getInstance().getUid();
                String recipeNameStr = recipeName.getEditText().getText().toString();
                String recipeIngredientsStr = recipeIngredients.getEditText().getText().toString();
                String recipeInstructionStr = recipeInstruction.getEditText().getText().toString();
                String caloriesStr = calories.getEditText().getText().toString();
                String timeStr = time.getEditText().getText().toString();
                String category = recipeCategory.getEditText().getText().toString();
                String image;
                if (imageSelected) {
                    image = null;
                } else {
                    int pictureId = R.drawable.meatr;
                    Drawable drawable = getResources().getDrawable(pictureId);
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] byteArray = baos.toByteArray();
                    String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    image = base64Image;
                }
                Recipe newRecipe = new Recipe(
                        userId,
                        recipeNameStr,
                        image,
                        "description",
                        category,
                        recipeInstructionStr,
                        recipeIngredientsStr,
                        caloriesStr,
                        timeStr
                );
                // Firebase'e ekleme işlemi
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Recipes").push();
                reference.setValue(newRecipe);
                addRecipeToCategory(newRecipe.getCategory() , newRecipe);
                // UI'yi güncelle (örneğin loading modal'ı kapat)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // UI işlemleri burada yapılır
                        // Örneğin loading modal'ı kapat
                        Toast.makeText(RecipeActivity.this, "Recipe Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // Thread'i başlat
        thread.start();
    }

    //SERDAR YILDIZ
    private void addRecipeToCategory(String category , Recipe recipe){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Category").child(category);
        DatabaseReference newChildRef = reference.push();
        newChildRef.child(category).setValue(recipe);
    }

    //SERDAR YILDIZ
    private void uploadImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesReference = storageRef.child("recipePhotos");
    }

    //SERDAR YILDIZ
    private void saveDataInDataBase(Recipe recipe, String url) {
        recipe.setImage(url);
        // 6. We will save the recipe object in the firebase database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Recipes");
        if (isEdit) {
            recipe.setId(recipeId);
            reference.child(recipe.getId()).setValue(recipe).addOnCompleteListener(task -> {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(RecipeActivity.this, "Recipe Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RecipeActivity.this, "Error in updating recipe", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
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
}
