package com.example.skillspace;

import java.util.Arrays;
import java.util.List;

public class TestData {
    public static List<Question> getSampleQuestions() {
        return Arrays.asList(
                new Question("What does HTML stand for?",
                        Arrays.asList("Hyper Text Markup Language", "Home Tool Markup Language", "Hyperlinks and Text Markup Language", "None of the above"), 0),
                new Question("What is the correct syntax for a link?",
                        Arrays.asList("<a href='url'>link</a>", "<link>url</link>", "<a>url</a>", "<a src='url'></a>"), 0),
                new Question("Which language is used for styling web pages?",
                        Arrays.asList("HTML", "CSS", "JavaScript", "PHP"), 1)
        );
    }

    public static List<Course> getCourses() {
        return Arrays.asList(
                new Course("Web Development", "An introductory course to web development."),
                new Course("Android Development", "Learn to build Android applications.")
        );
    }
}
