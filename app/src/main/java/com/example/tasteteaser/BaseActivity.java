package com.example.tasteteaser;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CompletableFuture;

public class BaseActivity extends AppCompatActivity {
    private CompletableFuture<Void> job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        job = new CompletableFuture<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        job.complete(null); // CompletableFuture'yi tamamlamak için complete(null) kullanın
    }
}
