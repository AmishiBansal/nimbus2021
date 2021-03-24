package com.nith.appteam.nimbus2021.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int PERMISSION_REQ_ID = 22;

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
        if(getIntent().hasExtra("uid2") && getIntent().hasExtra("channel"))
        {
            ViewGroup viewGroup = findViewById(R.id.content);
            final View dialogView = LayoutInflater.from(Videocall_Joining.this).inflate(R.layout.custom_dialog_report, viewGroup, false);

            Button report_true = dialogView.findViewById(R.id.button_report_yes);
            Button report_false = dialogView.findViewById(R.id.button_report_no);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

            report_true.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText reason_edit = alertDialog.findViewById(R.id.edit_report_reason);
                    final String reason_for_reporting = reason_edit.getText().toString().trim();

                    if (!reason_for_reporting.isEmpty() && reason_for_reporting.length() != 0) {
                        RequestQueue requestQueue = Volley.newRequestQueue(Videocall_Joining.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Url + "omegle_clone/report/", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Report", response);
                                Toast.makeText(getApplicationContext(), "Previous user reported successfully", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error report", String.valueOf(error.getMessage()));
                                Toast.makeText(getApplicationContext(), "Report unsuccessful", Toast.LENGTH_SHORT).show();

                            }
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                            }


                            @Override
                            protected Map<String, String> getParams() {
                                sharedPref = getSharedPreferences("app", MODE_PRIVATE);
                                uid = sharedPref.getString("firebaseUid","");

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("channel", getIntent().getStringExtra("channel"));
                                params.put("reporter", uid);
                                params.put("reported", getIntent().getStringExtra("uid2"));
                                params.put("reason", reason_for_reporting);
                                Log.e("report data", params.toString());
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                sharedPref = getSharedPreferences("app", MODE_PRIVATE);
                                uid = sharedPref.getString("firebaseUid","");
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Authorization", uid);
                                Log.d("get_headers", "getHeaders: " + uid);
                                return headers;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                        ));
                        requestQueue.add(stringRequest);
                    } else {
                        reason_edit.setError("Please give reason to report the user");
                        reason_edit.setText(" ");
                    }

                }
            });


            report_false.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

//            new AlertDialog.Builder(Videocall_Joining.this)
//                    .setTitle("Report")
//                    .setMessage("Wanna Report Previous User ?")
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            RequestQueue queue =  Volley.newRequestQueue(Videocall_Joining.this);
//                            StringRequest request = new StringRequest(Request.Method.GET, Constant.Url + "/omegle_clone/report/" + getIntent().getStringExtra("uid2"), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    Log.e("ReportResponse",response);
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Log.e("Error",error.toString());
//                                }
//                            });
//                            queue.add(request);
//                        }
//                    })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Log.e("onclick","No");
//                        }
//                    })
//                    .show();
        }
        videoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                        checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                        checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
                        progressBar.setVisibility(View.VISIBLE);
                        videoJoin.setText("Searching the User...");
                        videoJoin.setEnabled(false);
                        timer.start();
                        getUserId();
                }

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

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }

        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT
                        > Build.VERSION_CODES.Q){
                    new AlertDialog.Builder(Videocall_Joining.this)
                            .setTitle("Allow Permissions from the Settings")
                            .setMessage("1. Open Settings app.\n2. Tap Apps & notifications.\n3. Tap on Nimbus2K21. If you can't find it, first tap See all apps or App info.\n" +
                                    "4. Tap Permissions.\n" +
                                    "5. If you allowed or denied any permissions for the app, you’ll find them here.\n" +
                                    "6. To change a permission setting, tap it, then choose Allow or Deny.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                                }
                            })
                            .show();
                }
                else{
                    new AlertDialog.Builder(Videocall_Joining.this)
                            .setTitle("Re-Ask for Permissions")
                            .setMessage("Permissions are neccesary for this feature to work properly.\n" +
                                    "Steps to follow:" +
                                    "\n1. Close the Dialog Box." +
                                    "\n2. Click on Button Again. Permissions will be asked again." +
                                    "\n3. Allow Permissions for better experience.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }

            }
            else{
                progressBar.setVisibility(View.VISIBLE);
                videoJoin.setText("Searching the User...");
                videoJoin.setEnabled(false);
                timer.start();
                getUserId();
            }

            // Here we continue only if all permissions are granted.
            // The permissions can also be granted in the system settings manually.

        }

    }
}