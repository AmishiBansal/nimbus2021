package com.nith.appteam.nimbus2020.Activities;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class Nimbus2020 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Required for cloudinary
        Map config = new HashMap();
        config.put("cloud_name", "duutozrvz");
        MediaManager.init(this, config);
    }
}
