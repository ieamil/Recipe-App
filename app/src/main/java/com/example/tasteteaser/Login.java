package com.example.tasteteaser;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn, continueWithoutAccountBtn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        continueWithoutAccountBtn = findViewById(R.id.continue_without_account_btn);

        callSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            Pair[] pairs = new Pair[7];
            pairs[0] = new Pair<>(image, "logo_image");
            pairs[1] = new Pair<>(logoText, "logo_text");
            pairs[2] = new Pair<>(sloganText, "logo_desc");
            pairs[3] = new Pair<>(username, "username_tran");
            pairs[4] = new Pair<>(password, "password_tran");
            pairs[5] = new Pair<>(login_btn, "button_tran");
            pairs[6] = new Pair<>(callSignUp, "login_signup_tran");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        login_btn.setOnClickListener(view -> loginUser());

        continueWithoutAccountBtn.setOnClickListener(view -> continueWithoutAccount());
    }

    private void loginUser() {
        String email = username.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        if (!email.isEmpty() && !pass.isEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            checkEmailVerification();
                        } else {
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void continueWithoutAccount() {
        Intent intent = new Intent(Login.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
            // Giriş yapıldıktan sonra HomeActivity'e yönlendir.
            startActivity(new Intent(Login.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
