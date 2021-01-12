package com.nith.appteam.nimbus2020.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nith.appteam.nimbus2020.Activities.CampusAmbassadorPost;
import com.nith.appteam.nimbus2020.Adapters.FeedRecyclerAdapter;
import com.nith.appteam.nimbus2020.Models.FeedItem;
import com.nith.appteam.nimbus2020.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Wall extends Fragment {
    FeedRecyclerAdapter feedRecyclerAdapter;
    private SharedPreferences sharedPreferences;
    private FloatingActionButton upload;
    private RecyclerView feed;
    private ArrayList<FeedItem> feedList = new ArrayList<>();
    private ProgressBar progressBar;

    public Wall() {
    }

    @Override
    public void onResume() {
        feedList.clear();
        getFeeds();
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wall, container, false);
        upload = rootView.findViewById(R.id.upload);
        feed = rootView.findViewById(R.id.feed);
        progressBar = rootView.findViewById(R.id.progress_bar);

        StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        feed.setLayoutManager(_sGridLayoutManager);
        sharedPreferences = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);

        boolean caStatus = sharedPreferences.getBoolean("campusAmbassador", false);
        if (caStatus) {
            upload.setVisibility(View.VISIBLE);
        } else {
            upload.setVisibility(View.GONE);
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CampusAmbassadorPost.class);
                startActivity(i);
            }
        });
        progressBar.setVisibility(View.VISIBLE);
//        getFeeds();
        if (getContext() != null) {
            feedRecyclerAdapter = new FeedRecyclerAdapter(getContext(), feedList);
            feed.setAdapter(feedRecyclerAdapter);
        }
        return rootView;
    }

    private void getFeeds() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(getString(R.string.baseUrl) + "/views/links",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = jsonArray.length() - 1; i >= 0; i--) {
                                String imageUrl = jsonArray.getJSONObject(i).getString("image_url");
                                String postUrl = jsonArray.getJSONObject(i).getString("post_url");
                                FeedItem currentFeed = new FeedItem(imageUrl, postUrl);
                                feedList.add(currentFeed);

                            }
                            Log.e("arraySize", "" + jsonArray.length());
//                    Objects.requireNonNull(feed.getAdapter()).notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            feedRecyclerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }
}
