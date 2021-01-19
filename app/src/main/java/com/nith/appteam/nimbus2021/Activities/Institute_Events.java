package com.nith.appteam.nimbus2021.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nith.appteam.nimbus2021.Adapters.EventIRecyclerViewAdapter;
import com.nith.appteam.nimbus2021.Models.instituteEvent;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;
import com.nith.appteam.nimbus2021.Utils.PrefsIevent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Institute_Events extends AppCompatActivity {
    private RecyclerView recyclerViewIEVE;
    private EventIRecyclerViewAdapter eventIRecyclerViewAdapter;
    private RequestQueue requestQueueEVEI;
    private List<instituteEvent> eventIlist;
    private ProgressBar loadWall;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private EditText num;
    private SharedPreferences.Editor editor;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_institutional_events);

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
        FloatingActionButton fab = findViewById(R.id.fabI);
        if (sharedPref.getString("phoneNumber", "").equals("+918219341697") || sharedPref.getString("phoneNumber", "").equals("+917982107070") || sharedPref.getString("phoneNumber", "").equals("+918572027705") || sharedPref.getString("phoneNumber", "").equals("+918959747704") || sharedPref.getString("phoneNumber", "").equals("+919340453051")) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Institute_Events.this, Add_I_Events.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.ease_in, R.anim.ease_out);

                }
            });
        } else {
            fab.setVisibility(View.GONE);
        }
        requestQueueEVEI = Volley.newRequestQueue(this);
        loadWall = findViewById(R.id.loadwallEventI);
        recyclerViewIEVE = findViewById(R.id.recyclerViewEVEI);
        recyclerViewIEVE.setHasFixedSize(true);
        recyclerViewIEVE.setLayoutManager(new LinearLayoutManager(this));
        eventIlist = new ArrayList<>();
        PrefsIevent prefsIevent = new PrefsIevent(this);
        String search = prefsIevent.getSearch();
        eventIlist = getEventI(search);
        //   talkRecyclerViewAdapter=new TalkRecyclerViewAdapter(this,talkList);
        // recyclerView.setAdapter(talkRecyclerViewAdapter);
        //     talkRecyclerViewAdapter.notifyDataSetChanged();

    }

    public List<instituteEvent> getEventI(String searchTerm)//all info returned from api
    {
        loadWall.setVisibility(View.VISIBLE);
        eventIlist.clear();
        eventIRecyclerViewAdapter = new EventIRecyclerViewAdapter(this, eventIlist);
        recyclerViewIEVE.setAdapter(eventIRecyclerViewAdapter);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Constant.Url + searchTerm, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loadWall.setVisibility(View.GONE);
                Log.d("Response", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject talkObj = response.getJSONObject(i);
                        instituteEvent Ievent = new instituteEvent();
//                        talk.setName("Aysuh
//                        KAusnldjhlkhfkllnewlfnlwenflkjewlkjfljwhekjksdjkjhkuhkjhkjsdhlehlkjhalhldhll");
//                        talk.setVenue("LEcture
//                        aHAljewnfkljcnkjhfewkkjhefkjwhkfjwkejfhkwehkfhkwejnfkll");
                        //    Ievent.setRegURL("https://github.com/appteam-nith/nimbus2019");
//                        talk.setInfo("HE is
//                        veryhlhfeldijvoikbfewkjbkfjwkejfkjwejeovijoeijvoeijdvoijeoijeovjioejioeijvovjoeidjvlkdsnlkvn jsndoviejoiejvoljkdlkjvoeijvoiejovijdokjdeoivjolj");
//                        talk.setDate("19 2022002345453453453450 2");
                        Ievent.setNameIEVE(talkObj.getString("name"));
                        Ievent.setDateIEVE("On: " + talkObj.getString("date"));
                        Ievent.setImageIEVE(talkObj.getString("image"));
                        Ievent.setInfoIEVE(talkObj.getString("info"));
                        Ievent.setRegURLIEVE(talkObj.getString("regUrl"));
                        Ievent.setVenueIEVE("Venue: " + talkObj.getString("venue"));
                        Ievent.setAbstractIEVE(talkObj.getString("abstract"));
                        // Log.d("Talk",talk.getName());
                        //Log.d("date",talk.getDate());
                        eventIlist.add(Ievent);
                        eventIRecyclerViewAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());

            }
        });
        requestQueueEVEI.add(jsonArrayRequest);


        return eventIlist;
    }


}


