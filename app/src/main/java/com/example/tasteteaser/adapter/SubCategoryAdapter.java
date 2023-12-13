package com.example.tasteteaser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasteteaser.R;
import com.example.tasteteaser.entities.MealsItems;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder> {

    private SubCategoryAdapter.OnItemClickListener listener;
    private Context ctx;
    private ArrayList<MealsItems> arrSubCategory = new ArrayList<>();

    public interface OnItemClickListener {
        void onClicked(String id);
    }

    public void setData(List<MealsItems> arrData) {
        arrSubCategory = new ArrayList<>(arrData);
    }

    public void setClickListener(SubCategoryAdapter.OnItemClickListener listener1) {
        listener = listener1;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_sub_category, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.itemView.tv_dish_name.setText(arrSubCategory.get(position).getStrMeal());
        Glide.with(ctx).load(arrSubCategory.get(position).getStrMealThumb()).into(holder.itemView.img_dish);
        holder.itemView.rootView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClicked(arrSubCategory.get(position).getIdMeal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrSubCategory.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        public RecipeViewHolder(View view) {
            super(view);
        }
    }
}
