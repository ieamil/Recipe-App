package com.example.tasteteaser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tasteteaser.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;

    //IŞIL ÇOLAK
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.textView.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });

        binding.button.setOnClickListener(v -> {
            String email = binding.emailEt.getText().toString();
            String pass = binding.passET.getText().toString();
            String confirmPass = binding.confirmPassEt.getText().toString();

            if (!email.isEmpty() && !pass.isEmpty() && !confirmPass.isEmpty()) {
                if (pass.equals(confirmPass)) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    //User created with success , now post authentication mail
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful()) {
                                                        Toast.makeText(SignUp.this, "Email verification sent", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }

                                    // Diğer işlemler devam eder, örneğin giriş ekranına yönlendirme
                                    Intent intent = new Intent(SignUp.this, Login.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignUp.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}