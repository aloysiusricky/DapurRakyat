package com.dr.betadapurrakyat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.LogTime;
import com.dr.betadapurrakyat.Model.UserData;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    //Login
    private FirebaseAuth mAuth;
    private EditText loginPhone, loginName;
    private String sPhone, sFullname;
    private Button button_login;
    private ProgressBar mProgress;
    ///
    //Register

    //
    //firebase
    private String sPhoneKey;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseDatabase database;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideKeyboard();
        mAuth = FirebaseAuth.getInstance();
        //Login
        loginPhone = findViewById(R.id.phone_text);
        loginName = findViewById(R.id.fullname_text);
        mProgress = findViewById(R.id.pb_login);
        //PhoneAuthenticationProvider
        callback();
        button_login = findViewById(R.id.button_login);

        buttonLoginListener();

        //
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

    }


    @SuppressLint("ClickableViewAccessibility")
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();



        }

    }

    private void setUpLogIn() {
        Log.d(TAG, "setUpLogIn: ");
        sPhone = loginPhone.getText().toString();
        if (TextUtils.isEmpty(sPhone) || sPhone.length() < 10) {
            loginPhone.setError("No Telepon anda tidak valid");
            return;
        }
        sFullname = loginName.getText().toString();
        if (TextUtils.isEmpty(sFullname)) {
            loginName.setError("Harap Masukan Nama Lengkap Sesuai Identitas");
            return;
        }


    }
    private void buttonLoginListener() {
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                hideKeyboard();
                progressing_dialog();
                setUpLogIn();
                phoneAuth();

            }
        });
    }

    private void progressing_dialog() {
        Toast.makeText(MainActivity.this, "Proses Verifikasi", Toast.LENGTH_LONG).show();
        mProgress.setVisibility(View.VISIBLE);
    }

    private void phoneAuth() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                sPhone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void callback() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId;
//                mResendToken = token;

                // ...
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mProgress.setVisibility(View.GONE);
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            sPhoneKey = user.getPhoneNumber();

                            login();



                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void login() {
        myRef.child("User").child(sPhoneKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    myRef.child("User").child(sPhoneKey).child("fullname").setValue(sFullname);
                    Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeActivity);
                    finish();

                }else {
                    String uName = dataSnapshot.child("fullname").getValue().toString();
                    if (uName.equals(sFullname)){
                        Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(homeActivity);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Anda Sudah Terdaftar\nNama User Tidak Sesuai, Mohon Ulangi Kembali", Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        return;
                    }
                }

                }

                @Override
                public void onCancelled (DatabaseError databaseError){

                }

        });

    }


}