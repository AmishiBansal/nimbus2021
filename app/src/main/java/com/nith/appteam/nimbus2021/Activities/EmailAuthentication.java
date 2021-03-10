package com.nith.appteam.nimbus2021.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.nith.appteam.nimbus2021.Utils.Constant;

public class EmailAuthentication extends AppCompatActivity {

    private TextInputLayout memail,mpass,merror,mrepass;
    private Button next,done;
    private String email,pass,repass;
    private FirebaseAuth auth;
    private ProgressDialog pd;
    private TextView forgotpass;
    private TextWatcher mrepasstextwatcher,mpasstextwatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_authentication);
        mrepasstextwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().equals(pass))
                {
                    Log.e("watcher",s.toString());
                    mrepass.setError("Password Does Not Match");
                }
                else
                    {
                        mrepass.setErrorEnabled(false);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        mpasstextwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().equals(mrepass.getEditText().getText().toString()) && !mrepass.getEditText().getText().toString().isEmpty())
                {
                    Log.e("watcher",s.toString());
                    mrepass.setError("Password Does Not Match");
                }
                else
                {
                    mrepass.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        initViews();
        setOnClick();
    }

    private Boolean isLoginClicked() {
        return getIntent().getBooleanExtra("login",false);
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
                    Log.e("isLogin",isLoginClicked().toString());
                    if(isLoginClicked())
                    {
                        mpass.setVisibility(View.VISIBLE);
                        done.setVisibility(View.VISIBLE);
                        forgotpass.setVisibility(View.VISIBLE);
                        memail.setErrorEnabled(false);
                        memail.getEditText().setFocusable(false);
                    }
                    else
                    {
                        mpass.setVisibility(View.VISIBLE);
                        mrepass.setVisibility(View.VISIBLE);
                        done.setVisibility(View.VISIBLE);
                        mpass.setHint("Create Password");
                        memail.setErrorEnabled(false);
                        memail.getEditText().setFocusable(false);

                        mpass.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                pass = mpass.getEditText().getText().toString();
                                mpass.setErrorEnabled(false);
                                if(!hasFocus)
                                {
                                    if(pass.isEmpty())
                                    {
                                        mpass.setError("Required Field");
                                    }
                                    else if(pass.length()<6)
                                    {
                                        mpass.setError("Password Length must be greater than or equals 6");
                                    }
                                }
                                else
                                {
                                    mpass.getEditText().addTextChangedListener(mpasstextwatcher);
                                }
                            }
                        });
                        mrepass.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                repass = mrepass.getEditText().getText().toString();
                                if(!hasFocus)
                                {
                                    mrepass.setErrorEnabled(false);
                                    Log.e("focus","not has focus");
                                    if(repass.isEmpty())
                                    {
                                        mrepass.setError("Field Required");
                                    }
                                    else if(!repass.equals(pass))
                                    {
                                        mrepass.setError("Password Does Not Match");
                                    }
                                }
                                else
                                {
                                    Log.e("focus","has focus");
                                    if(!repass.equals(pass))
                                    {
                                        mrepass.setError("Password Does Not Match");
                                    }
                                    mrepass.getEditText().addTextChangedListener(mrepasstextwatcher);
                                }
                            }
                        });
                    }

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
                    if(isLoginClicked())
                    {
                        pd = new ProgressDialog(EmailAuthentication.this);
                        pd.show();
                        pd.setMessage("Please Wait....");
                        pd.setCanceledOnTouchOutside(false);
                        SignInUser(email,pass);
                    }
                    else
                    {
                        String repass = mrepass.getEditText().getText().toString();
                        mrepass.setErrorEnabled(false);
                        if(repass.isEmpty())
                        {
                            mrepass.setError("Field Required");
                        }
                        else if(!repass.equals(pass))
                        {
                            mrepass.setError("Password Does Not Match");
                        }
                        else
                        {
                            SignUpWithCredential(email,pass);
                        }
                    }
                }
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailAuthentication.this,ForgotPass.class));
            }
        });
    }

    private void SignUpWithCredential(final String email, final String pass) {

        pd = new ProgressDialog(this);
        pd.show();
        pd.setMessage("Please Wait....");
        pd.setCanceledOnTouchOutside(false);
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            new AlertDialog.Builder(EmailAuthentication.this)
                                    .setTitle("Email Verification")
                                    .setMessage("Verification mail has been sent to your Official College Email Id\nLogin After Verifying it")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            auth.signOut();
                                            startActivity(new Intent(EmailAuthentication.this,Login.class));
                                            finish();
                                        }
                                    })
                                    .setCancelable(false)
                                    .show();

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
                    Toast.makeText(getApplicationContext(),"Email Not Verified",Toast.LENGTH_LONG).show();
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
        mrepass = findViewById(R.id.repass_login);
        merror = findViewById(R.id.error_login);
        next = findViewById(R.id.login_next);
        done = findViewById(R.id.login_done);
        auth = FirebaseAuth.getInstance();
        forgotpass = findViewById(R.id.forgotpass);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}