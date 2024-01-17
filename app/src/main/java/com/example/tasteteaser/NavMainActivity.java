package com.example.tasteteaser;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavMainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            navigationView = findViewById(R.id.navigationView);
            setContentView(R.layout.nav_activity_main);
        }else{
            navigationView = findViewById(R.id.nonAccountNavigationView);
            setContentView(R.layout.nonaccount_nav_activity_main);
        }

        /*
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        boolean isUserLogged = intent.getBooleanExtra("isUserLogged" , false);
        if(isUserLogged){
            navigationView = findViewById(R.id.navigationView);
            TextView userNicknameTextView = navigationView.findViewById(R.id.userNickname);
            userNicknameTextView.setText(email);
            setContentView(R.layout.nav_activity_main);
        }else{
            navigationView = findViewById(R.id.nonAccountNavigationView);
            setContentView(R.layout.nonaccount_nav_activity_main);
        }
*/


        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar = findViewById(R.id.materialToolBar);
        frameLayout = findViewById(R.id.frameLayout);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                NavMainActivity.this, drawerLayout, materialToolbar, R.string.close_nav, R.string.open_nav);
        drawerLayout.addDrawerListener(toggle);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(currentUser != null){
                    if (item.getItemId() == R.id.nav_home) {
                        Toast.makeText(NavMainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);}

                    else if (item.getItemId() == R.id.nav_profile) {
                        Toast.makeText(NavMainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);}

                    else if (item.getItemId() == R.id.nav_logout) {
                        Toast.makeText(NavMainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);

                    }
                    return false;
                }else{
                    if (item.getItemId() == R.id.nav_home) {
                        Toast.makeText(NavMainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);}
                    else if(item.getItemId() == R.id.nav_logout) {
                        Toast.makeText(NavMainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);

                    }
                    return false;
                }

            }
        });
    }
}