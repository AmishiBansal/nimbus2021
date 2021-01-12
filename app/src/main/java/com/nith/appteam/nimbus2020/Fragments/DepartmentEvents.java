package com.nith.appteam.nimbus2020.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nith.appteam.nimbus2020.Activities.Add_D_Events;
import com.nith.appteam.nimbus2020.Adapters.Events_D_RecyclerViewAdapter;
import com.nith.appteam.nimbus2020.Models.departmentEvent;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.Constant;
import com.nith.appteam.nimbus2020.Utils.PrefsDevents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DepartmentEvents extends Fragment {
    private Context context;
    private RecyclerView recyclerViewDEVE;
    private Events_D_RecyclerViewAdapter events_d_recyclerViewAdapter;
    private RequestQueue requestQueueEVED;
    private List<departmentEvent> eventlistD;
    private ProgressBar loadWall;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private EditText num;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public DepartmentEvents(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_departmental_events, container, false);
//        TextView back;
//        back = rootView.findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((Activity) context).finish();
//                ((Activity) context).overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
//                ((Activity) context).overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
//            }
//        });

        sharedPref = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        FloatingActionButton fab = rootView.findViewById(R.id.fabD);
        Log.e("phone", sharedPref.getString("phoneNumber", ""));
        if (sharedPref.getString("phoneNumber", "").equals("+918219341697") || sharedPref.getString("phoneNumber", "").equals("+917982107070") || sharedPref.getString("phoneNumber", "").equals("+918572027705") || sharedPref.getString("phoneNumber", "").equals("+918959747704") || sharedPref.getString("phoneNumber", "").equals("+918572027705") || sharedPref.getString("phoneNumber", "").equals("+919340453051")) {
            fab.setVisibility(View.VISIBLE);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent((context), Add_D_Events.class);
                    startActivity(intent);


                }
            });
        } else {
            fab.setVisibility(View.INVISIBLE);
        }
        requestQueueEVED = Volley.newRequestQueue(context);
        loadWall = rootView.findViewById(R.id.loadwalldpt);
        recyclerViewDEVE = rootView.findViewById(R.id.recyclerViewEVED);
        recyclerViewDEVE.setHasFixedSize(true);
        recyclerViewDEVE.setLayoutManager(new LinearLayoutManager(context));
        eventlistD = new ArrayList<>();
        PrefsDevents prefsDevent = new PrefsDevents(((Activity) context));
        String search = prefsDevent.getSearch();
        eventlistD = getEventD(search);
        //   talkRecyclerViewAdapter=new TalkRecyclerViewAdapter(this,talkList);
        // recyclerView.setAdapter(talkRecyclerViewAdapter);
        //     talkRecyclerViewAdapter.notifyDataSetChanged();

        return rootView;
    }

    public List<departmentEvent> getEventD(String searchTerm)//all info returned from api
    {
        loadWall.setVisibility(View.VISIBLE);
        eventlistD.clear();
        events_d_recyclerViewAdapter = new Events_D_RecyclerViewAdapter(context, eventlistD);
        recyclerViewDEVE.setAdapter(events_d_recyclerViewAdapter);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Constant.Url + searchTerm, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loadWall.setVisibility(View.GONE);
                Log.d("Response", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject talkObj = response.getJSONObject(i);
                        departmentEvent eventD = new departmentEvent();
//                        talk.setName("Aysuh
//                        KAusnldjhlkhfkllnewlfnlwenflkjewlkjfljwhekjksdjkjhkuhkjhkjsdhlehlkjhalhldhll");
//                        talk.setVenue("LEcture
//                        aHAljewnfkljcnkjhfewkkjhefkjwhkfjwkejfhkwehkfhkwejnfkll");
                        //    Ievent.setRegURL("https://github.com/appteam-nith/nimbus2019");
//                        talk.setInfo("HE is
//                        veryhlhfeldijvoikbfewkjbkfjwkejfkjwejeovijoeijvoeijdvoijeoijeovjioejioeijvovjoeidjvlkdsnlkvn jsndoviejoiejvoljkdlkjvoeijvoiejovijdokjdeoivjolj");

//                        talk.setDate("19 2022002345453453453450 2");
                        eventD.setNameDEVE(talkObj.getString("name"));
                        eventD.setDateDEVE(talkObj.getString("date"));
                        eventD.setImageDEVE(talkObj.getString("image"));
                        eventD.setInfoDEVE(talkObj.getString("info"));

                        eventD.setRegURLDEVE(talkObj.getString("regUrl"));
                        eventD.setVenueDEVE("Venue: " + talkObj.getString("venue"));
                        eventD.setAbstractDEVE(talkObj.getString("abstract"));


                        // Log.d("Talk",talk.getName());
                        //Log.d("date",talk.getDate());
                        eventlistD.add(eventD);
                        events_d_recyclerViewAdapter.notifyDataSetChanged();

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
        requestQueueEVED.add(jsonArrayRequest);


        return eventlistD;
    }
}
