package com.example.tasteteaser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.tasteteaser.adapter.RecipeAdapter;
import com.example.tasteteaser.databinding.FragmentProfileBinding;
import com.example.tasteteaser.models.Recipe;
import com.example.tasteteaser.models.User;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public  void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        loadProfile();
        loadUserRecipes();
    }

    private void loadUserRecipes() {
        binding.rvProfile.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.rvProfile.setAdapter(new RecipeAdapter());
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("1","One","meatrecipe","null","Popular",
                "null","","","",""));
        recipes.add(new Recipe("2","2","meatrecipe","null","Popular",
                "null","","","",""));
        recipes.add(new Recipe("3","3","meatr","null","Popular",
                "null","","","",""));
        RecipeAdapter adapter =(RecipeAdapter) binding.rvProfile.getAdapter();
        if (adapter != null){
            adapter.setRecipeList(recipes);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadProfile() {
        User user = new User();
        user.setName("Denemee");
        user.setEmail("serdaryildiz2002@gmail.com");
        binding.tvUserName.setText(user.getName());
        binding.tvUserEmail.setText(user.getEmail());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
