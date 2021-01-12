package com.nith.appteam.nimbus2020.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QRScanner extends AppCompatActivity implements View.OnClickListener {

    private Button scanBtn;
    private SharedPreferences sharedPrefs;


    //private LinearLayout llSearch;
    private RequestQueue queue;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qrscanner);
        sharedPrefs = getSharedPreferences("app", MODE_PRIVATE);
//        llSearch.setVisibility(View.GONE);


        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setPrompt("Scan a barcode or QRcode");

        integrator.setOrientationLocked(false);

        integrator.initiateScan();
        scanBtn = findViewById(R.id.scan_button);


        //    llSearch = (LinearLayout) findViewById(R.id.llSearch);

        scanBtn.setOnClickListener(this);


    }

    public void onClick(View v) {

        //llSearch.setVisibility(View.GONE);

        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setPrompt("Scan a barcode or QRcode");

        integrator.setOrientationLocked(false);

        integrator.initiateScan();

//        Use this for more customization

//        IntentIntegrator integrator = new IntentIntegrator(this);

//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);

//        integrator.setPrompt("Scan a barcode");

//        integrator.setCameraId(0);  // Use a specific camera of the device

//        integrator.setBeepEnabled(false);

//        integrator.setBarcodeImageEnabled(true);

//        integrator.initiateScan();

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {

            if (result.getContents() == null) {

                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                //          llSearch.setVisibility(View.GONE);


                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();


            } else {

                Log.e("result", result.getContents());
                queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.POST, Constant.Url + "/user/qrcode", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("response", response);
                            JSONObject object = new JSONObject(response);
                            Boolean flag = true;
//                            Log.i("Tag", "Success");
//                            Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
//                            if (object.getString("message").equals("success")) {
//                                //getPoints();
//                            }
                            String error = object.getString("errorCode");
                            Log.e("error", error + "");
                            if (error.equals("1")) {
                                new AlertDialog.Builder(QRScanner.this)
                                        .setTitle("Invalid QR")
                                        .setMessage("You have scanned an invalid QR")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            } else if (error.equals("2")) {
                                flag = false;
                                new AlertDialog.Builder(QRScanner.this)
                                        .setTitle("QR has been already sent")
                                        .setMessage("Try another QR")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();

                            } else if (error.equals("0")) {
                                Log.i("Tag", "Success");
                                Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Score has been added. Check in profile", Toast.LENGTH_SHORT).show();
                                getPoints();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error" + e,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                        Toast.makeText(getApplication(), "Error:" + error, Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        HashMap<String, String> map = new HashMap<>();
                        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
                        String token = sharedPreferences.getString("token", null);

                        map.put("access-token", token);//enter 5e3d804ac8ce62598c00c5c4" for dummy check
                        return map;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("code", result.getContents());
                        return params;
                    }
                };

                queue.add(request);


            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);

        }

    }

    public void getPoints() {
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.baseUrl) + "/user/points", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(QRScanner.this, "LINK UPLOADED", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String normalPoints = jsonObject.getString("userPoints");
                    editor.putString("normalPoints", normalPoints);
                    Log.e("normal points", normalPoints);
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QRScanner.this, error.toString() + error.getCause(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("access-token", sharedPrefs.getString("token", ""));
                return headers;
            }
        };
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(QRScanner.this, MainActivity.class);
        startActivity(newIntent);
        finish();
        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);


    }
}