package com.nith.appteam.nimbus2021.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2021.Adapters.Events_D_RecyclerViewAdapter;
import com.nith.appteam.nimbus2021.Adapters.GalleryAdapter;
import com.nith.appteam.nimbus2021.Adapters.OurTeamAdapter;
import com.nith.appteam.nimbus2021.Models.ClubDetail;
import com.nith.appteam.nimbus2021.Models.TeamMember;
import com.nith.appteam.nimbus2021.Models.departmentEvent;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gallery extends Fragment {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Context context;
    private RequestQueue requestQueueClubs;
    GalleryAdapter galleryAdapter;
    List<ClubDetail> clubList = new ArrayList<>();
    Map<String,Integer> mapClubs=new HashMap<String,Integer>();
    public Gallery(Context context) {this.context = context; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.gallery_recycler_view);

        TextView quote1 = rootView.findViewById(R.id.quote1);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            quote1.setText(Html.fromHtml("<p>\"THESE GUYS COME FROM LEGEND \uD83D\uDEF8, <font color=\"#2fc0d1\">CAPTAIN</font><br>THEY'RE BASICALLY, <font color=\"#2fc0d1\">GODS</font> ✨.\" <small><i><font color=\"#888888\"> ~ BLACK WIDOW</font></i></small></p>", Html.FROM_HTML_MODE_COMPACT));
//        }
//        else {
//            quote1.setText(Html.fromHtml("<p>\"THESE GUYS COME FROM LEGEND \uD83D\uDEF8, <font color=\"#2fc0d1\">CAPTAIN</font><br>THEY'RE BASICALLY, <font color=\"#2fc0d1\">GODS</font> ✨.\" <small><i><font color=\"#888888\"> ~ BLACK WIDOW</font></i></small></p>"));
//        }
        mapClubs.put("app_team",12);
        mapClubs.put("c_helix",1);
        mapClubs.put("designocrats",2);
        mapClubs.put("hermetica",3);
        mapClubs.put("medextrous",4);
        mapClubs.put("meta_morph",5);
        mapClubs.put("nimbus",6);
        mapClubs.put("ojas",7);
        mapClubs.put("pixonoids",8);
        mapClubs.put("team_.exe",9);
        mapClubs.put("vibhav",10);
        mapClubs.put("web_team",11);
        sharedPref = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        requestQueueClubs = Volley.newRequestQueue(context);

//        ClubDetail clubDetail = new ClubDetail("App Team","https://avatars.githubusercontent.com/u/17087131?s=200&v=4",12);
//        clubList.add(clubDetail);
//        clubList.add(new ClubDetail("Team Exe","https://teamexe.in/images/logo.png",9));
//        clubList.add(new ClubDetail("Hermetica","https://scontent.fslv1-2.fna.fbcdn.net/v/t1.0-9/84570307_2600774863478544_2688631209260482560_n.jpg?_nc_cat=101&ccb=3&_nc_sid=85a577&_nc_ohc=yKo1sioroQYAX8HJ2vg&_nc_ht=scontent.fslv1-2.fna&oh=b16c5ee401f1fa74bacac1dbb145eb26&oe=605389DD",3));
//        clubList.add(new ClubDetail("App Team","https://avatars.githubusercontent.com/u/17087131?s=200&v=4",12));

        galleryAdapter = new GalleryAdapter(clubList, getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(galleryAdapter);
        getClubs(sharedPref.getString("firebaseUid","NULL"));
        return rootView;
    }

    private void getClubs(final String UID)//all info returned from api
    {
        clubList.clear();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Constant.Url + "departments/", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject talkObj = response.getJSONObject(i);
                        int club_id = 0;
                        if(mapClubs.get(talkObj.getString("name").toLowerCase())!=null)
                            club_id = mapClubs.get(talkObj.getString("name").toLowerCase());
                        ClubDetail cDetail = new ClubDetail(talkObj.getString("name"),talkObj.getString("image").replace("http","https"),club_id);
                        clubList.add(cDetail);
                        galleryAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("Error", error.getMessage());

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                Log.d("TAG", "getHeaders: "+UID);
                headers.put("Authorization", UID);
                return headers;
            }
        };
        requestQueueClubs.add(jsonArrayRequest);
    }
}
