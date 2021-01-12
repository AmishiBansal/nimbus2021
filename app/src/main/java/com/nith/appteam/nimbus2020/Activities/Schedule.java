package com.nith.appteam.nimbus2020.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.nith.appteam.nimbus2020.Adapters.ScheduleRecyclerViewAdapter;
import com.nith.appteam.nimbus2020.Models.FormatDate;
import com.nith.appteam.nimbus2020.Models.ScheduleModel;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.Constant;
import com.nith.appteam.nimbus2020.Utils.GradientTextView;
import com.nith.appteam.nimbus2020.Utils.PrefsSchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends AppCompatActivity {
    private RecyclerView recyclerViewSch;
    private List<ScheduleModel> scheduleModelList;
    private ScheduleModel scheduleModel;
    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;
    private RequestQueue requestQueueSch;
    private ProgressBar loadWall;
    private String day;
    private String date;
    private GradientTextView day_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        day_title = findViewById(R.id.day_title);
        TextView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

//        scheduleModel = (ScheduleModel) getIntent().getSerializableExtra("Day1");
        day = getIntent().getExtras().getString("Day");
        if (day.equals("1")) {
            date = "2020-02-01";
            day_title.setText("Day 1");
        } else if (day.equals("2")) {
            date = "2020-02-02";
            day_title.setText("Day 2");
        } else if (day.equals("3")) {
            date = "2020-02-03";
            day_title.setText("Day 3");
        }
        loadWall = findViewById(R.id.loadwallSch);
        requestQueueSch = Volley.newRequestQueue(this);
        recyclerViewSch = findViewById(R.id.recyclerViewSchedule);
        recyclerViewSch.setHasFixedSize(true);
        recyclerViewSch.setLayoutManager(new LinearLayoutManager(this));
        scheduleModelList = new ArrayList<>();
        PrefsSchedule prefsschedule = new PrefsSchedule(this);
        String search = prefsschedule.getSearch();
        scheduleModelList = getSchedule(search);
        //   talkRecyclerViewAdapter=new TalkRecyclerViewAdapter(this,talkList);
        // recyclerView.setAdapter(talkRecyclerViewAdapter);
        //     talkRecyclerViewAdapter.notifyDataSetChanged();

    }

    public List<ScheduleModel> getSchedule(String searchTerm)//all info returned from api
    {
        loadWall.setVisibility(View.VISIBLE);
        scheduleModelList.clear();
        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(this, scheduleModelList);
        recyclerViewSch.setAdapter(scheduleRecyclerViewAdapter);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Constant.Url + searchTerm, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loadWall.setVisibility(View.GONE);
                Log.d("Response", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject schObj = response.getJSONObject(i);
                        ScheduleModel sch = new ScheduleModel();
//                        talk.setName("Aysuh KAusnldjhlkhfkllnewlfnlwenflkjewlkjfljwhekjksdjkjhkuhkjhkjsdhlehlkjhalhldhll");
//                        talk.setVenue("LEcture aHAljewnfkljcnkjhfewkkjhefkjwhkfjwkejfhkwehkfhkwejnfkll");
                        //talk.setRegURL("https://github.com/appteam-nith/nimbus2019");
//                        talk.setInfo("HE is veryhlhfeldijvoikbfewkjbkfjwkejfkjwejeovijoeijvoeijdvoijeoijeovjioejioeijvovjoeidjvlkdsnlkvn jsndoviejoiejvoljkdlkjvoeijvoiejovijdokjdeoivjolj");
//                        talk.setDate("19 2022002345453453453450 2");
                        String requiredDate = (schObj.getString("time").substring(0, 10));
                        if (requiredDate.equals(date)) {
                            sch.setNameSch(schObj.getString("name"));
                            FormatDate date = new FormatDate(schObj.getString("time"));
                            sch.setTimeSch(date.getFormattedDate());
                            //sch.setS(talkObj.getString("info"));
                            // sch.setRegURL(  talkObj.getString("regUrl"));
                            sch.setVenueSch(schObj.getString("venue"));
                            sch.setDeptSch(schObj.getString("departmentName"));
                            // Log.d("Talk",talk.getName());
                            scheduleModelList.add(sch);
                        }
                        scheduleRecyclerViewAdapter.notifyDataSetChanged();

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
        requestQueueSch.add(jsonArrayRequest);


        return scheduleModelList;
    }

}
