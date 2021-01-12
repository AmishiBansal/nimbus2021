package com.nith.appteam.nimbus2020.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.Constant;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_I_Events extends AppCompatActivity {
    private EditText nameAddI, infoAddI, venueAddI, regUrlAddI, pdfAddI;
            private TextView dateAddI,timeAddD;
    private CircleImageView imgI;
    private Button addButtonI;
    private RequestQueue requestQueueI;
    private int PICK_PHOTO_CODE = 100;
    private byte[] byteArray;
    private String imageUrl = "";
    private Bitmap bmp, img;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Uri photoUri;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__i__events);
        nameAddI = findViewById(R.id.NameAddI);
        pdfAddI = findViewById(R.id.pdfAddI);
        infoAddI = findViewById(R.id.infoAddI);
        venueAddI = findViewById(R.id.venueAddI);
        dateAddI = findViewById(R.id.dateAddI);
        imgI = findViewById(R.id.addImgI);
        regUrlAddI = findViewById(R.id.addregUrlI);
        addButtonI = findViewById(R.id.AddButtonI);
        timeAddD = findViewById(R.id.timeAddD);

        addButtonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String data="{"+"name"+ nameAdd.getText().toString()+","+"info"+ infoAdd.getText().toString()+","+"venue"+venueAdd.getText().toString()
//                        +","+"date"+dateAdd.getText().toString()+","+"image"+imageAdd.getText().toString()+","+"regUrl"+regUrlAdd.getText().toString()+"}";

                if (photoUri != null) {
                    Bitmap bitmap = ((BitmapDrawable) imgI.getDrawable()).getBitmap();
                    getImageUrl(bitmap);
                } else {
                    progressDialog = new ProgressDialog(Add_I_Events.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Posting...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    imageUrl = String.valueOf(R.string.defaultImage);
                    AddDetailsI();
                }


            }
        });
        imgI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_PHOTO_CODE);
                }
            }
        });

        dateAddI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_I_Events.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateAddI.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        timeAddD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_I_Events.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeAddD.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            photoUri = data.getData();
            Bitmap selectedImage = null;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, bs);
            byteArray = bs.toByteArray();
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            img = getResizedBitmap(bmp, 300);
//          pass = encodeTobase64(img);
            imgI.setImageBitmap(img);

        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void getImageUrl(Bitmap bitmap) {
        progressDialog = new ProgressDialog(Add_I_Events.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Posting...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byteArray = stream.toByteArray();
        String requestId = MediaManager.get().upload(byteArray).constrain(TimeWindow.immediate())
                .unsigned("x2gjlxpr")
                .option("connect_timeout", 10000)
                .option("read_timeout", 10000)
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        imageUrl = String.valueOf(resultData.get("url"));
                        AddDetailsI();
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.i("HELLO", "JIJIJ");
//                      finish();
                        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                        Toast.makeText(Add_I_Events.this, "Upload Failed" + error.getDescription() + " requestId" + requestId, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        // your code here
                    }
                })
                .dispatch(Add_I_Events.this);

    }


    private void AddDetailsI() {
        //final String savedata=data;
        requestQueueI = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Constant.Url + "instituteEvents", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    Log.i("Tag", "Success");
                    Toast.makeText(getApplicationContext(), object.toString(), Toast.LENGTH_SHORT).show();
                    if (object.getString("message").equals("success")) {

                        nameAddI.setText("");
                        regUrlAddI.setText("");
                        Picasso.with(getApplicationContext()).load(R.drawable.fui_ic_anonymous_white_24dp).into(imgI);
                        venueAddI.setText("");
                        dateAddI.setText("");
                        pdfAddI.setText("");
                        infoAddI.setText("");
                        timeAddD.setText("");

                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_SHORT).show();


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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nameAddI.getText().toString());
                params.put("info", infoAddI.getText().toString());
                params.put("venue", venueAddI.getText().toString());
                params.put("date", dateAddI.getText().toString());
                params.put("abstract", pdfAddI.getText().toString());
                params.put("image", imageUrl);
                // params.put("image",imageAdd.getText().toString());
                params.put("regUrl", regUrlAddI.getText().toString());
                return params;
            }
        };

        requestQueueI.add(request);

    }
}

