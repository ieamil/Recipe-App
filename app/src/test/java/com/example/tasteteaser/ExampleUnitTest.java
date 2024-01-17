package com.example.tasteteaser;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.tasteteaser.adapter.CategoryAdapter;
import com.example.tasteteaser.adapter.RecipeAdapter;
import com.example.tasteteaser.models.Category;
import com.example.tasteteaser.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testCategoryLength(){
        CategoryAdapter categoryAdapter = new CategoryAdapter();
        List<Category> categoryList = new ArrayList<>();
        Category category = new Category();
        Category category1 = new Category();
        Category category2 = new Category();
        categoryList.add(category);
        categoryList.add(category1);
        categoryList.add(category2);
        categoryAdapter.setCategoryList(categoryList);
        assertEquals(3 , categoryAdapter.getItemCount());
    }

    @Test
    public void testRecipeLength() {
        RecipeAdapter recipeAdapter = new RecipeAdapter();
        List<Recipe> recipeList = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            Recipe recipe = new Recipe();
            recipeList.add(recipe);
        }
        recipeAdapter.setRecipes(recipeList);
        assertEquals(10 , recipeAdapter.getItemCount());
    }

}