package com.nith.appteam.nimbus2021.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

    TextView videoJoin;

    RequestQueue requestQueue;
    FirebaseUser firebaseUser;
    // uid of firebase user
    private String uid;
    String channel;
    String token;
    boolean gotDetails = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videocall__joining);

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
                            StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.baseUrl) + "/omegle_clone/report/" + getIntent().getStringExtra("uid2"), new Response.Listener<String>() {
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
                getUserId();
            }
        });
    }

    private void getUserId() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            uid = firebaseUser.getUid();
            Log.e("UID", "mslfkd");
            getChannelNameAndTokenId();
        } else
            {
            Toast toast = Toast.makeText(this, "Please Sign in to join video call", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.getView().setPadding(5, 5, 5, 5);
            toast.getView().setBackgroundColor(Color.GRAY);
            toast.show();
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
                    if (jsonObject.has("Message")) {
                        Log.e("response", jsonObject.get("Message").toString());
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        getChannelNameAndTokenId();
                                    }
                                },
                                1000);
                    }
                    else if (jsonObject.has("uid")) {
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
                            startActivity(intent);

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
            }
        });
        requestQueue.add(stringRequest);
    }
}