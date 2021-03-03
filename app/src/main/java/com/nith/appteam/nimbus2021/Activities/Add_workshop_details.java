package com.nith.appteam.nimbus2021.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.nith.appteam.nimbus2021.Models.WorkshopModel;
import com.nith.appteam.nimbus2021.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

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
        imgDetWor = findViewById(R.id.workshopImageIDDetail);
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
              Picasso.with(getApplicationContext()).load(workshopModel.getImageWor().replace("http://", "https://"))
              .into(imgDetWor, new Callback() {
                  @Override
                  public void onSuccess() {

                  }

                  @Override
                  public void onError() {
                    imgDetWor.setImageResource(R.drawable.nimbus_logo);
                  }
              });
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


