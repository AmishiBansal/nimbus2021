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
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.nith.appteam.nimbus2020.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class CampusAmbassadorPost extends AppCompatActivity {
    int PICK_PHOTO_CODE = 100;
    private Button submitPost, uploadPicture;
    private EditText link;
    private String socialUrl, imageUrl, hash;
    private ProgressBar progressBar;
    private byte[] byteArray;
    private Bitmap bmp, img;
    private ImageView image;
    private Uri photoUri;
    private SharedPreferences sharedPrefs;
    private LinearLayout info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_ambassador_post);
        link = findViewById(R.id.link);
        sharedPrefs = getSharedPreferences("app", MODE_PRIVATE);
        submitPost = findViewById(R.id.submit_post);
        uploadPicture = findViewById(R.id.upload_picture);
        info = findViewById(R.id.info);
        progressBar = findViewById(R.id.post_progress);
        image = findViewById(R.id.imageUpload);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_PHOTO_CODE);
                }
            }
        });

        submitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                submitPost.setVisibility(View.INVISIBLE);
                socialUrl = link.getText().toString();
                hash = md5(socialUrl);
                if (photoUri != null) {
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    getImageUrl(bitmap);
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
            image.setImageBitmap(img);
            submitPost.setVisibility(View.VISIBLE);
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
                        submitPost();
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
//                      finish();
                        overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                        submitPost.setVisibility(View.VISIBLE);
                        Toast.makeText(CampusAmbassadorPost.this, "Upload Failed" + error.getDescription() + " requestId" + requestId, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        // your code here
                    }
                })
                .dispatch(CampusAmbassadorPost.this);
    }


    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void submitPost() {
        if (!socialUrl.equals("") && URLUtil.isValidUrl(socialUrl)) {
            if (!(hash == null) && !(imageUrl == null)) {
                Log.e("generated URL hash", hash);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.baseUrl) + "/views/inc_points", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.equals("ok")) {
                            getPoints();
//                            Toast.makeText(CampusAmbassadorPost.this, "Post Uploaded", Toast.LENGTH_SHORT).show();
//                            info.setVisibility(View.VISIBLE);
                        } else try {
                            JSONObject status = new JSONObject(response);
                            if (status.get("status").equals("false")) {
                                submitPost.setVisibility(View.VISIBLE);
                                Toast.makeText(CampusAmbassadorPost.this, "Cannot upload same URL again.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CampusAmbassadorPost.this, error.toString() + error.getCause(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        submitPost.setVisibility(View.VISIBLE);

                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("access-token", sharedPrefs.getString("token", ""));
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("image_url", imageUrl);
                        params.put("post_url", socialUrl);
                        params.put("key", socialUrl);//change to hash if necessary
                        params.put("token", "" + sharedPrefs.getString("token", ""));
                        return params;
                    }

                };
                requestQueue.add(stringRequest);
            }
        } else {
            Toast.makeText(CampusAmbassadorPost.this, "Please enter valid URL", Toast.LENGTH_SHORT).show();
            submitPost.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }

    public void getPoints() {
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.baseUrl) + "/user/points", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CampusAmbassadorPost.this, "Post Uploaded", Toast.LENGTH_SHORT).show();
                info.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String caPoints = jsonObject.getString("campusAmbassadorPoints");
                    String normalPoints = jsonObject.getString("userPoints");
                    editor.putString("normalPoints", normalPoints);
                    editor.putString("caPoints", caPoints);
                    Log.e("caPoints", caPoints);
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CampusAmbassadorPost.this, error.toString() + error.getCause(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
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
}