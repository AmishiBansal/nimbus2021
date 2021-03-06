package com.nith.appteam.nimbus2021.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
import com.nith.appteam.nimbus2021.Adapters.ExhibitionRecyclerViewAdapter;
import com.nith.appteam.nimbus2021.Models.ExhibitionModel;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.Constant;
import com.nith.appteam.nimbus2021.Utils.PrefsExhibition;

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

public class Exhhibition extends AppCompatActivity {

    private RecyclerView recyclerViewExhib;
    private List<ExhibitionModel> exhibitionList;
    private ExhibitionRecyclerViewAdapter exhibitionRecyclerViewAdapter;
    private RequestQueue requestQueueExh;
    private ProgressBar loadWall;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private EditText num;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhhibition);

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
        requestQueueExh = Volley.newRequestQueue(this);
        FloatingActionButton fab = findViewById(R.id.fabExh);
            fab.setVisibility(View.GONE);
        loadWall = findViewById(R.id.loadwallExh);
        recyclerViewExhib = findViewById(R.id.recyclerViewExhibition);
        recyclerViewExhib.setHasFixedSize(true);
        recyclerViewExhib.setLayoutManager(new LinearLayoutManager(this));
        exhibitionList = new ArrayList<>();
        PrefsExhibition prefsExhibition = new PrefsExhibition(this);
        String search = prefsExhibition.getSearch();
        exhibitionList = getExhibition(search,sharedPref.getString("firebaseUid","NULL"));
//        talkRecyclerViewAdapter=new TalkRecyclerViewAdapter(this,talkList);
//        recyclerView.setAdapter(talkRecyclerViewAdapter);

    }

    public List<ExhibitionModel> getExhibition(String searchTerm,final String UID)//all info returned from api
    {
        loadWall.setVisibility(View.VISIBLE);
        exhibitionList.clear();
        exhibitionRecyclerViewAdapter = new ExhibitionRecyclerViewAdapter(this, exhibitionList);
        recyclerViewExhib.setAdapter(exhibitionRecyclerViewAdapter);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Constant.Url + "events/?type="+ "exhibition", null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                loadWall.setVisibility(View.GONE);
//                Log.d("Response",response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject exhObj = response.getJSONObject(i);
                        ExhibitionModel exhibition = new ExhibitionModel();
//                        talk.setName("APP on TALkoinghg iguhedbfuhcgwu");
//                        talk.setVenue("LEcture aHAljewnfkljcnkjhfewkkjhefkjwhkfjwkejfhkwehkfhkwejnfkll");
//                        exhibition.setRegURLExh("https://google.com");
//                        talk.setInfo("HE is veryhlhfeldijvoikbfewkjbkfjwkejfkjwejeovijoeijvoeijdvoijeoijeovjioejioeijvovjoeidjvlkdsnlkvn jsndoviejoiejvoljkdlkjvoeijvoiejovijdokjdeoivjolj");
//                        talk.setDate("19 2022002345453453453450 2");
                        exhibition.setNameExh(exhObj.getString("name"));
                        exhibition.setDateExh(exhObj.getString("start"));
//                        Log.e("dateeeeeeee", exhObj.getString("date"));
                        exhibition.setImageExh(exhObj.getString("image"));
                        //exhibition.setImageExh("https://www.google
                        // .com/search?q=images&rlz=1C1CHBF_enIN859IN859&sxsrf
                        // =ACYBGNS3W0FihD42Fyr2cRgnJ33k2Ihysw:1580381365046&source=lnms&tbm=isch
                        // &sa=X&ved=2ahUKEwj94_2uk6vnAhU1zTgGHUz7DEAQ_AUoAXoECA4QAw&biw=1536&bih
                        // =754#imgrc=_2JirDBiGzi3lM:");
                        exhibition.setInfoExh(exhObj.getString("info"));
                        exhibition.setRegURLExh(  exhObj.getString("regURL"));
                        exhibition.setVenueExh("Venue: " + exhObj.getString("venue"));
//                         Log.d("Talk",talk.getName());
//                       Log.d("date",talk.getDate());
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

                        Date myDate = null;
                        Date d2 = null;
                        Date date = new Date(System.currentTimeMillis());
                        try {
                            myDate = dateFormat.parse(exhObj.getString("end"));
                            d2 = dateFormat.parse(exhObj.getString("end"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.e("date", String.valueOf(myDate.before(date)));
                        if(!exhObj.getString("end").equals("null") && !myDate.before(date)){
                            exhibitionList.add(exhibition);
                            exhibitionRecyclerViewAdapter.notifyDataSetChanged();
                        }else if(exhObj.getString("end").equals("null")){
                            exhibitionList.add(exhibition);
                            exhibitionRecyclerViewAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Log.d("Error", error.getMessage());

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                Log.d("TAG", "getHeaders: "+UID);
                headers.put("Authorization", UID);
                return headers;
            }
        };
        requestQueueExh.add(jsonArrayRequest);


        return exhibitionList;
    }

    private void runLayoutAnim(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


}