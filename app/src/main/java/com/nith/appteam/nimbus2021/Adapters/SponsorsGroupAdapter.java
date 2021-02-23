package com.nith.appteam.nimbus2021.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2021.Models.Sponsor;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SponsorsGroupAdapter extends RecyclerView.Adapter<SponsorsGroupAdapter.SponsorsGroupViewHolder> {

    List<String> spon_title;
    Activity mActivity;

    public SponsorsGroupAdapter(List<String> spon_title,Activity mActivity) {
        this.spon_title = spon_title;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public SponsorsGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spon_list,parent,false);
        return  new SponsorsGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorsGroupViewHolder holder, final int position) {
        holder.t1.setText(spon_title.get(position));
        final ArrayList<Sponsor> spn = new ArrayList<>();
        final SponsorsAdapter sponsorsAdapter = new SponsorsAdapter(spn,mActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        holder.r1.setLayoutManager(layoutManager);
        holder.r1.setAdapter(sponsorsAdapter);

        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Url + "/members/sponsors", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("data",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        if(jsonArray.getJSONObject(i).get("position").equals(spon_title.get(position))){
                            spn.add(new Sponsor(String.valueOf(jsonObject.get("name")),String.valueOf(jsonObject.get("image")),String.valueOf(jsonObject.get("link"))));
                        }

                    }
                    sponsorsAdapter.notifyDataSetChanged();
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

    @Override
    public int getItemCount() {
        return spon_title.size();
    }

    public class SponsorsGroupViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        RecyclerView r1;
        public SponsorsGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.parent_item_title);
            r1 = itemView.findViewById(R.id.child_recyclerview);

        }
    }
}
