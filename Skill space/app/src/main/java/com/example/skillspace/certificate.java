package com.example.skillspace;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class certificate extends AppCompatActivity {

    public static String userID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout
        setContentView(R.layout.activity_certificate);

        // Apply system bars insets


        // Get the user's name from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String userName = prefs.getString("userName", "User");

        // Log to verify the retrieved name
        Log.d("CertificateActivity", "Retrieved userName: " + userName);

        // Set the user's name on the certificate
        TextView userNameTextView = findViewById(R.id.user_name_certificate);

        DocumentReference documentReference = db.collection("users").document(userID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userNameTextView.setText(document.getString("username"));

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(certificate.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (userName.equals("User")) {
            Toast.makeText(this, "No name found in SharedPreferences", Toast.LENGTH_LONG).show();
        } else {
            userNameTextView.setText(userName);
        }
    }
}
