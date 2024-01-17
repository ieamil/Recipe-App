package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.tasteteaser.adapter.CategoryAdapter;
import com.example.tasteteaser.databinding.ActivityAllCategoriesBinding;
import com.example.tasteteaser.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesActivity extends AppCompatActivity {
    ActivityAllCategoriesBinding binding;
    DatabaseReference reference;
    ImageView backBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference = FirebaseDatabase.getInstance().getReference("Category");
        binding.rvAllCategories.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvAllCategories.setAdapter(new CategoryAdapter());

        binding.backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllCategoriesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadAllCategories();
    }

    private void loadAllCategories() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Category> categories = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category);
                }
                CategoryAdapter adapter = (CategoryAdapter) binding.rvAllCategories.getAdapter();
                if (adapter != null) {
                    adapter.setCategoryList(categories);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", error.getMessage());
            }
        });
    }
}