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

public class NavMainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar = findViewById(R.id.materialToolBar);
        frameLayout = findViewById(R.id.frameLayout);
        navigationView = findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                NavMainActivity.this, drawerLayout, materialToolbar, R.string.close_nav, R.string.open_nav);
        drawerLayout.addDrawerListener(toggle);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    Toast.makeText(NavMainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);}

                else if (item.getItemId() == R.id.nav_profile) {
                    Toast.makeText(NavMainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);}

                else if (item.getItemId() == R.id.nav_recipe) {
                Toast.makeText(NavMainActivity.this, "Add Recipe", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);}

                else if (item.getItemId() == R.id.nav_recorded) {
                    Toast.makeText(NavMainActivity.this, "Recorded", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);}

                else if (item.getItemId() == R.id.nav_logout) {
                    Toast.makeText(NavMainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                return false;
            }
        });
    }
}