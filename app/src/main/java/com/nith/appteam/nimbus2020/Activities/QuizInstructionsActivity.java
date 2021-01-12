package com.nith.appteam.nimbus2020.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nith.appteam.nimbus2020.R;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizInstructionsActivity extends AppCompatActivity {

    Button playNow;
    Button leaderboard;
    String response;
    String quizId;
    TextView instructionsTV, back;
    int noquestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_instructions);
        back = findViewById(R.id.back);
        playNow = findViewById(R.id.playNowButton);
        leaderboard = findViewById(R.id.leaderboardButton);
        instructionsTV = findViewById(R.id.instructionsTV);


        instructionsTV.setText("This quiz contains");

        response = getIntent().getStringExtra("questions");
        quizId = getIntent().getStringExtra("quizId");

        Log.e("response", response);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        playNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean flag = true;

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int error = jsonObject.getInt("errorCode");

                    if (error == 1) {
                        String startTime = getIntent().getStringExtra("startTime");
                        String endTime = getIntent().getStringExtra("endTime");
                        new AlertDialog.Builder(QuizInstructionsActivity.this)
                                .setTitle("Not right time!")
                                .setMessage("Start time: " + startTime + "\n" + "End time: " + endTime)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        flag = false;

                    } else if (error == 2) {
                        new AlertDialog.Builder(QuizInstructionsActivity.this)
                                .setTitle("Already played")
                                .setMessage("You can play a quiz only one time")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        flag = false;

                    } else if (error == 4) {
                        Toast.makeText(QuizInstructionsActivity.this, "No questions available",
                                Toast.LENGTH_SHORT).show();
                        flag = false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (flag) {

                    Intent intent = new Intent(QuizInstructionsActivity.this, Quiz.class);
                    intent.putExtra("questions", response);
                    startActivity(intent);
                    overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                }


            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizInstructionsActivity.this,
                        LeaderBoardActivity.class);
                intent.putExtra("quizId", quizId);
                startActivity(intent);
            }
        });
    }
}
