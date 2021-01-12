package com.nith.appteam.nimbus2020.Utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class Constant {
    public static final String Url = "http://api.festnimbus.com/";

    public static final String GEOFENCE_ID = "TACME";
    public static final float GEOFENCE_RADIUS_IN_METERS = 500;

    /**
     * Map for storing information about tacme in the dubai.
     */
    public static final HashMap<String, LatLng> AREA_LANDMARKS = new HashMap<String, LatLng>();

    static {
        // Tacme
        AREA_LANDMARKS.put(GEOFENCE_ID, new LatLng(31.7091659, 76.5237565));
    }
}
