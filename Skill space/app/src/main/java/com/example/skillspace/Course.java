package com.example.skillspace;

public class Course {
    private String name;
    private String description;
    private boolean isCompleted; // Tracks if the course is completed
    private boolean isTestTaken; // Tracks if the test is taken

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
        this.isCompleted = false; // Default: course not completed
        this.isTestTaken = false; // Default: test not taken
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isTestTaken() {
        return isTestTaken;
    }

    public void setTestTaken(boolean testTaken) {
        isTestTaken = testTaken;
    }
}
