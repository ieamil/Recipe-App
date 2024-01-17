package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.tasteteaser.models.Category;
import com.example.tasteteaser.models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView menuIcon;
    private NavigationView navigationView;
    private Button addRecipeBtn;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
        // DrawerLayout, menuIcon ve NavigationView'u tanımla
        drawerLayout = findViewById(R.id.drawerLayout);
        menuIcon = findViewById(R.id.menuIcon);
        updateNavHeader();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            navigationView = findViewById(R.id.navigationView);
        }else{
            navigationView = findViewById(R.id.nonAccountNavigationView);
        }
        // menuIcon click listener
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Drawer'ı aç/kapat
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        addRecipeBtn = findViewById(R.id.add_recipe_button);
        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this , RecipeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        TextView tvSeeAllCategories = findViewById(R.id.tv_see_all_categories);
        tvSeeAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kullanıcı "See All"e tıkladığında ActivityAllCategories'e geçiş yap
                Intent intent = new Intent(HomeActivity.this, AllCategoriesActivity.class);
                startActivity(intent);
            }
        });


        // NavigationView içindeki menu öğelerine tıklama olayını dinle
        // NavigationView click listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Check id
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // Swap to HomeActivity if user click "Home"
                    goToHomeActivity();
                    return true;
                } else if (id == R.id.nav_logout) {
                    // Swap tp LoginActivity if user click "Logout"
                    performLogout();
                    return true;
                }else if (id == R.id.nav_profile) {
                    goToProfile();
                    return true;
                }
                return false;
            }
        });

    }

    //IŞIL ÇOLAK
    private void updateNavHeader() {
        // Kullanıcı bilgilerini al
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // NavigationView'ı bul
            NavigationView navigationView = findViewById(R.id.navigationView);
            if (navigationView != null) {
                // HeaderView'ı bul
                View headerView = navigationView.getHeaderView(0);
                if (headerView != null) {
                    // TextView'ları ve ImageView'ı bul
                    TextView userEmailTextView = headerView.findViewById(R.id.userEmail);
                    ImageView userImageView = headerView.findViewById(R.id.userImage);

                    if (userEmailTextView != null && userImageView != null) {
                        // User nesnesinden ad, e-posta ve profil fotoğrafı bilgilerini al
                        String userEmail = user.getEmail();
                        String userProfileImage = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";

                        // TextView ve ImageView'ları güncelle
                        userEmailTextView.setText("E-posta: " + userEmail);

                        // Profil fotoğrafını Glide kullanarak yükle
                        Glide.with(this)
                                .load(userProfileImage)
                                .placeholder(R.drawable.ttpng) // varsayılan resim
                                .error(R.drawable.img_cat) // hata durumunda gösterilecek resim
                                .into(userImageView);
                    }
                }
            }
        }
    }



    //IŞIL ÇOLAK
    private void goToProfile() {
        ProfileFragment profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, profileFragment)
                .addToBackStack(null)
                .commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    //IŞIL ÇOLAK
    private void goToHomeActivity() {
        // HomeActivity'ye geçiş yap
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(intent);
        // finishAffinity();
    }

    // // Logout activity
    //IŞIL ÇOLAK
    private void performLogout() {
        // Return LoginActivity
        Intent intent = new Intent(HomeActivity.this, Login.class);
        startActivity(intent);
        finish(); // Close HomeActivity
    }
    //SERDAR YILDIZ
    private List getCategories(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoriesData = database.getReference("Categories");
        List<Category> categories = new ArrayList<>();
        categoriesData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Get Categories Error " ,error.getMessage());
                return;
            }
        });
        return categories;
    }
}