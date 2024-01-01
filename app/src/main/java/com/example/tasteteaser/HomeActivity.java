package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Eğer bir layout kullanacaksanız, bu kısmı layout adınıza göre düzenleyin.
    }

    @Override
    public void onBackPressed() {
        // Bu metod geri tuşuna basıldığında çağrılır.
        // Buraya kullanıcıyı bir önceki sayfaya yönlendirmek için gerekli kodu ekleyin.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Bu satır eklenmiştir.
        super.onBackPressed(); // Bu satır da eklenmiştir.
    }

}