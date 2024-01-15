package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.tasteteaser.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/*
* Firebase storage kullanarak recipe fotoğraflarını yükleme işlemlerini yaptırıcan
 */
public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private DrawerLayout drawerLayout;
    private ImageView menuIcon;
    private NavigationView navigationView;

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
                }else if (id == R.id.nav_recipe) {
                    goToAddRecipe();
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

    private void goToAddRecipe() {
        /*RecipeFragment recipeFragment = new RecipeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment lastFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(lastFragment != null){
            fragmentTransaction.detach(lastFragment);
        }else{
            return;
        }
        fragmentTransaction.replace(R.id.fragment_container , recipeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, recipeFragment)
                .addToBackStack(null) // Bu, geri tuşuna basıldığında önceki fragmenta geri dönmek için kullanılır
                .commit();*/

        // Close Drawer
        Intent intent = new Intent(HomeActivity.this , RecipeActivity.class);
        startActivity(intent);
        finish();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    // Diğer metotlar buraya gelicek
}