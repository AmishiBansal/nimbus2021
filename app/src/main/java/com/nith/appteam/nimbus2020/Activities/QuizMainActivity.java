package com.nith.appteam.nimbus2020.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2020.Adapters.QuizRecyclerAdapter;
import com.nith.appteam.nimbus2020.Models.Id_Value;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class QuizMainActivity extends AppCompatActivity {
    RecyclerView quizrecyclerView;
    RequestQueue queue;
    ArrayList<Id_Value> quiztypes = new ArrayList<>();
    ProgressBar loadwall;
    ImageView quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity_main);

        TextView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        quizrecyclerView = findViewById(R.id.quizrecyclerview);
        queue = Volley.newRequestQueue(QuizMainActivity.this);

        loadwall = findViewById(R.id.loadwall);

        quizrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        quizrecyclerView.setAdapter(new QuizRecyclerAdapter(QuizMainActivity.this, quiztypes));
        getdata();
        quizrecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, quizrecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                postdata(position);
                                //Display toast until ui
                                Toast.makeText(QuizMainActivity.this, "Coming Soon..", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
        );

    }


    private void getdata() {
        loadwall.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getString(R.string.baseUrl) + "/quiz/departments", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loadwall.setVisibility(View.GONE);

                try {
                    Log.e("quiz resp", response);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String image;
                        if (jsonArray.getJSONObject(i).has("image")) {
                            image = jsonArray.getJSONObject(i).getString("image");
                        } else {
                            image =
                                    getResources().getString(R.string.defaultImageUrl);
                        }
                        Id_Value idValue = new Id_Value(
                                jsonArray.getJSONObject(i).getString("departmentName"),
                                jsonArray.getJSONObject(i).getString("departmentId"),
                                image, "0", "0");
                        quiztypes.add(idValue);
                        Objects.requireNonNull(
                                quizrecyclerView.getAdapter()).notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Loggerreer", error.toString());

            }
        });


        queue.add(stringRequest);


    }


    private void postdata(final int position) {

        final ProgressDialog progressDialog = new ProgressDialog(QuizMainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getString(R.string.baseUrl) + "/quiz/departments", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("quiz", response);
                boolean flag = true;

                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(response);
                    int error = jsonObject.getInt("errorCode");

                    if (error == 3) {
                        flag = false;
                        new AlertDialog.Builder(QuizMainActivity.this)
                                .setTitle("User not Validated!")
                                .setMessage("You first need to signup or login.")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (flag) {
                    Intent i = new Intent(QuizMainActivity.this, DepartmentQuiz.class);
                    i.putExtra("quiz", response);
                    i.putExtra("departmentname", quiztypes.get(position).getValue());
                    i.putExtra("image", quiztypes.get(position).getImageUrl());
                    progressDialog.dismiss();
                    startActivity(i);
                    overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", null);

                map.put("access-token", token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("deptId", quiztypes.get(position).getId());
                return params;
            }

        };

        queue.add(stringRequest);

    }


}
