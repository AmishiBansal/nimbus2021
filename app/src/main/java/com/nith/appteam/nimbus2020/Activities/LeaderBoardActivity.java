package com.nith.appteam.nimbus2020.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2020.Adapters.LeaderBoardAdapter;
import com.nith.appteam.nimbus2020.Models.LeaderboardModel;
import com.nith.appteam.nimbus2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar loadwall;
    LeaderBoardAdapter mLeaderBoardAdapter;
    List<LeaderboardModel> mLeaderboardModelList;
    String quizId;
    String image;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        TextView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        image = getResources().getString(R.string.defaultImageUrl);

        quizId = getIntent().getStringExtra("quizId");

        queue = Volley.newRequestQueue(LeaderBoardActivity.this);

        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        loadwall = findViewById(R.id.loadwall);
        mLeaderboardModelList = new ArrayList<>();
        mLeaderBoardAdapter = new LeaderBoardAdapter(mLeaderboardModelList, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mLeaderBoardAdapter);
        getData();

    }


    private void getData() {
        mLeaderboardModelList.clear();
        loadwall.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, getString(R.string.baseUrl) + "/quiz/leaderboards",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadwall.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response", response);

                            JSONArray players = jsonObject.getJSONArray("players");

                            for (int i = 0; i < players.length(); ++i) {

                                JSONObject player = players.getJSONObject(i);
                                if (player.has("image")) {
                                    image = player.getString("image");
                                }

                                mLeaderboardModelList.add(
                                        new LeaderboardModel(player.getString("name"),
                                                player.getInt("score"), image));
                                mLeaderBoardAdapter.notifyDataSetChanged();
                            }
                            loadwall.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Collections.sort(mLeaderboardModelList, new Comparator<LeaderboardModel>() {
                            @Override
                            public int compare(LeaderboardModel leaderboardModel,
                                               LeaderboardModel t1) {
                                if (leaderboardModel.getScore() == t1.getScore()) {
                                    return 0;
                                } else if (leaderboardModel.getScore() > t1.getScore()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        });

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("quizId", quizId);
                return params;
            }

        };

        queue.add(stringRequest);

    }

}


