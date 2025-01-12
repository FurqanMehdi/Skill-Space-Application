package com.example.skillspace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Test extends AppCompatActivity {

    private int[] courseProgress = {100, 60, 80, 100, 50}; // Example progress data
    private Button[] courseButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);

        // Apply edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize buttons
        courseButtons = new Button[] {
                findViewById(R.id.course1_button),
                findViewById(R.id.course2_button),
                findViewById(R.id.course3_button),
                findViewById(R.id.course4_button),
                findViewById(R.id.course5_button)
        };

        // Update button state based on progress
        updateButtonState();
    }

    private void updateButtonState() {
        for (int i = 0; i < courseProgress.length; i++) {
            final int courseIndex = i; // Create a final copy of i
            if (courseProgress[i] == 100) {
                courseButtons[i].setEnabled(true);
                courseButtons[i].setOnClickListener(view -> {
                    // Action when button is clicked
                    startCourse(courseIndex + 1);
                });
            } else {
                courseButtons[i].setEnabled(false);
            }
        }
    }

    private void startCourse(int courseNumber) {
        // Logic to navigate to the respective activity based on the course number
        Intent intent = null;
        switch (courseNumber) {
            case 1:
                intent = new Intent(this, cpp_test.class);
                break;
            case 2:
                intent = new Intent(this, android_test.class);
                break;
            case 3:
                intent = new Intent(this, wed_test.class);
                break;
            case 4:
                intent = new Intent(this, python_test.class);
                break;
            case 5:
                intent = new Intent(this, ds_test.class);
                break;
            default:
                return;
        }
        startActivity(intent);
    }
}
