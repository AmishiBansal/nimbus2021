package com.nith.appteam.nimbus2020.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.nith.appteam.nimbus2020.Adapters.CoreTeamAdapter;
import com.nith.appteam.nimbus2020.Models.CoreTeamModel;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.IResult;
import com.nith.appteam.nimbus2020.Utils.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CoreTeamActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar loadwall;
    CoreTeamAdapter mCoreTeamAdapter;
    List<CoreTeamModel> mCoreTeamModelList;

    private IResult mResultCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_team);

        TextView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        recyclerView = findViewById(R.id.coreTeamRecyclerView);
        loadwall = findViewById(R.id.loadwall);
        mCoreTeamModelList = new ArrayList<>();
        getData();
        mCoreTeamAdapter = new CoreTeamAdapter(mCoreTeamModelList, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mCoreTeamAdapter);

    }


    private void getData() {
        mCoreTeamModelList.clear();
        loadwall.setVisibility(View.VISIBLE);

        initVolleyCallback();

        final VolleyService mVolleyService = new VolleyService(mResultCallback, this);

        mVolleyService.getJsonArrayDataVolley("GETCORETEAMS",
                getString(R.string.baseUrl) + "/coreTeam");

    }


    void initVolleyCallback() {
        mResultCallback = new IResult() {
            JSONObject obj;

            @Override
            public void notifySuccess(String requestType, JSONObject response,
                                      JSONArray jsonArray) {


                if (response != null) {
                    loadwall.setVisibility(View.GONE);

                    try {
                        obj = response;
                        String name = obj.getString("name");
                        String position = obj.getString("position");
                        String image = getResources().getString(R.string.defaultImageUrl);
                        if (obj.has("image")) image = obj.getString("image");
                        mCoreTeamModelList.add(new CoreTeamModel(name, image, position));
                        mCoreTeamAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.e("Hellcatt", response.toString());

                } else {
                    Log.e("zHell", jsonArray.toString());

                    loadwall.setVisibility(View.GONE);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            obj = jsonArray.getJSONObject(i);
                            String name = obj.getString("name");
                            String position = obj.getString("position");
                            String image = getResources().getString(R.string.defaultImageUrl);
                            if (obj.has("image")) image = obj.getString("image");
                            mCoreTeamModelList.add(new CoreTeamModel(name, image, position));
                            mCoreTeamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    mCoreTeamAdapter.notifyDataSetChanged();

                }
            }


            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.i("error", error.toString());
            }
        };

    }
}
