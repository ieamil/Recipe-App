package com.example.tasteteaser;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RecipeActivity extends AppCompatActivity {

    RecipeActivity addRecipe;
    Button addRecipeBtn;
    TextInputLayout recipeName , recipeIngredients , recipeInstruction;
    TextInputEditText recipeNameInput , recipeIngredientsInput , recipeInstructionInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe_main);

        addRecipeBtn = findViewById(R.id.add_recipe_btn);
        recipeName = findViewById(R.id.recipe_name);
        recipeIngredients = findViewById(R.id.recipe_ingredients);
        recipeInstruction = findViewById(R.id.recipe_instructions);

        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeNameStr = recipeName.getEditText().getText().toString();
                String recipeIngredientsStr = recipeIngredients.getEditText().getText().toString();
                String recipeInstructionStr = recipeInstruction.getEditText().getText().toString();

                if(!TextUtils.isEmpty(recipeNameStr)){
                    if(!TextUtils.isEmpty(recipeIngredientsStr)){
                        if(!TextUtils.isEmpty(recipeInstructionStr)){
                            addRecipe();
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

    }
}
