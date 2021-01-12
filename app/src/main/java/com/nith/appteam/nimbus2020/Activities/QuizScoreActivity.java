package com.nith.appteam.nimbus2020.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nith.appteam.nimbus2020.R;

public class QuizScoreActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuizScoreActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);

        int score = getIntent().getIntExtra("score", 0);

        TextView sc = findViewById(R.id.score_obtained);
        sc.setText(score + "");

        findViewById(R.id.quiz_home_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizScoreActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

    }
}
