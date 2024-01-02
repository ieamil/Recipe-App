package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView menuIcon;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // DrawerLayout, menuIcon ve NavigationView'u tanımla
        drawerLayout = findViewById(R.id.drawerLayout);
        menuIcon = findViewById(R.id.menuIcon);
        navigationView = findViewById(R.id.navigationView);

        // menuIcon'a tıklanma olayını dinle
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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Tıklanan öğenin id'sini kontrol et
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // "Home" öğesine tıklanırsa HomeActivity'e geçiş yap
                    goToHomeActivity();
                    return true;
                } else if (id == R.id.nav_logout) {
                    // "Logout" öğesine tıklanırsa çıkış yap ve giriş ekranına dön
                    performLogout();
                    return true;
                }

                // Diğer öğeler için gerekli işlemleri buraya ekleyebilirsiniz

                return false;
            }
        });

        // Diğer gerekli başlangıç işlemleri...
    }

    // HomeActivity'e geçiş yapacak metot
    private void goToHomeActivity() {
        // HomeActivity'ye geçiş yap
        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
        startActivity(intent);
        // İsterseniz bu satırı ekleyerek HomeActivity'nin üzerindeki diğer tüm aktiviteleri kapatabilirsiniz
        // finishAffinity();
    }

    // Logout işlemini gerçekleştiren metot
    private void performLogout() {
        // Burada logout işlemlerini gerçekleştirin (örneğin, oturumu kapat, verileri temizle vs.)

        // LoginActivity'ye dön
        Intent intent = new Intent(HomeActivity.this, Login.class);
        startActivity(intent);
        finish(); // HomeActivity'yi kapat
    }

    // Diğer metotlar ve işlemler...
}
