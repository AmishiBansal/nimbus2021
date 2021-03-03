package com.nith.appteam.nimbus2021.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2021.Adapters.SliderAdapter;
import com.nith.appteam.nimbus2021.Models.GallerySliderItem;
import com.nith.appteam.nimbus2021.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubsGallery extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private List<GallerySliderItem> SliderItemsList;
    private Handler sliderHandler = new Handler();
    private String uid;
    private SliderAdapter adapter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_gallery);
        viewPager2 = findViewById(R.id.viewpager);
        progressBar = findViewById(R.id.progress_bar);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        uid = sharedPreferences.getString("firebaseUid", null);

        final int club_id = getIntent().getIntExtra("club_id",0);
        Log.e("clubId",""+club_id);
        SliderItemsList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(ClubsGallery.this);
        StringRequest request = new StringRequest(Request.Method.GET, getString(R.string.baseUrl) + "/gallery?dept_id="+club_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("responseimg",response);
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        String url = array.getJSONObject(i).get("image").toString();
                        SliderItemsList.add(new GallerySliderItem(url));
                        Log.e("url", String.valueOf(SliderItemsList.get(i).getImage()));
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error",e.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError",error.toString());
                progressBar.setVisibility(View.GONE);
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("error", message);
                    finish();
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("error", message);
                    finish();
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("error", message);
                    finish();
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("error", message);
                    finish();
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    Log.e("error", message);
                    finish();
                }
                else
                {
                    Log.e("VOLLEY", error.toString());
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    finish();
                }
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

        };
        queue.add(request);

        Log.e("size2",""+SliderItemsList.size());
        adapter = new SliderAdapter(SliderItemsList,viewPager2,ClubsGallery.this);
        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });
        viewPager2.setPageTransformer(transformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,2000);
            }
        });


    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
}