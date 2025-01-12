package com.example.skillspace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ds_test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ds_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RadioGroup question1Group = findViewById(R.id.question1_radio_group);
        RadioGroup question2Group = findViewById(R.id.question2_radio_group);
        RadioGroup question3Group = findViewById(R.id.question3_radio_group);
        RadioGroup question4Group = findViewById(R.id.question4_radio_group);
        RadioGroup question5Group = findViewById(R.id.question5_radio_group);

        // Get reference to the submit button
        Button submitButton = findViewById(R.id.submit_button);

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if all questions have been answered
                if (isAllQuestionsAnswered(question1Group, question2Group, question3Group, question4Group, question5Group)) {
                    // Show success message if all questions are answered
                    Toast.makeText(ds_test.this, "Submit Successfully", Toast.LENGTH_SHORT).show();

                    // Start DashboardActivity when submit button is clicked
                    Intent intent = new Intent(ds_test.this, dashboard.class);
                    startActivity(intent);
                } else {
                    // Show error message if any question is unanswered
                    Toast.makeText(ds_test.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isAllQuestionsAnswered(RadioGroup... groups) {
        for (RadioGroup group : groups) {
            if (group.getCheckedRadioButtonId() == -1) {
                return false; // Return false if any question is unanswered
            }
        }
        return true; // Return true if all questions are answered
    }
}