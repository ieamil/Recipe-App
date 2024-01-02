package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// Main activity representing the home screen
public class HomeActivity extends AppCompatActivity {

    // Creation of the activity and setting the content view
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Eğer bir layout kullanacaksanız, bu kısmı layout adınıza göre düzenleyin.
    }

    // Handling the back button press to navigate to the previous screen
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