package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView menuIcon;
    private NavigationView navigationView;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
        // DrawerLayout, menuIcon ve NavigationView'u tanımla
        drawerLayout = findViewById(R.id.drawerLayout);
        menuIcon = findViewById(R.id.menuIcon);
        navigationView = findViewById(R.id.navigationView);

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

        // NavigationView click listener

        // NavigationView içindeki menu öğelerine tıklama olayını dinle

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
    }

    // Diğer metotlar buraya gelicek
}