package com.nith.appteam.nimbus2020.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyService {

    IResult mResultCallback = null;
    Context mContext;

    public VolleyService(IResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }


    public void postJsonDataVolley(final String requestType, String url, JSONObject sendObj) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(url, sendObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (mResultCallback != null) {
                                mResultCallback.notifySuccess(requestType, response, null);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mResultCallback != null) {
                        mResultCallback.notifyError(requestType, error);
                    }
                }
            });

            queue.add(jsonObj);

        } catch (Exception e) {

        }
    }

    public void getJsonObjectDataVolley(final String requestType, String url) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (mResultCallback != null) {
                            mResultCallback.notifySuccess(requestType, response, null);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mResultCallback != null) {
                            mResultCallback.notifyError(requestType, error);
                        }
                    }
                });
        queue.add(jsonObjectRequest);

// Access the RequestQueue through your singleton class.

    }

    public void getJsonArrayDataVolley(final String requestType, String url) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        if (mResultCallback != null) {
                            mResultCallback.notifySuccess(requestType, null, jsonArray);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (mResultCallback != null) {
                            mResultCallback.notifyError(requestType, volleyError);
                        }
                    }
                });

        queue.add(request);
    }
}