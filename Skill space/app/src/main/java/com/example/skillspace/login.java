package com.example.skillspace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(login.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(login.this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userName = user.getDisplayName();  // You can set displayName or any other field

                                // Save user name in SharedPreferences
                                SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("userName", userName);  // Save the user name
                                editor.apply();

                                // Navigate to the dashboard
                                Intent intent = new Intent(login.this, dashboard.class);
                                intent.putExtra("userName", userName);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(login.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        findViewById(R.id.btnSignup).setOnClickListener(v -> {
            startActivity(new Intent(login.this, MainActivity.class));
        });
    }
}
