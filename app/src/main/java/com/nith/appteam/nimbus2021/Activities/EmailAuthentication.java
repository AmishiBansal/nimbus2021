package com.nith.appteam.nimbus2021.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.nith.appteam.nimbus2021.R;

public class EmailAuthentication extends AppCompatActivity {

    private TextInputLayout memail,mpass,merror;
    private Button next,done;
    private String email;
    private FirebaseAuth auth;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_authentication);
        initViews();
        setOnClick();
    }

    private void setOnClick() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = memail.getEditText().getText().toString();
                if(email.isEmpty())
                {
                    memail.setError("Required Field");
                }
                else if(!(email.endsWith("@nith.ac.in") ||email.endsWith("@iiitu.ac.in")))
                {
                    memail.setError("Use Official College Emails");
                }
                else
                    {
                        next.setVisibility(View.GONE);
                        mpass.setVisibility(View.VISIBLE);
                        done.setVisibility(View.VISIBLE);
                        memail.setErrorEnabled(false);
                        memail.getEditText().setFocusable(false);
                    }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = mpass.getEditText().getText().toString();
                merror.setErrorEnabled(false);
                if(pass.isEmpty())
                {
                    mpass.setError("Required Field");
                }
                else if(pass.length()<6)
                {
                    mpass.setError("Password Length must be greater than or equals 6");
                }
                else
                    {
                        mpass.setErrorEnabled(false);
                        SignUpWithCredential(email,pass);
                    }
            }
        });
    }

    private void SignUpWithCredential(final String email, final String pass) {

        pd = new ProgressDialog(this);
        pd.show();
        pd.setMessage("Please Wait....");
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Verification Mail Sent to Your Email",Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            pd.dismiss();
                            startActivity(new Intent(EmailAuthentication.this,Login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    {
                        try
                        {
                            throw task.getException();
                        }
                        catch (FirebaseAuthUserCollisionException existemail)
                        {
                            SignInUser(email,pass);
                        }
                        catch (Exception e)
                        {
                            merror.setError(e.getMessage());
                            pd.dismiss();
                        }
                    }
            }
        });
    }

    private void SignInUser(String email, String pass) {
        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                FirebaseUser user = authResult.getUser();
                if(user.isEmailVerified())
                {
                    Toast.makeText(getApplicationContext(),"User Signed in Successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EmailAuthentication.this,Login.class);
                    intent.putExtra("SignInFlag",true);
                    intent.putExtra("FirebaseUID",authResult.getUser().getUid());
                    intent.putExtra("LoginEmail",authResult.getUser().getEmail());
                    startActivity(intent);
                    pd.dismiss();
                    finish();
                }
                else
                    {
                        Toast.makeText(getApplicationContext(),"Email Not Verified",Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        startActivity(new Intent(EmailAuthentication.this,Login.class));
                        finish();
                    }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                merror.setError(e.getMessage());
                pd.dismiss();
            }
        });
    }

    private void initViews() {
        memail = findViewById(R.id.email_login);
        mpass = findViewById(R.id.pass_login);
        merror = findViewById(R.id.error_login);
        next = findViewById(R.id.login_next);
        done = findViewById(R.id.login_done);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}