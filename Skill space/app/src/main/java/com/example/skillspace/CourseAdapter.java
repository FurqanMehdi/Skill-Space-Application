package com.example.skillspace;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final List<Course> allCourses; // Keep track of all courses
    private List<Course> displayedCourses; // Currently displayed courses
    private Context context = null; // Context for starting activities

    public CourseAdapter(List<Course> courses) {
        this.context = context;
        this.allCourses = new ArrayList<>(courses); // Original list
        this.displayedCourses = new ArrayList<>(courses); // Mutable list for filtering
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_course_catalog, parent, false); // Assuming you have a separate layout for course items
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = displayedCourses.get(position);

        holder.courseName.setText(course.getName());
        holder.courseDescription.setText(course.getDescription());

        // Enable "Take Test" button only if the course is completed
        holder.takeTestButton.setEnabled(course.isCompleted());

        holder.takeTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!course.isTestTaken()) {
                    // Start the TestActivity
                    Intent intent = new Intent(context, Test.class);
                    intent.putExtra("courseName", course.getName());
                    context.startActivity(intent);

                    // Mark the test as taken after it's completed
                    course.setTestTaken(true);
                    notifyDataSetChanged(); // Update UI
                } else {
                    Toast.makeText(context, "Test already completed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Disable next course if this course's test is not completed
        if (position > 0 && !displayedCourses.get(position - 1).isTestTaken()) {
            holder.itemView.setEnabled(false); // Disable this course item
            holder.itemView.setAlpha(0.5f); // Visually indicate it's disabled
        } else {
            holder.itemView.setEnabled(true);
            holder.itemView.setAlpha(1f);
        }
    }

    @Override
    public int getItemCount() {
        return displayedCourses.size();
    }

    // Filter method
    public void filterCourses(String query) {
        if (query.isEmpty()) {
            displayedCourses = new ArrayList<>(allCourses); // Reset list if query is empty
        } else {
            List<Course> filteredList = new ArrayList<>();
            for (Course course : allCourses) {
                if (course.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(course);
                }
            }
            displayedCourses = filteredList; // Update displayed courses
        }
        notifyDataSetChanged(); // Notify adapter of data changes
    }

    // Mark the course as completed and update the UI
    public void markCourseCompleted(String courseName) {
        for (Course course : allCourses) {
            if (course.getName().equals(courseName)) {
                course.setCompleted(true);
                notifyDataSetChanged(); // Update the UI in RecyclerView
                Toast.makeText(context, courseName + " marked as completed!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, courseDescription;
        Button takeTestButton; // Added the Button for the test

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_title);
            courseDescription = itemView.findViewById(R.id.course_description);
            takeTestButton = itemView.findViewById(R.id.take_test_button); // Button initialization
        }
    }
}
