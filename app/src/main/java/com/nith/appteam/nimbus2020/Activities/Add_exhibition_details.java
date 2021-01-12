package com.nith.appteam.nimbus2020.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus2020.Models.ExhibitionModel;
import com.nith.appteam.nimbus2020.R;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Add_exhibition_details extends AppCompatActivity {
    private ExhibitionModel exhibitionModel;
    private TextView nameDetExh, infoDetExh, venueDetExh, dateDetExh;
    private CardView regDetExh;
    private ImageView imgDetExh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibition_details);

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

        exhibitionModel = (ExhibitionModel) getIntent().getSerializableExtra("exhibition");
        setUpUI();
        getMovieDetails();
        regDetExh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oprnURLExh(exhibitionModel.getRegURLExh());
            }
        });


    }

    private void oprnURLExh(String regURL) {
        Intent intent = new Intent(Add_exhibition_details.this, Web.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", regURL);
        startActivity(intent);
        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
    }

    private void getMovieDetails() {
        if (exhibitionModel != null) {
            nameDetExh.setText(exhibitionModel.getNameExh());
            infoDetExh.setText(exhibitionModel.getInfoExh());
            venueDetExh.setText(exhibitionModel.getVenueExh());
            dateDetExh.setText(exhibitionModel.getDateExh());
            //  tupeWo.setText(workshopModel.getTypeWor());
            // Picasso.with(getApplicationContext()).load(exhibitionModel.getImageExh())
            // .placeholder(android.R.drawable.ic_btn_speak_now).into(imgDetExh);
        }


    }

    private void setUpUI() {

        nameDetExh = findViewById(R.id.NameIDDetExh);
        infoDetExh = findViewById(R.id.InfoIDDetExh);
        venueDetExh = findViewById(R.id.VenueIDDetExh);
        dateDetExh = findViewById(R.id.DateDetExh);
        regDetExh = findViewById(R.id.registerDetExh);
        // imgDetExh = findViewById(R.id.ImgDetExh);
        //tupeWor=findViewById(R.id.workshopTypeIDDet);
    }
}

