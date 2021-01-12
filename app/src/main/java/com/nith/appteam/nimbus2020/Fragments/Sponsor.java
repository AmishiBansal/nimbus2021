package com.nith.appteam.nimbus2020.Fragments;

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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.nith.appteam.nimbus2020.Adapters.SponsorsAdapter;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.IResult;
import com.nith.appteam.nimbus2020.Utils.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sponsor extends Fragment {
    RecyclerView recyclerView;
    ProgressBar loadwall;
    SponsorsAdapter mSponsorsAdapter;
    List<com.nith.appteam.nimbus2020.Models.Sponsor> mSponsorList;
    Context context;
    private IResult mResultCallback;

    public Sponsor(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sponsor, container, false);
        recyclerView = rootView.findViewById(R.id.sponsorsRecyclerView);
        loadwall = rootView.findViewById(R.id.loadwall);
        mSponsorList = new ArrayList<>();
        getData();
        mSponsorsAdapter = new SponsorsAdapter(mSponsorList, getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mSponsorsAdapter);
        return rootView;
    }

    private void getData() {
        mSponsorList.clear();
        loadwall.setVisibility(View.VISIBLE);

        initVolleyCallback();

        final VolleyService mVolleyService = new VolleyService(mResultCallback, getActivity());

        mVolleyService.getJsonArrayDataVolley("GETSPONSORS",
                getString(R.string.baseUrl) + "/sponsor");

    }


    void initVolleyCallback() {
        mResultCallback = new IResult() {
            JSONObject obj;

            @Override
            public void notifySuccess(String requestType, JSONObject response,
                                      JSONArray jsonArray) {


                if (response != null) {
                    loadwall.setVisibility(View.GONE);

                    try {
                        obj = response;
                        String sponsorName = obj.getString("name");
                        String sponsor_logo = getResources().getString(R.string.defaultImageUrl);
                        if (obj.has("image")) sponsor_logo = obj.getString("image");
//                                String  = json.getString("event_time");
                        mSponsorList.add(new com.nith.appteam.nimbus2020.Models.Sponsor(sponsorName, sponsor_logo));
                        mSponsorsAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.e("Hellcatt", response.toString());

                } else {
                    Log.e("zHell", jsonArray.toString());

                    loadwall.setVisibility(View.GONE);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            obj = jsonArray.getJSONObject(i);
                            String sponsorName = obj.getString("name");
                            String sponsor_logo = context.getResources().getString(R.string.defaultImageUrl);
                            if (obj.has("image")) sponsor_logo = obj.getString("image");
                            mSponsorList.add(new com.nith.appteam.nimbus2020.Models.Sponsor(sponsorName, sponsor_logo));
                            mSponsorsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    mSponsorsAdapter.notifyDataSetChanged();

                }
            }


            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.i("error", error.toString());
            }
        };

    }
}
