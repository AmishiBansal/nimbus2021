package com.nith.appteam.nimbus2021.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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
import com.nith.appteam.nimbus2021.Adapters.EventIRecyclerViewAdapter;
import com.nith.appteam.nimbus2021.Models.instituteEvent;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;
import com.nith.appteam.nimbus2021.Utils.PrefsIevent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstituteEvents extends Fragment {
    Context context;
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

    public InstituteEvents(Activity context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institutional_events, container, false);

//        TextView back;
//        back = rootView.findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((Activity) context).finish();
//                ((Activity) context).overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
//            }
//        });

        sharedPref = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        FloatingActionButton fab = rootView.findViewById(R.id.fabI);

            fab.setVisibility(View.GONE);
        requestQueueEVEI = Volley.newRequestQueue(context);
        loadWall = rootView.findViewById(R.id.loadwallEventI);
        recyclerViewIEVE = rootView.findViewById(R.id.recyclerViewEVEI);
        recyclerViewIEVE.setHasFixedSize(true);
        recyclerViewIEVE.setLayoutManager(new LinearLayoutManager(context));
        eventIlist = new ArrayList<>();
        PrefsIevent prefsIevent = new PrefsIevent(((Activity) context));
        String search = prefsIevent.getSearch();
        eventIlist = getEventI(search,sharedPref.getString("firebaseUid","NULL"));
        //   talkRecyclerViewAdapter=new TalkRecyclerViewAdapter(this,talkList);
        // recyclerView.setAdapter(talkRecyclerViewAdapter);
        //     talkRecyclerViewAdapter.notifyDataSetChanged();

        return rootView;
    }

    public List<instituteEvent> getEventI(String searchTerm, final String UID)//all info returned from api
    {
        loadWall.setVisibility(View.VISIBLE);
        eventIlist.clear();
        eventIRecyclerViewAdapter = new EventIRecyclerViewAdapter(context, eventIlist);
        recyclerViewIEVE.setAdapter(eventIRecyclerViewAdapter);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Constant.Url + "events/?type="+ "institutional", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                        Ievent.setDateIEVE(talkObj.getString("start"));
                        Ievent.setImageIEVE(talkObj.getString("image"));
                        Ievent.setInfoIEVE(talkObj.getString("info"));
                        Ievent.setRegURLIEVE(talkObj.getString("regURL"));
                        Ievent.setVenueIEVE("Venue: " + talkObj.getString("venue"));
                        Ievent.setAbstractIEVE(talkObj.getString("abstract"));
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

                        Date myDate = null;
                        Date d2 = null;
                        Date date = new Date(System.currentTimeMillis());
                        try {
                            myDate = dateFormat.parse(talkObj.getString("end"));
                            d2 = dateFormat.parse(talkObj.getString("end"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("date", String.valueOf(myDate.before(date)));

                        if(!talkObj.getString("end").equals("null") && !myDate.before(date)){
                            eventIlist.add(Ievent);
                        eventIRecyclerViewAdapter.notifyDataSetChanged();
                        }else if(talkObj.getString("end").equals("null")){
                            eventIlist.add(Ievent);
                            eventIRecyclerViewAdapter.notifyDataSetChanged();
                        }

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
        requestQueueEVEI.add(jsonArrayRequest);


        return eventIlist;
    }

}
