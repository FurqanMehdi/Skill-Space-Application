package com.example.skillspace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput, usernameInput, passwordInput;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.etName);
        usernameInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Validate that the inputs are not empty
                if (!name.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    // Register the user with Firebase
                    mAuth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(MainActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Successfully registered
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    uid = user.getUid().toString();
                                    SaveData(uid);

                                    Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                                    // Navigate to login screen after registration
                                    Intent intent = new Intent(MainActivity.this, login.class);
                                    startActivity(intent);
                                    finish();  // Close the registration activity
                                } else {
                                    // If registration fails
                                    Toast.makeText(MainActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to login screen
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });
    }

    public void SaveData(String uid){
        String email = usernameInput.getText().toString();
        String username = nameInput.getText().toString();

        Map<String, Object> user_data = new HashMap<>();

        user_data.put("email",email);
        user_data.put("username",username);

        db.collection("users").document(uid).set(user_data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successfully pushed data", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Sorry"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
