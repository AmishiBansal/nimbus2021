package com.nith.appteam.nimbus2020.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus2020.Models.TalkModel;
import com.nith.appteam.nimbus2020.R;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Add_talk_details extends AppCompatActivity {
    private TalkModel talkModel;
    private TextView nameDet, infoDet, venueDet, dateDet;
    private CardView regDet;
    private ImageView imgDet;
    private String talkID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_talk_details);

        ImageView round_big = findViewById(R.id.e_n);
        ImageView round_small = findViewById(R.id.e_k);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fast_anim_v1);
        Random rand = new Random();
        animation.setDuration(rand.nextInt(2000) + 2000);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slow_anim_v1);
        animation1.setDuration(rand.nextInt(2000) + 2000);
        round_big.startAnimation(animation1);
        round_small.startAnimation(animation);

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

        talkModel = (TalkModel) getIntent().getSerializableExtra("talk");
//        talkID=talkModel.getIdTalk();
        setUpUI();
        getMovieDetails();
        regDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oprnURL(talkModel.getRegURL());
            }
        });


    }

    private void oprnURL(String regURL) {
        Intent intent = new Intent(Add_talk_details.this, Web.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", regURL);
        getApplicationContext().startActivity(intent);
    }

    private void getMovieDetails() {
        if (talkModel != null) {
            nameDet.setText(talkModel.getName());
            infoDet.setText(talkModel.getInfo());
            venueDet.setText(talkModel.getVenue());
            dateDet.setText(talkModel.getDate());
            // Picasso.with(getApplicationContext()).load(talkModel.getImage()).placeholder
            // (android.R.drawable.ic_btn_speak_now).into(imgDet);
        }


    }

    private void setUpUI() {

        nameDet = findViewById(R.id.speakerNameIDDet);
        infoDet = findViewById(R.id.speakerInfoIDDet);
        venueDet = findViewById(R.id.speakerVenueIDDet);
        dateDet = findViewById(R.id.SpeakerDateDet);
        regDet = findViewById(R.id.registerDet);
//        imgDet = findViewById(R.id.talk_ImgDet);
    }
}
