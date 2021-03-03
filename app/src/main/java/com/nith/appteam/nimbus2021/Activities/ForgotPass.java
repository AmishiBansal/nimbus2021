package com.nith.appteam.nimbus2021.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.nith.appteam.nimbus2021.R;

public class ForgotPass extends AppCompatActivity {

    FirebaseAuth auth;
    Button sendmail;
    TextInputLayout email;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.forgotpass_email);
        sendmail = findViewById(R.id.send_mail_forgotpass);
        bar = findViewById(R.id.progress_bar);

        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail = email.getEditText().getText().toString();
                email.setErrorEnabled(false);
                if(memail.isEmpty())
                {
                    email.setError("Required Field");
                }
                else if(!(memail.endsWith("@nith.ac.in") || memail.endsWith("@iiitu.ac.in")))
                {
                    email.setError("Use Official College email");
                }
                else
                    {
                        bar.setVisibility(View.VISIBLE);
                        auth.sendPasswordResetEmail(memail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                bar.setVisibility(View.GONE);
                                new AlertDialog.Builder(ForgotPass.this)
                                        .setTitle("Password Reset")
                                        .setMessage("Mail to reset password has been sent to your Official College Email\nFollow the link in the mail to reset your password")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                auth.signOut();
                                                startActivity(new Intent(ForgotPass.this,Login.class));
                                                finish();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.GONE);
                            }
                        });
                    }
            }
        });
    }
}