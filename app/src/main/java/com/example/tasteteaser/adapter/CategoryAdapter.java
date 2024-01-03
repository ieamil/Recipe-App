package com.example.tasteteaser.adapter;

import static com.example.tasteteaser.databinding.ItemCategoryBinding.bind;
import static com.example.tasteteaser.databinding.ItemCategoryBinding.inflate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasteteaser.R;
import com.example.tasteteaser.databinding.ItemCategoryBinding;
import com.example.tasteteaser.databinding.ItemCategoryBinding;
import com.example.tasteteaser.models.Category;
import com.example.tasteteaser.models.Recipe;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    List<Category> categoryList;

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
          //  binding.categoryRecipeImage.setImageResource(category.getImage().equalsIgnoreCase("meatr") ? R.drawable.meatrecipe : R.drawable.meatr);
           // binding.categoryRecipeName.setText(category.getName());
            binding.categoryRecipeImg.setImageResource(category.getImage().equalsIgnoreCase("meatrecipe") ? R.drawable.meatr : R.drawable.meatrecipe);
            binding.categoryRecipeName.setText(category.getName());
        }
    }
}
