package com.nith.appteam.nimbus2021.Activities;

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
import com.nith.appteam.nimbus2021.R;
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
    private EditText username,first,last,email,phoneNumber,college,rollno;
    private Button submitProfile;
    private ProgressBar progressBar;
    private CircleImageView profilePic;
    private int PICK_PHOTO_CODE = 100;
    private byte[] byteArray;
    private String imageUrl,uid;
    private Bitmap bmp, img;
    private RadioButton caYes, caNo;
    private ImageView uploadPic;
    private Uri photoUri;
    private LinearLayout ca;
    private boolean editProfile;
    private boolean isCa = false;

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
        }
        Intent intent = getIntent();
        if (intent.hasExtra("editProfile"))
        {
            editProfile = getIntent().getExtras().getBoolean("editProfile");
            imageUrl = sharedPrefs.getString("profileImage", null);
            if (editProfile)
            {
                uid = sharedPrefs.getString("firebaseUid","");
                Picasso.with(ProfileNew.this)
                        .load(imageUrl)
                        .placeholder(R.drawable.default_profile_pic)
                        .into(profilePic);
                first.setText(sharedPrefs.getString("firstname",""));
                last.setText(sharedPrefs.getString("lastname",""));
                email.setText(sharedPrefs.getString("email",""));
                email.setEnabled(false);
                phoneNumber.setText(sharedPrefs.getString("phoneNumber",""));
                rollno.setText(sharedPrefs.getString("rollNumber", ""));
                rollno.setEnabled(false);
                username.setText(sharedPrefs.getString("username", ""));
                username.setEnabled(false);
                college.setText(sharedPrefs.getString("college", ""));
                college.setEnabled(false);
            }
        }
        if(intent.hasExtra("email")&&intent.hasExtra("firebaseuid"))
        {
            String memail = intent.getStringExtra("email");
            email.setText(memail);
            email.setEnabled(false);
            uid = intent.getStringExtra("firebaseuid");
            if(memail.endsWith("@nith.ac.in"))
            {
                college.setText("NIT Hamirpur");
                college.setEnabled(false);
            }
            if(memail.endsWith("@iiitu.ac.in"))
            {
                college.setText("IIIT Una");
                college.setEnabled(false);
            }
        }

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
             isCa = true;
            }
        });
        caNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isCa = false;
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
                    imageUrl = getString(R.string.defaultImageUrl);
                    Log.e("ImageUrl",imageUrl);
                    submitProfile();
                } else if(imageUrl != null)
                {
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
                        Log.e("imageurlbitmap",imageUrl);
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
        if (!username.getText().toString().isEmpty() && !first.getText().toString().isEmpty() && !last.getText().toString().isEmpty() && !email.getText().toString().isEmpty() &&
                !phoneNumber.getText().toString().isEmpty() && !college.getText().toString().isEmpty() && !rollno.getText().toString().isEmpty() && !imageUrl.isEmpty() ) {
            Intent intent = getIntent();
            editProfile = false;
            if (intent.hasExtra("editProfile")) {
                editProfile = getIntent().getExtras().getBoolean("editProfile");
                Log.e("status", "" + editProfile);
            }
            if (editProfile)
            {
                Log.e("imageEditprofile",imageUrl);
                RequestQueue queue = Volley.newRequestQueue(ProfileNew.this);
                StringRequest stringRequest = new StringRequest(Request.Method.PATCH, getString(R.string.baseUrl) + "/users/"+uid+"/?format=json", new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("putresponse",response);
                        editor.putString("username", username.getText().toString().trim());
                        editor.putString("firstname", first.getText().toString().trim());
                        editor.putString("lastname", last.getText().toString().trim());
                        editor.putString("name",first.getText().toString().trim()+" "+last.getText().toString().trim());
                        editor.putString("email", email.getText().toString().trim());
                        editor.putString("phoneNumber", phoneNumber.getText().toString().trim());
                        editor.putString("rollNumber", rollno.getText().toString().trim());
                        editor.putString("college", college.getText().toString().trim());
                        editor.putString("profileImage", imageUrl);
                        //editor.putString("firebaseuid",uid);
                        editor.commit();
                        progressBar.setVisibility(View.GONE);
                        finish();
                        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfileNew.this, error.toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", username.getText().toString().trim());
                        params.put("firstName", first.getText().toString().trim());
                        params.put("lastName", last.getText().toString().trim());
                        params.put("rollNumber", rollno.getText().toString().trim());
                        params.put("college", college.getText().toString().trim());
                        //params.put("campusAmbassador", false);
                        params.put("image", imageUrl);
                        params.put("campusAmbassador", "" + sharedPrefs.getBoolean("campusAmbassador", false));
                        params.put("phone", phoneNumber.getText().toString().trim());
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
            }
            else
                {
                    RequestQueue queue = Volley.newRequestQueue(ProfileNew.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.baseUrl) + "/users/?format=json", new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            int errorCode = 1;
//                            String token;
//                            final JSONObject jsonObject;
//                            try {
//                                jsonObject = new JSONObject(response);
//                                errorCode = (int) jsonObject.get("errorCode");
//                                if (!editProfile) {
//                                    token = (String) jsonObject.get("token");
//                                    editor.putString("token", token);
//                                    editor.apply();
//                                }
////                        Toast.makeText(ProfileNew.this, "token" + sharedPrefs.getString("token", ""), Toast.LENGTH_LONG).show();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Toast.makeText(ProfileNew.this, response, Toast.LENGTH_LONG).show();
//                            if (errorCode == 0) {
//                                Toast.makeText(ProfileNew.this, "error code" + errorCode, Toast.LENGTH_SHORT).show();
                                editor.putString("firebaseUid", uid);
                                editor.putString("username", username.getText().toString().trim());
                                editor.putString("name", first.getText().toString().trim()+" "+last.getText().toString().trim());
                                editor.putString("firstname",first.getText().toString().trim());
                                editor.putString("lastname",last.getText().toString().trim());
                                editor.putString("email", email.getText().toString().trim());
                                editor.putString("phoneNumber", phoneNumber.getText().toString().trim());
                                editor.putString("rollNumber", rollno.getText().toString().trim());
                                editor.putString("college", college.getText().toString().trim());
                                editor.putString("profileImage", imageUrl);
                                editor.putBoolean("campusAmbassador", isCa);
                                editor.putBoolean("profileStatus", true);
                                editor.putBoolean("loginStatus",true);
                                editor.putInt("OmegleReport", 0);
                                editor.putBoolean("OmegleAllowed", true);
                                editor.commit();
                                progressBar.setVisibility(View.GONE);
                                Log.e("Image",imageUrl);
                                Intent i = new Intent(ProfileNew.this, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
//
//                            } else {
//                                Toast.makeText(ProfileNew.this, "Unknown error" + response, Toast.LENGTH_SHORT).show();
//                                Log.e("error", response);
//                                progressBar.setVisibility(View.GONE);
//                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ProfileNew.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ProfileNew.this, "Try again with another username!!!", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded; charset=UTF-8";
                        }

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("firebase" , uid);
                            params.put("username", username.getText().toString().trim());
                            params.put("phone", phoneNumber.getText().toString().trim());
                            params.put("email", email.getText().toString().trim());
                            params.put("firstName", first.getText().toString().trim());
                            params.put("lastName", last.getText().toString().trim());
                            params.put("omegleReports","0");
                            params.put("omegleAllowed","true");
                            params.put("profileImage",imageUrl);
                            params.put("campusAmbassador",String.valueOf(isCa));
                            params.put("rollNumber", rollno.getText().toString().trim());
                            params.put("collegeName", college.getText().toString().trim());
//                          params.put("rollNumber", rollno.getText().toString());
//                            params.put("college", college.getText().toString());
                            //params.put("campusAmbassador", false);
//                            params.put("image", imageUrl);
//                            params.put("campusAmbassador", "" + sharedPrefs.getBoolean("campusAmbassador", false));
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
                }
        }
        else
            {
                Toast.makeText(ProfileNew.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }


    }

    private void getUI() {
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        first = findViewById(R.id.firstname);
        last = findViewById(R.id.lastname);
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