package com.nith.appteam.nimbus2021.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class QuizInstructionsActivity extends AppCompatActivity {

    Button playNow;
    Button leaderboard;
    String response;
    String quizId;
    String result = "";
    TextView instructionsTV, back,instructionsDetails;
    RequestQueue requestQueue;
    boolean attempted;
    SharedPreferences sharedPref;
    private String uid;
    FirebaseUser firebaseUser;
    int noquestions;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_instructions);
        back = findViewById(R.id.back);
        playNow = findViewById(R.id.playNowButton);
        leaderboard = findViewById(R.id.leaderboardButton);
        instructionsTV = findViewById(R.id.instructionsTV);
        instructionsDetails = findViewById(R.id.instructionsDetails);


        instructionsTV.setText("This quiz contains");
        instructionsDetails.setText("This quiz consists of " + getIntent().getStringExtra("count")+
                " multiple-choice questions.You are allowed to attempt the quiz only once.Keep the following in mind:\n" +
                "1. You will have only one attempts for this quiz\n" +
                "2. Time given for each question will be 15s\n" +
                "To start, click the \"Play Now\" button.");

        response = getIntent().getStringExtra("questions");
        quizId = getIntent().getStringExtra("quizId");

        Log.e("response", quizId);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        requestQueue = Volley.newRequestQueue(this);

        //Retrieve questions


        playNow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
//                Calendar currentTime = Calendar.getInstance();

                boolean flag;
              String aT = getIntent().getStringExtra("startTime");
              String bT = getIntent().getStringExtra("endTime");

                ZonedDateTime zdt = ZonedDateTime.now();
                ZonedDateTime zdt1 = ZonedDateTime.parse(aT);
                ZonedDateTime zdt2 = ZonedDateTime.parse(bT);

                if(zdt.compareTo(zdt1)>0 && zdt.compareTo(zdt2)<0){
                    Toast.makeText(QuizInstructionsActivity.this,"Time is Right",Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                else{
                    Toast.makeText(QuizInstructionsActivity.this, "Not Right Time", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(QuizInstructionsActivity.this)
                                .setTitle("Not right time!")
                                .setMessage("Start time: " + zdt1 + "\n" + "End time: " + zdt2)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    flag = false;
                }


                getUserId();
                if(uid == null){
                    new AlertDialog.Builder(QuizInstructionsActivity.this)
                            .setTitle("Not right time!")
                            .setMessage("Please SignIN")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    flag = false;
                }
//
                Log.e("Attempt", String.valueOf(attempted));
                   if (flag) {
                       checkPlayedOrNot();
                   }

                   else{
                       Toast.makeText(QuizInstructionsActivity.this, uid, Toast.LENGTH_SHORT).show();
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

    private void checkPlayedOrNot() {
        Log.e("hiiii", "onResponse: ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("quizId",getIntent().getStringExtra("quizId"));
            jsonObject.put("userId",uid);
            result = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("result", result);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.Url + "/quiz/checkPlayedOrNot/?format=json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("hiiii", "onResponse: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    attempted = jsonObject.getBoolean("attempted");
                    if (!attempted){
                        Intent intent = new Intent(QuizInstructionsActivity.this, Quiz.class);
                        intent.putExtra("questions", getIntent().getStringExtra("questions"));
                        intent.putExtra("quizId",getIntent().getStringExtra("quizId"));
                        intent.putExtra("userId",uid);
                        startActivity(intent);
                        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);

                    }
                    else{
                        new AlertDialog.Builder(QuizInstructionsActivity.this)
                                .setTitle("Tried!")
                                .setMessage("You have already tried it")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    Log.e("at",String.valueOf(attempted));
                    overridePendingTransition(R.anim.ease_in, R.anim.ease_out);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }) {

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                Log.d("TAG", "getHeaders: "+uid);
                headers.put("Authorization", uid);
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return result == null ? null : result.getBytes(StandardCharsets.UTF_8);
            }
        };

        requestQueue.add(stringRequest);


    }

    private void getUserId() {
        sharedPref = getSharedPreferences("app", MODE_PRIVATE);
        uid = sharedPref.getString("firebaseUid","");
        if (!uid.isEmpty()) {
            Log.e("UID", uid);
        } else {
            Toast toast = Toast.makeText(this,"Try reinstalling the app or clearing data", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
