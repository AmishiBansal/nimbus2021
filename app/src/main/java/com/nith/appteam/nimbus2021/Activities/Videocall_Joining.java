package com.nith.appteam.nimbus2021.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

public class Videocall_Joining extends AppCompatActivity {

    TextView videoJoin,remainingT;
    CountDownTimer timer;
    RequestQueue requestQueue;
    private String uid;
    String channel;
    String token;
    boolean gotDetails = false;
    private SharedPreferences sharedPref;
    ProgressBar progressBar;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videocall__joining);
        progressBar = findViewById(R.id.pb);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                getChannelNameAndTokenId();
            }
        };

        remainingT = findViewById(R.id.remaining_time);

        timer = new CountDownTimer(60000,1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                remainingT.setText("Retry After : "+millisUntilFinished/1000 + "s");
                if (remainingT.getText().toString().equals("0")){
                    timer.onFinish();
                }
            }

            @Override
            public void onFinish() {
                videoJoin.setEnabled(true);
                videoJoin.setText("Video");
                handler.removeCallbacks(runnable);
                progressBar.setVisibility(View.GONE);
                remainingT.setText("Please Try Again");
                Toast.makeText(getApplicationContext(), "No connection present Try Again!", Toast.LENGTH_SHORT).show();
            }
        };

        videoJoin = findViewById(R.id.video_join);
        requestQueue = Volley.newRequestQueue(Videocall_Joining.this);
        if(getIntent().hasExtra("uid2"))
        {
            new AlertDialog.Builder(Videocall_Joining.this)
                    .setTitle("Report")
                    .setMessage("Wanna Report Previous User ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RequestQueue queue =  Volley.newRequestQueue(Videocall_Joining.this);
                            StringRequest request = new StringRequest(Request.Method.GET, Constant.Url + "/omegle_clone/report/" + getIntent().getStringExtra("uid2"), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("ReportResponse",response);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Error",error.toString());
                                }
                            });
                            queue.add(request);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("onclick","No");
                        }
                    })
                    .show();
        }
        videoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                videoJoin.setText("Searching the User...");
                videoJoin.setEnabled(false);
                timer.start();
                getUserId();
            }
        });
    }

    private void getUserId() {
        sharedPref = getSharedPreferences("app", MODE_PRIVATE);
        uid = sharedPref.getString("firebaseUid","");
        if (!uid.isEmpty()) {
            Log.e("UID", uid);
            getChannelNameAndTokenId();
        } else {
            Toast toast = Toast.makeText(this,"Try reinstalling the app or clearing data", Toast.LENGTH_SHORT);
            toast.show();
            progressBar.setVisibility(View.GONE);
            videoJoin.setText("Video");
        }
    }

    private void getChannelNameAndTokenId() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constant.Url + "omegle_clone/joinvc/" + uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("video call response", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("Message"))
                    {
                        Log.e("response", jsonObject.get("Message").toString());
                        handler.postDelayed(runnable,1000);                 // --for triggering getChannelNameAndTokenId() to send request to backend after every 1 sec
                    }
                    else if (jsonObject.has("uid"))
                    {
                        Log.e("onResponse: ", "got channel and token");
                        channel = jsonObject.getString("channel");
                        token = jsonObject.getString("token");
                        if (channel != null && channel.length() != 0 && token != null && token.length() != 0) {
                            gotDetails = true;
                            Intent intent = new Intent(Videocall_Joining.this, VideoCallActivity.class);
                            intent.putExtra("channel", channel);
                            intent.putExtra("token", token);
                            intent.putExtra("uid",uid);
                            intent.putExtra("uid2",jsonObject.getString("uid2"));
                            Log.e("channel", channel);
                            Log.e("token", token);
                            handler.removeCallbacks(runnable);
                            progressBar.setVisibility(View.GONE);
                            startActivity(intent);
                            timer.cancel();
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse: ", error.toString());
                progressBar.setVisibility(View.GONE);
                videoJoin.setText("Video");
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
        finish();
    }
}