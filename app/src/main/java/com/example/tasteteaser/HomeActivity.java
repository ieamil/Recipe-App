package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tasteteaser.adapter.CategoryAdapter;
import com.example.tasteteaser.databinding.ActivityHomeBinding;
import com.example.tasteteaser.models.Category;
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


/*
* Firebase storage kullanarak recipe fotoğraflarını yükleme işlemlerini yaptırıcan
 */
public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView menuIcon;
    private NavigationView navigationView;
    private Button addRecipeBtn;

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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            navigationView = findViewById(R.id.navigationView);
        }else{
            navigationView = findViewById(R.id.nonAccountNavigationView);
        }

        /*
        Intent loginIntent = getIntent();
        boolean isUserLogged = loginIntent.getBooleanExtra("isUserLogged" , false);
        Log.d("isUserLogged" , String.valueOf(isUserLogged));
        if(isUserLogged){
            navigationView = findViewById(R.id.navigationView);
        }else{
            navigationView = findViewById(R.id.nonAccountNavigationView);
            Log.d("Navigationasdasd", String.valueOf(navigationView));
        }
*/

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

    private void goToProfile() {
        //ProfileFragment'ı yarat
        ProfileFragment profileFragment = new ProfileFragment();
        //FragmentTransaction'ı başlat ve fragmentı yerine koy
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, profileFragment)
                .addToBackStack(null) // Bu, geri tuşuna basıldığında önceki fragmenta geri dönmek için kullanılır
                .commit();

        // Navigation Drawer'ı kapat
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    // HomeActivity'e geçiş yapacak metot
    private void goToHomeActivity() {
        // HomeActivity'ye geçiş yap
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(intent);
        // finishAffinity();
    }

    // // Logout activity
    private void performLogout() {
        // Return LoginActivity
        Intent intent = new Intent(HomeActivity.this, Login.class);
        startActivity(intent);
        finish(); // Close HomeActivity
    }
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