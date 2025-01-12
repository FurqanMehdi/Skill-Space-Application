package com.example.skillspace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class dashboard extends AppCompatActivity {

    // Declare UI components
    private TextView userNameTextView;
    private ImageView profileIcon;
    private EditText searchInput;
    private Button searchButton;
    private LinearLayout firstCourse, secondCourse, thirdCourse, fourthCourse, fifthCourse;
    private TextView firstCourseTitle, secondCourseTitle, thirdCourseTitle, fourthCourseTitle, fifthCourseTitle;

    // HashMap to store courses and their details
    private HashMap<String, String> courseLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Check if the user is logged in via Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "You need to register first.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(dashboard.this, login.class);
            startActivity(intent);
            finish();
            return; // Prevent further execution
        }

        // Retrieve user name from Intent or SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        // Initialize views
        searchInput = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);
        profileIcon = findViewById(R.id.profile_icon);

        // Course layouts
        firstCourse = findViewById(R.id.first);
        secondCourse = findViewById(R.id.second);
        thirdCourse = findViewById(R.id.third);
        fourthCourse = findViewById(R.id.fourth);
        fifthCourse = findViewById(R.id.fivth);

        // Course titles
        firstCourseTitle = findViewById(R.id.course_title);
        secondCourseTitle = findViewById(R.id.android_course_title);
        thirdCourseTitle = findViewById(R.id.cpp_course_title);
        fourthCourseTitle = findViewById(R.id.python_course_title);
        fifthCourseTitle = findViewById(R.id.ds_course_title);

        // Profile Icon Click Listener
        profileIcon.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(dashboard.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_test) {
                    Intent intent = new Intent(dashboard.this, Test.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.menu_certificate) {
                    Intent intent = new Intent(dashboard.this, certificate.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_logout) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();

                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(dashboard.this, login.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else {
                    return false;
                }
            });

            popupMenu.show();
        });

        // Initialize course links
        initializeCourses();

        // Debug Log
        Log.d("Dashboard", "Courses initialized: " + courseLinks.keySet());

        // Search button click listener
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim().toLowerCase();

            // Filter courses dynamically
            filterCourses(query, firstCourse, firstCourseTitle);
            filterCourses(query, secondCourse, secondCourseTitle);
            filterCourses(query, thirdCourse, thirdCourseTitle);
            filterCourses(query, fourthCourse, fourthCourseTitle);
            filterCourses(query, fifthCourse, fifthCourseTitle);

            Log.d("Dashboard", "Search query: " + query); // Debug Log
        });
    }

    // Initialize available courses
    private void initializeCourses() {
        courseLinks = new HashMap<>();
        courseLinks.put("Web Development", "https://youtu.be/HyU4vkZ2NB8");
        courseLinks.put("C++ Programming", "https://youtu.be/6mbwJ2xhgzM");
        courseLinks.put("Advance Python", "https://youtu.be/7wnove7K-ZQ?si=lCEYJTs1BM3OggSy");
        courseLinks.put("Data Science", "https://youtu.be/-ETQ97mXXF0?si=A8XtLF4a7NEpyN4c");
        courseLinks.put("Android Development", "https://youtu.be/1tON1Ui9ksk?si=6q_vuTXSTWSjvFmM");
        // Add more courses as needed
    }

    // Filter courses dynamically based on the query
    private void filterCourses(String query, LinearLayout courseLayout, TextView courseTitle) {
        if (courseTitle.getText().toString().toLowerCase().contains(query)) {
            courseLayout.setVisibility(View.VISIBLE);
        } else {
            courseLayout.setVisibility(View.GONE);
        }
    }

    // Open the course link in a browser
    private void openCourseLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }
}
