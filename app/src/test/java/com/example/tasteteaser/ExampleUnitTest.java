package com.example.tasteteaser;

import org.junit.Test;

import static org.junit.Assert.*;

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



  /*  public void findKeyRecipeActivityTest(){
        RecipeDetailsActivity recipeDetailsActivity = new RecipeDetailsActivity();
        String key = recipeDetailsActivity.findKeyOfRecipe("Tropical Fruit Smoothie", new RecipeDetailsActivity.keyFinderListener() {
            @Override
            public void keyFound(String key) {
                System.out.println("Test working");
            }
        });
        System.out.println(key);
        assertEquals("NoIoYdHvQuWdXzmSyKAA" , key);
    }*/


    public void testLoadPopularRecipes() {
        HomeFragment homeFragment = new HomeFragment();

        // create a dummy list of recipes for testing
        List<Recipe> dummyRecipes = new ArrayList<>();
        dummyRecipes.add(new Recipe());
        dummyRecipes.add(new Recipe());
        dummyRecipes.add(new Recipe());
        // call the private method
        //List<Recipe> result = homeFragment.loadPopularRecipes(dummyRecipes);

        // check if the result size is less than or equal to 5
        // because the method selects up to 5 random recipes
        //assertTrue(result.size() <= 5);
    }

    @Test
    public void testGetRecipesOfCategory() {
        HomeFragment homeFragment = new HomeFragment();

        // create a dummy listener for testing
        FunctionLoadedListener dummyListener = new FunctionLoadedListener() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                // do nothing
            }

            @Override
            public void onRecipesLoaded(List<Recipe> recipes) {
                // check if the recipes list is not null
                assertNotNull(recipes);
            }
        };

        // call the method
        List<Recipe> result = homeFragment.getRecipesOfCategory("DummyCategory", dummyListener);

        // check if the result is an empty list
        assertTrue(result.isEmpty());
    }

}