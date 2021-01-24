package com.nith.appteam.nimbus2021.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nith.appteam.nimbus2021.R;

public class Videocall_Joining extends AppCompatActivity {

    TextView videoJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videocall__joining);

        videoJoin = findViewById(R.id.video_join);

        videoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Videocall_Joining.this,VideoCallActivity.class));
            }
        });

    }
}