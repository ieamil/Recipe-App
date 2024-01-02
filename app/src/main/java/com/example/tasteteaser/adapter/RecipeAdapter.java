package com.example.tasteteaser.adapter;

import static com.example.tasteteaser.databinding.ItemRecipeBinding.inflate;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasteteaser.R;
import com.example.tasteteaser.databinding.ItemRecipeBinding;
import com.example.tasteteaser.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    //category için ayrı mı oluşturmam lazım emin olamadım bu kısımlarda
    //category adapter oluşturup home fragmentte food categoriesi ona göre eklemem gerekiyor ama aynı zamanda değişmez olmalı,

    List<Recipe> recipeList;

    public void setRecipeList(List<Recipe> recipeList){
        this.recipeList =recipeList;
    }
    @NonNull
    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeHolder(inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.onBind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        ItemRecipeBinding binding;
        public RecipeHolder(@NonNull ItemRecipeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        public void onBind(Recipe recipe){
            binding.bgImgRecipe.setImageResource(recipe.getImage().equalsIgnoreCase("meatrecipe") ?
                    R.drawable.meatrecipe : R.drawable.meatr);
            binding.tvRecipeName.setText(recipe.getName());
        }
    }
}
