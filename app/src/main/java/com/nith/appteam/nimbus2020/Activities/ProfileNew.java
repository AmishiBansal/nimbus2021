package com.nith.appteam.nimbus2020.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileNew extends AppCompatActivity {

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private EditText name, rollno, phoneNumber, college;
    private Button submitProfile;
    private ProgressBar progressBar;
    private CircleImageView profilePic;
    private int PICK_PHOTO_CODE = 100;
    private byte[] byteArray;
    private String imageUrl;
    private Bitmap bmp, img;
    private RadioButton caYes, caNo;
    private ImageView uploadPic;
    private Uri photoUri;
    private LinearLayout ca;
    private boolean editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_new);

        TextView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        sharedPrefs = getSharedPreferences("app", MODE_PRIVATE);
        editor = sharedPrefs.edit();
        getUI();
        if (sharedPrefs.getBoolean("profileStatus", false)) {
            ca.setVisibility(View.INVISIBLE);
            rollno.setEnabled(false);
        }
        Intent intent = getIntent();
        if (intent.hasExtra("editProfile")) {
            editProfile = getIntent().getExtras().getBoolean("editProfile");
            String image = sharedPrefs.getString("profileImage", null);
            if (editProfile) {
                Picasso.with(ProfileNew.this)
                        .load(image)
                        .placeholder(R.drawable.default_profile_pic)
                        .into(profilePic);
                rollno.setText(sharedPrefs.getString("rollno", ""));
                name.setText(sharedPrefs.getString("name", ""));
                college.setText(sharedPrefs.getString("college", ""));
            }
        }
        phoneNumber.setText(sharedPrefs.getString("phoneNumber", ""));
        phoneNumber.setEnabled(false);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_PHOTO_CODE);
                }
            }
        });
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePic.performClick();
            }
        });
        caYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("campusAmbassador", true);
                editor.commit();
            }
        });
        caNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("campusAmbassador", false);
                editor.commit();
            }
        });

        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (photoUri != null) {
                    Bitmap bitmap = ((BitmapDrawable) profilePic.getDrawable()).getBitmap();
                    getImageUrl(bitmap);
                } else if (imageUrl == null) {
                    imageUrl = String.valueOf(R.string.defaultImageUrl);
                    submitProfile();
                }
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
            profilePic.setImageBitmap(img);

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
                        submitProfile();
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.e("error", "cloudinary image upload error");
//                      finish();
                        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                        Toast.makeText(ProfileNew.this, "Image Upload Failed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        // your code here
                    }
                })
                .dispatch(ProfileNew.this);

    }

    private void submitProfile() {
        if (!name.getText().toString().isEmpty() && !rollno.getText().toString().isEmpty() &&
                !phoneNumber.getText().toString().isEmpty() && !college.getText().toString().isEmpty()) {
            Intent intent = getIntent();
            editProfile = false;
            if (intent.hasExtra("editProfile")) {
                editProfile = getIntent().getExtras().getBoolean("editProfile");
                Log.e("status", "" + editProfile);
            }
            RequestQueue queue = Volley.newRequestQueue(ProfileNew.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.baseUrl) + "/auth/signup", new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int errorCode = 1;
                    String token;
                    final JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response);
                        errorCode = (int) jsonObject.get("errorCode");
                        if (!editProfile) {
                            token = (String) jsonObject.get("token");
                            editor.putString("token", token);
                            editor.apply();
                        }
//                        Toast.makeText(ProfileNew.this, "token" + sharedPrefs.getString("token", ""), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                            Toast.makeText(ProfileNew.this, response, Toast.LENGTH_LONG).show();
                    if (errorCode == 0) {
//                                Toast.makeText(ProfileNew.this, "error code" + errorCode, Toast.LENGTH_SHORT).show();
                        editor.putString("name", name.getText().toString());
                        editor.putString("rollno", rollno.getText().toString());
                        editor.putString("college", college.getText().toString());
                        editor.putString("profileImage", imageUrl);
                        editor.putBoolean("profileStatus", true);
                        editor.commit();
                        progressBar.setVisibility(View.GONE);
                        if (editProfile) {
                            finish();
                            overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                        } else {
                            Intent i = new Intent(ProfileNew.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                            overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                            overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                        }
                    } else {
                        Toast.makeText(ProfileNew.this, "Unknown error" + response, Toast.LENGTH_SHORT).show();
                        Log.e("error", response);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileNew.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name.getText().toString());
                    params.put("rollNumber", rollno.getText().toString());
                    params.put("college", college.getText().toString());
                    //params.put("campusAmbassador", false);
                    params.put("image", imageUrl);
                    params.put("campusAmbassador", "" + sharedPrefs.getBoolean("campusAmbassador", false));
                    params.put("phone", phoneNumber.getText().toString());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("access-token", "" + sharedPrefs.getString("token", ""));
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        } else
            Toast.makeText(ProfileNew.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

    }

    private void getUI() {
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.mobile);
        rollno = findViewById(R.id.rollno);
        college = findViewById(R.id.college);
        submitProfile = findViewById(R.id.submit_profile);
        profilePic = findViewById(R.id.profile_pic);
        progressBar = findViewById(R.id.profile_progress);
        caNo = findViewById(R.id.ca_no);
        caYes = findViewById(R.id.ca_yes);
        ca = findViewById(R.id.ca);
        uploadPic = findViewById(R.id.profile_pic_button);
    }
}