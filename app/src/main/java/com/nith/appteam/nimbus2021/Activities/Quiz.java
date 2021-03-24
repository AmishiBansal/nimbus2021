package com.nith.appteam.nimbus2021.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nith.appteam.nimbus2021.Models.QuestionData;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;

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
    ImageView quesImg,opt1img,opt2img,opt3img,opt4img;
    CardView c1,c2,c3,c4;
    Button option1;
    Button option2;
    Button option3;
    String result = "";
    Button option4;
    RequestQueue requestQueue;
    List<QuestionData> questions = new ArrayList<>();
    int counter = 0;
    CountDownTimer timer = null;
    int a[] = {10,15,20,25};
    JSONArray mJSONArray;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mJSONArray = new JSONArray();
        //  Objects.requireNonNull(getSupportActionBar()).hide();
        timer = new CountDownTimer(15*1000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timeview.setText(millisUntilFinished / 1000 + "s");
                if(timeview.getText().toString().equals("0")){
                    timer.onFinish();
                }
            }


            public void onFinish() {

                if (questions.get(counter).getOption_chosen() == 30000) {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("questionId", questions.get(counter).getQuestionid());
                        jsonObject.put("answerId", questions.get(counter).getOption_chosen());
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
        quesImg = findViewById(R.id.quesImg);
        opt1img = findViewById(R.id.optionimg1);
        opt2img = findViewById(R.id.optionimg2);
        opt3img = findViewById(R.id.optionimg3);
        opt4img = findViewById(R.id.optionimg4);
        c1 = findViewById(R.id.cimg1);
        c2 = findViewById(R.id.cimg2);
        c3 = findViewById(R.id.cimg3);
        c4 = findViewById(R.id.cimg4);

        requestQueue = Volley.newRequestQueue(this);
        getQuestions();


        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(1);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("answerId", questions.get(counter).getOptionid_1());
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
                    jsonObject.put("answerId", questions.get(counter).getOptionid_2());
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
                    jsonObject.put("answerId", questions.get(counter).getOptionid_3());
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
                    jsonObject.put("answerId", questions.get(counter).getOptionid_4());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();
            }
        });


    }

    private void updateQuestion() {
        c1.setVisibility(View.GONE);
        c2.setVisibility(View.GONE);
        c3.setVisibility(View.GONE);
        c4.setVisibility(View.GONE);
        quesImg.setVisibility(View.GONE);
        questionView.setText(questions.get(counter).getQuestion());
        if(!questions.get(counter).getQuesImage().contains("null") && !questions.get(counter).getQuesImage().isEmpty()){
            quesImg.setVisibility(View.VISIBLE);
            Glide.with(Quiz.this).load(questions.get(counter).getQuesImage()).placeholder(R.color.black).into(quesImg);
        }

//        option1.setText(questions.get(counter).getOption_1());
//        option2.setText(questions.get(counter).getOption_2());
//        option3.setText(questions.get(counter).getOption_3());
//        option4.setText(questions.get(counter).getOption_4());
        if(questions.get(counter).getOptionCount()==2){
            option3.setVisibility(View.GONE);
            option4.setVisibility(View.GONE);
            option1.setText(questions.get(counter).getOption_1());
            option2.setText(questions.get(counter).getOption_2());
            if(!questions.get(counter).getOptionimg_1().contains("null") && !questions.get(counter).getOptionimg_1().isEmpty()){
                c1.setVisibility(View.VISIBLE);
                option1.setText("Option 1");

                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_1()).placeholder(R.color.black).into(opt1img);
            }
            if(!questions.get(counter).getOptionimg_2().contains("null") && !questions.get(counter).getOptionimg_2().isEmpty()){
                c2.setVisibility(View.VISIBLE);
                option2.setText("Option 2");

                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_2()).placeholder(R.color.black).into(opt2img);
            }
        }
        else if(questions.get(counter).getOptionCount()==3){
            option3.setVisibility(View.VISIBLE);
            option4.setVisibility(View.GONE);
            option1.setText(questions.get(counter).getOption_1());
            option2.setText(questions.get(counter).getOption_2());
            option3.setText(questions.get(counter).getOption_3());
            if(!questions.get(counter).getOptionimg_1().contains("null") && !questions.get(counter).getOptionimg_1().isEmpty()){
                c1.setVisibility(View.VISIBLE);
                option1.setText("Option 1");
                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_1()).placeholder(R.color.black).into(opt1img);
            }
            if(!questions.get(counter).getOptionimg_2().contains("null") && !questions.get(counter).getOptionimg_2().isEmpty()){
                c2.setVisibility(View.VISIBLE);
                option2.setText("Option 2");
                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_2()).placeholder(R.color.black).into(opt2img);
            }
            if(!questions.get(counter).getOptionimg_3().contains("null") && !questions.get(counter).getOptionimg_3().isEmpty()){
                c3.setVisibility(View.VISIBLE);
                option3.setText("Option 3");
                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_2()).placeholder(R.color.black).into(opt3img);
            }
        }
        else if(questions.get(counter).getOptionCount()==4){
            option3.setVisibility(View.VISIBLE);
            option4.setVisibility(View.VISIBLE);
            option1.setText(questions.get(counter).getOption_1());
            option2.setText(questions.get(counter).getOption_2());
            option3.setText(questions.get(counter).getOption_3());
            option4.setText(questions.get(counter).getOption_4());
            if(!questions.get(counter).getOptionimg_1().contains("null") && !questions.get(counter).getOptionimg_1().isEmpty()){
                c1.setVisibility(View.VISIBLE);
                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_1()).placeholder(R.color.black).into(opt1img);
                option1.setText("Option 1");
            }
            if(!questions.get(counter).getOptionimg_2().contains("null") && !questions.get(counter).getOptionimg_2().isEmpty()){
                c2.setVisibility(View.VISIBLE);
                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_2()).placeholder(R.color.black).into(opt2img);
                option2.setText("Option 2");
            }
            if(!questions.get(counter).getOptionimg_3().contains("null") && !questions.get(counter).getOptionimg_3().isEmpty()){
                c3.setVisibility(View.VISIBLE);
                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_2()).placeholder(R.color.black).into(opt3img);
                option3.setText("Option 3");
            }
            if(!questions.get(counter).getOptionimg_4().contains("null") && !questions.get(counter).getOptionimg_4().isEmpty()){
                c4.setVisibility(View.VISIBLE);
                Glide.with(Quiz.this).load(questions.get(counter).getOptionimg_4()).placeholder(R.color.black).into(opt4img);
                option4.setText("Option 4");
            }
        }
        questionnumber.setText("Q" + (counter + 1));
        questions.get(counter).setOption_chosen(30000);
        timer.cancel();
        timer = new CountDownTimer(questions.get(counter).getTimeLimit()*1000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timeview.setText(millisUntilFinished / 1000 + "s");
                if(timeview.getText().toString().equals("0")){
                    timer.onFinish();
                }
            }


            public void onFinish() {

                if (questions.get(counter).getOption_chosen() == 30000) {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("questionId", questions.get(counter).getQuestionid());
                        jsonObject.put("answerId", questions.get(counter).getOption_chosen());
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

                            jsonArray.getJSONObject(i).getString("text"),
                            jsonArray.getJSONObject(i).getString("id"),
                            jsonArray.getJSONObject(i).getJSONObject("option1").getString("text"),
                            jsonArray.getJSONObject(i).getJSONObject("option2").getString("text"),
                            jsonArray.getJSONObject(i).getJSONObject("option3").getString("text"),
                            jsonArray.getJSONObject(i).getJSONObject("option4").getString("text"),
                            jsonArray.getJSONObject(i).getJSONObject("option1").getString("id"),
                            jsonArray.getJSONObject(i).getJSONObject("option2").getString("id"),
                            jsonArray.getJSONObject(i).getJSONObject("option3").getString("id"),
                            jsonArray.getJSONObject(i).getJSONObject("option4").getString("id"),
                            jsonArray.getJSONObject(i).getJSONObject("option1").getString("image"),
                            jsonArray.getJSONObject(i).getJSONObject("option2").getString("image"),
                            jsonArray.getJSONObject(i).getJSONObject("option3").getString("image"),
                            jsonArray.getJSONObject(i).getJSONObject("option4").getString("image"),
                            jsonArray.getJSONObject(i).getString("image"),
                            jsonArray.getJSONObject(i).getInt("optionCount"),
                            jsonArray.getJSONObject(i).getInt("timeLimit"));
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
            jsonObject.put("answerId", questions.get(counter).getOption_chosen());
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
            jsonObject.put("quizId",getIntent().getStringExtra("quizId"));
            jsonObject.put("userId",getIntent().getStringExtra("userId"));
            jsonObject.put("responses", mJSONArray);
            result = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("result", result);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.Url + "/quiz/checkresponses/?format=json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (progressDialog != null) {
                    progressDialog.dismiss();
//                    onBackPressed();
                }
                Log.e("hiiii", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("message")){
                        Toast.makeText(Quiz.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Quiz.this,QuizMainActivity.class));
                        finish();

                    }                    Toast.makeText(Quiz.this, " score is " + jsonObject.getString("score"),
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
//                String UID = sharedPreferences.getString("firebaseUid","");
                String UID = "123456";
                HashMap<String, String> headers = new HashMap<String, String>();
                Log.d("TAG", "getHeaders: "+UID);
                headers.put("Authorization", UID);
                return headers;
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