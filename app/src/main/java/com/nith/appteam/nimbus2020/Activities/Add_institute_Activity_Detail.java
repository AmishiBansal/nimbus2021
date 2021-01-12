package com.nith.appteam.nimbus2020.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus2020.Models.instituteEvent;
import com.nith.appteam.nimbus2020.R;

import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Add_institute_Activity_Detail extends AppCompatActivity {
    private instituteEvent instituteEvent;
    private TextView nameDetEventsI, infoDetEventsI, venueDetEventsI, dateDetEventsI;
    private CardView regDetEventsI, absEventI;
    private ImageView imgDetEventsI;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private TextView abstractDet;
    private WebView webView;
    private String myPdfUrl;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_institute___detail);

        webView=findViewById(R.id.webView);

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

        instituteEvent = (instituteEvent) getIntent().getSerializableExtra("instituteEvents");
        setUpUI();
        //TextView abstractTV = findViewById(R.id.abstractIDDetEventsD);
        //abstractTV.setText(instituteEvent.getAbstractIEVE());
        getMovieDetails();
        regDetEventsI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oprnURLExh(instituteEvent.getRegURLIEVE());
            }
        });

        myPdfUrl = instituteEvent.getAbstractIEVE();

        Log.e("before", instituteEvent.getAbstractIEVE());

        if (myPdfUrl != null && !myPdfUrl.isEmpty()) {
            String substr = myPdfUrl.substring(myPdfUrl.length() - 4);
            if (!substr.equals("view")) myPdfUrl = myPdfUrl + "/view";

            Log.e("after", myPdfUrl);
        }

        CardView abstractButton = findViewById(R.id.abstractIDDetEventsD);
        abstractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    webView.setVisibility(View.VISIBLE);

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view,
                                WebResourceRequest request) {
                            view.loadUrl(myPdfUrl);
                            return true;
                        }
                    });
                    webView.loadUrl(myPdfUrl);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

//        absEventI.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialogBox();
//            }
//        });


    }

//    private void openDialogBox() {
//        alertDialogBuilder = new AlertDialog.Builder(this);
//        View view = getLayoutInflater().inflate(R.layout.abstract_dialog_box, null);
//        abstractDet = view.findViewById(R.id.abstract_dialogText);
//        abstractDet.setText(instituteEvent.getAbstractIEVE());
//        Button submit = view.findViewById(R.id.CloseButton);
//        alertDialogBuilder.setView(view);
//        dialog = alertDialogBuilder.create();
//        dialog.show();
//
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "exit", Toast.LENGTH_SHORT).show();
//
//                dialog.dismiss();
//            }
//        });
//    }

    private void oprnURLExh(String regURL) {
        Intent intent = new Intent(Add_institute_Activity_Detail.this, Web.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", regURL);
        getApplicationContext().startActivity(intent);

    }

    private void getMovieDetails() {
        if (instituteEvent != null) {
            nameDetEventsI.setText(instituteEvent.getNameIEVE());
            infoDetEventsI.setText(instituteEvent.getInfoIEVE());
            venueDetEventsI.setText(instituteEvent.getVenueIEVE());
            dateDetEventsI.setText(instituteEvent.getDateIEVE());
            //  tupeWo.setText(workshopModel.getTypeWor());
            // Picasso.with(getApplicationContext()).load(instituteEvent.getImageIEVE())
            // .placeholder(android.R.drawable.ic_btn_speak_now).into(imgDetEventsI);
        }


    }

    private void setUpUI() {

        nameDetEventsI = findViewById(R.id.NameIDDetEventsI);
        infoDetEventsI = findViewById(R.id.InfoIDDetEventsI);
        venueDetEventsI = findViewById(R.id.VenueIDDetEventsI);
        dateDetEventsI = findViewById(R.id.DateDetEventsI);
        regDetEventsI = findViewById(R.id.registerDetEventsI);
        //  absEventI = findViewById(R.id.abstractIEventsDet);
        // imgDetEventsI = findViewById(R.id.ImgDetEventsI);
    }

    @Override
    public void onBackPressed() {
        webView.setVisibility(View.GONE);
        super.onBackPressed();
    }
}
