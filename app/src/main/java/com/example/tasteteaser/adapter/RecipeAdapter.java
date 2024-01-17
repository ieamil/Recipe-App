package com.example.tasteteaser.adapter;

import static com.example.tasteteaser.databinding.ItemRecipeBinding.inflate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasteteaser.R;
import com.example.tasteteaser.RecipeDetailsActivity;
import com.example.tasteteaser.databinding.ItemRecipeBinding;
import com.example.tasteteaser.models.Recipe;

import java.util.ArrayList;
import java.util.List;

// Adapter for displaying recipes in a RecyclerView
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    List<Recipe> recipeList = new ArrayList<>();

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public void setRecipes(List<Recipe> recipeList){
        this.recipeList = recipeList;
    }

    // Inflates the layout for individual recipe items
    @NonNull
    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeHolder(inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    // Binds data to the views in each recipe item
    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.onBind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeHolder extends RecyclerView.ViewHolder {
        ItemRecipeBinding binding;
        public RecipeHolder(@NonNull ItemRecipeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(Recipe recipe) {
            Glide
                    .with(binding.getRoot().getContext())
                    .load(recipe.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.meatr)
                    .into(binding.imgDish);
            binding.tvDishName.setText(recipe.getName());

            binding.getRoot().setOnClickListener(view -> {
                Intent intent = new Intent(binding.getRoot().getContext(), RecipeDetailsActivity.class);
                intent.putExtra("recipe", recipe);
                binding.getRoot().getContext().startActivity(intent);
            });
        }
    }
}