package com.example.tasteteaser.adapter;

import static com.example.tasteteaser.databinding.ItemCategoryBinding.inflate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasteteaser.AllRecipesActivity;
import com.example.tasteteaser.R;
import com.example.tasteteaser.databinding.ItemCategoryBinding;
import com.example.tasteteaser.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<Category> categoryList = new ArrayList<>();

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.onBind(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {

        ItemCategoryBinding binding;
        public CategoryHolder(@NonNull ItemCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

/*
  Binds the data of a Category object to the views in the corresponding item layout.
  Sets the image resource based on the category's image name and updates the text with the category name.
 */
        public void onBind(Category category){
            binding.categoryRecipeName.setText(category.getName());
            Glide.with(binding.getRoot().getContext())
                    .load(category.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.meatrecipe)
                    .into(binding.categoryRecipeImg);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(binding.getRoot().getContext(), AllRecipesActivity.class);
                    intent.putExtra("type", "category");
                    intent.putExtra("category", category.getName());
                    binding.getRoot().getContext().startActivity(intent);
                }
            });
        }
    }
}
