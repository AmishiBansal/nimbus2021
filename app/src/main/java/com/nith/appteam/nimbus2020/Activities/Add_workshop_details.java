package com.nith.appteam.nimbus2020.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus2020.Models.WorkshopModel;
import com.nith.appteam.nimbus2020.R;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Add_workshop_details extends AppCompatActivity {
    private WorkshopModel workshopModel;
    private TextView nameDetWor, infoDetWor, venueDetWor, dateDetWor, tupeWor;
    private CardView regDetWOr;
    private ImageView imgDetWor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop_details);

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

        workshopModel = (WorkshopModel) getIntent().getSerializableExtra("workshop");
        setUpUI();
        getMovieDetails();
        regDetWOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oprnURLWor(workshopModel.getUrlWor());
            }
        });


    }

    private void oprnURLWor(String regURL) {
        Intent intent = new Intent(Add_workshop_details.this, Web.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", regURL);
        getApplicationContext().startActivity(intent);
    }

    private void getMovieDetails() {
        if (workshopModel != null) {
            nameDetWor.setText(workshopModel.getNameWor());
            infoDetWor.setText(workshopModel.getInfoWor());
            venueDetWor.setText(workshopModel.getVenueWor());
            dateDetWor.setText(workshopModel.getDateWor());
            // tupeWor.setText(workshopModel.getTypeWor());
            //  Picasso.with(getApplicationContext()).load(workshopModel.getImageWor())
            //  .placeholder(android.R.drawable.ic_btn_speak_now).into(imgDetWor);
        }


    }

    private void setUpUI() {

        nameDetWor = findViewById(R.id.NameIDDetWor);
        infoDetWor = findViewById(R.id.InfoIDDetWor);
        venueDetWor = findViewById(R.id.VenueIDDetWor);
        dateDetWor = findViewById(R.id.DateDetWor);
        regDetWOr = findViewById(R.id.registerDetWor);
        //imgDetWor = findViewById(R.id.ImgDetWor);
        //  tupeWor = findViewById(R.id.workshopTypeIDDet);
    }
}


