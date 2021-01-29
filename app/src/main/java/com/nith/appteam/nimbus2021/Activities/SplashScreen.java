package com.nith.appteam.nimbus2021.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.nith.appteam.nimbus2021.R;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

public class SplashScreen extends AppCompatActivity {

    RichPathView nimbus,nimbus1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Test
//        Intent intent = new Intent(SplashScreen.this, Exhhibition.class);
//        startActivity(intent);
//        finish();
        //Test end

        ImageView t_n, t_k, e_n, e_k;
        Animation animation, animation1, animation2, animation3, anim;

        nimbus = findViewById(R.id.nimbus);
        nimbus1 = findViewById(R.id.nimbus1);

        t_n = findViewById(R.id.t_n);
        t_k = findViewById(R.id.t_k);
        e_n = findViewById(R.id.e_n);
        e_k = findViewById(R.id.e_k);

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_anim);

        nimbus.startAnimation(anim);
        nimbus1.startAnimation(anim);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fast_anim);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slow_anim);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fast_anim_h);
        animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slow_anim_h);

        e_n.startAnimation(animation);
        e_k.startAnimation(animation1);
        t_n.startAnimation(animation2);
        t_k.startAnimation(animation3);

        animation();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        }, 2500);
    }

    public void animation() {
        final RichPath part1 = nimbus.findRichPathByName("tiny_right");
        final RichPath part2 = nimbus.findRichPathByName("tiny_left");
//        final RichPath part3 = nimbus.findRichPathByName("small_right");
//        final RichPath part4 = nimbus.findRichPathByName("small_left");
        final RichPath part5 = nimbus.findRichPathByName("big_right");
        final RichPath part6 = nimbus.findRichPathByName("big_left");

        RichPathAnimator
                .animate(part1)
                .trimPathOffset(0, 1.0f)
                .andAnimate(part2)
                .trimPathOffset(0, 1.0f)
//                .andAnimate(part3)
//                .trimPathOffset(0, 1.0f)
//                .andAnimate(part4)
//                .trimPathOffset(0, 1.0f)
                .andAnimate(part5)
                .trimPathOffset(0, 1.0f)
                .andAnimate(part6)
                .trimPathOffset(0, 1.0f)
                .durationSet(2000)
                .repeatModeSet(RichPathAnimator.RESTART)
                .repeatCountSet(RichPathAnimator.INFINITE)
                .interpolatorSet(new AccelerateDecelerateInterpolator())
                .start();
    }

}
