package com.nith.appteam.nimbus2021.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.nith.appteam.nimbus2021.Adapters.SliderAdapter;
import com.nith.appteam.nimbus2021.Models.GallerySliderItem;
import com.nith.appteam.nimbus2021.R;

import java.util.ArrayList;
import java.util.List;

public class ClubsGallery extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private List<GallerySliderItem> gallerySliderItems;
    private Handler sliderHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_gallery);
        viewPager2 = findViewById(R.id.viewpager);
        Log.e("clubId",""+getIntent().getIntExtra("club_id",0));
        gallerySliderItems = new ArrayList<>();
        gallerySliderItems.add(new GallerySliderItem("https://festnimbus.com/assets/gallery_image_%20(3).jpg"));
        gallerySliderItems.add(new GallerySliderItem("https://festnimbus.com/assets/gallery_image_%20(26).jpg"));
        gallerySliderItems.add(new GallerySliderItem("https://festnimbus.com/assets/gallery_image_%20(18).jpg"));

        viewPager2.setAdapter(new SliderAdapter(gallerySliderItems,viewPager2,this));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });
        viewPager2.setPageTransformer(transformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,2000);
            }
        });
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
}