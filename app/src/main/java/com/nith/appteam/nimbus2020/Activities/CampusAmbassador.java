package com.nith.appteam.nimbus2020.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.nith.appteam.nimbus2020.Fragments.Wall;
import com.nith.appteam.nimbus2020.R;

public class CampusAmbassador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Wall wall = new Wall();
        setContentView(R.layout.activity_campus_ambassador);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_holder, wall)
                .commit();
    }
}
