package com.example.skillspace;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button nextButton;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        questionText = findViewById(R.id.question_text);
        optionsGroup = findViewById(R.id.options_group);
        nextButton = findViewById(R.id.next_button);

        // Get sample questions from TestData
        questions = TestData.getSampleQuestions();

        // Display the first question
        displayQuestion();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = optionsGroup.getCheckedRadioButtonId();
                if (selectedOptionId == -1) {
                    Toast.makeText(TestActivity.this, "Please select an option!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedOption = findViewById(selectedOptionId);
                int selectedAnswerIndex = optionsGroup.indexOfChild(selectedOption);

                if (selectedAnswerIndex == questions.get(currentQuestionIndex).getCorrectAnswerIndex()) {
                    score++;
                }

                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    Toast.makeText(TestActivity.this, "Test complete! Your score: " + score, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionText.setText(currentQuestion.getQuestionText());

        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) optionsGroup.getChildAt(i);
            radioButton.setText(currentQuestion.getOptions().get(i));
        }

        optionsGroup.clearCheck();
    }
}
