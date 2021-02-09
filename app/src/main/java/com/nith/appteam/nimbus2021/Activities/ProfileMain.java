package com.nith.appteam.nimbus2021.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nith.appteam.nimbus2021.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileMain extends AppCompatActivity {

    private CircleImageView profilePicture;
    private TextView name,username,college, phoneNumber, rollno, normalPoints, caPoints;
    private ImageView diamondsPink;
    private SharedPreferences sharedPreferences;
    private FloatingActionButton editProfile;

    @Override
    protected void onResume() {
        getProfile();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        TextView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        getUI();
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        getProfile();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileMain.this, ProfileNew.class);
                i.putExtra("editProfile", true);
                startActivity(i);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);

            }
        });
    }

    private void getUI() {
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        college = findViewById(R.id.college);
        phoneNumber = findViewById(R.id.phone_number);
        editProfile = findViewById(R.id.edit_profile);
        profilePicture = findViewById(R.id.profile_picture);
    }

    private void getProfile() {
        name.setText(sharedPreferences.getString("name", "Your Name"));
        username.setText(sharedPreferences.getString("username","Your username"));
        college.setText(sharedPreferences.getString("college", "Your college name"));
        phoneNumber.setText(sharedPreferences.getString("phoneNumber", "123456890"));
        String imageUrl = sharedPreferences.getString("profileImage", String.valueOf(R.string.defaultImageUrl));
        Picasso.with(ProfileMain.this)
                .load(imageUrl)
                .placeholder(R.drawable.default_profile_pic)
                .into(profilePicture);

    }
}
