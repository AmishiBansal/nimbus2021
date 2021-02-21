package com.nith.appteam.nimbus2021.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2021.Adapters.SponsorsAdapter;
import com.nith.appteam.nimbus2021.Adapters.SponsorsGroupAdapter;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;
import com.nith.appteam.nimbus2021.Utils.IResult;
import com.nith.appteam.nimbus2021.Utils.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Sponsor extends Fragment {
    RecyclerView recyclerView;
    ProgressBar loadwall;
    SponsorsGroupAdapter mSponsorsAdapter;
    List<String> mSponsorList;
    Context context;
    private IResult mResultCallback;

    public Sponsor() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sponsor, container, false);
        recyclerView = rootView.findViewById(R.id.sponsorsRecyclerView);
        loadwall = rootView.findViewById(R.id.loadwall);
        mSponsorList = new ArrayList<>();
        getData();
        mSponsorsAdapter = new SponsorsGroupAdapter(mSponsorList, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mSponsorsAdapter);

        return rootView;
    }

    private void getData() {
        mSponsorList.clear();
        loadwall.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Url + "/members/sponsors", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("data",response);
                loadwall.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0;i<jsonArray.length();i++){
                        if(jsonArray.getJSONObject(i).has("position")){
                            if (!mSponsorList.contains(String.valueOf(jsonArray.getJSONObject(i).get("position")))) {

                                mSponsorList.add(String.valueOf(jsonArray.getJSONObject(i).get("position")));
                            }
                            mSponsorsAdapter.notifyDataSetChanged();
                            Log.e("abcdefg",String.valueOf(mSponsorList));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


//    void initVolleyCallback() {
//        mResultCallback = new IResult() {
//            JSONObject obj;
//
//            @Override
//            public void notifySuccess(String requestType, JSONObject response,
//                                      JSONArray jsonArray) {
//
//
//                if (response != null) {
//                    loadwall.setVisibility(View.GONE);
//
//                    try {
//                        obj = response;
//                        String sponsorName = obj.getString("name");
//                        String sponsor_logo = getResources().getString(R.string.defaultImageUrl);
//                        if (obj.has("image")) sponsor_logo = obj.getString("image");
////                                String  = json.getString("event_time");
//                        mSponsorList.add(new com.nith.appteam.nimbus2021.Models.Sponsor(sponsorName, sponsor_logo));
//                        mSponsorsAdapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    Log.e("Hellcatt", response.toString());
//
//                } else {
//                    Log.e("zHell", jsonArray.toString());
//
//                    loadwall.setVisibility(View.GONE);
//
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        try {
//                            obj = jsonArray.getJSONObject(i);
//                            String sponsorName = obj.getString("name");
//                            String sponsor_logo = getActivity().getResources().getString(R.string.defaultImageUrl);
//                            if (obj.has("image")) sponsor_logo = obj.getString("image");
//                            mSponsorList.add(new com.nith.appteam.nimbus2021.Models.Sponsor(sponsorName, sponsor_logo));
//                            mSponsorsAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    mSponsorsAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//
//            @Override
//            public void notifyError(String requestType, VolleyError error) {
//                Log.i("error", error.toString());
//            }
//        };
//
//    }
}
