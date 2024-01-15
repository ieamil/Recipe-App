package com.example.tasteteaser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.tasteteaser.databinding.AddRecipeFragmentBinding;

public class RecipeFragment extends Fragment {
    private AddRecipeFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = AddRecipeFragmentBinding.inflate(inflater , container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getAddRecipeFragment() {
        
    }
}
