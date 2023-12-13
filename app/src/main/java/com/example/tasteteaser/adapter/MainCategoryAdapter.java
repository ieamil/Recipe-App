package com.example.tasteteaser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasteteaser.R;
import com.example.tasteteaser.entities.CategoryItems;

import java.util.ArrayList;
import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder> {

    private OnItemClickListener listener;
    private Context ctx;
    private ArrayList<CategoryItems> arrMainCategory = new ArrayList<>();

    public interface OnItemClickListener {
        void onClicked(String categoryName);
    }

    public void setData(List<CategoryItems> arrData) {
        arrMainCategory = new ArrayList<>(arrData);
    }

    public void setClickListener(OnItemClickListener listener1) {
        listener = listener1;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main_category, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.itemView.tv_dish_name.setText(arrMainCategory.get(position).getStrcategory());
        Glide.with(ctx).load(arrMainCategory.get(position).getStrcategorythumb()).into(holder.itemView.img_dish);
        holder.itemView.rootView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClicked(arrMainCategory.get(position).getStrcategory());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrMainCategory.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        public RecipeViewHolder(View view) {
            super(view);
        }
    }
}
