package com.nith.appteam.nimbus2020.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nith.appteam.nimbus2020.R;

public class UI extends AppCompatActivity {
    TextView dashboardTab, sponsorTab, teamTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

    }
}
