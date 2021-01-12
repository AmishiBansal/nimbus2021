package com.nith.appteam.nimbus2020.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nith.appteam.nimbus2020.Activities.CampusAmbassador;
import com.nith.appteam.nimbus2020.Activities.Event_Choose;
import com.nith.appteam.nimbus2020.Activities.Exhhibition;
import com.nith.appteam.nimbus2020.Activities.QuizMainActivity;
import com.nith.appteam.nimbus2020.Activities.Talks;
import com.nith.appteam.nimbus2020.Activities.Workshops;
import com.nith.appteam.nimbus2020.Activities.contributorsActivity;
import com.nith.appteam.nimbus2020.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;


public class Dashboard extends Fragment {
    Activity context;
    Animation animation, animation1, animation2, animation3;
    private HtmlTextView quote1;
    private HtmlTextView quote2, event_text, campus_text, developers_text;
    private CardView quiz_card, workshop_card, exhibition_card, talk_card;
    private ImageView t_n, t_k, e_n, e_k;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    public Dashboard(Activity context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        quote1 = rootView.findViewById(R.id.quote1);
        quote2 = rootView.findViewById(R.id.quote2);
        event_text = rootView.findViewById(R.id.event_text);
        campus_text = rootView.findViewById(R.id.campus_text);
        quiz_card = rootView.findViewById(R.id.quiz_card);
        workshop_card = rootView.findViewById(R.id.workshop_card);
        exhibition_card = rootView.findViewById(R.id.exhibition_card);
        talk_card = rootView.findViewById(R.id.talk_card);
        developers_text = rootView.findViewById(R.id.developers_text);

        t_n = rootView.findViewById(R.id.t_n);
        t_k = rootView.findViewById(R.id.t_k);
        e_n = rootView.findViewById(R.id.e_n);
        e_k = rootView.findViewById(R.id.e_k);

        animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.fast_anim);
        animation1 = AnimationUtils.loadAnimation(context.getApplicationContext(),
                R.anim.slow_anim);
        animation2 = AnimationUtils.loadAnimation(context.getApplicationContext(),
                R.anim.fast_anim_v);
        animation3 = AnimationUtils.loadAnimation(context.getApplicationContext(),
                R.anim.slow_anim_v);

        e_n.startAnimation(animation);
        e_k.startAnimation(animation1);
        t_n.startAnimation(animation2);
        t_k.startAnimation(animation3);

        quote2.setHtml(
                "<p>\"HOW YOU <font color=\"#2fc0d1\">CODIN'</font> \uD83D\uDCBB ?!\" "
                        + "<small><i><font color=\"#888888\"> ~ <strike>JOEY</strike> "
                        + "NIMBUS</font></i></small></p>");
        developers_text.setHtml("<p>\"MEET THE<font color=\"#2fc0d1\"> DEVELOPERS</font> \uD83D\uDCBB\"</p>");

        campus_text.setHtml(
                "<p>\"ARE YOU A <font color=\"#2fc0d1\">CAMPUS AMBASSADOR</font> \uD83D\uDEA9"
                        + " ?\"</p>");


        event_text.setHtml(
                "<p>\"SNEAK PEAK \uD83D\uDD76 OUR <font color=\"#2fc0d1\">EVENTS</font> "
                        + "!\"</p>");


        quote1.setHtml(
                "<p>\"I AM <strike>IRON MAN</strike> <font color=\"#2fc0d1\">SEMBLANCE</font>"
                        + " \uD83D\uDE80 !\" <small><i><font color=\"#888888\"> ~ "
                        + "NIMBUS</font></i></small></p>");

        quiz_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), QuizMainActivity.class);
                context.startActivity(i);
                context.overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        workshop_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Workshops.class);
                startActivity(i);
                context.overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        talk_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Talks.class);
                startActivity(i);
                context.overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        exhibition_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Exhhibition.class);
                startActivity(i);
                context.overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        event_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Event_Choose.class);
                startActivity(i);
                context.overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        campus_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CampusAmbassador.class);
                startActivity(i);
                context.overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
        developers_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), contributorsActivity.class);
                startActivity(i);
            }
        });
        quote1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder = new AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.dialog_semblance, null);
                alertDialogBuilder.setView(view);
                dialog = alertDialogBuilder.create();
                dialog.show();
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
            }
        });
        return rootView;
    }
}
