package com.nith.appteam.nimbus2021.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nith.appteam.nimbus2021.Adapters.WorkshopRecyclerViewAdapter;
import com.nith.appteam.nimbus2021.Models.WorkshopModel;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;
import com.nith.appteam.nimbus2021.Utils.PrefsWorkshop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Workshops extends AppCompatActivity {
    ProgressBar loadWall;
    private RecyclerView recyclerViewwor;
    private List<WorkshopModel> workshopList;
    private WorkshopRecyclerViewAdapter workshopRecyclerViewAdapter;
    private RequestQueue requestQueuework;


    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private EditText num;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);

        TextView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        sharedPref = getSharedPreferences("app", MODE_PRIVATE);
        editor = sharedPref.edit();
        user = FirebaseAuth.getInstance().getCurrentUser();
        requestQueuework = Volley.newRequestQueue(this);

        FloatingActionButton fabwo = findViewById(R.id.fabworkshop);


            fabwo.setVisibility(View.GONE);
        loadWall = findViewById(R.id.loadwallWorkshop);
        recyclerViewwor = findViewById(R.id.recyclerViewWorkshop);
        recyclerViewwor.setHasFixedSize(true);
        recyclerViewwor.setLayoutManager(new LinearLayoutManager(this));
        workshopList = new ArrayList<>();
        PrefsWorkshop prefsWorkshop = new PrefsWorkshop(this);
        String search = prefsWorkshop.getSearch();
        workshopList = getWorkshop(search,sharedPref.getString("firebaseUid","NULL"));

    }

    public List<WorkshopModel> getWorkshop(String searchTerm,final String UID)//all info returned from api
    {
        loadWall.setVisibility(View.VISIBLE);
        workshopList.clear();
        workshopRecyclerViewAdapter = new WorkshopRecyclerViewAdapter(this, workshopList);
        recyclerViewwor.setAdapter(workshopRecyclerViewAdapter);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Constant.Url + "events/?type="+ "workshop", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                loadWall.setVisibility(View.GONE);
//                Log.d("Response",response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject workshopObj = response.getJSONObject(i);
                        WorkshopModel workshop = new WorkshopModel();
//                        talk.setName("Aysuh KAusnldjhlkhfkllnewlfnlwenflkjewlkjfljwhekjksdjkjhkuhkjhkjsdhlehlkjhalhldhll");
//                        talk.setVenue("LEcture aHAljewnfkljcnkjhfewkkjhefkjwhkfjwkejfhkwehkfhkwejnfkll");
                        //      workshop.setUrlWor("https://github.com/appteam-nith/nimbus2019");
//                        talk.setInfo("HE is veryhlhfeldijvoikbfewkjbkfjwkejfkjwejeovijoeijvoeijdvoijeoijeovjioejioeijvovjoeidjvlkdsnlkvn jsndoviejoiejvoljkdlkjvoeijvoiejovijdokjdeoivjolj");
//                        talk.setDate("19 2022002345453453453450 2");
                        workshop.setNameWor(workshopObj.getString("name"));
                        workshop.setDateWor(workshopObj.getString("start"));
                        workshop.setImageWor(workshopObj.getString("image"));
                        workshop.setInfoWor(workshopObj.getString("info"));
                        workshop.setUrlWor(workshopObj.getString("regURL"));
                        workshop.setVenueWor("Venue: " + workshopObj.getString("venue"));
                        workshop.setTypeWor("Type:" + workshopObj.getString("Type"));
                        // Log.d("Talk",talk.getName());
//                       Log.d("date",talk.getDate());
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

                        Date myDate = null;
                        Date d2 = null;
                        Date date = new Date(System.currentTimeMillis());
                        try {
                            myDate = dateFormat.parse(workshopObj.getString("end"));
                            d2 = dateFormat.parse(workshopObj.getString("end"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("date", String.valueOf(myDate.before(date)));

                        if(!workshopObj.getString("end").equals("null") && !myDate.before(date))
                        {
                            workshopList.add(workshop);
                            workshopRecyclerViewAdapter.notifyDataSetChanged();
                        }else if(workshopObj.getString("end").equals("null")){
                            workshopList.add(workshop);
                            workshopRecyclerViewAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
           //     Log.d("Error", error.getMessage());

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
        requestQueuework.add(jsonArrayRequest);


        return workshopList;
    }


}
