package com.nith.appteam.nimbus2021.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nith.appteam.nimbus2021.R;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 138;
    private List<AuthUI.IdpConfig> providers;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private TextView loginButton,SignUpbutton;
    RichPathView nimbus,nimbus1;
    Boolean SignInFlag;
    String email,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView t_n, t_k, e_n, e_k;
        Animation animation, animation1, animation2, animation3, anim;
        nimbus = findViewById(R.id.nimbus);
        nimbus1 = findViewById(R.id.nimbus1);

        t_n = findViewById(R.id.t_n);
        t_k = findViewById(R.id.t_k);
        e_n = findViewById(R.id.e_n);
        e_k = findViewById(R.id.e_k);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fast_anim);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slow_anim);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fast_anim_h);
        animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slow_anim_h);

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_anim);

        nimbus.startAnimation(anim);
        nimbus1.startAnimation(anim);

        e_n.startAnimation(animation);
        e_k.startAnimation(animation1);
        t_n.startAnimation(animation2);
        t_k.startAnimation(animation3);

        animation();

        SignInFlag = getIntent().getBooleanExtra("SignInFlag",false);

        loginButton = findViewById(R.id.login_btn);
        SignUpbutton = findViewById(R.id.signup_btn);
        progressBar = findViewById(R.id.login_progress);
        sharedPref = getSharedPreferences("app", MODE_PRIVATE);
        editor = sharedPref.edit();
        if(SignInFlag)
        {   //user SignedIn Successfully
            uid = getIntent().getStringExtra("FirebaseUID");
            email = getIntent().getStringExtra("LoginEmail");
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
            SignUpbutton.setVisibility(View.GONE);
            isUser(email);
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,EmailAuthentication.class);
                intent.putExtra("login",true);
                startActivity(intent);

            }
        });
        SignUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,EmailAuthentication.class);
                startActivity(intent);

            }
        });
    }

    public void animation() {
        final RichPath part1 = nimbus.findRichPathByName("tiny_right");
        System.out.println(part1);
        final RichPath part2 = nimbus.findRichPathByName("tiny_left");
        final RichPath part5 = nimbus.findRichPathByName("big_right");
        final RichPath part6 = nimbus.findRichPathByName("big_left");

        RichPathAnimator
                .animate(part1)
                .trimPathOffset(0, 1.0f)
                .andAnimate(part2)
                .trimPathOffset(0, 1.0f)
                .andAnimate(part5)
                .trimPathOffset(0, 1.0f)
                .andAnimate(part6)
                .trimPathOffset(0, 1.0f)
                .durationSet(2000)
                .repeatModeSet(RichPathAnimator.RESTART)
                .repeatCountSet(RichPathAnimator.INFINITE)
                .interpolatorSet(new OvershootInterpolator())
                .start();
    }


    private void isUser(final String email)
    {
        Log.e("user", "inside is User");
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.baseUrl)+"/users/checkUser/"+email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean isUserPresent;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("response  ", response);
                    isUserPresent = (Boolean)jsonObject.getBoolean("user_present");
                    if (isUserPresent) {
                        Log.e("Boolean  ", isUserPresent.toString());
//                        String token = (String) jsonObject.get("token");
//                        editor.putString("token", token);
//                        editor.apply();
                        getDetails();
                    } else{
//                        editor.putBoolean("loginStatus", true);
                        Intent intent = new Intent(Login.this, ProfileNew.class);
                        intent.putExtra("editProfile", false);
                        intent.putExtra("email",email);
                        intent.putExtra("firebaseuid",uid);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("is user error", error.toString());
            }
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                Log.d("TAG", "getHeaders: "+uid);
                headers.put("Authorization", uid);
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void getDetails() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.baseUrl) + "/users/"+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    Log.e("get details response  ", response);
//                    JSONArray jsonArray = new JSONArray(response);
//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONObject jsonObject = new JSONObject(response);

                    editor.putString("firebaseUid", jsonObject.getString("firebase"));
                    editor.putString("username", jsonObject.getString("username"));
                    editor.putString("name",jsonObject.getString("firstName")+" " + jsonObject.getString("lastName"));
                    editor.putString("phoneNumber", jsonObject.getString("phone"));
                    editor.putString("email", jsonObject.getString("email"));
                    editor.putString("firstname", jsonObject.getString("firstName"));
                    editor.putString("lastname", jsonObject.getString("lastName"));
                    editor.putInt("OmegleReport", Integer.parseInt(jsonObject.getString("omegleReports")));
                    editor.putBoolean("OmegleAllowed", Boolean.parseBoolean(jsonObject.getString("omegleAllowed")));
                    editor.putString("college", jsonObject.getString("collegeName"));
                    editor.putBoolean("campusAmbassador", jsonObject.getBoolean("campusAmbassador"));
//                    editor.putString("rollNumber", jsonObject.getString("rollNumber"));
                    editor.putString("profileImage",jsonObject.getString("profileImage"));
                    editor.putBoolean("loginStatus",true);
                    editor.putBoolean("profileStatus",true);

                    editor.commit();
//                  editor.putString("normalPoints", jsonObject.getString("userPoints"));
//                  editor.putString("caPoints", jsonObject.getString("campusAmbassadorPoints"));
//                  editor.commit();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("get details error ", error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                Log.d("TAG", "getHeaders: "+uid);
                headers.put("Authorization", uid);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();
    }
}
