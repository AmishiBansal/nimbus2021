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

                boolean flag = false;
              String aT = getIntent().getStringExtra("startTime");
              String bT = getIntent().getStringExtra("endTime");

                ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
                String cT = String.valueOf(utc.toInstant());
                ZonedDateTime zdt = ZonedDateTime.parse(cT);
                String newFormat = zdt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
                ZonedDateTime zdt1 = ZonedDateTime.parse(aT);
                String newFormat1 = zdt1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
                ZonedDateTime zdt2 = ZonedDateTime.parse(bT);
                String newFormat2 = zdt2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                try {
                    Date d1 = sdf.parse(newFormat1);
                    Date d2 = sdf.parse(newFormat);
                    Date d3 = sdf.parse(newFormat2);

                    assert d2 != null;
                    if(d2.compareTo(d1)>=0 && d2.compareTo(d3)<=0){
                        Toast.makeText(QuizInstructionsActivity.this,"Time is right",Toast.LENGTH_SHORT).show();
                        flag = true;

                    }
                    else{
                        new AlertDialog.Builder(QuizInstructionsActivity.this)
                                .setTitle("Not right time!")
                                .setMessage("Start time: " + String.valueOf(d1) + "\n" + "End time: " + String.valueOf(d3))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                                flag = false;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
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
//                boolean flag = getscore();
//                Toast.makeText(QuizInstructionsActivity.this, String.valueOf(flag), Toast.LENGTH_SHORT).show();

//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    int error = jsonObject.getInt("errorCode");
//
//                    if (error == 1) {
//                        String startTime = getIntent().getStringExtra("startTime");
//                        String endTime = getIntent().getStringExtra("endTime");
//                        new AlertDialog.Builder(QuizInstructionsActivity.this)
//                                .setTitle("Not right time!")
//                                .setMessage("Start time: " + startTime + "\n" + "End time: " + endTime)
//                                .setIcon(android.R.drawable.ic_dialog_alert)
//                                .show();
//                        flag = false;
//
//                    } else if (error == 2) {
//                        new AlertDialog.Builder(QuizInstructionsActivity.this)
//                                .setTitle("Already played")
//                                .setMessage("You can play a quiz only one time")
//                                .setIcon(android.R.drawable.ic_dialog_alert)
//                                .show();
//                        flag = false;
//
//                    } else if (error == 4) {
//                        Toast.makeText(QuizInstructionsActivity.this, "No questions available",
//                                Toast.LENGTH_SHORT).show();
//                        flag = false;
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
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
                Constant.Url + "/quiz/checkPlayedOrNot/", new Response.Listener<String>() {
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
                SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", null);
                HashMap<String, String> map = new HashMap<>();
                map.put("access-token", token);
                return map;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return result == null ? null : result.getBytes(StandardCharsets.UTF_8);
            }
        };

        requestQueue.add(stringRequest);


    }

    private void getUserId() {
//        uid = "krejbgfkjerjg"; // for testing
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            uid = firebaseUser.getUid();
            Log.e("UID", uid);
        } else {
            Toast.makeText(this,uid, Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
