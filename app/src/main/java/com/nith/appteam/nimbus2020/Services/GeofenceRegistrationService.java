package com.nith.appteam.nimbus2020.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.nith.appteam.nimbus2020.Activities.MainActivity;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class GeofenceRegistrationService extends IntentService {

    private static final String TAG = "GeoIntentService";

    private static long timeStart, timeEnd;

    public GeofenceRegistrationService() {
        super(TAG);
    }

    // Handle errors
    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";

        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "GeofencingEvent error " + geofencingEvent.getErrorCode());
        } else {
            int transaction = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(
                    Constant.GEOFENCE_ID)) {
                Log.d(TAG, "You are inside Tacme");
                //timeStart = System.currentTimeMillis();
                Log.e("start", "" + timeStart);
            } else {
                Log.d(TAG, "You are outside Tacme");
            }
            String geofenceTransitionDetails = getGeofenceTrasitionDetails(transaction, geofences);

            sendNotification(geofenceTransitionDetails);
        }
    }

    // Create a detail message with Geofences received
    private String getGeofenceTrasitionDetails(int geoFenceTransition,
                                               List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesList.add(geofence.getRequestId());
        }

        String status = null;
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            status = "Entering ";
            timeStart = System.currentTimeMillis();
        } else if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            status = "Exiting ";
            timeEnd = System.currentTimeMillis();

            Log.e("time", "" + timeEnd);
            Log.e("timestart", "" + timeStart);

            Handler mainHandler = new Handler(Looper.getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(GeofenceRegistrationService.this, "dsvs" + (timeEnd
                            - timeStart) / 1000, Toast.LENGTH_SHORT).show();
                } // This is your code
            };
            mainHandler.post(myRunnable);
            Log.e("stayed", "" + (timeEnd
                    - timeStart) / 1000);

        }
        return status + TextUtils.join(", ", triggeringGeofencesList);
    }

    // Send a notification
    private void sendNotification(String msg) {
        Log.i(TAG, "sendNotification: " + msg);

        // Intent to start the main Activity
        Intent notificationIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Creating and sending Notification
        NotificationManager notificatioMng =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificatioMng.notify(
                0,
                createNotification(msg, notificationPendingIntent));
    }

    // Create a notification
    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        Log.e("not", "fication");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                "xfbdvvx");
        notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText("Geofence Notification!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(
                        Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE
                                | Notification.DEFAULT_SOUND)
                .setAutoCancel(false);
        return notificationBuilder.build();
    }
}
