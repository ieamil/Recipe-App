package com.example.tasteteaser;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.tasteteaser.adapter.RecipeAdapter;
import com.example.tasteteaser.databinding.FragmentProfileBinding;
import com.example.tasteteaser.models.Recipe;
import com.example.tasteteaser.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Login Required")
                    .setMessage("You need to login to view your profile")
                    .show();
        } else {
            loadProfile();
            init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserRecipes();
    }

    //IŞIL ÇOLAK , SERDAR YILDIZ
    private void init() {
        binding.imgProfileEdit.setOnClickListener(v -> {
            // We will pick image from gallery and upload it to firebase storage
            // I prefer to use a 3rd party library for picking images from gallery
            PickImageDialog.build(new PickSetup()).show(requireActivity()).setOnPickResult(r -> {
                Log.e("ProfileFragment", "onPickResult: " + r.getUri());
                binding.imageProfile.setImageBitmap(r.getBitmap());
                uploadImage(r.getBitmap());
            }).setOnPickCancel(() -> Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show());
        });
        //binding.btnSetting.setOnClickListener(view1 -> startActivity(new Intent(requireContext(), SettingActivity.class)));
    }

    //IŞIL ÇOLAK , SERDAR YILDIZ
    private void uploadImage(Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images/" + FirebaseAuth.getInstance().getUid() + "image.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return storageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                // We need to save this download url in firebase database
                // So that we can load it in our app
                Toast.makeText(requireContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                user.setImage(Objects.requireNonNull(downloadUri).toString());
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(user);

            } else {
                Log.e("ProfileFragment", "onComplete: " + Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }


    //IŞIL ÇOLAK , SERDAR YILDIZ
    private void loadUserRecipes() {
        binding.rvProfile.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvProfile.setAdapter(new RecipeAdapter());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipes");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //Recipe recipe = new Recipe("" , "" , "" , "" , "" , "" , "" , "" , "");
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);
                    if(recipe.getId().equals(FirebaseAuth.getInstance().getUid())){
                        recipes.add(recipe);
                    }
                }
                ((RecipeAdapter) Objects.requireNonNull(binding.rvProfile.getAdapter())).setRecipeList(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileFragment", "onCancelled: " + error.getMessage());
            }
        });
    }

    //IŞIL ÇOLAK
    private void loadProfile() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                if (user != null) {
                    binding.tvUserName.setText(user.getName());
                    binding.tvUserEmail.setText(user.getEmail());
                    Glide
                            .with(requireContext())
                            .load(user.getImage())
                            .centerCrop()
                            .placeholder(R.drawable.img_user)
                            .into(binding.imageProfile);
                }else {
                    Log.e("ProfileFragment", "onDataChange: User is null");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileFragment", "onCancelled: " + error.getMessage());
            }
        });
        User user = new User(); // Load From Firebase here, we will learn it in next video
        user.setName("Name Surname");
        user.setEmail("mobileproject@gmail.com");
        binding.tvUserName.setText(user.getName());
        binding.tvUserEmail.setText(user.getEmail());
        // We will load images later, whenever we add firebase database
        // Let's test our code, Before testing our code, let's add some data in RecyclerView

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
