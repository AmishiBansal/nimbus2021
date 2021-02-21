package com.nith.appteam.nimbus2021.Activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.volley.VolleyError;
import com.nith.appteam.nimbus2021.Adapters.DiscoverAdapter;
import com.nith.appteam.nimbus2021.Fragments.Dashboard;
import com.nith.appteam.nimbus2021.Fragments.Gallery;
import com.nith.appteam.nimbus2021.Fragments.Sponsor;
import com.nith.appteam.nimbus2021.Models.DiscoverModel;
import com.nith.appteam.nimbus2021.R;
import com.nith.appteam.nimbus2021.Utils.IResult;
import com.nith.appteam.nimbus2021.Utils.StartSnapHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RecyclerView mRecyclerView;
    DiscoverAdapter mDiscoverAdapter;
    List<DiscoverModel> mDiscoverModelList;
    TextView dashboardTab, sponsorTab, teamTab, galleryTab;
    Typeface psbi, psi;
    private IResult mResultCallback;
    private Button quiz, sponsor, profile, campusA, workshops, talks, events, qr, exhibition, schedule, contributors, coreTeam;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private boolean isMonitoring = false;
    private PendingIntent pendingIntent;
    private ImageView profileImage;
    private ImageView scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        profileImage = findViewById(R.id.profileImage);
//        scanner = findViewById(R.id.scanner);

        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        String image = sharedPreferences.getString("profileImage", null);
        Log.e("image", "any" + image);
        Picasso.with(this).load(image).resize(30, 30).placeholder(R.drawable.fui_ic_anonymous_white_24dp).into(profileImage);

        getUI();


        sharedPref = getSharedPreferences("app", MODE_PRIVATE);
        editor = sharedPref.edit();

        // Checking whether user has logged in or not
        if (sharedPref.getBoolean("loginStatus", false) == false) {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
        }

        //Checking whether user has created profile or not
        else if (sharedPref.getBoolean("profileStatus", false) == false) {
            Intent i = new Intent(this, ProfileNew.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
        }

        psbi = ResourcesCompat.getFont(this, R.font.psbitalic);
        psi = ResourcesCompat.getFont(this, R.font.psitalic);

        setClickListener();

        mRecyclerView = findViewById(R.id.recyclerView);
        mDiscoverModelList = new ArrayList<>();

//        getData();

        mDiscoverAdapter = new DiscoverAdapter(mDiscoverModelList, this);
        mDiscoverModelList.clear();
        mDiscoverModelList.add(new DiscoverModel("Day 1", "", " ", ""));
        mDiscoverModelList.add(new DiscoverModel("Day 2", "", " ", ""));
        mDiscoverModelList.add(new DiscoverModel("Day 3", "", " ", ""));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mDiscoverAdapter);
        mDiscoverAdapter.notifyDataSetChanged();
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

    }

    public void setClickListener() {

//        scanner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, QRScanner.class);
//                startActivity(intent);
//            }
//        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ProfileMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        coreTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CoreTeamActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileMain.class);
                startActivity(i);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
//        campusA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, CampusAmbassador.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
//            }
//        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuizMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        sponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UI.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        workshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Workshops.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        talks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Talks.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Event_Choose.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schedule_Day.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        exhibition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Exhhibition.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        // QR Code scanner
//        qr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, QRScanner.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
//            }
//        });

        dashboardTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardTab.setTypeface(psbi);
                sponsorTab.setTypeface(psi);
                teamTab.setTypeface(psi);
                galleryTab.setTypeface(psi);

                dashboardTab.setTextColor(getResources().getColor(R.color.black));
                sponsorTab.setTextColor(getResources().getColor(R.color.lightGray));
                teamTab.setTextColor(getResources().getColor(R.color.lightGray));
                galleryTab.setTextColor(getResources().getColor(R.color.lightGray));

                Dashboard dashboard = new Dashboard();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_holder, dashboard)
                        .commit();
            }
        });

        sponsorTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardTab.setTypeface(psi);
                sponsorTab.setTypeface(psbi);
                teamTab.setTypeface(psi);
                galleryTab.setTypeface(psi);

                sponsorTab.setTextColor(getResources().getColor(R.color.black));
                dashboardTab.setTextColor(getResources().getColor(R.color.lightGray));
                teamTab.setTextColor(getResources().getColor(R.color.lightGray));
                galleryTab.setTextColor(getResources().getColor(R.color.lightGray));
                Sponsor sponsorFragment = new Sponsor();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_holder, sponsorFragment)
                        .commit();
            }
        });
//        teamTab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                teamTab.setTypeface(psbi);
//                sponsorTab.setTypeface(psi);
//                dashboardTab.setTypeface(psi);
//
//                teamTab.setTextColor(getResources().getColor(R.color.black));
//                sponsorTab.setTextColor(getResources().getColor(R.color.lightGray));
//                dashboardTab.setTextColor(getResources().getColor(R.color.lightGray));
//
//                OurTeam ourTeam = new OurTeam(MainActivity.this);
//                FragmentManager fm = getSupportFragmentManager();
//                fm.beginTransaction()
//                        .replace(R.id.fragment_holder, ourTeam)
//                        .commit();
//            }
//        });

                galleryTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryTab.setTypeface(psbi);
                sponsorTab.setTypeface(psi);
                dashboardTab.setTypeface(psi);

                galleryTab.setTextColor(getResources().getColor(R.color.black));
                sponsorTab.setTextColor(getResources().getColor(R.color.lightGray));
                dashboardTab.setTextColor(getResources().getColor(R.color.lightGray));

                Gallery gallery = new Gallery(MainActivity.this);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_holder, gallery)
                        .commit();
            }
        });

        dashboardTab.performClick();
    }

    private void getData() {

        //  loadwall.setVisibility(View.VISIBLE);
//
//        initVolleyCallback();
//
//        final VolleyService mVolleyService = new VolleyService(mResultCallback, this);
//
//        mVolleyService.getJsonArrayDataVolley("GETDISCOVER",
//                getString(R.string.baseUrl) + "/discover");
    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            JSONObject obj;

            @Override
            public void notifySuccess(String requestType, JSONObject response,
                                      JSONArray jsonArray) {


                if (response != null) {
                    //loadwall.setVisibility(View.GONE);

                    try {
                        obj = response;
                        String name = obj.getString("name");
                        String time = obj.getString("time");
                        String location = obj.getString("location");
                        String image = obj.getString("image_src");
                        mDiscoverModelList.add(new DiscoverModel(name, location, time, image));
                        mDiscoverAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.e("Hellcatt", response.toString());

                } else {
                    Log.e("zHell", jsonArray.toString());

                    //  loadwall.setVisibility(View.GONE);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            obj = jsonArray.getJSONObject(i);
                            String name = obj.getString("name");
                            String time = obj.getString("time");
                            String location = obj.getString("location");
                            String image = obj.getString("image_src");
                            mDiscoverModelList.add(new DiscoverModel(name, location, time, image));
                            mDiscoverAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    mDiscoverAdapter.notifyDataSetChanged();

                }
            }


            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.i("error", error.toString());
            }
        };

    }

    private void getUI() {
        dashboardTab = findViewById(R.id.dashboard_tab);
        sponsorTab = findViewById(R.id.sponsor_tab);
        galleryTab = findViewById(R.id.gallery_tab);
        teamTab = findViewById(R.id.team_tab);
        quiz = findViewById(R.id.quiz);
        sponsor = findViewById(R.id.sponsors);
        talks = findViewById(R.id.talks);
        workshops = findViewById(R.id.workshops);
        events = findViewById(R.id.events);
//        campusA = findViewById(R.id.ca);
        profile = findViewById(R.id.profile);
//        qr = findViewById(R.id.qr);
        exhibition = findViewById(R.id.exhibition);
        schedule = findViewById(R.id.schedule);
        coreTeam = findViewById(R.id.coreTeam);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}