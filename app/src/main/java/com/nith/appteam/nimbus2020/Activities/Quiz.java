package com.nith.appteam.nimbus2020.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2020.Models.QuestionData;
import com.nith.appteam.nimbus2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz extends AppCompatActivity {
    TextView questionView, questionnumber, timeview;
    Button option1;
    Button option2;
    Button option3;
    String result = "";
    Button option4;
    RequestQueue requestQueue;
    List<QuestionData> questions = new ArrayList<>();
    int counter = 0;
    CountDownTimer timer;
    JSONArray mJSONArray;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mJSONArray = new JSONArray();
        //  Objects.requireNonNull(getSupportActionBar()).hide();
        timer = new CountDownTimer(15000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timeview.setText(millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {

                if (questions.get(counter).getOption_chosen() == 0) {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("questionId", questions.get(counter).getQuestionid());
                        jsonObject.put("correct", questions.get(counter).getOption_chosen());
                        mJSONArray.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                counter++;
                if (counter < questions.size() && questions.size() > 0) {
                    updateQuestion();
                } else {

                    Log.e("else", "else");

                    getscore();
                }
            }
        };

        questionnumber = findViewById(R.id.quizquestionnumber);
        timeview = findViewById(R.id.quizTimer);
        questionView = findViewById(R.id.quizquestion);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        requestQueue = Volley.newRequestQueue(this);
        getQuestions();


        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(1);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("correct", questions.get(counter).getOption_chosen());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();


            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(4);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("correct", questions.get(counter).getOption_chosen());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(2);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("correct", questions.get(counter).getOption_chosen());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(3);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("correct", questions.get(counter).getOption_chosen());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();
            }
        });


    }

    private void updateQuestion() {
        questionView.setText(questions.get(counter).getQuestion());
        option1.setText(questions.get(counter).getOption_1());
        option2.setText(questions.get(counter).getOption_2());
        option3.setText(questions.get(counter).getOption_3());
        option4.setText(questions.get(counter).getOption_4());
        questionnumber.setText("Q" + (counter + 1));
        timer.start();

    }


    private void getQuestions() {
        String response = getIntent().getStringExtra("questions");
        questionView.setText(response);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    QuestionData question = new QuestionData(
                            jsonArray.getJSONObject(i).getString("_id"),
                            jsonArray.getJSONObject(i).getString("question"),
                            jsonArray.getJSONObject(i).getString("option1"),
                            jsonArray.getJSONObject(i).getString("option2"),
                            jsonArray.getJSONObject(i).getString("option3"),
                            jsonArray.getJSONObject(i).getString("option4"));
                    questions.add(question);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (questions.size() > 0) {
            updateQuestion();
        } else {
            Toast.makeText(this, "No questions", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onBackPressed() {
        timer.cancel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("questionId", questions.get(counter).getQuestionid());
            jsonObject.put("correct", questions.get(counter).getOption_chosen());
            mJSONArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getscore();
    }


    private void getscore() {
        timer.cancel();
        progressDialog = new ProgressDialog(Quiz.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Calculating score...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        option4.setClickable(false);
        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);
        Log.e("hiiii", "onResponse: ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("answers", mJSONArray);
            result = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("result", result);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getString(R.string.baseUrl) + "/quiz/submit/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Quiz.this, "Response " + response, Toast.LENGTH_SHORT).show();
                if (progressDialog != null) {
                    progressDialog.dismiss();
//                    onBackPressed();
                }
                Log.e("hiiii", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Quiz.this, " score is " + jsonObject.getString("score"),
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Quiz.this, QuizScoreActivity.class);
                    intent.putExtra("score", Integer.valueOf(jsonObject.getString("score")));
                    startActivity(intent);
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

    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        super.onDestroy();
    }
}
